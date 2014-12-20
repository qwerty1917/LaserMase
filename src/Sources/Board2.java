package Sources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hyeongminpark on 14. 12. 20..
 */
public class Board2 {
    int cardNum = 50;

    ArrayList<Image> freeTokenImages = new ArrayList<Image>();
    Image[][] fixedTokenImages = new Image[5][5];

    Card card;
//    JPanel tokenLine;

    public void go(){
        doMouseSmt();
    }

    public Board2(){
        for(int i = 0; i<5; i++){
            for(int j = 0; j<5; j++){
                fixedTokenImages[i][j] = null;
            }
        }

        String cardFileDir = "./src/Cards/Card" + cardNum + ".txt";
        card = new Card(cardFileDir);
        card.plotBoard();
    }

    public void doMouseSmt(){
//        tokenUpdate();
        showGUI();
    }

    public void showGUI(){
        JFrame mainFrame = new JFrame("Game Board");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TokensImageDraw draw = new TokensImageDraw(card);

        mainFrame.getContentPane().add(draw);

        mainFrame.pack();
        mainFrame.setSize(500, 620); // 위쪽 바가 20픽셀 차지한다.
        mainFrame.setVisible(true);
//        mainFrame.repaint();
    }

    class TokensImageDraw extends JPanel implements MouseListener, MouseMotionListener{
        private Card card;

        int sX = -1, sY = -1;
        boolean dragging = false;
        int curX = -1, curY = -1;

        //이 클래스의 생성자이다.
        public TokensImageDraw(Card card){
            System.out.println("JPanel 에 카드 객체 받아옴");
            addMouseListener(this);
            addMouseMotionListener(this);
            this.card = card;
        }

        public void controlVhange(MouseEvent e){
            repaint();
        }

        public Token guessToken(int x, int y){
            int tokenPosX = x/100;
            int tokenPosY = y/100; // freetoken라인을 고려해서 6줄로 친다

            System.out.println("guessed token pos x: "+ tokenPosX);
            System.out.println("guessed token pos y: "+ tokenPosY);

            if(tokenPosY==0){
                System.out.println("guessing from free token line");
                return card.getFreeTokenTable()[tokenPosX];
            }else{
                System.out.println("guessing from token table");
                return card.getTokenTable()[tokenPosY-1][tokenPosX];
            }
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            if(event.getClickCount()==2){
                Point point = event.getPoint();
                int dX = point.x;
                int dY = point.y;

                Token token = guessToken(dX, dY);

                if(dY==0){
                    return;
                }

                if(token.getRotateAble()){
                    token.rotate();
//                    this.image = rotate(this.image);
//                    repaint();
                }else{
                    System.out.println("this token set unrotatable");
                }

                event.consume();
            }
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent event) {}

        @Override
        public void mouseExited(MouseEvent event) {}

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

            System.out.println("MOUSE MOVE IS " + sX + "," + sY + " to " + p.x + "," + p.y);

        }

        @Override
        public void mouseDragged(MouseEvent event) {

            Point p = event.getPoint();

            // System.err.println("mouse drag to " + p);

//        showStatus("mouse Dragged to " + p);

            curX = p.x;

            curY = p.y;

            Token token = guessToken(sX, sY);
            if(token.getMoveAble()){
                token.setPosX(p.x/100);
                token.setPosY(p.y/100);

            }
            repaint();

            if (dragging) {

                repaint();

            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        public void paintComponent(Graphics g){
            System.out.println("paintComponent 들어옴");
//            Image image2 = new ImageIcon("../images/TokenBlack.png").getImage();
//            g.drawImage(image2, 0, 0, this);

            //free토큰을 우선 한줄로 그림
            for(int i = 0; i < card.getFreeTokens().size(); i++){
                System.out.println("자유토큰 그리기시작.. "+ i);

                Token token = card.getFreeTokens().get(i);
                System.out.println(token.getColor() + " 그릴 차례 ");
                try {
                    if (token.getColor().equals("k")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlack.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, i * 100, 0, this);

                    } else if (token.getColor().equals("b")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlue.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, i * 100, 0, this);

                    } else if (token.getColor().equals("g")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenGreen.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, i * 100, 0, this);

                    } else if (token.getColor().equals("p")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurple.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, i * 100, 0, this);

                    } else if (token.getColor().equals("r")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenRed.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, i * 100, 0, this);

                    } else if (token.getColor().equals("y")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenYellow.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, i * 100, 0, this);
                    }
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }

            //fixture 토큰을 격자로 그림
            for(int i = 0; i < card.getFixtureTokens().size(); i++){
                System.out.println("고정토큰 그리기시작.. "+ i);

                Token token = card.getFixtureTokens().get(i);
                System.out.println(token.getColor() + " 그릴 차례 ");

                try {
                    if (token.getColor().equals("k")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlack.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, token.getPosX() * 100, (token.getPosY() + 1) * 100, this);

                    } else if (token.getColor().equals("b")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlue.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, token.getPosX() * 100, (token.getPosY() + 1) * 100, this);

                    } else if (token.getColor().equals("g")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenGreen.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, token.getPosX() * 100, (token.getPosY() + 1) * 100, this);

                    } else if (token.getColor().equals("p")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurple.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, token.getPosX() * 100, (token.getPosY() + 1) * 100, this);

                    } else if (token.getColor().equals("r")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenRed.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, token.getPosX() * 100, (token.getPosY() + 1) * 100, this);

                    } else if (token.getColor().equals("y")) {
                        System.out.println(token.getColor() + " 그리는중");
                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenYellow.png"));
                        image = setTokenImageDir(image, token);
                        g.drawImage(image, token.getPosX() * 100, (token.getPosY() + 1) * 100, this);
                    }
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }

        public Image setTokenImageDir(Image image, Token token){
            if(token.getDir()==12){
                return image;
            }else if(token.getDir()==3){
                return rotate(image);
            }else if(token.getDir()==6){
                return rotate(rotate(image));
            }else if(token.getDir()==9){
                return rotate(rotate(rotate(image)));
            }else{
                System.out.println("token.getDir 에서 이상한 값 받음;; : " + token.getDir());
                return image;
            }
        }

        public Image rotate(Image img)
        {
            BufferedImage oldImage = imageToBufImage(img);

            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(90), oldImage.getWidth() / 2, oldImage.getHeight() / 2);

            AffineTransformOp opRotated = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage newImage = opRotated.filter(oldImage, null);

            return bufImageToImage(newImage);
        }

        public BufferedImage imageToBufImage(Image img){
            if(img instanceof BufferedImage){
                return (BufferedImage)img;
            }
            BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            Graphics2D tmp = bufferedImage.createGraphics();
            tmp.drawImage(img, 0, 0, null);
            tmp.dispose();

            return bufferedImage;
        }

        public Image bufImageToImage(BufferedImage buffImg){
            return Toolkit.getDefaultToolkit().createImage(buffImg.getSource());
        }
    }
}