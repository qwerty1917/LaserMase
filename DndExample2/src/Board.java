
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.accessibility.Accessible;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class Board extends JPanel {

    ArrayList<DTPicture> tokenImages = new ArrayList<DTPicture>();
    int tokenCount = 6;
    int roomCount = 25;
    String tokenNameList[] = {"TokenBlack.png", "TokenBlue.png", "TokenGreen.png", "TokenPurple.png", "TokenRed.png", "TokenYellow.png"};

    PictureTransferHandler picHandler;

    public Board() {
        super(new BorderLayout());
        picHandler = new PictureTransferHandler();
        JPanel mugshots = new JPanel(new GridLayout(5, 5));

        for (int i = 0; i < tokenCount; i++) {
            tokenImages.add(new DTPicture(createImageIcon("images/" + tokenNameList[i], tokenNameList[i]).getImage()));
            tokenImages.get(i).setTransferHandler(picHandler);
            mugshots.add(tokenImages.get(i));
        }

        //These six components with no pictures provide handy
        //drop targets.

        for (int i = tokenCount; i < roomCount; i++) {
            tokenImages.add(new DTPicture(null));
            tokenImages.get(i).setTransferHandler(picHandler);
            mugshots.add(tokenImages.get(i));
        }

        setPreferredSize(new Dimension(500, 500));
        add(mugshots, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imageURL = Board.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return new ImageIcon(imageURL, description);
        }
    }

    /**
     * Create the GUI and show it.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
//        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("game board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the menu bar and content pane.
        Board demo = new Board();
//        demo.setOpaque(true); //content panes must be opaque
        frame.setContentPane(demo);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        createAndShowGUI();
    }
}

/*
 * DTPicture.java is used by the 1.4 DragPictureDemo.java example.
 */

//A subclass of Picture that supports Data Transfer.

class DTPicture extends Picture implements MouseMotionListener {
    private MouseEvent firstMouseEvent = null;

    public DTPicture(Image image) {
        super(image);
        addMouseMotionListener(this);
    }

    public void setImage(Image image) {
        this.image = image;
        this.repaint();
    }

    public void mouseClicked(MouseEvent e){
        if(e.getClickCount()==2){
            //input your code
            this.image = rotate(this.image);
            this.repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
        //Don't bother to drag if there is no image.
        if (image == null)
            return;

        firstMouseEvent = e;
        e.consume();
    }

    public void mouseDragged(MouseEvent e) {
        //Don't bother to drag if the component displays no image.
        if (image == null)
            return;

        if (firstMouseEvent != null) {
            e.consume();

            JComponent c = (JComponent) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            //Tell the transfer handler to initiate the drag.
            handler.exportAsDrag(c, firstMouseEvent, TransferHandler.MOVE);
            firstMouseEvent = null;
        }
    }

    public void mouseReleased(MouseEvent e) {
        firstMouseEvent = null;
    }

    public void mouseMoved(MouseEvent e) {}

    public static Image rotate(Image img)
    {
        BufferedImage oldImage = imageToBufImage(img);

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(90), oldImage.getWidth() / 2, oldImage.getHeight() / 2);

        AffineTransformOp opRotated = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage newImage = opRotated.filter(oldImage, null);

        return bufImageToImage(newImage);
    }

    public static BufferedImage imageToBufImage(Image img){
        if(img instanceof BufferedImage){
            return (BufferedImage)img;
        }
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D tmp = bufferedImage.createGraphics();
        tmp.drawImage(img, 0, 0, null);
        tmp.dispose();

        return bufferedImage;
    }

    public static Image bufImageToImage(BufferedImage buffImg){
        return Toolkit.getDefaultToolkit().createImage(buffImg.getSource());
    }
}

class Picture extends JComponent implements MouseListener, Accessible {
    Image image;
//    Image newImage = new Image(image.getHeight(), image.getWidth(), image.getType());

    public Picture(Image image) {

        this.image = image;
        addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    protected void paintComponent(Graphics graphics) {
        Graphics g = graphics.create();

        //Draw in our entire space, even if isOpaque is false.
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image == null ? 100 : image.getWidth(this),
                image == null ? 100 : image.getHeight(this));

        if (image != null) {
            //Draw image at its natural size of 125x125.
            g.drawImage(image, 0, 0, this);
        }
    }
}

/*
 * PictureTransferHandler.java is used by the 1.4 DragPictureDemo.java example.
 */

class PictureTransferHandler extends TransferHandler {
    DataFlavor pictureFlavor = DataFlavor.imageFlavor;

    DTPicture sourcePic;

    boolean shouldRemove;

    public boolean importData(JComponent c, Transferable t) {
        Image image;
        if (canImport(c, t.getTransferDataFlavors())) {
            DTPicture pic = (DTPicture) c;
            //Don't drop on myself.
            if (sourcePic == pic) {
                shouldRemove = false;
                return true;
            }
            try {
                image = (Image) t.getTransferData(pictureFlavor);
                //Set the component to the new picture.
                pic.setImage(image);
                return true;
            } catch (UnsupportedFlavorException ufe) {
                System.out.println("importData: unsupported data flavor");
            } catch (IOException ioe) {
                System.out.println("importData: I/O exception");
            }
        }
        return false;
    }

    protected Transferable createTransferable(JComponent c) {
        sourcePic = (DTPicture) c;
        shouldRemove = true;
        return new PictureTransferable(sourcePic);
    }

    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    protected void exportDone(JComponent c, Transferable data, int action) {
        if (shouldRemove && (action == MOVE)) {
            sourcePic.setImage(null);
        }
        sourcePic = null;
    }

    public boolean canImport(JComponent c, DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (pictureFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }

    class PictureTransferable implements Transferable {
        private Image image;

        PictureTransferable(DTPicture pic) {
            image = pic.image;
        }

        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return image;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{pictureFlavor};
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return pictureFlavor.equals(flavor);
        }
    }
}