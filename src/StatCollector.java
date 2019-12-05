import java.time.Duration;
import java.time.Instant;

/**
 * This class is made to test how long it takes the computer to play against itself.
 * @author Dylan Bui
 */
public class StatCollector {
    public static void main(String[] args) {
        BattleshipGame test = new BattleshipGame("");
        int p1wins = 0;
        int numberOfGames = 100000;
        Instant start = Instant.now();
        for(int i = 0; i < numberOfGames; i++) {
            test.testTwoCPUSNoPrinting();
            if(test.getWinner())
                p1wins++;
            test.resetGame();
        }
        Instant end = Instant.now();
        System.out.println("In " + numberOfGames + " games, CPU1 won " + p1wins + " times.");
        System.out.println("It took " + Duration.between(start, end).toMillis() + " milliseconds.");
    }
}
