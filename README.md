# FritzTR064

Java-library to communicate with the AVM FritzBox by using the TR-064 protocol.


## Quickstart

Get all the posibel Actions:

```java
FritzConnection fc = new FritzConnection("192.168.1.1","<username>","<password>");
fc.init();
fc.printInfo();
```
The next Example shows how you can get the number of connected Wlan Devices:
```java
FritzConnection fc = new FritzConnection("192.168.1.1","<username>","<password>");
fc.init();
Service service = fc.getService("WLANConfiguration:1");
Action action = service.getAction("GetTotalAssociations");
Response response = action.execute();
int deviceCount = response.getValueAsInteger("NewTotalAssociations");

```
For more examples see [the available IntegrationTests](src/test/java/de/mapoll/javaAVMTR064/)

## Resorces
* [AVM API Description](https://avm.de/service/schnittstellen/) (German)
