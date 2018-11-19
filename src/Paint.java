import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Paint extends JPanel implements MouseListener, ActionListener, MouseMotionListener, MouseWheelListener
{
    private static String[] colors = {"Red", "Green", "Blue", "Black"};
    private static JComboBox colorMenu = new JComboBox(colors);
    private static String[] shapes = {"Rectangle", "Ellipse", "Line"};
    private static JComboBox shapeMenu = new JComboBox(shapes);
    private static JCheckBox filledCheckBox = new JCheckBox("Filled");
    private static JPanel mouseLocationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private boolean clicked = false;
    private boolean rightClicked = false;
    private int selectedShapeIndex;
    private MyShape draggedShape;
    private ShapeStack ss = new ShapeStack();

    private int pressedX = -1, pressedY = -1, rightClickedX = -1, rightClickedY = -1;
    
    /**
     * Creates the JFrame to be displayed.
     *
     * @param args delivered by console, but not used.
     */

    public static void main(String[] args)
    {
        //Creates and sets layout of JFrame.
        JFrame main = new JFrame();
        Paint paint = new Paint();
        main.setLayout(new BorderLayout());
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Option panel starts here
        JPanel options = new JPanel();

        JButton undoButton = new JButton("Undo");
        undoButton.setActionCommand("Undo");
        undoButton.addActionListener(paint);
        options.add(undoButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand("Clear");
        clearButton.addActionListener(paint);
        options.add(clearButton);

        options.add(colorMenu);

        options.add(shapeMenu);

        options.add(filledCheckBox);


        main.add(options, BorderLayout.NORTH);
        //Option panel ends here, added to North end of frame.
        

        //Mouse location panel starts here
        mouseLocationPanel.add(new JLabel("Mouse is out"));
        main.add(mouseLocationPanel, BorderLayout.SOUTH);
        //Mouse location panel ends here, added to South end of frame.


        main.add(paint);

        paint.addMouseMotionListener(paint);

        main.setSize(600, 600);
        main.setLocationRelativeTo(null);
        main.setVisible(true);
    }
    
    /**
     * Constructs the paint panel and object. Kept public so that it can
     * potentially be used in other programs.
     */

    public Paint()
    {
        addMouseListener(this);
        setSize(400, 450);
    }
    
    
    /**
     * Clears the canvas, then draws all existing shapes. If the user is
     * currently making a shape, will also display that shape.
     *
     * @param g given by background process, converted to Graphics2D
     */
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.clearRect(0,0, (int)getSize().getWidth(),
                (int)getSize().getHeight());

        for(int i = 0; i < ss.getIndex(); i++)
        {
            g2d.setColor(ss.shapeAt(i).getColor());
            g2d.draw(ss.shapeAt(i));
            if(ss.shapeAt(i).getFilled())
            {
                g2d.fill(ss.shapeAt(i));
            }
            
        }
        if(clicked)
        {
            g2d.setColor(ss.getCurrentShape().getColor());
            g2d.draw(ss.getCurrentShape());
            if(ss.getCurrentShape().getFilled())
            {
                g2d.fill(ss.getCurrentShape());
            }
        }
        repaint();
    }
    
    
    /**
     * Changes the mouse location in the bottom left corner when the mouse exits the frame.
     *
     * @param e Mouse event created by moving the mouse out of the paint panel.
     */

    @Override
    public void mouseExited(MouseEvent e)
    {
        mouseLocationPanel.removeAll();
        mouseLocationPanel.add(new JLabel("Mouse is out"));
        mouseLocationPanel.repaint();
        mouseLocationPanel.revalidate();
    }
    
    /**
     * Detects whether the user clicked left or right mouse button. If the
     * user left-clicked, starts the new shape. If the user right-clicked,
     * checks whether the clicked point is in a filled shape or on the border
     * of an empty shape or an edge.
     *
     * @param e Mouse event given by the listener.
     */
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        if(SwingUtilities.isLeftMouseButton(e))
        {
            pressedX = e.getX();
            pressedY = e.getY();
        }
        else if(SwingUtilities.isRightMouseButton(e) && !rightClicked)
        {
            rightClicked = true;
            rightClickedX = e.getX();
            rightClickedY = e.getY();
            for(int i = ss.getIndex() - 1; i >= 0; i--)
            {
                if(ss.shapeAt(i).getFilled())
                {
                    if(ss.shapeAt(i).contains(rightClickedX, rightClickedY))
                    {
                        draggedShape = ss.shapeAt(i);
                        selectedShapeIndex = i;
                        break;
                    }
                }
                else
                {
                    if(ss.shapeAt(i).isOnBorder(rightClickedX, rightClickedY))
                    {
                        draggedShape = ss.shapeAt(i);
                        selectedShapeIndex = i;
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * When the user releases the mouse button, detects which button is
     * released. If the left button is released, finishes the shape and adds it
     * to the stack. If the right button is released, places the shape (if one
     * is selected) and clears all necessary variables.
     *
     * @param e Mouse event created by listener.
     */

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if(SwingUtilities.isLeftMouseButton(e))
        {
            int releasedX = e.getX();
            int releasedY = e.getY();
            //Swaps x if the user drags from right to left, swaps y if the user
            // drags from bottom to top. Skips operation if line is selected.
            if(!shapeMenu.getSelectedItem().toString().equals("Line"))
            {
                if(pressedX > releasedX)
                {
                    int temp = releasedX;
                    releasedX = pressedX;
                    pressedX = temp;
                }

                if(pressedY > releasedY)
                {
                    int temp = releasedY;
                    releasedY = pressedY;
                    pressedY = temp;
                }
            }

            ss.pushShape(colorMenu.getSelectedItem().toString(),
                    shapeMenu.getSelectedItem().toString(),
                    filledCheckBox.isSelected(), pressedX, pressedY,
                    releasedX, releasedY);
            clicked = false;
        }
        else if(SwingUtilities.isRightMouseButton(e))
        {
            if(draggedShape != null)
            {
            /*ss.pushShape(ss.getCurrentShape().getColor(),
                    ss.getCurrentShape().getShape(),
                    ss.getCurrentShape().getFilled(),
                    (int)ss.getCurrentShape().getX(),
                    (int)ss.getCurrentShape().getY(),
                    (int)(ss.getCurrentShape().getX()
                            + ss.getCurrentShape().getWidth()),
                    (int)(ss.getCurrentShape().getY()
                            + ss.getCurrentShape().getHeight()));*/
                ss.replaceShape(selectedShapeIndex, draggedShape);
                draggedShape = null;
                repaint();
            }
            rightClicked = false;
        }
    }
    
    /**
     * Makes the buttons work. Undo pops the stack, clear empties it. Can only
     * undo new shapes, cannot undo movement.
     *
     * @param e Event created by listener when a button is clicked.
     */


    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch(e.getActionCommand())
        {
            case "Undo": ss.popShape();
                break;
            case "Clear": ss.emptyStack();
                break;
        }
    }
    
    /**
     * Gets mouse location and sets pane in lower left to display current
     * coordinates.
     *
     * @param e mouse event created when the mouse is moved.
     * */

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseLocationPanel.removeAll();
        int x = (int)MouseInfo.getPointerInfo().getLocation().getX() - (int)this.getLocationOnScreen().getX();
        int y = (int)MouseInfo.getPointerInfo().getLocation().getY() - (int)this.getLocationOnScreen().getY();
        mouseLocationPanel.add(new JLabel("(" + x + ", " + y + ")"));
        mouseLocationPanel.repaint();
        mouseLocationPanel.revalidate();
    }
    
    /**
     * First, duplicates the code from mouseMove to update the mouse location.
     * After that, checks whether the mouse is left-clicked or right-clicked.
     * If it is left-clicked, updates the current shape so the user can see it
     * being drawn. If it is right-clicked, moves the selected shape.
     *
     * @param e Mouse event created when the mouse is dragged.
     */

    @Override
    public void mouseDragged(MouseEvent e)
    {
        //Updates the mouse location panel
        mouseLocationPanel.removeAll();
        int x = (int)MouseInfo.getPointerInfo().getLocation().getX() - (int)this.getLocationOnScreen().getX();
        int y = (int)MouseInfo.getPointerInfo().getLocation().getY() - (int)this.getLocationOnScreen().getY();
        mouseLocationPanel.add(new JLabel("(" + x + ", " + y + ")"));
        mouseLocationPanel.repaint();
        mouseLocationPanel.revalidate();
        //Checks if the user is left-clicking.
        if(SwingUtilities.isLeftMouseButton(e))
        {
            int currentX = (int)(MouseInfo.getPointerInfo().getLocation().getX() - this.getLocationOnScreen().getX());
            int currentY = (int)(MouseInfo.getPointerInfo().getLocation().getY() - this.getLocationOnScreen().getY());
            int previousX = pressedX;
            int previousY = pressedY;
            //Swaps x if the user drags from right to left, swaps y if the user
            // drags from bottom to top. Skips operation if line is selected.
            if(!shapeMenu.getSelectedItem().toString().equals("Line"))
            {
                if(previousX > currentX)
                {
                    int temp = currentX;
                    currentX = previousX;
                    previousX = temp;
                }

                if(previousY > currentY)
                {
                    int temp = currentY;
                    currentY = previousY;
                    previousY = temp;
                }
            }

            ss.setCurrentShape(colorMenu.getSelectedItem().toString(),
                    shapeMenu.getSelectedItem().toString(),
                    filledCheckBox.isSelected(), previousX, previousY,
                    currentX, currentY);

            clicked = true;
        }
        else if(SwingUtilities.isRightMouseButton(e) && (draggedShape != null))
        {
            draggedShape.moveBy(e.getX() - rightClickedX, e.getY() - rightClickedY);
            rightClickedX = e.getX();
            rightClickedY = e.getY();
            repaint();
        }
    }
    
    
    
    
    
    /**
     * Unused.
     *
     * @param e unused
     */
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
    
    }
    
    /**
     * Unused.
     *
     * @param e unused
     */
    
    @Override
    public void mouseEntered(MouseEvent e)
    {
    
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if(draggedShape != null)
        {
        
        }
    }
}
