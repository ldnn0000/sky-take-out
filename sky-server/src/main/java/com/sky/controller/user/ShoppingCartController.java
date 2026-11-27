package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * 添加购物车
     */
    @PostMapping("/add")
    public Result<String> addShoppingCart(@RequestBody ShoppingCartDTO shoppingDTO) {
        // 调用服务层方法添加购物车
        shoppingCartService.addShoppingCart(shoppingDTO);
        return Result.success("添加成功");
    }

    @GetMapping("/list")
    public Result<List<ShoppingCart>> listShoppingCart() {
        // 调用服务层方法查询购物车列表
        List<ShoppingCart> list = shoppingCartService.listShoppingCart();
        return Result.success(list);
    }

    @PostMapping("/sub")
    public Result<String> subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        // 调用服务层方法减少购物车数量
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success("减少成功");
    }

    @DeleteMapping("/clean")
    public Result<String> cleanShoppingCart() {
        // 调用服务层方法清空购物车
        shoppingCartService.cleanShoppingCart();
        return Result.success("清空成功");
    }
}
