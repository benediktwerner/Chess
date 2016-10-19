package de.benedikt_werner.chess;

import java.awt.Point;

public class Knight extends Piece {
	protected static final String PIECE_ID = "K";

	public Knight(Point startPosition, Player player) {
		super(startPosition, player);
	}

	@Override
	protected boolean canMove(Board board, Point endPos) {
		int xDiff = Math.abs(position.x - endPos.x);
		int yDiff = Math.abs(position.y - endPos.y);
		return (xDiff == 2 && yDiff == 1) || (xDiff == 1 && yDiff == 2);
	}

	@Override
	protected boolean canHit(Board board, Point endPos) {
		return canMove(board, endPos);
	}
	
	public String getPieceId() {
		return PIECE_ID;
	}
}
