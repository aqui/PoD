package com.akifbatur.pod;

/*
 @author PoD Team
 * Marmara University
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import static com.akifbatur.pod.MainFrame.imagePanelForGoogleMaps;

public class Search extends JFrame
{
    private JLabel jLabel1;
    private JTextField tFName;
    private JButton bSearch;
    private JPanel panel1, panel2, panel3;

    public Search()
    {
        new JFrame();
        setSize(400, 150);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        setLocation(x, y);
        setVisible(true);
        setResizable(false);
        setTitle("Search Engine (better than Google)");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Color bgcolor = new Color(204, 204, 204);
        jLabel1 = new JLabel("", JLabel.LEFT);
        tFName = new JTextField(1);
        tFName.setDocument(new JTextFieldLimit(20));

        jLabel1.setFont(new Font("Arial", Font.BOLD, 10));
        jLabel1.setForeground(Color.black);
        jLabel1.setText("Name");
        jLabel1.setName("jLabel1");
        tFName.setName("tFName");

        bSearch = new JButton();
        bSearch.setFont(new Font("Arial", 0, 11));
        bSearch.setText("Search");
        bSearch.setName("bSearch");

        panel1 = new JPanel(new GridLayout(3, 1, 5, 5)); //for textfield names
        panel1.setBackground(bgcolor);
        panel2 = new JPanel(new GridLayout(3, 1, 5, 5)); //for textfields
        panel2.setBackground(bgcolor);
        panel3 = new JPanel(new BorderLayout(5, 5)); //panel1+panel2+panel3+panel7
        panel3.setBackground(bgcolor);
        panel1.add(jLabel1);
        panel2.add(tFName);
        panel3.add(panel1, BorderLayout.WEST);
        panel3.add(panel2, BorderLayout.CENTER);
        panel3.add(bSearch, BorderLayout.SOUTH);
        add(panel3);

        btnAction();
    }

    protected MaskFormatter createFormatter(String s)
    {
        MaskFormatter formatter = null;
        try
        {
            formatter = new MaskFormatter(s);
            formatter.setPlaceholderCharacter('_');
        } catch (java.text.ParseException exc)
        {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    private void btnAction()
    {
        bSearch.addActionListener(
                new ActionListener()
                {

                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        String fname = tFName.getText();
                        if (fname.isEmpty())
                        {
                            JOptionPane.showMessageDialog(null, "You must fill in at least one field to search!");
                        } 
                        else
                        {
                            try
                            {
                                dbConnection.st = dbConnection.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                String sql = "select * from Pharmacies where Pharmacy_Name='" + fname + "'";
                                dbConnection.rs = dbConnection.st.executeQuery(sql);
                                dbConnection.rs.first();
                                MainFrame.jTextFieldPharmacyName.setText(dbConnection.rs.getString("Pharmacy_Name"));
                                MainFrame.jTextFieldLatitude.setText(dbConnection.rs.getString("Latitude"));
                                MainFrame.jTextFieldLongitude.setText(dbConnection.rs.getString("Longitude"));
                                MainFrame.jFormattedTextFieldPhone.setText(dbConnection.rs.getString("Phone"));
                                MainFrame.jTextFieldEmail.setText(dbConnection.rs.getString("Email"));
                                MainFrame.jTextAreaAddress.setText(dbConnection.rs.getString("Address"));
                                MainFrame.imagePanelForQRCode.setImage("images/" + dbConnection.rs.getString("QR") + ".png");
                                if(Integer.parseInt(dbConnection.rs.getString("OnDuty"))==1)
                                {
                                    MainFrame.jCheckBoxOnDuty.setSelected(true);
                                }
                                else
                                {
                                    MainFrame.jCheckBoxOnDuty.setSelected(false);
                                }
                                imagePanelForGoogleMaps.flush();
                               imagePanelForGoogleMaps.setImage("images/"+dbConnection.rs.getString("Latitude")+dbConnection.rs.getString("Longitude")+".png");
                                MainFrame.jLabelWarning.setText("Eureka!");
                                dbConnection.st.close();
                                dbConnection.rs.close();
                                dbConnection.st = dbConnection.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                String sql2 = "select * from Pharmacies";
                                dbConnection.rs = dbConnection.st.executeQuery(sql2);
                                dbConnection.rs.beforeFirst();
                                dispose();
                            } catch (Exception ex)
                            {
                                System.out.println("[WARN]: "+ex.getMessage());
                                MainFrame.jLabelWarning.setText("Couldn't find");
                                try
                                {
                                    dbConnection.st.close();
                                    dbConnection.rs.close();
                                    dbConnection.st = dbConnection.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                    String sql = "select * from Pharmacies";
                                    dbConnection.rs = dbConnection.st.executeQuery(sql);
                                    dbConnection.rs.first();
                                    MainFrame.jTextFieldPharmacyName.setText(dbConnection.rs.getString("Fname"));
                                    MainFrame.jTextFieldLatitude.setText(dbConnection.rs.getString("Lname"));
                                    MainFrame.jFormattedTextFieldPhone.setText(dbConnection.rs.getString("Phone"));
                                    MainFrame.jTextFieldEmail.setText(dbConnection.rs.getString("Email"));
                                    MainFrame.jTextAreaAddress.setText(dbConnection.rs.getString("Address"));
                                    MainFrame.imagePanelForQRCode.setImage("images/" + dbConnection.rs.getString("QR") + ".png");
                                    dispose();
                                    setVisible(false);
                                } catch (Exception exs)
                                {
                                }
                                dispose();
                            }
                        }
                    }
                });
    }
}
