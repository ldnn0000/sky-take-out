package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username: 员工用户名
     * @return 员工对象
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);
    
    /**
     * 插入数据
     * @param employee: 员工对象
     */
    @AutoFill(OperationType.INSERT)
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) " +
            "values (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    /**
     * 分页查询员工
     * @param name: 员工姓名
     * @return 分页结果
     */
    Page<Employee> selectPage(String name);
    
    /**
     * 更新员工
     * @param employee: 员工对象
     */
    @AutoFill(OperationType.UPDATE)
    void updateById(Employee employee);

    Employee selectById(Long id);
    
}
