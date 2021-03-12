package com.kai.check.utils;

import jplag.ExitException;
import jplag.Program;
import jplag.options.CommandLineOptions;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckCode {

    @Value("${kai.sim}")
    private static String sim;

    public static void deleteWorkFileByPath(String workFilePath) {
        if (workFilePath != null && !workFilePath.equals("")) {
            File file = new File(workFilePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private static void cleanFiles(File resDir) {
        if (resDir.listFiles() != null) {
            File[] files = resDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    public static String check(String resourcePath, String resultPath, String workName1, String workName2, String lang) {
        // 如果已经查重过(结果文件夹有文件),将结果清除重新查重
        File resDir = new File(resultPath);
        cleanFiles(resDir);
        try {
            // 查重
            List<String> args = new ArrayList<>();
            // 指定语言
            args.add("-l");
            // java只能检测java9以后版本
            if ("java".equals(lang)) {
                args.add("java19");
            } else if ("cpp".equals(lang) || "c".equals(lang)) {
                args.add("c/c++");
            } else if ("py".equals(lang)) {
                args.add("python3");
            } else {
                args.add(lang);
            }
            args.add("-r");
            args.add(resultPath);
//             设置相似度检查门限参数值
            args.add("-m");
            args.add("0" + "%");
//             指定源文件存放路径
            args.add("-s");
            args.add(resourcePath);
            args.add("-c");
            args.add(workName1);
            args.add(workName2);
            String[] toPass = new String[args.size()];
            toPass = args.toArray(toPass);
            new Program(new CommandLineOptions(toPass)).run();
        } catch (ExitException e) {
            // 异常 说明源码中含有中文
            return "-1";
        }
        return getCheckResult(resultPath);
    }

    private static String getCheckResult(String resultPath) {
        String result = "-1";
        File resultFile = new File(resultPath, "matches_avg.csv");
        if (!resultFile.exists()) {
            return result;
        }
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(resultFile));) {
            if (resultFile.length() == 0) {
                return "0";
            }
            byte[] bytes = new byte[(int) resultFile.length()];
            bufferedInputStream.read(bytes);
            StringBuilder resString = new StringBuilder(new String(bytes));
            char c = ';';
            Integer[] integers = new Integer[4];
            int index = 0;
            int length = resString.length();
            for (int i = 0; i < length; i++) {
                if (c == resString.charAt(i)) {
                    integers[index++] = i;
                }
            }
            result = resString.substring(integers[2] + 1, integers[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
