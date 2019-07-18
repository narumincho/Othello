import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClientBoardMouseListener implements MouseListener {
  private int x;
  private int y;
  private ClientThread clientThread;

  ClientBoardMouseListener(final int x, final int y, ClientThread clientThread) {
    this.x = x;
    this.y = y;
    this.clientThread = clientThread;
  }

  @Override
  public void mouseClicked(@NotNull MouseEvent mouseEvent) {
    this.clientThread.mouseClicked(this.x, this.y);
  }

  @Override
  public void mouseEntered(@NotNull MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}
}
