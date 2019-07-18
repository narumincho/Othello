package Data;

import org.jetbrains.annotations.Contract;

public enum Skill {
  Normal("normal", "普通の駒"),
  Cat("cat", "ひっくり返した駒の枚数×300のダメージを与える"),
  Boar("boar", "盤面で表になっている間、毎ターン300のダメージを与える"),
  Horse("horse", "自分のHPが減少する程ダメージが上昇する"),
  Bird("bird", "ひっくり返した後の、相手の駒数×80のダメージを与える");

  private String fileName;
  private String description;

  @Contract(pure = true)
  Skill(final String fileName, final String description) {
    this.fileName = fileName;
    this.description = description;
  }

  @Contract(pure = true)
  public String getFileName() {
    return this.fileName;
  }

  @Contract(pure = true)
  public String getDescription() {
    return description;
  }
}
