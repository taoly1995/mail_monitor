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
使用javax.mail包，因为只需要接收邮件，所以选用**pop3**协议，另外还需要你邮箱服务器pop3协议的**host地址**。  
我使用的是网易企业邮箱，一般第三方客户端调用邮箱内容还需要一个**授权码**。  
host地址有时候网上查到可能连不通服务，可以打电话给客服，授权码可以去邮箱设置里面找。
打开邮箱连接后，默认getFolder("INBOX"),从收件箱里取。有以下4个方法取具体邮件（Message）：  
`Message getMessage(int msgnum)`：取邮箱中第`msgnum`封邮件，从1开始计数。  
`Message[] getMessages(int start, int end)`：取邮箱中第`start`到第`end`封  
`Message[] getMessages(int[] msgnums)`：取数组数字对应的邮件  
`Message[] getMessages()`：取文件夹下全部邮件  
`folder.java`这个类里还有些别方法，比如：`getMessageCount()` 取邮件总数，对于邮件处理的逻辑有帮助。  
在`MailAnalysisUtil`工具类中有相应取邮件内容，邮件名，邮件日期等方法
### 解析HTML
由于目标邮件的内容是一个html，所以需要解析html，获取相应的数据。  
这里用了jsoup，局限性比较大，如果html格式变动大的话，要手动调。
### 内容存储
天眼查的风险监控邮件主要内容一般分为两种，一种是单行类，一种是表格类。在相应的service都用了反射，自动进入是分别的处理方法。单行类比较简单，找到相应html标签解析出来直接存就行了。表格类需要从一个Elements按行去保存。  
2019年9月12日：
由于`DetailType`可能有所增加，需要手动去调比较麻烦，现在已经改成自动增加DetailType，MailDetail里的表格类也有header1，header2这样的字段去存，相应的对应放在了AttributeToForm里。
