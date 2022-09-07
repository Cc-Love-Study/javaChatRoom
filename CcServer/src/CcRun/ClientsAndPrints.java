package CcRun;
import java.util.HashMap;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;
public class ClientsAndPrints {	
	// 声明输出流链表
	
	// 声明服务列表
	public static List<Socket> clientList = new ArrayList<>();
	
	public static HashMap<String,PrintWriter> printWriterMap = new HashMap<>();;
	
	public static synchronized void AddClient(Socket client) {
			clientList.add(client);
	}
	public static synchronized void AddPrint(String id,PrintWriter printer) {
			printWriterMap.put(id,printer);
	}
	// 在链表内 移除对象
	public static synchronized void RemovePrint(String id) {
		printWriterMap.remove(id);
	}
	public static synchronized void RemoveClient(Socket client) {
		clientList.remove(client);
	}
}
