/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiSimpleDemo.client;

import bidiSimpleDemo.common.MessageService;
import bidiSimpleDemo.common.Message;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author rarora
 */
public class Client {
    public static void main(String[] args) throws TException{
        TTransport transport = new TSocket("localhost", 9095);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        MessageService.Client client = new MessageService.Client(protocol);
        client.sendMessage(new Message("B","Hello there."));
        System.out.println("Message sent to server.");
        
        MessageService.Iface handler = new MessageService.Iface() {
            @Override
            public void sendMessage(Message msg) throws TException {
                System.out.println("---------------------");
                System.out.println("Got message from " + msg.clientName);
                System.out.println(msg.message);
                System.out.println("---------------------");
            }
        };
        
        final MessageListener msgListener = new MessageListener(protocol, handler);
        new Thread(msgListener).start();
    }
}
