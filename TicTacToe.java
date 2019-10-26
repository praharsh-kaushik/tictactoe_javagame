package GameDevelopment;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToe extends JFrame implements ActionListener {

	public static int BOARD_SIZE = 3;// size of TicTacToe Board

	public static enum GameStatus {
		Incomplete, XWins, ZWins, Tie
	}

	// buttons on which X or 0 will appear
	private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
	boolean crossTurn = true;

	public TicTacToe() {
		// making a frame/window to play a game on
		super.setTitle("TicTacToe Game");
		super.setSize(700, 700);

		// components are made to be present on a Grid form
		GridLayout grid = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		super.setLayout(grid);
		Font font = new Font("Comic Sans", 1, 150);

		// making jbuttons on the frame/grid
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				JButton button = new JButton("");
				buttons[row][col] = button;
				button.setFont(font);
				button.addActionListener(this);// all buttons are attached to ActionListeners
				super.add(button);
			}
		}

		super.setResizable(false);// frame size can not be changed
		super.setVisible(true);// makes frame visible on the desktop

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton clickedButton = (JButton) e.getSource();// action happened due to a button
		makeMove(clickedButton);
		GameStatus gs = this.getGameStatus();// check status after every move
		if (gs == GameStatus.Incomplete) {
			return;// if no one has won yet
		}
		declareWinner(gs);

		int choice = JOptionPane.showConfirmDialog(this, "Wanna play again?");
		if (choice == JOptionPane.YES_OPTION) {// reset all the buttons to play again
			for (int row = 0; row < BOARD_SIZE; row++) {
				for (int col = 0; col < BOARD_SIZE; col++) {
					buttons[row][col].setText("");
				}
			}
			crossTurn = true;
		} else {
			super.dispose();// terminate game
		}
	}

	private void makeMove(JButton clickedButton) {

		String buttonText = clickedButton.getText();// X or 0 or ""
		if (buttonText.length() > 0) {
			JOptionPane.showMessageDialog(this, "Invalid Move");// shows a message dialog
		} else {
			if (crossTurn) {
				clickedButton.setText("X");
			} else {
				clickedButton.setText("0");
			}
			crossTurn = !crossTurn;
		}

	}

	private GameStatus getGameStatus() {

		String text1 = "";
		String text2 = "";

		int row;
		int col;

		// checking text inside rows
		row = 0;
		while (row < BOARD_SIZE) {
			col = 0;
			while (col < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row][col + 1].getText();

				if (!text1.equals(text2) || text1.length() == 0) {
					break;// check for next row
				}
				col++;
			}

			if (col == BOARD_SIZE - 1) {// if all of the buttons in row are of one type
				if (text1.equals("X")) {
					return GameStatus.XWins;
				} else {
					return GameStatus.ZWins;
				}
			}
			row++;
		}

		// checking text inside columns
		col = 0;
		while (col < BOARD_SIZE) {
			row = 0;
			while (row < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row + 1][col].getText();

				if (!text1.equals(text2) || text1.length() == 0) {
					break;// check for next column
				}
				row++;
			}

			if (row == BOARD_SIZE - 1) {// if all of the buttons in column are of one type
				if (text1.equals("X")) {
					return GameStatus.XWins;
				} else {
					return GameStatus.ZWins;
				}
			}
			col++;
		}

		// checking text inside diagonal
		row = 0;
		col = 0;
		while (row < BOARD_SIZE - 1) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row + 1][col + 1].getText();

			if (!text1.equals(text2) || text1.length() == 0) {
				break;// check for next win combination
			}
			row++;
			col++;

			if (row == BOARD_SIZE - 1) {// if all of the buttons in diagonal are of one type
				if (text1.equals("X")) {
					return GameStatus.XWins;
				} else {
					return GameStatus.ZWins;
				}
			}
		}

		// text inside off-diagonal
		row = BOARD_SIZE - 1;
		col = 0;
		while (row > 0) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row - 1][col + 1].getText();

			if (!text1.equals(text2) || text1.length() == 0) {
				break;// check for next win combination
			}
			row--;
			col++;
		}

		if (row == 0) {// if all of the buttons in off-diagonal are of one type
			if (text1.equals("X")) {
				return GameStatus.XWins;
			} else {
				return GameStatus.ZWins;
			}
		}

		// if no player has won yet
		String text = "";
		for (row = 0; row < BOARD_SIZE; row++) {
			for (col = 0; col < BOARD_SIZE; col++) {
				text = buttons[row][col].getText();
				if (text.length() == 0) {
					return GameStatus.Incomplete;
				}
			}
		}

		// if above loop is not executed
		return GameStatus.Tie;
	}

	private void declareWinner(GameStatus gs) {// using a dialog box

		if (gs == GameStatus.XWins) {
			JOptionPane.showMessageDialog(this, "X Player Wins");
		} else if (gs == GameStatus.ZWins) {
			JOptionPane.showMessageDialog(this, "Z Player Wins");
		} else {
			JOptionPane.showMessageDialog(this, "It's a Tie");
		}
	}

	public static void main(String[] args) {

		TicTacToe game = new TicTacToe();

	}

}
