package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.awt.image.PixelGrabber;
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
    /**
     * 分页查询菜品
     * @param dishPageQueryDTO: 分页查询DTO
     * @return 分页查询结果
     */
    Page<DishVO> selectPage(DishPageQueryDTO dishPageQueryDTO);
    
    @Select("select * from dish where id = #{id}")
    Dish selectById(Long id);

    void deleteBatchIds(List<Long> ids);

    void deleteFlavorsBatchIds(List<Long> ids);
    
    SetmealDish selectByDishId(Long dishId);

    /**
     * 切换菜品起售停售状态
     * @param status 菜品状态
     * @param id 菜品id
     */
    void updateStatus(@Param("status") Integer status, @Param("id") Long id);

    List<DishFlavor> selectFlavorsByDishId(Long id);

    void updateById(DishDTO dishDTO);
    
    void updateFlavors(List<DishFlavor> flavors, Long dishId);
}