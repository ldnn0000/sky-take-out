package com.sky.aspect;

import com.sky.anno.AutoFill;

import com.sky.context.BaseContext;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


// 自动填充切面
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //指定切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.anno.AutoFill)")
    public void pointCut() {
    }

    ;

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
        //判断参数是否为Employee类型
        Employee employee = new Employee();
        if (args[0] instanceof Employee) {
            // 转换为Employee类型
            employee = (Employee) args[0];
        }
        //根据操作类型填充数据
        switch (operationType) {
            case INSERT:
                // 插入数据时填充createUser和updateUser
                log.info("插入数据时填充createUser和updateUser");
                // 填充createUser和updateUser
                employee.setCreateUser(currentLoginId);
                employee.setUpdateUser(currentLoginId);
                // 填充createTime和updateTime
                employee.setCreateTime(LocalDateTime.now());
                employee.setUpdateTime(LocalDateTime.now());
                break;
            case UPDATE:
                // 更新数据时填充updateUser
                log.info("更新数据时填充updateUser");
                // 填充updateUser
                employee.setUpdateUser(currentLoginId);
                // 填充updateTime
                employee.setUpdateTime(LocalDateTime.now());
                break;
            default:
                break;
        }
    }
}
