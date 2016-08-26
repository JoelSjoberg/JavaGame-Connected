import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class PlayerObject extends GameObject {

	int maxHp = 100, hp = maxHp;
	int Interval = 500;
	boolean shooting = false, invulnerable = false;
	
	ArrayList<GameObject> shots = new ArrayList();
	
	public PlayerObject(int x, int y, int size) {
		super(x, y, size);
	}

	public void update(Graphics2D g, int w, int h){
		// UI 
		int barW = hp * 100 /maxHp , barH = 15, outlineW = 100;
		int barX = w - 200, barY = h - 70;
		g.drawString("HP:" + hp + " / " + maxHp, barX , barY);
		g.setColor(Color.green);
		g.fillRect(barX, barY, barW, barH);
		g.setColor(Color.WHITE);
		g.drawRect(barX, barY, outlineW, barH);
		
		//actions
		if(shooting){
			shots.add(new GameObject(x, y, 10, 200));
		}
	}

}
