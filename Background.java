import java.awt.Color;
import java.awt.Graphics2D;

public class Background {
	int width, height, speed = 60;
	double pusher = 0;
	double eq;
	int[] yPos;
	
	public Background(int w, int h){
		this.height = h;
		this.width = w;
		// the y position of the stars
		yPos = new int[w];
		for(int i = 0; i < w; i++){
			yPos[i] = height;
		}
	}
	public void draw(Graphics2D g, int timeMs){
		eq = (double) 1000/timeMs/speed;
		pusher += 1/eq;
		g.setColor(Color.WHITE);
		
		// move the stars across the screen, if they get outside = make ypos == 0
		for(int i = 0; i < width; i+= 2){
			int size = (int)(Math.random() * 3 + 1);
			g.fillOval(i, yPos[i], size, size);
			if(pusher > 1){
				yPos[i] += pusher;
			}
			if(Math.random() * 100 + 1 > 100.9 && yPos[i] > height){
				yPos[i] = 0;
			}
		}
		if(pusher > 1) pusher -= (int)pusher;
	}	
}
