package com.classuml.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Optional;

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


    /*
     * Draws the arrows for the relationships, allows for arrows to
     * avoid each other by rerouting one of them
     */
    private void drawArrows(Graphics g){
        if (this.relationships != null){

            for (Relationship rel : relationships){
                if(relationships.size() > 1){
                    for(Relationship rel2: relationships){
                        if(!rel.getSource().equals(rel2.getSource())
                            && !rel.getDestination().equals(rel2.getSource())
                            && !rel.getSource().equals(rel2.getDestination())
                            && !rel.getDestination().equals(rel2.getDestination())){
                                boolean temp = checkArrows(rel, rel2, g);
                                if(temp == true && rel2.isRerouted == false){
                                    rel.isRerouted = temp;
                                    reroutArrow(rel, rel2, g); 
                                }else
                                {
                                    rel.isRerouted = false;
                                }
                        }
                        
                    }
                }

                if(rel.isRerouted == true){
                    continue;   //the new arrow will already be drawn, so skip drawing this one
                }

                if(rel.getType() == 1){
                    ArrowAggregationBuidler aggArrow = new ArrowAggregationBuidler();
                    drawType(rel, aggArrow, g, Optional.empty(), Optional.empty());
                }
                if(rel.getType() == 2){
                    ArrowCompositionBuidler compArrow = new ArrowCompositionBuidler();
                    drawType(rel, compArrow, g, Optional.empty(), Optional.empty());
                }
                if(rel.getType() == 3){
                    ArrowInheritanceBuidler inArrow = new ArrowInheritanceBuidler();
                    drawType(rel, inArrow, g, Optional.empty(), Optional.empty());
                }
                if(rel.getType() == 4){
                    ArrowRealizationBuidler relArrow = new ArrowRealizationBuidler();
                    drawType(rel, relArrow, g, Optional.empty(), Optional.empty());
                }
                
            }
        }
    }


    /*
     * Draws the specific type of arrow for the relationship
     */
    public void drawType(Relationship rel, Builder arrow, Graphics g, Optional<Integer> reroutX, Optional<Integer> reroutY){
        UMLClass c1 = null;
        if(reroutX.isEmpty() && reroutY.isEmpty()){
           c1 = getSourceFromRelationship(rel); 
        }
        
        UMLClass c2 = getDestinationFromRelationship(rel);

        if (c1 != null){
            if(c1.position.x + c1.uniformWidth < c2.position.x){
                arrow.drawArrowBody(g, c1.position.x + c1.uniformWidth, c1.position.y + (c1.totalHeight/2), c2.position.x, c2.position.y + (c2.totalHeight/2)
                                        ,false, false, false);
            }else if (c1.position.x > c2.position.x + c2.uniformWidth){
                arrow.drawArrowBody(g, c1.position.x, c1.position.y + (c1.totalHeight/2), c2.position.x + c2.uniformWidth, c2.position.y + (c2.totalHeight/2)
                                        ,true , false, false);
            }else if (c1.position.y + c1.totalHeight < c2.position.y){
                arrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y + c1.totalHeight, c2.position.x + (c2.uniformWidth/2), c2.position.y
                                        ,false, true, false);
            }else if (c1.position.y > c2.position.y + c2.totalHeight){
                arrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y, c2.position.x + (c2.uniformWidth/2), c2.position.y + c2.totalHeight
                                        ,false, false, true);
            }else{

            }
            
        }else{
            if(reroutX.get() < c2.position.x){
                arrow.drawArrowBody(g, reroutX.get(), reroutY.get(), c2.position.x, c2.position.y + (c2.totalHeight/2)
                                        ,false, false, false);
            }else if (reroutX.get() > c2.position.x + c2.uniformWidth){
                arrow.drawArrowBody(g, reroutX.get(), reroutY.get(), c2.position.x + c2.uniformWidth, c2.position.y + (c2.totalHeight/2)
                                        ,true , false, false);
            }else if (reroutY.get() < c2.position.y){
                arrow.drawArrowBody(g, reroutX.get(), reroutY.get(), c2.position.x + (c2.uniformWidth/2), c2.position.y
                                        ,false, true, false);
            }else if (reroutY.get() > c2.position.y + c2.totalHeight){
                arrow.drawArrowBody(g, reroutX.get(), reroutY.get(), c2.position.x + (c2.uniformWidth/2), c2.position.y + c2.totalHeight
                                        ,false, false, true);
            }else{

            }
        }
    }


    /*
     * Is used for detecting if arrows are crossing each other,
     * will rerout one arrow if paths are crossing
     */
    public boolean checkArrows(Relationship relate, Relationship relate2, Graphics g){
        //mids are the middle points of the GUI class boxes
        Point[] mids1 = getClassesMidPoints(relate);
        double[] line1 = lineEquation(mids1);

                    
        if(relate != relate2){
                            
            Point[] mids2 = getClassesMidPoints(relate2);
            double[] line2 = lineEquation(mids2);


            //Optional<Integer> is used in case the value found is null
            if((line2[0] - line1[0]) != 0){
                double intersectX = (line2[1] - line1[1]) / (line1[0] - line2[0]);
                double intersectY = (line1[0] * intersectX) + line1[1];
                                
                if(intersecting(mids1, mids2, intersectX, intersectY)){
                    double x1 = Math.max((double)mids1[0].x, (double)mids1[1].x) - Math.min((double)mids1[0].x, (double)mids1[1].x);
                    double y1 = Math.max((double)mids1[0].y, (double)mids1[1].y) - Math.min((double)mids1[0].y, (double)mids1[1].y);
                    double distance1 = Math.sqrt((x1 * x1) + (y1 * y1));

                    double x2 = Math.max((double)mids1[0].x, (double)mids1[1].x) - Math.min((double)mids1[0].x, (double)mids1[1].x);
                    double y2 = Math.max((double)mids1[0].y, (double)mids1[1].y) - Math.min((double)mids1[0].y, (double)mids1[1].y);
                    double distance2 = Math.sqrt((x2 * x2) + (y2 * y2));
                    if(distance1 >= distance2){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        return false;
    }


    public void reroutArrow(Relationship relate, Relationship toAvoidRelate, Graphics g){
        UMLClass c1 = getSourceFromRelationship(relate);
        UMLClass c2 = getDestinationFromRelationship(relate);
        Point[] mids1 = getClassesMidPoints(relate);

        UMLClass toAvoidC1 = getSourceFromRelationship(toAvoidRelate);
        UMLClass toAvoidC2 = getDestinationFromRelationship(toAvoidRelate);
        
        
        double closerTo1 = Math.sqrt(Math.pow(toAvoidC1.position.x - c2.position.x,2) + Math.pow(toAvoidC1.position.y - c2.position.y,2));
        double closerTo2 = Math.sqrt(Math.pow(toAvoidC2.position.x - c2.position.x,2) + Math.pow(toAvoidC2.position.y - c2.position.y,2));
        
        if(closerTo1 <= closerTo2){//closer to source if true
            drawRerout(relate, c1, c2, toAvoidC1, toAvoidC2, mids1, g);
        }else{
            drawRerout(relate, c1, c2, toAvoidC2, toAvoidC1, mids1, g);
        }     
    }

    public void drawRerout(Relationship relate, UMLClass c1, UMLClass c2, UMLClass closer, UMLClass farther, Point[]mids1, Graphics g){
        int greaterY = Math.max(closer.position.y + closer.totalHeight + 20, mids1[0].y);
        int greaterX = Math.max(closer.position.x, c1.position.x);
        int lesserY = Math.min(c1.position.y, (closer.position.y - 20));

        int reroutX = 0;
        int reroutY = 0;

        if(closer.position.y >= farther.position.y){//avoiding source is higher than destination
            if(greaterY >= mids1[0].y + (c1.totalHeight / 2) ){
             if(greaterX != c1.position.x){
                 g.drawLine(mids1[0].x, mids1[0].y + (c1.totalHeight / 2), mids1[0].x, greaterY);
                 g.drawLine(mids1[0].x, greaterY, c1.position.x + c1.uniformWidth, greaterY);
             }else{
                 g.drawLine(mids1[0].x, mids1[0].y + (c1.totalHeight / 2), mids1[0].x, greaterY);
                 g.drawLine(mids1[0].x, greaterY, c1.position.x, greaterY);
             } 
         }
         //draw line from class 1 around avoidclass1 
         if(greaterX != c1.position.x){
             g.drawLine(c1.position.x + c1.uniformWidth, greaterY, greaterX + closer.uniformWidth + 20, greaterY );
             reroutX = greaterX + closer.uniformWidth + 20;
             reroutY = greaterY;
             if(greaterX >= c2.position.x){
                 g.drawLine(greaterX + closer.uniformWidth + 20, greaterY, greaterX + closer.uniformWidth + 20, closer.position.y - 20);
                 reroutY = closer.position.y - 20;
             }
             
             //draw relationship line from this point on
         }else{
             //line should wrap around it's left side
             g.drawLine(c1.position.x, greaterY, closer.position.x - 20, greaterY);
             reroutX = closer.position.x - 20;
             reroutY = greaterY;
             if(closer.position.x <= c2.position.x){
                 g.drawLine(closer.position.x - 20, greaterY, closer.position.x - 20, closer.position.y - 20);
                 reroutY = closer.position.y - 20;
             }
             //draw relationship line from this point on
         } 
         }else{
             if(closer.position.y <= c1.position.y){
                 if(greaterX != c1.position.x){
                     g.drawLine(mids1[0].x, c1.position.y, mids1[0].x, closer.position.y - 20);
                     g.drawLine(mids1[0].x, closer.position.y - 20, c1.position.x + c1.uniformWidth, closer.position.y - 20);
                 }else{
                     g.drawLine(mids1[0].x, c1.position.y, mids1[0].x, closer.position.y - 20);
                     g.drawLine(mids1[0].x, closer.position.y - 20, c1.position.x, closer.position.y - 20);
                 } 
             }
             if(greaterX!= c1.position.x){
                 g.drawLine(c1.position.x + c1.uniformWidth, lesserY, closer.position.x + closer.uniformWidth + 20, lesserY);
                 reroutX = closer.position.x + closer.uniformWidth + 20;
                 reroutY = lesserY;
                 if(greaterX >= c2.position.x){
                     g.drawLine(closer.position.x + closer.uniformWidth + 20, lesserY, closer.position.x + closer.uniformWidth + 20, closer.position.y + 20);
                     reroutY = closer.position.y + 20;
                 }
                 //draw relationship line from here
             }else{
                g.drawLine(c1.position.x, lesserY, closer.position.x - 20, lesserY);
                reroutX = closer.position.x - 20;
                reroutY = lesserY;
                if(closer.position.x <= c2.position.x){
                    g.drawLine(closer.position.x - 20, lesserY, closer.position.x - 20, closer.position.y + 20);
                    reroutY = closer.position.y + 20;
                }
             }
         }

        Optional<Integer> reRoutX = Optional.of(reroutX);
        Optional<Integer> reRoutY = Optional.of(reroutY);

        if(relate.getType() == 1){
            ArrowAggregationBuidler aggArrow = new ArrowAggregationBuidler();
            drawType(relate, aggArrow, g, reRoutX, reRoutY);
        }
        if(relate.getType() == 2){
            ArrowCompositionBuidler compArrow = new ArrowCompositionBuidler();
            drawType(relate, compArrow, g, reRoutX, reRoutY);
        }
        if(relate.getType() == 3){
            ArrowInheritanceBuidler inArrow = new ArrowInheritanceBuidler();
            drawType(relate, inArrow, g, reRoutX, reRoutY);
        }
        if(relate.getType() == 4){
            ArrowRealizationBuidler relArrow = new ArrowRealizationBuidler();
            drawType(relate, relArrow, g, reRoutX, reRoutY);
        }

    }


    public double[] lineEquation(Point[] mids){
        double slope = ((double)mids[1].y - (double)mids[0].y) / ((double)mids[1].x - (double)mids[0].x);
        double yInt = (double)mids[0].y - ((double)mids[0].x * slope);
                    
        return new double[] {slope, yInt};
    }

    public Point[] getClassesMidPoints(Relationship relate){
        UMLClass c1 = getSourceFromRelationship(relate);
        UMLClass c2 = getDestinationFromRelationship(relate);
        Point c1Mid;
        Point c2Mid;
                    
        if(c1 != null && c2 != null){
            c1Mid = new Point(c1.position.x + (c1.uniformWidth / 2), c1.position.y + (c1.totalHeight/2));
            c2Mid = new Point(c2.position.x + (c2.uniformWidth / 2), c2.position.y + (c2.totalHeight/2));
            Point[] mids = {c1Mid, c2Mid};
            return mids;
        }
        return null;
    }

    public boolean intersecting(Point[] mids1, Point[] mids2, double intersectX, double intersectY){
        if(intersectX >= Math.min(mids1[0].x, mids1[1].x)
            && intersectX <= Math.max(mids1[0].x, mids1[1].x)
            && intersectX >= Math.min(mids2[0].x, mids2[1].x)
            && intersectX <= Math.max(mids2[0].x, mids2[1].x)){
                if(intersectY >= Math.min(mids1[0].y, mids1[1].y)
                    && intersectY <= Math.max(mids1[0].y, mids1[1].y)
                    && intersectY >= Math.min(mids2[0].y, mids2[1].y)
                    && intersectY <= Math.max(mids2[0].y, mids2[1].y)){
                        return true;
                    }

            }
        return false;
    }

    /**
     * Initializes the component by setting up mouse listeners for drag functionality.
     */
    private void initComponent() {
        lastPoint = null;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            @Override
            public void mousePressed(MouseEvent e) {
                for(UMLClass c: classes){
                    if(withinPosition(e, c)){
                        c.isClicked = true;
                        lastPoint = e.getPoint();
                    }
                }
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                for(UMLClass c: classes){
                    c.isClicked = false;
                    lastPoint = null;
                }
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
            if (lastPoint != null && c.isClicked) {
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
        if(e.getX() >= c.position.x
            && e.getX() <= (c.position.x + c.uniformWidth)
            && e.getY() >= c.position.y
            && e.getY() <= (c.position.y + c.totalHeight)){
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
            String fieldsText = "";
            for (Field field : c.getFields()) {
                fieldsText += "+ " + field.getName() + ": " + field.getType();
                if (c.getFields().size() > 1){
                    fieldsText += "\n";
                }
            }
            localY = drawItem(g, fieldsText, localX, localY, false, c);
        }
    
    
        if (c.getMethods().size() > 0) {
            String methodsText = "";
            for (Method method : c.getMethods()) {
                methodsText += "+ " + method.getName() + "() : " + method.getReturnType();
                if (method.getParameters().size() > 0) {
                    methodsText += "\n";
                    for (Parameter parameter : method.getParameters()) {
                        methodsText += "  - " + parameter.getName() + ": " + parameter.getType();
                        if (method.getParameters().size() > 1) {
                            methodsText += "\n";
                        }
                    }
                }
                if (c.getMethods().size() > 1){
                    methodsText += "\n";
                }
            }
            localY = drawItem(g, methodsText, localX, localY, false, c);
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

    private UMLClass getSourceFromRelationship(Relationship rel){
        
        for (UMLClass cls : classes){
            if (cls.getName().equals(rel.getSource())){
                return cls;
            }
        }
        return null;
    }

    private UMLClass getDestinationFromRelationship(Relationship rel){
        
        for (UMLClass cls : classes){
            if (cls.getName().equals(rel.getDestination())){
                return cls;
            }
        }
        return null;
    }
}
