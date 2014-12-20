package Sources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Created by hyeongminpark on 14. 12. 20..
 */
public class Board2 {
    int cardNum = 50;

    ArrayList<BufferedImage> fixedTokenImages = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> freeTokenImages = new ArrayList<BufferedImage>();

    Card card;
    JPanel tokenLine;

    public void go(){
//        showGUI();
    }

    public Board2(){
        String cardFileDir = "./src/Cards/Card" + cardNum + ".txt";
        card = new Card(cardFileDir);
        card.plotBoard();

//        JPanel imageLine = new JPanel(new GridLayout(1, 5));
//        imageLine.setPreferredSize(new Dimension(500, 100));
//        JPanel imageTable = new JPanel(new GridLayout(5, 5));
//        imageTable.setPreferredSize(new Dimension(500, 500));
//
//
//        for(int i=0; i<card.getFreeTokens().size(); i++){
//            try {
//                if (card.getFreeTokens().get(i).getColor().equals("k")) {
//                    freeTokenImages.add(ImageIO.read(new File("../images/TokenBlack.png")));
//                } else if (card.getFreeTokens().get(i).getColor().equals("b")) {
//                    freeTokenImages.add(ImageIO.read(new File("../images/TokenBlue.png")));
//                } else if (card.getFreeTokens().get(i).getColor().equals("g")) {
//                    freeTokenImages.add(ImageIO.read(new File("../images/TokenGreen.png")));
//                } else if (card.getFreeTokens().get(i).getColor().equals("p")) {
//                    freeTokenImages.add(ImageIO.read(new File("../images/TokenPurple.png")));
//                } else if (card.getFreeTokens().get(i).getColor().equals("r")) {
//                    freeTokenImages.add(ImageIO.read(new File("../images/TokenRed.png")));
//                } else if (card.getFreeTokens().get(i).getColor().equals("y")) {
//                    freeTokenImages.add(ImageIO.read(new File("../images/TokenYellow.png")));
//                } else {
//                    System.out.println("invalid color key inputted: " + card.getFreeTokens().get(i).getColor());
//                }
//                imageLine.add(new JLabel(new ImageIcon(freeTokenImages.get(freeTokenImages.size() - 1))));
//            }catch(IOException ex){
//                ex.printStackTrace();
//            }
//        }
//
////        for(int i=card.getFreeTokens().size(); i<5; i++){
////            freeTokenImages.add(null);
////            imageLine.add(null);
////        }
//
//        for(int i=0; i<5; i++){
//            for(int j=0; j<5; j++){
//                if(card.getTokenTable()[i][j] != null){
//                    try {
//                        if (card.getTokenTable()[i][j].getColor().equals("k")) {
//                            fixedTokenImages.add(ImageIO.read(new File("../images/TokenBlack.png")));
//                        } else if (card.getTokenTable()[i][j].getColor().equals("b")) {
//                            fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenBlue.png", "TokenBlue.png").getImage(), card.getTokenTable()[i][j]));
//                        } else if (card.getTokenTable()[i][j].getColor().equals("g")) {
//                            fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenGreen.png", "TokenGreen.png").getImage(), card.getTokenTable()[i][j]));
//                        } else if (card.getTokenTable()[i][j].getColor().equals("p")) {
//                            fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenPurple.png", "TokenPurple.png").getImage(), card.getTokenTable()[i][j]));
//                        } else if (card.getTokenTable()[i][j].getColor().equals("r")) {
//                            fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenRed.png", "TokenRed.png").getImage(), card.getTokenTable()[i][j]));
//                        } else if (card.getTokenTable()[i][j].getColor().equals("y")) {
//                            fixedTokenImages.add(new DTPicture(createImageIcon("../images/TokenYellow.png", "TokenYellow.png").getImage(), card.getTokenTable()[i][j]));
//                        } else {
//                            System.out.println("invalid color key inputted: " + card.getTokenTable()[i][j].getColor());
//                        }
////            fixedTokenImages.add(new DTPicture(createImageIcon("../images/" + tokenNameList[i], tokenNameList[i]).getImage()));
//                        fixedTokenImages.get(fixedTokenImages.size() - 1).setTransferHandler(picHandler);
//                        imageTable.add(fixedTokenImages.get(fixedTokenImages.size() - 1));
//                    }catch(IOException ex){
//                        ex.printStackTrace();
//                    }
//                }else{
//                        fixedTokenImages.add(new DTPicture(null, null));
//                        fixedTokenImages.get(fixedTokenImages.size() - 1).setTransferHandler(picHandler);
//                        imageTable.add(fixedTokenImages.get(fixedTokenImages.size() - 1));
//
//                }
//            }
//        }
    }

    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imageURL = Board.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return new ImageIcon(imageURL, description);
        }
    }
}
