import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JPanel;

public class Screen extends JPanel{

    int width;
    int height;
    int frames = 0;
    int timeMs;
    int px = 0, py = 200, pSize = 50;
    int level = 0, section = 0;
    int points = 0;  
    int bgMaxSpeed = 500, bgMinSpeed = 60; 
    int enemySpeed = 125, enemyAngle = 90;
    int shotDirection = 270;
    
    // input vars
    int up = 38, down = 40, left = 37, right = 39;
    int shoot = 65;
    int pause = 27;
    boolean paused = false;
    
    float alpha = 1.0f;

    long spawnTime = 3500;
    long sysSpawnTime = System.currentTimeMillis() + spawnTime;
    
    // text properties
    int fontSize = 22;
    int[] levelText = {10, 20};
    int[] pointText = {100, 20}; // x coord: from right 
    
    
    PlayerObject player;
    Level levels = new Level();
    

    ArrayList<GameObject> enemies = new ArrayList<GameObject>();
    Background bg;
    
    public Screen(int w, int h, int tm){
    	System.out.println(shoot);
        this.width = w;
        this.height = h;
        px = width / 2;
        player = new PlayerObject(px, py, pSize);
        bg = new Background(width, height);
        timeMs = tm;
        bg.speed = bgMaxSpeed;
    }
    
    @Override
    protected void paintComponent(Graphics origing){
        Graphics2D g = (Graphics2D) origing;
        // opacity for pause screen
        g.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha));
        RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_TEXT_ANTIALIASING,
             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        if(!(paused)){
        	//alpha = 1.0f;
	        g.setRenderingHints(rh);
	        g.setColor(Color.BLACK);
	        g.fillRect(0, 0, width, height);
	        bg.draw(g, timeMs);
	        
	        // draw every GameObject
	        player.draw(g);
	        try{
	        	for(GameObject ob: enemies){
	        		ob.move(enemyAngle, timeMs);
	        		ob.draw(g);
        				if(ob.y > height) enemies.remove(ob);        			
	        	}
	        }catch(ConcurrentModificationException e){
	        	
	        }
        	for(GameObject ob: enemies){
				if(player.collide(ob, g) && ! player.invulnerable){
					player.hurt(ob.damage);
				}
			}
        	if(player.shots.size() > 0){
        		try{
        			for(GameObject ob: player.shots){
        				ob.draw(g);
        				ob.move(shotDirection, timeMs);
        				if(ob.y < 0){
        					player.shots.remove(ob);
        				}
        				for(GameObject enemy: enemies){
        					if(ob.collide(enemy, g)){
        						enemies.remove(enemy);
        						if(!(ob.size > player.maxShotSize / 2)){
        							player.shots.remove(ob);        							
        						}
        						points++;
        					}
        				}
        			}        		
        		}catch(ConcurrentModificationException e){
        			
        		}
        	}        	
        	player.update(g, width, height);
        
        	// Draw the current level and section
	        g.setColor(Color.WHITE);
	        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
	        g.drawString(level +" - " + section, levelText[0], levelText[1]);
	        g.drawString("Points:" + points, width - pointText[0], pointText[1]);
        }else{// paused
        	alpha = 0.3f;
        	g.fillRect(0, 0, width, height);
        }
    }

// Screen methods
    void render(boolean[] keys){
    	frames++;
    	if(!(paused)){
    		
    		//--------------------- SPAWN ENEMIES-----------------
    		if((enemies.size() == 0 && System.currentTimeMillis() > sysSpawnTime) || System.currentTimeMillis() > sysSpawnTime){
    			try{
    				bg.speed = bgMinSpeed;
    				//spawnTime += 2000;
    				sysSpawnTime = System.currentTimeMillis() + spawnTime;
    				spawnEnemies(level, section);
    				section++;
    				if(section == levels.levels[level].length){
    					section = 0;
    					level++;
    				}    				
    			}catch(ArrayIndexOutOfBoundsException e){
    				
    			}
    		}
    		
    		// Handle user input
    		
    		// Moving
    		if(keys[37] && keys[38]) player.move(225, timeMs);
    		else if(keys[37] && keys[40]) player.move(135, timeMs);
    		else if(keys[38] && keys[39]) player.move(315, timeMs);
    		else if(keys[40] && keys[39]) player.move(45, timeMs);
    		else{
    			if(keys[left]){
    				player.move(180, timeMs);
    			}if(keys[up]){
    				player.move(270, timeMs);
    			}if(keys[right]){
    				player.move(0, timeMs);
    			}if(keys[down]){
    				player.move(90, timeMs);
    			}  
    		}
    		
    		// abilities
    		if(keys[shoot]){
    			player.shooting = true;
    		}else{
    			player.shooting = false;
    		}
    		if(keys[pause]){
// TODO: make the pause more responsive, right now it lags and shifts the boolean
    			paused = true;
    		}
    		
    	}else{// game is paused
//TODO: if player pauses make sure to update sysSpawnTime accordingly to avoid abuse of spawning
    		sysSpawnTime = System.currentTimeMillis();
    		if(!keys[pause]){
    			paused = false;
    		}
    	}
        repaint();    
    }
    
    public void spawnEnemies(int level, int section){
// TODO: make enemies appear in patterns
    	try{
    		for(int i = 0; i < levels.levels[level][section]; i++){
    			enemies.add(new GameObject(
    					(int)(Math.random()*(width - 50) + 1), 0, 50, enemySpeed));
    			enemies.get(i).setColor(Color.RED);
    		}    		
    	}catch(ArrayIndexOutOfBoundsException e){
    		System.out.println("No more levels");
    	}
    }
    
    public int countFps(){
    	int ret = frames;
    	frames = 0;
    	return ret;
    }
}
