
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class Sketch extends Component implements MouseListener, MouseMotionListener {

    int sX = -1, sY = -1;
    static Label stat;
    Image bImage;
    boolean dragging = false;
    int curX = -1, curY = -1;

    public static void main(String[] av) {

        JFrame jFrame = new JFrame("Mouse Dragger");

        Container cPane = jFrame.getContentPane();

        Image im = Toolkit.getDefaultToolkit().getImage("C:/Users/nikos7/Desktop/pic.jpg");

        Sketch sk = new Sketch(im);

        cPane.setLayout(new BorderLayout());

        cPane.add(BorderLayout.NORTH, new Label(""));

        cPane.add(BorderLayout.CENTER, sk);

        cPane.add(BorderLayout.SOUTH, stat = new Label());

        stat.setSize(jFrame.getSize().width, stat.getSize().height);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.pack();

        jFrame.setVisible(true);

    }

    public Sketch(Image i) {

        super();

        bImage = i;

        setSize(300, 200);

        addMouseListener(this);

        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {

        Point point = event.getPoint();

        sX = point.x;

        sY = point.y;

        dragging = true;
    }

    @Override
    public void mouseReleased(MouseEvent event) {

        dragging = false;

        Point p = event.getPoint();

        System.out.println("MOUSE MOVE IS " + sX + "," + sY + " to "

                + p.x + "," + p.y);
    }

    @Override
    public void mouseDragged(MouseEvent event) {

        Point p = event.getPoint();

        // System.err.println("mouse drag to " + p);

//        showStatus("mouse Dragged to " + p);

        curX = p.x;

        curY = p.y;

        if (dragging) {

            repaint();

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

//        showStatus("Mouse to " + e.getPoint());
    }
}