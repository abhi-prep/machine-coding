package services;

import model.Board;
import model.Player;
import strategy.DiceStrategy;

import java.util.*;
import java.util.concurrent.locks.*;

public class GameEngine {
    private static final GameEngine INSTANCE = new GameEngine();
    private final Lock lock = new ReentrantLock();
    private final Queue<Player> players = new LinkedList<>();
    private Board board;
    private DiceStrategy dice;
    private volatile boolean gameOver = false;

    private GameEngine() {}

    public static GameEngine getInstance() {
        return INSTANCE;
    }

    public void init(Board board, DiceStrategy dice, List<Player> playersList) {
        this.board = board;
        this.dice = dice;
        this.players.clear();
        this.players.addAll(playersList);
        this.gameOver = false;
    }

    public void playTurn() {
        if (gameOver) return;

        lock.lock();
        try {
            Player currentPlayer = players.poll();
            if (currentPlayer == null) return;

            int diceRoll = dice.roll();
            System.out.println(Thread.currentThread().getName() + " - " + currentPlayer.getId() + " rolls " + diceRoll);

            int newPosition = board.getNextPosition(currentPlayer.getPosition(), diceRoll);
            currentPlayer.setPosition(newPosition);

            System.out.println(currentPlayer.getId() + " moves to " + newPosition);

            if (newPosition == board.getSize()) {
                System.out.println(currentPlayer.getId() + " wins the game!");
                gameOver = true;
            } else {
                players.add(currentPlayer);
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
