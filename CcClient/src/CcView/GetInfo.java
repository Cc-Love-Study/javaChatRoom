package CcView;

import java.io.BufferedReader;
import java.util.Date;
import javax.swing.JTextArea;
import java.text.SimpleDateFormat;
import com.google.gson.*;
import CcMsgType.Msg;

public class GetInfo extends Thread {
	private BufferedReader myReader;
	private Gson gsonTool;
	public Msg getMsg;
	private SimpleDateFormat Ftime;
	public GetInfo(BufferedReader myReader) {
		this.myReader = myReader;
		this.gsonTool = new Gson();
		this.Ftime = new SimpleDateFormat("HH:mm:ss YYYY/MM/dd");
	}
	// 需要将 getinfo的值 传给 界面
	@Override
	public void run() {
		while (true) {
			try {
				String msg = myReader.readLine();
				Msg getMsg = gsonTool.fromJson(msg, Msg.class);
				// 根据不同的 信息类型码 来显示不同的内容
				int code = getMsg.GetCode();
				switch (code) {
				case 999:{
					// 作用只有更新界面的用户列表 和 增删 私聊区域
					// 更改 列表数据
			        ClientView.list.setListData((String[]) getMsg.GetUserList().toArray(new String[getMsg.GetUserList().size()]));
			        // 给新用户增加聊天框
			        for (String id:getMsg.GetUserList()) {
			        	System.out.println("id:"+id);
			        	if(!ClientView.OneChatMap.containsKey(id)) {
			        		ClientView.OneChatMap.put(id, new JTextArea());
			        	}
			        }
			        // 程序结束前 不会进行删除 为了存储聊天记录
			        break;
				}
				
				// 接收到群发 
				case 400: {
					System.out.println(getMsg.GetId()+" to allPeople:");
					System.out.println(getMsg.GetInfo());
					// 接收信息 群聊文本框
					Date nowTime = new Date();
					ClientView.textArea.append(Ftime.format(nowTime)+"  " + getMsg.GetName()+"("+getMsg.GetId()+")"+" send to All: \n"+getMsg.GetInfo()+'\n');
					break;
				}
				// 私聊
				case 300:{
					// 将私聊内容显示在对应 对应的 来的用户的界面上22 发给我的
					Date nowTime = new Date();
					// 拿到的是谁发来的id
					ClientView.GetArea(getMsg.GetId()).append(Ftime.format(nowTime)+" "+getMsg.GetId()+":\n"+getMsg.GetInfo()+"\n");
					break;
				}
				}
			}catch (Exception e){
				System.out.println("clientback is stop");
			}
		}
		
	}
}
