package org.nuaa.b730401.softwarereliability.controller;

import org.nuaa.b730401.softwarereliability.entity.Response;
import org.nuaa.b730401.softwarereliability.entity.UserEntity;
import org.nuaa.b730401.softwarereliability.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 19:16
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Response login(UserEntity user, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.login(user, session);
    }

    @PostMapping("/register")
    public Response register(UserEntity user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.register(user);
    }

    @PutMapping
    public Response updateUserInfo(UserEntity user) {
        return userService.updateUserInfo(user);
    }

    @GetMapping("/user/{id}")
    public Response getUserInfo(@PathVariable(name = "id") Long id) {
        return new Response(
                Response.SUCCESS_CODE,
                "获取用户信息成功",
                userService.getUserinfo(id)
        );
    }
}
