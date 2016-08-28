import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Screen extends JPanel{

    private final int width;
    private final int height;
    int counter;
    int frames = 0;
    int timeMs;
    
    double originVectorX = 1;
    double originVectorY = 0;
    
    public PlayerObject player;
    
    double Xpusher = 0;
    double Ypusher = 0;
    double eq;
    
    boolean paused = false;
    ArrayList<GameObject> objects = new ArrayList<GameObject>();
    
    Background bg;
    public Screen(int w, int h, int tm){
        this.width = w;
        this.height = h;
        player = new PlayerObject(width / 2, 200, 50);
        bg = new Background(width, height);
        objects.add(player);
        timeMs = tm;
    }
    
    @Override
    protected void paintComponent(Graphics origing){
        Graphics2D g = (Graphics2D) origing;
        RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_TEXT_ANTIALIASING,
             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHints(rh);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        bg.draw(g, timeMs);
        g.setColor(Color.WHITE);
        for(GameObject ob: objects){
            ob.draw(g);   
        }
        if(player.shots.size() > 0){
        	for(GameObject ob: player.shots){
        		ob.draw(g);
        		ob.move(270, timeMs);
        		if(ob.y < 0){
        			player.shots.remove(this);
        		}
        	}
        }
         player.update(g, width, height);
    }
   
    
    void render(boolean[] keys){
    	frames++;
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
        repaint();    
    }
    public int countFps(){
    	int ret = frames;
    	frames = 0;
    	return ret;
    }
}
