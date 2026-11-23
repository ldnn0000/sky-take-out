package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 分页查询套餐列表
     * @param setmealPageQueryDTO: 分页查询套餐列表的DTO
     * @return: 分页查询套餐列表的结果
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);
    /**
     * 新增套餐
     * @param setmealDTO: 套餐DTO
     */
    void addSetmeal(SetmealDTO setmealDTO);

    /**
     * 切换套餐状态
     * @param id 套餐id
     * @param status 状态 0:停用 1:启用
     */
    void updateStatus(Long id, Integer status);
    /**
     * 根据id查询套餐
     * @param id 套餐id
     * @return 套餐VO
     */
    SetmealVO getById(Long id);
    /**
     * 修改套餐
     * @param setmealDTO 套餐DTO
     */
    void updateSetmeal(SetmealDTO setmealDTO);
    /**
     * 删除套餐
     * @param ids 套餐id列表
     */
    void deleteSetmeal(List<Long> ids);
}
