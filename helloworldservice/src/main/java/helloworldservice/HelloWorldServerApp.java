/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworldservice;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 *
 * @author rarora
 */
public class HelloWorldServerApp {
    public static void main(String[] args){
        try{
            HelloWorldServer hwServer = new HelloWorldServer();
            HelloWorldService.Processor processor = new HelloWorldService.Processor(hwServer);
            TServerTransport serverTransport = new TServerSocket(9091);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("Starting Java server");
            server.serve();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
