import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;

class Thread03 extends Thread {

  public static final String HOST = "localhost"; // ホストを指定
	public static final int PORT = 10389; // ポート番号を指定
	Socket client;
	int number;
	static Vector threads; // 現在動作中のスレッドの集合

	public Thread03(Socket s, int n) {
		super(); // Threadクラスのコンストラクタを呼ぶ
		client = s;
		number = n;
		if (threads == null) {
		    threads = new Vector(); // スレッドの集合の初期化
		}
		threads.add(this); // スレッドの集合に自分を追加
	}

	public void run() {
		try {
			PrintWriter out = new PrintWriter(client.getOutputStream(), true); // 出力ストリームの獲得
			Scanner sc = new Scanner(client.getInputStream());

			out.println("これはNo." + number + "です");

			while (sc.hasNextLine()) {
				String ss = sc.nextLine();
				System.out.println("No." + number + "：" + ss);
				if (ss.equals("out")) { // outと入力された場合に
					break; // 切断する
				}
				out.println(ss);
				out.flush();
			}
			out.println("切断しました");
			System.out.println("切断しました");
			client.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void talk(String ss) {
		// スレッドの集合のそれぞれについて…
		for (int i = 0; i < threads.size(); i++) {
			Thread03 t = (Thread03) threads.get(i);
			if (t.isAlive()) { // そのスレッドが動作していたら
				t.talkone(this, ss); // そのスレッドにメッセージを送信
			}
		}
		System.err.println("No." + number + ":" + ss);
	}

	// そのスレッドにメッセージを送信
	public void talkone(Thread03 talker, String ss) {
		try {
			PrintWriter out = new PrintWriter(client.getOutputStream(),true);

			if (talker == this) {
				out.print("");
			} else { // 他人からのメッセージ
				out.println("No." + number + "]" + ss);
			}
			out.flush();
			
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}

	class Server03 {
		public static final int PORT = 10389; // ポート番号を指定
		public static void main(String[] args) {
			int n = 1;

			try {
				ServerSocket server = new ServerSocket(PORT); // サーバソケットを作成
				Socket client;
				System.out.println("待機中です");
				while (true) {
					try{
					client = server.accept(); // クライアントからの接続を待つ				
					System.out.println("No." + n + "と接続しました");
					new Thread03(client, n).start(); //接続されるたびに
					n++; // 数が増える
					} catch (IOException e) {
					}
				}
			} catch (IOException e) {
				System.err.println(e);
			}
			}
		}
