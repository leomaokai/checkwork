package com.kai.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kai.check.pojo.Menus;
import com.kai.check.pojo.User;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespPageBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
public interface IUserService extends IService<User> {

    User getUserByUsername(String username);

    RespBean login(String username, String password, String code, HttpServletRequest request);

    RespBean updatePassword(String oldPassword, String newPassword, String name);

    RespPageBean getStudentByPage(Integer currentPage, Integer size, String studentId);

    RespPageBean getTeacherByPage(Integer currentPage, Integer size, String teacherId);

    RespBean updateUser(User user);

    Boolean insertUser(Integer roleId, String[] usernames);


    RespBean initStuById(String studentId);

    RespBean deleteTeacher(String id);

    List<Menus> getMenus(String name);

    RespPageBean listUsersByPage(Integer currentPage, Integer size, String userId);

    boolean insertOneUser(Integer roleId, String teachersId);

    RespBean initUserPassword(String username);
}
