/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.client;

import bidiDemo.common.MessageService;
import bidiDemo.common.Message;
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
        TTransport transport = new TSocket("localhost", 9099);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        MessageService.Client client = new MessageService.Client(protocol);
        client.sendMessage(new Message("B","I exist here."));
        System.out.println("Message sent to server.");
        
        final MessageClient msgReceiver = new MessageClient(protocol);
        new Thread(msgReceiver).start();
    }
}
