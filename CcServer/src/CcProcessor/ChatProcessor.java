package CcProcessor;

import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.*;
import CcMsgType.Msg;
import CcRun.ClientsAndPrints;

public class ChatProcessor {
	
	private Gson gsonTool;
	
	public ChatProcessor() {
		// TODO Auto-generated constructor stub
		this.gsonTool = new Gson();
	}
	// 私聊逻辑
	public boolean OnlyChat(Msg tMsg,PrintWriter myPrint) {
		
		// 如果包括这个
		boolean v = ClientsAndPrints.printWriterMap.containsKey(tMsg.GetToId());
		boolean isSend = true;
		String sMsg;
		if (v){
			PrintWriter aimPrinter = ClientsAndPrints.printWriterMap.get(tMsg.GetToId());
			sMsg = gsonTool.toJson(tMsg);
			// 发送
			aimPrinter.println(sMsg);
			isSend = true;
		}else {
			isSend = false;
		}
//
//		tMsg.newBackOneChat(isSend);
//		sMsg = gsonTool.toJson(tMsg);
//		myPrint.println(sMsg);
//		
		return isSend;
	}
	
	
	// 群发逻辑 要有锁 因为输出流 会被冲突
	public boolean AllChat(Msg tMsg){
		// 这里接收的 一定是 类型码为 400 的信息
		// 转发给全部的 客户端
		try {
		for(PrintWriter myPrinter:ClientsAndPrints.printWriterMap.values()) {
			String tString = gsonTool.toJson(tMsg);
			myPrinter.println(tString);
		}
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
	// 在线程链表 和 输出流链表内删除 然后停止该线程 在客户端那边
	public boolean Logoff(Msg myMsg,Socket mySocket,PrintWriter myPrint){ 
		try {
			ClientsAndPrints.RemoveClient(mySocket);
			ClientsAndPrints.RemovePrint(myMsg.GetId());
			mySocket.close();
			myPrint.close();
			System.out.println(myMsg.GetId()+" logoff");
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
}
