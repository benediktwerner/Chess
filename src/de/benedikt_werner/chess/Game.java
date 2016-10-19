package de.benedikt_werner.chess;

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
	
	public Board getBoard() {
		return mBoard;
	}
	
	public Player getActivePlayer() {
		return mAcvitePlayer;
	}
}
