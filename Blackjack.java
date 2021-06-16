import java.util.Random;
import java.util.Timer;
import java.util.Scanner;
import java.util.InputMismatchException;

class Board {
  // 山札のカード
  // deck_card[n][m]=1のとき、スートがｎで数字がｍ+1のカードが山札にある
  // n=0:スペード,n=1:ハート,n=2:ダイヤ,n=3:クラブ
  private int[][] deck_card = new int[4][13];

  void init_deck_card() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        deck_card[i][j] = 1;
      }
    }
  }

  void hit(Player player) {
    Random rand = new Random();
    int[] return_card = new int[2];
    int i,j;
    do{
      i = rand.nextInt(4);
      j = rand.nextInt(13);
    }while(deck_card[i][j] == 0);
    return_card[0] = i;
    return_card[1] = j;
    player.setHandCard(return_card);
    deck_card_reset(i, j);
    player.calc_hand();
    player.setHandNum(player.getHandNum() + 1);
  }

  // 山札からカードがなくなった時の処理
  void deck_card_reset(int i, int j) {
    deck_card[i][j] = 0;
  }

  // 勝敗判定、勝ちで1、分けで0、負けで-1
  int is_win(Player player, Player dealer) {
    if (player.getHandSum() > dealer.getHandSum()) {
      return 1;
    } else if (player.getHandSum() == dealer.getHandSum()) {
      return 0;
    } else {
      return -1;
    }
  }
}



class Player {
  // プレイヤーのカード
  // [ n ]{ m , l }で、n枚目に引いたカードのmがスート、l+1が数字を表す。
  //// m=0:スペード,m=1:ハート,m=2:ダイヤ,m=3:クラブ
  // 所持可能なカードは最大で12枚。m=l=-1のときカードはそこにない。
  private int[][] hand_card = new int[12][2];
  // 所持カード枚数
  private int hand_num = 0;

  int getHandNum() {
    return hand_num;
  }

  void setHandNum(int hand_num) {
    this.hand_num = hand_num;
  }

  // カードの初期化
  void init_hand_card() {
    for (int i = 0; i < 12; i++) {
      for (int j = 0; j < 2; j++) {
        hand_card[i][j] = -1;
      }
    }
  }

  int[][] getHandCard() {
    return hand_card;
  }

  void setHandCard(int card[]) {
    this.hand_card[this.hand_num][0] = card[0];
    this.hand_card[this.hand_num][1] = card[1];
  }

  void printOpenHandCard() {
    for (int i = 0; i < hand_num; i++) {
      System.out.print(this.hand_card[i][1]+1);
      System.out.print(" ");
    }
    System.out.print("\n");
  }

  void printCloseHandCard() {
    System.out.print(this.hand_card[0][1]+1);
    for (int i = 1; i < hand_num; i++) {
      System.out.print(" ■");
    }
    System.out.print("\n");
  }

  // ディーラーと自分のハンドの値
  private int hand_sum = 0;

  int getHandSum() {
    return hand_sum;
  }

  void setHandSum(int hand_sum) {
    this.hand_sum = hand_sum;
  }

  // ハンドを計算
  void calc_hand() {
    int hand_sum = 0;
    int ace_num = 0;
    int i;
    for (i = 0; i <= this.hand_num; i++) {
      if (hand_card[i][1] == 0) {
        ace_num++;
        hand_sum += 11;
      } else if (hand_card[i][1] >= 9) {
        hand_sum += 10;
      } else {
        hand_sum += (hand_card[i][1] + 1);
      }
    }
    while (is_burst() == 1 && ace_num > 0) {
      hand_sum -= 10;
      ace_num--;
    }
    setHandSum(hand_sum);
  }

  int is_burst() {
    if (hand_sum > 21) {
      return 1;
    } else {
      return 0;
    }
  }

  // コイン
  private int my_coin = 100;
  private int bet_coin = 0;

  int getCoin() {
    return my_coin;
  }

  void setCoin(int my_coin) {
    this.my_coin = my_coin;
  }

  int getBetCoin() {
    return bet_coin;
  }

  void setBetCoin(int bet_coin) {
    this.bet_coin = bet_coin;
  }

  int can_bet() {
    if (this.bet_coin <= this.my_coin) {
      return 1;
    } else {
      return 0;
    }
  }

  // ディーラーがカードを追加するか判定
  int dealer_draw() {
    if (this.getHandSum() < 17) {
      return 1;
    } else {
      return 0;
    }
  }
}

class Blackjack {
  public static void main(String argv[]) {
    Board board = new Board();
    Player player = new Player();
    Player dealer = new Player();
    Scanner scanner = new Scanner(System.in);
    Timer timer = new Timer();
    int bet_flag = 0;
    int set_flag = 0;

    // 賭けるコインの数を選択
    System.out.print("Game Start\n- - - - - - - - - -\n");
    while (true) {
      board.init_deck_card();
      player.init_hand_card();
      dealer.init_hand_card();
      while (bet_flag == 0) {
        System.out.print("Coin : ");
        System.out.println(player.getCoin());
        System.out.print("choose how many coins you bet\n--->");
        player.setBetCoin(scanner.nextInt());
        try {
          if (player.can_bet() == 1) {
            bet_flag = 1;
          } else {
            System.out
                .println("The number of bet coins exceeds the number of coins in your possession.\nPlease type again.");
          }
        } catch (InputMismatchException e) {
          System.out.println("Please type the correct integer.");
        }
      }
      board.hit(dealer);
      board.hit(dealer);
      board.hit(player);
      board.hit(player);

      // プレイヤーのヒットが終わるまで繰り返し
      while (set_flag == 0) {
        System.out.print("\n");
        dealer.printCloseHandCard();
        System.out.println("- - - - - - - - - -");
        player.printOpenHandCard();
        System.out.print("Player Hand : ");
        System.out.print(player.getHandSum());
        System.out.print("\nCoin : ");
        System.out.print(player.getCoin());
        System.out.print(" Bet : ");
        System.out.print(player.getBetCoin());
        System.out.print("\nhit or set?(h/s)\n--->");
        String response = scanner.next();
        if (response.equals("s")) {
          set_flag = 1;
        } else if (response.equals("h")) {
          board.hit(player);
        } else {
          System.out.println("Please type h or s correctly.");
        }
      }

      //ディーラーがセットするまで繰り返し
      while(dealer.dealer_draw()==1){
        System.out.println("\nDealer Hit");
        board.hit(dealer);
        System.out.print("\n");
        dealer.printCloseHandCard();
        System.out.println("- - - - - - - - - -");
        player.printOpenHandCard();
        System.out.print("Player Hand : ");
        System.out.println(player.getHandSum());
      }
    }
  }
}