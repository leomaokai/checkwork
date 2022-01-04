package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.config.security.component.JwtTokenUtil;
import com.kai.check.mapper.*;
import com.kai.check.pojo.*;
import com.kai.check.service.IUserService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import com.kai.check.utils.SecretUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private ClassTeaMapper classTeaMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private StuWorkMapper stuWorkMapper;
    @Resource
    private ClassWorkMapper classWorkMapper;
    @Resource
    private WorkResultMapper workResultMapper;
    @Resource
    private TeaWorkMapper teaWorkMapper;
    @Resource
    private MenusMapper menusMapper;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${kai.resource}")
    private String resource;
    @Value("${kai.result}")
    private String result;

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {

        //验证码校验
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (captcha == null || code == null || code.isEmpty() || !captcha.equalsIgnoreCase(code)) {
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }
        // 登录
        // 解密
        password = SecretUtil.desEncrypt(password);
        if (password == null) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        // 更新登录对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println("authenticationToken==>"+authenticationToken);
        // 得到token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> map = new HashMap<>();
        User user = this.getUserByUsername(username);
        Integer roleId = user.getUserRoleId();
        map.put("roleId", roleId);
        map.put("token", token);
        map.put("tokenHead", tokenHead);

        return RespBean.success(RespBeanEnum.LOGIN_SUCCESS, map);
    }

    @Override
    @Transactional
    public RespBean updatePassword(String oldPassword, String newPassword, String name) {
        // 解密
        oldPassword = SecretUtil.desEncrypt(oldPassword);
        newPassword = SecretUtil.desEncrypt(newPassword);
        User user = this.getUserByUsername(name);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
            if (userMapper.updateById(user) == 1) {
                return RespBean.success(RespBeanEnum.UPDATE_PWD_SUCCESS);
            }
        }
        return RespBean.error(RespBeanEnum.UPDATE_PWD_ERROR);
    }

    @Override
    public RespPageBean getStudentByPage(Integer currentPage, Integer size, String studentId) {
        Page<User> page = new Page<>(currentPage, size);
        IPage<User> studentByPage = userMapper.getStudentByPage(page, studentId);
        return new RespPageBean(studentByPage.getTotal(), studentByPage.getRecords());
    }

    @Override
    public RespPageBean getTeacherByPage(Integer currentPage, Integer size, String teacherId) {
        Page<User> page = new Page<>(currentPage, size);
        IPage<User> teacherByPage = userMapper.getTeacherByPage(page, teacherId);
        return new RespPageBean(teacherByPage.getTotal(), teacherByPage.getRecords());
    }

    @Override
    public RespBean updateUser(User user) {
        if (!(user.getPassword().isEmpty()))
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userMapper.updateById(user) == 1) {
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.FAIL);
    }


    /**
     * 默认添加用户
     * 存在则跳过
     *
     * @param roleId
     * @param usernames
     * @return
     */
    @Override
    @Transactional
    public Boolean insertUser(Integer roleId, String[] usernames) {
        for (String username : usernames) {
            User user1 = userMapper.selectById(username);
            if (user1 != null) {
                continue;
            }
            User user = new User();
            user.setUsername(username);
            user.setUserRoleId(roleId);
            user.setPassword(passwordEncoder.encode(username));
            userMapper.insert(user);
            if (roleId == 2) {
                Teacher teacher = new Teacher();
                teacher.setTeaId(username);
                teacherMapper.insert(teacher);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public RespBean initStuById(String studentId) {
        User user = userMapper.selectById(studentId);
        if (user.getUserRoleId() != 3) {
            return RespBean.error(RespBeanEnum.INIT_ERROR);
        }
        user.setPassword(passwordEncoder.encode(user.getUsername()));
        if (userMapper.updateById(user) == 1) {
            return RespBean.success(RespBeanEnum.INIT_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.INIT_ERROR);
    }

    @Override
    @Transactional
    public RespBean initUserPassword(String username) {
        User user = userMapper.selectById(username);
        user.setPassword(passwordEncoder.encode(user.getUsername()));
        if (userMapper.updateById(user) == 1) {
            return RespBean.success(RespBeanEnum.INIT_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.INIT_ERROR);
    }

    @Override
    @Transactional
    public RespBean deleteTeacher(String id) {
        File file = new File(resource, id);
        if (file.exists()) {
            file.delete();
        }
        File file1 = new File(result, id);
        if (file1.exists()) {
            file1.delete();
        }
        List<ClassTea> classTeas = classTeaMapper.selectList(new QueryWrapper<ClassTea>().eq("tea_id", id));
        for (ClassTea classTea : classTeas) {
            Integer classId = classTea.getId();
            List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().eq("stu_class_id", classId));
            for (Student student : students) {
                String stuId = student.getStuId();
                stuWorkMapper.deleteByStuId(stuId);
                studentMapper.deleteById(stuId);
                userMapper.deleteById(stuId);
            }
            classTeaMapper.deleteById(classId);
        }
        classWorkMapper.delete(new QueryWrapper<ClassWork>().eq("tea_id",id));
        List<TeaWork> teaWorks = teaWorkMapper.selectList(new QueryWrapper<TeaWork>().eq("tea_id", id));
        for (TeaWork teaWork : teaWorks) {
            Integer workId = teaWork.getWorkId();
            workResultMapper.deleteByWorkId(workId);
        }
        teaWorkMapper.delete(new QueryWrapper<TeaWork>().eq("tea_id",id));
        teacherMapper.deleteById(id);
        if (userMapper.deleteById(id) == 1) {
            return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.DELETE_ERROR);
    }

    @Override
    public List<Menus> getMenus(String name) {
        return menusMapper.getMenus(name);
    }

    @Override
    public RespPageBean listUsersByPage(Integer currentPage, Integer size, String userId) {
        Page<User> userPage = new Page<>(currentPage, size);
        IPage<User> res = userMapper.listUsersByPage(userPage, userId);
        return new RespPageBean(res.getTotal(), res.getRecords());
    }

    @Override
    public boolean insertOneUser(Integer roleId, String teachersId) {
        User user1 = userMapper.selectById(teachersId);
        if (user1 != null) {
            return false;
        }
        User user = new User();
        user.setUsername(teachersId);
        user.setUserRoleId(roleId);
        user.setPassword(passwordEncoder.encode(teachersId));
        userMapper.insert(user);
        if (roleId == 2) {
            Teacher teacher = new Teacher();
            teacher.setTeaId(teachersId);
            if (teacherMapper.insert(teacher) == 1) {
                return true;
            }
        }
        return false;
    }
}
