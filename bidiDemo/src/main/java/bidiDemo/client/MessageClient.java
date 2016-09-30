/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.client;

import bidiDemo.common.MessageService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 *
 * @author rarora
 */
public class MessageClient implements Runnable{
    private final MessageService.Processor processor;
    private final TProtocol protocol;
    
    public MessageClient(TProtocol pcl, MessageServiceHandler messageServiceHandler){
        protocol = pcl;
        processor = new MessageService.Processor(messageServiceHandler);
    }
     
    @Override
    public void run() {
        while (true) {
        try {
            System.out.println("Client Processor ran.");
            while (processor.process(protocol, protocol) == true) { }
        } catch (TException ex) {
            ex.printStackTrace();
        }
      }
    }
    
}
