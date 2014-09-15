
package spacetiles.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import spacetiles.gameObjects.shipParts.LaserBuggy;

/**
 *
 * @author Lydell
 */
public class Card {
    private static final int SPEED = 1;
    private static final int HEALTH = 2;
    private static final int LASERBUDDY = 3;
    
    private int xpos;
    private int ypos;
    private int width;
    private int height;
    
    private String name;
    private String disc;
    private int duration;
    private int effect;
    private double amount;
    
    private Card()
    {
        width = 120;
        height = 50;
    }
    
    
    
    public void applyEffect(Ship s)
    {
        switch(effect){
            case HEALTH: s.takeDamage(-(int)amount); break;
            case LASERBUDDY: new LaserBuggy(s.getGrid(), s.getOwner(), s); break;
            case SPEED: s.setSpeed(amount, duration);
        }
    }
    
    public void setLocation(int x, int y)
    {
        xpos = x;
        ypos = y;
    }
    
    public int getX() {
        return xpos;
    }
    
    public int getY() {
        return ypos;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
     
    
    public void draw(Graphics2D g)
    {
        g.setColor(Color.white);
        g.fillRect(xpos, ypos, width, height);
        g.setColor(Color.black);
        g.drawString(name, xpos, ypos+10);
    }
    
    
    private static String prefixes[] = {"Lesser", "Regular", "Greater"};
    
    public static Card healthCard()
    {
        Card health = new Card();
        int index = (int)(Math.random()*prefixes.length);
        health.name = prefixes[index] + "Repair Kit";
        
        health.effect = Card.HEALTH;
        health.amount = index*10 + 10;
        return health;
    }
    
    public static Card droneCard()
    {
        Card drone = new Card();
        int index = (int)(Math.random()*prefixes.length);
        drone.name = prefixes[index] + "Laser Buggy";
        
        drone.effect = Card.LASERBUDDY;
        //health.amount = index*10 + 10;
        return drone;
    }
    
    public static Card speedBoostCard()
    {
        Card speedUp = new Card();
        int index = (int)(Math.random()*prefixes.length);
        speedUp.name = prefixes[index] + "Speed Boost";
        
        speedUp.effect = Card.SPEED;
        speedUp.amount = 1.5;
        speedUp.duration = 200;
        return speedUp;
    }
    
    public static Card getCard()
    {
        int selection = (int)Math.round(Math.random()*3);
        
        if(selection == 0)
        {
            return droneCard();
        }
        if(selection == 1)
        {
            return speedBoostCard();
        }
        else
        {
           return healthCard(); 
        }
            
    }
    
    
    
    
    
    // add dronebuddy CHECK
    // +20% speed boost
    // Repair Kit++ CHECK
    // add weapon
    
}
