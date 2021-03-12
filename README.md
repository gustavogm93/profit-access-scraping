# Profit Access Scrapping
This repository contains classes with the purpose of extract financial summaries of companies, process the information and save it in NoSQL Database 


## Connection
-Through JSOUP library, a connection will be established with investment              servers



 ## To Fetch
 The following Summaries of a company will be collected.
   - Financial Summary
   - Income Statement
   - Balance Sheet
   - Cash flow



## Summary Properties
 Each summary will contains the following data.
  - Title
  - Value
  - Period Ending



## JSON Structure
  https://jsoneditoronline.org/#right=cloud.5942f0b09c2041c6aa7c892bf02785d7&left=cloud.a2d626e900124fa1be4dfe5f633d38f9



##  Company Queues Architecture
The architecture of the company's queues consists of two queues, each queue will have a producer and a listener:



### CompanyStarterQueue
 It is the first step, it is responsible for sending the initial message, and the receiver takes
 this message to build the companyOperation Object
 
   -Message: {companyTitle: “Bank-of-America” }
   -Listener: @JmsListener CompanyOperation.create();



### CompanyOperationQueue                                   
It is responsible for sending CompanyOperation, listener executes according to the status of CompanyOperation 
for fetch missing or failed summaries and fill Company Object 

   -Message: {CompanyCode = 243, CompanyState: FINANCIALSUMMARY_FAILED}
   -Listener @JmsListener Company.save();
   
   
   
   ![alt text](https://i.ibb.co/k6tkmXX/tql.png) 
   
