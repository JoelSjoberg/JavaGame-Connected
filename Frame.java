import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel{
	private static final long serialVersionUID = 1L;
	final int width = 640;int height = 560;
	int cells;
	int cellWidth;
	int cellHeight;
	int selectorX;
	int selectorY;
	JFrame frame;
	Piece[] board;
	int boxX, boxY;
	ArrayList<Integer> availableMoves = new ArrayList<Integer>();
	
	public Frame(int c, Piece[] b){
		cells = c;
		board = b;
		board = new Piece[cells*cells];
		frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Press 'n' to start new game");
		frame.setVisible(true);
		selectorX = getWidth();
		selectorY = getHeight();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		cellWidth = frame.getWidth() / cells - 2;
		cellHeight = frame.getHeight() / cells - 5;
		
		g2.setColor(new Color(245,184,0));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(new Color(184,138,0));
		
															// DRAW THE BOARD
		for(int y = 0; y < getHeight(); y += cellHeight * 2){
			for(int x = 0; x < getWidth(); x += cellWidth * 2){
				g2.fillRect(x, y, cellWidth, cellHeight);
			}			
		}
		for(int y = cellHeight; y < getHeight(); y += cellHeight * 2){
			for(int x = cellWidth; x < getWidth(); x += cellWidth * 2){
				g2.fillRect(x, y, cellWidth, cellHeight);
			}			
		}
		
		// show available moves
		g2.setColor(Color.blue);
		for(int i = 0; i < availableMoves.size(); i++){
			boxX = (availableMoves.get(i) % cells) * cellWidth;
			boxY = availableMoves.get(i) / cells * cellHeight;
			g2.fillRect(boxX, boxY, cellWidth, cellHeight);
		}
															// show where the player is
		g2.setColor(Color.CYAN);
		g2.fillRect(selectorX, selectorY, cellWidth, cellHeight);
		
															// draw the pieces on the board
		for(int i = 0; i < board.length; i++){
			if(board[i] != null){
				if(board[i].getType() == 1)g.setColor(Color.WHITE);			
				g2.drawImage(board[i].getImage(),(i % cells) * cellWidth, (i / cells) * cellHeight, cellWidth, cellHeight, null);
			}
		}
	}
	
	public void setBoard(Piece[] b){this.board = b;}
	public void setX(int x) {selectorX = (x / cellWidth) * cellWidth;}
	public void setY(int y) {selectorY = (y / cellHeight) * cellHeight;}
	public void setMoves(ArrayList ls) {availableMoves = ls;}
	public void clearMoves(){ availableMoves.clear();}
}