

import java.awt.Color;
import java.awt.Graphics2D;

public class GameObject {
    int x, y, size;
    public GameObject(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
    }
    public GameObject(int x, int y, int size, int speed){
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
    }
    int speed = 160;
    int counter;
    int frames = 0;
    int lastAngle;
    
    int damage = 25;
    
    double originVectorX = 1;
    double originVectorY = 0;
    
    double[] vector = {1, 0};
    
    double Xpusher = 0;
    double Ypusher = 0;
    double eq;
    Color color = Color.WHITE;
    
    void draw(Graphics2D g){
       g.setColor(color);
       g.drawOval(x - size/2, y - size/2, size, size);
   }
    void setColor(Color c){
        this.color = c;
    }
    public void move(int currentAngle, int timeMs){
        //originVectorX need to be reset to one each itteration to rotate from vector (1,0)
        //speed++;
        //size++;
        eq = (double) 1000/timeMs/speed;
        vector[0] = 1/eq;
        Xpusher += vector[0] * Math.cos(Math.toRadians(currentAngle) 
            - vector[1] * Math.sin(Math.toRadians(currentAngle)));
        Ypusher += vector[0] * Math.sin(Math.toRadians(currentAngle))
            + vector[1] * Math.cos(Math.toRadians(currentAngle));
         
        
        // X and Y need to be incremented when they are >= 1
        // the whole number is how many pixels to move and the remainder
        // MUST BE SAVED!(for science)
        if(Xpusher > 1 || Xpusher < -1){
            this.x += Math.round(Xpusher)*1000 / 1000.0;
            Xpusher -= Math.round(Xpusher);
        }if(Ypusher > 1 || Ypusher < -1){
            this.y += Math.round(Ypusher) * 1000 / 1000.0;
            Ypusher -= Math.round(Ypusher);
        }
        //Print out info on vartiables 
        //System.out.println("Frames: " + frames + " | eq = " + 1000 / timeMs / speed + "    | Ypusher: " + Ypusher + " | Xpusher: " + Xpusher);
        //System.out.println("X: " + x + "    |   Y: " + y);
    }
    // Collision detection with circles!
    double xdif, ydif, distance;
    public boolean collide(GameObject ob, Graphics2D g){
        xdif = this.x - ob.x;
        ydif = this.y - ob.y;
        //the circles collide if the vector between them = the sum of their size/2(radius)
        distance = Math.sqrt((Math.pow(xdif, 2) + Math.pow(ydif, 2)));
        // put in a 5px safe-space
        return (this.size/2 + ob.size/2) - 5 >= distance;
    }
}
