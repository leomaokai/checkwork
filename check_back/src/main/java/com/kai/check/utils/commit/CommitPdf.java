package com.kai.check.utils.commit;

import com.kai.check.service.IStuWorkService;
import com.kai.check.service.IWorkResultService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

public class CommitPdf implements ICommit{

    @Resource
    private IStuWorkService stuWorkService;
    @Resource
    private IWorkResultService workResultService;
    @Override
    public int commit(Integer id, MultipartFile file, String name) {
        return 0;
    }
}
