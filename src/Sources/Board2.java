package Sources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hyeongminpark on 14. 12. 20..
 */
public class Board2 implements ActionListener{
    int cardNum = 1;
    int moveOrRotateCount = 0;

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
    TokensImageDraw draw;
    public void showGUI(){
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
//        mainFrame.repaint();
    }

    public void actionPerformed(ActionEvent event){
        System.out.println("레이저 버튼 누름");
        card.shootLaser();

        draw.repaint();

        int firstLaserShootDir=0;
        int firstLaserShootPosX=0;
        int firstLaserShootPosY=0;

        class LaserGoing{
            public int laserDir;
            public int fromX;
            public int fromY;

            public LaserGoing(int x,int y,int d){
                this.laserDir = d;
                this.fromX = x;
                this.fromY = y;
            }
        }

//        ArrayList<LaserGoing> laserGoingList = new ArrayList<LaserGoing>();
//
//        int laserMoveCount=0;
//
//        for(int i = 0; i<5; i++){
//            for(int j=0; j<5; j++){
//                if(card.getTokenTable()[j][i]==null){
//                    continue;
//                }
//                if(card.getTokenTable()[j][i].getColor().equals("r")){
//                    card.getTokenTable()[j][i].setLaserShootDir();
//                    System.out.println(card.getTokenTable()[j][i].getLaserShootDirs());
//                    firstLaserShootDir = card.getTokenTable()[j][i].getLaserShootDirs().get(card.getTokenTable()[j][i].getLaserShootDirs().size()-1);
//                    firstLaserShootPosX = card.getTokenTable()[j][i].getPosX();
//                    firstLaserShootPosY = card.getTokenTable()[j][i].getPosY();
//
//                    laserGoingList.add(new LaserGoing(firstLaserShootPosX, firstLaserShootPosY, firstLaserShootDir));
//                }
//            }
//        }
//        System.out.println(firstLaserShootDir);
//
//        while(laserMoveCount<30){
//            ArrayList<LaserGoing> tmpLaserGoingList = new ArrayList<LaserGoing>();
//
//            for(int i=0; i<laserGoingList.size(); i++){
//                LaserGoing tmpLaserGoing;
//                if(laserGoingList.get(i).laserDir == 12){
//                    for(int k=laserGoingList.get(i).fromY-1; k>=0; k--){
//                        if(card.getTokenTable()[k][laserGoingList.get(i).fromX]!=null){
//                            card.getTokenTable()[k][laserGoingList.get(i).fromX].addLaserDetectedDir(6);
//
//                            if(card.getTokenTable()[k][laserGoingList.get(i).fromX].isTarget()){
//                                System.out.println("////////// Hit!! //////////");
//                            }
//
//                            card.getTokenTable()[k][laserGoingList.get(i).fromX].setLaserShootDir();
//                            int newLaserDir = card.getTokenTable()[k][laserGoingList.get(i).fromX].getLaserShootDirs().get(card.getTokenTable()[k][laserGoingList.get(i).fromX].getLaserShootDirs().size()-1);
//
//                            tmpLaserGoing = new LaserGoing(laserGoingList.get(i).fromX, k, newLaserDir);
//                            tmpLaserGoingList.add(tmpLaserGoing);
//                            break;
//                        }
//                    }
//                }else if(laserGoingList.get(i).laserDir == 3){
//                    for(int k=laserGoingList.get(i).fromX+1; k<5; k++){
//                        if(card.getTokenTable()[laserGoingList.get(i).fromY][k]!=null){
//                            card.getTokenTable()[laserGoingList.get(i).fromY][k].addLaserDetectedDir(9);
//
//                            if(card.getTokenTable()[laserGoingList.get(i).fromY][k].isTarget()){
//                                System.out.println("Hit!!");
//                            }
//
//                            card.getTokenTable()[laserGoingList.get(i).fromY][k].setLaserShootDir();
//                            int newLaserDir = card.getTokenTable()[laserGoingList.get(i).fromY][k].getLaserShootDirs().get(card.getTokenTable()[laserGoingList.get(i).fromY][k].getLaserShootDirs().size()-1);
//
//                            tmpLaserGoing = new LaserGoing(k, laserGoingList.get(i).fromY, newLaserDir);
//                            tmpLaserGoingList.add(tmpLaserGoing);
//                            break;
//                        }
//                    }
//
//                }else if(laserGoingList.get(i).laserDir == 6){
//                    for(int k=laserGoingList.get(i).fromY+1; k<5; k++){
//                        System.out.println("k: " + k);
//                        System.out.println("laserGoingList.get(i).fromX: "+ laserGoingList.get(i).fromX);
//                        if(card.getTokenTable()[k][laserGoingList.get(i).fromX]!=null){
//                            card.getTokenTable()[k][laserGoingList.get(i).fromX].addLaserDetectedDir(12);
//
//                            if(card.getTokenTable()[k][laserGoingList.get(i).fromX].isTarget()){
//                                System.out.println("Hit!!");
//                            }
//
//                            card.getTokenTable()[k][laserGoingList.get(i).fromX].setLaserShootDir();
//                            int newLaserDir = card.getTokenTable()[k][laserGoingList.get(i).fromX].getLaserShootDirs().get(card.getTokenTable()[k][laserGoingList.get(i).fromX].getLaserShootDirs().size()-1);
//
//                            tmpLaserGoing = new LaserGoing(laserGoingList.get(i).fromX, k, newLaserDir);
//                            tmpLaserGoingList.add(tmpLaserGoing);
//                            break;
//                        }
//                    }
//
//                }else if(laserGoingList.get(i).laserDir == 9){
//                    for(int k=laserGoingList.get(i).fromX-1; k>=0; k--){
//                        if(card.getTokenTable()[laserGoingList.get(i).fromY][k]!=null){
//                            card.getTokenTable()[laserGoingList.get(i).fromY][k].addLaserDetectedDir(3);
//
//                            if(card.getTokenTable()[laserGoingList.get(i).fromY][k].isTarget()){
//                                System.out.println("Hit!!");
//                            }
//
//                            card.getTokenTable()[laserGoingList.get(i).fromY][k].setLaserShootDir();
//                            int newLaserDir = card.getTokenTable()[laserGoingList.get(i).fromY][k].getLaserShootDirs().get(card.getTokenTable()[laserGoingList.get(i).fromY][k].getLaserShootDirs().size()-1);
//
//                            tmpLaserGoing = new LaserGoing(k, laserGoingList.get(i).fromY, newLaserDir);
//                            tmpLaserGoingList.add(tmpLaserGoing);
//                            break;
//                        }
//                    }
//
//                }else{
//                    System.out.println("이상한 레이저 발사 방향: " + laserGoingList.get(i).laserDir);
//                }
//            }
//            //laserGoinglist 지우고
//            //tmpLaserGoingList 로 다시 채우기
//            if(laserGoingList.size() != 0){
//                System.out.println(" 레이저 도착점중 하나 " +laserGoingList.get(laserGoingList.size()-1).fromX + " " + laserGoingList.get(laserGoingList.size()-1).fromY);
//            }else{
//                System.out.println(" laserGoingList 비었음");
//            }
//            laserGoingList.clear();
//            for(int i=0; i<tmpLaserGoingList.size(); i++){
//                laserGoingList.add(tmpLaserGoingList.get(i));
//            }
//
//            laserMoveCount++;
//        }

    }

