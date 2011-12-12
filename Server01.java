import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server01 {

  public static final String HOST = "localhost"; // ホストを指定
	public static final int PORT = 10389; // ポート番号を指定

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(PORT); // サーバソケットを作成
			System.out.println("待機中です");

			Socket client = server.accept(); // クライアントからの接続を待つ
			PrintWriter out = new PrintWriter(client.getOutputStream(), true); // 出力ストリームの獲得
			Scanner sc = new Scanner(client.getInputStream());

			out.println("接続しました");
			System.out.println("接続しました");

			while (sc.hasNextLine()) {
				String ss = sc.nextLine();
				System.out.println("クライアント：" + ss);
				if (ss.equals("out")) { // outと入力された場合に
					break; // 切断する
				}

				out.println(ss.toUpperCase()); // 大文字に変換して出力
			}
			out.println("切断しました");
			System.out.println("切断しました");
			client.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
