package com.sky.mapper;

import com.sky.anno.AutoFill;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId: 分类id
     * @return 菜品数量
     */
    Integer countByCategoryId(Long categoryId);
    /**
     * 保存菜品
     * @param dish: 菜品
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);
    /**
     * 保存菜品口味
     * @param flavors: 菜品口味列表
     */
    @AutoFill(OperationType.INSERT)
    void insertFlavors(List<DishFlavor> flavors);
}
