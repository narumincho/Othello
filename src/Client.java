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

public class Client extends JFrame implements MouseListener {

  private JLabel player_HPGAUGE;
  private JLabel playerHpLabel;

  private JButton othello_piece_A;
  private JButton othello_piece_B;
  private JButton othello_piece_C;
  private JButton othello_piece_D;
  private JButton othello_piece_Normal;

  private JLabel strMain;
  private JLabel strMain2;
  private JLabel pieceDescription;
  private JLabel DoorLabel;
  private JLabel damageLabel;
  private JLabel poisonDamageLabel;
  private JLabel skillDamageLabel;
  private JLabel attackSumLabel;

  private JButton[][] buttonArray;

  private JLabel enemy_HPGAUGE;
  private JLabel enemy_HP;
  private double maxHpGaugeWidth;

  private ImageIcon blackIcon, whiteIcon, boardIcon, canPutIcon;
  private ImageIcon white_selectedIcon;
  private ImageIcon black_selectedIcon;
  private ImageIcon white_noSelectedIcon;
  private ImageIcon black_noSelectedIcon;
  private ImageIcon cat_black_noSelectedIcon,
      cat_black_selectedIcon,
      cat_white_noSelectedIcon,
      cat_white_selectedIcon;
  private ImageIcon boar_black_noSelectedIcon,
      boar_black_selectedIcon,
      boar_white_noSelectedIcon,
      boar_white_selectedIcon;
  private ImageIcon horse_black_noSelectedIcon,
      horse_black_selectedIcon,
      horse_white_noSelectedIcon,
      horse_white_selectedIcon;
  private ImageIcon bird_black_noSelectedIcon,
      bird_black_selectedIcon,
      bird_white_noSelectedIcon,
      bird_white_selectedIcon;
  private ImageIcon cat_white_putIcon,
      boar_white_putIcon,
      horse_white_putIcon,
      bird_white_putIcon,
      cat_black_putIcon,
      boar_black_putIcon,
      horse_black_putIcon,
      bird_black_putIcon;

  private final ImageIcon[] reverseIcon = new ImageIcon[24];
  private final ImageIcon[] yourturnIcon = new ImageIcon[34];
  private final ImageIcon[] youwinIcon = new ImageIcon[34];
  private final ImageIcon[] youloseIcon = new ImageIcon[34];
  private final ImageIcon[] passIcon = new ImageIcon[35];

  private ImageIcon myIcon, yourIcon;
  private ImageIcon mySelectedIcon, myNoSelectedIcon;
  private ImageIcon myNoSelectdCatIcon, mySelectedCatIcon;
  private ImageIcon myNoSelectedBoarIcon, mySelectedBoarIcon;
  private ImageIcon myNoSelectedHorseIcon, mySelectedHorseIcon;
  private ImageIcon myNoSelectedBirdIcon, mySelectedBirdIcon;
  private ImageIcon myPutCatIcon,
      myPutBoarIcon,
      myPutHorseIcon,
      myPutBirdIcon,
      yourPutCatIcon,
      yourPutBoarIcon,
      yourPutHorseIcon,
      yourPutBirdIcon;

  private final ImageIcon[] catDoorIcon = new ImageIcon[37];
  private final ImageIcon[] boarDoorIcon = new ImageIcon[37];
  private final ImageIcon[] horseDoorIcon = new ImageIcon[37];
  private final ImageIcon[] birdDoorIcon = new ImageIcon[37];

  private BlackOrWhite myColor;
  private boolean myTurn;
  private final int MASU = 8;
  private int reversedSum;
  private boolean endGame = false;

  private int player0_hp;
  private int player1_hp;
  private int enemy0_hp;
  private int enemy1_hp;

  private int mySelected = 70;
  private int yourSelected = 70;
  private int blackChange = mySelected;
  private int whiteChange = yourSelected;

  private int selected;

  private Socket socket;
  private ObjectOutputStream objectOutputStream;

