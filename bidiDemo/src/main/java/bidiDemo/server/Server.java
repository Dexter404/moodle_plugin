/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidiDemo.server;

import bidiDemo.common.MessageService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author rarora
 */
public class Server {
    private int port;
    private TServerTransport transport;
    
    public Server(int p) throws TTransportException{
        port = p;
        transport = new TServerSocket(port);
    }
    
    public void init(){
        System.out.println("Initializing server...");

        final MessageDistributorAndHandler mdh = new MessageDistributorAndHandler();
        new Thread(mdh.new Messenger()).start();

        // Using our own TProcessorFactory gives us an opportunity to get
        // access to the transport right after the client connection is
        // accepted.
        TProcessorFactory processorFactory = new TProcessorFactory(null) {
            @Override
            public TProcessor getProcessor(TTransport trans) {
                MessageServiceClient msgClient = new MessageServiceClient(trans);
                mdh.addClient(msgClient);
                System.out.println("Client added to list.");
                return new MessageService.Processor(mdh.new MessageServiceHandler(msgClient));
            }
        };

        TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(transport);
        serverArgs.processorFactory(processorFactory);
        TServer server = new TThreadPoolServer(serverArgs);
        System.out.println("Server started.");
        server.serve();
    }
    
    public static void main(String[] args){
        try {
            Server server = new Server(9091);
            server.init();
        } catch (TTransportException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
