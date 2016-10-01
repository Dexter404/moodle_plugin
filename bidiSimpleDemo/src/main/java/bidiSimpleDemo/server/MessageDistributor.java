/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiSimpleDemo.server;

import bidiSimpleDemo.common.Message;
import bidiSimpleDemo.common.MessageService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.thrift.TException;

/**
 *
 * @author rarora
 */
public class MessageDistributor implements Runnable {
    private final Map<MessageService.Client,Message> clients;
    
    public MessageDistributor(){
        clients = Collections.synchronizedMap(new HashMap<>());
    }
    
    public void addClient(MessageService.Client client){
        clients.put(client,null);
    }
    
    public void setMessage(MessageService.Client client, Message msg){
        clients.put(client, msg);
    }
    
    @Override
    public void run() {
        while(true){
            synchronized(clients){
                Iterator<Map.Entry<MessageService.Client,Message>> clientItr = clients.entrySet().iterator();
                if (clientItr.hasNext()) {
                    Map.Entry<MessageService.Client,Message> client = clientItr.next();
                    if(client.getValue()!=null){
                        try {
                            Message msg = new Message("A","Hello " + client.getValue().clientName + "!");
                            //Message msg = new Message("A","Greetings from server!");
                            client.getKey().sendMessage(msg);
                        } catch (TException te) {
                            System.out.println(te.getStackTrace());
                        }
                        clientItr.remove();
                    }
                }
            }
        }
    }
    
}
