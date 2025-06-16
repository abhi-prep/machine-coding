package model;

import java.util.*;

public class Board {
    private final int size;
    private final Map<Integer, Integer> snakes = new HashMap<>();
    private final Map<Integer, Integer> ladders = new HashMap<>();

    public Board(int size) {
        this.size = size;
    }

    public void addSnake(Snake snake) {
        snakes.put(snake.getStart(), snake.getEnd());
    }

    public void addLadder(Ladder ladder) {
        ladders.put(ladder.getStart(), ladder.getEnd());
    }

    public int getSize() {
        return size;
    }

    public int getNextPosition(int current, int diceRoll) {
        int next = current + diceRoll;
        if (next > size) return current;

        if (snakes.containsKey(next)) {
            System.out.println("Oops! Bitten by a snake at " + next);
            return snakes.get(next);
        }

        if (ladders.containsKey(next)) {
            System.out.println("Yay! Climbed a ladder at " + next);
            return ladders.get(next);
        }

        return next;
    }
}
