package com.akifbatur.pod;

/*
 @author PoD Team
 * Marmara University
 */

import java.awt.Color;
import java.awt.GridLayout;
public class Main
{

    public static void main(String[] args) throws Exception
    {
        System.out.println("[INFO]: Arayüz oluşturuluyor...");
        MainFrame jframe = new MainFrame();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.setExtendedState(jframe.MAXIMIZED_BOTH);  
        jframe.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Pharmacies on Duty");
        jframe.setLayout(new GridLayout(1, 1, 5, 5));
        jframe.setResizable(true);
        Color bgcolor = new Color(204, 204, 204);
        jframe.setBackground(bgcolor);
        System.out.println("[INFO]: Arayüz oluşturuldu.");
    }
}
