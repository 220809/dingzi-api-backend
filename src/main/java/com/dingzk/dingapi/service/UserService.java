package com.dingzk.dingapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dingzk.dingapi.model.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
* @author ding
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-10-15 12:54:52
*/
public interface UserService extends IService<User> {
    /**
     * 注册用户
     * @param userDo 用户创建信息:账号密码
     * @return 插入用户ID
     */
    long userRegister(User userDo);

    /**
     * 用户登录
     * @param userDo 用户登录数据
     * @return 用户ID
     */
    long userLogin(User userDo);

    /**
     * 修改用户
     * @param user 修改内容
     * @return 修改成功?
     */
    boolean updateUser(User user);

    /**
     * 保存用户登录态
     * @param loginUser 当前登录用户
     */
    default void saveUserLoginState(User loginUser) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        if (loginUser != null) {
            session.setAttribute("login_user", loginUser);
            return;
        }
        session.removeAttribute("login_user");
    }

    /**
     * 获取当前登录用户
     * @return 当前登录用户
     */
    default User getLoginUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        return (User) session.getAttribute("login_user");
    }
}