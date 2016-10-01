/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.client;

import bidiDemo.common.MessageService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 *
 * @author rarora
 */
public class MessageReceiver implements Runnable {
    private final MessageService.Processor processor;
    private final TProtocol protocol;
  
    public MessageReceiver(TProtocol protocol, MessageService.Iface messageService) {
        this.protocol = protocol;
        this.processor = new MessageService.Processor(messageService);
    }

    @Override
    public void run() {
        //while (true) {
            try {
                System.out.println("Message Receiver started.");
                while (processor.process(protocol, protocol) == true) { }
                System.out.println("Message Receiver stopped working.");
            } catch (TException ex) {
                System.out.println("Unable to start Message Receiver!");
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        //}
    }
  
}
