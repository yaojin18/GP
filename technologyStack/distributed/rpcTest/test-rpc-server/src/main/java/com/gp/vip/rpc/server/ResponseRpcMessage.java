package com.gp.vip.rpc.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import com.gp.vip.rpc.pojo.RequestMessageDto;
import com.gp.vip.rpc.server.impl.StudentServiceImpl;

public class ResponseRpcMessage {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8080);
        StudentServiceImpl service = new StudentServiceImpl();

        Socket socket = serverSocket.accept();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        RequestMessageDto request = (RequestMessageDto)ois.readObject();
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

        // 通过反射的方式调用方法

        Class clazz = Class.forName(request.getClassName());
        Method method = clazz.getMethod(request.getMethodName(), request.getParamType());
        Object result = method.invoke(service, request.getArgs());

        os.writeObject(result);

    }

}
