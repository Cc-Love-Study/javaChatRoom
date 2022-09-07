package CcProcessor;
import java.io.PrintWriter;

import com.google.gson.Gson;

import CcMsgType.Msg;
import CcRun.ClientsAndPrints;
import redis.clients.jedis.*;
public class UserProcessor {
	public boolean Login(Msg loginMsg,PrintWriter myPrint,Gson gsonTool) {
		String id = loginMsg.GetId();
		String pass = loginMsg.GetPass();
		boolean isLogin = false;
		// 如果登录成功 才会 放入链表
		// 这里还需要 链表内不存在 才可以登录
		
		Jedis jedis = new Jedis("localhost");
		Boolean isHave = jedis.hexists("userlist", id);
		if(isHave) {
			// 如果存在 才会继续获得密码
			String getPass = jedis.hget("userlist", id);
			// 将密码 带入判断
			if (pass.equals(getPass)&& !ClientsAndPrints.printWriterMap.containsKey(id)) {
				try {
					// 将 输入流 放入链表
					ClientsAndPrints.AddPrint(loginMsg.GetId(),myPrint);
					// 打印当前在线人id
					for(String show:ClientsAndPrints.printWriterMap.keySet()) {
						System.out.println("is login:");
						System.out.println(show);
					}
					isLogin = true;
					
					
				}catch(Exception e) {
					isLogin = false;
					System.out.println("login falx");
				}
			}else {
				isLogin = false;
				System.out.println("login falxx");
			}
			
		}else {
			// 数据库不存在 就直接挂掉
			isLogin = false;
		}

		jedis.close();
		// 将登录成功的消息返还
		loginMsg.newBackLogin(isLogin); 
		String backString = gsonTool.toJson(loginMsg);
		myPrint.println(backString);
		
		return isLogin;

	}
	
	public void NewUserList(Gson gsonTool) {
		Msg newMsg = new Msg();
		newMsg.newUserListMsg();
		String backString = gsonTool.toJson(newMsg);
		try {
			for(PrintWriter myPrinter:ClientsAndPrints.printWriterMap.values()) {
				myPrinter.println(backString);
		}
		}catch (Exception e){
		}
	}
	
	public boolean Register(Msg registerMsg,PrintWriter myPrint,Gson gsonTool) {
		String id = registerMsg.GetId();
		String pass = registerMsg.GetPass();
		boolean isRegister = true;
		// 数据库 操作
		// 对数据库进行增
		// 连接数据库
		Jedis jedis = new Jedis("localhost");
		System.out.println(id);
		Boolean isHave = jedis.hexists("userlist", id);
		// 如果已经存在
		if(isHave) {
			isRegister = false;
		}else {
			jedis.hset("userlist", id, pass);
			isRegister = true;
		}
		jedis.close();

		registerMsg.newBackRegisterMsg(isRegister);
		String backString = gsonTool.toJson(registerMsg);
		myPrint.println(backString);
		return isRegister;
	}
}
