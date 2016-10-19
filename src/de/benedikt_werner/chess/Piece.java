package de.benedikt_werner.chess;

import java.awt.Point;

public abstract class Piece {
	public Point position;
	public boolean alive;
	public Player player;
	
	public Piece(Point startPosition, Player player) {
		this.position = startPosition;
		this.player = player;
		alive = true;
	}

	protected abstract boolean canMove(Board board, Point endPos);
	protected abstract boolean canHit(Board board, Point endPos);
	
	public boolean isValidMove(Board board, Point endPos) {
		if (endPos.x < 0 || endPos.x >= board.getSize() || endPos.y < 0 || endPos.y >= board.getSize() || endPos == position) {
			return false;
		}
		
		Piece endPosPiece = board.getPiece(endPos);
		if (endPosPiece == null) {
			return canMove(board, endPos);
		}
		else if (endPosPiece.player == this.player) {
			return false;
		}
		else { // Enemy piece on endPos
			return canHit(board, endPos);
		}
	}
	
	public String toString() {
		switch (player) {
			case BLACK: return getPieceId().toLowerCase();
			case WHITE: return getPieceId().toUpperCase();
			default: throw new IllegalStateException("Piece has an illegal player type: " + player);
		}
	}
	
	public abstract String getPieceId();
}
