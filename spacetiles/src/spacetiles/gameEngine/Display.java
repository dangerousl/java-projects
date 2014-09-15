package spacetiles.gameEngine;

import spacetiles.gameEngine.Launcher;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import spacetiles.gameEngine.*;

/**
 * This class manages the window and screen resolution
 * @author Matthew Carlson
 * @version 1/29/2012
 */
public class Display {
    private final GraphicsDevice graphicsDevice; //FULLSCREEN
    private final DisplayMode defaultDisplayMode;
    private final DisplayMode fullScreenDisplayMode;
    private JFrame frame;
    
    private boolean fullscreen = false;
    private BufferStrategy buffer;

    public Display()
    {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(Launcher.MAC_OS)
            com.apple.eawt.FullScreenUtilities.setWindowCanFullScreen(frame, true);
        
        GraphicsEnvironment gEnviroment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = gEnviroment.getDefaultScreenDevice();
        defaultDisplayMode = graphicsDevice.getDisplayMode();
        fullScreenDisplayMode = graphicsDevice.getDisplayModes()[0];//new DisplayMode(1440,900,32,DisplayMode.REFRESH_RATE_UNKNOWN);
        frame.setSize(800,600);   
        updateScreenMode();
        
        //frame.setIgnoreRepaint(true);
        frame.createBufferStrategy(2);
        buffer = frame.getBufferStrategy();
        
        InputManager inputManager = InputManager.getManager();
        frame.addKeyListener(inputManager);
        frame.addMouseListener(inputManager);
        frame.addMouseMotionListener(inputManager);
        frame.addMouseWheelListener(inputManager);
        
        //displayModes = graphicsDevice.getDisplayModes();
    }
    
    private synchronized void updateScreenMode()
    {
        frame.setVisible(false);
        frame.dispose();
        frame.setUndecorated(fullscreen);
        frame.setResizable(!fullscreen);
        if(fullscreen){
            graphicsDevice.setFullScreenWindow(frame);
            if(fullScreenDisplayMode != null && graphicsDevice.isDisplayChangeSupported())
            {
                try{
                    graphicsDevice.setDisplayMode(fullScreenDisplayMode);
                }catch(Exception ex){}
            }
        }else{
            graphicsDevice.setFullScreenWindow(null);
            if(defaultDisplayMode != null && graphicsDevice.isDisplayChangeSupported())
            {
                try{
                    graphicsDevice.setDisplayMode(defaultDisplayMode);
                }catch(Exception ex){}
            }
            frame.setVisible(true);
        }
        frame.requestFocus();
    }
    
    public void setFullscreeen(boolean mode)
    {
        if(fullscreen != mode){
            fullscreen = mode;
            updateScreenMode();
        }     
    }
    
    public synchronized Graphics2D getGraphics()
    { 
        return (Graphics2D)buffer.getDrawGraphics();
    }
    
    public synchronized void refresh()
    {
        //BufferStrategy strategy = frame.getBufferStrategy();
        if(!buffer.contentsLost()){
            buffer.show();
        }
    }
    
    public int getWidth()
    {
        return frame.getWidth();
    }
    
    public int getHeight()
    {
        return frame.getHeight();
    }
    
    /**
     * Creates an image in accelerated memory on the graphics card!
     * @param width
     * @param height
     * @param transparency
     * @return 
     */
    public BufferedImage createCompatibleImage(int width, int height, int transparency)
    {
        return frame.getGraphicsConfiguration().createCompatibleImage(width, height, transparency);
    }
}
