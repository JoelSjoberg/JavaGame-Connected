import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

public class Logic{
	
	private static Piece[] grid;
	static ArrayList<Piece> playerPieces;
	static ArrayList<Piece> enemyPieces;
	private static int cells;
	private static Frame frame;
	private static int x, y, index;
	private static Keyboard key = new Keyboard();
	static Random rand = new Random();
	static int pos = -1, dest = -1;
	static boolean checker;
	
	private static void updateGrid(){
		// update the whole grid before printing
		for(int i = 0; i < grid.length; i++){
			if(grid[i] != null && grid[i].getPosition() != i){
				grid[grid[i].getPosition()] = grid[i];
				grid[i] = null;
			}
		}
	}
	
	// fill the grid with pieces at the beginning of the game
	private static void fillGrid(){
		enemyPieces = new ArrayList<Piece>();
		playerPieces = new ArrayList<Piece>();
		Piece p;
														// Enemy pieces
		// Tower
		p = new Tower(cells, 0, 0);
		grid[0] = p;
		enemyPieces.add(p);
		p = new Tower(cells, 7, 0);
		grid[7] = p;
		enemyPieces.add(p);
		
		// Horse
		p = new Horse(cells, 1, 0);
		grid[1] = p;
		enemyPieces.add(p);
		p = new Horse(cells, 6, 0);
		grid[6] = p;
		enemyPieces.add(p);
		
		// Knight
		p = new Knight(cells, 2, 0);
		grid[2] = p;
		enemyPieces.add(p);
		p = new Knight(cells, 5, 0);
		grid[5] = p;
		enemyPieces.add(p);
		
		// Queen
		p = new Queen(cells, 3, 0);
		grid[3] = p;
		enemyPieces.add(p);
		
		// King
		p = new King(cells, 4, 0);
		grid[4] = p;
		enemyPieces.add(p);

		// peasants
		for(int i = 0; i < grid.length; i++){
			if(i <= (cells * 2) - 1 && i >= cells || i >= (cells * 6) && i <= (cells * 7) - 1 ){
				
				if(i < grid.length / 2){
					//enemy pieces type is 0
					p = new Peasant(cells, i, 0);
					grid[i] = p;
					enemyPieces.add(p);
					
				}else if(i > grid.length / 2){
					// player pieces type is 1
					p = new Peasant(cells, i, 1);
					grid[i] = p;
					playerPieces.add(p);
				}
			}
		}
														//Player pieces 
		// Tower
		p = new Tower(cells, 56, 1);
		grid[56] = p;
		playerPieces.add(p);
		p = new Tower(cells, 63, 1);
		grid[63] = p;
		playerPieces.add(p);
		
		// Horse
		p = new Horse(cells, 57, 1);
		grid[57] = p;
		playerPieces.add(p);
		p = new Horse(cells, 62, 1);
		grid[62] = p;
		playerPieces.add(p);
		
		// Knight
		p = new Knight(cells, 58, 1);
		grid[58] = p;
		playerPieces.add(p);
		p = new Knight(cells, 61, 1);
		grid[61] = p;
		playerPieces.add(p);
		
		// Queen
		p = new Queen(cells, 60, 1);
		grid[60] = p;
		playerPieces.add(p);
		
		// King
		p = new King(cells, 59, 1);
		grid[59] = p;
		playerPieces.add(p);
	}
	
	
	
