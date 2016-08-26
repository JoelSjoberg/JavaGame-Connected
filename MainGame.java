

import java.awt.Dimension;
import javax.swing.JFrame;

public class MainGame extends JFrame{
    
    private final int width = 1000;
    private final int height = width / 16 * 9;
    private boolean running = false;
    private Screen screen;
    private KeyBoard k = new KeyBoard();
    public int msPerFrame = 5;
    private void begin(){    
        screen = new Screen(width, height, msPerFrame);
        add(screen);
        pack();
        addKeyListener(k);
        setSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // Game loop
    public void run() {
        
        double prev = System.currentTimeMillis();
        double lag = 0.0;
        double current;
        double elapsed;
        
        double fpsTimeBegin = System.currentTimeMillis();
        double fpsEnd;
        while(running){
            current = System.currentTimeMillis();
            elapsed = current - prev;
            prev = current;
            lag += elapsed;
            
// input here
            
            if(lag >= msPerFrame){
                screen.render(k.getKeys());
                lag -= msPerFrame;
                
// FPS counter
                fpsEnd = System.currentTimeMillis();
                if(fpsEnd - fpsTimeBegin > 1000){
                    setTitle(Integer.toString(screen.countFps()));
                    fpsTimeBegin = fpsEnd;
                }
            } 
        }
    }
    
    private void start(){
        running = true;
    }
    private void stop(){
        running = false;
    }
    public int getTimeMs(){
        return msPerFrame;
    }
    
    public static void main(String[] args){
        MainGame game = new MainGame();
        game.begin();
        game.start();
        game.run();
    }
}
