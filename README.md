# SCIP-Connector

This project is a implementation of the org.camunda.bpm.engine.delegate.JavaDelegate interface for Camunda version 7.16.0.
It allows Camunda service tasks to invoke smart contracts using the SCIP protocols JSON RPC Binding.
In case of an synchronous error in the reply message it throws a BPMN error event.


expected input parameters:

Local Variable Name | Variable Assignment Type | Description | example input
-------- | -------- | -------- | -------
timeout   | String or Expression   | timeout in ms or 0 for no timeout | 0  
inputs   | List |list of input parameters in the same format as the SCIP Json RPC binding uses or empty | {"name": "amount", "type": "{\"type\":\"integer\",\"minimum\": 0,\"maximum\":115792089237316195423570985008687907853269984665640564039457584007913129639935}", "value": 15 }  
outputs   | List |list of output parameters in the same format as the SCIP Json RPC binding uses or empty |{ "name": "newName", "type": " {\"type\":\"string\"}"} 
timeout   | String or Expression   | timeout in ms or 0 for no timeout | 0  
doc   | String or Expression   | degree of confidence as floating point number| 50.1  
callbackUrl   | String or Expression   | URL where the callback message should be send to, e.g the camunda message endpoint|  http://172.16.238.14:8080/engine-rest/message
functionIdentifier   | String or Expression   |name of the smart contract function to invoke | orderPart
url   | String or Expression  |SCL of the smart contract to use | http://172.16.238.1:8081/blockchain-access-layer/webapi?blockchain=ethereum&blockchain-id=eth-0&address=0x75f17644EAEb3cC6511764a6F1138F14B3e33D0f

The Connector will send a JSON RPC 2.0 Request to the scip gateway, with randomly generated ID and a <ProcessID>_<ActivityID> as correlation Identifier.

Usage:
-Build the project and put the jar file to a location where camunda platform can find them (This depends on the Camunda distribution. For Camunda Platform run this is the userlibs folder.(https://docs.camunda.org/manual/7.16/user-guide/camunda-bpm-run/#starting-with-camunda-platform-run). For the camunda docker it is the "/camunda/lib/" folder. See the camunda dokumentation for details.)

- The java class to use in the Task is: ScipConnector.ScipDelegate

See https://github.com/dabberpk/CaseStudy for an example where it is used.
