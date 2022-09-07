package CcRun;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import CcView.ClientView;
import com.google.gson.*;
/*
 * 聊天室功能
 * 1. 确定服务器的IP和端口
 * 2. 连接服务器
 * 3. 选择功能
 */

public class InitClient {
	
	private Socket clientSocket;
	private PrintWriter myPrint;
	private BufferedReader myReader;
	private Gson gsonTool;
	// 序列化生成器 

	// 构造函数
	public InitClient(){
		/*
		 * 在这里对 服务器进行连接
		 */
		try {
			this.clientSocket = new Socket("127.0.0.1",9999);
		}catch (Exception e) {
			System.out.println("client conn err");
			e.printStackTrace();
			return;
		}
		
		//拿到输入输出流
		try {
			// 拿到输入输出流 
			OutputStream outputStream = clientSocket.getOutputStream();
			// 高级封装
			OutputStreamWriter outputStreamWriter = 
					new OutputStreamWriter(outputStream);
			// 自动行刷新的输出流
			this.myPrint = new PrintWriter(outputStreamWriter,true);
			// 拿到输出流 
			InputStream inputStream = clientSocket.getInputStream();
			InputStreamReader inputStreamReader = 
					new InputStreamReader(inputStream,"UTF-8");
			this.myReader = new BufferedReader(inputStreamReader);
			// gson对象初始化
			this.gsonTool = new Gson();
			
		}catch (Exception e) {
			System.out.println("create in out err");
			return;
		}	
		

	}
	// 这里可以开始写入 界面 和 给用户发送信息了
	public void Start() {
		// 这里用来显示界面 和 发送信息
		// 需要拿到print 和 reader
		ClientView myView = new ClientView(gsonTool,myPrint,myReader);
		myView.ViewRun();
	}
	
}
