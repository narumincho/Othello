import Data.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class ClientThread extends Thread {
  private Socket socket;
  private ObjectOutputStream objectOutputStream;
  private ClientView clientView;
  private BlackOrWhite myColor;

  private void sendDataToServer(@NotNull final String message) {
    try {
      System.out.println(message + "をサーバーに送ろうとしている");
      if (objectOutputStream == null) {
        objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
      }
      objectOutputStream.writeUTF(message);
      objectOutputStream.flush();

      System.out.println(message + "をサーバーに送った");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  ClientThread(@NotNull final Socket socket, ClientView clientView) {
    this.socket = socket;
    this.clientView = clientView;
  }

  private void sleep() {
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void longSleep() {
    try {
      Thread.sleep(35);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void longLongSleep() {
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // 通信状況を監視し，受信データによって動作する
  @Override
  public void run() {
    try {
      ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
      Data.FirstResponse firstResponse = (Data.FirstResponse) objectInputStream.readObject();
      if (firstResponse instanceof FirstResponseFull) {
        System.out.println("接続が多くて閉め出された");
        return;
      }
      myColor = ((FirstResponseOk) firstResponse).getBlackOrWhite();
      myTurn = colorToFirstTurn(myColor);
      System.out.println("自分の色=" + myColor);
      setIconFromBlackOrWhite(myColor);

      clientView.updatePalette(myColor, null);

      clientView.setBorad(BlackOrWhite.White, Skill.Normal, 3, 3);
      buttonArray[4][3].setIcon(blackIcon);
      buttonArray[4][4].setIcon(whiteIcon);
      buttonArray[3][4].setIcon(blackIcon);

      if (myTurn) {
        buttonArray[3][2].setIcon(canPutIcon);
        buttonArray[2][3].setIcon(canPutIcon);
        buttonArray[5][4].setIcon(canPutIcon);
        buttonArray[4][5].setIcon(canPutIcon);
      }
      System.out.println("ループ前に到達");

      while (true) {
        final String inputLine = objectInputStream.readUTF();
        System.out.println("サーバーから " + inputLine);
        final String[] inputTokens = inputLine.split(" ");
        final String command = inputTokens[0];

        if (command.equals("PASS")) {

          if (myTurn) {

            for (ImageIcon i : passIcon) {
              strMain.setIcon(i);
              longSleep();
            }
            strMain.setIcon(null);

            myTurn = false;
          } else {
            myTurn = true;
            if (countPutDownStone()) {
              String msg = "RESULT";
              endGame = true;
              sendDataToServer(msg);
            }
          }
        }

        if (command.equals("CANPUT")) {

          final int x = Integer.parseInt(inputTokens[1]);
          final int y = Integer.parseInt(inputTokens[2]);

          if (myTurn) {
            if (buttonArray[y][x].getIcon() == boardIcon) {
              buttonArray[y][x].setIcon(canPutIcon);
            }

          } else {
            for (int i = 0; i < MASU; i++) {
              for (int j = 0; j < MASU; j++) {
                if (buttonArray[i][j].getIcon() == canPutIcon) {
                  buttonArray[i][j].setIcon(boardIcon);
                }
              }
            }
          }
        }

        if (command.equals("FLIP")) {
          reversedSum = 0;

          int x = Integer.parseInt(inputTokens[1]);
          int y = Integer.parseInt(inputTokens[2]);

          if (myTurn) {
            // 送信元クライアントでの処理
            if (mySelected == 70) {
              buttonArray[y][x].setIcon(myIcon);
            } else if (mySelected == 71) {
              buttonArray[y][x].setIcon(myPutCatIcon);
            } else if (mySelected == 72) {
              buttonArray[y][x].setIcon(myPutBoarIcon);
            } else if (mySelected == 73) {
              buttonArray[y][x].setIcon(myPutHorseIcon);
            } else if (mySelected == 74) {
              buttonArray[y][x].setIcon(myPutBirdIcon);
            }
          } else {
            if (yourSelected == 70) {
              buttonArray[y][x].setIcon(yourIcon);
            } else if (yourSelected == 71) {
              buttonArray[y][x].setIcon(yourPutCatIcon);
            } else if (yourSelected == 72) {
              buttonArray[y][x].setIcon(yourPutBoarIcon);
            } else if (yourSelected == 73) {
              buttonArray[y][x].setIcon(yourPutHorseIcon);
            } else if (yourSelected == 74) {
              buttonArray[y][x].setIcon(yourPutBirdIcon);
            }
          }
        }

        if (command.equals("SELECT")) {
          final BlackOrWhite blackOrWhite = BlackOrWhite.valueOf(inputTokens[1]);
          int buttonNum = Integer.parseInt(inputTokens[2]);

          if (blackOrWhite == BlackOrWhite.Black) {
            blackChange = buttonNum;
          } else {
            whiteChange = buttonNum;
          }

          if (blackOrWhite == BlackOrWhite.Black) {
            if (myTurn) {
              mySelected = blackChange;
              yourSelected = whiteChange;
              selected = mySelected;
            } else {
              mySelected = whiteChange;
              yourSelected = blackChange;
              selected = yourSelected;
            }
          } else {
            if (myTurn) {
              mySelected = whiteChange;
              yourSelected = blackChange;
              selected = mySelected;
            } else {
              mySelected = blackChange;
              yourSelected = whiteChange;
              selected = yourSelected;
            }
          }
        }

        if (command.equals("REVERSE")) {
          final BlackOrWhite blackOrWhite = BlackOrWhite.valueOf(inputTokens[1]);
          int x = Integer.parseInt(inputTokens[2]);
          int y = Integer.parseInt(inputTokens[3]);
          if (blackOrWhite == BlackOrWhite.Black) {

            for (int i = reverseIcon.length - 1; i >= 0; i--) {
              buttonArray[y][x].setIcon(reverseIcon[i]);
              sleep();
            }

            buttonArray[y][x].setIcon(blackIcon);

          } else {
            for (ImageIcon i : reverseIcon) {
              buttonArray[y][x].setIcon(i);
              sleep();
            }

            buttonArray[y][x].setIcon(whiteIcon);
          }
          reversedSum++;
        }

        if (command.equals("REVERSED")) {
          Client.Counter counter;
          counter = countStone();
          double OffensivePower = 300;
          double attackMagnification = 1.15;
          double attack;
          int itDamege;
          if (reversedSum == 0) {
            attack = attackMagnification;
          } else {
            attack = Math.pow(attackMagnification, reversedSum);
          }
          int damege = (int) (OffensivePower * attack);

          damageLabel.setText(OffensivePower + "×" + attack + "=" + damege);

          if (selected == 70) {
            skillDamageLabel.setText("スキル未使用");
          }
          if (selected == 71) {
            for (ImageIcon i : catDoorIcon) {
              DoorLabel.setIcon(i);
              longSleep();

              if (i == catDoorIcon[15]) {
                strMain.setText("ひっくり返した駒の枚数");
                strMain2.setText("×300のダメージを与える");
                longLongSleep();
              }
            }
            strMain2.setText(null);
            strMain.setText(null);
            itDamege = 300 * reversedSum;
            damege += itDamege;
            skillDamageLabel.setText("一度に返した枚数" + reversedSum + "×" + 300 + "=" + itDamege);
          }

          if (selected == 72) {
            skillDamageLabel.setText("イノシシ+1");
            for (ImageIcon i : boarDoorIcon) {
              DoorLabel.setIcon(i);
              longSleep();

              if (i == boarDoorIcon[15]) {
                strMain.setText("盤面で表になっている間、毎");
                strMain2.setText("ターン300のダメージを与える");
                longLongSleep();
              }
            }
            strMain2.setText(null);
            strMain.setText(null);
          }

          int Poison;
          int PoisonDamage;
          if (myTurn) {
            Poison = counter.getMyBoarCount();
            PoisonDamage = Poison * 300;
          } else {
            Poison = counter.getYourBoarCount();
            PoisonDamage = Poison * 300;
          }
          poisonDamageLabel.setText("イノシシの個数" + Poison + "×" + 300 + "=" + PoisonDamage);
          damege += PoisonDamage; // 72 毒

          int lifeBurst;

          if (selected == 73) {
            if (myTurn) {
              if (myColor == BlackOrWhite.Black) {
                lifeBurst = (Player.maxHp - player1_hp) / 100;
              } else {
                lifeBurst = (Player.maxHp - player0_hp) / 100;
              }
            } else {
              if (myColor == BlackOrWhite.Black) {
                lifeBurst = (Player.maxHp - enemy1_hp) / 100;
              } else {
                lifeBurst = (Player.maxHp - enemy0_hp) / 100;
              }
            }
            itDamege = lifeBurst * 60;
            skillDamageLabel.setText("減少分割合　" + lifeBurst + "%×" + 60 + "=" + itDamege);
            damege += itDamege;

            for (ImageIcon i : horseDoorIcon) {
              DoorLabel.setIcon(i);
              longSleep();

              if (i == horseDoorIcon[15]) {
                strMain.setText("自分のHPが減少する程");
                strMain2.setText("与えるダメージが上昇する");
                longLongSleep();
              }
            }
            strMain2.setText(null);
            strMain.setText(null);
          }

          if (selected == 74) {
            int pieceSum;
            if (myTurn) {
              pieceSum =
                  counter.getYourIconCount()
                      + counter.getYourCatCount()
                      + counter.getYourBoarCount()
                      + counter.getYourHorseCount()
                      + counter.getYourBirdCount();
            } else {
              pieceSum =
                  counter.getMyIconCount()
                      + counter.getMyCatCount()
                      + counter.getMyBoarCount()
                      + counter.getMyHorseCount()
                      + counter.getMyBirdCount();
            }
            itDamege = pieceSum * 80;
            skillDamageLabel.setText("相手の駒の総数" + pieceSum + "×" + 80 + "=" + itDamege);
            damege += itDamege;

            for (ImageIcon i : birdDoorIcon) {
              DoorLabel.setIcon(i);
              longSleep();

              if (i == birdDoorIcon[15]) {
                strMain.setText("ひっくり返した後の、");
                strMain2.setText("相手の駒数×80のダメージを与える");
                longLongSleep();
              }
            }
            strMain2.setText(null);
            strMain.setText(null);
          }

          attackSumLabel.setText("合計ダメージ＝" + damege);

          if (myTurn) {
            if (myColor == BlackOrWhite.Black) {
              enemy1_hp -= damege;
              String strEnemy1_hp = String.valueOf(enemy1_hp);
              enemy_HP.setText(strEnemy1_hp);

              enemy_HPGAUGE.setBounds(0, 55, (int) (maxHpGaugeWidth * enemy1_hp), 10);
            } else {
              enemy0_hp -= damege;
              String strEnemy0_hp = String.valueOf(enemy0_hp);
              enemy_HP.setText(strEnemy0_hp);

              enemy_HPGAUGE.setBounds(0, 55, (int) (maxHpGaugeWidth * enemy0_hp), 10);
            }
            myTurn = false;
            for (int i = 0; i < MASU; i++) {
              for (int j = 0; j < MASU; j++) {
                if (buttonArray[i][j].getIcon() == canPutIcon) {
                  buttonArray[i][j].setIcon(boardIcon);
                }
              }
            }
          } else {
            if (myColor == BlackOrWhite.Black) {
              player1_hp -= damege;
              String strPlayer1_hp = String.valueOf(player1_hp);
              playerHpLabel.setText(strPlayer1_hp);

              player_HPGAUGE.setBounds(0, 5, (int) (maxHpGaugeWidth * player1_hp), 10);
            } else {
              player0_hp -= damege;
              String strPlayer0_hp = String.valueOf(player0_hp);
              playerHpLabel.setText(strPlayer0_hp);
              player_HPGAUGE.setBounds(0, 5, (int) (maxHpGaugeWidth * player0_hp), 10);
            }

            if (hpCheckerLessThanZero()) {
              String msg = "RESULT";
              endGame = true;
              sendDataToServer(msg);
            }

            if (!endGame) {
              myTurn = true;
              countPutDownStone();

              for (ImageIcon i : yourturnIcon) {
                strMain.setIcon(i);
                longSleep();
              }

              strMain.setIcon(null);
            }
          }
          System.out.println(endGame);
        }

        if (command.equals("RESULT")) {
          break;
        }
      }
      if (myIcon == blackIcon) {
        if (player1_hp < 0) {
          for (ImageIcon i : youloseIcon) {
            strMain.setIcon(i);
            longSleep();
          }
        } else {
          for (ImageIcon i : youwinIcon) {
            strMain.setIcon(i);
            longSleep();
          }
        }
      } else {
        if (player0_hp < 0) {
          for (ImageIcon i : youloseIcon) {
            strMain.setIcon(i);
            longSleep();
          }
        } else {
          for (ImageIcon i : youwinIcon) {
            strMain.setIcon(i);
            longSleep();
          }
        }
        socket.close();
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Contract(pure = true)
  private boolean colorToFirstTurn(BlackOrWhite blackOrWhite) {
    return blackOrWhite == BlackOrWhite.Black;
  }

  public void mouseEntered(Skill skill) {
    pieceDescription.setText(this.skill.getDescription());
    pieceDescription.setOpaque(true);
  }

  public void mouseExited(Skill skill) {
    pieceDescription.setText(null);
    pieceDescription.setOpaque(false);
  }

  public void mouseClicked(final int x, final int y) {
    System.out.println("(" + x + "," + y + ")クリック");
    if (canPutDown(x, y)) {
      System.out.println("canPutDownでTrueになった" + x + y);
      sendDataToServer("FLIP" + " " + x + " " + y);
      reverse(x, y);
      sendDataToServer("REVERSED"); // 送信データをバッファに書き出す
    } else {
      System.out.println("そこには配置できません");
    }
  }

  public void mouseClicked(Skill skill) {
    this.clientView.updatePalette(skill);
    sendDataToServer("SELECT" + " " + this.myColor + " " + skill);
  }
}
