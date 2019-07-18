import java.io.ObjectInputStream;
import java.io.PrintWriter;

/** 各クライアントからリクエストを受け取るスレッド */
class ServerThread extends Thread {
  /** 管理する接続番号 */
  private ObjectInputStream objectInputStream;

  private PrintWriter printWriter;
  /** 接続者の名前 */
  private String name;

  ServerThread(final ObjectInputStream objectInputStream, final PrintWriter printWriter) {
    this.objectInputStream = objectInputStream;
    this.printWriter = printWriter;
  }

  @Override
  public void run() {
    try {
      printWriter.println(this.getId()); // 初回だけ呼ばれる

      this.name = objectInputStream.readUTF();

      while (true) { // 無限ループで，ソケットへの入力を監視する
        final String messageFromClient = objectInputStream.readUTF();
        if (messageFromClient.toUpperCase().equals("BYE")) {
          printWriter.println("Good bye!");
          return;
        }
        Server.SendAll(messageFromClient, this.getId()); // サーバに来たメッセージは接続しているクライアント全員に配る
      }
    } catch (Exception e) {
      // ここにプログラムが到達するときは，接続が切れたとき
      System.out.println("Disconnect from client No." + this.getId() + "(" + this.name + ")");
      Server.disconnected(this.getId()); // 接続が切れたのでフラグを下げる
    }
  }

  public void sendMessage(final String message) {
    printWriter.println(message);
  }
}
