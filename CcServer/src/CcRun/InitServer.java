package CcRun;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class InitServer {
	// 声明套接字
	private ServerSocket serverSocket;
	// 声明线程池
	private ExecutorService threadPool;
	// 构造方法 进行初始化
	public InitServer() {
		// 启动 服务端socket对象   监听端口
		try {
		// 端口号9999
		serverSocket = new ServerSocket(9999);
		// 创建线程池
		threadPool = Executors.newFixedThreadPool(50);
		
		} catch (Exception e) {
			System.out.println("server init err");
			e.printStackTrace();
		}
			      
	}
	
	// 启动监听 进行服务
	public void Start() {
		/* 
		 * 开始监听 9999 端口 进行服务
		 */
		try {	
			while (true) {
				try {
					// 拿到套接字对象
					Socket socketObj = serverSocket.accept();
					// 创建一个任务对象 交给线程池
					SwitchServer clientLine = new SwitchServer(socketObj);
					// 添加到用户服务对象列表  可以通过这个列表 知道有多少连接
					ClientsAndPrints.AddClient(socketObj);
					//将任务对象 派发给线程池
					threadPool.execute(clientLine);
					
				} catch (Exception e) {
					System.out.println("9999 err");
					e.printStackTrace();
				}
			}
		}finally {
				threadPool.shutdown();
		}
	}
}