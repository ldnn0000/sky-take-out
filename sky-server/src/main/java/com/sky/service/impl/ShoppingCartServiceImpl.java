package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);   
        //如果查询到有购物车数据，数量加一
        if(!list.isEmpty()){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberByID(cart);
        }else {
            //判断插入的是菜品还是套餐
            if(shoppingCart.getDishId() != null){
                //菜品
                //查询菜品信息（名称、图片、金额）
                Dish dish = dishMapper.selectById(shoppingCart.getDishId());
                if(dish != null) {
                    shoppingCart.setName(dish.getName());
                    shoppingCart.setImage(dish.getImage());
                    shoppingCart.setAmount(dish.getPrice());
                }
            }else {
                //套餐
                //查询套餐信息（名称、图片、金额）
                SetmealVO setmeal = setmealMapper.selectById(shoppingCart.getSetmealId());
                if(setmeal != null){
                    shoppingCart.setName(setmeal.getName());
                    shoppingCart.setImage(setmeal.getImage());
                    shoppingCart.setAmount(setmeal.getPrice());
                    
                }
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
        
    }
    @Override
    public List<ShoppingCart> listShoppingCart() {
        //根据用户id查询购物车列表
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        return shoppingCartMapper.list(shoppingCart);
    }
    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //根据id查询购物车数据
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if(!list.isEmpty()){
            ShoppingCart cart = list.get(0);
            if(cart.getNumber() > 1){
                //数量减一
                cart.setNumber(cart.getNumber() - 1);
                shoppingCartMapper.updateNumberByID(cart);
            }else {
                //删除购物车数据
                shoppingCartMapper.deleteCart(cart);
            }
        }
    }
    @Override
    public void cleanShoppingCart() {
        //根据用户id删除购物车数据
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCartMapper.deleteCart(shoppingCart);
    }
}
