// Missile prototype stuff
package spacetiles.gameObjects;


import spacetiles.gameEngine.GridUniverse;
import java.awt.Graphics2D;

/**
 *
 * @author Lydell Newby III, Matthew Carlson
 */
public abstract class Entity {
    private String name;
    private int owner;
    private int health;
    private int maxHealth;
    private double xCoord,yCoord;
    private double xVel, yVel;
    
    
    private GridUniverse grid;
    
    public Entity(){
        
    }
    
    public Entity(GridUniverse grid, int owner, String name, int maxHealth){
        this.grid = grid;
        this.owner = owner;
        this.name = name;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
                
        xVel = 0;
        yVel = 0;
        
        grid.addEntity(this);
    }
    
    public void updateLocation()
    {
        xCoord += xVel;
        yCoord += yVel;
    }
    
    public abstract void update();
    
    public abstract void actOnEntity(Entity e);
    
    public abstract void draw(Graphics2D g);

    
    public void takeDamage(int damageTaken)
    {
        if(isAlive()){
            this.health -= damageTaken;
            if(this.health <= 0)
            {
                this.health = 0;
                this.die();
            }
        }
    }
    
    public void die()
    {
        grid.removeEntity(this);
    }
    
    
    public GridUniverse getGrid()
    {
        return grid;
    }
    
    public final int getOwner()
    {
        return owner;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean isAlive()
    {
        return health > 0;
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public int getMaxHealth()
    {
        return maxHealth;
    }
    
    public void setLocation(double x, double y)
    {
        xCoord = x;
        yCoord = y;
    }
    
    public void setVelocity(double xVel, double yVel)
    {
        this.xVel = xVel;
        this.yVel = yVel;
    }
    
    public double getXCoord()
    {
        return xCoord;
    }
    
    public double getYCoord()
    {
        return yCoord;
    }
    
    public double getXVel() {
        return xVel;
    }
    
    public double getYVel() {
        return yVel;
    }
    
    public double distance(Entity otherShip)
    {
        double xDelta = xCoord - otherShip.xCoord;
        double yDelta = yCoord - otherShip.yCoord;
        return Math.sqrt(xDelta*xDelta + yDelta*yDelta);
    }
}
