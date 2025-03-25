package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {
    private static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 设置营业状态
     */
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置状态为：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    /**
     * 获取店铺营业状态
     * @return
     */
    @GetMapping("/status")
    public Result<Integer> getStatus(){
        Integer stauts =  (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺营业状态为 : {}" , stauts == null ? null : stauts == 1 ? "营业中" : "打烊中");
        return Result.success(stauts);
    }
}
