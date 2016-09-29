/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.server;

import bidiDemo.common.Message;
import bidiDemo.server.MessageServiceHandler;
import bidiDemo.common.MessageService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author rarora
 */
public class Server {
    public static void main(String[] args){
        try {
            final MessageServer msgServer = new MessageServer();
            new Thread(msgServer).start();
            
            TProcessorFactory processorFactory = new TProcessorFactory(null) {
                @Override
                public TProcessor getProcessor(TTransport trans) {
                    MessageService.Client msgSrvClient = new MessageService.Client(new TBinaryProtocol(trans));
                    msgServer.addClient(msgSrvClient,new Message());
                    return new MessageService.Processor(new MessageServiceHandler(msgSrvClient, msgServer));
                }
            };

            
            TServerTransport serverTransport = new TServerSocket(9099);
            TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverTransport);
            serverArgs.processorFactory(processorFactory);
            TServer server = new TThreadPoolServer(serverArgs);
            System.out.println("Java server started.");
            server.serve();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
