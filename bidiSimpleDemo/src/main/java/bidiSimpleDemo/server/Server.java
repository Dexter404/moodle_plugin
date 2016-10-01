/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiSimpleDemo.server;

import bidiSimpleDemo.common.Message;
import bidiSimpleDemo.common.MessageService;
import bidiSimpleDemo.server.MessageServiceHandler;
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
            final MessageDistributor msgDistributor = new MessageDistributor();
            new Thread(msgDistributor).start();
            
            TProcessorFactory processorFactory = new TProcessorFactory(null) {
                @Override
                public TProcessor getProcessor(TTransport trans) {
                    MessageService.Client msgSrvClient = new MessageService.Client(new TBinaryProtocol(trans));
                    msgDistributor.addClient(msgSrvClient);
                    System.out.println("Client added to list.");
                    return new MessageService.Processor(new MessageServiceHandler(msgSrvClient, msgDistributor));
                }
            };

            
            TServerTransport serverTransport = new TServerSocket(9095);
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
