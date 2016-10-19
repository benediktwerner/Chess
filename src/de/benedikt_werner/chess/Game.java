package de.benedikt_werner.chess;

import java.awt.Point;

public class Game {
	private Board mBoard;
	private Player mAcvitePlayer;

	public Game() {
		mBoard = Board.getDefaultBoard();
		mAcvitePlayer = Player.WHITE;
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
			switchPlayer();
			return true;
		}
		return false;
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
