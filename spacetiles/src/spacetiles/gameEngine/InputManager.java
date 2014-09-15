package spacetiles.gameEngine;

import java.awt.event.*;


/**
 *
 * @author Matt
 */
public class InputManager implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
    private static final InputManager INSTANCE = new InputManager();
    private byte[] keyState = new byte[256];
    private boolean isBuffering = false;
    private String  keyBuffer = "";
    
    private int mouseX, mouseY;
    private boolean[] mouseDown = new boolean[3];
    private boolean mouseEventHandled = false;
    
    public static int LEFT_BUTTON = 0;
    public static int RIGHT_BUTTON = 2;
    public static int WHEEL_BUTTON = 1;
    
    GridUniverse grid;
    
    private InputManager(){}
    
    public static InputManager getManager()
    {
        return INSTANCE;
    }
        
    public static boolean isKeyDown(int keyCode)
    {
        return INSTANCE.keyState[keyCode] > 0;
    }
    
    /**
     * 
     * @return the x coordinate of the mouse  
     */
    public static int getMouseX()
    {
        return INSTANCE.mouseX;
    }
    
    public static int getMouseY()
    {
        return INSTANCE.mouseY;
    }
    
    static boolean isMouseDown(int button)
    {
        return INSTANCE.mouseDown[button];
    }
    
    public void setGrid(GridUniverse gu)
    {
        grid = gu;
    }
    
    
    //KEYBOARD EVENTS
    @Override
    public void keyPressed(KeyEvent kEvent) {
        int key = kEvent.getKeyCode();
        //kEvent.consume();
        if(key >=0 && key <= keyState.length){
            keyState[key] = 2;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent kEvent) {
        int key = kEvent.getKeyCode();
        if(key >=0 && key <= keyState.length){
            keyState[key] = 0;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent kEvent) {
        if(isBuffering)
            keyBuffer += kEvent.getKeyChar();
    }
    
    
    //MOUSE EVENTS
    private void mouseEvent(MouseEvent me, int context)
    {
        if(context == 0){
            mouseX = me.getX();
            mouseY = me.getY();
        }else{
            int buttonNum = me.getButton()-1;
            if(buttonNum < 3){
                mouseDown[buttonNum] = true;
                boolean clicked = context == 3;
                boolean pressed = context == 1;
                grid.mouseEvent(buttonNum, clicked, pressed);
                //mouseEventHandled = UIManager.getManager().mouseEvent(buttonNum, clicked, pressed);
            }
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        mouseEvent(me, 3);
//        System.out.println("Clicked at "+me.getX() + ","+ me.getY() + " Button:" + me.getButton());
    }
    @Override
    public void mousePressed(MouseEvent me) {
        mouseEvent(me, 1);
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        mouseEvent(me, 2);
    }
    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        mouseEvent(me, 0);
    }
    @Override
    public void mouseMoved(MouseEvent me) {
        mouseEvent(me, 0);
    }
    @Override
    
    public void mouseWheelMoved(MouseWheelEvent mwe) {
//        System.out.println("Scrolled! ammount:" + mwe.getScrollAmount() + " type" + mwe.getScrollType() +" units"+ mwe.getUnitsToScroll()+ " rotation"+ mwe.getWheelRotation());
    }
}
