package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id: 分类id
     * @return 套餐数量
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);
    /**
     * 分页查询套餐列表
     * @param setmealPageQueryDTO: 分页查询套餐列表的DTO
     * @return: 分页查询套餐列表的结果
     */
    Page<SetmealVO> page(SetmealPageQueryDTO setmealPageQueryDTO);
    /**
     * 新增套餐
     * @param setmeal: 套餐实体
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);
    /**
     * 新增套餐菜品关系
     * @param setmealDishes: 套餐菜品关系列表
     */
    void insertSetmealDishes(List<SetmealDish> setmealDishes);
    /**
     * 切换套餐状态
     * @param setmeal: 套餐实体
     */
    @AutoFill(OperationType.UPDATE)
    void updateStatus(Setmeal setmeal);
    /**
     * 根据id查询套餐
     * @param id 套餐id
     * @return 套餐实体
     */
    SetmealVO selectById(Long id);

    List<SetmealDish> selectSetmealDishesBySetmealId(Long id);
    /**
     * 更新套餐
     * @param setmeal: 套餐实体
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
    /**
     * 删除套餐菜品关系
     * @param id 套餐id
     */
    void deleteSetmealDishesBySetmealId(Long id);
    /**
     * 批量删除套餐
     * @param ids 套餐id列表
     */
    void deleteBatchIds(List<Long> ids);
    /**
     * 查询套餐下是否有未起售菜品
     * @param id 套餐id
     * @return 是否有未起售菜品
     */
    Integer countStatusBySetmealId(Long id);
    /**
     * 查询套餐是否起售
     * @param ids 套餐id
     * @return 是否起售
     */
    Integer countStatusBySetmealIds(List<Long> ids);
    /**
     * 删除套餐菜品关系
     * @param ids 套餐id列表
     */
    void deleteSetmealDishesBySetmealIds(List<Long> ids);
    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

}