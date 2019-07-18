import Data.FirstResponseFull;
import Data.FirstResponseOk;
import Data.BlackOrWhite;
import Data.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;

public class Client {
  public static void main(String[] args) {
    Client client = new Client();
  }

  private Client() {
    ClientView clientView = new ClientView();
    try {
      ClientThread clientThread = new ClientThread(new Socket("localhost", 10000));
      clientView.setThread(clientThread);
      clientThread.start();

    } catch (UnknownHostException e) {
      System.err.println("ホストの IP アドレスが判定できません: " + e);
    } catch (IOException e) {
      System.err.println("エラーが発生しました: " + e);
    }
  }
  // ----------------------------------------------------------------------------------------------

  private void longSleep() {
    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void setIconFromBlackOrWhite(@NotNull BlackOrWhite blackOrWhite) {
    if (blackOrWhite == BlackOrWhite.Black) {
      myIcon = blackIcon;
      myNoSelectedIcon = black_noSelectedIcon;
      mySelectedIcon = black_selectedIcon;
      myNoSelectdCatIcon = cat_black_noSelectedIcon;
      mySelectedCatIcon = cat_black_selectedIcon;
      myNoSelectedBoarIcon = boar_black_noSelectedIcon;
      mySelectedBoarIcon = boar_black_selectedIcon;
      myNoSelectedHorseIcon = horse_black_noSelectedIcon;
      mySelectedHorseIcon = horse_black_selectedIcon;
      myNoSelectedBirdIcon = bird_black_noSelectedIcon;
      mySelectedBirdIcon = bird_black_selectedIcon;
      myPutCatIcon = cat_black_putIcon;
      myPutBoarIcon = boar_black_putIcon;
      myPutHorseIcon = horse_black_putIcon;
      myPutBirdIcon = bird_black_putIcon;

      yourIcon = whiteIcon;
      yourPutCatIcon = cat_white_putIcon;
      yourPutBoarIcon = boar_white_putIcon;
      yourPutHorseIcon = horse_white_putIcon;
      yourPutBirdIcon = bird_white_putIcon;

      for (ImageIcon imageIcon : yourturnIcon) {
        strMain.setIcon(imageIcon);
        longSleep();
      }

      strMain.setIcon(null);

    } else {
      myIcon = whiteIcon;
      myNoSelectedIcon = white_noSelectedIcon;
      mySelectedIcon = white_selectedIcon;
      myNoSelectdCatIcon = cat_white_noSelectedIcon;
      mySelectedCatIcon = cat_white_selectedIcon;
      myNoSelectedBoarIcon = boar_white_noSelectedIcon;
      mySelectedBoarIcon = boar_white_selectedIcon;
      myNoSelectedHorseIcon = horse_white_noSelectedIcon;
      mySelectedHorseIcon = horse_white_selectedIcon;
      myNoSelectedBirdIcon = bird_white_noSelectedIcon;
      mySelectedBirdIcon = bird_white_selectedIcon;
      myPutCatIcon = cat_white_putIcon;
      myPutBoarIcon = boar_white_putIcon;
      myPutHorseIcon = horse_white_putIcon;
      myPutBirdIcon = bird_white_putIcon;

      yourIcon = blackIcon;
      yourPutCatIcon = cat_black_putIcon;
      yourPutBoarIcon = boar_black_putIcon;
      yourPutHorseIcon = horse_black_putIcon;
      yourPutBirdIcon = bird_black_putIcon;
    }
  }

  private Counter countStone() {
    Counter counter = new Counter();

    for (int y = 0; y < MASU; y++) {
      for (int x = 0; x < MASU; x++) {
        if (buttonArray[y][x].getIcon() == myIcon) counter.myIconCount++;
        if (buttonArray[y][x].getIcon() == yourIcon) counter.yourIconCount++;
        if (buttonArray[y][x].getIcon() == myPutCatIcon) {
          counter.myCatCount++;
        }
        if (buttonArray[y][x].getIcon() == myPutBoarIcon) {
          counter.myBoarCount++;
        }
        if (buttonArray[y][x].getIcon() == myPutHorseIcon) {
          counter.myHorseCount++;
        }
        if (buttonArray[y][x].getIcon() == myPutBirdIcon) {
          counter.myBirdCount++;
        }
        if (buttonArray[y][x].getIcon() == yourPutCatIcon) {
          counter.yourCatCount++;
        }
        if (buttonArray[y][x].getIcon() == yourPutBoarIcon) {
          counter.yourBoarCount++;
        }
        if (buttonArray[y][x].getIcon() == yourPutHorseIcon) {
          counter.yourHorseCount++;
        }
        if (buttonArray[y][x].getIcon() == yourPutBirdIcon) {
          counter.yourBirdCount++;
        }
      }
    }
    return counter;
  }

  private class Counter {
    public int myIconCount;
    public int yourIconCount;
    public int myCatCount;
    public int myBoarCount;
    public int myHorseCount;
    public int myBirdCount;
    public int yourCatCount;
    public int yourBoarCount;
    public int yourHorseCount;
    public int yourBirdCount;

    public int getMyIconCount() {
      return myIconCount;
    }

    public int getYourIconCount() {
      return yourIconCount;
    }

    public int getMyCatCount() {
      return myCatCount;
    }

    public int getMyBoarCount() {
      return myBoarCount;
    }

    public int getMyHorseCount() {
      return myHorseCount;
    }

    public int getMyBirdCount() {
      return myBirdCount;
    }

    public int getYourCatCount() {
      return yourCatCount;
    }

    public int getYourBoarCount() {
      return yourBoarCount;
    }

    public int getYourHorseCount() {
      return yourHorseCount;
    }

    public int getYourBirdCount() {
      return yourBirdCount;
    }
  }

  private void reverse(int x, int y) {
    // ひっくり返せる石がある方向はすべてひっくり返す
    if (canPutDown(x, y, 1, 0)) {
      reverse(x, y, 1, 0);
    }
    if (canPutDown(x, y, 0, 1)) {
      reverse(x, y, 0, 1);
    }
    if (canPutDown(x, y, -1, 0)) {
      reverse(x, y, -1, 0);
    }
    if (canPutDown(x, y, 0, -1)) {
      reverse(x, y, 0, -1);
    }
    if (canPutDown(x, y, 1, 1)) {
      reverse(x, y, 1, 1);
    }
    if (canPutDown(x, y, -1, -1)) {
      reverse(x, y, -1, -1);
    }
    if (canPutDown(x, y, 1, -1)) {
      reverse(x, y, 1, -1);
    }
    if (canPutDown(x, y, -1, 1)) {
      reverse(x, y, -1, 1);
    }
  }

  private void reverse(int x, int y, final int vecX, final int vecY) {
    // 相手の石がある間ひっくり返し続ける
    // (x,y)に打てるのは確認済みなので相手の石は必ず
    x += vecX;
    y += vecY;
    while (buttonArray[y][x].getIcon() != myIcon
        && buttonArray[y][x].getIcon() != myPutHorseIcon
        && buttonArray[y][x].getIcon() != myPutBirdIcon
        && buttonArray[y][x].getIcon() != myPutCatIcon
        && buttonArray[y][x].getIcon() != myPutBoarIcon) {
      // ひっくり返す
      sendDataToServer("REVERSE" + " " + myColor + " " + x + " " + y);
      x += vecX;
      y += vecY;
    }
  }

  private boolean canPutDown(final int x, final int y) {
    // すでに石が打たれてたら打てない
    Icon icon = buttonArray[y][x].getIcon();
    if (icon == myIcon
        || icon == myPutHorseIcon
        || icon == myPutBirdIcon
        || icon == myPutCatIcon
        || icon == myPutBoarIcon
        || icon == yourIcon
        || icon == yourPutHorseIcon
        || icon == yourPutBirdIcon
        || icon == yourPutCatIcon
        || icon == yourPutBoarIcon) {
      return false;
    }

    // 8方向のうち一箇所でもひっくり返せればこの場所に打てる
    // ひっくり返せるかどうかはもう1つのcanPutDownで調べる
    return canPutDown(x, y, 1, -1) // 右上
        || canPutDown(x, y, 1, 0) // 右
        || canPutDown(x, y, 1, 1) // 右下
        || canPutDown(x, y, 0, 1) // 下
        || canPutDown(x, y, -1, 1) // 左下
        || canPutDown(x, y, -1, 0) // 左
        || canPutDown(x, y, -1, -1) // 左上
        || canPutDown(x, y, 0, -1); // 上
  }

  private boolean canPutDown(int x, int y, final int vecX, final int vecY) {

    // 隣の場所へ。どの隣かは(vecX, vecY)が決める。
    x += vecX;
    y += vecY;
    // 盤面外だったら打てない
    if (x < 0 || x >= MASU || y < 0 || y >= MASU) return false;
    // 隣が自分の石の場合は打てない
    if (buttonArray[y][x].getIcon() == myIcon
        || buttonArray[y][x].getIcon() == myPutHorseIcon
        || buttonArray[y][x].getIcon() == myPutBirdIcon
        || buttonArray[y][x].getIcon() == myPutCatIcon
        || buttonArray[y][x].getIcon() == myPutBoarIcon) return false;
    // 隣が空白の場合は打てない
    if (buttonArray[y][x].getIcon() == boardIcon || buttonArray[y][x].getIcon() == canPutIcon)
      return false;
    // さらに隣を調べていく

    x += vecX;
    y += vecY;
    // となりに石がある間ループがまわる

    int count = 0;
    int canPutX, canPutY;
    while (x >= 0 && x < MASU && y >= 0 && y < MASU) {

      count++;
      // 空白が見つかったら打てない（1つもはさめないから)
      if (buttonArray[y][x].getIcon() == boardIcon || buttonArray[y][x].getIcon() == canPutIcon)
        return false;
      // 自分の石があればはさめるので打てる
      if (buttonArray[y][x].getIcon() == myIcon
          || buttonArray[y][x].getIcon() == myPutHorseIcon
          || buttonArray[y][x].getIcon() == myPutBirdIcon
          || buttonArray[y][x].getIcon() == myPutCatIcon
          || buttonArray[y][x].getIcon() == myPutBoarIcon) {
        canPutX = x - (vecX * (count + 1));
        canPutY = y - (vecY * (count + 1));
        String msg = "CANPUT" + " " + canPutX + " " + canPutY;
        sendDataToServer(msg);
        return true;
      }
      x += vecX;
      y += vecY;
    }
    // 相手の石しかない場合はいずれ盤面の外にでてしまうのでこのfalse
    return false;
  }

  private boolean countPutDownStone() {
    int count = 0;

    for (int y = 0; y < MASU; y++) {
      for (int x = 0; x < MASU; x++) {
        if (canPutDown(x, y)) {
          count++;
        }
      }
    }
    if (count == 0) {
      sendDataToServer("PASS"); // 送信データをバッファに書き出す
      return true;
    }
    return false;
  }

  @Contract(pure = true)
  private boolean hpCheckerLessThanZero() {
    return player0_hp < 0 || player1_hp < 0;
  }
}
