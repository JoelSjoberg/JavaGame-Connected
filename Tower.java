import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Tower implements Piece<Object>{

	private int position;
	private int cells;
	private int type;
	private Image ally;
	private Image enemy;
	boolean up, down, right, left;
	
	public Tower(int c, int pos, int t){
		cells = c;
		position = pos;
		type = t;
		try {
			ally = ImageIO.read(new File("src/img/ta.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			enemy = ImageIO.read(new File("src/img/te.png"));
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
		up = true;
		down = true;
		left = true;
		right = true;
		
		for(int i = 1; i <= cells; i++){
			if (up && position - (cells * i) > -1){
				if(board[position - (cells * i)] != null && board[position - (cells * i)].getType() == this.type){
					up = false;
				}
				else if (isEnemyAt(position - (cells * i), board)){
					up = false;
					availableMoves.add(position - (cells * i));					
				}else{
					availableMoves.add(position - (cells * i));
				}
			}
			
			if (down && position + (i * cells) < cells * cells - 1){
				if(board[position + (cells * i)] != null && board[position + (cells * i)].getType() == this.type){
					down = false;
				}
				else if (isEnemyAt(position + (cells * i), board)){
					down = false;
					availableMoves.add(position + (cells * i));					
				}else{
					availableMoves.add(position + (cells * i));
				}
			}
			
			if (left){
// TODO: make edge cases work
				// if piece is on the left edge	or left is outside board  or  (cell to left has a piece	and	that piece is your piece)
				if((position - (i - 1)) % cells == 0 || position - i < 0 ||(board[position - i] != null && board[position - i].getType() == this.type)){
					left = false;
				}
				else if (isEnemyAt(position - i, board)){
					left = false;
					availableMoves.add(position - i);					
				}else{
					availableMoves.add(position - i);
				}
			}
			
			if (right){
				if ((position + (i - 1) + 1) % cells == 0 || position + i > cells * cells || (board[position + i] != null && board[position + i].getType() == this.type)){
					right = false;
				}
				else if (isEnemyAt(position + i, board)){
					right = false;
					availableMoves.add(position + i);
				}else{
					availableMoves.add(position + i);
				}
			}
		}
		return availableMoves;
		
		/*for(int i = 0; i < cells; i++){
			// look upwards on the board
			try{
				if((board[position - cells * i] == null || isEnemyAt(position - cells * i, board)) && up){
					availableMoves.add(position - cells * i);
					if(isEnemyAt(position - cells * i, board)) up = false;
					if(board[position + cells * i] == null) up = false;
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				up = false;
			}
			
			// look downwards on the board
			try{
				if((board[position + cells * i] == null || isEnemyAt(position + cells * i, board)) && down){
					availableMoves.add(position + cells * i);
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				down = false;
			}
			
			// look to the left on the board
			try{
				if(((board[position - i] == null || isEnemyAt(position - i, board))) && left){
					if(position - i % cells - 1 != 0){
						left = false;
					}else{
						availableMoves.add(position - i);						
					}
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				left = false;
			}
			
			// look to the right on the board
			try{
				if(((board[position + i] == null || isEnemyAt(position + i, board)) && position + i % cells != 0) && right){
					availableMoves.add(position + i);
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				right = false;
			}
		}
		return availableMoves;
		*/
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
		if(this.type == 1)return "t" ;
		else return "T";
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