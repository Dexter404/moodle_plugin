/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.client;

import bidiDemo.common.MessageService;
import org.apache.thrift.protocol.TProtocol;

/**
 *
 * @author rarora
 */
public class MessageClient implements Runnable{
    private final MessageService.Processor processor;
    private final TProtocol protocol;
    
    public MessageClient(TProtocol pcl){
        protocol = pcl;
        processor = new MessageService.Processor();
    }
     
    @Override
    public void run() {
        
    }
    
}
