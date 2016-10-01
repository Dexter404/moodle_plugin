/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.client;

import bidiDemo.common.Message;
import bidiDemo.common.MessageService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 *
 * @author rarora
 */
public class MessageSender implements Runnable{
    private final MessageService.Client client;
    private final BlockingQueue<Message> msgSendQueue;

    MessageSender(TProtocol protocol) {
        this.client = new MessageService.Client(protocol);
        this.msgSendQueue = new LinkedBlockingQueue<Message>(); 
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message msg = msgSendQueue.take();
                try {
                    client.sendMessage(msg);
                } catch (TException e) {
                    // The message isn't lost, but it could end up being sent 
                    // out of order - not ideal.
                    msgSendQueue.add(msg);
                    System.out.println("I am caught, message added in the queue again.");
                }
            } catch (InterruptedException ex) {
                // Thread will be interrupted if connection is lost, we should wait
                // for reconnection if that happens.
                Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null,ex);
            }
        }
    }

    void send(Message msg) {
        msgSendQueue.add(msg);
    }
    
}
