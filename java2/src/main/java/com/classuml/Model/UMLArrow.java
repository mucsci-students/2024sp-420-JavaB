package com.classuml.Model;
// https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
//import java.awt.geom.AffineTransform;
//import java.awt.geom.Line2D;
//import java.awt.Polygon;
//import java.awt.Graphics2D;
//import java.awt.Graphics;
//import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class UMLArrow {
    public Point position;
    public int XPos;
    public int YPos;
    public int width;
    public int height;
    AffineTransform tx;
    Line2D.Double line;
    Polygon arrowHead;

    public UMLArrow() {
        if (this.position == null){
			this.position = new Point();
		}
        XPos = -1;
        YPos = -1;
        width = 0;
        height = 0;
        // create an AffineTransform 
        // and a triangle centered on (0,0) and pointing downward
        // somewhere outside Swing's paint loop
        tx = new AffineTransform();
        line = new Line2D.Double(0,0,100,100);
        arrowHead = new Polygon();
    }
    
    public void testAddArrowHeadPoints() {
        addArrowHeadPoint( 0,5);
        addArrowHeadPoint( -5, -5);
        addArrowHeadPoint( 5,-5);
    }
    public void addArrowHeadPoint(int x, int y) {
        arrowHead.addPoint(x, y);
    }
    
    public void drawArrowHead(Graphics2D g2d) {  
        tx.setToIdentity();
        double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
        tx.translate(line.x2, line.y2);
        tx.rotate((angle-Math.PI/2d));  

        Graphics2D g = (Graphics2D) g2d.create();
        g.setTransform(tx);   
        g.fill(arrowHead);
        g.dispose();
    }

    public int getXPos() {
        return XPos;
    }

    public int getYPos() {
        return YPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Draw an arrow line between two points.
     * @param g the graphics component.
     * @param x1 x-position of first point.
     * @param y1 y-position of first point.
     * @param x2 x-position of second point.
     * @param y2 y-position of second point.
     * @param d  the width of the arrow.
     * @param h  the height of the arrow.
     */
    public void drawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }
}

/*public class BevelArrows
{
    public static void main ( String...args )
    {
        SwingUtilities.invokeLater ( new Runnable () {
            BevelArrows arrows = new BevelArrows();

            @Override
            public void run () {
                JFrame frame = new JFrame ( "Bevel Arrows" );

                frame.add ( new JPanel() {
                    public void paintComponent ( Graphics g ) {
                        arrows.draw ( ( Graphics2D ) g, getWidth(), getHeight() );
                    }
                }
                , BorderLayout.CENTER );

                frame.setSize ( 800, 400 );
                frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
                frame.setVisible ( true );
            }
        } );
    }

    interface Arrow {
        void draw ( Graphics2D g );
    }

    Arrow[] arrows = { new LineArrow()};

    void draw ( Graphics2D g, int width, int height )
    {
        g.setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        g.setColor ( Color.WHITE );
        g.fillRect ( 0, 0, width, height );

        for ( Arrow arrow : arrows ) {
            g.setColor ( Color.ORANGE );
            g.fillRect ( 350, 20, 20, 280 );

            g.setStroke ( new BasicStroke ( 20.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL ) );
            g.translate ( 0, 60 );
            arrow.draw ( g );

            g.setStroke ( new BasicStroke ( 20.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER ) );
            g.translate ( 0, 100 );
            arrow.draw ( g );

            g.setStroke ( new BasicStroke ( 20.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND ) );
            g.translate ( 0, 100 );
            arrow.draw ( g );

            g.translate ( 400, -260 );
        }
    }

    static class LineArrow  implements Arrow
    {
        public void draw ( Graphics2D g )
        {
            // where the control point for the intersection of the V needs calculating
            // by projecting where the ends meet

            float arrowRatio = 0.5f;
            float arrowLength = 80.0f;

            BasicStroke stroke = ( BasicStroke ) g.getStroke();

            float endX = 350.0f;

            float veeX;

            switch ( stroke.getLineJoin() ) {
                case BasicStroke.JOIN_BEVEL:
                    // IIRC, bevel varies system to system, this is approximate
                    veeX = endX - stroke.getLineWidth() * 0.25f;
                    break;
                default:
                case BasicStroke.JOIN_MITER:
                    veeX = endX - stroke.getLineWidth() * 0.5f / arrowRatio;
                    break;
                case BasicStroke.JOIN_ROUND:
                    veeX = endX - stroke.getLineWidth() * 0.5f;
                    break;
            }

            // vee
            Path2D.Float path = new Path2D.Float();

            path.moveTo ( veeX - arrowLength, -arrowRatio*arrowLength );
            path.lineTo ( veeX, 0.0f );
            path.lineTo ( veeX - arrowLength, arrowRatio*arrowLength );

            g.setColor ( Color.BLUE );
            g.draw ( path );

            // stem for exposition only
            g.setColor ( Color.YELLOW );
            g.draw ( new Line2D.Float ( 50.0f, 0.0f, veeX, 0.0f ) );

            // in practice, move stem back a bit as rounding errors
            // can make it poke through the sides of the Vee
            g.setColor ( Color.RED );
            g.draw ( new Line2D.Float ( 50.0f, 0.0f, veeX - stroke.getLineWidth() * 0.25f, 0.0f ) );
        }
    }
    // There was another example in stack overflow on curved arrows, but that is completely unnecessary.
}*/