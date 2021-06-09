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
        cp_card[i][j] = 0;
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

  // カードの所持、未所持を管理
  void card_set(int i, int j, int[][] card) {
    card[i][j] = 1;
  }

  void card_reset(int i, int j, int[][] card) {
    card[i][j] = 0;
  }

  // ディーラーと自分のハンドの値
  private int my_hand = 0, dealer_hand = 0;

  int getMyHand() {
    return my_hand;
  }

  int getDealerHand() {
    return dealer_hand;
  }

  int setMyHand(int my_hand) {
    this.my_hand = my_hand;
  }

  int setDealerHand(int dealer_hand) {
    this.dealer_hand = dealer_hand;
  }

  // ハンドを計算
  int calc_hand(int[][] card) {
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

  int setMyCoin(int my_coin) {
    this.my_coin = my_coin;
  }

  int getBetCoin() {
    return bet_coin;
  }

  int setBetCoin(int bet_coin) {
    this.bet_coin = bet_coin;
  }

  public static void main(String argv[]) {
    Board board = new Board();
  }
}