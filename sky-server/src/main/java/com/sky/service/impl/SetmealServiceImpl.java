package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import com.sky.vo.DishItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.page(setmealPageQueryDTO);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());
        return pageResult;
    }

    @Override
    @Transactional
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        // 将setmealDTO转换为setmeal
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 保存套餐
        setmealMapper.insert(setmeal);
        // 保存套餐菜品关系
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
            setmealMapper.insertSetmealDishes(setmealDishes);
        }
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        // 套餐下有菜品未起售的不能起售
        Integer count = setmealMapper.countStatusBySetmealId(id);
        if (count > 0 && status == 1) {
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
        }
        // 更新套餐状态
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.updateStatus(setmeal);
    }

    @Override
    public SetmealVO getById(Long id) {
        SetmealVO setmealVO = setmealMapper.selectById(id);
        if (setmealVO == null) {
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_NOT_FOUND);
        }
        // 查询套餐菜品关系
        List<SetmealDish> setmealDishes = setmealMapper.selectSetmealDishesBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO) {
        // 查询套餐是否存在
        SetmealVO setmealVO = setmealMapper.selectById(setmealDTO.getId());
        if (setmealVO == null) {
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_NOT_FOUND);
        }
        Setmeal setmeal = new Setmeal();
        // 将setmealDTO转换为setmeal
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 更新套餐
        setmealMapper.update(setmeal);
        // 更新套餐菜品关系
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
            // 先删除套餐菜品关系
            setmealMapper.deleteSetmealDishesBySetmealId(setmeal.getId());
            // 保存套餐菜品关系
            setmealMapper.insertSetmealDishes(setmealDishes);
        }
    }
    @Override
    @Transactional
    public void deleteSetmeal(List<Long> ids) {
        // 起售中的套餐不能删除
        Integer count = setmealMapper.countStatusBySetmealIds(ids);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        // 删除套餐菜品关系
        setmealMapper.deleteSetmealDishesBySetmealIds(ids);
        // 删除套餐
        setmealMapper.deleteBatchIds(ids);
    }
    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

}
    