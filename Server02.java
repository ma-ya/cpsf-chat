import java.io.*;
import java.net.*;
import java.util.Scanner;

class Thread02 extends Thread {

  Socket client;
	int number;

	public Thread02(Socket s, int n) {
		client = s;
		number = n;
	}

	public void run() {
		try {
			PrintWriter out = new PrintWriter(client.getOutputStream(), true); // 出力ストリームの獲得
			Scanner sc = new Scanner(client.getInputStream());

			out.println("これはNo." + number + "です");

			while (sc.hasNextLine()) {
				String ss = sc.nextLine();
				System.out.println("クライアントNo." + number + "：" + ss);
				if (ss.equals("out")) { // outと入力された場合に
					break; // 切断する
				}
				out.println(ss);
			}
			out.println("切断しました");
			System.out.println("切断しました");
			client.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}

class Server02 {
	public static final String HOST = "localhost"; // ホストを指定
	public static final int PORT = 10389; // ポート番号を指定
	
	public static void main(String[] args) {
		int n = 1;

		try {
			ServerSocket server = new ServerSocket(PORT); // サーバソケットを作成
			System.out.println("待機中です");
			while (true) {
				Socket client = server.accept(); // クライアントからの接続を待つ
				System.out.println("No." + n + "と接続しました");
				new Thread02(client, n).start(); //接続されるたびに
				n++; //数が増える
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
