package com.uygun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Main {
    public static int score = 20;
    public static int numOfShipButtonsFound = 0;
    public static JLabel scoreLabel;
    public static int[] shipLengths = {5, 4, 3, 3, 2};
    public static void main(String[] args) {
        JFrame frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel scorePanel = new JPanel();
        scoreLabel = new JLabel("Score: " + score);
        scorePanel.add(scoreLabel);
        frame.add(scorePanel, BorderLayout.PAGE_START);

        JPanel buttonPanel = new JPanel();
        JPanel containerPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8,8));

        Grid board = new Grid();
        for(int r=0; r<8; r++){
            for(int c=0; c<8; c++){
                GridButton button = new GridButton(r, c);
                board.buttonList[r][c] = button;
                button.setBackground(new Color(227, 229, 232));
                buttonPanel.add(button);
                button.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        gridButtonPressed(button, frame, board);
                    }
                });
            }
        }

        buttonPanel.setPreferredSize(new Dimension(400, 400));
        containerPanel.add(buttonPanel);

        frame.getContentPane().add(containerPanel);
        frame.pack();
        frame.setVisible(true);
        board.resetBoard();

        JOptionPane.showOptionDialog(frame,
                "Find all the ships to win!\nYou lose if you destroy the fishing boat or your score is less than 0.\nHit: +5 points\nMiss: -3 points",
                "Welcome to Battleship Game! by Nil Uygun",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Start"}, "Start");
    }

    public static void gridButtonPressed(GridButton button, JFrame frame, Grid board){
        if(!button.hasShip){
            if(button.isFishingBoat){
                setScore(score-50);
                int playAgain = JOptionPane.showOptionDialog(null, "You destroyed the fishing boat!\nYour score is " + score + "\nDo you want to play again!",
                        "You lost!",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Yes", "No"}, "Yes");
                if(playAgain == 0){
                    board.resetBoard();
                }else{
                    score+=50;
                    frame.dispose();
                }
            }else if(button.buttonColour != Color.blue) {
                button.setBackground(Color.blue);
                setScore(score-3);
            }
        }else{
            if(button.buttonColour != Color.red){
                button.setBackground(Color.red);
                setScore(score+5);
                numOfShipButtonsFound++;
            }
        }
        if(score<0){
            int playAgain = JOptionPane.showOptionDialog(null, "Your score is " + score + ".\nDo you want to play again!",
                    "You lost!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Yes", "No"}, "Yes");
            if(playAgain == 0){
                board.resetBoard();
            }else{
                frame.dispose();
            }
        }else if(numOfShipButtonsFound == 17){
            int playAgain = JOptionPane.showOptionDialog(null, "Congratulations!\nYour score is " + score + ".\nDo you want to play again!",
                    "You won!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "Yes");
            if(playAgain == 0){
                board.resetBoard();
            }else{
                frame.dispose();
            }
        }
    }

    public static void setScore(int newScore){
        scoreLabel.setText("Score: " + newScore);
        score = newScore;
    }

}
