import Data.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ClientView extends JFrame {
  private final ImageIcon boardIcon = new ImageIcon("../assets/board.jpg");
  private final ImageIcon canPutIcon = new ImageIcon("../assets/can-put-down.jpg");

  private final int doorFrame = 37;
  private final ImageIcon[] catDoorIcon = new ImageIcon[doorFrame];
  private final ImageIcon[] boarDoorIcon = new ImageIcon[doorFrame];
  private final ImageIcon[] horseDoorIcon = new ImageIcon[doorFrame];
  private final ImageIcon[] birdDoorIcon = new ImageIcon[doorFrame];

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

  private final ImageIcon[] reverseIcon = new ImageIcon[24];
  private final ImageIcon[] yourturnIcon = new ImageIcon[34];
  private final ImageIcon[] youwinIcon = new ImageIcon[34];
  private final ImageIcon[] youloseIcon = new ImageIcon[34];
  private final ImageIcon[] passIcon = new ImageIcon[35];

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

  ClientView() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ウィンドウを閉じるときに，正しく閉じるように設定する
    this.setVisible(true);

    setTitle("Client"); // ウィンドウのタイトルを設定する
    setSize(375, 550); // ウィンドウのサイズを設定する
    Container contentPane = getContentPane(); // フレームのペインを取得する

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

    JLabel enemy_MAXHP = new JLabel("/" + (Integer.toString(enemy.getMaxHp())));
    enemy_MAXHP.setBounds(320, 55, 100, 10);

    String enemy_hp = String.valueOf(enemy.getHp());
    enemy_HP = new JLabel(enemy_hp);
    enemy_HP.setBounds(285, 55, 100, 10);

    enemy_HPGAUGE = new JLabel(hpGauge);
    final int IntEnemy_hpWidth = (int) hpWidth;
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
        buttonArray[y][x].setBounds(x * 45, y * 45, 45, 45);
        mainPane.add(buttonArray[y][x]);
        mainPane.setLayer(buttonArray[y][x], 0);
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

    othello_piece_A = new JButton(boardIcon);
    othello_piece_A.setBounds(85, 20, 50, 50);

    othello_piece_B = new JButton(boardIcon);
    othello_piece_B.setBounds(155, 20, 50, 50);

    othello_piece_C = new JButton(boardIcon);
    othello_piece_C.setBounds(225, 20, 50, 50);

    othello_piece_D = new JButton(boardIcon);
    othello_piece_D.setBounds(295, 20, 50, 50);

    JLabel playerBackground = new JLabel();
    playerBackground.setBounds(0, 0, 375, 90);
    playerBackground.setIcon(playerBackgroundIcon);
    player_Panel.setLayer(playerBackground, 0);

    player_Panel.add(playerHpLabel);
    player_Panel.add(playerMaxHpLabel);
    player_Panel.add(player_HPGAUGE);

    player_Panel.add(othello_piece_Normal);
    player_Panel.add(othello_piece_A);
    player_Panel.add(othello_piece_B);
    player_Panel.add(othello_piece_C);
    player_Panel.add(othello_piece_D);
    player_Panel.add(playerBackground);

    player_Panel.setBounds(0, 430, 375, 90);

    contentPane.add(player_Panel);
  }

  public void setThread(ClientThread thread) {
    for (int y = 0; y < MASU; y++) {
      for (int x = 0; x < MASU; x++) {
        buttonArray[y][x].addMouseListener(new ClientBoardMouseListener(x, y, thread));
      }
    }
    othello_piece_Normal.addMouseListener(new ClientPaletteMouseListener(Skill.Normal, thread));
    othello_piece_A.addMouseListener(new ClientPaletteMouseListener(Skill.Cat, thread));
    othello_piece_B.addMouseListener(new ClientPaletteMouseListener(Skill.Boar, thread));
    othello_piece_C.addMouseListener(new ClientPaletteMouseListener(Skill.Horse, thread));
    othello_piece_D.addMouseListener(new ClientPaletteMouseListener(Skill.Bird, thread));
  }

  public void updatePalette(@NotNull BlackOrWhite blackOrWhite, @Nullable Skill skill) {
    if (skill == null) {
      othello_piece_Normal.setIcon(Stone.paletteImage(blackOrWhite, Skill.Normal));
      othello_piece_A.setIcon(Stone.paletteImage(blackOrWhite, Skill.Cat));
      othello_piece_B.setIcon(Stone.paletteImage(blackOrWhite, Skill.Boar));
      othello_piece_C.setIcon(Stone.paletteImage(blackOrWhite, Skill.Horse));
      othello_piece_D.setIcon(Stone.paletteImage(blackOrWhite, Skill.Bird));
      return;
    }
    switch (skill) {
      case Normal:
        othello_piece_Normal.setIcon(Stone.paletteSelectedImage(blackOrWhite, Skill.Normal));
        othello_piece_A.setIcon(Stone.paletteImage(blackOrWhite, Skill.Cat));
        othello_piece_B.setIcon(Stone.paletteImage(blackOrWhite, Skill.Boar));
        othello_piece_C.setIcon(Stone.paletteImage(blackOrWhite, Skill.Horse));
        othello_piece_D.setIcon(Stone.paletteImage(blackOrWhite, Skill.Bird));
        return;
      case Cat:
        othello_piece_Normal.setIcon(Stone.paletteImage(blackOrWhite, Skill.Normal));
        othello_piece_A.setIcon(Stone.paletteSelectedImage(blackOrWhite, Skill.Cat));
        othello_piece_B.setIcon(Stone.paletteImage(blackOrWhite, Skill.Boar));
        othello_piece_C.setIcon(Stone.paletteImage(blackOrWhite, Skill.Horse));
        othello_piece_D.setIcon(Stone.paletteImage(blackOrWhite, Skill.Bird));
        return;
      case Boar:
        othello_piece_Normal.setIcon(Stone.paletteImage(blackOrWhite, Skill.Normal));
        othello_piece_A.setIcon(Stone.paletteImage(blackOrWhite, Skill.Cat));
        othello_piece_B.setIcon(Stone.paletteSelectedImage(blackOrWhite, Skill.Boar));
        othello_piece_C.setIcon(Stone.paletteImage(blackOrWhite, Skill.Horse));
        othello_piece_D.setIcon(Stone.paletteImage(blackOrWhite, Skill.Bird));
        return;
      case Horse:
        othello_piece_Normal.setIcon(Stone.paletteImage(blackOrWhite, Skill.Normal));
        othello_piece_A.setIcon(Stone.paletteImage(blackOrWhite, Skill.Cat));
        othello_piece_B.setIcon(Stone.paletteImage(blackOrWhite, Skill.Boar));
        othello_piece_C.setIcon(Stone.paletteSelectedImage(blackOrWhite, Skill.Horse));
        othello_piece_D.setIcon(Stone.paletteImage(blackOrWhite, Skill.Bird));
        return;
      case Bird:
        othello_piece_Normal.setIcon(Stone.paletteImage(blackOrWhite, Skill.Normal));
        othello_piece_A.setIcon(Stone.paletteImage(blackOrWhite, Skill.Cat));
        othello_piece_B.setIcon(Stone.paletteImage(blackOrWhite, Skill.Boar));
        othello_piece_C.setIcon(Stone.paletteImage(blackOrWhite, Skill.Horse));
        othello_piece_D.setIcon(Stone.paletteSelectedImage(blackOrWhite, Skill.Bird));
    }
  }

  public void setBorad(
      @NotNull BlackOrWhite blackOrWhite, @NotNull Skill skill, final int x, final int y) {
    buttonArray[y][x].setIcon(Stone.boardImage(blackOrWhite, skill));
  }
}
