using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Thrift.Protocol;
using Thrift.Transport;

namespace HelloWorldClient
{
    class Program
    {
        static void Main(string[] args)
        {
            TTransport transport = new TSocket("localhost", 9091);
            transport.Open();
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloWorldService.HelloWorldService.Client client = new HelloWorldService.HelloWorldService.Client(protocol);
            //System.Diagnostics.Debug.WriteLine(client.hello("c# client"));
            System.Console.WriteLine(client.hello("c# client"));
            System.Console.ReadLine();
        }
    }
}
