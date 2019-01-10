package org.nuaa.b730401.softwarereliability.service.impl;

import org.nuaa.b730401.softwarereliability.entity.Response;
import org.nuaa.b730401.softwarereliability.entity.UserEntity;
import org.nuaa.b730401.softwarereliability.repository.IUserRepository;
import org.nuaa.b730401.softwarereliability.service.IUserService;
import org.nuaa.b730401.softwarereliability.util.PasswordEncryptUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 16:51
 */
@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response register(UserEntity user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (userRepository.findUserEntityByName(user.getName()).isPresent()) {
            return new Response(Response.NORMAL_EROOR_CODE, "用户名或密码错误");
        }

        user.setPassword(PasswordEncryptUtil.getEncryptedPwd(user.getPassword()));
        userRepository.save(user);
        return new Response<UserEntity>(
                Response.SUCCESS_CODE,
                "注册用户成功"
        );
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Response login(UserEntity user, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserEntity result = userRepository.findUserEntityByName(user.getName()).orElseGet(() -> null);
        if (result == null) {
            return new Response(Response.NORMAL_EROOR_CODE, "用户名或密码错误");
        }

        if (PasswordEncryptUtil.validPassword(user.getPassword(), result.getPassword())) {
            result.setPassword("");
            userRepository.updateLoginCount(user.getId());
            session.setAttribute(String.valueOf(user.getId()), user);
            return new Response<UserEntity>(Response.SUCCESS_CODE, "登录成功", result);
        }
        return new Response(Response.NORMAL_EROOR_CODE, "用户名或密码错误");
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Response updateUserInfo(UserEntity user) {
        userRepository.updateUserInfo(user.getName(), user.getPhone(), user.getEmail(),
                user.getSex(), user.getId());
        return new Response(Response.SUCCESS_CODE, "更新用户信息成功");
    }

    @Override
    public UserEntity getUserinfo(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseGet(() -> null);
        return user;
    }
}
