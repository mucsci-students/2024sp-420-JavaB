package com.classuml.View;

import javax.swing.*;

import com.classuml.Model.UMLClass;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

/**
 * The {@code GuiView} class extends {@code JComponent} to represent a visual component
 * that displays class information, including its name, fields, methods, and relationships.
 * It supports basic interactivity, such as dragging to reposition the component within its parent container.
 */
public class guiView extends JComponent {
    private static final long serialVersionUID = 1L;
    
    private String className;
    private List<String> fields;
    private List<String> methods;
    private UMLClass guiClass;

    private final int padding = 10;
    private int x = 20;
    private int y = 20;
    private Point lastPoint;
    private boolean isClicked = false;

    private FontMetrics fm;
    private int uniformWidth = -1; // Cached width for uniform drawing
    private int totalHeight = -1; // Cached total height

    private int boxWidth = 0;
    private int boxHeight = 0;

    
   /**
     * Constructs a {@code GuiView} component with specified class information.
     * 
     * @param className     The name of the class.
     * @param fields        The list of field descriptions.
     * @param methods       The list of method descriptions.
     * @param relationships The list of relationship descriptions.
     */
    public guiView(String className, List<String> fields, List<String> methods, UMLClass guiClass) {
        this.className = className;
        this.fields = fields;
        this.methods = methods;
        this.guiClass = guiClass;
        initComponent();
    }

    /**
     * Updates the content of this component based on the specified parameters.
     * 
     * @param className     The new class name.
     * @param fields        The new list of fields.
     * @param methods       The new list of methods.
     * @param relationships The new list of relationships.
     */
    public void updateContents(String className, List<String> fields, List<String> methods) {
        this.className = className;
        this.fields = fields;
        this.methods = methods;

        setPreferredSize(calculatePreferredSize());
        revalidate();
        repaint();
    }


