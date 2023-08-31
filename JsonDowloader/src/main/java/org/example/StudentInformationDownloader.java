package org.example;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
    public class StudentInformationDownloader {
        public static void downloadImage(String imageUrl, String saveFilePath) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (InputStream inputStream = conn.getInputStream();
                         FileOutputStream outputStream = new FileOutputStream(saveFilePath)) {

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        System.out.println("已下载并保存到: " + saveFilePath);
                    }
                } else {
                    System.out.println("下载图片时出现错误。HTTP响应代码: " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("下载图片出现异常: " + e.getMessage());
            }
        }
        public static String downloadWebPageContent(String url) {
            try {
                // 创建URL对象
                URL webpageURL = new URL(url);

                // 打开URL连接
                HttpURLConnection connection = (HttpURLConnection) webpageURL.openConnection();

                // 设置请求方式
                connection.setRequestMethod("GET");

                // 获取输入流
                InputStream inputStream = connection.getInputStream();

                // 创建一个BufferedReader来读取输入流中的数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder contentBuilder = new StringBuilder();
                String line;

                // 逐行读取网页内容并追加到StringBuilder中
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }

                // 关闭输入流和BufferedReader
                reader.close();
                inputStream.close();

                // 将StringBuilder转换为字符串并返回
                return contentBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        static class Thread1 implements Runnable{
            private Integer id;
            Thread1( Integer id){
                this.id=id;

            }
            @Override
            public void run() {
                while (true){
                    String imageUrl = "http://yx.hbmzu.edu.cn:6521/api/zhxg-yxwz/openapi/student/getStudentPhoto?xsid="+id.toString()+"&type=10&uuid=7da443fa-02f3-406a-9d76-467294c77ec2";


                    String url = "http://yx.hbmzu.edu.cn:6521/api/zhxg-yxwz/openapi/student/"+id.toString()+"/getUserInfoByXsid"; // 要下载的网页URL
                    String webpageContent = downloadWebPageContent(url);

                    if (webpageContent != null) {
                        System.out.println("下载成功！");
                    } else {
                        System.out.println("无法下载。");
                    }

                    // 将JSON字符串解析为JSONObject




                    try{

                        JSONObject jsonObject = new JSONObject(webpageContent);
                        // 读取"data"字段下的"XM"字段（姓名）
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        String name = dataObject.getString("XM");
                        // 读取"data"字段下的"ZYMC"字段（专业名称）
                        String major = dataObject.getString("ZYMC");
                        String studentId = dataObject.getString("XSID");
                        String idCard = dataObject.getString("SFZJH");

                        String savePath = "C:\\Users\\knifefire\\Desktop\\民院学生信息\\身份信息\\data.txt";
                        try {
                            // 创建File对象
                            File outputFile = new File(savePath);

                            // 创建PrintWriter对象，用于写入文件
                            PrintWriter writer = new PrintWriter(new FileWriter(outputFile, true));

                            // 写入输出内容到文件
                            writer.print(name);
                            writer.print(" ");
                            writer.print(major);
                            writer.print(" ");
                            writer.print(studentId);
                            writer.print(" ");
                            writer.println(idCard);

                            // 关闭PrintWriter
                            writer.close();

                            System.out.println("输出已保存到文件：" + savePath);

                            String saveFilePath = "C:\\Users\\knifefire\\Desktop\\民院学生信息\\证件照\\"+name+".jpg";

                            // 调用函数下载图片
                            downloadImage(imageUrl, saveFilePath);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        id++;

                    }catch (JSONException e){
                        continue;



                    }

                }
            }
        }
        static class Thread2 implements Runnable{
            private Integer id;
            Thread2( Integer id){
                this.id=id;

            }
            @Override
            public void run() {
                while (true){
                    String imageUrl = "http://yx.hbmzu.edu.cn:6521/api/zhxg-yxwz/openapi/student/getStudentPhoto?xsid="+id.toString()+"&type=10&uuid=7da443fa-02f3-406a-9d76-467294c77ec2";


                    String url = "http://yx.hbmzu.edu.cn:6521/api/zhxg-yxwz/openapi/student/"+id.toString()+"/getUserInfoByXsid"; // 要下载的网页URL
                    String webpageContent = downloadWebPageContent(url);

                    if (webpageContent != null) {
                        System.out.println("下载成功！");
                    } else {
                        System.out.println("无法下载。");
                    }

                    // 将JSON字符串解析为JSONObject
                    JSONObject jsonObject = new JSONObject(webpageContent);

/*
            // 读取"code"字段
            int code = jsonObject.getInt("code");
            System.out.println("Code: " + code);
*/

                    // 读取"data"字段下的"XM"字段（姓名）
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    String name = dataObject.getString("XM");
                    // 读取"data"字段下的"ZYMC"字段（专业名称）
                    String major = dataObject.getString("ZYMC");
                    String studentId = dataObject.getString("XSID");
                    String idCard = dataObject.getString("SFZJH");
                    String savePath = "C:\\Users\\knifefire\\Desktop\\民院学生信息\\身份信息\\data.txt";
                    try {
                        // 创建File对象
                        File outputFile = new File(savePath);

                        // 创建PrintWriter对象，用于写入文件
                        PrintWriter writer = new PrintWriter(new FileWriter(outputFile, true));

                        // 写入输出内容到文件
                        writer.print(name);
                        writer.print(" ");
                        writer.print(major);
                        writer.print(" ");
                        writer.print(studentId);
                        writer.print(" ");
                        writer.println(idCard);

                        // 关闭PrintWriter
                        writer.close();

                        System.out.println("输出已保存到文件：" + savePath);

                        String saveFilePath = "C:\\Users\\knifefire\\Desktop\\民院学生信息\\证件照\\"+name+".jpg";

                        // 调用函数下载图片
                        downloadImage(imageUrl, saveFilePath);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    id--;
                }
            }
        }
        public static void main(String[] args) {
           Integer id = 202211165;
           Thread1 mr1 = new Thread1(id);
           Thread2 mr2 = new Thread2(id);
           Thread t1 = new Thread(mr1);
           Thread t2 = new Thread(mr2);
           t1.start();
           t2.start();


        }
    }

