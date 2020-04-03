package com.jarry.io.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞IO服务端
 */
public class BIOServerTest {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        //循环获取连接
        while (true) {
            //阻塞等待连接
            Socket accept = serverSocket.accept();
            //创建输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            //读取一行信息
            String s = br.readLine();
            System.out.println("接收到客户端发送的信息:" + s);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
            bw.write("hello, 我是服务端");

            bw.close();
            accept.close();
        }

    }
}
