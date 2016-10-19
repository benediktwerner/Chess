package de.benedikt_werner.chess;

import java.awt.Point;

public class Board {
	private Piece[][] mBoard;
	
	public Board() {
		this(8);
	}
	
	public Board(int size) {
		mBoard = new Piece[size][size];
	}
	
	public void setBoardFromString(String[] board) {
		mBoard = new Piece[board.length][board.length];
		for (int x = 0; x < board.length; x++) {
			String line = board[x];
			for (int y = 0; y < board.length; y++) {
				char c = line.charAt(y);
				Player p = Character.isUpperCase(c) ? Player.WHITE : Player.BLACK;
				Piece pc = null;
				c = Character.toUpperCase(c);
				
				switch (c) {
					case 'P': pc = new Pawn(new Point(x, y), p); break;
					case 'K': pc = new Knight(new Point(x, y), p); break;
					case 'R': pc = new Rook(new Point(x, y), p); break;
					case 'B': pc = new Bishop(new Point(x, y), p); break;
					case 'C': pc = new King(new Point(x, y), p); break;
					case 'Q': pc = new Queen(new Point(x, y), p); break;
				}
				mBoard[x][y] = pc;
			}
		}
	}
	
	public void executeMove(Point piecePos, Point endPos) {
		Piece piece = pieceAt(piecePos);
		Piece other = pieceAt(endPos);
		if (other != null) {
			other.alive = false;
			other.position = new Point(-1, -1);
		}
		piece.position = endPos;
		mBoard[endPos.x][endPos.y] = piece;
		mBoard[piecePos.x][piecePos.y] = null;
	}
	
	public Piece pieceAt(Point position) {
		return pieceAt(position.x, position.y);
	}
	
	public Piece pieceAt(Point position, int x, int y) {
		return pieceAt(position.x + x, position.y + y);
	}
	
	public Piece pieceAt(int x, int y) {
		return mBoard[x][y];
	}
	
	public boolean isEmpty(int x, int y) {
		return mBoard[x][y] == null;
	}
	
	public boolean isLineEmpty(int x1, int y1, int x2, int y2) {
		int xDiff = x2 - x1;
		int yDiff = y2 - y1;
		int xMove = (int) Math.signum(xDiff);
		int yMove = (int) Math.signum(yDiff);
		
		int moves = Math.max(Math.abs(xDiff), Math.abs(yDiff));
		
		for (int i = 1; i < moves; i++) {
			if (!isEmpty(x1 + (xMove * i), y1 + (yMove * i))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isLineEmpty(Point p1, Point p2) {
		return isLineEmpty(p1.x, p1.y, p2.x, p2.y);
	}
	
	public int getSize() {
		return mBoard.length;
	}
	
	public Piece[][] getBoard() {
		return mBoard;
	}
	
	public void print() {
		String countTop = " |";
		String line = "-+";
		for (int i = 0; i < mBoard.length; i++) {
			countTop += i;
			line += "-";
		}
		countTop += "|";
		line += "+";
		System.out.println(countTop);
		System.out.println(line);
		for (int x = 0; x < mBoard.length; x++) {
			String s = x + "|";
			for (int y = 0; y < mBoard[x].length; y++) {
				Piece p = mBoard[x][y]; 
				s += (p == null) ? " " : p;
			}
			System.out.println(s + "|");
		}
		System.out.println(line);
	}
	
	public static Board getDefaultBoard() {
		Board board = new Board();
		board.setBoardFromString(new String[] {
				"rkbcqbkr",
				"pppppppp",
				"        ",
				"        ",
				"        ",
				"        ",
				"PPPPPPPP",
				"RKBCQBKR"
		});
		return board;
	}
}
