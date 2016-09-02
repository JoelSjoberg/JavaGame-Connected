import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class PlayerObject extends GameObject {

	int maxHp = 100, hp = maxHp;
	int hpLen = 100;
	
	//regular shooting
	int interval = 200;
	long lastShot = 0;
	int shotSize = 4, shotSpeed = 200;
	int[] shotOrigin = {shotSize, shotSpeed};
	boolean shooting = false, invulnerable = false;
	
	//charge variables
	int maxCharge = 15;
	int charge = 0;
	int chargeLen = 50;
	int chargeTime = 100;
	long currChargeTime = 0;
	long invulTime = 0;
	long invulDuration = 500;
	
	ArrayList<GameObject> shots = new ArrayList();
	
	public PlayerObject(int x, int y, int size) {
		super(x, y, size);
	}

	public void update(Graphics2D g, int w, int h){
		// UI 
		int barW = hp * hpLen /maxHp , barH = 15; 
		int barX = w - 200, barY = h - 70;
		int outlineW = hpLen;
		
		g.setColor(Color.green);
		g.fillRect(barX, barY, barW, barH);
		g.setColor(Color.WHITE);
		g.drawString("HP:" + hp + " / " + maxHp, barX, barY + 15);
		
		// White outline
			// hp
		g.drawRect(barX, barY, outlineW, barH);
		
			// Charger
		g.drawRect(x, y + size/2, chargeLen, barH/2);
		
		if(invulTime < System.currentTimeMillis()){
			this.invulnerable = false;
		}
		
		//actions
		if(shooting){
			if(System.currentTimeMillis() - lastShot > interval){
				shots.add(new GameObject(x, y, shotSize, shotSpeed));
				lastShot = System.currentTimeMillis();
				shotSize = shotOrigin[0];
				shotSpeed = shotOrigin[1];
				charge = 0;
			}
			
		}else{
			charge();
			//Charge meter
			if(maxCharge == charge){
				g.setColor(Color.GREEN);				
			}else g.setColor(Color.CYAN);
			g.fillRect(x, y+ size / 2, charge * chargeLen / maxCharge, barH / 2);
		}
	}
	
	void charge(){
		if(System.currentTimeMillis() - currChargeTime > chargeTime && maxCharge > charge){
			currChargeTime = System.currentTimeMillis();
			charge++;
			shotSize += 1;
			shotSpeed += 20;
		}
	}
	void hurt(int damage){
		this.invulTime = System.currentTimeMillis() + invulDuration;
		this.invulnerable = true;
		this.hp -= damage;
	}
}
