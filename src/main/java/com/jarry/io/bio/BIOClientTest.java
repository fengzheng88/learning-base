package com.jarry.io.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 阻塞IO客户端
 */
public class BIOClientTest {
    public static void main(String[] args) throws Exception {
        //与服务器创建连接
        Socket socket = new Socket("127.0.0.1",9999);

        //创建输出流
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write("你好,我是客户端");
        //因为读取的是一行，所以必须换行，否则一直阻塞
        bw.newLine();
        bw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = br.readLine();
        System.out.println("接收到服务端信息:" + s);

        socket.close();

    }
}
