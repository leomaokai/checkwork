package com.kai.check.utils.commit;

import com.kai.check.pojo.*;
import com.kai.check.service.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

//@Service
public class CommitPdf implements ICommit {

    @Resource
    private IStuWorkService stuWorkService;

    @Override
    public boolean commit(Integer id, MultipartFile file, String name) {
        StuWork stuWork = stuWorkService.getById(id);
        if (stuWork != null) {
            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            if (extension == null || !extension.equals("pdf")) {
                return false;
            }
            String workName = stuWork.getWorkName();
            String substring = workName.substring(workName.lastIndexOf('.') + 1);
            String pdfName = workName.replace(substring, "pdf");
            String workUrl = stuWork.getWorkUrl();
            String replace = (workUrl.substring(0, workUrl.lastIndexOf('/') + 1)).replace("code", "pdf");
            String pdfUrl = replace + pdfName;
            if (CommitUtils.commitWorkToFile(file, pdfUrl)) {
                return false;
            }
            stuWork.setPdfName(pdfName);
            stuWork.setPdfPath(pdfUrl);
//            ClassWork classWork = classWorkService.getOne(new QueryWrapper<ClassWork>().eq("class_id", stuWork.getClassId()).eq("workId", stuWork.getWorkId()));
//            LocalDateTime endTime = classWork.getEndTime();
            stuWork.setIsCommit("已提交");
            return stuWorkService.updateById(stuWork);
        }
        return false;
    }
}
