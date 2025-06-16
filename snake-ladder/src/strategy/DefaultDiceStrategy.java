package strategy;

import java.util.Random;

public class DefaultDiceStrategy implements DiceStrategy {
    private final Random random = new Random();
    public int roll() {
        return 1 + random.nextInt(6);
    }
}
