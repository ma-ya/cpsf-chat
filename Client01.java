import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client01 {

  public static final String HOST = "localhost";
	public static final int PORT = 10389;

	public static void main(String[] args) {
		try {
			Socket sock = new Socket(HOST, PORT); // クライアントソケットの作成
			Scanner sc = new Scanner(sock.getInputStream()); // サーバからのデータ受け取り準備
			Scanner in = new Scanner(System.in);
			PrintWriter out = new PrintWriter(sock.getOutputStream(), true); // サーバへのデータ送信

			while (sc.hasNext()) {
				String ss = sc.nextLine();
				System.out.println("サーバ：" + ss);
				if (ss.equals("切断しました")) {
					break;
				}
				ss = in.nextLine();
				out.println(ss);
			}
			sock.close(); // ソケットを閉じる
		} catch (Exception e) {
			System.err.println(e);
		}

	}
}
