package com.akifbatur.pod;

/*
 @author PoD Team
 * Marmara University
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;

public class dbConnection
{

    private final String framework = "embedded";
    private final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private final String protocol = "jdbc:derby:";
    static Statement st = null;
    static ResultSet rs = null;
    static Connection conn = null;
    private ArrayList statements = new ArrayList();
    private Properties props = new Properties();
    private String dbName = "dbPoD";

    public dbConnection()
    {
        loadDriver();
        try
        {
            props.put("user", "");
            props.put("password", "");
            conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
            conn.setAutoCommit(false);
            st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statements.add(st);
            try
            {
                //Pharmacies var mi, yok mu?
                System.out.println("[INFO]: Pharmacies tablosu var mı yok mu bakılıyor....");
                String sql = "select * from Pharmacies";
                rs = st.executeQuery(sql);
                System.out.println("[INFO]: Pharmacies tablosu varmış.");
                System.out.println("[INFO]: Arayüz oluşturmaya devam ediliyor...");
            } catch (Exception ex)
            {
                //Pharmacies yoksa bir tane olustur
                if (!"".equals(ex.getMessage()))
                {
                    //Pharmacy_Name
                    //Latitude
                    //Longitude
                    //Phone
                    //Email
                    //Address
                    //QR
                    System.out.println("[WARNING]: Pharmacies tablosu yokmuş. Yeni bir tane oluşturuluyor.");
                    st.executeUpdate("create table Pharmacies(Pharmacy_Name varchar(40), Latitude varchar(40), Longitude varchar(40), Phone varchar(20), Email varchar(70), Address long varchar, QR varchar(70), OnDuty varchar(1) DEFAULT '0')");
                    conn.commit();
                    System.out.println("[INFO]: Pharmacies tablosu oluşturuldu.");
                }
            }
            conn.commit();
            String sql = "select * from Pharmacies";
            rs = st.executeQuery(sql);
        } catch (Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Programı çalıştırdığını zannettin ama zaten çalışıyordu hatası!");
            System.exit(0);
        }
    }

    public ResultSet getRs()
    {
        return rs;
    }

    public Statement getSt()
    {
        return st;
    }

    public Connection getConn()
    {
        return conn;
    }

    public void loadDriver()
    {
        try
        {
            Class.forName(driver).newInstance();
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "[WARNING]: Bir seyler oldugunu sandin ama olmadi hatasi. Library'lerden eksik olabilir.");
            System.exit(0);
        }
    }
}
