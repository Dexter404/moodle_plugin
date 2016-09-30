using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Thrift.Server;
using Thrift.Transport;

namespace LMSReporting
{
    class Program
    {
        private void initServer()
        {
            try
            {
                ReportingServer repServer = new ReportingServer();
                reportingService.Processor processor = new reportingService.Processor(repServer);
                TServerTransport serverTransport = new TServerSocket(9092);
                TServer server = new TSimpleServer(processor, serverTransport);
                Console.WriteLine("Starting the server...");
                server.Serve();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
            Console.WriteLine("Server closed.");
        }
        static void Main(string[] args)
        {
            Program P = new Program();
            P.initServer();
        }
    }
}
