package com.github.guoyaohui.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * @author 郭垚辉
 * @date 2018/08/06
 */
public class DirectoryUtil {


    /**
     * 给定文件/目录，判断文件所在的文件/目录是否存在，如果不存在则创建该文件/目录
     *
     * @param file 文件/目录
     * @param type true 则file为文件， false则file为目录
     */
    public static File createDirectory(File file, boolean type) {
        if (file != null) {
            Path currentPath = file.toPath();
            if (type) {
                currentPath = currentPath.getParent();
            }
            List<Path> pathList = new ArrayList<>();
            // 首先判断当前文件夹是否存在
            while (Files.notExists(currentPath)) {
                pathList.add(currentPath);
                currentPath = currentPath.getParent();
            }

            try {
                if (!CollectionUtils.isEmpty(pathList)) {
                    for (int i = pathList.size() - 1; i >= 0; i--) {
                        Files.createDirectories(pathList.get(i));
                    }
                }
                if (type) {
                    Files.createFile(file.toPath());
                }
                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
