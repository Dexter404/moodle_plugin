namespace php LMSReporting
namespace csharp LMSReporting
namespace cpp LMSReporting

service reportingService{
    bool launchApplication(1:string appName, 2:string appPath);
    bool closeApplication(1:string appId);
}