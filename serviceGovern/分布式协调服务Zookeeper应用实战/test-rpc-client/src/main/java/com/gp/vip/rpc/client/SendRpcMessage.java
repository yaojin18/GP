package com.gp.vip.rpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.gp.vip.rpc.pojo.RequestMessageDto;

public class SendRpcMessage {

    private String ip;

    private int port;

    private RequestMessageDto request;

    public SendRpcMessage(String ip, int port, RequestMessageDto request) {
        this.ip = ip;
        this.port = port;
        this.request = request;
    }

    public Object sendMessage() throws Exception {

        Socket socket = new Socket(ip, port);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(request);

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Object result = ois.readObject();

        socket.close();
        return result;
    }
}
