import java.awt.*;
import java.awt.geom.Line2D;

public class MyLine extends Line2D.Float implements MyShape
{
    private Color color;
    
    /**
     * Creates a new line.
     *
     * @param color sets the color of the shape.
     * @param xStart Sets the starting x-value of the shape.
     * @param yStart Sets the starting y-value of the shape.
     * @param xEnd Sets the ending x-value of the shape.
     * @param yEnd Sets the ending y-value of the shape.
     */
    
    MyLine(Color color, int xStart, int yStart, int xEnd, int yEnd)
    {
        super(xStart, yStart, xEnd, yEnd);
        this.color = color;
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
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
    }
    
    /**
     * @return Whether the shape is filled or not.
     */

    @Override
    public boolean getFilled()
    {
        return false;
    }
    
    /**
     * Detects whether a square of width 2 surrounding a given point makes
     * contact with the line.
     *
     * @param x x location of point to test for border.
     * @param y y location of point to test for border.
     * @return A boolean to tell whether the given point is on the border or
     * not.
     */
    
    @Override
    public boolean isOnBorder(int x, int y)
    {
        return intersects(x-2, y-2, 4, 4);
    }
}
