package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
    private static int clickX = 2000, clickY = 2000;

    public int getClickX(){
        return clickX;
    }

    public int getClickY(){
        return clickY;
    }

    public void reset(){
        clickX = 2000;
        clickY = 2000;
    }


    @Override
    public void mouseClicked(MouseEvent e){

    }

    @Override
    public void mousePressed(MouseEvent e){
        clickX = e.getX();
        clickY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e){

    }

    @Override
    public void mouseEntered(MouseEvent e){

    }

    @Override
    public void mouseExited(MouseEvent e){

    }
}
