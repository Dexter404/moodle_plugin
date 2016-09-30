/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.server;

import bidiDemo.common.Message;
import bidiDemo.common.MessageService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.thrift.TException;

/**
 *
 * @author rarora
 */
public class MessageServer implements Runnable {
    private final Map<MessageService.Client,Message> clients;
    
    public MessageServer(){
        clients = new HashMap<>();
    }
    
    public void addClient(MessageService.Client client, Message msg){
        clients.put(client,msg);
    }
    
    public void setMessage(MessageService.Client client, Message msg){
        clients.put(client, msg);
    }
    
    @Override
    public void run() {
        while(true){
            Iterator<Map.Entry<MessageService.Client,Message>> clientItr = clients.entrySet().iterator();
            while (clientItr.hasNext()) {
                Map.Entry<MessageService.Client,Message> client = clientItr.next();
                try {
                    //Message msg = new Message("A","Hello " + client.getValue().clientName + "!");
                    Message msg = new Message("A","Greetings from server!");
                    client.getKey().sendMessage(msg);
                } catch (TException te) {
                    clientItr.remove();
                    System.out.println(te.getStackTrace());
                }
            }
        }
    }
    
}
