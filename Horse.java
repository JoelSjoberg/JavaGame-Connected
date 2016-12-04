import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Horse implements Piece{

	private int position;
	private int cells;
	private int type;
	private Image ally;
	private Image enemy;
	
	public Horse(int c, int pos, int t){
		cells = c;
		position = pos;
		type = t;
		try {
			ally = ImageIO.read(new File("src/img/ha.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			enemy = ImageIO.read(new File("src/img/he.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean isEnemyAt(int position, Piece[] board) {
		try{
			if(board[position] != null &&
					board[position].getType() != this.type) return true;			
		}catch(ArrayIndexOutOfBoundsException e){
			return true;
		}
		return false;
	}

	@Override
	public ArrayList getValidMoves(Piece[] board) {
		ArrayList<Integer> availableMoves = new ArrayList<Integer>();

								// furthest to the left
		if(position % cells > 1){
			availableMoves.add(position - 2 - cells);
			availableMoves.add(position - 2 + cells);			
		}
								// to the left
		if(position % cells > 0){
			availableMoves.add(position - (cells*2) - 1);
			availableMoves.add(position + (cells*2) - 1);			
		}
								// to the right
		if(position  % cells < cells - 1){
			availableMoves.add(position - (cells*2) + 1);
			availableMoves.add(position + (cells*2) + 1);			
		}
								// furthest to the right
		if(position  % cells < cells - 2){
			availableMoves.add(position + 2 + cells);
			availableMoves.add(position + 2 - cells);			
		}
		
		for(int i = availableMoves.size() - 1; i > -1; i--){
			//	check if the moves are inside the board
			if(availableMoves.get(i) > cells * cells - 1 || availableMoves.get(i) < 0 ||
			// 	or there is an ally piece on destination
			(board[availableMoves.get(i)] != null &&board[availableMoves.get(i)].getType() == this.type))
				availableMoves.remove(i);
		}
		
		return availableMoves;
	}

	@Override
	public void makeMove(int destination) {
		position = destination;
	}

	@Override
	public int getType() {
		return this.type;
	}

	@Override
	public int getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(int pos) {
		this.position = pos;
	}
	public String toString(){
		if(this.type == 1)return "h" ;
		else return "H";
	}


	@Override
	public Image getImage() {
		if(this.type == 1){
			return this.ally;
		}else{
			return this.enemy;
		}
	}
}