    class TokensImageDraw extends JPanel implements MouseListener, MouseMotionListener{
        private Card card;

        int sX = 0, sY = 0;
        boolean dragging = false;
        int curX = 0, curY = 0;

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
                    moveOrRotateCount++;
                    card.laserGoingList.clear();
//                    this.image = rotate(this.image);
//                    repaint();
                }else{
                    System.out.println("this token set unrotatable");
                }

                event.consume();
            }
            this.repaint();
            card.plotBoard();
            event.consume();
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
                System.out.println("movable");

                // 마우스를 놓은 위치가 free토큰 라인이면 무시한다.
                if(p.y/100 == 0){
                    return;
                }

                if((p.x/100 == sX/100) && (p.y/100 == sY/100)){
                    return;
                }
                System.out.println("====\nMOUSE MOVE IS " + sX + "," + sY + " to " + p.x + "," + p.y);
                System.out.println("TOKEN MOVE IS " + sX/100 + "," + ((sY/100)-1) + "to" + p.x/100 + "," +((p.y/100)-1));
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
                System.out.println("unmoveable");
            }
            card.plotBoard();

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
//            System.out.println("paintComponent 들어옴");
//            Image image2 = new ImageIcon("../images/TokenBlack.png").getImage();
//            g.drawImage(image2, 0, 0, this);

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
                                Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlack.png"));
                                image = setTokenImageDir(image, token);
                                g.drawImage(image, j * 100, (i + 1) * 100, this);

                            } else if (token.getColor().equals("b")) {
                                Image image = ImageIO.read(this.getClass().getResource("../images/TokenBlue.png"));
                                image = setTokenImageDir(image, token);
                                g.drawImage(image, j * 100, (i + 1) * 100, this);

                            } else if (token.getColor().equals("g")) {
                                Image image = ImageIO.read(this.getClass().getResource("../images/TokenGreen.png"));
                                image = setTokenImageDir(image, token);
                                g.drawImage(image, j * 100, (i + 1) * 100, this);

                            } else if (token.getColor().equals("p")) {
                                if(token.isTarget()){
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurpleTarget.jpg"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }else{
                                    Image image = ImageIO.read(this.getClass().getResource("../images/TokenPurple.png"));
                                    image = setTokenImageDir(image, token);
                                    g.drawImage(image, j * 100, (i + 1) * 100, this);
                                }

                            } else if (token.getColor().equals("r")) {
                                Image image = ImageIO.read(this.getClass().getResource("../images/TokenRed.png"));
                                image = setTokenImageDir(image, token);
                                g.drawImage(image, j * 100, (i + 1) * 100, this);

                            } else if (token.getColor().equals("y")) {
                                Image image = ImageIO.read(this.getClass().getResource("../images/TokenYellow.png"));
                                image = setTokenImageDir(image, token);
                                g.drawImage(image, j * 100, (i + 1) * 100, this);
                            }
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }else{

                    }

                }
            }

//            System.out.println("moveOrRotateCount count: " + moveOrRotateCount);

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
//                System.out.println("token.getDir 에서 이상한 값 받음;; : " + token.getDir());
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