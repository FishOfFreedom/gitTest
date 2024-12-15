package com.example;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileDownloader {

    public static void downloadFile(String fileUrl, String saveDir) {
        try {
            URL url = new URL(fileUrl);
            // 打开连接
            InputStream in = new BufferedInputStream(url.openStream());
            // 创建文件输出流，将文件保存到指定目录
            FileOutputStream fileOutputStream = new FileOutputStream(saveDir);

            int bufferSize = 4096; // 缓冲区大小
            byte[] buffer = new byte[bufferSize];
            int bytesRead;

            // 读取数据
            while ((bytesRead = in.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            // 关闭流
            in.close();
            fileOutputStream.close();
            System.out.println("File downloaded successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileUrl = "https://mediafilez.forgecdn.net/files/5958/422/torchesbecomesunlight-0.3.0.jar"; // 替换为你要下载的文件的URL
        String saveDir = "D:/torchesbecomesunlight-0.3.0.jar"; // 替换为你想要保存文件的路径
        downloadFile(fileUrl, saveDir);
    }
}