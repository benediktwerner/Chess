package de.benedikt_werner.chess;

import java.awt.Point;
import java.util.Scanner;

public class Game {
    private Board mBoard;
    private Player mAcvitePlayer;
    private King[] mKings;
    private boolean mGameOver;
    
    public static void main(String[] args) {
        new Game().play();
    }

    public Game() {
        mBoard = Board.getDefaultBoard();
        mKings = new King[2];
        mKings[0] = (King) mBoard.pieceAt(0, 4);
        mKings[1] = (King) mBoard.pieceAt(7, 4);
        mAcvitePlayer = Player.WHITE;
        mGameOver = false;
    }
    
    public void play() {
        System.out.println("White: upper case, Black: lower case");
        System.out.println("Enter moves like this: <start> <end>");
        System.out.println("For example: e2 e3 to move the pawn on e2 to e3\n");
        
        Scanner in = new Scanner(System.in);
        while (!mGameOver) {
            mBoard.print();
            System.out.print(mAcvitePlayer + "'s turn: ");
            while (true) {
                String move = in.nextLine().toLowerCase();
                String[] moves = move.split(" ");
                if (moves.length != 2 || moves[0].length() != 2 || moves[1].length() != 2) {
                    System.out.print("Invalid input!\n"
                            + "Enter move: ");
                    continue;
                }
                Point start = strToPoint(moves[0]);
                Point end = strToPoint(moves[1]);
                if (executeMove(start, end))
                    break;
                else
                    System.out.print("Invalid move!\n"
                            + "Enter move: ");
            }
            System.out.println();
        }
        in.close();
    }

    public Point strToPoint(String s) {
        return new Point(s.charAt(1) - '1', s.charAt(0) - 'a');
    }
    
    private void switchPlayer() {
        switch (mAcvitePlayer) {
            case WHITE: mAcvitePlayer = Player.BLACK; break;
            case BLACK: mAcvitePlayer = Player.WHITE; break;
        }
    }

    public boolean executeMove(Point start, Point end) {
        if (start.x < 0 || start.x >= mBoard.getSize() || start.y < 0 || start.y >= mBoard.getSize())
            return false;
        Piece p = mBoard.pieceAt(start);
        if (p != null && p.player == mAcvitePlayer && p.isValidMove(mBoard, end)) {
            mBoard.executeMove(start, end);
            checkGameOver();
            if (!mGameOver) {
                switchPlayer();
            }
            return true;
        }
        return false;
    }

    private void checkGameOver() {
        for (King k : mKings) {
            if (!k.alive) {
                mGameOver = true;
                break;
            }
        }
    }

    public boolean isGameOver() {
        return mGameOver;
    }

    public Board getBoard() {
        return mBoard;
    }

    public Player getActivePlayer() {
        return mAcvitePlayer;
    }

    public boolean isValidSelection(Point position) {
        Piece p = mBoard.pieceAt(position);
        return p != null && p.player == mAcvitePlayer;
    }
}
