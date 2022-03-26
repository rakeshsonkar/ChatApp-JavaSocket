
 import java.io.IOException;
import java.net.*;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Server extends JFrame{

	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	// Declare Components 
	private JLabel heading=new JLabel("Server Area");
	private JTextArea messageArea=new JTextArea();
	private JTextField messageInput= new JTextField();
	private Font font=new Font("Roboto",Font.PLAIN,20);
	
	public Server() {
		try {
			server=new ServerSocket(777);
			System.out.println("server is  ready to accept Connection");
			socket=server.accept();
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
			
			createGUI();
			handleEvents();
			startReading();
			//startWriting();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void startReading() {
		Runnable r1=()->{
			System.out.println("reader starting ...");
			try {
			while(!socket.isClosed()){
				
					String msg=br.readLine();
					if(msg.equals("exit")) {
						//System.out.println("client terminates the chat");
						JOptionPane.showMessageDialog(this,"client terminated the Chat");
						messageInput.setEnabled(false);
						socket.close();
						break;
					}
					//System.out.println("client : "+msg);
					messageArea.append("client : "+msg+"\n");
				
			}
		} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
			System.out.print("connection close Server");
			//System.out.print("connection close Client");
				}
		};
		new Thread(r1).start();
	}
	private void handleEvents() {
	messageInput.addKeyListener(new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==10) {
				//System.out.println("click enter botton");
				String contentToSend=messageInput.getText();
				messageArea.append("Me : "+contentToSend +"\n");
				out.println(contentToSend);
				out.flush();
				messageInput.setText("");
				messageInput.requestFocus();
			}
			
		}
		
	});
	}
	private void createGUI() {
		this.setTitle("Server Message[END]");
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// coding for  component
		heading.setFont(font);
		messageArea.setFont(font);
		messageInput.setFont(font);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		messageArea.setEditable(false);
		this.setLayout(new BorderLayout());
		//adding the  component to frame 
		this.add(heading,BorderLayout.NORTH);
		JScrollPane jScrollPane=new JScrollPane(messageArea);
		this.add(jScrollPane,BorderLayout.CENTER);
		this.add(messageInput,BorderLayout.SOUTH);
		
		
		this.setVisible(true);
		
	}
	public void startWriting() {
		Runnable r2=()->{
			System.out.println("writer starting .......");
			try {
			while(!socket.isClosed()) {
				
					BufferedReader br1 =new BufferedReader(new InputStreamReader(System.in));
					String content=br1.readLine();
					out.println(content);
					out.flush();
					if(content.equals("exit")){
						socket.close();
						break;
					}
				
			}
			} catch(Exception e) {
					//e.printStackTrace();
				System.out.print("connection close Server");
				}
		};
		new Thread(r2).start();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("server starting to .......");
		new Server();
	}

}
