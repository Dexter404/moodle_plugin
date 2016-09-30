using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Thrift.Server;
using Thrift.Transport;

namespace HelloWorldService
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                HelloWorldServer hwServer = new HelloWorldServer();
                HelloWorldService.Processor processor = new HelloWorldService.Processor(hwServer);
                TServerTransport serverTransport = new TServerSocket(9092);
                TServer server = new TSimpleServer(processor, serverTransport);
                Console.WriteLine("Starting the server...");
                server.Serve();
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
            Console.WriteLine("done.");
        }
    }
}
