/*
 * 
 * The Purpose of this Class is to act as a Parent for any
 * Ships/objects on the space tiles.
 */
package spacetiles.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import spacetiles.gameEngine.GridUniverse;

/**
 *
 * @author Lydell Newby III, Matthew Carlson
 */
public class Ship extends Entity {
    private int actionPoints;
    private int maxAP;
    private double defaultVelocity;
    private double maxVelocity;
    private boolean waypointSet;
    private double xWaypoint, yWaypoint;
    private double duration;
    private ArrayList<Weapon> weapons;
    
    public Ship( GridUniverse grid, int owner, String name, int maxHealth, int maxAP){
        super(grid,owner,name,maxHealth);
        this.maxAP = maxAP;
        this.actionPoints = 0;
        
        maxVelocity = 1;
        defaultVelocity = maxVelocity;
        waypointSet = false;
        this.weapons = new ArrayList<Weapon>();
    }
    
    
    public void attachWeapon(Weapon w)
    {
        weapons.add(w);
    }
    
    public void setWaypoint(double x, double y)
    {
        waypointSet = true;
        xWaypoint = x;
        yWaypoint = y;
    }
    
    @Override
    public void update()
    {
        for(int i = 0; i < weapons.size(); i++)
        {
            weapons.get(i).update();
        }
    }
    
    public void setSpeed(double modifier, int duration)
    {
        maxVelocity *= modifier;
        this.duration = duration;
    }
    
    @Override
    public void updateLocation()
    {
        if(waypointSet){
            double xDelta = xWaypoint - this.getXCoord();
            double yDelta = yWaypoint - this.getYCoord();
            double distance = Math.sqrt(xDelta*xDelta + yDelta*yDelta);
            if(distance < maxVelocity){
                this.setVelocity(0, 0);
                this.setLocation(xWaypoint, yWaypoint);
                waypointSet = false;
            }else{
                double nXvel = (xDelta/distance) * maxVelocity;
                double nYvel = (yDelta/distance) * maxVelocity;
                this.setVelocity(nXvel, nYvel);
            }
        }
        super.updateLocation();
        this.duration--;
        if(duration == 0){
            maxVelocity = defaultVelocity;
        }

    }
    
    @Override
    public void actOnEntity(Entity entity) {
        for(int i = 0; i < weapons.size(); i++)
        {
            if ( this.getOwner() != entity.getOwner()){ //dont shoot at your own stuff
                weapons.get(i).attemptToShoot(entity);
            }
        }
    }
    
    
    @Override
    public void draw(Graphics2D g)
    {
        if(waypointSet){
            g.setColor(Color.yellow);
            g.drawLine((int)this.getXCoord(), (int)this.getYCoord(), (int)xWaypoint, (int)yWaypoint);
        }
        
        double xCoord = this.getXCoord();
        double yCoord = this.getYCoord();
        g.setColor(Color.yellow);
        g.translate(xCoord, yCoord);
        g.fillOval(-10, -10, 20, 20);
        g.translate(-xCoord, -yCoord);
        
        this.drawWeapon(g);
    }
    
    public void drawWeapon(Graphics2D g)
    {
        
        for(int i = 0; i < weapons.size(); i++){
            weapons.get(i).draw(g);
        }
    }
}
