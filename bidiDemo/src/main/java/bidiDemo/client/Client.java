/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.client;

import bidiDemo.common.MessageService;
import bidiDemo.common.Message;
import java.util.Scanner;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author rarora
 */
public class Client {
    private final MessageSender sender;
    private final MessageReceiver receiver;

    private final String name;
    private final String server;
    private int port;

    private final TTransport transport;
    private final TProtocol protocol;
    
    public Client(String server, int port, MessageService.Iface messageHandler) throws TTransportException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        name = sc.nextLine();
        this.port = port;
        this.server = server;
        
        this.transport = new TSocket(server, port);
        transport.open();
        this.protocol = new TBinaryProtocol(transport);

        this.sender = new MessageSender(this.protocol);
        this.receiver = new MessageReceiver(this.protocol, messageHandler);

        new Thread(sender).start();
        new Thread(receiver).start();
    }

    public void sendMessageToServer(String msg) {
        sender.send(new Message(name, msg));
    }
    
    public static void main(String[] args) throws TException{
        MessageService.Iface handler = new MessageService.Iface() {
            @Override
            public void sendMessage(Message msg) throws TException {
                System.out.println("---------------------");
                System.out.println("Got message from " + msg.clientName);
                System.out.println("Message: " + msg.message);
                System.out.println("---------------------");
            }
        };
        try{
            Client client = new Client("localhost", 9091, handler);
            client.sendMessageToServer("Hello! Are you there?");
        } catch(TTransportException te){
            System.out.println("Unable to run client!");
        }

        /*TTransport transport = new TSocket("localhost", 9091);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        MessageService.Client client = new MessageService.Client(protocol);
        client.sendMessage(new Message("B","I exist here."));
        System.out.println("Message sent to server.");
        
        MessageServiceHandler handler = new MessageServiceHandler();
        
        final MessageClient msgReceiver = new MessageClient(protocol, handler);
        new Thread(msgReceiver).start();*/
    }
}
