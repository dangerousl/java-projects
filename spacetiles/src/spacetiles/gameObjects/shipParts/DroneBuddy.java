
package spacetiles.gameObjects.shipParts;

import java.awt.Color;
import java.awt.Graphics2D;
import spacetiles.gameEngine.GridUniverse;
import spacetiles.gameObjects.Ship;
import spacetiles.gameObjects.weapons.MchnGun150mm;

/**
 *
 * @author Lydell
 */
public class DroneBuddy extends Ship {
    private String name;
    private double duration;
    private Ship s;
    private int maxVel = 1;
    private double angle = 0;
    
    public DroneBuddy(GridUniverse u1, int owner, Ship s){
        super(u1,owner,"DroneBuddy",50,0);
        this.attachWeapon(new MchnGun150mm(this));
        this.s = s;
    }
    
    
    @Override
    public void draw(Graphics2D g)
    {
        double xCoord = this.getXCoord();
        double yCoord = this.getYCoord();
        g.setColor(Color.blue);
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
        
        
        
        this.angle += .033;
        if(angle == 360 || angle == -360)
        {
            angle = 0;
        }
    }
    
}
