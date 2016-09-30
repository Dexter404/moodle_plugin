/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.server;

import bidiDemo.common.Message;
import bidiDemo.common.MessageService;
import org.apache.thrift.TException;

/**
 *
 * @author rarora
 */
public class MessageServiceHandler implements MessageService.Iface{
    private MessageService.Client client;
    private MessageServer msgServer;
    
    public MessageServiceHandler(MessageService.Client cl, MessageServer mserver){
        client = cl;
        msgServer = mserver;
    }
    
    @Override
    public void sendMessage(Message msg) throws TException{
        System.out.println("---------------------");
        System.out.println("Message: " + msg.message);
        System.out.println("-Sent by " + msg.clientName);
        System.out.println("---------------------");
        
        //msgServer.setMessage(client, msg);
    }
}
