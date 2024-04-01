package com.classuml.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import com.classuml.Model.*;

/**
 * The {@code GuiView} class extends {@code JComponent} to represent a visual component
 * that displays class information, including its name, fields, methods, and relationships.
 * It supports basic interactivity, such as dragging to reposition the component within its parent container.
 */
public class guiView extends JComponent {
    private static final long serialVersionUID = 1L;

    private List<UMLClass> classes;

    private List<Relationship> relationships;

    private final int padding = 10;
    
    private Point lastPoint;
    

    
   /**
     * Constructs a {@code GuiView} component with specified class information.
     * 
     * @param className     The name of the class.
     * @param fields        The list of field descriptions.
     * @param methods       The list of method descriptions.
     * @param relationships The list of relationship descriptions.
     */
    public guiView(List<UMLClass> classes, List<Relationship> relationships) {
        this.relationships = relationships;
        this.classes = classes;
       
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
    public void updateContents(List<UMLClass> classes, List<Relationship> relationships) {
        this.classes = classes;
        this.relationships = relationships;
        revalidate();
        repaint();
    }

    interface Builder {
        void drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below);
        void drawArrowHead(Graphics g, int x1, int y1, boolean flip, boolean pointDown, boolean pointUp);
    }
    class ArrowInheritanceBuidler implements Builder {
        public void drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below){
            if(toRight){
                g.drawLine(x1, y1, x2+10, y2);
                drawArrowHead(g, x2, y2, true, false, false);
            }else if(above){
                g.drawLine(x1, y1, x2, y2-10);
                drawArrowHead(g, x2, y2, false, true, false);
            }else if(below){
                g.drawLine(x1, y1, x2, y2+10);
                drawArrowHead(g, x2, y2, false, false, true);
            }else{
                g.drawLine(x1, y1, x2-10, y2);
                drawArrowHead(g, x2, y2, false, false, false);
            }
        }
        public void drawArrowHead(Graphics g, int x1, int y1, boolean flip, boolean pointDown, boolean pointUp){
            if(flip){
                g.drawLine(x1 + 10, y1 - 10, x1, y1);
                g.drawLine(x1, y1, x1+10, y1+10);
                g.drawLine(x1+10, y1+10, x1+10, y1);
                g.drawLine(x1+10, y1, x1+10, y1-10);
            }else if(pointDown){
                g.drawLine(x1 - 10, y1 - 10, x1, y1);
                g.drawLine(x1, y1, x1+10, y1-10);
                g.drawLine(x1+10, y1-10, x1, y1-10);
                g.drawLine(x1, y1-10, x1-10, y1-10);
            }else if(pointUp){
                g.drawLine(x1 - 10, y1 + 10, x1, y1);
                g.drawLine(x1, y1, x1+10, y1+10);
                g.drawLine(x1+10, y1+10, x1, y1+10);
                g.drawLine(x1, y1+10, x1-10, y1+10);
            }else{
                g.drawLine(x1 - 10, y1 - 10, x1, y1);
                g.drawLine(x1, y1, x1-10, y1+10);
                g.drawLine(x1-10, y1+10, x1-10, y1);
                g.drawLine(x1-10, y1, x1-10, y1-10); 
            }
            
        }
        
    }
    class ArrowRealizationBuidler implements Builder {
        public void drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below){
            Graphics2D g2d = (Graphics2D) g.create();
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            if(toRight){
                g2d.drawLine(x1, y1, x2+10, y2);
                drawArrowHead(g, x2, y2, true, false, false);
            }else if(above){
                g2d.drawLine(x1, y1, x2, y2-10);
                drawArrowHead(g, x2, y2, false, true, false);
            }else if(below){
                g2d.drawLine(x1, y1, x2, y2+10);
                drawArrowHead(g, x2, y2, false, false, true);
            }else{
                g2d.drawLine(x1, y1, x2-10, y2);
                drawArrowHead(g, x2, y2, false, false, false);
            }
        }
        public void drawArrowHead(Graphics g, int x1, int y1, boolean flip, boolean pointDown, boolean pointUp){
            if(flip){
                g.drawLine(x1 + 10, y1 - 10, x1, y1);
                g.drawLine(x1, y1, x1+10, y1+10);
                g.drawLine(x1+10, y1+10, x1+10, y1);
                g.drawLine(x1+10, y1, x1+10, y1-10);
            }else if(pointDown){
                g.drawLine(x1 - 10, y1 - 10, x1, y1);
                g.drawLine(x1, y1, x1+10, y1-10);
                g.drawLine(x1+10, y1-10, x1, y1-10);
                g.drawLine(x1, y1-10, x1-10, y1-10);
            }else if(pointUp){
                g.drawLine(x1 - 10, y1 + 10, x1, y1);
                g.drawLine(x1, y1, x1+10, y1+10);
                g.drawLine(x1+10, y1+10, x1, y1+10);
                g.drawLine(x1, y1+10, x1-10, y1+10);
            }else{
                g.drawLine(x1 - 10, y1 - 10, x1, y1);
                g.drawLine(x1, y1, x1-10, y1+10);
                g.drawLine(x1-10, y1+10, x1-10, y1);
                g.drawLine(x1-10, y1, x1-10, y1-10); 
            }
            
        }
    }
    class ArrowCompositionBuidler implements Builder {
        public void drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below){
            if(toRight){
                g.drawLine(x1, y1, x2+20, y2);
                drawArrowHead(g, x2, y2, true, false, false);
            }else if(above){
                g.drawLine(x1, y1, x2, y2-20);
                drawArrowHead(g, x2, y2, false, true, false);
            }else if(below){
                g.drawLine(x1, y1, x2, y2+20);
                drawArrowHead(g, x2, y2, false, false, true);
            }else{
                g.drawLine(x1, y1, x2-20, y2);
                drawArrowHead(g, x2, y2, false, false, false);
            }
        }
        public void drawArrowHead(Graphics g, int x1, int y1, boolean flip, boolean pointDown, boolean pointUp){
            if(flip){
                g.drawLine(x1, y1, x1+10, y1-10);
                g.drawLine(x1+10, y1-10, x1+20, y1);
                g.drawLine(x1+20, y1, x1+10, y1+10);
                g.drawLine(x1+10, y1+10, x1, y1);
                arrrowHeadFill(g, x1, y1, false);
            }else if(pointDown){
                g.drawLine(x1, y1, x1+10, y1-10);
                g.drawLine(x1+10, y1-10, x1, y1-20);
                g.drawLine(x1, y1-20, x1-10, y1-10);
                g.drawLine(x1-10, y1-10, x1, y1);
                arrrowHeadFill(g, x1, y1-20, true);
            }else if(pointUp){
                g.drawLine(x1, y1, x1+10, y1+10);
                g.drawLine(x1+10, y1+10, x1, y1+20);
                g.drawLine(x1, y1+20, x1-10, y1+10);
                g.drawLine(x1-10, y1+10, x1, y1);
                arrrowHeadFill(g, x1, y1, true);
            }else{
                g.drawLine(x1, y1, x1-10, y1-10);
                g.drawLine(x1-10, y1-10, x1-20, y1);
                g.drawLine(x1-20, y1, x1-10, y1+10);
                g.drawLine(x1-10, y1+10, x1, y1);
                arrrowHeadFill(g, x1-20, y1, false);
            }
            
        }
        public void arrrowHeadFill(Graphics g, int x1, int y1, boolean turn){
            if(turn == false){
                int [] x = {x1, x1+10, x1+20, x1+10, x1};
                int [] y = {y1, y1-10, y1, y1+10, y1};
                g.fillPolygon(x, y, 4);
            }else{
                int [] x = {x1, x1-10, x1, x1+10, x1};
                int [] y = {y1, y1+10, y1+20, y1+10, y1};
                g.fillPolygon(x, y, 4);
            }
        }
    }
    class ArrowAggregationBuidler implements Builder {
        public void drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below){
            g.setColor(Color.black);
            if(toRight){
                g.drawLine(x1, y1, x2+20, y2);
                drawArrowHead(g, x2, y2, true, false, false);
            }else if(above){
                g.drawLine(x1, y1, x2, y2-20);
                drawArrowHead(g, x2, y2, false, true, false);
            }else if(below){
                g.drawLine(x1, y1, x2, y2+20);
                drawArrowHead(g, x2, y2, false, false, true);
            }else{
                g.drawLine(x1, y1, x2-20, y2);
                drawArrowHead(g, x2, y2, false, false, false);
            }
            
        }
        public void drawArrowHead(Graphics g, int x1, int y1, boolean flip, boolean pointDown, boolean pointUp){
            if(flip){
                g.drawLine(x1, y1, x1+10, y1-10);
                g.drawLine(x1+10, y1-10, x1+20, y1);
                g.drawLine(x1+20, y1, x1+10, y1+10);
                g.drawLine(x1+10, y1+10, x1, y1);
            }else if(pointDown){
                g.drawLine(x1, y1, x1+10, y1-10);
                g.drawLine(x1+10, y1-10, x1, y1-20);
                g.drawLine(x1, y1-20, x1-10, y1-10);
                g.drawLine(x1-10, y1-10, x1, y1);
            }else if(pointUp){
                g.drawLine(x1, y1, x1+10, y1+10);
                g.drawLine(x1+10, y1+10, x1, y1+20);
                g.drawLine(x1, y1+20, x1-10, y1+10);
                g.drawLine(x1-10, y1+10, x1, y1);
            }else{
                g.drawLine(x1, y1, x1-10, y1-10);
                g.drawLine(x1-10, y1-10, x1-20, y1);
                g.drawLine(x1-20, y1, x1-10, y1+10);
                g.drawLine(x1-10, y1+10, x1, y1);
            }
            
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for(UMLClass c : classes){
            prepareForDrawing(g, c);
            drawComponentContent(g, c);
        }
        drawArrows(g);
    }
    private void drawArrows(Graphics g){
        if (this.relationships != null){
            for (Relationship rel : relationships){
                UMLClass c1 = new UMLClass();
                UMLClass c2 = new UMLClass();
                for (UMLClass cls : classes){
                    if (cls.getName().equals(rel.getSource())){
                        c1 = cls;
                    }
                    if (cls.getName().equals(rel.getDestination())){
                        c2 = cls;
                    }
                }
                if (rel.getType() == 1){
                    ArrowAggregationBuidler aggArrow = new ArrowAggregationBuidler();
                    if(c1.position.x + c1.uniformWidth < c2.position.x){
                        aggArrow.drawArrowBody(g, c1.position.x + c1.uniformWidth, c1.position.y + (c1.totalHeight/2), c2.position.x, c2.position.y + (c2.totalHeight/2)
                                                ,false, false, false);
                    }else if (c1.position.x > c2.position.x + c2.uniformWidth){
                        aggArrow.drawArrowBody(g, c1.position.x, c1.position.y + (c1.totalHeight/2), c2.position.x + c2.uniformWidth, c2.position.y + (c2.totalHeight/2)
                                                ,true , false, false);
                    }else if (c1.position.y + c1.totalHeight < c2.position.y){
                        aggArrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y + c1.totalHeight, c2.position.x + (c2.uniformWidth/2), c2.position.y
                                                ,false, true, false);
                    }else if (c1.position.y > c2.position.y + c2.totalHeight){
                        aggArrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y, c2.position.x + (c2.uniformWidth/2), c2.position.y + c2.totalHeight
                                                ,false, false, true);
                    }else{

                    }
                    
                }
                if (rel.getType() == 2){
                    ArrowCompositionBuidler compArrow = new ArrowCompositionBuidler();
                    if(c1.position.x + c1.uniformWidth < c2.position.x){
                        compArrow.drawArrowBody(g, c1.position.x + c1.uniformWidth, c1.position.y + (c1.totalHeight/2), c2.position.x, c2.position.y + (c2.totalHeight/2) 
                                                ,false, false, false);
                    }else if (c1.position.x > c2.position.x + c2.uniformWidth){
                        compArrow.drawArrowBody(g, c1.position.x, c1.position.y + (c1.totalHeight/2), c2.position.x + c2.uniformWidth, c2.position.y + (c2.totalHeight/2)
                                                ,true, false, false);
                    }else if (c1.position.y + c1.totalHeight < c2.position.y){
                        compArrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y + c1.totalHeight, c2.position.x + (c2.uniformWidth/2), c2.position.y
                                                ,false, true, false);
                    }else if (c1.position.y > c2.position.y + c2.totalHeight){
                        compArrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y, c2.position.x + (c2.uniformWidth/2), c2.position.y + c2.totalHeight
                                                ,false, false, true);
                    }else{

                    }
                }
                if (rel.getType() == 3){
                    ArrowInheritanceBuidler inhertArrow = new ArrowInheritanceBuidler();
                    if(c1.position.x + c1.uniformWidth < c2.position.x){
                        inhertArrow.drawArrowBody(g, c1.position.x + c1.uniformWidth, c1.position.y + (c1.totalHeight/2), c2.position.x, c2.position.y + (c2.totalHeight/2)
                                                ,false, false, false);
                    }else if (c1.position.x > c2.position.x + c2.uniformWidth){
                        inhertArrow.drawArrowBody(g, c1.position.x, c1.position.y + (c1.totalHeight/2), c2.position.x + c2.uniformWidth, c2.position.y + (c2.totalHeight/2)
                                                ,true, false, false);
                    }else if (c1.position.y + c1.totalHeight < c2.position.y){
                        inhertArrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y + c1.totalHeight, c2.position.x + (c2.uniformWidth/2), c2.position.y
                                                ,false, true, false);
                    }else if (c1.position.y > c2.position.y + c2.totalHeight){
                        inhertArrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y, c2.position.x + (c2.uniformWidth/2), c2.position.y + c2.totalHeight
                                                ,false, false, true);
                    }else{

                    }
                }
                if (rel.getType() == 4){
                    ArrowRealizationBuidler realArrow = new ArrowRealizationBuidler();
                    if(c1.position.x + c1.uniformWidth < c2.position.x){
                        realArrow.drawArrowBody(g, c1.position.x + c1.uniformWidth, c1.position.y + (c1.totalHeight/2), c2.position.x, c2.position.y + (c2.totalHeight/2)
                                                ,false, false, false);
                    }else if (c1.position.x > c2.position.x + c2.uniformWidth){
                        realArrow.drawArrowBody(g, c1.position.x, c1.position.y + (c1.totalHeight/2), c2.position.x + c2.uniformWidth, c2.position.y + (c2.totalHeight/2)
                                                ,true, false, false);
                    }else if (c1.position.y + c1.totalHeight < c2.position.y){
                        realArrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y + c1.totalHeight, c2.position.x + (c2.uniformWidth/2), c2.position.y
                                                ,false, true, false);
                    }else if (c1.position.y > c2.position.y + c2.totalHeight){
                        realArrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y, c2.position.x + (c2.uniformWidth/2), c2.position.y + c2.totalHeight
                                                ,false, false, true);
                    }else{

                    }
                }
            }
        }
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
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastPoint = null;
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
    private void prepareForDrawing(Graphics g, UMLClass c) {
        c.fm = g.getFontMetrics();
        cacheMetrics(c);
    }

    /**
     * Caches metrics such as {@code FontMetrics}, uniform width, and total height.
     * This is called when the component is added to the container and when metrics need to be recalculated.
     */
    private void cacheMetrics(UMLClass c) {
        if (c.fm != null) {
            c.uniformWidth = calculateMaxTextWidth(c);
        }
    }

    /**
     * Handles the dragging of the component within its parent container, ensuring it stays within bounds.
     *
     * @param e The {@code MouseEvent} containing the current mouse coordinates.
     */
    private void handleDrag(MouseEvent e) {
        for(UMLClass c: classes){
            if (lastPoint != null && withinPosition(e, c)) {
                int dx = e.getX() - lastPoint.x;
                int dy = e.getY() - lastPoint.y;

                updatePosition(dx, dy, c);
                lastPoint = e.getPoint();
                repaint();
            }
        }
        
    }

    /**
     * Updates the position of this component based on drag movements.
     *
     * @param dx The change in X direction.
     * @param dy The change in Y direction.
     */
    private void updatePosition(int dx, int dy, UMLClass c) {
        c.position.setLocation((c.position.getX() + dx), (c.position.getY() + dy));
    }

    private boolean withinPosition(MouseEvent e, UMLClass c){
        if(e.getX() >= c.position.getX()
            && e.getX() <= (c.position.getX() + c.uniformWidth)
            && e.getY() >= c.position.getY()
            && e.getY() <= (c.position.getY() + c.totalHeight)){
                return true;
            }
            else{
                return false;
            }
    }

    /**
     * Draws the content of this component, including class name, fields, methods, and relationships.
     *
     * @param g The {@code Graphics} context used for drawing.
     */
    private void drawComponentContent(Graphics g, UMLClass c) {
        int localX = (int)c.position.getX();
        int localY = (int)c.position.getY();
        localY = drawItem(g, "Class: " + c.getName(), localX, localY, true, c);

        if(c.getFields().size() > 0){
            localY = drawItem(g, "Fields: " + c.getFields(), localX, localY, false, c);
        }
        

        for (Method method : c.getMethods()) {
            localY = drawItem(g, "Method: " + method, localX, localY, false, c);
        }
        c.totalHeight = localY - c.position.y;
    }

    /**
     * Calculates the maximum width needed for the component's text content.
     *
     * @param fm The {@code FontMetrics} used to measure text width.
     * @return The maximum text width.
     */
    private int calculateMaxTextWidth(UMLClass c) {
        int maxWidth = c.fm.stringWidth("Class: " + c.getName());
        maxWidth = Math.max(maxWidth, calculateWidthForField(c.fm, c.getFields(), "Field: "));
        maxWidth = Math.max(maxWidth, calculateWidthForMethod(c.fm, c.getMethods(), "Method: "));
        return maxWidth + padding * 2;
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
    private int drawItem(Graphics g, String text, int x, int y, boolean isHeader, UMLClass c) {
        // Split the text into lines
        String[] lines = text.split("\n");

        // Set the color and height for header or item
        Color bgColor = isHeader ? new Color(135, 206, 250) : Color.WHITE;
        int lineHeight = c.fm.getHeight() + padding / 2;  // Adjust padding for each line

        // Calculate the total height of the text block (add extra padding if there are multiple lines)
        c.totalHeight = lineHeight * lines.length + (lines.length > 1 ? padding : 0);

        // Draw the background rectangle for the entire text block
        g.setColor(bgColor);
        g.fillRect(x, y, c.uniformWidth, c.totalHeight);
        
        // Draw the border
        if (!isHeader) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, c.uniformWidth, c.totalHeight);
        }

        // Draw each line of text within the same box
        int currentY = y + c.fm.getAscent() + padding / 2;  // Start drawing at this Y position
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
            g.drawRect(x, y, c.uniformWidth, c.totalHeight);
        }

        // Return the Y position after the entire text block
        return y + c.totalHeight;
    }
    
    /**
     * Calculates the width for a list of items (fields, methods) including a prefix.
     *
     * @param fm     The {@code FontMetrics} for measuring text.
     * @param items  The list of items to measure.
     * @param prefix The prefix to add before each item.
     * @return The maximum width needed for the items.
     */
    private int calculateWidthForMethod(FontMetrics fm, List<Method> items, String prefix) {
        int maxWidth = 0;
        for (Method item : items) {
            maxWidth = Math.max(maxWidth, fm.stringWidth(prefix + item.toString().split(",")[0].trim()));
        }
        return maxWidth;
    }

    private int calculateWidthForField(FontMetrics fm, List<Field> items, String prefix) {
        int maxWidth = 0;
        for (Field item : items) {
            maxWidth = Math.max(maxWidth, fm.stringWidth(prefix + item.toString().split(",")[0].trim()));
        }
        return maxWidth;
    }
}
