package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kai.check.mapper.StuDesignMapper;
import com.kai.check.mapper.StudentMapper;
import com.kai.check.pojo.StuDesign;
import com.kai.check.pojo.StuGroup;
import com.kai.check.mapper.StuGroupMapper;
import com.kai.check.service.IStuGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-20
 */
@Service
public class StuGroupServiceImpl extends ServiceImpl<StuGroupMapper, StuGroup> implements IStuGroupService {

    @Resource
    private StuGroupMapper stuGroupMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private StuDesignMapper stuDesignMapper;

    @Override
    @Transactional
    public RespBean createGroup(String[] studentIds, String name, Integer designId) {
        StuGroup stuGroup = new StuGroup();
        stuGroup.setStuId1(name);
        stuGroup.setDesignId(designId);
        Integer classId = studentMapper.getClassIdByStuId(name);
        stuGroup.setClassId(classId);
        studentMapper.setIsGroup(name);
        int len = studentIds.length;
        if (len == 1) {
            stuGroup.setStuId2(studentIds[0]);
            studentMapper.setIsGroup(studentIds[0]);
        } else if (len == 2) {
            stuGroup.setStuId2(studentIds[0]);
            stuGroup.setStuId3(studentIds[1]);
            studentMapper.setIsGroup(studentIds[0]);
            studentMapper.setIsGroup(studentIds[1]);
        } else if (len == 3) {
            stuGroup.setStuId2(studentIds[0]);
            stuGroup.setStuId3(studentIds[1]);
            stuGroup.setStuId4(studentIds[2]);
            studentMapper.setIsGroup(studentIds[0]);
            studentMapper.setIsGroup(studentIds[1]);
            studentMapper.setIsGroup(studentIds[2]);
        }
        if (stuGroupMapper.insert(stuGroup) == 1) {
            StuGroup group = stuGroupMapper.selectOne(new QueryWrapper<StuGroup>().eq("stu_id1", name));
            StuDesign stuDesign = new StuDesign();
            stuDesign.setDesignId(designId);
            stuDesign.setGroupId(group.getId());
            stuDesignMapper.insert(stuDesign);
            return RespBean.success(RespBeanEnum.GROUP_SUCCESS);
        }
        return RespBean.error(RespBeanEnum.GROUP_ERROR);
    }

    @Override
    public StuGroup selectGroupMembers(String name) {
        return stuGroupMapper.selectGroupByOneStudentId(name);
    }

    @Override
    public List<StuGroup> listStudentsGroups(Integer classId) {
        return stuGroupMapper.listStudentsGroups(classId);
    }

}
