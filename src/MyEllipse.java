import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MyEllipse extends Ellipse2D.Float implements MyShape
{
    private Color color;
    private boolean filled;
    
    /**
     * Creates a new ellipse.
     *
     * @param color sets the color of the shape.
     * @param filled sets whether the shape is filled or not.
     * @param xStart Sets the starting x-value of the shape.
     * @param yStart Sets the starting y-value of the shape.
     * @param xEnd Sets the ending x-value of the shape.
     * @param yEnd Sets the ending y-value of the shape.
     */
    
    MyEllipse(Color color, boolean filled, int xStart, int yStart, int xEnd, int yEnd)
    {
        super(xStart, yStart, (xEnd-xStart), (yEnd - yStart));
        this.color = color;
        this.filled = filled;
    }
    
    /**
     * @return The color of the shape.
     */

    @Override
    public Color getColor()
    {
        return color;
    }
    
    /**
     * Moves the shape by a given amount.
     *
     * @param dx How far to move it to the right (negative is left).
     * @param dy How far to move it down (negative is up).
     */
    
    @Override
    public void moveBy(int dx, int dy)
    {
        x += dx;
        y += dy;
    }
    
    /**
     * @return Whether the shape is filled or not.
     */

    @Override
    public boolean getFilled()
    {
        return filled;
    }
    
    /**
     * Checks to the left and right of the point, as well as above and below.
     * If the point to the left is in the oval and the point to the right is
     * not (or vice-versa), the point is within 3 pixels of the border. Same
     * with above and below.
     *
     * @param x x location of point to test for border.
     * @param y y location of point to test for border.
     * @return A boolean to tell whether the given point is on the border or
     * not.
     */
    
    @Override
    public boolean isOnBorder(int x, int y)
    {
        if(this.contains(x-4, y) != this.contains(x+4, y))
        {
            return true;
        }
        return(this.contains(x, y-4) != this.contains(x, y+4));
    }
}
