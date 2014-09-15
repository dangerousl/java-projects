/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetiles.gameEngine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import spacetiles.gameObjects.Card;
import spacetiles.gameObjects.Entity;
import spacetiles.gameObjects.Ship;
import spacetiles.gameObjects.Weapon;
import spacetiles.gameObjects.shipParts.DroneBuddy;
import spacetiles.gameObjects.shipParts.LaserBuggy;
import spacetiles.gameObjects.shipParts.RocketPal;
import spacetiles.gameObjects.weapons.BeamCannon;
import spacetiles.gameObjects.weapons.HellionMissile;
import spacetiles.gameObjects.weapons.MchnGun150mm;

/**
 *
 * @author Lydell Newby III, Matthew Carlson
 */
public class GridUniverse {
    private final int THE_UNIVERSE = 0;
    private final int PLAYER_ONE = 1;
    private final int PLAYER_TWO = 2;
    private final int PLAYER_THREE = 3;
    
    private ArrayList<Entity> entities;
    private ArrayList<Entity> addedEntities;
    private ArrayList<Entity> removedEntities;
    private Display display;
    
    private Entity selected;
    private String status;
    
    private ArrayList<Card> cards;
    
    GridUniverse()
    {
        InputManager inputMan = InputManager.getManager();
        inputMan.setGrid(this);
        display = new Display();
        entities        = new ArrayList<Entity>();
        addedEntities   = new ArrayList<Entity>();
        removedEntities = new ArrayList<Entity>();
        cards = new ArrayList<Card>();;
        
        status = "Welcome to the grid universe.";
        
        Ship s = new Ship(this,PLAYER_ONE,"Call of Booty",100,3);
        s.setLocation(20, 100);
        s.setVelocity(.2, 0);
        
        Weapon weapon = new MchnGun150mm(s);
        s.attachWeapon(weapon);
        weapon = new BeamCannon(s);
        s.attachWeapon(weapon);
        weapon = new HellionMissile(s);
        s.attachWeapon(weapon);
        
        Ship l = new Ship(this,PLAYER_TWO,"The Great LOLs", 100,4);
        l.setLocation(400, 150);
        l.setWaypoint(40, 100);
        //l.setVelocity(-.2, 0);
        
        weapon = new MchnGun150mm(l);
        l.attachWeapon(weapon);
        weapon = new BeamCannon(l);
        l.attachWeapon(weapon);
        weapon = new HellionMissile(l);
        l.attachWeapon(weapon);
        
        Ship t = new Ship(this,PLAYER_THREE,"Trolo", 100,4);
        t.setLocation(220, 150);
        t.setVelocity(0, 0);
        
        weapon = new MchnGun150mm(t);
        t.attachWeapon(weapon);
        weapon = new BeamCannon(t);
        t.attachWeapon(weapon);
        weapon = new HellionMissile(t);
        t.attachWeapon(weapon);
        
        DroneBuddy d = new DroneBuddy(this,PLAYER_TWO,l);
        LaserBuggy lb = new LaserBuggy(this,PLAYER_ONE,s);
        RocketPal rp = new RocketPal(this,PLAYER_THREE,t);
        
        Card card = Card.getCard();
        card.setLocation(5, 400);
        cards.add(card);
        
        card = Card.getCard();
        card.setLocation(200, 400);
        cards.add(card);
        
        card = Card.getCard();
        card.setLocation(410, 400);
        cards.add(card);
  
    }
    
    public void update()
    {
        for(int i = (entities.size() - 1); i >= 0; i--){
            Entity curEntity = entities.get(i);
            if(curEntity != null){
                curEntity.updateLocation();
                curEntity.update();
                
                for(int j = (entities.size() - 1); j >= 0; j--){
                    if(i != j && entities.get(j) != null)
                        curEntity.actOnEntity(entities.get(j));
                }
            }
        }
        entities.addAll(addedEntities);
        addedEntities.clear();
        entities.removeAll(removedEntities);
        removedEntities.clear();
        if(selected != null)
            status = selected.getName() + " " + selected.getHealth() + "/"+selected.getMaxHealth();
    }
    
    public void addEntity(Entity entity)
    {
        addedEntities.add(entity);
    }
    
    public void removeEntity(Entity entity)
    {
        removedEntities.add(entity);
    }
    
    public void draw()
    {
        Graphics2D g = display.getGraphics();
        g.setBackground(Color.black);
        g.setColor(Color.black);
        g.fillRect(0, 0, display.getWidth(), display.getHeight());
        //g.clearRect(0, 0, display.getWidth(), display.getHeight());
        for(int i = 0; i < entities.size(); i++){
            entities.get(i).draw(g);
        }
        g.setColor(Color.orange);
        g.drawString(status, 10, 500);
        for(int i = 0; i < cards.size(); i++)
            cards.get(i).draw(g);
        
        g.dispose();
        display.refresh();
    }

    void mouseEvent(int buttonNum, boolean clicked, boolean pressed) {
        if(clicked){
            double mouseX = InputManager.getMouseX();
            double mouseY = InputManager.getMouseY();
            if(buttonNum == InputManager.LEFT_BUTTON){
                
                //card apply
                if(selected != null && selected instanceof Ship) {
                    for(int i = 0; i < cards.size(); i++){
                        Card card = cards.get(i);
                        if(mouseX > card.getX() && mouseX <card.getX() + card.getWidth())
                            if(mouseY > card.getY() && mouseY <card.getY() + card.getHeight()){
                                card.applyEffect((Ship)selected);
                                int x = card.getX();
                                int y = card.getY();
                                cards.remove(card);

                                card = Card.getCard();
                                card.setLocation(x, y);
                                cards.add(card);
                                return;
                            }
                    }
                }
                
                //entity select
                for(int i = 0; i < entities.size(); i++){
                    Entity e = entities.get(i);
                    if(Math.abs(e.getXCoord() - mouseX) < 10 && Math.abs(e.getYCoord() - mouseY) < 10){
                        selected = e;
                        break;
                    }
                }
            }else{ //waypoint set
                if(selected != null && selected instanceof Ship){
                    Ship ship = (Ship)selected;
                    ship.setWaypoint(mouseX, mouseY);
                }
            }
        }
    }
    
    
}
