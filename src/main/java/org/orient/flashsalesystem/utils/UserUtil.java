package org.orient.flashsalesystem.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.orient.flashsalesystem.pojo.User;
import org.orient.flashsalesystem.vo.RespBean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class UserUtil {

    private static void createUser(int count) throws Exception {
        List<User> users = new ArrayList<User>();
        for (int i = 5; i < count; i++) {
            User user = new User();
            user.setId(13000_00000_0L + i);
            user.setNickname("username" + i);
            user.setSalt("a12s3df1");
            user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSalt()));
            users.add(user);
        }
        System.out.println("create user!");
        // 插入数据库
        Connection conn = getConn();
        String sql = "insert into t_user(id, nickname, password, salt) values(?,?,?,?)";
        PreparedStatement prepareStatement = conn.prepareStatement(sql);
        for (User user : users) {
            prepareStatement.setLong(1, user.getId());
            prepareStatement.setString(2, user.getNickname());
            prepareStatement.setString(4, user.getSalt());
            prepareStatement.setString(3, user.getPassword());
            prepareStatement.addBatch();
        }
        prepareStatement.executeBatch();
        prepareStatement.clearParameters();
        prepareStatement.close();
        conn.close();
        System.out.println("插入完成!");
        // 登录 获取userticket
        String url = "http://localhost:8080/login/doLogin";
        File file = new File("C:\\Users\\Administrator\\Desktop\\config.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url1 = new URL(url);
            HttpURLConnection co = (HttpURLConnection) url1.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFormPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream in = co.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) >= 0) {
                baos.write(buffer, 0, len);
            }
            in.close();
            baos.close();
            String response = baos.toString();
            ObjectMapper om = new ObjectMapper();
            RespBean respBean = om.readValue(response, RespBean.class);
            String userTicket = (String) respBean.getObj();
            System.out.println("create userTicket + " + userTicket);
            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("wirte to file:" + user.getId());

        }
        raf.close();
        System.out.println("over!");

    }

    private static Connection getConn() throws Exception {
        String url = "jdbc:mysql://localhost:3306/flash_sale?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String driver = "com.mysql.cj.jdbc.Driver";
        String username = "root";
        String password = "woaihbdr!";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }
}
