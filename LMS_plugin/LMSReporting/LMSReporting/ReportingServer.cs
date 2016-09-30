using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LMSReporting
{
    class ReportingServer : reportingService.Iface
    {
        private string appName;
        private string appPath;
        private string appProcessId;

        public bool launchApplication(string name, string path)
        {
            appName = name;
            appPath = path;
            System.Console.WriteLine("Launching " + appName + "...");

            System.Console.WriteLine(appName + "launched.");
            return true;
        }

        public bool closeApplication(string processId)
        {
            System.Console.WriteLine("Closing " + appName + "...");
            System.Console.WriteLine(appName + "closed.");
            return true;
        }
    }
}
