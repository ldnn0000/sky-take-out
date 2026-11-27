package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /** 
     * 添加购物车
     */
    void addShoppingCart(ShoppingCartDTO shoppingDTO);

    List<ShoppingCart> listShoppingCart();

    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);

    void cleanShoppingCart();
}
