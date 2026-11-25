package com.sky.controller.user;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
public class ShopController {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
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
