/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetiles.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Lydell
 */
public class Missile extends Entity {
    private Entity target;
    private int damage;
    private int fuel;
    private double maxAcc;
    
    
    public Missile(Entity master, Entity target, int damage, int fuel)
    {
        super(master.getGrid(), master.getOwner(),"missile",20);
        this.target = target;
        this.damage = damage;
        this.fuel = fuel;
        maxAcc = .1;
    }
    
    @Override
    public void update() {
        if(target == null || !target.isAlive()){
            die();
            return;
        }
        double tarDist = distance(target);
        if(tarDist < 10){
            target.takeDamage(damage);
            this.die();
        }else{
            fuel--;
            if(fuel > 0){
                double xDelta = target.getXCoord() - this.getXCoord();
                double yDelta = target.getYCoord() - this.getYCoord();
                
                double xAcc = (xDelta/tarDist) *maxAcc;
                double yAcc = (yDelta/tarDist) * maxAcc;
                this.setVelocity(this.getXVel()+xAcc, this.getYVel()+yAcc);
            }
        }
    }

    @Override
    public void actOnEntity(Entity e) {
        
    }

    @Override
    public void draw(Graphics2D g) {
        double x = this.getXCoord();
        double y = this.getYCoord();
        g.translate(x, y);
        g.setColor(Color.orange);
        g.fillOval(-5, -5, 10, 10);
        g.translate(-x, -y);
    }
    
    
}