  public static void main(String[] args) {
    Client client = new Client();
    client.setVisible(true);
  }

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

  private Client() {
    // ウィンドウを作成する
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ウィンドウを閉じるときに，正しく閉じるように設定する
    setTitle("Client"); // ウィンドウのタイトルを設定する
    setSize(375, 550); // ウィンドウのサイズを設定する
    Container contentPane = getContentPane(); // フレームのペインを取得する

    // アイコンの設定
    whiteIcon = new ImageIcon("../assets/white.jpg");
    blackIcon = new ImageIcon("../assets/black.jpg");
    boardIcon = new ImageIcon("../assets/board.jpg");
    white_selectedIcon = new ImageIcon("../assets/animals/white_selected.jpg");
    black_selectedIcon = new ImageIcon("../assets/animals/black_selected.jpg");
    white_noSelectedIcon = new ImageIcon("../assets/animals/white_no_selected.jpg");
    black_noSelectedIcon = new ImageIcon("../assets/animals/black_no_selected.jpg");

    cat_white_noSelectedIcon = new ImageIcon("../assets/animals/cat_white_no_selected.jpg");
    cat_white_selectedIcon = new ImageIcon("../assets/animals/cat_white_selected.jpg");
    cat_black_noSelectedIcon = new ImageIcon("../assets/animals/cat_black_no_selected.jpg");
    cat_black_selectedIcon = new ImageIcon("../assets/animals/cat_black_selected.jpg");

    boar_white_noSelectedIcon = new ImageIcon("../assets/animals/boar_white_no_selected.jpg");
    boar_white_selectedIcon = new ImageIcon("../assets/animals/boar_white_selected.jpg");
    boar_black_noSelectedIcon = new ImageIcon("../assets/animals/boar_black_no_selected.jpg");
    boar_black_selectedIcon = new ImageIcon("../assets/animals/boar_black_selected.jpg");

    horse_white_noSelectedIcon = new ImageIcon("../assets/animals/horse_white_no_selected.jpg");
    horse_white_selectedIcon = new ImageIcon("../assets/animals/horse_white_selected.jpg");
    horse_black_noSelectedIcon = new ImageIcon("../assets/animals/horse_black_no_selected.jpg");
    horse_black_selectedIcon = new ImageIcon("../assets/animals/horse_black_selected.jpg");

    bird_white_noSelectedIcon = new ImageIcon("../assets/animals/bird_white_no_selected.jpg");
    bird_white_selectedIcon = new ImageIcon("../assets/animals/bird_white_selected.jpg");
    bird_black_noSelectedIcon = new ImageIcon("../assets/animals/bird_black_no_selected.jpg");
    bird_black_selectedIcon = new ImageIcon("../assets/animals/bird_black_selected.jpg");

    cat_white_putIcon = new ImageIcon("../assets/small_animals/cat_white_no_selected.jpg");
    cat_black_putIcon = new ImageIcon("../assets/small_animals/cat_black_no_selected.jpg");
    boar_white_putIcon = new ImageIcon("../assets/small_animals/boar_white_no_selected.jpg");
    boar_black_putIcon = new ImageIcon("../assets/small_animals/boar_black_no_selected.jpg");
    horse_white_putIcon = new ImageIcon("../assets/small_animals/horse_white_no_selected.jpg");
    horse_black_putIcon = new ImageIcon("../assets/small_animals/horse_black_no_selected.jpg");
    bird_white_putIcon = new ImageIcon("../assets/small_animals/bird_white_no_selected.jpg");
    bird_black_putIcon = new ImageIcon("../assets/small_animals/bird_black_no_selected.jpg");

    for (int i = 0; i < catDoorIcon.length; i++) {
      catDoorIcon[i] = new ImageIcon(String.format("../assets/cat_door/%02d.png", i));
    }
    for (int i = 0; i < boarDoorIcon.length; i++) {
      boarDoorIcon[i] = new ImageIcon(String.format("../assets/boar_door/%02d.png", i));
    }
    for (int i = 0; i < horseDoorIcon.length; i++) {
      horseDoorIcon[i] = new ImageIcon(String.format("../assets/horse_door/%02d.png", i));
    }
    for (int i = 0; i < birdDoorIcon.length; i++) {
      birdDoorIcon[i] = new ImageIcon(String.format("../assets/bird_door/%02d.png", i));
    }

    final ImageIcon hpGauge = new ImageIcon("../assets/hpguage/hp_green.jpg");
    canPutIcon = new ImageIcon("../assets/can-put-down.jpg");

    ImageIcon enemyBackgroundIcon = new ImageIcon("../assets/background/enemy.jpg");
    ImageIcon playerBackgroundIcon = new ImageIcon("../assets/background/player.jpg");

    for (int i = 0; i < reverseIcon.length; i++) {
      reverseIcon[i] = new ImageIcon(String.format("../assets/reverse/%02d.jpg", i));
    }

    for (int i = 0; i < yourturnIcon.length; i++) {
      yourturnIcon[i] = new ImageIcon(String.format("../assets/yourturn/%02d.png", i));
    }

    for (int i = 0; i < youwinIcon.length; i++) {
      youwinIcon[i] = new ImageIcon(String.format("../assets/youwin/%02d.png", i));
    }

    for (int i = 0; i < youloseIcon.length; i++) {
      youloseIcon[i] = new ImageIcon(String.format("../assets/youlose/%02d.png", i));
    }

    for (int i = 0; i < passIcon.length; i++) {
      passIcon[i] = new ImageIcon(String.format("../assets/pass/%02d.png", i));
    }

    contentPane.setLayout(null); // 自動レイアウトの設定を行わない

    float hpWidth = 375;
    Player player = new Player();
    maxHpGaugeWidth = hpWidth / player.getHp();

    player0_hp = player.getHp();
    player1_hp = player.getHp();
    Player enemy = new Player();
    enemy0_hp = enemy.getHp();
    enemy1_hp = enemy.getHp();

    // 敵
    JLayeredPane enemy_Panel = new JLayeredPane();
    enemy_Panel.setLayout(null);

    JLabel enemy_NAME = new JLabel("enemy");
    enemy_NAME.setPreferredSize(new Dimension(130, 80));
    enemy_NAME.setBounds(10, 5, 80, 20);

    //        enemyIcon = new ImageIcon("../assets/animal_uma_horse_30960-101x101.jpg");
    //
    //        enemy_Icon = new JLabel(enemyIcon);
    //        enemy_Icon.setBounds(0, 40, 100, 100);

    String enemy_maxhp = Integer.toString(enemy.getMaxHp());
    JLabel enemy_MAXHP = new JLabel("/" + (enemy_maxhp));
    enemy_MAXHP.setBounds(320, 55, 100, 10);

    String enemy_hp = String.valueOf(enemy.getHp());
    enemy_HP = new JLabel(enemy_hp);
    enemy_HP.setBounds(285, 55, 100, 10);

    enemy_HPGAUGE = new JLabel(hpGauge);
    int IntEnemy_hpWidth = (int) hpWidth;
    enemy_HPGAUGE.setBounds(0, 55, IntEnemy_hpWidth, 10);

    damageLabel = new JLabel();
    damageLabel.setBounds(80, -40, 375, 100);
    damageLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 11));
    damageLabel.setForeground(new Color(0, 0, 255));

    skillDamageLabel = new JLabel();
    skillDamageLabel.setBounds(80, -30, 375, 100);
    skillDamageLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 11));
    skillDamageLabel.setForeground(new Color(0, 0, 255));

    poisonDamageLabel = new JLabel();
    poisonDamageLabel.setBounds(80, -20, 375, 100);
    poisonDamageLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 11));
    poisonDamageLabel.setForeground(new Color(0, 0, 255));

    attackSumLabel = new JLabel();
    attackSumLabel.setBounds(80, -5, 375, 100);
    attackSumLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 11));
    attackSumLabel.setForeground(new Color(0, 0, 255));

    JLabel enemyBackground = new JLabel();
    enemyBackground.setBounds(0, 0, 375, 70);
    enemyBackground.setIcon(enemyBackgroundIcon);
    enemy_Panel.setLayer(enemyBackground, 0);

    enemy_Panel.add(enemy_HP);
    enemy_Panel.add(enemy_MAXHP);
    enemy_Panel.add(enemy_NAME);
    enemy_Panel.add(enemy_HPGAUGE);

    enemy_Panel.add(damageLabel);
    enemy_Panel.add(poisonDamageLabel);
    enemy_Panel.add(skillDamageLabel);
    enemy_Panel.add(attackSumLabel);

    enemy_Panel.add(enemyBackground);

    //  enemy_Panel.add(enemy_Icon);
    enemy_Panel.setBounds(0, 0, IntEnemy_hpWidth, 70);
    contentPane.add(enemy_Panel);

    JLayeredPane mainPane = new JLayeredPane();

    // ボタンの生成
    buttonArray = new JButton[MASU][MASU];

    for (int y = 0; y < MASU; y++) {
      for (int x = 0; x < MASU; x++) {
        buttonArray[y][x] = new JButton(boardIcon); // ボタンにアイコンを設定する
        buttonArray[y][x].setBounds(x * 45, y * 45, 45, 45); // ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
        mainPane.add(buttonArray[y][x]);
        mainPane.setLayer(buttonArray[y][x], 0);
        buttonArray[y][x].addMouseListener(this); // ボタンをマウスでさわったときに反応するようにする
        //
        // buttonArray[y][x].addMouseMotionListener(this);//ボタンをマウスで動かそうとしたときに反応するようにする
        buttonArray[y][x].setActionCommand(Integer.toString(y * MASU + x));
      }
    }

    // 文字表示

    strMain = new JLabel();
    strMain.setBounds(20, 20, 300, 300);
    strMain.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
    strMain.setForeground(new Color(255, 0, 255));
    mainPane.setLayer(strMain, 4);

    strMain2 = new JLabel();
    strMain2.setBounds(20, 40, 300, 300);
    strMain2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
    strMain2.setForeground(new Color(255, 0, 255));
    mainPane.setLayer(strMain2, 4);

    pieceDescription = new JLabel();
    pieceDescription.setBounds(0, 180, 375, 50);
    pieceDescription.setBackground(new Color(128, 128, 128, 128));
    pieceDescription.setForeground(new Color(0, 0, 255));
    mainPane.setLayer(pieceDescription, 2);

    DoorLabel = new JLabel();
    DoorLabel.setBounds(0, -10, 375, 375);
    mainPane.setLayer(DoorLabel, 3);

    mainPane.add(DoorLabel);
    mainPane.add(strMain);
    mainPane.add(strMain2);
    mainPane.add(pieceDescription);

    mainPane.setBounds(0, 70, 375, 360);
    contentPane.add(mainPane);

    // player
    JLayeredPane player_Panel = new JLayeredPane();
    player_Panel.setLayout(null);

    //        player_NAME = new JLabel(name);
    //        player_NAME.setPreferredSize(new Dimension(130,80));
    //        player_NAME.setBounds(5,10,80,20);

    String playerMaxHpString = Integer.toString(player.getMaxHp());
    JLabel playerMaxHpLabel = new JLabel("/" + (playerMaxHpString));
    playerMaxHpLabel.setBounds(320, 5, 100, 10);

    String playerHpString = String.valueOf(player.getHp());
    playerHpLabel = new JLabel(playerHpString);
    playerHpLabel.setBounds(285, 5, 100, 10);

    player_HPGAUGE = new JLabel(hpGauge);
    player_HPGAUGE.setBounds(0, 5, IntEnemy_hpWidth, 10);

    othello_piece_Normal = new JButton(boardIcon);
    othello_piece_Normal.setBounds(15, 20, 50, 50);
    othello_piece_Normal.addMouseListener(this);
    othello_piece_Normal.setActionCommand(Integer.toString(70));

    othello_piece_A = new JButton(boardIcon);
    othello_piece_A.setBounds(85, 20, 50, 50);
    othello_piece_A.addMouseListener(this);
    othello_piece_A.setActionCommand(Integer.toString(71));

    othello_piece_B = new JButton(boardIcon);
    othello_piece_B.setBounds(155, 20, 50, 50);
    othello_piece_B.addMouseListener(this);
    othello_piece_B.setActionCommand(Integer.toString(72));

    othello_piece_C = new JButton(boardIcon);
    othello_piece_C.setBounds(225, 20, 50, 50);
    othello_piece_C.addMouseListener(this);
    othello_piece_C.setActionCommand(Integer.toString(73));

    othello_piece_D = new JButton(boardIcon);
    othello_piece_D.setBounds(295, 20, 50, 50);
    othello_piece_D.addMouseListener(this);
    othello_piece_D.setActionCommand(Integer.toString(74));

    JLabel playerBackground = new JLabel();
    playerBackground.setBounds(0, 0, 375, 90);
    playerBackground.setIcon(playerBackgroundIcon);
    player_Panel.setLayer(playerBackground, 0);

    player_Panel.add(playerHpLabel);
    player_Panel.add(playerMaxHpLabel);
    //        player_Panel.add(player_NAME);
    player_Panel.add(player_HPGAUGE);

    player_Panel.add(othello_piece_Normal);
    player_Panel.add(othello_piece_A);
    player_Panel.add(othello_piece_B);
    player_Panel.add(othello_piece_C);
    player_Panel.add(othello_piece_D);
    player_Panel.add(playerBackground);

    player_Panel.setBounds(0, 430, 375, 90);

    contentPane.add(player_Panel);

    // サーバに接続する
    try {
      this.socket = new Socket("localhost", 10000);
      (new ClientThread()).start();
    } catch (UnknownHostException e) {
      System.err.println("ホストの IP アドレスが判定できません: " + e);
    } catch (IOException e) {
      System.err.println("エラーが発生しました: " + e);
    }
  }

  // メッセージ受信のためのスレッド
  public class ClientThread extends Thread {
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

        othello_piece_Normal.setIcon(mySelectedIcon);
        othello_piece_A.setIcon(myNoSelectdCatIcon);
        othello_piece_B.setIcon(myNoSelectedBoarIcon);
        othello_piece_C.setIcon(myNoSelectedHorseIcon);
        othello_piece_D.setIcon(myNoSelectedBirdIcon);

        buttonArray[3][3].setIcon(whiteIcon);
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
            Counter counter;
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
        System.err.println("エラーが発生しました: " + e);
      }
    }
  }

  // ----------------------------------------------------------------------------------------------

  @Contract(pure = true)
  private boolean colorToFirstTurn(BlackOrWhite blackOrWhite) {
    return blackOrWhite == BlackOrWhite.Black;
  }

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

  @Override
  public void mouseClicked(@NotNull MouseEvent mouseEvent) { // ボタンをクリックしたときの処理
    System.out.println("クリック");
    final JButton theButton = (JButton) mouseEvent.getComponent(); // クリックしたオブジェクトを得る．型が違うのでキャストする
    final int theArrayIndexInt = Integer.parseInt(theButton.getActionCommand()); // ボタンの配列の番号を取り出す
    final int y = theArrayIndexInt / MASU;
    final int x = theArrayIndexInt % MASU;

    System.out.println("theArrayIndexInt=" + theArrayIndexInt);
    final Icon theIcon = theButton.getIcon();
    System.out.println(theIcon);

    if (myTurn && !endGame) {
      if (theArrayIndexInt == 70) {
        othello_piece_Normal.setIcon(mySelectedIcon);
        othello_piece_A.setIcon(myNoSelectdCatIcon);
        othello_piece_B.setIcon(myNoSelectedBoarIcon);
        othello_piece_C.setIcon(myNoSelectedHorseIcon);
        othello_piece_D.setIcon(myNoSelectedBirdIcon);
        sendDataToServer("SELECT" + " " + myColor + " " + theArrayIndexInt);
      }
      if (theArrayIndexInt == 71) {
        othello_piece_Normal.setIcon(myNoSelectedIcon);
        othello_piece_A.setIcon(mySelectedCatIcon);
        othello_piece_B.setIcon(myNoSelectedBoarIcon);
        othello_piece_C.setIcon(myNoSelectedHorseIcon);
        othello_piece_D.setIcon(myNoSelectedBirdIcon);
        sendDataToServer("SELECT" + " " + myColor + " " + theArrayIndexInt);
      }
      if (theArrayIndexInt == 72) {
        othello_piece_Normal.setIcon(myNoSelectedIcon);
        othello_piece_A.setIcon(myNoSelectdCatIcon);
        othello_piece_B.setIcon(mySelectedBoarIcon);
        othello_piece_C.setIcon(myNoSelectedHorseIcon);
        othello_piece_D.setIcon(myNoSelectedBirdIcon);
        sendDataToServer("SELECT" + " " + myColor + " " + theArrayIndexInt);
      }
      if (theArrayIndexInt == 73) {
        othello_piece_Normal.setIcon(myNoSelectedIcon);
        othello_piece_A.setIcon(myNoSelectdCatIcon);
        othello_piece_B.setIcon(myNoSelectedBoarIcon);
        othello_piece_C.setIcon(mySelectedHorseIcon);
        othello_piece_D.setIcon(myNoSelectedBirdIcon);
        sendDataToServer("SELECT" + " " + myColor + " " + theArrayIndexInt);
      }
      if (theArrayIndexInt == 74) {
        othello_piece_Normal.setIcon(myNoSelectedIcon);
        othello_piece_A.setIcon(myNoSelectdCatIcon);
        othello_piece_B.setIcon(myNoSelectedBoarIcon);
        othello_piece_C.setIcon(myNoSelectedHorseIcon);
        othello_piece_D.setIcon(mySelectedBirdIcon);
        sendDataToServer("SELECT" + " " + myColor + " " + theArrayIndexInt);
      }
      if (canPutDown(x, y)) {
        System.out.println("canPutDownでTrueになった" + x + y);
        sendDataToServer("FLIP" + " " + x + " " + y);
        reverse(x, y);
        sendDataToServer("REVERSED"); // 送信データをバッファに書き出す
      } else {
        System.out.println("そこには配置できません");
      }
    }
  }

  @Override
  public void mouseEntered(@NotNull MouseEvent e) { // マウスがオブジェクトに入ったときの処理
    JButton theButton = (JButton) e.getComponent(); // クリックしたオブジェクトを得る．型が違うのでキャストする
    String theArrayIndex = theButton.getActionCommand(); // ボタンの配列の番号を取り出す
    int theArrayIndexInt = Integer.parseInt(theArrayIndex);

    if (theArrayIndexInt == 74) {
      pieceDescription.setText("     ひっくり返した後の、相手の駒数×80のダメージを与える");
      pieceDescription.setOpaque(true);
    }
    if (theArrayIndexInt == 73) {
      pieceDescription.setText("        　　自分のHPが減少する程ダメージが上昇する");
      pieceDescription.setOpaque(true);
    }
    if (theArrayIndexInt == 72) {
      pieceDescription.setText("盤面で表になっている間、毎ターン300のダメージを与える");
      pieceDescription.setOpaque(true);
    }
    if (theArrayIndexInt == 71) {
      pieceDescription.setText(" 　　ひっくり返した駒の枚数×300のダメージを与える");
      pieceDescription.setOpaque(true);
    }
    if (theArrayIndexInt == 70) {
      pieceDescription.setText(" 　            普通の駒");
      pieceDescription.setOpaque(true);
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    pieceDescription.setText(null);
    pieceDescription.setOpaque(false);
  }

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}
}
