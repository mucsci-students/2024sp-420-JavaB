package com.classuml.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.ArrayList;
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
        ArrayList<Point[]> drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below, int pushInt, int line);
        void drawArrowHead(Graphics g, int x1, int y1, boolean flip, boolean pointDown, boolean pointUp);
    }
    class ArrowInheritanceBuidler implements Builder {
        public ArrayList<Point[]> drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below, int pushInt, int line){
            int halfway;
            ArrayList<Point[]> lines = new ArrayList<Point[]>();
            Point[] line1 = new Point[2];
            Point[] line2 = new Point[2];
            Point[] line3 = new Point[2];

            if(toRight){
                halfway = (x1 - x2) / 2;

                if(line == 0){
                    g.drawLine(x1, y1, x1 - halfway, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1 - halfway, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y1, x1 - halfway, y2);
                    line2[0] = new Point(x1 - halfway, y1);
                    line2[1] = new Point(x1 - halfway, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y2, x2 + 10, y2);
                    line3[0] = new Point(x1 - halfway, y2);
                    line3[1] = new Point(x2 + 10, y2);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 + 10, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 + 10, pushInt, x2 + 10, y2);
                    line3[0] = new Point(x2 + 10, pushInt);
                    line3[1] = new Point(x2 + 10, y2);
                }else if(line == 2 ){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y1, pushInt, y2);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y2, x2 + 10, y2);
                    line3[0] = new Point(pushInt, y2);
                    line3[1] = new Point(x2 + 10, y2);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 + 10, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2 + 20, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 + 20, pushInt, x2 + 10, y2);
                    line3[0] = new Point(x2 + 10, pushInt);
                    line3[1] = new Point(x2 + 10, y2);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, true, false, false);
            }else if(above){
                halfway = (y2 - y1) / 2;
                if(line == 0){
                    g.drawLine(x1, y1, x1, y1 + halfway);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, y1 + halfway);
                    g.drawLine(x1, y1 + halfway,x2, y1 + halfway);
                    line2[0] = new Point(x1, y1 + halfway);
                    line2[1] = new Point(x2, y1 + halfway);
                    g.drawLine(x2, y1 + halfway, x2, y2-10);
                    line3[0] = new Point(x2, y1 + halfway);
                    line3[1] = new Point(x2, y2 - 10);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2-10);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 - 10);
                }else if(line == 2){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    g.drawLine(pushInt, y1,pushInt, y2 - 10);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2 - 10);
                    g.drawLine(pushInt, y2 - 10, x2, y2-10);
                    line3[0] = new Point(pushInt, y2 - 10);
                    line3[1] = new Point(x2, y2 - 10);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2-10);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 - 10);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, false, true, false);
            }else if(below){
                halfway = (y1 - y2) / 2;
                if(line == 0){
                    g.drawLine(x1, y1, x1, y1 + halfway);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, y1 + halfway);
                    g.drawLine(x1, y1 + halfway,x2, y1 + halfway);
                    line2[0] = new Point(x1, y1 + halfway);
                    line2[1] = new Point(x2, y1 + halfway);
                    g.drawLine(x2, y1 + halfway, x2, y2+10);
                    line3[0] = new Point(x2, y1 + halfway);
                    line3[1] = new Point(x2, y2 + 10);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2+10);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 + 10);
                }else if(line == 2){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    g.drawLine(pushInt, y1,pushInt, y2 + 10);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2 + 10);
                    g.drawLine(pushInt, y2 + 10, x2, y2+10);
                    line3[0] = new Point(pushInt, y2 + 10);
                    line3[1] = new Point(x2, y2 + 10);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2+10);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 + 10);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, false, false, true);
            }else{
                halfway = (x2 - x1) / 2;
                if(line == 0){
                    g.drawLine(x1, y1, x1 - halfway, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1 - halfway, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y1, x1 - halfway, y2);
                    line2[0] = new Point(x1 - halfway, y1);
                    line2[1] = new Point(x1 - halfway, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y2, x2 - 10, y2);
                    line3[0] = new Point(x1 - halfway, y2);
                    line3[1] = new Point(x2 - 10, y2);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 - 10, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 - 10, pushInt, x2 - 10, y2);
                    line3[0] = new Point(x2 - 10, pushInt);
                    line3[1] = new Point(x2 - 10, y2);
                }else if(line == 2 ){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y1, pushInt, y2);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y2, x2 - 10, y2);
                    line3[0] = new Point(pushInt, y2);
                    line3[1] = new Point(x2 - 10, y2);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 - 10, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2 - 10, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 - 10, pushInt, x2 - 10, y2);
                    line3[0] = new Point(x2 - 10, pushInt);
                    line3[1] = new Point(x2 - 10, y2);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, false, false, false);
            }
            return new ArrayList<Point[]>();
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
        public ArrayList<Point[]> drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below, int pushX, int pushY){
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
            return new ArrayList<Point[]>();
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
        public ArrayList<Point[]> drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below, int pushInt, int line){
            int halfway;
            ArrayList<Point[]> lines = new ArrayList<Point[]>();
            Point[] line1 = new Point[2];
            Point[] line2 = new Point[2];
            Point[] line3 = new Point[2];

            if(toRight){
                halfway = (x1 - x2) / 2;

                if(line == 0){
                    g.drawLine(x1, y1, x1 - halfway, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1 - halfway, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y1, x1 - halfway, y2);
                    line2[0] = new Point(x1 - halfway, y1);
                    line2[1] = new Point(x1 - halfway, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y2, x2 + 20, y2);
                    line3[0] = new Point(x1 - halfway, y2);
                    line3[1] = new Point(x2 + 20, y2);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 + 20, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 + 20, pushInt, x2 + 20, y2);
                    line3[0] = new Point(x2 + 20, pushInt);
                    line3[1] = new Point(x2 + 20, y2);
                }else if(line == 2 ){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y1, pushInt, y2);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y2, x2 + 20, y2);
                    line3[0] = new Point(pushInt, y2);
                    line3[1] = new Point(x2 + 20, y2);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 + 20, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2 + 20, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 + 20, pushInt, x2 + 20, y2);
                    line3[0] = new Point(x2 + 20, pushInt);
                    line3[1] = new Point(x2 + 20, y2);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, true, false, false);
            }else if(above){
                halfway = (y2 - y1) / 2;
                if(line == 0){
                    g.drawLine(x1, y1, x1, y1 + halfway);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, y1 + halfway);
                    g.drawLine(x1, y1 + halfway,x2, y1 + halfway);
                    line2[0] = new Point(x1, y1 + halfway);
                    line2[1] = new Point(x2, y1 + halfway);
                    g.drawLine(x2, y1 + halfway, x2, y2-20);
                    line3[0] = new Point(x2, y1 + halfway);
                    line3[1] = new Point(x2, y2 - 20);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2-20);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 - 20);
                }else if(line == 2){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    g.drawLine(pushInt, y1,pushInt, y2 - 20);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2 - 20);
                    g.drawLine(pushInt, y2 - 20, x2, y2-20);
                    line3[0] = new Point(pushInt, y2 - 20);
                    line3[1] = new Point(x2, y2 - 20);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2-20);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 - 20);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, false, true, false);
            }else if(below){
                halfway = (y1 - y2) / 2;
                if(line == 0){
                    g.drawLine(x1, y1, x1, y1 + halfway);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, y1 + halfway);
                    g.drawLine(x1, y1 + halfway,x2, y1 + halfway);
                    line2[0] = new Point(x1, y1 + halfway);
                    line2[1] = new Point(x2, y1 + halfway);
                    g.drawLine(x2, y1 + halfway, x2, y2+20);
                    line3[0] = new Point(x2, y1 + halfway);
                    line3[1] = new Point(x2, y2 + 20);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2+20);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 + 20);
                }else if(line == 2){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    g.drawLine(pushInt, y1,pushInt, y2 + 20);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2 + 20);
                    g.drawLine(pushInt, y2 + 20, x2, y2+20);
                    line3[0] = new Point(pushInt, y2 + 20);
                    line3[1] = new Point(x2, y2 + 20);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2+20);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 + 20);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, false, false, true);
            }else{
                halfway = (x2 - x1) / 2;
                if(line == 0){
                    g.drawLine(x1, y1, x1 - halfway, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1 - halfway, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y1, x1 - halfway, y2);
                    line2[0] = new Point(x1 - halfway, y1);
                    line2[1] = new Point(x1 - halfway, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y2, x2 - 20, y2);
                    line3[0] = new Point(x1 - halfway, y2);
                    line3[1] = new Point(x2 - 20, y2);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 - 20, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 - 20, pushInt, x2 - 20, y2);
                    line3[0] = new Point(x2 - 20, pushInt);
                    line3[1] = new Point(x2 - 20, y2);
                }else if(line == 2 ){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y1, pushInt, y2);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y2, x2 - 20, y2);
                    line3[0] = new Point(pushInt, y2);
                    line3[1] = new Point(x2 - 20, y2);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 - 20, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2 - 20, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 - 20, pushInt, x2 - 20, y2);
                    line3[0] = new Point(x2 - 20, pushInt);
                    line3[1] = new Point(x2 - 20, y2);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, false, false, false);
            }
                

            
            return lines;
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
        public ArrayList<Point[]> drawArrowBody(Graphics g, int x1, int y1, int x2, int y2, boolean toRight, boolean above, boolean below, int pushInt, int line){
            g.setColor(Color.black);
            int halfway;
            ArrayList<Point[]> lines = new ArrayList<Point[]>();
            Point[] line1 = new Point[2];
            Point[] line2 = new Point[2];
            Point[] line3 = new Point[2];
            if(toRight){
                halfway = (x1 - x2) / 2;

                if(line == 0){
                    g.drawLine(x1, y1, x1 - halfway, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1 - halfway, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y1, x1 - halfway, y2);
                    line2[0] = new Point(x1 - halfway, y1);
                    line2[1] = new Point(x1 - halfway, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 - halfway, y2, x2 + 20, y2);
                    line3[0] = new Point(x1 - halfway, y2);
                    line3[1] = new Point(x2 + 20, y2);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 + 20, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 + 20, pushInt, x2 + 20, y2);
                    line3[0] = new Point(x2 + 20, pushInt);
                    line3[1] = new Point(x2 + 20, y2);
                }else if(line == 2 ){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y1, pushInt, y2);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y2, x2 + 20, y2);
                    line3[0] = new Point(pushInt, y2);
                    line3[1] = new Point(x2 + 20, y2);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 + 20, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2 + 20, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 + 20, pushInt, x2 + 20, y2);
                    line3[0] = new Point(x2 + 20, pushInt);
                    line3[1] = new Point(x2 + 20, y2);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, true, false, false);
            }else if(above){
                halfway = (y2 - y1) / 2;
                if(line == 0){
                    g.drawLine(x1, y1, x1, y1 + halfway);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, y1 + halfway);
                    g.drawLine(x1, y1 + halfway,x2, y1 + halfway);
                    line2[0] = new Point(x1, y1 + halfway);
                    line2[1] = new Point(x2, y1 + halfway);
                    g.drawLine(x2, y1 + halfway, x2, y2-20);
                    line3[0] = new Point(x2, y1 + halfway);
                    line3[1] = new Point(x2, y2 - 20);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2-20);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 - 20);
                }else if(line == 2){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    g.drawLine(pushInt, y1,pushInt, y2 - 20);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2 - 20);
                    g.drawLine(pushInt, y2 - 20, x2, y2-20);
                    line3[0] = new Point(pushInt, y2 - 20);
                    line3[1] = new Point(x2, y2 - 20);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2-20);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 - 20);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, false, true, false);
            }else if(below){
                halfway = (y1 - y2) / 2;
                if(line == 0){
                    g.drawLine(x1, y1, x1, y1 - halfway);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, y1 - halfway);
                    g.drawLine(x1, y1 - halfway,x2, y1 - halfway);
                    line2[0] = new Point(x1, y1 - halfway);
                    line2[1] = new Point(x2, y1 - halfway);
                    g.drawLine(x2, y1 - halfway, x2, y2+20);
                    line3[0] = new Point(x2, y1 - halfway);
                    line3[1] = new Point(x2, y2 + 20);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2+20);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 + 20);
                }else if(line == 2){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    g.drawLine(pushInt, y1,pushInt, y2 + 20);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2 + 20);
                    g.drawLine(pushInt, y2 + 20, x2, y2+20);
                    line3[0] = new Point(pushInt, y2 + 20);
                    line3[1] = new Point(x2, y2 + 20);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    g.drawLine(x1, pushInt,x2, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2, pushInt);
                    g.drawLine(x2, pushInt, x2, y2+20);
                    line3[0] = new Point(x2, pushInt);
                    line3[1] = new Point(x2, y2 + 20);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, false, false, true);
            }else{
                halfway = (x2 - x1) / 2;
                if(line == 0){
                    g.drawLine(x1, y1, x1 + halfway, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1 + halfway, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 + halfway, y1, x1 + halfway, y2);
                    line2[0] = new Point(x1 + halfway, y1);
                    line2[1] = new Point(x1 + halfway, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(x1 + halfway, y2, x2 - 20, y2);
                    line3[0] = new Point(x1 + halfway, y2);
                    line3[1] = new Point(x2 - 20, y2);
                }else if(line == 1){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 - 20, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2 - 20, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 - 20, pushInt, x2 - 20, y2);
                    line3[0] = new Point(x2 - 20, pushInt);
                    line3[1] = new Point(x2 - 20, y2);
                }else if(line == 2 ){
                    g.drawLine(x1, y1, pushInt, y1);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(pushInt, y1);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y1, pushInt, y2);
                    line2[0] = new Point(pushInt, y1);
                    line2[1] = new Point(pushInt, y2);
                    //check the line to see if needs rerouting
                    g.drawLine(pushInt, y2, x2 - 20, y2);
                    line3[0] = new Point(pushInt, y2);
                    line3[1] = new Point(x2 - 20, y2);
                }else if (line == 3){
                    g.drawLine(x1, y1, x1, pushInt);
                    line1[0] = new Point(x1, y1);
                    line1[1] = new Point(x1, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x1, pushInt, x2 - 20, pushInt);
                    line2[0] = new Point(x1, pushInt);
                    line2[1] = new Point(x2 - 20, pushInt);
                    //check the line to see if needs rerouting
                    g.drawLine(x2 - 20, pushInt, x2 - 20, y2);
                    line3[0] = new Point(x2 - 20, pushInt);
                    line3[1] = new Point(x2 - 20, y2);
                }
                

                lines.add(line1); lines.add(line2); lines.add(line3);
                drawArrowHead(g, x2, y2, false, false, false);
            }
            return lines;
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
                        if(rel.drawnLines.size() > 0){
                            
                                int checking1 = checkArrows(rel.drawnLines.get(0), getSourceFromRelationship(rel2), getDestinationFromRelationship(rel2), g);
                                int checking2 = checkArrows(rel.drawnLines.get(1), getSourceFromRelationship(rel2), getDestinationFromRelationship(rel2), g);
                                int checking3 = checkArrows(rel.drawnLines.get(2), getSourceFromRelationship(rel2), getDestinationFromRelationship(rel2), g);
                                if((checking1 == 2 || checking1 == 4)){
                                    rel.rerouted = true;
                                    drawRerout(rel, getSourceFromRelationship(rel2), checking1, 1, g);
                                    break;
                                }else if((checking2 == 1 || checking2 == 3)){
                                    rel.rerouted = true;
                                    drawRerout(rel, getSourceFromRelationship(rel2), checking2, 2, g);
                                    break;
                                }else if((checking3 == 2 || checking3 == 4)){
                                    rel.rerouted = true;
                                    drawRerout(rel, getSourceFromRelationship(rel2), checking3, 3, g);
                                    break;
                                }
                                

                                checking1 = checkArrows(rel.drawnLines.get(0), getDestinationFromRelationship(rel2), getSourceFromRelationship(rel2), g);
                                checking2 = checkArrows(rel.drawnLines.get(1), getDestinationFromRelationship(rel2), getSourceFromRelationship(rel2), g);
                                checking3 = checkArrows(rel.drawnLines.get(2), getDestinationFromRelationship(rel2), getSourceFromRelationship(rel2), g);
                                if((checking1 == 2 || checking1 == 4)){
                                    drawRerout(rel, getDestinationFromRelationship(rel2), checking1, 1, g);
                                    rel.rerouted = true;
                                    break;
                                }else if((checking2 == 1 || checking2 == 3)){
                                    rel.rerouted = true;
                                    drawRerout(rel, getDestinationFromRelationship(rel2), checking2, 2, g);
                                    break;
                                }else if(( checking3 == 2 || checking3 == 4)){
                                    rel.rerouted = true;
                                    drawRerout(rel, getDestinationFromRelationship(rel2), checking3, 3, g);
                                    break;
                                }else{
                                    rel.rerouted = false;
                                }
                            
                        }   
                    }
                    
                    
                }
                if(rel.rerouted == true){
                        continue;
                }



                
                if(rel.getType() == 1){
                    ArrowAggregationBuidler aggArrow = new ArrowAggregationBuidler();
                    rel.drawnLines = drawType(rel, aggArrow, g, 0, 0);
                }
                if(rel.getType() == 2){
                    ArrowCompositionBuidler compArrow = new ArrowCompositionBuidler();
                    rel.drawnLines = drawType(rel, compArrow, g, 0, 0);
                }
                if(rel.getType() == 3){
                    ArrowInheritanceBuidler inArrow = new ArrowInheritanceBuidler();
                    rel.drawnLines = drawType(rel, inArrow, g, 0, 0);
                }
                if(rel.getType() == 4){
                    ArrowRealizationBuidler relArrow = new ArrowRealizationBuidler();
                    rel.drawnLines = drawType(rel, relArrow, g, 0, 0);
                }
                
            }
                
            
        }
    }


    /*
     * Draws the specific type of arrow for the relationship
     */
    public ArrayList<Point[]> drawType(Relationship rel, Builder arrow, Graphics g, int pushX, int pushY){
        UMLClass c1 = getSourceFromRelationship(rel);
        
        UMLClass c2 = getDestinationFromRelationship(rel);
        ArrayList<Point[]> lines = new ArrayList<Point[]>();

        
            if(c1.position.x + c1.uniformWidth + 30 < c2.position.x){
                lines = arrow.drawArrowBody(g, c1.position.x + c1.uniformWidth, c1.position.y + (c1.totalHeight/2), c2.position.x, c2.position.y + (c2.totalHeight/2)
                                        ,false, false, false, pushX, pushY);
            }else if (c1.position.x > c2.position.x + c2.uniformWidth + 30){
                lines = arrow.drawArrowBody(g, c1.position.x, c1.position.y + (c1.totalHeight/2), c2.position.x + c2.uniformWidth, c2.position.y + (c2.totalHeight/2)
                                        ,true , false, false, pushX, pushY);
            }else if (c1.position.y + c1.totalHeight < c2.position.y){
                lines = arrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y + c1.totalHeight, c2.position.x + (c2.uniformWidth/2), c2.position.y
                                        ,false, true, false, pushX, pushY);
            }else if (c1.position.y > c2.position.y + c2.totalHeight){
                lines = arrow.drawArrowBody(g, c1.position.x + (c1.uniformWidth/2), c1.position.y, c2.position.x + (c2.uniformWidth/2), c2.position.y + c2.totalHeight
                                        ,false, false, true, pushX, pushY);
            }else{

            }
        return lines;
    }


    /*
     * Is used for detecting if arrows are crossing each other,
     * will rerout one arrow if paths are crossing
     */
    public int checkArrows(Point[] lines, UMLClass r2C, UMLClass r2C2, Graphics g){
        //mids are the middle points of the GUI class boxes

        
            
        if(lines[0].x >= r2C.position.x && lines[1].x >= r2C.position.x &&
            lines[0].x <= r2C.position.x + r2C.uniformWidth && lines[1].x <= r2C.position.x + r2C.uniformWidth &&
            ((lines[0].y <= r2C.position.y && lines[1].y >= r2C.position.y + r2C.totalHeight) ||
            (lines[1].y <= r2C.position.y && lines[0].y >= r2C.position.y + r2C.totalHeight))){
                if(r2C.position.x >= r2C2.position.x){
                    return 1;
                }else{
                    return 3;
                }
                
        }else if(((lines[0].x <= r2C.position.x && lines[1].x >= r2C.position.x + r2C.uniformWidth) ||
                    (lines[1].x <= r2C.position.x && lines[0].x >= r2C.position.x + r2C.uniformWidth)) &&
                    lines[0].y >= r2C.position.y && lines[1].y >= r2C.position.y &&
                    lines[0].y <= r2C.position.y + r2C.totalHeight && lines[1].y <= r2C.position.y + r2C.totalHeight){
                        if(r2C.position.y >= r2C2.position.y){
                            return 2;
                        }else{
                            return 4;
                        }
        }else{
            return 0;
        }
    }

    public Point drawRerout(Relationship relate,UMLClass r2C,int push,int line, Graphics g){
        
        int pushInt = 0;

        if(push == 1){
            pushInt = r2C.position.x + r2C.uniformWidth + 50;
        }else if(push == 2){
            pushInt = r2C.position.y + r2C.totalHeight + 50;
        }else if(push == 3){
            pushInt = r2C.position.x - 50;
        }else if(push == 4){
            pushInt = r2C.position.y - 50;
        }

        

        if(relate.getType() == 1){
            ArrowAggregationBuidler aggArrow = new ArrowAggregationBuidler();
            drawType(relate, aggArrow, g, pushInt, line);
        }
        if(relate.getType() == 2){
            ArrowCompositionBuidler compArrow = new ArrowCompositionBuidler();
            drawType(relate, compArrow, g, pushInt, line);
        }
        if(relate.getType() == 3){
            ArrowInheritanceBuidler inArrow = new ArrowInheritanceBuidler();
            drawType(relate, inArrow, g, pushInt, line);
        }
        if(relate.getType() == 4){
            ArrowRealizationBuidler relArrow = new ArrowRealizationBuidler();
            drawType(relate, relArrow, g, pushInt, line);
        }

        return new Point();
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
        localY = drawItem(g, c.getName(), localX, localY, true, c);
    
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
        int maxWidth = c.fm.stringWidth(c.getName());
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
