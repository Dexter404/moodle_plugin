/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.server;

import bidiDemo.common.Message;
import bidiDemo.common.MessageService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author rarora
 */
public class MessageServiceClient {
    protected final TTransport transport;
    protected final String address;
    protected final int port;
    protected final MessageService.Client client;
    protected Message message;
    
    public MessageServiceClient(TTransport trans){
        transport = trans;
        TSocket tsocket = (TSocket)transport;
        client = new MessageService.Client(new TBinaryProtocol(transport));
        address = tsocket.getSocket().getInetAddress().getHostAddress();
        port = tsocket.getSocket().getPort();
    }
    
    public void sendMessage(Message msg) throws TException {
        client.sendMessage(msg);
    }
    
    public Message getMessage(){
        return message;
    }
    
    public void setMessage(Message msg){
        message = msg;
    }
}
