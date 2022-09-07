package Processor;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import CcMsgType.Msg;
import CcView.ClientView;

public class CcProcessor {
	private PrintWriter myPrinter;
	private BufferedReader myReader;
	private Gson gsonTool;
	private SimpleDateFormat Ftime = new SimpleDateFormat("HH:mm:ss YYYY/MM/dd");;
	public CcProcessor(Gson gsontool,PrintWriter myprinter,BufferedReader myReader){
		this.myPrinter = myprinter;
		this.gsonTool = gsontool;
		this.myReader = myReader;
	}
	
	public boolean Login(String sendName,String sendId,String sendPass){
		// 发送一条 登录信息 
        Msg sendMsg = new Msg();
        sendMsg.newLoginMsg(sendName, sendId, sendPass);
        String sendjson = gsonTool.toJson(sendMsg);
        myPrinter.println(sendjson);
        System.out.println(sendjson);
        // 拿到登录返还消息 
        try {
		String msg = myReader.readLine();
		Msg getMsg = gsonTool.fromJson(msg, Msg.class);
			if (getMsg.GetLogin() == true) {
				return true;
			}else {
				return false;
			}
        }catch (Exception e) {
			// TODO: handle exception
        	return false;
		}
	}
	
	public boolean OneChat(String myId,String toId,String sendinfo) {
		Msg sendMsg = new Msg();
		sendMsg.newOneChatMsg(myId, toId, sendinfo);
		String sendString = gsonTool.toJson(sendMsg);
		myPrinter.println(sendString);
		Date nowTime = new Date();
		ClientView.GetArea(toId).append(Ftime.format(nowTime)+" "+"我说:\n"+sendinfo+"\n");
		return true;
	}
	public boolean AllChat(String sendId,String sendName,String sendinfo){
		// 发送一条 登录信息 
        Msg sendMsg = new Msg();
        sendMsg.newAllChatMsg(sendName,sendId,sendinfo);
        String sendjson = gsonTool.toJson(sendMsg);
        myPrinter.println(sendjson);
        return true;
	}
	


	public boolean Cancel(String id){
		// 发送一条 登录信息 
        Msg sendMsg = new Msg();
        sendMsg.newLogoff(id);
        String sendjson = gsonTool.toJson(sendMsg);
        myPrinter.println(sendjson);
        return true;
	}
	
	public boolean Register(String sendId,String sendPass){
		// 发送一条 登录信息 
        Msg sendMsg = new Msg();
        sendMsg.newRegisterMsg(sendId, sendPass);
        String sendjson = gsonTool.toJson(sendMsg);
        myPrinter.println(sendjson);
        System.out.println(sendjson);
        // 拿到注册返还消息 
        try {
		String msg = myReader.readLine();
		Msg getMsg = gsonTool.fromJson(msg, Msg.class);
			if (getMsg.GetisRegister() == true) {
				return true;
			}else {
				return false;
			}
        }catch (Exception e) {
			// TODO: handle exception
        	return false;
		}
	}
}
