package Data;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.HashMap;

public class Stone {
  private static HashMap<Skill, ImageIcon> blackBoardImage = new HashMap<>();
  private static HashMap<Skill, ImageIcon> whiteBoardImage = new HashMap<>();
  private static HashMap<Skill, ImageIcon> blackPaletteImage = new HashMap<>();
  private static HashMap<Skill, ImageIcon> whitePaletteImage = new HashMap<>();
  private static HashMap<Skill, ImageIcon> blackPaletteSelectedImage = new HashMap<>();
  private static HashMap<Skill, ImageIcon> whitePaletteSelectedImage = new HashMap<>();

  public static @NotNull ImageIcon boardImage(
      @NotNull final BlackOrWhite blackOrWhite, @NotNull final Skill skill) {
    return getImageIcon(blackBoardImage, whiteBoardImage, skill, blackOrWhite, "board");
  }

  public static @NotNull ImageIcon paletteImage(
      @NotNull BlackOrWhite blackOrWhite, @NotNull Skill skill) {
    return getImageIcon(blackPaletteImage, whitePaletteImage, skill, blackOrWhite, "palette");
  }

  public static @NotNull ImageIcon paletteSelectedImage(
      @NotNull BlackOrWhite blackOrWhite, @NotNull Skill skill) {
    return getImageIcon(
        blackPaletteSelectedImage,
        whitePaletteSelectedImage,
        skill,
        blackOrWhite,
        "palette-selected");
  }

  private static @NotNull ImageIcon getImageIcon(
      @NotNull HashMap<Skill, ImageIcon> blackImageIconHashMap,
      @NotNull HashMap<Skill, ImageIcon> whiteImageIconHashMap,
      @NotNull Skill skill,
      @NotNull BlackOrWhite blackOrWhite,
      @NotNull String fileName) {

    if (blackOrWhite == BlackOrWhite.Black) {
      final ImageIcon imageFromCache = blackImageIconHashMap.get(skill);
      if (imageFromCache != null) {
        return imageFromCache;
      }
      final ImageIcon imageFromFile =
          new ImageIcon(
              "../assets/"
                  + skill.getFileName()
                  + "/"
                  + fileName
                  + "-"
                  + blackOrWhite.getFileName()
                  + ".jpg");
      blackImageIconHashMap.put(skill, imageFromFile);
      return imageFromFile;
    }
    final ImageIcon imageFromCache = whiteImageIconHashMap.get(skill);
    if (imageFromCache != null) {
      return imageFromCache;
    }
    final ImageIcon imageFromFile =
        new ImageIcon(
            "../assets/"
                + skill.getFileName()
                + "/"
                + fileName
                + "-"
                + blackOrWhite.getFileName()
                + ".jpg");
    whiteImageIconHashMap.put(skill, imageFromFile);
    return imageFromFile;
  }
}
