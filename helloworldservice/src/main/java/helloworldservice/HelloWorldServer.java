/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworldservice;

import org.apache.thrift.TException;
import java.util.Scanner;
/**
 *
 * @author rarora
 */
public class HelloWorldServer implements HelloWorldService.Iface{
    public String hello(String name) throws TException{
        System.out.println(name);
        Scanner sc = new Scanner(System.in);
        while(true){
            String input = sc.nextLine();
            if(input.equals("quit"))
                break;            
        }
        return name + "Hello world from Java server.";
    }
}
