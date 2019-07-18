import Data.FirstResponseFull;
import Data.FirstResponseOk;
import Data.BlackOrWhite;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Server {

  private static ServerThread whiteThread;
  private static ServerThread blackThread;

  public static void main(String[] args) throws IOException {
    System.out.println("The server has launched!");
    ServerSocket server = new ServerSocket(10000);
    while (true) {
      final Socket socket = server.accept(); // wait connection
      if (blackThread == null) {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(new FirstResponseOk(BlackOrWhite.Black));
        outputStream.flush();
        blackThread = new ServerThread(socket, outputStream, BlackOrWhite.Black);
        blackThread.start();
        System.out.println("黒と接続した");
        continue;
      }
      if (whiteThread == null) {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(new FirstResponseOk(BlackOrWhite.White));
        outputStream.flush();
        whiteThread = new ServerThread(socket, outputStream, BlackOrWhite.White);
        whiteThread.start();
        System.out.println("白と接続した");
        continue;
      }
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeObject(new FirstResponseFull());
      outputStream.flush();
      System.out.println("3つ以上のクライアントと接続した");
    }
  }

  /** 送られた来たメッセージを接続している全員に送る */
  static void SendAll(@NotNull final String message, @NotNull final BlackOrWhite blackOrWhite) {
    if (whiteThread != null) {
      whiteThread.sendMessage(message);
    }
    if (blackThread != null) {
      blackThread.sendMessage(message);
    }
    System.out.println("Send messages(=" + message + ") from " + blackOrWhite);
  }

  /**
   * 接続が切れたことをスレッドから受け取ったら
   *
   * @param blackOrWhite 接続が切れた色
   */
  static void disconnect(@NotNull final BlackOrWhite blackOrWhite) {
    System.out.println(blackOrWhite + "と接続が切れました");
    if (blackOrWhite == BlackOrWhite.Black) {
      blackThread = null;
    } else {
      whiteThread = null;
    }
  }
}
