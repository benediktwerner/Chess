package de.benedikt_werner.chess;

import java.awt.Point;

public class Pawn extends Piece {
    protected static final String PIECE_ID = "P";

    public Pawn(Point startPosition, Player player) {
        super(startPosition, player);
    }

    @Override
    protected boolean canMove(Board board, Point endPos) {
        if (endPos.y != position.y) return false;

        if (player == Player.BLACK) {
            if (endPos.x >= position.x || endPos.x < position.x - 2 || board.pieceAt(position, -1, 0) != null)
                return false;
            else if (endPos.x < position.x - 1)
                return (position.x == 6) && (board.pieceAt(position, -2, 0) == null);
            else return true;
        }
        else {
            if (endPos.x <= position.x || endPos.x > position.x + 2 || board.pieceAt(position, +1, 0) != null)
                return false;
            else if (endPos.x > position.x + 1)
                return (position.x == 1) && (board.pieceAt(position, +2, 0) == null);
            else return true;
        }
    }

    @Override
    protected boolean canHit(Board board, Point endPos) {
        if (Math.abs(position.y - endPos.y) != 1) return false;

        if (player == Player.BLACK)
            return endPos.x == position.x - 1;
        else
            return endPos.x == position.x + 1;
    }

    public String getPieceId() {
        return PIECE_ID;
    }
}
