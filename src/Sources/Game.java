package Sources;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hyeongminpark on 14. 12. 18..
 */
public class Game {
    public static void main(String[] args){

        Game game = new Game();
        game.go();

        Scanner sc = new Scanner(System.in);
        System.out.println("카드 번호를 선택하십시오");
        int cardNum = sc.nextInt();
    }

    String[] cardList = {"1","2","4","5","20","50"};

    JLabel cardLabel;
    JFrame frame = new JFrame();

    public void go(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);

        JButton decideButton = new JButton("play");
        JButton beforeButton = new JButton("←");
        JButton nextButton = new JButton("→");

        decideButton.addActionListener(new dicisionListener());
        beforeButton.addActionListener(new beforeListener());
        nextButton.addActionListener(new nextListener());

        cardLabel = new JLabel("1", JLabel.CENTER);
        cardLabel.setFont(new Font("Serif", Font.BOLD, 50));
        cardLabel.setBackground(Color.pink);

        panel.add(beforeButton);
        panel.add(decideButton);
        panel.add(nextButton);

        panel.setSize(350, 100);
        frame.getContentPane().add(BorderLayout.CENTER, cardLabel);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.setSize(350, 200);
        frame.setVisible(true);
    }

    class beforeListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(cardLabel.getText().equals("1")){
                return;
            }else{
                System.out.println(cardList[Arrays.asList(cardList).indexOf(cardLabel.getText())-1]);
                cardLabel.setText(cardList[Arrays.asList(cardList).indexOf(cardLabel.getText())-1]);
            }
        }
    }

    class nextListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(cardLabel.getText().equals("50")){
                return;
            }else {
                System.out.println(cardList[Arrays.asList(cardList).indexOf(cardLabel.getText())+1]);
                cardLabel.setText(cardList[Arrays.asList(cardList).indexOf(cardLabel.getText())+1]);
            }
        }
    }

    class dicisionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Board myBoard = new Board(Integer.parseInt(cardLabel.getText()));
            myBoard.showGUI();
            frame.dispose();
        }
    }
}