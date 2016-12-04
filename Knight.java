import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Knight implements Piece{
	
	private int position;
	private int cells;
	private int type;
	private Image ally;
	private Image enemy;
	
	public Knight(int c, int pos, int t){
		cells = c;
		position = pos;
		type = t;
		try {
			ally = ImageIO.read(new File("src/img/pa.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			enemy = ImageIO.read(new File("src/img/pe.png"));
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
		
		for(int i = 1; i < cells; i++){
			availableMoves.add(position - (cells * i) + i);
			availableMoves.add(position - (cells * i) - i);
			availableMoves.add(position + (cells * i) + i);
			availableMoves.add(position + (cells * i) - i);
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
		if(this.type == 1)return "p" ;
		else return "P";
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