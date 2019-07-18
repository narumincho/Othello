package Data;

import org.jetbrains.annotations.Contract;

public class Player {
  private int hp;
  public static final int maxHp = 10000;

  @Contract(pure = true)
  public Player() {
    this.hp = 10000;
  }

  public int getHp() {
    return this.hp;
  }

  public int getMaxHp() {
    return 10000;
  }
}
