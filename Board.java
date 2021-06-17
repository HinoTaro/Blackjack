import java.util.Random;
import java.util.Scanner;

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

  // 山札からカードがなくなった時の処理
  void deck_card_reset(int i, int j) {
    deck_card[i][j] = 0;
  }

  // カードを引く処理
  void hit(Player player) {
    Random rand = new Random();
    int[] return_card = new int[2];
    int i, j;
    do {
      i = rand.nextInt(4);
      j = rand.nextInt(13);
    } while (deck_card[i][j] == 0);
    return_card[0] = i;
    return_card[1] = j;
    player.setHandCard(return_card);
    deck_card_reset(i, j);
    player.calc_hand();
    player.setHandNum(player.getHandNum() + 1);
  }

  // プレイヤーがセットするまでカードを引き続ける処理
  void player_hit(Player player, Player dealer) {
    int set_flag = 0;
    Scanner scanner = new Scanner(System.in);
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
        this.hit(player);
      } else {
        System.out.println("Please type h or s correctly.");
      }
      if (player.is_burst() == 1) {
        System.out.print("\n");
        dealer.printCloseHandCard();
        System.out.println("- - - - - - - - - -");
        player.printOpenHandCard();
        System.out.print("Player Hand : ");
        System.out.println(player.getHandSum());
        System.out.println("Your hand is already burst.\n");
        set_flag = 1;
      }
    }
  }

  //// ディーラーがセットするまでカードを引き続ける処理
  void dealer_hit(Player player, Player dealer) {
    while (dealer.dealer_draw() == 1) {
      System.out.println("\nDealer Hit");
      this.hit(dealer);
      System.out.print("\n");
      dealer.printCloseHandCard();
      System.out.println("- - - - - - - - - -");
      player.printOpenHandCard();
      System.out.print("Player Hand : ");
      System.out.println(player.getHandSum());
    }
  }

  // 勝敗判定、勝ちで1、分けで0、負けで-1
  int is_win(Player player, Player dealer) {
    if(player.is_burst()==0&&dealer.is_burst()==1){return 1;}
    else if(player.is_burst()==1&&dealer.is_burst()==1){return 0;}
    else if(player.is_burst()==1&&dealer.is_burst()==0){return -1;}
    else if (player.getHandSum() > dealer.getHandSum()) {
      return 1;
    } else if (player.getHandSum() == dealer.getHandSum()) {
      return 0;
    } else {
      return -1;
    }
  }

  // 勝敗によるコインの処理と画面表示
  void coin_win_lose(Player player, Player dealer) {
    if (this.is_win(player, dealer) == 1) {
      System.out.print("\n【 You win! 】\nCoin--->");
      player.setCoin(player.getCoin() + player.getBetCoin());
      System.out.print(player.getCoin());
      System.out.print("(+");
      System.out.print(player.getBetCoin());
      System.out.println(")");
    }
    else if (this.is_win(player, dealer) == -1) {
      System.out.print("\n【 You lose... 】\nCoin--->");
      player.setCoin(player.getCoin() - player.getBetCoin());
      System.out.print(player.getCoin());
      System.out.print("(-");
      System.out.print(player.getBetCoin());
      System.out.println(")");
    } else {
      System.out.print("\n【 Draw 】\nCoin--->");
      System.out.print(player.getCoin());
      System.out.println("(±0)");
    }
  }

  //ゲームを続けるかどうか
  int game_continue(Player player) {
    if(player.have_coin()==0){
      System.out.println("You no longer have the coin. \nYou can't continue the game.");
      return 0;
    }
    while (true) {
      System.out.print("Do you continue?(y/n)\n--->");
      Scanner scanner = new Scanner(System.in);
      String response = scanner.next();
      if (response.equals("y")) {return 1;}
      else if (response.equals("n")) {return 0;}
      else {
        System.out.println("Please type h or s correctly.");
      }
    }
  }
}