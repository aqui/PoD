package com.akifbatur.pod;

/*
 @author PoD Team
 * Marmara University
 */

import com.itextpdf.text.BaseColor;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class dbReport
{
    public static final String RESULT = "Report.pdf";
    static Paragraph preface = new Paragraph();
    static String tab = "                ";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    private static Font smallPlain = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

    dbReport() throws DocumentException, IOException
    {
        try
        {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph(tab + tab + tab + tab + tab + tab + tab + "Pharmacies on Duty Report Page", smallBold));
            document.add(new Paragraph(" "));
            PdfPTable titleTable = new PdfPTable(2);
            titleTable.setWidthPercentage(100);
            PdfPCell cell1 = new PdfPCell(new Phrase("Pharmacy", smallBold));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cell1);
            PdfPCell cellc = new PdfPCell(new Phrase("QR Code", smallBold));
            cellc.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellc);
            dbConnection.st = dbConnection.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select * from Pharmacies where OnDuty='1'";
            dbConnection.rs = dbConnection.st.executeQuery(sql);
            int i = 1;
            while (dbConnection.rs.next())
            {
                String pharmacyInfo = "";
                pharmacyInfo += "Pharmacy Name: "+dbConnection.rs.getString(1);
                pharmacyInfo += "\n";
                pharmacyInfo += "Latitude: "+dbConnection.rs.getString(2);
                pharmacyInfo += "\n";
                pharmacyInfo += "Longitude: "+dbConnection.rs.getString(3);
                pharmacyInfo += "\n";
                pharmacyInfo += "Phone: "+dbConnection.rs.getString(4);
                pharmacyInfo += "\n";
                pharmacyInfo += "Email: "+dbConnection.rs.getString(5);
                pharmacyInfo += "\n";
                pharmacyInfo += "Address: "+dbConnection.rs.getString(6);
                pharmacyInfo += "\n";
                
                pharmacyInfo += "Link: https://maps.google.com/maps?q=description+("+dbConnection.rs.getString(1).replaceAll("\\s","")+")@"+dbConnection.rs.getString(2)+"%2C"+dbConnection.rs.getString(3);
                //pharmacyInfo += "Link: https://maps.google.com/maps?q="+dbConnection.rs.getString(2)+"N+"+dbConnection.rs.getString(3)+"W+("+dbConnection.rs.getString(1)+")&ll="+dbConnection.rs.getString(2)+","+dbConnection.rs.getString(3)+"&spn=0.004250,0.011579&t=h&iwloc=A&hl=en";
                PdfPCell cell6 = new PdfPCell(new Phrase(pharmacyInfo, smallPlain));
                titleTable.addCell(cell6);
                BarcodeQRCode qrcode = new BarcodeQRCode(pharmacyInfo, 1, 1, null);
                Image img = qrcode.getImage();
                titleTable.addCell(img);
                i++;
            }

            document.add(titleTable);
            document.close();
            FileOutputStream fos = new FileOutputStream(RESULT);
            fos.write(baos.toByteArray());
            fos.close();
            try
            {

                File pdfFile = new File("Report.pdf");
                if (pdfFile.exists())
                {
                    if (Desktop.isDesktopSupported())
                    {
                        try
                        {
                           Desktop.getDesktop().open(pdfFile);
                        }catch(Exception ex)
                        {
                           //linux desteklemeyecegi icin, bunu bunu alin buradan...
                           MainFrame.jLabelWarning.setText("Report page is ready to use. Open up from ~");
                        }
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "Awt Desktop is not supported!");
                    }

                } else
                {
                    JOptionPane.showMessageDialog(null, "File is not exist!");
                }

                MainFrame.jLabelWarning.setText("Report page is ready to use.");

            } catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Please close the report page before create a new one!");
            }
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "You should close the report page before create a new one.");
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number)
    {
        for (int i = 0; i < number; i++)
        {
            paragraph.add(new Paragraph(" "));
        }
    }
}
