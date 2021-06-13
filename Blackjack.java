import java.util.Random;
import java.util.Timer;

class Board {

  // ディーラーと自分と山札のカード
  private int[][] my_card = new int[4][13];
  private int[][] dealer_card = new int[4][13];
  private int[][] deck_card = new int[4][13];

  // カードの初期化
  void init_my_card() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        my_card[i][j] = 0;
      }
    }
  }

  void init_dealer_card() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        dealer_card[i][j] = 0;
      }
    }
  }

  void init_deck_card() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        deck_card[i][j] = 1;
      }
    }
  }

  void hit(int[][] card) {
    Random rand = new Random();
    int flag = 0;
    while (flag == 0) {
      int i = rand.nextInt(4);
      int j = rand.nextInt(13);
      if (deck_card[i][j] == 1) {
        card_set(i, j, card);
        card_reset(i, j, deck_card);
        flag=1;
      }
    }
  }

  int[][] getMyCard() {
    return my_card;
  }

  int[][] getDealerCard() {
    return dealer_card;
  }

  // カードの所持、未所持を管理
  void card_set(int i, int j, int[][] card) {
    card[i][j] = 1;
  }

  void card_reset(int i, int j, int[][] card) {
    card[i][j] = 0;
  }

  // ディーラーと自分のハンドの値
  private int my_hand = 0;
  private int dealer_hand = 0;

  int getMyHand() {
    return my_hand;
  }

  int getDealerHand() {
    return dealer_hand;
  }

  void setMyHand(int my_hand) {
    this.my_hand = my_hand;
  }

  void setDealerHand(int dealer_hand) {
    this.dealer_hand = dealer_hand;
  }

  int is_burst(int hand) {
    if (hand > 21) {
      return 1;
    } else {
      return 0;
    }
  }

  //勝敗判定、勝ちで1、分けで0、負けで-1
  int is_win(){
    if(my_hand>dealer_hand){return 1;}
    else if(my_hand==dealer_hand){return 0;}
    else{return -1;}
  }

  // ディーラーがカードを追加するか判定
  int dealer_draw() {
    if (dealer_hand < 17) {
      return 1;
    } else {
      return 0;
    }
  }

  // ハンドを計算
  int calc_hand(int[][] card){
    int hand = 0;
    int i, j;
    for (i = 0; i < 4; i++) {
      for (j = 1; j < 10; j++) {
        if (card[i][j] == 1) {
          hand += j + 1;
        }
      }
      for (j = 10; j < 13; j++) {
        if (card[i][j] == 1) {
          hand += 10;
        }
      }
    }
    for (i = 0; i < 4; i++) {
      if (card[i][0] == 1) {
        hand += 11;
        if (hand > 21) {
          hand -= 10;
        }
      }
    }
    return hand;
  }

  // コイン
  private int my_coin = 100;
  private int bet_coin = 0;

  int getMyCoin() {
    return my_coin;
  }

  void setMyCoin(int my_coin) {
    this.my_coin = my_coin;
  }

  int getBetCoin() {
    return bet_coin;
  }

  void setBetCoin(int bet_coin) {
    this.bet_coin = bet_coin;
  }

  public static void main(String argv[]) {
    Board board = new Board();
    Timer timer = new Timer();
    printf("Game Start\n------------\n");
    while(1){
      board.init_my_card();
      board.init_dealer_card();
      board.init_deck_card();
      board.hit(board.getMyCard());
      board.hit(board.getMyCard());
      board.hit(board.getDealerCard());
      board.hit(board.getDealerCard());
    }
  }
}