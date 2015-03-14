package com.akifbatur.pod;

/*
 @author PoD Team
 * Marmara University
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel
{

    private ImageIcon imageIcon;
    private Image image;

    public void setImage(String str)
    {
        imageIcon = new ImageIcon(str);        
        image = imageIcon.getImage();
        repaint();
    }
    public void flush()
    {
        imageIcon = null;       
        image.flush();
        image = null;
    }
    /** Draw image on the panel */
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (image != null)
        {
            Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}