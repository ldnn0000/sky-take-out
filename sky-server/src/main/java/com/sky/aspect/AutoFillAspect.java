package com.sky.aspect;

import com.sky.anno.AutoFill;

import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;


// 自动填充切面
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //指定切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.anno.AutoFill)")
    public void pointCut() {
    }

    //指定前置通知
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        //获取当前拦截方法上的AutoFill注解对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        //从上下文获取当前操作类型
        OperationType operationType = autoFill.value();
        // 从上下文获取当前登录用户id
        Long currentLoginId = BaseContext.getCurrentId();
        //获取当前拦截方法的参数
        Object[] args = joinPoint.getArgs();
        //根据操作类型填充数据
        switch (operationType) {
            case INSERT:
                // 插入数据时填充createTime、updateTime、createUser、updateUser
                log.info("插入数据时填充createTime、updateTime、createUser、updateUser");
                for (Object arg : args) {
                    fillEntity(arg, LocalDateTime.now(), currentLoginId, true);
                }
                break;
            case UPDATE:
                // 更新数据时填充updateTime、updateUser
                log.info("更新数据时填充updateTime、updateUser");
                for (Object arg : args) {
                    fillEntity(arg, LocalDateTime.now(), currentLoginId, false);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 填充方法参数中的实体对象，若参数为集合（如 List<实体>）则逐一对元素填充
     *
     * @param arg        方法参数
     * @param now        当前时间
     * @param currentId  当前登录用户id
     * @param isInsert   是否为插入操作
     */
    private void fillEntity(Object arg, LocalDateTime now, Long currentId, boolean isInsert) {
        if (arg == null) {
            return;
        }
        if (arg instanceof Collection) {
            // 参数为集合，遍历填充每个元素
            for (Object item : (Collection<?>) arg) {
                fillSingle(item, now, currentId, isInsert);
            }
            return;
        }
        fillSingle(arg, now, currentId, isInsert);
    }

    /**
     * 反射填充单个实体对象中存在的字段
     *
     * @param entity    实体对象
     * @param now       当前时间
     * @param currentId 当前登录用户id
     * @param isInsert  是否为插入操作
     */
    private void fillSingle(Object entity, LocalDateTime now, Long currentId, boolean isInsert) {
        if (entity == null) {
            return;
        }
        fillSetter(entity, "setCreateTime", now, isInsert);
        fillSetter(entity, "setUpdateTime", now, true);
        fillSetter(entity, "setCreateUser", currentId, isInsert);
        fillSetter(entity, "setUpdateUser", currentId, true);
    }

    /**
     * 通过反射调用实体类的set方法，实体类不存在对应字段时静默跳过
     *
     * @param entity       实体对象
     * @param methodName   set方法名
     * @param value        待填充的值
     * @param needFill     是否需要填充
     */
    private void fillSetter(Object entity, String methodName, Object value, boolean needFill) {
        // 无需填充或值为空时不处理
        if (!needFill || value == null) {
            return;
        }
        try {
            Method method = entity.getClass().getMethod(methodName, value.getClass());
            method.invoke(entity, value);
        } catch (Exception e) {
            // 实体类没有对应的字段，跳过即可
            log.debug("实体类{}不包含字段对应的set方法{}", entity.getClass().getSimpleName(), methodName);
        }
    }
}