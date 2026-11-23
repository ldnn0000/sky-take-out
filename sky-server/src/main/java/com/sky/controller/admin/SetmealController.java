package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {
    private final SetmealService setmealService;
    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
    }
    /**
     * 分页查询套餐列表
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        //分页查询套餐
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * 新增套餐
     * @param setmealDTO 套餐DTO
     * @return 新增成功
     */
    @PostMapping
    public Result<String> addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        // 调用服务层方法添加套餐
        setmealService.addSetmeal(setmealDTO);
        return Result.success("添加成功");
    }
    /**
     * 切换套餐状态
     * @param id 套餐id
     */
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status, Long id) {
        // 调用服务层方法切换套餐状态
        setmealService.updateStatus(id, status);
        return Result.success("切换成功");
    }
    /**
     * 根据id查询套餐
     * @param id 套餐id
     * @return 套餐VO
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        // 调用服务层方法根据id查询套餐
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }
    /**
     * 修改套餐
     * @param setmealDTO 套餐DTO
     * @return 修改成功
     */
    @PutMapping
    public Result<String> updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        // 调用服务层方法修改套餐
        setmealService.updateSetmeal(setmealDTO);
        return Result.success("修改成功");
    }
    @DeleteMapping
    public Result<String> deleteSetmeal(@RequestParam List<Long> ids) {
        // 调用服务层方法删除套餐
        setmealService.deleteSetmeal(ids);
        return Result.success("删除成功");
    }
}
