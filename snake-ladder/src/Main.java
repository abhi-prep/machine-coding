import model.Board;
import model.Player;
import services.BoardFactory;
import strategy.DefaultDiceStrategy;
import strategy.DiceStrategy;
import services.GameEngine;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = BoardFactory.createBoard(100, 5, 5);
        List<Player> players = List.of(new Player("Player1"), new Player("Player2"));
        DiceStrategy dice = new DefaultDiceStrategy();

        GameEngine engine = GameEngine.getInstance();
        engine.init(board, dice, new ArrayList<>(players));

        Thread t = new Thread(() -> {
            while (!engine.isGameOver()) {
                engine.playTurn();
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
        }, "Game-Thread");

        t.start();

        System.out.println("Game Over!");
    }
}
