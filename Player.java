import java.util.Scanner;
import java.util.InputMismatchException;

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

  // ハンドの合計値
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
    while (hand_sum > 21 && ace_num > 0) {
      hand_sum -= 10;
      ace_num--;
    }
    setHandSum(hand_sum);
  }

  int is_burst() {
    if (this.hand_sum > 21) {
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

  //選択したコイン数を賭けられるのか判定
  int can_bet() {
    if (this.bet_coin <= this.my_coin) {
      return 1;
    } else {
      return 0;
    }
  }

  //コインを1枚以上持っているか判定
  int have_coin(){
    if(this.getCoin()>0){return 1;}
    return 0;
  }

  //賭けるコイン数を標準入力で受けとる
  void choose_coin(){
    int bet_flag=0;
    while (bet_flag == 0) {
      System.out.print("Coin : ");
      System.out.println(this.getCoin());
      System.out.print("choose how many coins you bet\n--->");
      try {
        Scanner scanner = new Scanner(System.in);
        this.setBetCoin(scanner.nextInt());
        if (this.can_bet() == 1) {
          bet_flag = 1;
        } else if(this.bet_coin==0){
          System.out
              .println("You need to select one or more coins.\nPlease type again.");
        }
        else {
          System.out
              .println("The number of bet coins exceeds the number of coins in your possession.\nPlease type again.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Please type the correct integer.");
      }
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
