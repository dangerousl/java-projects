/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetiles.gameObjects;

/**
 *
 * @author matt
 */
public class Shot {
    private boolean instant = true;
    private double xCoord, yCoord;
    private double xVel, yVel;
    private long timeFired;
    
    public Shot(double xCoord, double yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        timeFired = System.currentTimeMillis();
    }
    
    public int ix()
    {
        return (int)xCoord;
    }
    
    public int iy()
    {
        return (int)yCoord;
    }
    
    public int age()
    {
        return (int)(System.currentTimeMillis() - timeFired);
    }
}
