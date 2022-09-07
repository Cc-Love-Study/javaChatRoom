# 短学期作业 java编写基于网络通信的小聊天室
一个java编写的聊天app
运行条件:
  需要redis数据库

    一个小项目,在校学生编写,代码风格很差
    但是可以作为课程作业提交


## 服务端设计
![image](https://user-images.githubusercontent.com/92032190/188825525-22be296a-3d77-4a94-9ae5-706c26f9a934.png)

## 客户端设计
![image](https://user-images.githubusercontent.com/92032190/188825555-6e6e9c6b-52dc-4a38-8b1a-b2207880ffb9.png)

## 服务客户端交互设计
![image](https://user-images.githubusercontent.com/92032190/188825581-62277126-7bc5-4bb9-bc56-9e1db4935856.png)

## 客户端目录结构
    CcClient/
    |-- src/
    |   |-- CcMsgType/
    |   |   |-- Msg.java
    |   |-- CcRun/
    |   |   |-- ClientMain.java
    |   |   |-- InitClient.java|
    |   |-- CcView/
    |   |   |-- ClientView.java
    |   |   |-- GetInfo.java
    |   |-- Processor/
    |   |   |-- CcProcessor.java
    |   |   
    
## 服务端目录结构
    CcServer/
    |-- src/
    |   |-- CcMsgType/
    |   |   |-- Msg.java
    |   |-- CcRun/
    |   |   |-- ServerMain.java
    |   |   |-- ClientsAndPrints.java
    |   |   |-- InitSever.java
    |   |   |-- SwitchServer.java
    |   |-- CcProcessor/
    |   |   |-- ChatProcessor.java
    |   |   |-- UserProcessor.java|
    |   |

