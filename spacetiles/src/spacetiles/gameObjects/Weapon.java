/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetiles.gameObjects;

import java.awt.Graphics2D;

/**
 *
 * @author Lydell Newby III, Matthew Carlson
 */
public interface Weapon {
    
    public double getDamage();
    public boolean attemptToShoot(Entity target);
    public void update();
    public void draw(Graphics2D g);

}
