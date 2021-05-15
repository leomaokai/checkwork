package com.kai.check.utils.commit;


import com.kai.check.service.IDesignResultService;
import com.kai.check.service.IStuDesignService;
import com.kai.check.service.IStuGroupService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

public class CommitDesignCode implements ICommit{

    @Resource
    private IStuDesignService stuDesignService;
    @Resource
    private IStuGroupService stuGroupService;
    @Resource
    private IDesignResultService designResultService;

    @Override
    @Transactional
    public boolean commit(Integer id, MultipartFile file, String name) {

        return false;
    }
}
