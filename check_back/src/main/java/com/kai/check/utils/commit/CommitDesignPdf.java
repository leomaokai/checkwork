package com.kai.check.utils.commit;

import com.kai.check.service.IDesignResultService;
import com.kai.check.service.IStuDesignService;
import com.kai.check.service.IStuGroupService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

public class CommitDesignPdf implements ICommit{

    @Resource
    private IStuDesignService stuDesignService;
    @Resource
    private IStuGroupService stuGroupService;
    @Resource
    private IDesignResultService designResultService;
    @Override
    public int commit(Integer id, MultipartFile file, String name) {
        return 0;
    }
}
