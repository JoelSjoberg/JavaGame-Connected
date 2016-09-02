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

    private final int width;
    private final int height;
    int counter;
    int frames = 0;
    int timeMs;
    
    int level = 0, section = 0;
    int points = 0;  
    
    long spawnTime = 1000;
    long sysSpawnTime = System.currentTimeMillis() + spawnTime;
    
    public PlayerObject player;
    public Level levels = new Level();
    
    boolean paused = false;
    
    // for opacity
    float alpha = 1.0f;
    ArrayList<GameObject> enemies = new ArrayList<GameObject>();

    Background bg;
    public Screen(int w, int h, int tm){
        this.width = w;
        this.height = h;
        player = new PlayerObject(width / 2, 200, 50);
        bg = new Background(width, height);
        timeMs = tm; 
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
        	alpha = 1.0f;
	        g.setRenderingHints(rh);
	        g.setColor(Color.BLACK);
	        g.fillRect(0, 0, width, height);
	        bg.draw(g, timeMs);
	        
	        // draw every GameObject
	        player.draw(g);
        	for(GameObject ob: enemies){
        		ob.move(90, timeMs);
        		ob.draw(g);
        		if(ob.y > height) ob.y = 0;
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
        				ob.move(270, timeMs);
        				if(ob.y < 0){
        					player.shots.remove(this);
        				}
        				for(GameObject enemy: enemies){
        					if(ob.collide(enemy, g)){
        						enemies.remove(enemy);
        						player.shots.remove(ob);
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
	        g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
	        g.drawString(level +" - " + section, 10, 20);
	        g.drawString("Points:" + points, width - 100, 20);
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
    				System.out.println(true);
    				spawnTime = 300;
    				sysSpawnTime = System.currentTimeMillis() + spawnTime;
    				spawnEnemies(level, section);
    				section++;
// TODO: (a lot...) avoid indexoutofbounds if section > limit!
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
    			if(keys[37]){
    				player.move(180, timeMs);
    			}if(keys[38]){
    				player.move(270, timeMs);
    			}if(keys[39]){
    				player.move(0, timeMs);
    			}if(keys[40]){
    				player.move(90, timeMs);
    			}  
    		}
    		
    		// abilities
    		if(keys[65]){
    			player.shooting = true;
    		}else{
    			player.shooting = false;
    		}
    		if(keys[27]){
// TODO: make the pause more responsive, right now it lags and shifts the boolean
    			paused = true;
    		}
    		
    	}else{// game is paused
    		if(!keys[27]){
    			paused = false;
    		}
    	}
        repaint();    
    }
    
    public void spawnEnemies(int level, int section){
// TODO: make enemies appear in patterns
    	try{
    		for(int i = 0; i < levels.levels[level][section]; i++){
    			enemies.add(new GameObject((int)(Math.random()*(width - 50) + 1), 10, 50, 125));
    			enemies.get(i).color = Color.RED;
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
