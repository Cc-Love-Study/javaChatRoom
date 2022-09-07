package CcMsgType;

import java.util.ArrayList;
import java.util.List;

import CcRun.ClientsAndPrints;

// 客户端信息类型  都是 客户端-服务器类型的消息
public class Msg {
	private String code;
	private String name;
	private String toId;
	private String id;
	private String pass;
	private String info;
	private boolean isLogin;
	private boolean isSend;
	private boolean isRegister;
	public List<String> userList = new ArrayList<>();

	// 返还字符串
	
	// 返还当前在线用户的列表
	public void newUserListMsg() {
		this.code = "999";
		for(String myPrinter:ClientsAndPrints.printWriterMap.keySet()) {
			this.userList.add(myPrinter);
			System.out.println("在线:");
			System.out.println(myPrinter);
		}
	}
	public List<String> GetUserList(){
		return this.userList;
	}
	
	public void newLoginMsg(String name,String id,String pass) {
		this.code = "100";
		this.name = name;
		this.id = id;
		this.pass = pass;
	}
	public void newBackLogin(Boolean login) {
		this.code = "10";
		this.isLogin = login;
	}
	
	public void newRegisterMsg(String id,String pass) {
		this.code = "200";
		this.id = id;
		this.pass = pass;
	}

	public void newBackRegisterMsg(Boolean isRegister) {
		this.code = "20";
		this.isRegister = isRegister;
	}
	
	public void newOneChatMsg(String id,String toId,String info) {
		this.code = "300";
		this.id = id;
		this.toId = toId;
		this.info = info;
	}
	public void newBackOneChat(Boolean isSend) {
		this.code = "30";
		this.isSend = isSend;
	}
	
	public void newAllChatMsg(String name,String Id,String info) {
		this.code = "400";
		this.id = Id;
		this.name = name;
		this.info = info;
	}
	
	public void newLogoff() {
		this.code = "500";
	}
	

	public boolean GetisRegister() {
		return this.isRegister;
	}
	public boolean GetisSend() {
		return this.isSend;
	}
	public boolean GetLogin() {
		return this.isLogin;
	}
	public int GetCode() {
		return Integer.valueOf(this.code);
	}
	public String GetName() {
		return this.name;
	}
	public String GetId() {
		return this.id;
	}
	public String GetToId() {
		return this.toId;
	}
	public String GetInfo() {
		return this.info;
	}
	public String GetPass() {
		return this.pass;
	}
	
	
}
