import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class Server {

  private static HashMap<Long, ServerThread> serverThreads = new HashMap<>();

  /** 送られた来たメッセージを接続している全員に送る */
  static void SendAll(final String message, final long sourceId) {
    serverThreads.forEach(
        (id, thread) -> {
          thread.sendMessage(message);
          System.out.println("Send messages from " + sourceId + " to client No." + id);
        });
  }

  // フラグの設定を行う
  static void disconnected(final long n) {
    serverThreads.remove(n);
  }

  public static void main(String[] args) throws IOException {
    System.out.println("The server has launched!");
    ServerSocket server = new ServerSocket(10000); // 10000番ポートを利用する
    while (true) {
      final Socket socket = server.accept();
      final ServerThread thread =
          new ServerThread(
              new ObjectInputStream(socket.getInputStream()),
              new PrintWriter(socket.getOutputStream(), true));
      thread.start();

      System.out.println("Accept client ID=" + thread.getId());
      Server.serverThreads.put(thread.getId(), thread);
    }
  }
}
