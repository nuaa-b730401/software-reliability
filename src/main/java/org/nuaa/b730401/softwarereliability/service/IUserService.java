package org.nuaa.b730401.softwarereliability.service;

import org.nuaa.b730401.softwarereliability.entity.Response;
import org.nuaa.b730401.softwarereliability.entity.UserEntity;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 16:45
 */
public interface IUserService {
    /**
     * 注册
     * @param user
     * @return
     */
    Response register(UserEntity user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 登录
     * @param user
     * @param session
     * @return
     */
    Response login(UserEntity user, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Response updateUserInfo(UserEntity user);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserEntity getUserinfo(Long userId);
}