    /**
     * Sets the position of the component.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        Point pos = new Point(x, y);
        guiClass.setPosition(pos);
        Rectangle bounds = getBounds();
        setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public Dimension getPreferredSize() {
        if (fm == null) {
            fm = getFontMetrics(getFont());
        }
        return new Dimension(calculateMaxTextWidth(fm), calculateTotalHeight(fm));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        prepareForDrawing(g);
        drawComponentContent(g);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        cacheMetrics();
    }

    /**
     * Initializes the component by setting up mouse listeners for drag functionality.
     */
    private void initComponent() {
        lastPoint = null;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastPoint = e.getPoint();
                if(e.getX() >= x && e.getX() <= x + boxWidth && e.getY() >= y - (padding) && e.getY() <= (y + (padding) + boxHeight)){
                    isClicked = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastPoint = null;
                isClicked = false;
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleDrag(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // Not used
            }
        });
    }

    /**
     * Prepares graphics settings for drawing the component content.
     * This method sets the {@code FontMetrics} and calculates dimensions if necessary.
     *
     * @param g The {@code Graphics} context used for drawing.
     */
    private void prepareForDrawing(Graphics g) {
        fm = g.getFontMetrics();
        if (uniformWidth == -1 || totalHeight == -1) {
            cacheMetrics();
        }
    }

    /**
     * Caches metrics such as {@code FontMetrics}, uniform width, and total height.
     * This is called when the component is added to the container and when metrics need to be recalculated.
     */
    private void cacheMetrics() {
        if (fm != null) {
            uniformWidth = calculateMaxTextWidth(fm);
            totalHeight = calculateTotalHeight(fm);
        }
    }

    /**
     * Handles the dragging of the component within its parent container, ensuring it stays within bounds.
     *
     * @param e The {@code MouseEvent} containing the current mouse coordinates.
     */
    private void handleDrag(MouseEvent e) {
        if (lastPoint != null && isClicked == true) {
            int dx = e.getX() - lastPoint.x;
            int dy = e.getY() - lastPoint.y;

            updatePosition(dx, dy);
            lastPoint = e.getPoint();
            repaint();
        }
    }

    /**
     * Updates the position of this component based on drag movements.
     *
     * @param dx The change in X direction.
     * @param dy The change in Y direction.
     */
    private void updatePosition(int dx, int dy) {
        this.x += dx;
        this.y += dy;

        Rectangle bounds = getBounds();
        int newX = Math.max(0, Math.min(bounds.x + x, getParent().getWidth() - bounds.width));
        int newY = Math.max(0, Math.min(bounds.y + y, getParent().getHeight() - bounds.height));
        Point pos = new Point(newX, newY);
        guiClass.setPosition(pos);

        setBounds(newX, newY, bounds.width, bounds.height);
    }

    /**
     * Calculates and returns the preferred size of this component based on its content.
     *
     * @return The preferred {@code Dimension} of this component.
     */
    private Dimension calculatePreferredSize() {
        ensureFontMetrics();
        return new Dimension(calculateMaxTextWidth(fm), calculateTotalHeight(fm));
    }

    /**
     * Ensures that {@code FontMetrics} is initialized. If not, it initializes it with the current font.
     */
    private void ensureFontMetrics() {
        if (fm == null) {
            Font font = getFont();
            if (font == null) {
                font = new Font("Dialog", Font.PLAIN, 12);
                setFont(font);
            }
            fm = getFontMetrics(font);
        }
    }

    /**
     * Draws the content of this component, including class name, fields, methods, and relationships.
     *
     * @param g The {@code Graphics} context used for drawing.
     */
    private void drawComponentContent(Graphics g) {
        int localY = y;
        localY = drawItem(g, "Class: " + className, x, localY, true);

        //for (String field : fields) {
            localY = drawItem(g, "Fields: " + fields, x, localY, false);
        //}
        for (String method : methods) {
            localY = drawItem(g, "Method: " + method, x, localY, false);
        }
        // for (String relationship : relationships) {
        //     localY = drawItem(g, relationship, x, localY, false);
        // }
    }

    /**
     * Calculates the maximum width needed for the component's text content.
     *
     * @param fm The {@code FontMetrics} used to measure text width.
     * @return The maximum text width.
     */
    private int calculateMaxTextWidth(FontMetrics fm) {
        int maxWidth = fm.stringWidth("Class: " + className);
        maxWidth = Math.max(maxWidth, calculateWidthForList(fm, fields, "Field: "));
        maxWidth = Math.max(maxWidth, calculateWidthForList(fm, methods, "Method: "));
        return boxWidth = maxWidth + padding * 2;
    }

    /**
     * Calculates the total height needed for the component's text content.
     *
     * @param fm The {@code FontMetrics} used to measure text height.
     * @return The total text height.
     */
    private int calculateTotalHeight(FontMetrics fm) {
        int heightPerItem = fm.getHeight();
        int totalItems =  (fm.getHeight() * fields.size()) + (fm.getHeight() * methods.size());
        return boxHeight = (heightPerItem + padding) * totalItems + padding * 2;
    }

 
    /**
     * Draws an individual item (class name, field, method, or relationship) on this component.
     *
     * @param g         The {@code Graphics} context used for drawing.
     * @param text      The text to draw.
     * @param x         The x-coordinate at which to start drawing.
     * @param y         The y-coordinate at which to start drawing.
     * @param isHeader  Indicates whether the item is a header (class name).
     * @return The y-coordinate after drawing the item.
     */
    private int drawItem(Graphics g, String text, int x, int y, boolean isHeader) {
        // Split the text into lines
        String[] lines = text.split("\n");

        // Set the color and height for header or item
        Color bgColor = isHeader ? new Color(135, 206, 250) : Color.WHITE;
        int lineHeight = fm.getHeight() + padding / 2;  // Adjust padding for each line

        // Calculate the total height of the text block (add extra padding if there are multiple lines)
        int textBlockHeight = lineHeight * lines.length + (lines.length > 1 ? padding : 0);

        // Draw the background rectangle for the entire text block
        g.setColor(bgColor);
        g.fillRect(x, y, uniformWidth, textBlockHeight);
        
        // Draw the border
        if (!isHeader) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, uniformWidth, textBlockHeight);
        }

        // Draw each line of text within the same box
        int currentY = y + fm.getAscent() + padding / 2;  // Start drawing at this Y position
        for (String line : lines) {
            // Draw the text
            g.setColor(Color.BLACK);
            g.drawString(line, x + padding, currentY);

            // Increment the Y position for the next line
            currentY += lineHeight;
        }

        // Draw the border for the header once around the entire text block
        if (isHeader) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, uniformWidth, textBlockHeight);
        }

        // Return the Y position after the entire text block
        return y + textBlockHeight;
    }
    
    /**
     * Calculates the width for a list of items (fields, methods) including a prefix.
     *
     * @param fm     The {@code FontMetrics} for measuring text.
     * @param items  The list of items to measure.
     * @param prefix The prefix to add before each item.
     * @return The maximum width needed for the items.
     */
    private int calculateWidthForList(FontMetrics fm, List<String> items, String prefix) {
        int maxWidth = 0;
        for (String item : items) {
            maxWidth = Math.max(maxWidth, fm.stringWidth(prefix + item.split(",")[0].trim()));
        }
        return maxWidth;
    }

    public String getCName(){
        return className;
    }

    public List<String> getCFields(){
        return fields;
    }

    public List<String> getCMethods(){
        return methods;
    }

    public void setCName(String cName){
        className = cName;
    }

    public void setCFields(List<String> cFields){
        fields = cFields;
    }

    public void setCMethods(List<String> cMethods){
        methods = cMethods;
    }
}
