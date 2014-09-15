package spacetiles.gameObjects.weapons;

/*
 * Hellion Missle is going to be a mid-tier, long range
 * big damage weapon with limited charges for slow firing.
 * Values are strictly test values relative to other weapons
 */

import java.awt.Graphics2D;
import spacetiles.gameObjects.Entity;
import spacetiles.gameObjects.Missile;
import spacetiles.gameObjects.Ship;
import spacetiles.gameObjects.Weapon;

/**
 *
 * @author Lydell Newby III
 */
public class HellionMissile implements Weapon {
    private Ship master;
    private String name;
    private int hitChance;
    private int dmgValue;
    private int wepCharge;
    private int maxCharge;
    private int chargeTimer;
    private int chargeTimerMax;
    private double range;
   
    public HellionMissile(Ship master){
        this.name = "Hellion Missile";
        this.master = master;
        dmgValue = 30;
        wepCharge = 1;
        maxCharge = 1;
        chargeTimer = 0;
        chargeTimerMax = 100;
        range = 500;
    }
    
    public double getDamage(){
        return this.dmgValue;
    }
    
    @Override
    public boolean attemptToShoot(Entity target)
    {
        if(wepCharge > 0 && !(target instanceof Missile) && master.distance(target) < range){
            for( int i = 0; i < 2; i++){
                Missile m = new Missile(master, target,dmgValue,100);
                m.setLocation(master.getXCoord()+i*10, master.getYCoord());
            }
             wepCharge--;
             return true;
        }
        return false;
    }
    
    @Override
    public void update()
    {
        if(wepCharge < maxCharge){
            chargeTimer++;
            if(chargeTimer >= chargeTimerMax){
              chargeTimer = 0;
               wepCharge++;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        //Im invisable lool
    }
}
