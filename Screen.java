package connected;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Screen extends JPanel{

    private int width;
    private int height;
    int counter, timeMs;
    int frames = 0;

    double originVectorX = 1;
    double originVectorY = 0;
    
    public GameObject player, object;
    
    double Xpusher = 0;
    double Ypusher = 0;
    double eq;
    
    ArrayList<GameObject> objects = new ArrayList<GameObject>();
    public Screen(int w, int h, int tm){
        this.width = w;
        this.height = h;
        timeMs = tm;
        player = new GameObject(width / 2, 200, 50);
        object = new GameObject(width / 2, 200, 150);
        
        objects.add(object);
        objects.add(player);
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
        g.setColor(Color.BLUE);
        g.drawLine(player.x, player.y, object.x, object.y);
        
        
        for(GameObject ob: objects){
            ob.draw(g);   
        }
        if(player.collide(object, g)){
            player.setColor(Color.RED);
            objects.add(new GameObject(player.x, player.y, 5));
        }else{
            player.setColor(Color.WHITE);
        }
    }
   
    int currentAngle = (int) (Math.random() * 360 + 1);
    void render(boolean[] keys){
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
        repaint();    
    }
}
