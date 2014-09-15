package spacetiles.gameObjects.shipParts;


import java.awt.Color;
import java.awt.Graphics2D;
import spacetiles.gameEngine.GridUniverse;
import spacetiles.gameObjects.Ship;
import spacetiles.gameObjects.weapons.HellionMissile;

/**
 *
 * @author Lydell
 */
public class RocketPal extends Ship {
    private String name;
    private double duration;
    private Ship s;
    private int maxVel = 1;
    private double angle = 0;
    
    public RocketPal(GridUniverse u1, int owner, Ship s){
        super(u1,owner,"LaserBuggy",500,0);
        this.attachWeapon(new HellionMissile(this));
        this.s = s;
    }
    
    
    @Override
    public void draw(Graphics2D g)
    {
        double xCoord = this.getXCoord();
        double yCoord = this.getYCoord();
        g.setColor(Color.red);
        g.translate(xCoord, yCoord);
        g.fillOval(-5, -5, 10, 10);
        g.translate(-xCoord, -yCoord);
        
        this.drawWeapon(g);
        
    }
    
    @Override
    public void updateLocation()
    {
        int radius = 20;
//        super.updateLocation();
        this.setLocation(s.getXCoord() + (radius*Math.cos(angle)), s.getYCoord() + (radius*Math.sin(angle)));
        
        this.angle -= .02;
        if(angle == 360 || angle == -360)
        {
            angle = 0;
        }
        
    }
    
}
