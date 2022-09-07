package CcView;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.*;

import com.google.gson.Gson;

import Processor.CcProcessor;
public class ClientView{

	public String userName;
	public String userId;
	public static JList<String> list = new JList<String>();
	private JFrame frame;
	private CcProcessor myProcessor;
	private BufferedReader tranBackReadner;
	public static JTextArea textArea= new JTextArea();
	public static HashMap<String, JTextArea> OneChatMap = new HashMap<String, JTextArea>();
	
	
	public ClientView(Gson gsonTool,PrintWriter myPrinter,BufferedReader myReader){
		this.myProcessor = new CcProcessor(gsonTool, myPrinter, myReader);
		this.tranBackReadner = myReader;
        textArea.setLineWrap(true);
	}
	public void OneChatView(String toName) {
		JFrame frame1 = new JFrame("私聊:"+toName);
        JFrame.setDefaultLookAndFeelDecorated(true);
        // 创建及设置窗口
        frame1.setSize(300, 600);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // 面板类似 <div>
        JPanel panel = new JPanel();    
        // 添加面板 到 窗口
        frame1.add(panel);
        // 设置布局为 null
        panel.setLayout(null);
        // 设置自动换行

        // 设置自动换行
        GetArea(toName).setLineWrap(true);
//        GetArea(toName).setBounds(0, 0, 300, 300);
        
        JScrollPane getArea = new JScrollPane(GetArea(toName));
        getArea.setBounds(0, 0, 300, 300);
        getArea.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // 添加到内容面板
        
        // 添加到内容面板
        panel.add(getArea);
        
        final JTextArea textArea2 = new JTextArea() ;
        // 设置自动换行
        textArea2.setLineWrap(true);
        textArea2.setBounds(0, 400, 300, 100);
        
        // 添加到内容面板
        panel.add(textArea2);
        
        // 创建退出按钮
        JButton CancelButton = new JButton("Cancel");
        CancelButton.setBounds(30, 530, 80, 25);
        // 退出监听器
        CancelButton.addActionListener(new ActionListener(){
        	// 这里进入退出方法 发送退出信息
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame1.dispose();
            }});
        
        panel.add(CancelButton);
        
        // 创建发送按钮
        JButton SendButton = new JButton("Send");
        SendButton.setBounds(100, 530, 80, 25);
        // 发送监听器
        SendButton.addActionListener(new ActionListener(){
        	// 这里进入发送方法 发送 信息
            @Override
            public void actionPerformed(ActionEvent e) {
            	String info = textArea2.getText();
                myProcessor.OneChat(userId,toName,info);
                textArea2.setText("");
            }});
        
