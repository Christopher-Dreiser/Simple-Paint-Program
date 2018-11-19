import java.awt.*;

public interface MyShape extends Shape
{
    /**
     * @return The color of the shape.
     */
    Color getColor();
    
    /**
     * Moves the shape by a given amount.
     *
     * @param dx How far to move it to the right (negative is left).
     * @param dy How far to move it down (negative is up).
     */
    void moveBy(int dx, int dy);
    
    /**
     * @return Whether the shape is filled or not.
     */
    boolean getFilled();
    
    /**
     * Detects whether the position is near enough to the border of the object.
     * Should provide a bit of leeway, so it is not frustrating to grab the
     * shape.
     *
     * @param x x location of point to test for border.
     * @param y y location of point to test for border.
     * @return A boolean to tell whether the given point is on the border or
     * not.
     */
    boolean isOnBorder(int x, int y);
    
    
}
