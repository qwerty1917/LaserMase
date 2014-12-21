package Sources;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.MouseInfo;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.accessibility.Accessible;
import javax.swing.*;

/**
 * Created by hyeongminpark on 14. 12. 18..
 */
public class Board extends JPanel{
    int cardNum = 50;
    int startX, startY, endX, endY;

    ArrayList<DTPicture> fixedTokenImages = new ArrayList<DTPicture>();
    ArrayList<DTPicture> freeTokenImages = new ArrayList<DTPicture>();

    PictureTransferHandler picHandler;

//    String cardFileDir = "./src/Cards/Card" + cardNum + ".txt";
//    Card card = new Card(cardFileDir);

    public void go(){

        showGUI();
    }

    public Board() {
        super(new BorderLayout());

        //어느 카드 데이터를 가져올 것인가 정
        String cardFileDir = "./src/Cards/Card" + cardNum + ".txt";
        Card card = new Card(cardFileDir);
        card.plotBoard();

        picHandler = new PictureTransferHandler();
        JPanel imageLine = new JPanel(new GridLayout(1, 5));
        imageLine.setPreferredSize(new Dimension(500, 100));
        JPanel imageTable = new JPanel(new GridLayout(5, 5));
        imageTable.setPreferredSize(new Dimension(500, 500));

        for(int i=0; i<card.getFreeTokens().size(); i++){
            if(card.getFreeTokens().get(i).getColor().equals("k")){
                freeTokenImages.add(new DTPicture(createImageIcon("../images/TokenBlack.png", "TokenBlack.png").getImage(), card.getFreeTokens().get(i)));
            }else if(card.getFreeTokens().get(i).getColor().equals("b")){
                freeTokenImages.add(new DTPicture(createImageIcon("../images/TokenBlue.png", "TokenBlue.png").getImage(), card.getFreeTokens().get(i)));
            }else if(card.getFreeTokens().get(i).getColor().equals("g")){
                freeTokenImages.add(new DTPicture(createImageIcon("../images/TokenGreen.png", "TokenGreen.png").getImage(), card.getFreeTokens().get(i)));
            }else if(card.getFreeTokens().get(i).getColor().equals("p")){
                freeTokenImages.add(new DTPicture(createImageIcon("../images/TokenPurple.png", "TokenPurple.png").getImage(), card.getFreeTokens().get(i)));
            }else if(card.getFreeTokens().get(i).getColor().equals("r")){
                freeTokenImages.add(new DTPicture(createImageIcon("../images/TokenRed.png", "TokenRed.png").getImage(), card.getFreeTokens().get(i)));
            }else if(card.getFreeTokens().get(i).getColor().equals("y")){
                freeTokenImages.add(new DTPicture(createImageIcon("../images/TokenYellow.png", "TokenYellow.png").getImage(), card.getFreeTokens().get(i)));
            }else{
                System.out.println("invalid color key inputted: " + card.getFreeTokens().get(i).getColor());
            }
            freeTokenImages.get(freeTokenImages.size() - 1).setTransferHandler(picHandler);
            imageLine.add(freeTokenImages.get(freeTokenImages.size() - 1));
        }

        for(int i=card.getFreeTokens().size(); i<5; i++){
            freeTokenImages.add(new DTPicture(null, null));
            freeTokenImages.get(freeTokenImages.size() - 1).setTransferHandler(picHandler);
            imageLine.add(freeTokenImages.get(freeTokenImages.size() - 1));
        }

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(card.getTokenTable()[i][j] != null){
                    if(card.getTokenTable()[i][j].getColor().equals("k")){
                        fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenBlack.png", "TokenBlack.png").getImage(), card.getTokenTable()[i][j]));
                    }else if(card.getTokenTable()[i][j].getColor().equals("b")){
                        fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenBlue.png", "TokenBlue.png").getImage(), card.getTokenTable()[i][j]));
                    }else if(card.getTokenTable()[i][j].getColor().equals("g")){
                        fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenGreen.png", "TokenGreen.png").getImage(), card.getTokenTable()[i][j]));
                    }else if(card.getTokenTable()[i][j].getColor().equals("p")){
                        fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenPurple.png", "TokenPurple.png").getImage(), card.getTokenTable()[i][j]));
                    }else if(card.getTokenTable()[i][j].getColor().equals("r")){
                        fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenRed.png", "TokenRed.png").getImage(), card.getTokenTable()[i][j]));
                    }else if(card.getTokenTable()[i][j].getColor().equals("y")){
                        fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenYellow.png", "TokenYellow.png").getImage(), card.getTokenTable()[i][j]));
                    }else{
                        System.out.println("invalid color key inputted: " + card.getTokenTable()[i][j].getColor());
                    }
//            freeTokenImages.add(new DTPicture(createImageIcon("../images/" + tokenNameList[i], tokenNameList[i]).getImage()));
                    fixedTokenImages.get(fixedTokenImages.size() - 1).setTransferHandler(picHandler);
                    imageTable.add(fixedTokenImages.get(fixedTokenImages.size() - 1));
                }else{
                    fixedTokenImages.add(new DTPicture(null, null));
                    fixedTokenImages.get(fixedTokenImages.size() - 1).setTransferHandler(picHandler);
                    imageTable.add(fixedTokenImages.get(fixedTokenImages.size() - 1));
                }
            }
        }


        setPreferredSize(new Dimension(540, 640));
        add(imageLine, BorderLayout.NORTH);
        add(imageTable, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
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
    private void showGUI() {
        //Make sure we have nice window decorations.
//        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("game board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the menu bar and content pane.
        Board demo = this;
//        demo.setOpaque(true); //content panes must be opaque
        frame.setContentPane(demo);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}

class DTPicture extends Picture implements MouseMotionListener {
    int startX, startY, endX, endY;

    private MouseEvent firstMouseEvent = null;
    Token token;

    public DTPicture(Image image, Token tokenRef) {
        super(image);
        addMouseMotionListener(this);
        token = tokenRef;
    }

    public void setImage(Image image) {
        this.image = image;
        this.repaint();
    }

    public void setToken(Token token) {
        this.token = token;
        this.repaint();
    }

    public void mouseClicked(MouseEvent e){
        if(e.getClickCount()==2){
            if(token.getRotateAble()){
                token.rotate();
                this.image = rotate(this.image);
                repaint();
            }else{
                System.out.println("this token set unrotatable");
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        //Don't bother to drag if there is no image.
        if (image == null)
            return;

        firstMouseEvent = e;

        Point startPos = MouseInfo.getPointerInfo().getLocation();
        startX = startPos.x;
        startY = startPos.y;

        e.consume();
    }

    public void mouseDragged(MouseEvent e) {
        //Don't bother to drag if the component displays no image.
        if (image == null)
            return;

        if (firstMouseEvent != null) {

//            e.consume();
//            if(token.getMoveAble() == true){
//
//                JComponent c = (JComponent) e.getSource();
//                TransferHandler handler = c.getTransferHandler();
//                //Tell the transfer handler to initiate the drag.
//                handler.exportAsDrag(c, firstMouseEvent, TransferHandler.MOVE);
//                firstMouseEvent = null;
//            }else{
//                System.out.println("this token set unmovable");
//                firstMouseEvent = null;
//
//                return;
//            }

            e.consume();

            JComponent c = (JComponent) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            //Tell the transfer handler to initiate the drag.
            handler.exportAsDrag(c, firstMouseEvent, TransferHandler.MOVE);
            firstMouseEvent = null;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(token.getMoveAble()){
        }else{
        }

        Point endPos = MouseInfo.getPointerInfo().getLocation();
        endX = endPos.x;
        endY = endPos.y;
//        printMouseMove();

        firstMouseEvent = null;
        e.consume();
    }

    public void printMouseMove(){
        System.out.println("x moved: " + startX + " " +endX);
        System.out.println("y moved: " + startY + " " +endY);
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

    protected int startX, startY, endX, endY;

    public Picture(Image image) {

        this.image = image;
        addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {
        Point startPos = MouseInfo.getPointerInfo().getLocation();
        startX = startPos.x;
        startY = startPos.y;
    }
    public void mouseReleased(MouseEvent e) {
        Point endPos = MouseInfo.getPointerInfo().getLocation();
        endX = endPos.x;
        endY = endPos.y;
        printMouseMove();
    }
    public void printMouseMove(){
        System.out.println("x moved: " + startX + " " +endX);
        System.out.println("y moved: " + startY + " " +endY);
    }

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
    DataFlavor tokenFlavor = new DataFlavor(Token.class, "Token");
    DataFlavor dtpicFlavor = new DataFlavor(DTPicture.class, "DTPicture");

    DTPicture sourcePic;

    boolean shouldRemove;

    public boolean importData(JComponent c, Transferable t) {
        Image image;
        Token token;
        DTPicture picture;
        if (canImport(c, t.getTransferDataFlavors())) {
//            if (true) {
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

//                token = (Token) t.getTransferData(tokenFlavor);
//                pic.setToken(token);

//                picture = (DTPicture) t.getTransferData(dtpicFlavor);
//                pic = (DTPicture)picture.clon();


                //
                ////여기에 좌표 옮기는 메서드 넣음
                //

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
//        return new TokenTransferable(sourcePic);
//        return new DTPicTransferable(sourcePic);

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

        PictureTransferable(DTPicture dtPic) {
            image = dtPic.image;
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

    class TokenTransferable implements Transferable {
        private Token token;

        TokenTransferable(DTPicture dtPic) {
            token = dtPic.token;
        }

        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return token;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{pictureFlavor};
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return pictureFlavor.equals(flavor);
        }
    }

    class DTPicTransferable implements Transferable {
        private DTPicture pic;

        DTPicTransferable(DTPicture dtPic) {
            pic = dtPic;
        }

        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return pic;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{pictureFlavor};
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return pictureFlavor.equals(flavor);
        }
    }
}