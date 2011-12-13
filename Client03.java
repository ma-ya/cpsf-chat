import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Client03 extends JFrame implements Runnable {

  public static final String HOST = "localhost";
	public static final int PORT = 10388;
	private JTextField tf;
	private JTextArea ta;
	private JScrollPane sp;
	private JPanel pn;

	private Socket sock;
	private BufferedReader br;
	private PrintWriter out;

	public static void main(String[] args) {

		Client03 c3 = new Client03();

	}

	public Client03() {

		tf = new JTextField();
		ta = new JTextArea();
		sp = new JScrollPane(ta);
		pn = new JPanel();

		// super("チャットルーム");
		add(tf, BorderLayout.SOUTH);
		add(sp, BorderLayout.CENTER);
		add(pn, BorderLayout.NORTH);
		tf.addActionListener(new SampleActionListener());

		setSize(500, 400);
		setVisible(true);

		Thread th = new Thread(this);
		th.start();
	}

	public void run() {
			try {
				sock = new Socket(HOST, PORT); // クライアントソケットの作成
				br = new BufferedReader(new InputStreamReader(sock.getInputStream())); // サーバからのデータ受け取り準備
				out = new PrintWriter(sock.getOutputStream(), true); // サーバへのデータ送信

				while (true) {
					try {
						String ss = br.readLine();
						ta.append(ss + "¥n");
						
					} catch (Exception e) {
						System.out.println(e);

					}
				}

			 } catch (Exception e) {
				System.out.println(e);
			}
		}
	

	class SampleActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				out = new PrintWriter(sock.getOutputStream(), true); // サーバへのデータ送信
				
				String ss = tf.getText();
				//out.flush();
				tf.setText("");
				
				out.println(ss);
			} catch (Exception ex) {
				System.out.println(e);
			}
		}
		
		class SampleWindowListener extends WindowAdapter {
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		}
	}
}
