package de.benedikt_werner.chess;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TextGUI {
	
	private static final int OUTPUT_WIDTH = 12;

	private Game mGame;
	private Board mBoard;
	private Point mSelection = new Point(-1, -1);
	
	private JFrame frame;
	private JTextPane output;
	private JLabel lblComment;
	private JLabel lblPlayer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Game game = new Game();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextGUI window = new TextGUI(game);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TextGUI(Game game) {
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		mGame = game;
		mBoard = mGame.getBoard();
		initialize();
		drawBoard();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("JChess");
		frame.setBounds(100, 100, 450, 315);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		output = new JTextPane();
		output.setBounds(10, 11, 200, 250);
		output.setContentType("text/html");
		frame.getContentPane().add(output);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Optional<Point> point = getSelection();
				if (point.isPresent()) {
					Point p = point.get();
					if (mGame.isValidSelection(p)) {
						comment("Selected (" + p.x + "|" + p.y + ")");
						mSelection = p;
						drawBoard();
					}
					else {
						comment("Invalid selection");
					}
				}
			}
		});
		btnSelect.setBounds(220, 11, 89, 23);
		frame.getContentPane().add(btnSelect);
		
		JButton btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mSelection.x == -1 || mSelection.y == -1) {
					comment("No piece selected");
					return;
				}
				Optional<Point> point = getSelection();
				if (point.isPresent()) {
					Point p = point.get();
					if (mGame.executeMove(mSelection, p)) {
						comment("Moved " + mBoard.pieceAt(p) + " at (" + mSelection.x + "|" + mSelection.y + ") to (" + p.x + "|" + p.y + ")");
						drawBoard();
					}
					else {
						comment("Invalid move");
					}
				}
			}
		});
		btnMove.setBounds(220, 45, 89, 23);
		frame.getContentPane().add(btnMove);
		
		lblPlayer = new JLabel("WHITE");
		lblPlayer.setBounds(378, 11, 46, 14);
		frame.getContentPane().add(lblPlayer);
		
		lblComment = new JLabel("");
		lblComment.setBounds(220, 108, 204, 40);
		frame.getContentPane().add(lblComment);
	}
	
	private Optional<Point> getSelection() {
		int selectionStart = output.getSelectionStart();
		int selectionEnd = output.getSelectionEnd();
		if (selectionEnd - selectionStart == 1) {
			int x = selectionStart / OUTPUT_WIDTH - 2;
			int y = selectionStart % OUTPUT_WIDTH - 3;
			if (x >= 0 && y >= 0 && x < 8 && y < 8) {
				return Optional.of(new Point(x, y));
			}
		}
		comment("Invalid selection");
		return Optional.empty();
	}
	
	private void drawBoard() {
		final String SPACE = "&#x0020;";
		
		Piece[][] board = mBoard.getBoard();
		String countTop = SPACE + "|";
		String line = "-+";
		for (int i = 0; i < board.length; i++) {
			countTop += i;
			line += "-";
		}
		countTop += "|";
		line += "+";
		String boardString = "<html><span style='font-family: Monospaced'>" + countTop + "<br>" + line + "<br>";
		for (int x = 0; x < board.length; x++) {
			String s = x + "|";
			for (int y = 0; y < board[x].length; y++) {
				Piece p = board[x][y];
				if (x == mSelection.x && y == mSelection.y) {
					s += "<u>" + (p == null ? SPACE : p) + "</u>";
				}
				else {
					s += (p == null) ? SPACE : p;
				}
			}
			boardString += s + "|<br>";
		}
		boardString += line + "</span></html>";
		output.setText(boardString);
		lblPlayer.setText(mGame.getActivePlayer().toString());
		if (mGame.isGameOver()) {
			JOptionPane.showMessageDialog(frame, "Game over! Player " + mGame.getActivePlayer() + " won!", "Game over", JOptionPane.OK_OPTION);
		}
	}
	
	private void comment(String text) {
		lblComment.setText(text);
	}
}