        panel.add(SendButton);
		frame1.setLocationRelativeTo(null);//窗体默认居中
        // 显示窗口
        frame1.setVisible(true);
	}
	public void AllChatView() {
		frame = new JFrame("欢迎:"+userName);
        JFrame.setDefaultLookAndFeelDecorated(true);
        // 创建及设置窗口
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 面板类似 <div>
        JPanel panel = new JPanel();    
        // 添加面板 到 窗口
        frame.add(panel);
        // 设置布局为 null
        panel.setLayout(null);
        // 设置自动换行


        JScrollPane jsp = new JScrollPane(textArea);
        jsp.setBounds(0, 0, 400, 300);
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // 添加到内容面板
        panel.add(jsp);
        
        
        
        
        final JTextArea textArea2 = new JTextArea() ;
        // 设置自动换行
        textArea2.setLineWrap(true);
        textArea2.setBounds(0, 400, 400, 100);
        // 添加到内容面板
        panel.add(textArea2);
        
        // 创建退出按钮
        JButton CancelButton = new JButton("Cancel");
        CancelButton.setBounds(20, 520, 80, 25);
        // 退出监听器
        CancelButton.addActionListener(new ActionListener(){
        	// 这里进入退出方法 发送退出信息
            @Override
            public void actionPerformed(ActionEvent e) {
            	myProcessor.Cancel(userId);
            	frame.dispose();
            	 System.exit(0);
            }});
        
        panel.add(CancelButton);
        
   
       // 创建一个 JList 实例
        // 设置一下首选大小
        list.setPreferredSize(new Dimension(200, 100));
        // 允许可间断的多选
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 添加选项选中状态被改变的监听器
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 获取所有被选中的选项索引
            	try{
                if (list.getValueIsAdjusting()==false) {
                	ListModel<String> listModel = list.getModel();
                	String toName = listModel.getElementAt(list.getSelectedIndex());
                	System.out.println(toName);
                	OneChatView(toName);
                }}catch (Exception ee) {
					// TODO: handle exception
				}
                
            }
        });
        JScrollPane jspList = new JScrollPane(list);
        jspList.setBounds(450, 50, 200, 400);
        jspList.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jspList);
        
        
        JLabel label01 = new JLabel();
        label01.setText("在线用户列表");
        label01.setFont(new Font(null, Font.PLAIN, 25));  // 设置字体，null 表示使用默认字体
        label01.setBounds(450, 0, 200, 50);
        panel.add(label01);
        
        
        
        
        // 创建发送按钮
        JButton registerButton = new JButton("Send");
        registerButton.setBounds(300, 520, 80, 25);        
        // 监听器
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 这里发送函数
            	String info = textArea2.getText();
                myProcessor.AllChat(userId, userName, info);
                textArea2.setText("");
            }
        });
        panel.add(registerButton);
		frame.setLocationRelativeTo(null);//窗体默认居中
        // 显示窗口
        frame.setVisible(true);

	}
	
    private void LoginView() {
        // 确保一个漂亮的外观风格
        // 显示应用 GUI
    	frame = new JFrame("LoginView");
        JFrame.setDefaultLookAndFeelDecorated(true);
        // 创建及设置窗口
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 面板类似 <div>
        JPanel panel = new JPanel();    
        // 添加面板 到 窗口
        frame.add(panel);
        // 设置布局为 null
        panel.setLayout(null);
		
        // 用户名
        JLabel userLabel = new JLabel("UserName:");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // id
        JLabel idLabel = new JLabel("UserId:");
        idLabel.setBounds(10,50,80,25);
        panel.add(idLabel);
        JTextField idText = new JTextField(20);
        idText.setBounds(100,50,165,25);
        panel.add(idText);

        
        // 密码
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,80,80,25);
        panel.add(passwordLabel);
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,80,165,25);
        panel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("login");
        loginButton.setBounds(130, 130, 80, 25);
        // 登录监听器
        loginButton.addActionListener(new ActionListener(){
        	// 这里进入登录方法 发送登录信息
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
	                String sendId = idText.getText().trim();       
	                String sendName = userText.getText().trim();
	                String sendPass =  new String(passwordText.getPassword()).trim();  
	                if ("".equals(sendId) ||"".equals(sendName)||"".equals(sendPass)){
						JOptionPane.showMessageDialog(
	                            frame,
	                            "cant empty",
	                            "消息标题",
	                            JOptionPane.WARNING_MESSAGE
	                    );
	                }else{
		                boolean res = myProcessor.Login(sendName, sendId, sendPass);
	    				if (res) {
	    					userName = sendName;
	    					userId = sendId;
	    					JOptionPane.showMessageDialog(
	    	                        frame,
	    	                        "Login Success !!!",
	    	                        "消息标题",
	    	                        JOptionPane.INFORMATION_MESSAGE
	    	                        
	    	                );
	    					frame.dispose();
	    					// 登录成功后 进入聊天页面
	    					// 这里需要开一个聊天线程 接收消息 更新聊天框
	      					 GetInfo clientBack = new GetInfo(tranBackReadner);
	    					 clientBack.start();
	    					 AllChatView();
	    				}else {
	    					JOptionPane.showMessageDialog(
	                                frame,
	                                "Login err",
	                                "消息标题",
	                                JOptionPane.WARNING_MESSAGE
	                        );
	    				}}
            	} catch (Exception e1) {
					// TODO: handle exception
                    JOptionPane.showMessageDialog(
                            frame,
                            "Unknown error",
                            "消息标题",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }});
        
        panel.add(loginButton);
        
        // 创建注册按钮
        JButton registerButton = new JButton("register");
        registerButton.setBounds(300, 130, 80, 25);

        
        
        // 注册监听器
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 这里进入注册页面
                frame.dispose();
                RegisterView();
            }
        });
        panel.add(registerButton);
		frame.setLocationRelativeTo(null);//窗体默认居中
        // 显示窗口
        frame.setVisible(true);

    }    
    
    
    public static synchronized JTextArea GetArea(String id) {
		return ClientView.OneChatMap.get(id);
    }
    
    private void RegisterView() {
        // 显示应用 GUI
    	frame = new JFrame("RegisterView");
        JFrame.setDefaultLookAndFeelDecorated(true);
        // 创建及设置窗口
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 // 面板类似 <div>
        JPanel panel = new JPanel();    
        // 添加面板 到 窗口
        frame.add(panel);
        // 设置布局为 null
        panel.setLayout(null);
        // id
        JLabel idLabel = new JLabel("UserId:");
        idLabel.setBounds(10,50,80,25);
        panel.add(idLabel);
        JTextField idText = new JTextField(20);
        idText.setBounds(100,50,165,25);
        panel.add(idText);

        
        // 密码
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,80,80,25);
        panel.add(passwordLabel);
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,80,165,25);
        panel.add(passwordText);
        
        
        // 创建返回按钮
        JButton backButton = new JButton("back");
        backButton.setBounds(130, 130, 80, 25);

        // 返回监听器
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 返回页面
                frame.dispose();
                LoginView();
            }
        });
        panel.add(backButton);
        
        
        
        // 创建注册按钮
        JButton registerButton = new JButton("register");
        registerButton.setBounds(300, 130, 80, 25);

        // 注册监听器
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 这里进入注册页面
            	
            	String sendId = idText.getText().trim();       
                String sendPass =  new String(passwordText.getPassword()).trim();
                if ("".equals(sendId) || "".equals(sendPass)) {
					JOptionPane.showMessageDialog(
                            frame,
                            "cant empty",
                            "消息标题",
                            JOptionPane.WARNING_MESSAGE
                    );
                }else {        
	                boolean res = myProcessor.Register(sendId, sendPass);
					if (res) {
						JOptionPane.showMessageDialog(
		                        frame,
		                        "Register Success !!!",
		                        "消息标题",
		                        JOptionPane.INFORMATION_MESSAGE
		                        // 注册成功
		                );
					}else {
						JOptionPane.showMessageDialog(
	                            frame,
	                            "Register err",
	                            "消息标题",
	                            JOptionPane.WARNING_MESSAGE
	                    );
					}
            
                }}});
            	
            
        panel.add(registerButton);
        
        
		frame.setLocationRelativeTo(null);//窗体默认居中
        // 显示窗口
        frame.setVisible(true);
    }
    
    public void ViewRun() {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	LoginView();
            }
        });
    }
 }
