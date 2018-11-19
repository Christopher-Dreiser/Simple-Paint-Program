import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MyRectangle extends Rectangle2D.Float implements MyShape
{
    private Color color;
    private boolean filled;
    
    /**
     * Creates a new rectangle.
     *
     * @param color sets the color of the shape.
     * @param filled sets whether the shape is filled or not.
     * @param xStart Sets the starting x-value of the shape.
     * @param yStart Sets the starting y-value of the shape.
     * @param xEnd Sets the ending x-value of the shape.
     * @param yEnd Sets the ending y-value of the shape.
     */
    
    MyRectangle(Color color, boolean filled, int xStart, int yStart, int xEnd, int yEnd)
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
     * Detects whether the position is within 3 pixels of each border.
     *
     * @param x x location of point to test for border.
     * @param y y location of point to test for border.
     * @return A boolean to tell whether the given point is on the border or
     * not.
     */
    
    @Override
    public boolean isOnBorder(int x, int y)
    {
        //Checks whether the given x is within 3 pixels of either x border
        if((this.x  + 3 >= x  && this.x - 3 <= x) ||
                        (this.x + width + 3 >= x && this.x + width - 3 <= x))
        {
            //Checks whether the y is within 3 pixels of the shape
            if(this.y - 3 <= y && this.y + height + 3 >= y)
            {
                return true;
            }
        }
        //Checks whether the given y is within 3 pixels of either y border
        if((this.y  + 3 >= y  && this.y - 3 <= y) ||
                (this.y + height + 3 >= y && this.y + height - 3 <= y))
        {
            //Checks whether the x is within 3 pixels of the shape
            if(this.x - 3 <= x && this.x + width + 3 >= x)
            {
                return true;
            }
        }
        //Default case, if neither of the above true conditions have been met,
        // the point is not on the border.
        return false;
    }


}