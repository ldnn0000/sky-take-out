package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import java.util.List;

public interface EmployeeService {

    /**
     * 员工登录
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     *
     * @param employeeDTO: 要添加的员工信息
     */
    void addEmployee(EmployeeDTO employeeDTO);

    PageResult getEmployeePage(EmployeePageQueryDTO employeePageQueryDTO);

    void updateStatus(Integer status, Long id);

    Employee getEmployeeById(Long id);

    void updateEmployee(EmployeeDTO employeeDTO);
}
