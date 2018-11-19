import java.awt.*;

public class ShapeStack
{
    private final int SIZE = 100;
    private int index = 0;
    private MyShape stack[] = new MyShape[SIZE];
    private MyShape currentShape;
    
    
    /**
     * Empties the stack.
     */
    
    public void emptyStack()
    {
        index = 0;
    }
    
    /**
     * Adds a new shape to the top of the stack.
     *
     * @param shapeColor Sets the color of the shape.
     * @param shapeType Tells whether it is a rectangle, line, or ellipse.
     * @param filled Tells whether the shape is filled or not.
     * @param startX Starting x point.
     * @param startY Starting y point.
     * @param endX Finishing x point.
     * @param endY Finishing y point.
     */
    
    public void pushShape(String shapeColor, String shapeType, boolean filled,
                          int startX, int startY, int endX, int endY)
    {
        if(index < SIZE)
        {
            Color color = setColor(shapeColor);

            switch(shapeType)
            {
                case "Rectangle":
                    stack[index++] = new MyRectangle(color, filled, startX, startY, endX, endY);
                    break;
                case "Ellipse":
                    stack[index++] = new MyEllipse(color, filled, startX, startY, endX, endY);
                    break;
                case "Line":
                    stack[index++] = new MyLine(color, startX, startY, endX, endY);
                    break;
            }
        }
    }
    
    /**
     * Removes a shape from the top of the stack.
     *
     * @return returns the shape at the index.
     */
    
    public MyShape popShape()
    {
        if(index > 0)
        {
            return stack[--index];
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Turns a string into a color. Includes additional colors for the case
     * that a programmer (myself) wants more options.
     *
     * @param shapeColor A string that says the name of a color.
     * @return The color object that matches the shape.
     */
    
    private Color setColor(String shapeColor)
    {
        switch(shapeColor.toUpperCase())
        {
            case "RED":
                return Color.RED;
            case "GREEN":
                return Color.GREEN;
            case "BLUE":
                return Color.BLUE;
            case "BLACK":
                return Color.BLACK;
            case "CYAN":
                return Color.CYAN;
            case "GRAY":
            case "GREY":
                return Color.GRAY;
            case "DARK GREY":
            case "DARK GRAY":
                return Color.DARK_GRAY;
            case "LIGHT GREY":
            case "LIGHT GRAY":
                return Color.LIGHT_GRAY;
            case "MAGENTA":
                return Color.MAGENTA;
            case "PINK":
                return Color.PINK;
            case "WHITE":
                return Color.WHITE;
            case "YELLOW":
                return Color.YELLOW;
            case "ORANGE":
                return Color.ORANGE;
            default:
                return Color.BLACK;
        }
    }
    
    /**
     * @return the current index of the stack.
     */
    
    public int getIndex()
    {
        return index;
    }
    
    /**
     * Returns the item at the given location as long as the given index is
     * lower than the stack's current index.
     *
     * @param i Given index of the stack.
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    
    public MyShape shapeAt(int i) throws ArrayIndexOutOfBoundsException
    {
        if(i < index)
        {
            return stack[i];
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    /**
     * Sets the current shape that is being made by the user.
     *
     * @param shapeColor Sets the color of the shape.
     * @param shapeType Sets the type of the shape.
     * @param filled Sets the boolean to tell whether the shape is filled or not.
     * @param startX The starting x point.
     * @param startY The starting y point.
     * @param endX The ending x point.
     * @param endY The ending y point.
     */

    public void setCurrentShape(String shapeColor, String shapeType, boolean filled,
                                int startX, int startY, int endX, int endY)
    {
        if(index < SIZE)
        {
            Color color = setColor(shapeColor);

            switch(shapeType)
            {
                case "Rectangle":
                    currentShape = new MyRectangle(color, filled, startX, startY, endX, endY);
                    break;
                case "Ellipse":
                    currentShape = new MyEllipse(color, filled, startX, startY, endX, endY);
                    break;
                case "Line":
                    currentShape = new MyLine(color, startX, startY, endX, endY);
                    break;
            }
        }
    }
    
    /**
     * Replaces a shape at a given index.
     *
     * @param i The index the user wishes to replace
     * @param newShape The new shape object the user wants to replace the
     *                 current shape with.
     */
    
    public void replaceShape(int i, MyShape newShape)
    {
        if(i < index)
        {
            stack[i] = newShape;
        }
    }
    
    /**
     * Replaces the current shape being created by the user.
     *
     * @param shape The new shape object the user wants to replace the current
     *              shape with.
     */

    public void setCurrentShape(MyShape shape)
    {
        currentShape = shape;
    }
    
    /**
     * @return Gives the current shape.
     */

    public MyShape getCurrentShape()
    {
        return currentShape;
    }
}
