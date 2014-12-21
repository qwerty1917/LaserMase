import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Created by hyeongminpark on 14. 12. 18..
 */
public class main {

    JFrame frame;

    public static void main(String[] args){
        main gui = new main();
        gui.go();
    }

    public void go(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyDrawPanel drawPanel = new MyDrawPanel();
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(300,300);
        frame.setVisible(true);

    }
}

class MyDrawPanel extends JPanel{
    public void paintComponent(Graphics g){
//        Image image = new ImageIcon("./src/TokenYellow.png").getImage();
        try {
            Image image = ImageIO.read(this.getClass().getResource("TokenYellow.png"));
            g.drawImage(image, 3, 4, this);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}