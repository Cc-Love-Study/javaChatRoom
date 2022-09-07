package CcRun;

import CcMsgType.Msg;
import CcProcessor.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.io.PrintWriter;
import com.google.gson.*;
public class SwitchServer implements Runnable{
	
	private boolean isRun = true;
	private boolean isLogin = false;
	private String userId; 
	private Socket mySocket;
	private PrintWriter myPrint;
	private BufferedReader myReader;
	private Gson gsonTool;
	private ChatProcessor myChat;
	private UserProcessor myUser;
	//private Msg getMsg;
	
	public SwitchServer(Socket socekt) {
		this.mySocket = socekt;
	}
	@Override
	public void run() {
	
		/*
		 *  开始监听信息
		 *  初始化 输入输出流
		 */
		try {
			// 拿到输入流 
			OutputStream outputStream = mySocket.getOutputStream();
			OutputStreamWriter outputStreamWriter = 
					new OutputStreamWriter(outputStream);
			this.myPrint = new PrintWriter(outputStreamWriter,true);
			
			// 拿到输入流 
			InputStream inputStream = mySocket.getInputStream();
			InputStreamReader inputStreamReader = 
					new InputStreamReader(inputStream,"UTF-8");
			this.myReader = new BufferedReader(inputStreamReader);
			
			this.gsonTool = new Gson();
			
			this.myChat = new ChatProcessor();
			this.myUser = new UserProcessor();
		}catch (Exception e) {
			// 如果初始化失败 就挂断
			System.out.print(mySocket.getInetAddress());
			System.out.println("create in out err");
			return;
		}
		
		
		
		// 这里读取信息 并且根据信息的类型 进行选择
		while(true) {
			try {
			   // 读一条信息
				String getMsg = this.myReader.readLine();
				// 数据转为Msg
				Msg myMsg = gsonTool.fromJson(getMsg, Msg.class);
				// 拿到code
				int code = myMsg.GetCode();

// **************************解析信息 根据信息进入不同的Processor********************

			switch (code){
			// 登录 只有登录后 printer才会被放入链表  id:printer
			case 100:
				Boolean success1 = this.myUser.Login(myMsg,this.myPrint,gsonTool);
				this.isLogin = success1;
				// 服务端拿到服务对象的 ID
				this.userId = myMsg.GetId();
				this.myUser.NewUserList(gsonTool);
				
				if (!success1){
					System.out.println("login err");	
				}
				break;

			// 注册
			case 200:
				Boolean success2 = this.myUser.Register(myMsg, myPrint, gsonTool);
				if (!success2) {
					System.out.println("register err");
				}
				break;
			// 单聊
			case 300:
				boolean success3 = this.myChat.OnlyChat(myMsg, myPrint);
				if (!success3) {
					System.out.println("only Chat err");
				}
				break;
			// 群聊
			case 400:
				boolean success4 = this.myChat.AllChat(myMsg);
				if (!success4){
					System.out.println("err from send all peopel");	
				}
				
				break;
			// 退出 从客户端关闭 这边删除链表内容
			case 500:
				boolean success5 = this.myChat.Logoff(myMsg,mySocket,myPrint);
				if (!success5){
					System.out.println("Logoff Err!");	
				}
				this.myUser.NewUserList(gsonTool);
				
				this.isLogin = false;
				this.isRun = false;
				break;
			}		
				
// ************************************************
			}catch(Exception e){
				// 如果读取信息失败 就return
				// 如果是已经登录的 意外中断 也要冲列表下线
				if (ClientsAndPrints.printWriterMap.containsKey(userId)){
					ClientsAndPrints.RemoveClient(mySocket);
					// 如果有某Id 则去掉 
					ClientsAndPrints.RemovePrint(this.userId);
				}
				System.out.print(mySocket.getInetAddress());
				System.out.println("read inf err");
				return;
			}
			if (this.isRun == false && this.isLogin == false) {
				break;
			}
			
		}
}
}
