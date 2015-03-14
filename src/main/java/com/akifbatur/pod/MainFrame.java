package com.akifbatur.pod;

/*
 @author PoD Team
 * Marmara University
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JCheckBox;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class MainFrame extends JFrame {

    //Buttons
    private JButton jButtonDelete, jButtonFirst, jButtonLast, jButtonNew,
            jButtonNext, jButtonPrev, jButtonSave, jButtonSearch, jButtonUpdate, jButtonReport;
    //Labels
    static JLabel jLabelWarning;
    private JLabel jLabelPharmacyName, jLabelLatitude, jLabelLongitude,
            jLabelPhone, jLabelEmail, jLabelAddress, jLabelOnDuty;
    //MenuBar
    private JMenu jMenuFile, jMenuHelp;
    private JMenuBar jMenuBar;
    private JMenuItem jMenuItemNew, jMenuItemExit, jMenuItemAbout;
    //TextFields and TextAreas
    static JTextField jTextFieldPharmacyName, jTextFieldLatitude,
            jTextFieldLongitude, jTextFieldEmail, jTextFieldSearchBox;
    static JFormattedTextField jFormattedTextFieldPhone;
    static JTextArea jTextAreaAddress;
    static JCheckBox jCheckBoxOnDuty;
    //Panels
    private JPanel jPanelForjLabels, jPanelForjTextFields,
            jPanelForActionButtons, jPanelForNavigationButtons,
            jPanelForWarningLabel, panel6, panel7;
    static ImagePanel imagePanelForQRCode, imagePanelForGoogleMaps;
    //SQL Connection
    dbConnection dbconnection = new dbConnection();
    private Statement statement = dbconnection.getSt();
    private ResultSet resultSet = dbconnection.getRs();
    private Connection conn = dbconnection.getConn();
    //QR Code bilgileri
    ArrayList<String> qrInfoArray = new ArrayList<String>();

    //MainFrame() Begin
    public MainFrame() throws IOException {
        System.out.println("[INFO]: Arayüz bileşenleri oluşturuluyor...");
        initComponents();
        System.out.println("[INFO]: Arayüz bileşenleri oluşturuldu.");
        System.out.println("[INFO]: Button action'ları tanımlanıyor...");
        btnAction();
        System.out.println("[INFO]: Button action'ları tanımlandı.");
        System.out.println("[INFO]: İlk kaydın QR Code'u oluşturuluyor...");
        initFirstRecord();
        System.out.println("[INFO]: İlk kaydın QR Code'u oluşturuldu.");
    }
    //MainFrame() End

    private void initComponents() throws IOException {
        Color bgcolor = new Color(204, 204, 204);
//################MENU BAR######################
        jMenuBar = new JMenuBar();
        jMenuFile = new JMenu("File");
        jMenuHelp = new JMenu("Help");
        jMenuItemExit = new JMenuItem("Exit");
        jMenuItemNew = new JMenuItem("New");
        jMenuItemAbout = new JMenuItem("About");

        setJMenuBar(jMenuBar);
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuHelp);
        jMenuFile.add(jMenuItemNew);
        jMenuFile.add(jMenuItemExit);
        jMenuHelp.add(jMenuItemAbout);
//################LABELS######################
        jLabelPharmacyName = new JLabel("", JLabel.LEFT);
        jLabelPharmacyName.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelPharmacyName.setForeground(Color.black);
        jLabelPharmacyName.setText("Pharmacy Name");
        jLabelPharmacyName.setName("jLabelPharmacyName");

        jLabelLatitude = new JLabel("", JLabel.LEFT);
        jLabelLatitude.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelLatitude.setForeground(Color.black);
        jLabelLatitude.setText("Latitude");
        jLabelLatitude.setName("jLabelLatitude");

        jLabelLongitude = new JLabel("", JLabel.LEFT);
        jLabelLongitude.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelLongitude.setForeground(Color.black);
        jLabelLongitude.setText("Longitude");
        jLabelLongitude.setName("jLabelLongitude");

        jLabelPhone = new JLabel("", JLabel.LEFT);
        jLabelPhone.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelPhone.setForeground(Color.black);
        jLabelPhone.setText("Phone");
        jLabelPhone.setName("jLabelPhone");

        jLabelEmail = new JLabel("", JLabel.LEFT);
        jLabelEmail.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelEmail.setForeground(Color.black);
        jLabelEmail.setText("EMail");
        jLabelEmail.setName("jLabelEmail");

        jLabelAddress = new JLabel("", JLabel.LEFT);
        jLabelAddress.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelAddress.setForeground(Color.black);
        jLabelAddress.setText("Address");
        jLabelAddress.setName("jLabelAddress");

        jLabelWarning = new JLabel("", JLabel.CENTER);
        jLabelWarning.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelWarning.setForeground(Color.red);
        jLabelWarning.setName("jLabelWarning");
        
        jLabelOnDuty = new JLabel("", JLabel.LEFT);
        jLabelOnDuty.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelOnDuty.setForeground(Color.black);
        jLabelOnDuty.setText("On Duty?");
        jLabelOnDuty.setName("jLabelOnDuty");
//################TEXT FIELDS######################
        jTextFieldPharmacyName = new JTextField(1);
        jTextFieldPharmacyName.setDocument(new JTextFieldLimit(40));
        jTextFieldPharmacyName.setName("jTextFieldPharmacyName");
        jTextFieldPharmacyName.setFont(new Font("Arial", Font.BOLD, 14));
        
        jTextFieldSearchBox = new JTextField(1);
        jTextFieldSearchBox.setDocument(new JTextFieldLimit(40));
        jTextFieldSearchBox.setName("jTextFieldSearchBox");
        jTextFieldSearchBox.setFont(new Font("Arial", Font.BOLD, 14));
        
        jTextFieldLatitude = new JTextField(1);
        jTextFieldLatitude.setDocument(new JTextFieldLimit(40));
        jTextFieldLatitude.setName("jTextFieldLatitude");
        jTextFieldLatitude.setFont(new Font("Arial", Font.BOLD, 14));
        jTextFieldLongitude = new JTextField(1);
        jTextFieldLongitude.setDocument(new JTextFieldLimit(40));
        jTextFieldLongitude.setName("jTextFieldLongitude");
        jTextFieldLongitude.setFont(new Font("Arial", Font.BOLD, 14));
        jFormattedTextFieldPhone = new JFormattedTextField(createFormatter("(###)###-####"));
        jFormattedTextFieldPhone.setName("tPhone");
        jFormattedTextFieldPhone.setFont(new Font("Arial", Font.BOLD, 14));
        jTextFieldEmail = new JTextField(1);
        jTextFieldEmail.setDocument(new JTextFieldLimit(40));
        jTextFieldEmail.setName("tEmail");
        jTextFieldEmail.setFont(new Font("Arial", Font.BOLD, 14));
        jTextAreaAddress = new JTextArea();
        jTextAreaAddress.setDocument(new JTextFieldLimit(500));
        jTextAreaAddress.setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane areaScrollPane = new JScrollPane(jTextAreaAddress);
        jTextAreaAddress.setName("tAddress");
        jCheckBoxOnDuty = new JCheckBox();
//################BUTTONS######################
        jButtonFirst = new JButton();
        jButtonFirst.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonFirst.setText("<<");
        jButtonFirst.setName("jButtonFirst");

        jButtonPrev = new JButton();
        jButtonPrev.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonPrev.setText("<-");
        jButtonPrev.setName("jButtonPrev");

        jButtonNext = new JButton();
        jButtonNext.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonNext.setText("->");
        jButtonNext.setName("jButtonNext");

        jButtonLast = new JButton();
        jButtonLast.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonLast.setText(">>");
        jButtonLast.setName("jButtonLast");

        jButtonDelete = new JButton();
        jButtonDelete.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonDelete.setText("Delete");
        jButtonDelete.setName("jButtonDelete");

        jButtonSave = new JButton();
        jButtonSave.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonSave.setText("Save");
        jButtonSave.setName("jButtonSave");

        jButtonUpdate = new JButton();
        jButtonUpdate.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonUpdate.setText("Update");
        jButtonUpdate.setName("jButtonUpdate");

        jButtonSearch = new JButton();
        jButtonSearch.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonSearch.setText("Search");
        jButtonSearch.setName("jButtonSearch");

        jButtonNew = new JButton();
        jButtonNew.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonNew.setText("New");
        jButtonNew.setName("jButtonNew");

        jButtonReport = new JButton();
        jButtonReport.setFont(new Font("Arial", Font.BOLD, 14));
        jButtonReport.setText("Report");
        jButtonReport.setName("jButtonReport");
//################PANELS###################### 
        jPanelForjLabels = new JPanel(new GridLayout(7, 1, 5, 5)); //for textfield labels
        jPanelForjLabels.setBackground(bgcolor);
        jPanelForjLabels.add(jLabelOnDuty);
        jPanelForjLabels.add(jLabelPharmacyName);
        jPanelForjLabels.add(jLabelLatitude);
        jPanelForjLabels.add(jLabelLongitude);
        jPanelForjLabels.add(jLabelPhone);
        jPanelForjLabels.add(jLabelEmail);
        jPanelForjLabels.add(jLabelAddress);
        
        jPanelForjTextFields = new JPanel(new GridLayout(7, 1, 5, 5)); //for textfields
        jPanelForjTextFields.setBackground(bgcolor);
        jPanelForjTextFields.add(jCheckBoxOnDuty);
        jPanelForjTextFields.add(jTextFieldPharmacyName);
        jPanelForjTextFields.add(jTextFieldLatitude);
        jPanelForjTextFields.add(jTextFieldLongitude);
        jPanelForjTextFields.add(jFormattedTextFieldPhone);
        jPanelForjTextFields.add(jTextFieldEmail);
        jPanelForjTextFields.add(areaScrollPane);
        
        
        jPanelForActionButtons = new JPanel(new GridLayout(1, 7, 1, 1)); //for action buttons
        jPanelForActionButtons.setBackground(bgcolor);
        jPanelForActionButtons.add(jButtonNew);
        jPanelForActionButtons.add(jButtonSave);
        jPanelForActionButtons.add(jButtonUpdate);
        jPanelForActionButtons.add(jButtonDelete);
        jPanelForActionButtons.add(jButtonReport);        
        jPanelForActionButtons.add(jTextFieldSearchBox);
        jPanelForActionButtons.add(jButtonSearch);
        
        
        jPanelForNavigationButtons = new JPanel(new GridLayout(1, 1, 0, 0)); //for navigation buttons
        jPanelForNavigationButtons.setBackground(bgcolor);
        jPanelForWarningLabel = new JPanel(new GridLayout(1, 1, 5, 5)); //for warning label
        jPanelForWarningLabel.setBackground(bgcolor);
        
        jPanelForNavigationButtons.add(jButtonFirst);
        jPanelForNavigationButtons.add(jButtonPrev);
        jPanelForNavigationButtons.add(jButtonNext);
        jPanelForNavigationButtons.add(jButtonLast);
        jPanelForWarningLabel.add(jLabelWarning);
        
        imagePanelForQRCode = new ImagePanel();
        imagePanelForQRCode.setBackground(bgcolor);
        imagePanelForGoogleMaps = new ImagePanel();
        imagePanelForGoogleMaps.setBackground(bgcolor);
        imagePanelForGoogleMaps.setImage("image.jpg"); 
        
        JPanel jPanelForImages = new JPanel(new GridLayout(2, 1, 5, 5));
        jPanelForImages.add(imagePanelForQRCode);
        jPanelForImages.add(imagePanelForGoogleMaps);
        
        panel6 = new JPanel(new BorderLayout(5, 5)); //panel1+panel2+panel3+panel7
        panel6.setBackground(bgcolor);
        panel7 = new JPanel(new GridLayout(2, 1, 5, 5)); //panel4+panel5
        panel7.setBackground(bgcolor);
        panel7.add(jPanelForNavigationButtons);
        panel7.add(jPanelForWarningLabel);
        panel6.add(jPanelForjLabels, BorderLayout.WEST);
        panel6.add(jPanelForImages, BorderLayout.EAST);
        panel6.add(jPanelForjTextFields, BorderLayout.CENTER);
        panel6.add(jPanelForActionButtons, BorderLayout.NORTH);
        panel6.add(panel7, BorderLayout.PAGE_END);
        add(panel6);

        pack();
    }
//#########################!!!ACTIONS!!!##############################

    private void btnAction() {
        jCheckBoxOnDuty.addActionListener(
                new ActionListener() {
        
            @Override
                    public void actionPerformed(ActionEvent e) {
                        if(jTextFieldPharmacyName.getText().equals("") || 
                                jTextFieldLatitude.getText().equals("") || 
                                jTextFieldLongitude.getText().equals("") || 
                                jFormattedTextFieldPhone.getText().equals("") || 
                                jTextFieldEmail.getText().equals("") || 
                                jTextAreaAddress.getText().equals(""))
                        {
                            System.out.println("[INFO]: Boş kayıt için nöbetçi işlemi seçildi.");
                            return;
                        }
                        try {
                            if (Integer.parseInt(resultSet.getString("OnDuty"))==1) {
                                System.out.println("[INFO]: Kayıt nöbetçi değil olarak işaretlendi");
                                resultSet.updateString("OnDuty", "0");
                                resultSet.updateRow();
                                conn.commit();
                                resultSet.close();
                                statement.close();
                                statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                String sql2 = "select * from Pharmacies where Pharmacy_Name='" + jTextFieldPharmacyName.getText() + "'";
                                resultSet = statement.executeQuery(sql2);
                                resultSet.first();
                                setTextFields();
                            } else {
                                System.out.println("[INFO]: Kayıt nöbetçi olarak işaretlendi");
                                resultSet.updateString("OnDuty", "1");
                                resultSet.updateRow();
                                conn.commit();
                                resultSet.close();
                                statement.close();
                                statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                String sql2 = "select * from Pharmacies where Pharmacy_Name='" + jTextFieldPharmacyName.getText() + "'";
                                resultSet = statement.executeQuery(sql2);
                                resultSet.first();
                                setTextFields();
                            }
                        } catch (SQLException ex) {
                            System.out.println("[WARN]: "+ex.getMessage());
                        }
                    }
        }
        );
        // EXIT
        jMenuItemExit.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
        // ABOUT
        jMenuItemAbout.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("[INFO]: About Box açılıyor...");
                        new aboutBox();
                        System.out.println("[INFO]: About Box açıldı.");
                    }
                });
        // NEW
        jMenuItemNew.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("[INFO]: Yeni kayıt için alanlar temizlendi.");
                        jLabelWarning.setText("New record");
                        setTextFieldsEmpty();
                        File temporaryQR = new File("images/temporaryQR.png");
                        if (!temporaryQR.exists()) {
                            defaultQRCode();
                        }
                        imagePanelForQRCode.flush();
                        imagePanelForQRCode.setImage("images/temporaryQR.png");
                        imagePanelForGoogleMaps.flush();
                        imagePanelForGoogleMaps.setImage("images/mapImage.jpg");
                    }
                });
        // NEW
        jButtonNew.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("[INFO]: Yeni kayıt için alanlar temizlendi.");
                        jLabelWarning.setText("New record");
                        setTextFieldsEmpty();
                        File temporaryQR = new File("images/temporaryQR.png");
                        if (!temporaryQR.exists()) {
                            defaultQRCode();
                        }
                        imagePanelForQRCode.flush();
                        imagePanelForQRCode.setImage("images/temporaryQR.png");
                        imagePanelForGoogleMaps.flush();
                        imagePanelForGoogleMaps.setImage("images/mapImage.jpg");
                    }
                });
        // NEXT
        jButtonNext.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (resultSet.next()) {
                                System.out.println("[INFO]: Sıradaki kayıt");
                                setTextFields();
                                File qr = new File("images/" + resultSet.getString("QR") + ".png");
                                if (!qr.exists()) {
                                    System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                                    createQRback();
                                    System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
                                }
                                imagePanelForQRCode.flush();
                                imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
                            } else {
                                resultSet.previous();
                                System.out.println("[INFO]: Başka kayıt yok.");
                                jLabelWarning.setText("No more records found!");
                            }
                        } catch (Exception ex) {
                            System.out.println("[WARNING]: Next butonu kısmında bir hata var.");
                            System.out.println("[WARNING]: " + ex.getMessage());
                        }
                    }
                });
        // FIRST
        jButtonFirst.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            statement.close();
                            resultSet.close();
                            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            resultSet = statement.executeQuery("select * from Pharmacies");
                            resultSet.first();
                            System.out.println("[INFO]: İlk kayıt");
                            setTextFields();
                            File qr = new File("images/" + resultSet.getString("QR") + ".png");
                            if (!qr.exists()) {
                                System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                                createQRback();
                                System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
                            }
                            imagePanelForQRCode.flush();
                            imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
                            jLabelWarning.setText("First record");
                        } catch (Exception ex) {
                            System.out.println("[WARNING]: First butonunda hata var.");
                            System.out.println("[WARNING]: " + ex.getMessage());
                            jLabelWarning.setText("No more records found!");
                        }
                    }
                });
        // PREVIOUS
        jButtonPrev.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (resultSet.previous()) {
                                System.out.println("[INFO]: Önceki kayıt");
                                setTextFields();
                                File qr = new File("images/" + resultSet.getString("QR") + ".png");
                                if (!qr.exists()) {
                                    System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                                    createQRback();
                                    System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
                                }
                                imagePanelForQRCode.flush();
                                imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
                            } else {
                                resultSet.next();
                                System.out.println("[INFO]: Başka kayıt yok.");
                                jLabelWarning.setText("No more records found!");
                            }
                        } catch (Exception ex) {
                            System.out.println("[WARNING]: Prev butonunda hata var.");
                            System.out.println("[WARNING]: " + ex.getMessage());
                        }
                    }
                });
        // LAST
        jButtonLast.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            statement.close();
                            resultSet.close();
                            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            resultSet = statement.executeQuery("select * from Pharmacies");
                            resultSet.last();
                            System.out.println("[INFO]: Son kayıt");
                            setTextFields();
                            File qr = new File("images/" + resultSet.getString("QR") + ".png");
                            if (!qr.exists()) {
                                System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                                createQRback();
                                System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
                            }
                            imagePanelForQRCode.flush();
                            imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
                            jLabelWarning.setText("Last record!");
                        } catch (Exception ex) {
                            System.out.println("[WARNING]: Last butonunda hata var");
                            System.out.println("[WARNING]: " + ex.getMessage());
                            jLabelWarning.setText("No more records found!");
                        }
                    }
                });
        // UPDATE
        jButtonUpdate.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String pharmacyName = jTextFieldPharmacyName.getText();
                        String latitude = jTextFieldLatitude.getText();
                        String longitude = jTextFieldLongitude.getText();
                        String phone = jFormattedTextFieldPhone.getText();
                        String email = jTextFieldEmail.getText();
                        String address = jTextAreaAddress.getText();
                        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
                        Matcher matcher = pattern.matcher(email);
                        boolean matchFound = matcher.matches();
                        if (!email.equals("")) {
                            if (!matchFound) {
                                jLabelWarning.setText("Wrong email!");
                                return;
                            }
                        }
                        if (pharmacyName.isEmpty()) {
                            jLabelWarning.setText("You can not update a blank record!");
                            return;
                        }
                        try {
                            String phoneCheck = "";
                            for (int i = 0; i < phone.length(); i++) {
                                if (Character.isDigit(phone.charAt(i))) {
                                    phoneCheck += phone.charAt(i);
                                }
                            }
                            phone = phoneCheck;
                            resultSet.moveToCurrentRow();
                            qrInfoArray.add(pharmacyName);
                            qrInfoArray.add(latitude);
                            qrInfoArray.add(longitude);
                            qrInfoArray.add(phone);
                            qrInfoArray.add(email);
                            qrInfoArray.add(address);
                            updateQR(qrInfoArray, resultSet.getString("QR"));
                            qrInfoArray.clear();
                            resultSet.updateString("Pharmacy_Name", pharmacyName);
                            resultSet.updateString("Latitude", latitude);
                            resultSet.updateString("Longitude", longitude);
                            resultSet.updateString("Phone", phone);
                            resultSet.updateString("Email", email);
                            resultSet.updateString("Address", address);
                            resultSet.updateRow();
                            conn.commit();
                            resultSet.close();
                            statement.close();
                            System.out.println("[INFO]: Kayıt yenileniyor...");
                            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            String sql2 = "select * from Pharmacies where Pharmacy_Name='" + pharmacyName + "'";
                            resultSet = statement.executeQuery(sql2);
                            resultSet.first();
                            setTextFields();
                            File qr = new File("images/" + resultSet.getString("QR") + ".png");
                            if (!qr.exists()) {
                                System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                                createQRback();
                                System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
                            }
                            imagePanelForQRCode.flush();
                            imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
                            setTextFields();
                            System.out.println("[INFO]: Kayıt yenilendi.");
                            jLabelWarning.setText("Updated!");
                        } catch (Exception ex) {
                            System.out.println("[WARNING]: Update butonunda hata var.");
                            System.out.println("[WARNING]: " + ex.getMessage());
                            jLabelWarning.setText("Huh!?");
                        }
                    }
                });
        // DELETE
        jButtonDelete.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String Pharmacy_Name = jTextFieldPharmacyName.getText();
                        if (Pharmacy_Name.isEmpty()) {
                            jLabelWarning.setText("You can not delete a blank record!");
                            return;
                        } else {
                            try {
                                statement.close();
                                resultSet.close();
                                statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                String sql = "select * from Pharmacies where Pharmacy_Name='" + Pharmacy_Name + "'";
                                resultSet = statement.executeQuery(sql);
                                if (!resultSet.first()) {
                                    jLabelWarning.setText("Huh!?");
                                    statement.close();
                                    resultSet.close();
                                    statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                    String sql2 = "select * from Pharmacies";
                                    resultSet = statement.executeQuery(sql2);
                                    resultSet.first();
                                    imagePanelForGoogleMaps.flush();
                                    setTextFields();
                                    File qr = new File("images/" + resultSet.getString("QR") + ".png");
                                    if (!qr.exists()) {
                                        System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                                        createQRback();
                                        System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
                                    }
                                    imagePanelForQRCode.flush();
                                    imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
                                    return;
                                } else {
                                    int onay = JOptionPane.showConfirmDialog(null, "Are you sure?", "Careful!", JOptionPane.YES_NO_OPTION);
                                    if (onay == 0) {
                                        File f = new File("images/" + resultSet.getString("QR") + ".png");
                                        f.delete();
                                        resultSet.deleteRow();
                                        statement.close();
                                        resultSet.close();
                                        conn.commit();
                                        jLabelWarning.setText("Deleted!");
                                        statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                        String sql2 = "select * from Pharmacies";
                                        resultSet = statement.executeQuery(sql2);
                                        if (resultSet.next()) {
                                            setTextFields();
                                            File qr = new File("images/" + resultSet.getString("QR") + ".png");
                                            if (!qr.exists()) {
                                                System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                                                createQRback();
                                                System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
                                            }
                                            imagePanelForQRCode.flush();
                                            imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
                                        } else {
                                            setTextFieldsEmpty();
                                            jLabelWarning.setText("Database is empty!");
                                            File temporaryQR = new File("images/temporaryQR.png");
                                            if (!temporaryQR.exists()) {
                                                defaultQRCode();
                                            }
                                            imagePanelForQRCode.flush();
                                            imagePanelForQRCode.setImage("images/temporaryQR.png");
                                        }
                                    } else if (onay == 1 || onay == -1) {
                                        resultSet.first();
                                        jLabelWarning.setText("Cancelled!");
                                        return;
                                    }
                                }
                            } catch (Exception ex) {
                                System.out.println("[WARNING]: Delete butonunda hata var.");
                                System.out.println("[WARNING]: " + ex.getMessage());
                            }

                        }
                    }
                });
        // SAVE
        jButtonSave.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("[INFO]: Kayıt işlemi başladı...");
                        String stringPharmacyName = jTextFieldPharmacyName.getText();
                        String stringLatitude = jTextFieldLatitude.getText();
                        String stringLongitude = jTextFieldLongitude.getText();
                        String stringPhone = jFormattedTextFieldPhone.getText();
                        String stringEmail = jTextFieldEmail.getText();
                        String stringAddress = jTextAreaAddress.getText();
                        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
                        Matcher matcher = pattern.matcher(stringEmail);
                        boolean matchFound = matcher.matches();
                        String phoneCheck = "";

                        for (int i = 0; i < stringPhone.length(); i++) {
                            if (Character.isDigit(stringPhone.charAt(i))) {
                                phoneCheck += stringPhone.charAt(i);
                            }
                        }

                        if (stringPharmacyName.isEmpty()) {
                            jLabelWarning.setText("You must enter the Pharmacy Name!");
                            System.out.println("[WARNING]: Eczane ismi girilmedi. Kayıt durduruldu.");
                            return;
                        }
                        if (phoneCheck.length() < 10) {
                            jLabelWarning.setText("Wrong phone number!");
                            System.out.println("[WARNING]: Telefon yanlış girildi. Kayıt durduruldu.");
                            return;
                        }
                        if (!stringEmail.equals("")) {
                            if (!matchFound) {
                                jLabelWarning.setText("Wrong email!");
                                System.out.println("[WARNING]: Email yanlış girildi. Kayıt durduruldu.");
                                return;
                            }
                        }
                        try {
                            stringPhone = phoneCheck;
                            statement.close();
                            resultSet.close();
                            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            String sql = "select * from Pharmacies where Pharmacy_Name='" + stringPharmacyName + "'";
                            resultSet = statement.executeQuery(sql);
                            if (resultSet.first()) {
                                File qr = new File("images/" + resultSet.getString("QR") + ".png");
                                if (!qr.exists()) {
                                    System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                                    createQRback();
                                    System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
                                }
                                imagePanelForQRCode.flush();
                                imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
                                jLabelWarning.setText("This record already exists!");
                                System.out.println("[WARNING]: Kayıt varmış, işlem iptal.");
                                return;
                            } else {
                                int onay = JOptionPane.showConfirmDialog(null, "Are you sure?", "Saving Facility v 1.0", JOptionPane.YES_NO_OPTION);
                                if (onay == 0) {
                                    try {
                                        qrInfoArray.add(stringPharmacyName);
                                        qrInfoArray.add(stringLatitude);
                                        qrInfoArray.add(stringLongitude);
                                        qrInfoArray.add(stringPhone);
                                        qrInfoArray.add(stringEmail);
                                        qrInfoArray.add(stringAddress);
                                        String qrName = createQR(qrInfoArray);
                                        qrInfoArray.clear();
                                        resultSet.moveToInsertRow();
                                        resultSet.updateString("Pharmacy_Name", stringPharmacyName);
                                        resultSet.updateString("latitude", stringLatitude);
                                        resultSet.updateString("longitude", stringLongitude);
                                        resultSet.updateString("Phone", stringPhone);
                                        resultSet.updateString("Email", stringEmail);
                                        resultSet.updateString("Address", stringAddress);
                                        resultSet.updateString("QR", qrName);
                                        resultSet.insertRow();
                                        conn.commit();
                                        statement.close();
                                        resultSet.close();
                                        statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                        String sql2 = "select * from Pharmacies";
                                        resultSet = statement.executeQuery(sql2);
                                        jLabelWarning.setText("Done!");
                                        resultSet.last();
                                        File qr = new File("images/" + resultSet.getString("QR") + ".png");
                                        if (!qr.exists()) {
                                            System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                                            createQRback();
                                            System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
                                        }
                                        imagePanelForQRCode.flush();
                                        imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
                                        setTextFields();
                                        System.out.println("[INFO]: Kayıt tamamlandı.");

                                    } catch (Exception ex) {
                                        System.out.println("[WARNING]: Kayıt yapılamadı.");
                                        JOptionPane.showMessageDialog(null, ex);
                                    }
                                } else if (onay == 1 || onay == -1) {
                                    jLabelWarning.setText("Cancelled..");
                                    return;
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("[WARNING]: Save butonunda hata var.");
                            System.out.println("[WARNING]: " + ex.getMessage());
                        }
                    }
                });
        // SEARCH
        jButtonSearch.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new Search();
                    }
                });
        // REPORT
        jButtonReport.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            new dbReport();
                        } catch (Exception ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
    }

    private void defaultQRCode() {
        ByteArrayOutputStream out = QRCode.from("Pharmacies on Duty\nMarmara University\nComputer Engineering\n").withSize(300, 300).to(ImageType.PNG).stream();
        try {
            File klasor = new File("images");
            if (!klasor.exists()) {
                (new File("images")).mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(new File("images/temporaryQR.png"));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "temporaryQR.png olusturulamadi");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "temporaryQR.png olusturulamadi");
        }
    }

    private String createQR(ArrayList<String> myArr) {
        String qr = myArr.get(0) + " " + myArr.get(1) + "\n" + myArr.get(2) + 
                "\n" + myArr.get(3) + "\n" + myArr.get(4) + "\n" + myArr.get(5);
        String link = "";
        Calendar cal = Calendar.getInstance();
        String day = String.valueOf(cal.get(Calendar.DATE));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String hour = String.valueOf(cal.get(Calendar.HOUR));
        String minute = String.valueOf(cal.get(Calendar.MINUTE) + 1);
        String second = String.valueOf(cal.get(Calendar.SECOND));
        link = day + month + year + hour + minute + second;

        ByteArrayOutputStream out = QRCode.from(qr).withSize(300, 300).to(ImageType.PNG).stream();
        try {
            File klasor = new File("images");
            if (!klasor.exists()) {
                (new File("images")).mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(new File("images/" + link + ".png"));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Yeni QR olusturulamadi");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Yeni QR olusturulamadi");
        }
        return link;
    }

    private void updateQR(ArrayList<String> myArr, String link) {
        String qr = myArr.get(0) + " " + myArr.get(1) + "\n" + myArr.get(2) + 
                "\n" + myArr.get(3) + "\n" + myArr.get(4) + "\n" + myArr.get(5);
        ByteArrayOutputStream out = QRCode.from(qr).withSize(300, 300).to(ImageType.PNG).stream();
        try {
            File klasor = new File("images");
            if (!klasor.exists()) {
                (new File("images")).mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(new File("images/" + link + ".png"));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "QR update edilemedi");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "QR update edilemedi");
        }
    }

    private void createQRback() throws SQLException {
        qrInfoArray.add(resultSet.getString("Pharmacy_Name"));
        qrInfoArray.add(resultSet.getString("Latitude"));
        qrInfoArray.add(resultSet.getString("Longitude"));
        qrInfoArray.add(resultSet.getString("Email"));
        qrInfoArray.add(resultSet.getString("Phone"));
        qrInfoArray.add(resultSet.getString("Address"));

        String qr = qrInfoArray.get(0) + " " + qrInfoArray.get(1) + "\n"
                + qrInfoArray.get(2) + "\n" + qrInfoArray.get(3) + "\n" + qrInfoArray.get(4);

        ByteArrayOutputStream out = QRCode.from(qr).withSize(300, 300).to(ImageType.PNG).stream();
        try {
            File klasor = new File("images");
            if (!klasor.exists()) {
                (new File("images")).mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(new File("images/" + resultSet.getString("QR") + ".png"));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "QR olusturulamadi");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "QR olusturulamadi");
        }
        qrInfoArray.clear();
    }

    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setPlaceholderCharacter('_');
        } catch (java.text.ParseException exc) {
            System.err.println("[INFO]: Formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    /**
     * İlk kayıt işlemleri. İlk kaydın QR Code'u oluşturuluyor. Kayıtlar
     * sayılıyor ve Warning Label'da belirtiliyor. İlk kayıt yoksa yer tutucu QR
     * Code panele ekleniyor.
     */
    private void initFirstRecord() {
        try {
            int count = 0;
            resultSet.first();
            setTextFields();
            File qr = new File("images/" + resultSet.getString("QR") + ".png");
            if (!qr.exists()) {
                System.out.println("[WARNING]: Kaydın QR Code'u silinmiş. Yenisi oluşturuluyor.");
                createQRback();
                System.out.println("[INFO]: Kaydın QR Code'u oluşturuldu.");
            }
            imagePanelForQRCode.setImage("images/" + resultSet.getString("QR") + ".png");
            resultSet.close();
            statement.close();
            System.out.println("[INFO]: Kayıtlar sayılıyor...");
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select count(*) from Pharmacies";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            String s = "";
            if (count > 1) {
                s = "s";
            }
            jLabelWarning.setText("Welcome! You've got " + count + " record" + s);
            resultSet.close();
            statement.close();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql2 = "select * from Pharmacies";
            resultSet = statement.executeQuery(sql2);
            resultSet.first();
            jLabelWarning.setFont(new Font("Arial", Font.BOLD, 14));
            jLabelWarning.setForeground(Color.red);
        } catch (Exception ex) {
            System.out.println("[INFO]: İlk kayıt bulunamadı...");
            jLabelWarning.setText("Welcome!");
            jLabelWarning.setFont(new Font("Arial", Font.BOLD, 14));
            File temporaryQR = new File("images/temporaryQR.png");
            if (!temporaryQR.exists()) {
                System.out.println("[INFO]: Yer tutucu QR Code oluşturuluyor...");
                defaultQRCode();
            }
            imagePanelForQRCode.setImage("images/temporaryQR.png");
            System.out.println("[INFO]: Yer tutucu QR Code oluşturuldu ve panele eklendi.");
        }                
    }

    private void setTextFields() throws SQLException {
        if(Integer.parseInt(resultSet.getString("OnDuty"))==1)
        {
            jCheckBoxOnDuty.setSelected(true);
        }
        else
        {
            jCheckBoxOnDuty.setSelected(false);
        }
        jTextFieldPharmacyName.setText(resultSet.getString("Pharmacy_Name"));
        jTextFieldLatitude.setText(resultSet.getString("Latitude"));
        jTextFieldLongitude.setText(resultSet.getString("Longitude"));
        jFormattedTextFieldPhone.setText(resultSet.getString("Phone"));
        jTextFieldEmail.setText(resultSet.getString("Email"));
        jTextAreaAddress.setText(resultSet.getString("Address"));
        String googleMapsImage = setGoogleMaps(resultSet.getString("Latitude"), resultSet.getString("Longitude"));
        imagePanelForGoogleMaps.flush();
        imagePanelForGoogleMaps.setImage(googleMapsImage);
    }

    private void setTextFieldsEmpty() {
        jCheckBoxOnDuty.setSelected(false);
        jTextFieldPharmacyName.setText("");
        jTextFieldLatitude.setText("");
        jTextFieldLongitude.setText("");
        jFormattedTextFieldPhone.setText("");
        jTextFieldEmail.setText("");
        jTextAreaAddress.setText("");
    }
    
    private String setGoogleMaps(String lat, String lng)
    {
        File mapImage = new File("images/" + lat + lng + ".png");
            if (mapImage.exists())
            {
                System.out.println("[INFO]: Harita resmi zaten var. Yenisi oluşturulmadı.");
                return "images/"+lat+lng+".png";
            }
        try {
            String imageUrl = "http://maps.googleapis.com/maps/api/staticmap?center="+lat+","+lng+"&zoom=16&size=300x300&maptype=satellite&markers=color:red|label:E|"+lat+","+lng+"&sensor=false&format=png";
            String destinationFile = "images/"+lat+lng+".png";
            System.out.println("[INFO]: Destination image is "+destinationFile);
            String str = destinationFile;
            URL url2 = new URL(imageUrl);
            InputStream is = url2.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            System.out.println("[WARN]: "+e.getMessage());
        }
        return ("images/"+lat+lng+".png");
    }
}
