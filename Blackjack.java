class Blackjack {
  public static void main(String argv[]) {
    Board board = new Board();
    Player player = new Player();
    Player dealer = new Player();

    System.out.print("\nGame Start\n- - - - - - - - - -\n");
    int con_flag=1;
    while(con_flag==1){
      //盤面のカードの初期化
      board.init_deck_card();
      player.init_hand_card();
      player.setHandNum(0);
      dealer.init_hand_card();
      dealer.setHandNum(0);

      player.choose_coin();

      //初期カード配布
      board.hit(dealer);
      board.hit(dealer);
      board.hit(player);
      board.hit(player);

      //プレイヤーとディーラーのカードヒット
      board.player_hit(player, dealer);
      board.dealer_hit(player, dealer);

      //最終的な盤面の表示
      System.out.println("\nDealer Set");
      System.out.println("\nCard Open\n");
      System.out.print("Dealer Hand : ");
      System.out.println(dealer.getHandSum());
      dealer.printOpenHandCard();
      System.out.println("- - - - - - - - - -");
      player.printOpenHandCard();
      System.out.print("Player Hand : ");
      System.out.println(player.getHandSum());

      //勝敗の処理
      board.coin_win_lose(player, dealer);

      con_flag=board.game_continue(player);
    }
    System.out.println("Thank you for playing.");
    System.exit(0);
  }
}