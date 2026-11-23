package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * 新增菜品
     *
     * @param dishDTO 菜品DTO
     * @return 新增成功
     */
    @PostMapping
    public Result<String> addDish(@RequestBody DishDTO dishDTO) {
        // 调用服务层方法添加菜品
        dishService.addDish(dishDTO);
        return Result.success("添加成功");
    }

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO 分页查询DTO
     * @return 分页查询结果
     */
    @GetMapping("/page")
    public Result<PageResult> pageDish(DishPageQueryDTO dishPageQueryDTO) {
        // 调用服务层方法分页查询菜品
        PageResult dishPageResult = dishService.pageDish(dishPageQueryDTO);
        return Result.success(dishPageResult);
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> deleteDish(@RequestParam List<Long> ids) {
        // 调用服务层方法批量删除菜品
        dishService.deleteDish(ids);
        return Result.success("删除成功");
    }

    /**
     * 切换菜品起售停售状态
     *
     * @param id 菜品id
     * @return 切换成功
     */
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status, @RequestParam("id") Long id) {
        // 调用服务层方法切换菜品起售停售状态
        dishService.updateStatus(status, id);
        return Result.success("切换成功");
    }

    /**
     * 根据id查询菜品
     *
     * @param id 菜品id
     * @return 菜品
     */
    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        // 调用服务层方法根据id查询菜品
        DishVO dish = dishService.getDishById(id);
        return Result.success(dish);
    }
    /**
     * 修改菜品
     * @param dishDTO 菜品DTO
     * @return 修改成功
     */
    @PutMapping
    public Result<String> updateDish(@RequestBody DishDTO dishDTO) {
        // 调用服务层方法修改菜品
        dishService.updateDish(dishDTO);
        return Result.success("修改成功");
    }
    /**
     * 根据分类id查询菜品列表
     * @param categoryId 分类id
     * @return 菜品列表
     */
    @GetMapping("/list")
    public Result<List<DishVO>> listDishByCategoryId(@RequestParam Long categoryId) {
        // 调用服务层方法根据分类id查询菜品列表
        List<DishVO> dishList = dishService.listDishByCategoryId(categoryId);
        return Result.success(dishList);
    }
}
