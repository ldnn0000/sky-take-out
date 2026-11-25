package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.BaseException;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishMapper dishMapper;
    public DishServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }
    @Transactional
    @Override
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        //将dishDTO转换为dish
        BeanUtils.copyProperties(dishDTO, dish);
        // 保存菜品
        dishMapper.insert(dish);
        // 保存菜品id
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && !flavors.isEmpty()){
            // 保存口味
            flavors.forEach(flavor -> flavor.setDishId(dishId));
            dishMapper.insertFlavors(flavors);
        }
    }
    @Override
    public PageResult pageDish(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.selectPage(dishPageQueryDTO);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());
        return pageResult;
    }
    @Transactional
    @Override
    public void deleteDish(List<Long> ids) {
        // 检测菜品是否启用，启用的菜品不能删除
        if(ids.stream().anyMatch(id -> dishMapper.selectById(id).getStatus() == 1)){
            throw new BaseException(MessageConstant.DISH_ON_SALE);
        }
        // 被套餐关联的菜品不能删除
        if(ids.stream().anyMatch(id -> dishMapper.selectByDishId(id) != null)){
            throw new BaseException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 批量删除菜品
        dishMapper.deleteBatchIds(ids);
        // 批量删除菜品口味
        dishMapper.deleteFlavorsBatchIds(ids);
    }
    @Transactional
    @Override
    public void updateStatus(Integer status, Long id) {
        // 更新菜品状态
        dishMapper.updateStatus(status, id);
    }
    @Override
    public DishVO getDishById(Long id) {
        // 根据id查询菜品
        Dish dish = dishMapper.selectById(id);
        // 将dish转换为dishVO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        // 查询菜品口味
        List<DishFlavor> flavors = dishMapper.selectFlavorsByDishId(id);
        // 将口味添加到dishVO
        dishVO.setFlavors(flavors);
        return dishVO;
    }
    @Override
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        // 更新菜品
        dishMapper.updateById(dishDTO);
        // 更新菜品口味
        dishMapper.deleteFlavorsBatchIds(List.of(dishDTO.getId()));
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && !flavors.isEmpty()){
            // 保存口味
            flavors.forEach(flavor -> flavor.setDishId(dishDTO.getId()));
            dishMapper.insertFlavors(flavors);
        }
    }
    @Override
    public List<DishVO> listDishByCategoryId(Long categoryId) {
        // 根据分类id查询菜品列表
        List<DishVO> dishList = dishMapper.selectByCategoryId(categoryId);
        return dishList;
    }
    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishMapper.selectFlavorsByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }
}
