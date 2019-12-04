public class StatCollector {
    public static void main(String[] args) {
        BattleshipGame test = new BattleshipGame("");
        int p1wins = 0;
        for(int i = 0; i < 1000; i++) {
            test.setupGame();
            test.runGame();
            if(test.getWinner())
                p1wins++;
            test.resetGame();
        }
        System.out.println("In 1000 games, CPU1 won " + p1wins + " times.");
    }
}
