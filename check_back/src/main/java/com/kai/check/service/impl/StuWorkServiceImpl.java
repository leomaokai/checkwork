package com.kai.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kai.check.mapper.StuWorkMapper;
import com.kai.check.mapper.WorkClassMapper;
import com.kai.check.pojo.StuWork;
import com.kai.check.pojo.WorkClass;
import com.kai.check.service.IStuWorkService;
import com.kai.check.utils.RespBean;
import com.kai.check.utils.RespBeanEnum;
import com.kai.check.utils.RespPageBean;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
@Service
public class StuWorkServiceImpl extends ServiceImpl<StuWorkMapper, StuWork> implements IStuWorkService {

    @Resource
    private StuWorkMapper stuWorkMapper;
    @Resource
    private WorkClassMapper workClassMapper;

    @Override
    @Transactional
    public List<StuWork> listWorks(String name) {
        List<StuWork> stuWorks = stuWorkMapper.selectList(new QueryWrapper<StuWork>().eq("stu_id", name));
        for (StuWork stuWork : stuWorks) {
            Integer workId = stuWork.getWorkId();
            if (stuWork.getIsCommit().equals("未提交")) {
                stuWork.setWorkName("未提交");
                stuWork.setWorkUrl("null");
            }
            WorkClass workClass = workClassMapper.selectOne(new QueryWrapper<WorkClass>().eq("id", workId));
            //stuWork.setWorkClass(workClass);
        }
        return stuWorks;
    }

    @Override
    @Transactional
    public RespBean deleteWork(Integer stuWorkId) {
        StuWork stuWork = stuWorkMapper.selectOne(new QueryWrapper<StuWork>().eq("id", stuWorkId));
        stuWork.setIsCommit("未提交");
        if (stuWorkMapper.updateById(stuWork) == 1) {
            return RespBean.success();
        }
        return RespBean.error();
    }

    @Override
    public RespBean downWork(Integer stuWorkId, Integer isDown, HttpServletResponse response) {
        StuWork stuWork = stuWorkMapper.selectById(stuWorkId);
        if (stuWork.getIsCommit().equals("未提交")) {
            return RespBean.error(RespBeanEnum.COMMIT_NOT);
        }
        String workUrl = stuWork.getWorkUrl();
        String openStyle = "attachment";
        if (isDown == 0) {
            // isDown =0 在线打开
            openStyle = "inline";
        }
        try (
                FileInputStream inputStream = new FileInputStream(new File(workUrl));
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            response.setHeader("content-disposition", openStyle + ";fileName" + URLEncoder.encode(stuWork.getWorkName(), "UTF-8"));
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespBean.success();
    }

    @Override
    public RespPageBean getClassStudentWorks(Integer currentPage, Integer size, Integer classId, Integer workId) {

        Page<StuWork> stuWorkPage = new Page<>(currentPage, size);
        IPage<StuWork> res=stuWorkMapper.getClassStudentWorks(stuWorkPage,classId,workId);
        return new RespPageBean(res.getTotal(),res.getRecords());
    }
}
