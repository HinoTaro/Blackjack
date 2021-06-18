class Blackjack {
  public static void main(String argv[]) {
    Board board = new Board();
    Player player = new Player();
    Player dealer = new Player();

    System.out.print("\nGame Start\n- - - - - - - - - -\n");
    int conFlag=1;
    while(conFlag==1){
      //盤面のカードの初期化
      board.initDeckCard();
      player.initHandCard();
      player.setHandNum(0);
      dealer.initHandCard();
      dealer.setHandNum(0);

      player.chooseCoin();

      //初期カード配布
      board.hit(dealer);
      board.hit(dealer);
      board.hit(player);
      board.hit(player);

      //プレイヤーとディーラーのカードヒット
      board.playerHit(player, dealer);
      board.dealerHit(player, dealer);

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
      board.coinWinLose(player, dealer);

      conFlag=board.gameContinue(player);
    }
    System.out.println("Thank you for playing.");
    System.exit(0);
  }
}