	// the AI controll
	private static void getCompMove(){
		
		/* AI algorithm
		 * 
		 1. pos = enemiesCopy.get(rand.nextInt(enemiesCopy.size())).getPosition();		
		 
		 2. if (grid[pos].getValidMoves(grid).size() == 0) throw new ArrayIndexOutOfBoundsException();

		 3. dest = (int) grid[pos].getValidMoves(grid).get(rand.nextInt(grid[pos].getValidMoves(grid).size()));
		 
		 4. if (!(grid[pos].getValidMoves(grid).contains(dest))) throw new ArrayIndexOutOfBoundsException();
		 
		 */
		
		// enemiesCopy is used to reduce load time for ai
		@SuppressWarnings("rawtypes")
		ArrayList<Piece> enemiesCopy = new ArrayList<Piece>(enemyPieces);
		System.out.println("Computers turn! Pieces: " + enemyPieces);
		do {
			checker = true;
														//1: Select a random piece
			
			try {// IllegalArgumentException will appear if size = 0
				System.out.println(enemiesCopy.size());
				pos = enemiesCopy.get(rand.nextInt(enemiesCopy.size())).getPosition();								
			
			} catch(IllegalArgumentException e){
				System.out.println("AI cannot make any moves");
				pos = -1; dest = -1;
				break;
			}
			
														//2: if there are no available moves: loop
			try {
				if (grid[pos].getValidMoves(grid).size() == 0) throw new ArrayIndexOutOfBoundsException();
				System.out.println(grid[pos].getValidMoves(grid));
			} catch (ArrayIndexOutOfBoundsException e){	
				
				System.out.println(pos + " " + dest);
				enemiesCopy.remove(grid[pos]);
				checker = false;
				continue;
				
			} catch (NullPointerException e1){
				System.out.println("something went wrong with pos: " + pos);
				//System.exit(0);
				e1.printStackTrace();
				continue;
			}
			
														//3: Select random destination of given moves
			dest = (int) grid[pos].getValidMoves(grid).get(rand.nextInt(grid[pos].getValidMoves(grid).size()));
						
														//4: if the input destination is not in the available moves: loop
			try {
				if (!(grid[pos].getValidMoves(grid).contains(dest))) throw new ArrayIndexOutOfBoundsException();				
			} catch(ArrayIndexOutOfBoundsException e){
				System.out.println("problem with Dest");
				checker = false;
				continue;	
			}
			grid[pos].makeMove(dest);
			System.out.println("The opponent moved " + pos + " to " + dest);
		} while (!checker);
		
		if (dest != -1 && grid[dest] != null && grid[dest].getType() == 1){
			playerPieces.remove(grid[dest]);
		}
	}// end of getCompMove
	
	// game will run here
	
	static boolean playerTurn = true;
	private static void beginGame(){
		fillGrid();
		frame.addKeyListener(key);
		frame.setFocusable(true);
		while(true){
			frame.repaint();
			frame.revalidate();
			frame.setBoard(grid);
			frame.setX(x);
			frame.setY(y);
			if(key.getKeys()[(int) 'n'] == true){
				fillGrid();
				playerTurn = true;
			}
			updateGrid();
			if(!playerTurn){
				getCompMove();				
				playerTurn = true;
			}
			updateGrid();
		}
	}
	
	public static void main(String args[]){
		cells = 8;
		grid = new Piece[cells*cells];
		playerPieces = new ArrayList<Piece>();
		enemyPieces = new  ArrayList<Piece>();
		frame = new Frame(cells, grid);
		
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e){
				x = e.getX();
				y = e.getY();
			}
		});
													// Player input comes from mouse-events
		frame.addMouseListener(new MouseAdapter() {
			boolean playerSelected = false;
			int SelectedPlayer = -1;
			
			public void mouseExited(MouseEvent e){
				x = frame.getHeight();
				y = frame.getWidth();
			}
			public void mouseClicked(MouseEvent e){
				index = (x / (frame.getWidth() / cells)) + (y / (frame.getHeight()/ cells)) * cells;
													// right-click
				if(e.getButton() == 3){
					frame.clearMoves();
					playerSelected = false;
				}				
													// Choose player and give frame info to draw
				if (!playerSelected && playerTurn){
					if(grid[index] != null){
						frame.setMoves(grid[index].getValidMoves(grid));
						SelectedPlayer = index;
						playerSelected = true;
					}else{
													// If the wrong cell is picked, remove info from frame
						frame.clearMoves();
					}
					
				}else if (playerSelected && playerTurn){
					try{
						if (grid[SelectedPlayer].getValidMoves(grid).contains(index)){
													// remove enemy piece from memory if they clash!
							if (grid[SelectedPlayer].isEnemyAt(index, grid)){
								enemyPieces.remove(grid[index]);
							}
							
							grid[SelectedPlayer].makeMove(index);
							playerTurn = false;
						}
						
					}catch(NullPointerException e1){
						System.out.println(SelectedPlayer);
					}
					
					playerSelected = false;
					frame.clearMoves();
				}
			}
		});
		beginGame();
	}
}