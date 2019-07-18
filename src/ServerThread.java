import Data.BlackOrWhite;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;

/** 各クライアントからリクエストを受け取るスレッド */
class ServerThread extends Thread {
  private Socket socket;
  private ObjectOutputStream outputStream;
  private BlackOrWhite blackOrWhite;

  ServerThread(
      @NotNull final Socket socket,
      @NotNull final ObjectOutputStream outputStream,
      @NotNull final BlackOrWhite blackOrWhite) {
    this.socket = socket;
    this.outputStream = outputStream;
    this.blackOrWhite = blackOrWhite;
  }

  @Override
  public void run() {
    try {
      final ObjectInputStream inputStream = new ObjectInputStream(this.socket.getInputStream());
      while (true) {
        System.out.println(this.blackOrWhite + "クライアントからのリクエスト待ち");
        final String messageFromClient = inputStream.readUTF();

        System.out.println(this.blackOrWhite + "クライアントから" + messageFromClient + "がきた");
        Server.SendAll(messageFromClient, blackOrWhite); // サーバに来たメッセージは接続しているクライアント全員に配る
      }
    } catch (IOException e) {
      // 接続が切れたことを呼び出し元に伝える
      Server.disconnect(this.blackOrWhite);
    }
  }

  public void sendMessage(@NotNull final String message) {
    try {
      this.outputStream.writeUTF(message);
      this.outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
