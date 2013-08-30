package sample;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<File> scanFile(File root) {
        List<File> fileInfo = new ArrayList<File>();

        File[] files = root.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.isDirectory() && pathname.isHidden()) { // 去掉隐藏文件夹
                    return false;
                }

                if (pathname.isFile() && pathname.isHidden()) {// 去掉隐藏文件
                    return false;
                }
                return true;
            }
        });

        for (File file : files) {// 逐个遍历文件
            if (file.isDirectory()) { // 如果是文件夹，则进一步遍历，属于递归算法
                List<File> ff = scanFile(file);
                fileInfo.addAll(ff);
            } else {
                fileInfo.add(file); // 如果不是文件夹，则添加入文件列表
            }
        }

        return fileInfo;
    }

    public static void main(String[] args) {
        File root = new File("E:/rings/data");
        List<File> files = scanFile(root);
        
        System.out.println("最终文件数量为： " + files.size());
        for (File f : files) {
            System.out.println(f.getAbsolutePath());
        }
    }
}