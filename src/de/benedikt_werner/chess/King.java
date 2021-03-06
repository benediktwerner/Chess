package de.benedikt_werner.chess;

import java.awt.Point;

public class King extends Piece {
    protected static final String PIECE_ID = "K";

    public King(Point startPosition, Player player) {
        super(startPosition, player);
    }

    @Override
    protected boolean canMove(Board board, Point endPos) {
        int xDiff = Math.abs(position.x - endPos.x);
        int yDiff = Math.abs(position.y - endPos.y);
        if (xDiff > 1 || yDiff > 1) return false;
        else return true;

    }

    @Override
    protected boolean canHit(Board board, Point endPos) {
        return canMove(board, endPos);
    }

    public String getPieceId() {
        return PIECE_ID;
    }
}
