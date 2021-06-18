import java.util.Scanner;
import java.util.InputMismatchException;

class Player {
  // プレイヤーのカード
  // [ n ]{ m , l }で、n枚目に引いたカードのmがスート、l+1が数字を表す。
  //// m=0:スペード,m=1:ハート,m=2:ダイヤ,m=3:クラブ
  // 所持可能なカードは最大で12枚。m=l=-1のときカードはそこにない。
  private int[][] handCard = new int[12][2];
  // 所持カード枚数
  private int handNum = 0;

  int getHandNum() {
    return handNum;
  }

  void setHandNum(int handNum) {
    this.handNum = handNum;
  }

  // カードの初期化
  void initHandCard() {
    for (int i = 0; i < 12; i++) {
      for (int j = 0; j < 2; j++) {
        handCard[i][j] = -1;
      }
    }
  }

  int[][] getHandCard() {
    return handCard;
  }

  void setHandCard(int card[]) {
    this.handCard[this.handNum][0] = card[0];
    this.handCard[this.handNum][1] = card[1];
  }

  void printOpenHandCard() {
    for (int i = 0; i < handNum; i++) {
      System.out.print(this.handCard[i][1]+1);
      System.out.print(" ");
    }
    System.out.print("\n");
  }

  void printCloseHandCard() {
    System.out.print(this.handCard[0][1]+1);
    for (int i = 1; i < handNum; i++) {
      System.out.print(" ■");
    }
    System.out.print("\n");
  }

  // ハンドの合計値
  private int handSum = 0;

  int getHandSum() {
    return handSum;
  }

  void setHandSum(int handSum) {
    this.handSum = handSum;
  }

  // ハンドを計算
  void calcHand() {
    int handSum = 0;
    int ace_num = 0;
    int i;
    for (i = 0; i <= this.handNum; i++) {
      if (handCard[i][1] == 0) {
        ace_num++;
        handSum += 11;
      } else if (handCard[i][1] >= 9) {
        handSum += 10;
      } else {
        handSum += (handCard[i][1] + 1);
      }
    }
    while (handSum > 21 && ace_num > 0) {
      handSum -= 10;
      ace_num--;
    }
    setHandSum(handSum);
  }

  int isBurst() {
    if (this.handSum > 21) {
      return 1;
    } else {
      return 0;
    }
  }

  // コイン
  private int myCoin = 100;
  private int betCoin = 0;

  int getCoin() {
    return myCoin;
  }

  void setCoin(int myCoin) {
    this.myCoin = myCoin;
  }

  int getBetCoin() {
    return betCoin;
  }

  void setBetCoin(int betCoin) {
    this.betCoin = betCoin;
  }

  //選択したコイン数を賭けられるのか判定
  int canBet() {
    if (this.betCoin <= this.myCoin) {
      return 1;
    } else {
      return 0;
    }
  }

  //コインを1枚以上持っているか判定
  int haveCoin(){
    if(this.getCoin()>0){return 1;}
    return 0;
  }

  //賭けるコイン数を標準入力で受けとる
  void chooseCoin(){
    int bet_flag=0;
    while (bet_flag == 0) {
      System.out.print("Coin : ");
      System.out.println(this.getCoin());
      System.out.print("choose how many coins you bet\n--->");
      try {
        Scanner scanner = new Scanner(System.in);
        this.setBetCoin(scanner.nextInt());
        if (this.canBet() == 1) {
          bet_flag = 1;
        } else if(this.betCoin==0){
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
  int dealerDraw() {
    if (this.getHandSum() < 17) {
      return 1;
    } else {
      return 0;
    }
  }
}
