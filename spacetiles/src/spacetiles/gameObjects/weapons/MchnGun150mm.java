package spacetiles.gameObjects.weapons;

/*
 * The 150mm Machine Gun has fast firing, higher damage
 * but at very limited range. I would like to implement a way
 * for this weapon to require a reload for a few seconds once it 
 * reaches the end of it's clip.
 * Values are strictly test values relative to other weapons
 * 
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
 * @author Lydell Newby III
 */
public class MchnGun150mm implements Weapon {
    private Ship master;
    private String name;
    private double hitChance;
    private int dmgValue;
    private int charges;
    private int maxCharges;
    
    private int reloadTimer;
    private int reloadTime;
    
    private int maxShotAge;
    
    private double range;
    
    private ArrayList<Shot> shots;
       
    
    public MchnGun150mm(Ship master){
        this.name = "150mm Machine Gun";
        this.master = master;

        dmgValue = 1;
        hitChance = .4;
        charges = 150;
        maxCharges = 150;

        reloadTimer = 0;
        reloadTime = 100;
        
        maxShotAge = 500;
        range = 100;

        shots = new ArrayList<Shot>();
    }
    
    @Override
    public double getDamage(){
        return this.dmgValue;
    }
    
    @Override
    public boolean attemptToShoot(Entity target)
    {
        int shotsTaken = 0;
        int damage = 0;

        for(int i = 0; i < 5; i++) { // take 10 shots every time
            if(charges > 0 && master.distance(target) < range){
                charges--;
                double tarX;
                double tarY;
                shotsTaken++;
                if(Math.random() < hitChance){ //miss chance
                    damage += dmgValue;
                    target.takeDamage(dmgValue);
                    tarX = target.getXCoord() + Math.random()*20-10;
                    tarY = target.getYCoord() + Math.random()*20-10;
                }else{
                    tarX = target.getXCoord() + Math.random()*30-25;
                    tarY = target.getYCoord() + Math.random()*30-25;
                }
                shots.add(new Shot(tarX, tarY));
            }
        }

        
        if(shotsTaken > 0){
            System.out.println(this.master.getName() + "'s " + this.name + " took "+shotsTaken+" shots on " + target.getName() + ",");
            System.out.println("doing " + damage + " damage.");
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public void update()
    {
        if(charges == 0){
            if(reloadTimer < reloadTime){
                reloadTimer++;
            }else{
                reloadTimer = 0;
                charges = maxCharges;
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
                Color c = new Color(1,1,0,alpha);
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