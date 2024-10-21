# SCIP Connector for Camunda

This project is a implementation of the `org.camunda.bpm.engine.delegate.JavaDelegate` interface for Camunda version 7.21.0.
It allows Camunda service tasks to send B-SCIP request messages 
(invoke smart contract function, send transaction, receive transaction, and ensure a desired state for a given transaction). 
It uses json-rpc over http to submit the requests.
In case of a synchronous error in the reply message it throws a BPMN error event.


## Input parameters:

Local Variable Name | Variable Assignment Type | Description                                                                                                                                              | example input                                                                                                                                                                
-------- | -------- |----------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
isStateful   | String or Expression   | indiciates whether the invoked function may alter the blockchain's state                                                                                 | true                                                                                                                                                                            
inArgs   | String or Expression | list of input arguments to be passed to the smart contract function. The list is formatted in the same format as the SCIP Json RPC binding uses or empty | `{ "name": "amount", "value": 15 }` 
outputs   | String or Expression | list of output parameters in the same format as the SCIP Json RPC binding uses or empty                                                                  | `[{ "name": "userId", "type": " {\"type\":\"string\"}"}]`                                                                                                                       
doc   | String or Expression   | degree of confidence as a percentage                                                                                                                     | 95                                                                                                                                                                           
signature   | String or Expression   | signature of the smart contract function to invoke                                                                                                       | `{ "name": "getBalance", "function": "true", "parameters": [{ "name":"userId", "type":"{\"type\":\"string\"}" }] }`                                                          
scl   | String or Expression  | SCL to target the message at                                                                                                                             | `http://172.16.238.1:8081?blockchain=ethereum&blockchain-id=eth-0&address=0x75f17644EAEb3cC6511764a6F1138F14B3e33D0f`                                                          
value  | String or Expression | the amount to send in a tx (smallest denomination, e.g., wei)                                                                                            | 100000
from   | String or Expression | the blockchain-specific address from which we expect to receive a transaction                                                                            |  0xABf17644EAEb3cC6511764a6F1138F14B3e33D2D
corrId | String or Expression | the correlation identifier to identify the task | abcd
ref    | String or Expression | the correlation identifier of the task whose transaction is to be monitored for its state | abcd

The Connector will send a JSON RPC 2.0 Request to the scip gateway, with randomly generated UUID and `"${processInstanceId}_{corrId}"` as correlation identifier.

## Usage:

- Build the project and put the jar file to a location where camunda platform can find it (This depends on the Camunda distribution. For Camunda Platform Run this is the `/configuration/userlibs` folder (https://docs.camunda.org/manual/7.16/user-guide/camunda-bpm-run/#starting-with-camunda-platform-run). For the camunda docker it is the `/camunda/lib/` folder. See the camunda documentation for details.
- The java class to use in the Task is one of: 
  - `scip.connector.task.SendSendTxRequestTask`
  - `scip.connector.task.SendReceiveTxRequestTask`
  - `scip.connector.task.SendEnsureStateRequestTask`
  - `scip.connector.task.SendInvokeRequestTask`
