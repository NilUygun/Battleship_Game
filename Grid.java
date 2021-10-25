package com.uygun;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    public GridButton[][] buttonList = new GridButton[8][8];
    public ArrayList<GridButton> targetShipButtons = new ArrayList<GridButton>();
    enum Direction{
        HORIZONTAL,
        VERTICAL,
    }

    Random rand = new Random();
    private GridButton getRandomButton(){
        while(true) {
            int randomColumn = rand.nextInt(8);
            int randomRow = rand.nextInt(8);
            GridButton randButton = buttonList[randomRow][randomColumn];
            if (randButton.hasShip) {
                continue;
            }else{
                return randButton;
            }
        }
    }

    private Direction getRandomDirection(){
        int randomDirectionNum = rand.nextInt(2);
        Direction randDirection;
        if (randomDirectionNum == 0) {
            randDirection = Direction.HORIZONTAL;
        }else {
            randDirection = Direction.VERTICAL;
        }
        return randDirection;
    }

    private int checkRight(int row, int column){
        int count = 0;
        int columnToCheck = column+1;
        while(true){
            if(columnToCheck>7 || buttonList[row][columnToCheck].hasShip){
                return count;
            }else{
                count++;
                columnToCheck++;
            }
        }
    }

    private int checkLeft(int row, int column){
        int count = 0;
        int columnToCheck = column-1;
        while(true){
            if(columnToCheck<0 || buttonList[row][columnToCheck].hasShip){
                return count;
            }else{
                count++;
                columnToCheck--;
            }
        }
    }

    private int checkDown(int row, int column){
        int count = 0;
        int rowToCheck = row+1;
        while(true){
            if(rowToCheck>7 || buttonList[rowToCheck][column].hasShip){
                return count;
            }else{
                count++;
                rowToCheck++;
            }
        }
    }

    private int checkUp(int row, int column){
        int count = 0;
        int rowToCheck = row-1;
        while(true){
            if(rowToCheck<0 || buttonList[rowToCheck][column].hasShip){
                return count;
            }else{
                count++;
                rowToCheck--;
            }
        }
    }

    public void placeShip(int length){
        while(true) {
            GridButton initialButton = getRandomButton();
            Direction theDirection = getRandomDirection();

            if (theDirection == Direction.HORIZONTAL) {
                int leftSpace = checkLeft(initialButton.row, initialButton.column);
                int rightSpace = checkRight(initialButton.row, initialButton.column);
                int totalSpace = leftSpace + rightSpace + 1;
                if(totalSpace<length){
                    continue;
                }else{
                    int rightLimit = (initialButton.column + rightSpace)-(length - 1);
                    int leftLimit = initialButton.column - leftSpace;
                    int randStartColumn = rand.nextInt((rightLimit - leftLimit) + 1) + leftLimit;
                    for(int c = randStartColumn; c<= (randStartColumn+length)-1; c++){
                        targetShipButtons.add(buttonList[initialButton.row][c]);
                        buttonList[initialButton.row][c].hasShip = true;
                    }
                    break;
                }
            } else {
                int upSpace = checkUp(initialButton.row, initialButton.column);
                int downSpace = checkDown(initialButton.row, initialButton.column);
                int totalSpace = upSpace + downSpace + 1;
                if(totalSpace<length){
                    continue;
                }else{
                    int downLimit = (initialButton.row + downSpace)-(length - 1);
                    int upLimit = initialButton.row - upSpace;
                    int randStartRow = rand.nextInt((downLimit - upLimit) + 1) + upLimit;
                    for(int r = randStartRow; r<= (randStartRow+length)-1; r++){
                        targetShipButtons.add(buttonList[r][initialButton.column]);
                        buttonList[r][initialButton.column].hasShip = true;
                    }
                    break;
                }
            }
        }
    }

    public void placeFishingBoat(){
        GridButton fishingBoatButton = getRandomButton();
        fishingBoatButton.isFishingBoat = true;
    }

    public void resetBoard(){
        for(int r = 0; r<8; r++){
            for(int c = 0; c<8; c++){
                buttonList[r][c].hasShip = false;
                buttonList[r][c].isFishingBoat = false;
                buttonList[r][c].setBackground(new Color(227, 229, 232));
            }
        }

        Main.setScore(20);
        Main.numOfShipButtonsFound = 0;
        for(int length : Main.shipLengths){
            placeShip(length);
        }
        placeFishingBoat();
    }
}
