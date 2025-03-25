package com.sky.controller.user;

import com.fasterxml.jackson.core.util.InternCache;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserService userService;
    /**
     * 微信登陆
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信用户登陆：{}",userLoginDTO.getCode());
        //微信登陆
        User user = userService.wxLogin(userLoginDTO);

        //为微信用户生成JWT令牌
        Map cliams = new HashMap<String, Integer>();
        cliams.put(JwtClaimsConstant.USER_ID,user.getId());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), cliams);
        UserLoginVO userLoginVO = UserLoginVO.builder().token(jwt).id(user.getId()).openid(user.getOpenid()).build();
        return Result.success(userLoginVO);
    }
}
