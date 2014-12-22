package Sources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;


/**
 * Created by hyeongminpark on 14. 12. 20..
 */
public class Board implements ActionListener{
    int cardNum;
    int moveOrRotateCount = 0;

    Image[][] fixedTokenImages = new Image[5][5];

    Card card;

    public Board(int cn){
        cardNum = cn;
        for(int i = 0; i<5; i++){
            for(int j = 0; j<5; j++){
                fixedTokenImages[i][j] = null;
            }
        }

        String cardFileDir = "./src/Cards/Card" + cardNum + ".txt";
        card = new Card(cardFileDir);
//        card.plotBoard();
    }

    TokensImageDraw draw;
    public void showGUI(){
        String targetInfo = "number of target: " + card.getNumOfTargets();
        JOptionPane.showMessageDialog(null, targetInfo);

        JFrame mainFrame = new JFrame("Game Board");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        draw = new TokensImageDraw(card);
        JButton laserButton = new JButton("shoot Laser");
        laserButton.addActionListener(this);

        mainFrame.getContentPane().add(BorderLayout.CENTER, draw);
        mainFrame.getContentPane().add(BorderLayout.SOUTH, laserButton);

        mainFrame.pack();
        mainFrame.setSize(500, 660); // 위쪽 바가 20픽셀 차지한다.
        mainFrame.setVisible(true);
    }



    public void actionPerformed(ActionEvent event){
        System.out.println("레이저 버튼 누름");

        card.shootLaser();

        draw.repaint();

        if(card.isWin){
            JOptionPane.showMessageDialog(null, "Win !!!");
        }
    }

    class TokensImageDraw extends JPanel implements MouseListener, MouseMotionListener{
        private Card card;

        int sX = 0, sY = 0;
        boolean dragging = false;
        int curX = 0, curY = 0;

        //이 클래스의 생성자이다.
        public TokensImageDraw(Card card){
            addMouseListener(this);
            addMouseMotionListener(this);
            this.card = card;
        }

        public Token guessToken(int x, int y){
            int tokenPosX = x/100;
            int tokenPosY = y/100; // freetoken라인을 고려해서 6줄로 친다


            if(tokenPosY==0){
                return card.getFreeTokenTable()[tokenPosX];
            }else{
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
                    moveOrRotateCount++;
                    card.laserGoingList.clear();
                }else{
                    JOptionPane.showMessageDialog(null, "this token set unrotatable");
                }

                event.consume();
            }
            this.repaint();
//            card.plot();
            event.consume();
            card.laserGoingFullPath.clear();

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

            Token token = guessToken(sX, sY);
            if(token.getMoveAble()){
//                System.out.println("movable");

                // 마우스를 놓은 위치가 free토큰 라인이면 무시한다.
                if(p.y/100 == 0){
                    return;
                }

                if((p.x/100 == sX/100) && (p.y/100 == sY/100)){
                    return;
                }

                if(card.getTokenTable()[(p.y/100)-1][p.x/100]!=null){
                    JOptionPane.showMessageDialog(null, "두 토큰을 겹칠 수 없습니다.");
                    return;
                }
//                System.out.println("====\nMOUSE MOVE IS " + sX + "," + sY + " to " + p.x + "," + p.y);
//                System.out.println("TOKEN MOVE IS " + sX/100 + "," + ((sY/100)-1) + "to" + p.x/100 + "," +((p.y/100)-1));
                card.insertFreeToknToTable(token, p.x/100, ((p.y/100)-1));

                moveOrRotateCount++;
                card.laserGoingList.clear();


                if(sY/100 == 0){
                    card.removeTokenFromLine(sX/100);
                }else{
                    card.removeTokenfromTable(sX/100, (sY/100)-1);
                }
                event.consume();
                this.repaint();
            }else{
                System.out.println("unmovable");
            }
//            card.plot();
            card.laserGoingFullPath.clear();

        }

