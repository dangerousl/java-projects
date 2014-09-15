/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetiles.gameEngine;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matt
 */
public class Launcher {
    public static final boolean MAC_OS = System.getProperty("os.name").equals("Mac OS X");
    
    public static boolean ENABLE_INTERPOLATION = false;
    public static boolean DEBUG = false;
    public static boolean DEBUG_COLLISION = true;
    
        
    public static void main(String args[])
    {

        if(MAC_OS){
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Ship Game");
            com.apple.eawt.Application app = com.apple.eawt.Application.getApplication();
//            app.setDockIconBadge("12345678");
            app.requestForeground(false);
            //com.apple.eawt
        }
        SoundEngine se = new SoundEngine(new File("SpaceTilesBattleTheme.wav"));
        se.start();
        
        GridUniverse universe = new GridUniverse();
        Thread gameLoop = new Thread();
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        while(true){
            universe.update();
            universe.draw();
            try {
                Thread.sleep(33);
            } catch (InterruptedException ex) {
                Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
