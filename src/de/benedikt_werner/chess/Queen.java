package de.benedikt_werner.chess;

import java.awt.Point;

public class Queen extends Piece {
    protected static final String PIECE_ID = "Q";

    public Queen(Point startPosition, Player player) {
        super(startPosition, player);
    }

    @Override
    protected boolean canMove(Board board, Point endPos) {
        int xDiff = Math.abs(position.x - endPos.x);
        int yDiff = Math.abs(position.y - endPos.y);
        if (xDiff != yDiff && xDiff != 0 && yDiff != 0) return false;
        return board.isLineEmpty(position, endPos);

    }

    @Override
    protected boolean canHit(Board board, Point endPos) {
        return canMove(board, endPos);
    }

    public String getPieceId() {
        return PIECE_ID;
    }
}
