import Data.Skill;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClientPaletteMouseListener implements MouseListener {
  private Skill skill;
  private ClientThread clientThread;

  ClientPaletteMouseListener(Skill skill, ClientThread clientThread) {
    this.skill = skill;
    this.clientThread = clientThread;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.clientThread.mouseClicked(this.skill);
  }

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {
    this.clientThread.mouseEntered(this.skill);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    this.clientThread.mouseExited(this.skill);
  }
}
