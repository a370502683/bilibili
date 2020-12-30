package chu;

import chu.bilibili.type.DownloadCollection;
import chu.bilibili.type.DownloadColumn;
import chu.bilibili.type.DownloadColumnPic;
import chu.bilibili.type.DownloadRelease;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter(new File("bilibili.txt"));

        System.out.println("请选择需要获取的类型");
        System.out.println("1.用户所有发布的视频BV号");
        System.out.println("2.用户收藏的视频BV号");
        System.out.println("3.用户发布的专栏链接");
        System.out.println("4.用户发布的专栏内的图片链接");

        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        if (num == 1) {
            System.out.println("输入用户ID");
            Scanner scan = new Scanner(System.in);
            String uid = scan.next();

            List<String> releaseList = DownloadRelease.start(uid);
            if (releaseList.size() > 0) {
                System.out.println("该用户总共发布了" + releaseList.size() + "个视频");
                for (String s : releaseList) {
//                    System.out.println(s);
                    fileWriter.write(s);
                    fileWriter.write("\n");
                    fileWriter.flush();
                }
            } else {
                System.out.println("该用户没有发布视频");
            }
        } else if (num == 2) {
            System.out.println("输入用户ID");
            Scanner scan = new Scanner(System.in);
            String uid = scan.next();

            List<String> collectionList = DownloadCollection.start(uid);
            for (String s : collectionList) {
//                System.out.println(s);
                fileWriter.write(s);
                fileWriter.write("\n");
                fileWriter.flush();
            }
        } else if (num == 3) {
            System.out.println("输入用户ID");
            Scanner scan = new Scanner(System.in);
            String uid = scan.next();

            List<String> column = DownloadColumn.start(uid);
            for (String s : column) {
//                System.out.println(s);
                fileWriter.write(s);
                fileWriter.write("\n");
                fileWriter.flush();
            }
        } else if (num == 4) {
            System.out.println("输入用户ID");
            Scanner scan = new Scanner(System.in);
            String uid = scan.next();

            List<String> columnPicList = DownloadColumnPic.start(uid);
            for (String s : columnPicList) {
//                System.out.println(s);
                fileWriter.write(s);
                fileWriter.write("\n");
                fileWriter.flush();
            }
        } else {
            System.out.println("输入错误");
        }

        fileWriter.close();
    }
}
