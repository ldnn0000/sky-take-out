package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    /**
     * 设置店铺状态
     * @param status 店铺状态
     * @return
     */
    @PutMapping("/{status}")
    public Result shopStatus(@PathVariable Integer status){
        log.info("设置店铺状态为:{}",status);
        //设置店铺状态
        redisTemplate.opsForValue().set("SHOP_STATUS",status == 1 ?"营业中":"休息中");
        return Result.success();
    }
    /**
     * 获取店铺状态
     * @return 店铺状态
     */
    @GetMapping("/status")
    public Result<Integer> shopStatus(){
        String status = (String) redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(status.equals("营业中")?1:0);
    }
}
