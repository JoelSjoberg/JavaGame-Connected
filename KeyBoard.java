package connected;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener{

    boolean[] keys = new boolean[127];
    //left, up, right, down
    int angles[] = {180, 270, 0, 90};
    public boolean[] getKeys(){
        return keys;
    }
    


    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    public void keyTyped(KeyEvent e) {
    }
}
