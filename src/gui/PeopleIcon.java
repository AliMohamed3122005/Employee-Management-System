package gui;

import javax.swing.Icon;
import java.awt.*;
import java.awt.geom.*;

class PeopleIcon implements Icon
{
    private final int size;
    private final Color color;

    PeopleIcon(int size, Color color)
    {
        this.size = size;
        this.color = color;
    }

    @Override public int getIconWidth()  { return size; }
    @Override public int getIconHeight() { return size; }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        g2.scale(size / 100.0, size / 100.0);     // draw on a 100 x 100 grid, scale to any size
        g2.setColor(color);

        Area sides = person(24, 38, 11, 36, 54, 26);          // left person
        sides.add(person(76, 38, 11, 36, 54, 26));            // right person
        Area middle = person(50, 28, 15, 52, 50, 38);         // centre person, bigger

        // cut a thin gap around the centre person so the three figures stay separate
        sides.subtract(middle);
        sides.subtract(new Area(new BasicStroke(5f).createStrokedShape(middle)));

        g2.fill(sides);
        g2.fill(middle);
        g2.dispose();
    }

    // one person = a circle (head) + the top half of an ellipse (shoulders)
    private static Area person(double centreX, double headY, double headRadius,
                            double bodyWidth, double bodyTop, double bodyHeight)
    {
        Area person = new Area(new Ellipse2D.Double(
                centreX - headRadius, headY - headRadius, headRadius * 2, headRadius * 2));
        person.add(new Area(new Arc2D.Double(
                centreX - bodyWidth / 2, bodyTop, bodyWidth, bodyHeight * 2, 0, 180, Arc2D.CHORD)));
        return person;
    }
}
