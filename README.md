# mail_monitor 
天眼查风险监控邮件内容存入数据库
****
|Author|taoly|
|---|---
|E-mail|taolybaka@hotmail.com
****
首先这个项目**不是爬虫项目**，mail_monitor是一个将**邮件内容定期存储**，并且能返回邮件相关内容的微服务。
####
我们使用了天眼查的风险监控服务，用来监控客户、供应商的风险信息，天眼查会定期将这些信息以邮件的形式发到指定邮箱。风控每天去查看邮件，并且处理这些非结构化的数据比较麻烦，这个微服务的主要作用是把邮件内容存入数据库中，并且开放一些风险信息查询接口供其他业务使用。
####
主要使用了`springboot` `jpa`  
下载项目，maven reimport，在application.yml里配置好自己的数据库，就可以直接使用（项目启动后，会自动建表）。  
在正式调用前，需要在数据库运行（因为在代码没有处理初次运行，但mail_log里没有数据的问题）：  
`insert into mail_log (all_mail_num,daily_all_mail_num,daily_effective_mail_num,log_type,msg) values(0,0,0,0,'初始化')`  
然后在MailController里，找到saveMailScheduled()方法，可以直接通过 ip:7410:monitor/task调用，也可以通过修改@Scheduled注解里的cron表达式来修改定时运行的时间

****
## 主要思路和要点
* [连接邮箱并获取内容](#连接邮箱并获取内容)
* [解析HTML](#解析HTML)
* [内容存储](#内容存储)

### 连接邮箱并获取内容  

### 解析HTML
### 内容存储
