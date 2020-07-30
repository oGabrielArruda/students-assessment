package Programa;

import Janelas.InitialWindow;

import javax.swing.*;

public class Programa {
    public static void main(String[] args) {
        try
        {
            JFrame j = new JFrame();
            j.setContentPane(new InitialWindow().panelMain);
            j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            j.pack();
            j.setSize(300, 400);
            j.setVisible(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
