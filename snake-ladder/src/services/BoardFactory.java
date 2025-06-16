package services;

import model.Board;
import model.Ladder;
import model.Snake;

import java.util.Random;

public class BoardFactory {
    public static Board createBoard(int size, int snakeCount, int ladderCount) {
        Board board = new Board(size);
        Random rand = new Random();

        while (snakeCount-- > 0) {
            int start = rand.nextInt(size - 10) + 10;
            int end = rand.nextInt(start - 1);
            board.addSnake(new Snake(start, end));
        }

        while (ladderCount-- > 0) {
            int start = rand.nextInt(size - 10);
            int end = rand.nextInt(size - start - 1) + start + 1;
            board.addLadder(new Ladder(start, end));
        }

        return board;
    }
}