        @Override
        public void mouseDragged(MouseEvent event) {
            Point p = event.getPoint();
            curX = p.x;
            curY = p.y;
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        public void paintComponent(Graphics g){

            //free토큰을 우선 한줄로 그림
            for(int i = 0; i < card.getFreeTokenTable().length; i++){

                Token token = card.getFreeTokenTable()[i];
                if(token != null){
                    try {
                        if (token.getColor().equals("k")) {
                            Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlackRotate.JPG"));
                            image = setTokenImageDir(image, token);
                            g.drawImage(image, i * 100, 0, this);

                        } else if (token.getColor().equals("b")) {
                            Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlueRotate.JPG"));
                            image = setTokenImageDir(image, token);
                            g.drawImage(image, i * 100, 0, this);

                        } else if (token.getColor().equals("g")) {
                            Image image = ImageIO.read(this.getClass().getResource("../images/TokenGreenRotate.JPG"));
                            image = setTokenImageDir(image, token);
                            g.drawImage(image, i * 100, 0, this);

                        } else if (token.getColor().equals("p")) {
                            if(token.isTarget()){
                                Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurpleRotateTarget.JPG"));
                                image = setTokenImageDir(image, token);
                                g.drawImage(image, i * 100, 0, this);
                            }else {
                                Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurpleRotate.JPG"));
                                image = setTokenImageDir(image, token);
                                g.drawImage(image, i * 100, 0, this);
                            }

                        } else if (token.getColor().equals("r")) {
                            Image image = ImageIO.read(this.getClass().getResource("../images/TokenRedRotate.JPG"));
                            image = setTokenImageDir(image, token);
                            g.drawImage(image, i * 100, 0, this);

                        } else if (token.getColor().equals("y")) {
                            Image image = ImageIO.read(this.getClass().getResource("../images/TokenYellowRotate.JPG"));
                            image = setTokenImageDir(image, token);
                            g.drawImage(image, i * 100, 0, this);
                        }

                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }else{

                }

            }

            //fixture 토큰을 격자로 그림
            for(int i = 0; i < 5; i++){
                for(int j=0; j < 5; j++){

                    Token token = card.getTokenTable()[i][j];

                    if(token != null){

                        try {
                            if (token.getColor().equals("k")) {
                                if(moveOrRotateCount==0 && token.getRotateAble()){
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlack.png"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }else{
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlack.png"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }

                            } else if (token.getColor().equals("b")) {
                                if(moveOrRotateCount==0 && token.getRotateAble()){
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlueRotate.jpg"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }else{
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlue.png"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }

                            } else if (token.getColor().equals("g")) {
                                if(moveOrRotateCount==0 && token.getRotateAble()){
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenGreenRotate.jpg"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }else{
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenGreen.png"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }

                            } else if (token.getColor().equals("p")) {
                                if(moveOrRotateCount==0 && token.getRotateAble()){
                                    if(token.isTarget()){
                                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurpleRotateTarget.jpg"));
                                        image = setTokenImageDir(image, token);
                                        g.drawImage(image, j * 100, (i + 1) * 100, this);
                                    }else{
                                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurpleRotate.jpg"));
                                        image = setTokenImageDir(image, token);
                                        g.drawImage(image, j * 100, (i + 1) * 100, this);
                                    }
                                }else{
                                    if(token.isTarget()){
                                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurpleTarget.jpg"));
                                        image = setTokenImageDir(image, token);
                                        g.drawImage(image, j * 100, (i + 1) * 100, this);
                                    }else{
                                        Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurple.png"));
                                        image = setTokenImageDir(image, token);
                                        g.drawImage(image, j * 100, (i + 1) * 100, this);
                                    }
                                }

                            } else if (token.getColor().equals("r")) {
                                if(moveOrRotateCount==0 && token.getRotateAble()){
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenRedRotate.jpg"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }else{
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenRed.png"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }

                            } else if (token.getColor().equals("y")) {
                                if(moveOrRotateCount==0 && token.getRotateAble()){
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenYellowRotate.jpg"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }else{
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenYellow.png"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }

                            }
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }else{

                    }

                }
            }

            //이제 레이저를 그리자!
            for(Card.LaserGoing laserGoing: card.laserGoingFullPath){
                int sx, sy, ex, ey;
                if(laserGoing.laserDir==12){
                    int x = laserGoing.fromX;
                    sx=laserGoing.fromX*100+50;
                    sy=laserGoing.fromY*100+50;
                    ex=laserGoing.fromX*100+50;
                    ey=laserGoing.fromY*100+50;
                    for(int y = laserGoing.fromY-1; y>=0; y--){
                        if(card.getTokenTable()[y][x]!=null){
                            ey=y*100+50;
                            break;
                        }
                        ey=0;
                    }

                }else if(laserGoing.laserDir==3){
                    int y = laserGoing.fromY;
                    sx=laserGoing.fromX*100+50;
                    sy=laserGoing.fromY*100+50;
                    ex=laserGoing.fromX*100+50;
                    ey=laserGoing.fromY*100+50;
                    for(int x = laserGoing.fromX+1; x<5; x++){
                        if(card.getTokenTable()[y][x]!=null){
                            ex=x*100+50;
                            break;
                        }
                        ex=500;
                    }

                }else if(laserGoing.laserDir==6){
                    int x = laserGoing.fromX;
                    sx=laserGoing.fromX*100+50;
                    sy=laserGoing.fromY*100+50;
                    ex=laserGoing.fromX*100+50;
                    ey=laserGoing.fromY*100+50;
                    for(int y = laserGoing.fromY+1; y<5; y++){
                        if(card.getTokenTable()[y][x]!=null){
                            ey=y*100+50;
                            break;
                        }
                        ey=600;
                    }

                }else if(laserGoing.laserDir==9){
                    int y = laserGoing.fromY;
                    sx=laserGoing.fromX*100+50;
                    sy=laserGoing.fromY*100+50;
                    ex=laserGoing.fromX*100+50;
                    ey=laserGoing.fromY*100+50;
                    for(int x = laserGoing.fromX-1; x>=0; x--){
                        if(card.getTokenTable()[y][x]!=null){
                            ex=x*100+50;
                            break;
                        }
                        ex=0;
                    }

                }else{
                    sx=0;
                    sy=0;
                    ex=0;
                    ey=0;
                }

                // 제일 윗줄이 공간을 차지하기에 한칸씩 내려준다
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.red);
                g2.setStroke(new BasicStroke(5));
                g2.drawLine(sx, sy + 100, ex, ey + 100);
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