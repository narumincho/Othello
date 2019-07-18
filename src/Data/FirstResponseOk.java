package Data;

import java.io.Serializable;

public class FirstResponseOk extends FirstResponse implements Serializable {
  private BlackOrWhite blackOrWhite;

  public FirstResponseOk(final BlackOrWhite blackOrWhite) {
    this.blackOrWhite = blackOrWhite;
  }

  public BlackOrWhite getBlackOrWhite() {
    return blackOrWhite;
  }
}
