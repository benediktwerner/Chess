package de.benedikt_werner.chess;

import java.awt.Point;

public class Game {
	private Board mBoard;
	private Player mAcvitePlayer;
	private King[] mKings;
	private boolean mGameOver;

	public Game() {
		mBoard = Board.getDefaultBoard();
		mKings = new King[2];
		mKings[0] = (King) mBoard.pieceAt(0, 3);
		mKings[1] = (King) mBoard.pieceAt(7, 3);
		mAcvitePlayer = Player.WHITE;
		mGameOver = false;
	}
	
	private void switchPlayer() {
		switch (mAcvitePlayer) {
			case WHITE: mAcvitePlayer = Player.BLACK; break;
			case BLACK: mAcvitePlayer = Player.WHITE; break;
		}
	}
	
	public boolean executeMove(Point start, Point end) {
		Piece p = mBoard.pieceAt(start);
		if (p.isValidMove(mBoard, end)) {
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
