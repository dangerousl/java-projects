package spacetiles.gameObjects.weapons;

/*
 * Beam Cannon is going to be a mid-tier laser based weapon.
 * with faster firing. Weaker range will/should be a draw back of laser
 * based weaponry, low damage counters for the faster rate of fire.
 * Values are strictly test values relative to other weapons.
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import spacetiles.gameObjects.Entity;
import spacetiles.gameObjects.Ship;
import spacetiles.gameObjects.Shot;
import spacetiles.gameObjects.Weapon;

/**
 *
 * @author Lydell
 */
public class BeamCannon implements Weapon {
    private Ship master;
    private String name;
    private int hitChance;
    private int dmgValue;
    private boolean charged;
    private int chargeTimer;
    private int chargeTime;
    
    private int maxShotAge;
    
    private double range;
    
    private ArrayList<Shot> shots;
       
    
    public BeamCannon(Ship master){
        this.name = "Beam Cannon";
        this.master = master;

        dmgValue = 25;
        charged = true;
        chargeTimer = 0;
        chargeTime = 100;
        range = 150;
        
        maxShotAge = 2000;
        
        shots = new ArrayList<Shot>();
    }
    
    @Override
    public double getDamage(){
        return this.dmgValue;
    }
    
    @Override
    public boolean attemptToShoot(Entity target)
    {
        if(charged && master.distance(target) < range){
            charged = false;
            System.out.println(this.master.getName() + "'s " + this.name + " fired on " + target.getName() + ",");
            System.out.println("doing " + this.dmgValue + " damage.");
            target.takeDamage(dmgValue);
            shots.add(new Shot(target.getXCoord(), target.getYCoord()));
            return true;
        }
        return false;
    }
    
    @Override
    public void update()
    {
        if(!charged){
            chargeTimer++;
            if(chargeTimer >= chargeTime){
              chargeTimer = 0;
               charged = true;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        for(int i = shots.size()-1; i >= 0; i--){
            Shot shot = shots.get(i);
            int age = Math.max(shot.age(), 1);
            if(age < maxShotAge){
                float alpha = 1 - (age/(float)maxShotAge);
                Color c = new Color(1,0,0,alpha);
                int startX = (int)master.getXCoord();
                int startY = (int)master.getYCoord();
                g.setColor(c);
                g.drawLine(startX, startY, shot.ix(), shot.iy());
            } else {
                shots.remove(i);
            }
        }
    }
}
