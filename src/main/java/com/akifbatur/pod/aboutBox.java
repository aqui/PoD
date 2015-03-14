package com.akifbatur.pod;

/*
 @author PoD Team
 * Marmara University
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class aboutBox extends JFrame
{
	private static final long serialVersionUID = 1L;

	public class panel extends JPanel
    {
		private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);   
            g.drawString("Pharmacies on Duty",200,30);
            g.drawString("v.1.0",230,60);
            g.drawString("Vendor: Group 1",200,90);
            //g.drawString("Contact: ",130,120);
            ImageIcon imageIcon = new ImageIcon("images/temporaryQR.png");        
            java.awt.Image image = imageIcon.getImage();
            g.drawImage(image, 100, 150, this);
        }
    }
    
    aboutBox()
    {
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Pharmacies on Duty");
        setLayout(new GridLayout(1, 1, 5, 5));
        setResizable(false);
        Color bgcolor = new Color(204, 204, 204);
        setBackground(bgcolor);
        panel pan = new panel();
        add(pan);
    }
}
