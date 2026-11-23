package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {
    /**
     * 新增菜品
     * @param dishDTO 菜品DTO
     */
    void addDish(DishDTO dishDTO);
    /**
     * 分页查询菜品
     * @param dishPageQueryDTO 分页查询DTO
     * @return 分页查询结果
     */
    PageResult pageDish(DishPageQueryDTO dishPageQueryDTO);
    /**
     * 批量删除菜品
     * @param ids 菜品id列表
     */
    void deleteDish(List<Long> ids);
    /**
     * 切换菜品起售停售状态
     * @param status 菜品状态
     * @param id 菜品id
     */
    void updateStatus(Integer status, Long id);
    /**
     * 根据id查询菜品
     * @param id 菜品id
     * @return 菜品
     */
    DishVO getDishById(Long id);
    /**
     * 修改菜品
     * @param dishDTO 菜品DTO
     */
    void updateDish(DishDTO dishDTO);

    /**
     * 根据分类id查询菜品列表
     * @param categoryId 分类id
     * @return 菜品列表
     */
    List<DishVO> listDishByCategoryId(Long categoryId);
}
