package com.uygun;

import javax.swing.JButton;
import java.awt.*;

public class GridButton extends JButton{

    int row;
    int column;
    Color buttonColour;
    Boolean hasShip = false;
    Boolean isFishingBoat = false;

    public GridButton(int r, int c){
        this.row = r;
        this.column = c;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        buttonColour = bg;
    }
}