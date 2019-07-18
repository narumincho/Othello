package Data;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;

public enum BlackOrWhite implements Serializable {
  Black("black"),
  White("white");

  private String fileName;

  @Contract(pure = true)
  BlackOrWhite(final String fileName) {
    this.fileName = fileName;
  }

  @Contract(pure = true)
  public String getFileName() {
    return this.fileName;
  }
}
