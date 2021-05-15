package com.kai.check.utils.commit;

import org.springframework.web.multipart.MultipartFile;

public interface ICommit {
    boolean commit(Integer id, MultipartFile file,String name);
}
