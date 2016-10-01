/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiSimpleDemo.server;

import bidiSimpleDemo.server.MessageDistributor;
import bidiSimpleDemo.common.Message;
import bidiSimpleDemo.common.MessageService;
import bidiSimpleDemo.server.MessageDistributor;
import org.apache.thrift.TException;

/**
 *
 * @author rarora
 */
public class MessageServiceHandler implements MessageService.Iface{
    private MessageService.Client client;
    private MessageDistributor msgDistributor;
    
    public MessageServiceHandler(MessageService.Client client, MessageDistributor msgDistributor){
        this.client = client;
        this.msgDistributor = msgDistributor;
    }
    
    @Override
    public void sendMessage(Message msg) throws TException{
        System.out.println("---------------------");
        System.out.println("Message: " + msg.message);
        System.out.println("-Sent by " + msg.clientName);
        System.out.println("---------------------");
        
        msgDistributor.setMessage(client, msg);
    }
}
