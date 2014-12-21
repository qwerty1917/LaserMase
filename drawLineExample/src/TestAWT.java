
import java.awt.Component;
import java.awt.Graphics;

public class TestAWT extends Component {

    /** @see java.awt.Component#paint(java.awt.Graphics) */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0,0,100,100);
        g.drawLine(10, 10, 20, 300);
        // more drawing code here...
    }



}