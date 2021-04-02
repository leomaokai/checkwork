package com.kai.check.utils.commit;

import java.util.HashMap;
import java.util.Map;

public class CommitFactory {

//    private static final Map<Integer, ICommit> commits = new HashMap<>();
//
//    static {
//        commits.put(1, new CommitCode());
//        commits.put(2, new CommitPdf());
//        commits.put(3,new CommitDesignCode());
//        commits.put(4,new CommitDesignPdf());
//    }

    public static ICommit getCommit(Integer type) {
        if (type == null) {
            return null;
        }
        if (type == 1) {
            return new CommitCode();
        } else if (type == 2) {
            return new CommitPdf();
        } else if (type == 3) {
            return new CommitDesignCode();
        } else if (type == 4) {
            return new CommitDesignPdf();
        }
        return null;
    }
}
