/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Output;

import Connections.DBConnection;
import RORO.*;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author cmeehan
 */
public class QuotePDf {

    private PdfPCell cell;
    private final String userHome = System.getProperty("user.home"), accessories_accompanying = "Accessories must move with the main unit for the same rate to apply.";
    private final Connection conn = new DBConnection().connect();
    private OutputStream desktopFile, groupFile;
    private JTable packingListTable;
    private String quoteID, date, customerName, company, customerEmail, status, user, pol, pod, tship, commodityClass, cargoHandling, description, oft, mafiMinimum, baf, eca, thc, wfg, doc, wRsk, carrierComments, userInformation, mtdApproval, spaceApproval, overseasResponse;
    private Boolean accessories, spotRate, includeMafiMinimum, includeCarrierRemarks, includeWarRisk;
    private final String groupEmail = "kam-roro-sales@us.kline.com";
    private static final Font labelFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private static final Font tableHeadingFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.WHITE);
    private static final Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    private static final Font emailTextFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.UNDERLINE, BaseColor.BLUE);
    private static final Font rowLabelFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private static final Font subjectToFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLDITALIC);
    private static final Font disclaimerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLDITALIC);
    private static final Font fmcFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.UNDERLINE);
    private static final Font plLabelFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

    private String getUserInformation() {
        String SQL = "SELECT CONCAT(firstName, ' ', lastName) as 'Name' FROM authorized_users WHERE username=?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userInformation = rs.getString("Name");
            }
            return userInformation;
        } catch (Exception ex) {
            System.out.println("Error retrieving user information: " + ex.getMessage());
            return ex.getMessage();
        }
    }

    public void QuotePDF(String quoteID, String date, String customerName, String company, String customerEmail, String status, String user, String pol, String pod, String tship, String commodityClass, String cargoHandling, Boolean accessories, String description, String oft, Boolean includeMafiMinimum, String mafiMinimum, String baf, String eca, String thc, String wfg, String doc, Boolean includeWarRisk, String wRsk, Boolean includeCarrierRemarks, String carrierComments, JTable packingList, Boolean spotRate, String MTDApproval, String spaceApproval, String overseasResponse) throws IOException {

        //
        System.out.println("Executing");
        this.quoteID = quoteID;
        this.date = date;
        this.customerName = customerName;
        this.company = company;
        this.customerEmail = customerEmail;
        this.status = status;
        this.user = user;
        this.pol = pol;
        this.pod = pod;
        this.tship = tship;
        this.commodityClass = commodityClass;
        this.cargoHandling = cargoHandling;
        this.accessories = accessories;
        this.description = description;
        this.oft = oft;
        this.includeMafiMinimum = includeMafiMinimum;
        this.mafiMinimum = mafiMinimum;
        this.baf = baf;
        this.eca = eca;
        this.thc = thc;
        this.wfg = wfg;
        this.doc = doc;
        this.includeWarRisk = includeWarRisk;
        this.wRsk = wRsk;
        this.spotRate = spotRate;
        this.includeCarrierRemarks = includeCarrierRemarks;
        this.carrierComments = carrierComments;
        this.packingListTable = packingList;
        this.mtdApproval = MTDApproval;
        this.spaceApproval = spaceApproval;
        this.overseasResponse = overseasResponse;
        try {
            GeneratePDF();
        } catch (BadElementException | IOException ex) {
            System.out.println("PDF Exception: " + ex.getMessage());
        }
    }

    private void GeneratePDF() throws BadElementException, IOException {
        try {

            desktopFile = new FileOutputStream(new File(userHome + "\\Desktop\\Quotes\\RQS" + quoteID + ".pdf"));//Save the PDF to the user's folder "Quote" on the desktop
            groupFile = new FileOutputStream(new File("S:\\KAM RORO SALES\\Quoting System\\Rates\\" + user + " RQS" + quoteID + ".pdf")); // Saves a copy of the PDF quote to the group S: drive as a backup. 

            //Create a new PDF document
            Document document = new Document();

            //Writes PDF to designated locations
            PdfWriter desktopWriter = PdfWriter.getInstance(document, desktopFile);
            PdfWriter sharedWriter = PdfWriter.getInstance(document, groupFile);

            // Get the user's first and last name and assign to inner variable personnelInformation
            String personnelInformation = getUserInformation();

            // Open the PDF for editing
            document.open();
            document.add(headerImage());
            document.add(shipperInformationTable(personnelInformation));
            document.add(sectionSeparator());
            document.add(commodityTable());
            document.add(sectionSeparator());
            document.add(rateTable());
            document.add(disclaimerTable(spotRate, mtdApproval, spaceApproval, overseasResponse));
            document.add(carrierComments(includeCarrierRemarks, carrierComments));
            //New Page
            document.newPage();
            document.add(packingListTable(packingListTable));

            // Close the document for editing
            document.close();

            try {
                // Close the files for editing
                desktopFile.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                groupFile.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (FileNotFoundException | DocumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Image headerImage() throws BadElementException, IOException {
        Image image = Image.getInstance(getClass().getClassLoader().getResource("resources/header.jpg"));
        image.setAlignment(Element.ALIGN_CENTER);
        image.scalePercent(35f);

        return image;

    }

    private PdfPTable sectionSeparator() {
        PdfPTable separator = new PdfPTable(1);
        separator.setWidthPercentage(100f);
        cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(Rectangle.BOTTOM);
        separator.addCell(cell);
        separator.setSpacingAfter(10f);
        return separator;
    }

    private PdfPTable shipperInformationTable(String personnelInformation) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        float[] columnWidths = new float[]{30f, 70f, 20f, 80f};
        table.setWidths(columnWidths);

        cell = new PdfPCell(new Phrase("Quote ID:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(quoteID, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Date:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(date, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Company:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(company, textFont));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Customer:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(customerName, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Email:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(customerEmail, emailTextFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Quote Type:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(status, textFont));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Quoted By:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(personnelInformation, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Email:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(groupEmail, emailTextFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        return table;
    }

    private PdfPTable commodityTable() throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        float[] columnWidths = new float[]{55f, 70f, 55f, 65f};
        table.setWidths(columnWidths);

        cell = new PdfPCell(new Phrase("Port(s) of Load:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(pol, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("via:", labelFont));
        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(tship, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Port(s) of Discharge:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(pod, textFont));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Commodity:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(commodityClass, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Handling:", labelFont));
        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(cargoHandling, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Accompanying:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        if (accessories) {
            cell = new PdfPCell(new Phrase(accessories_accompanying, textFont));
            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        } else {
            cell = new PdfPCell(new Phrase("N/A", textFont));
            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }

        cell = new PdfPCell(new Phrase("Description", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(description, textFont));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        return table;
    }

    private PdfPTable rateTable() throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        float[] columnWidths = new float[]{35f, 75f};
        table.setWidths(columnWidths);
        table.setSpacingAfter(10f);

        cell = new PdfPCell(new Phrase("Base Ocean Freight:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(oft, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("MAFI Minimum Charge:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        if (includeMafiMinimum) {
            cell = new PdfPCell(new Phrase("$" + mafiMinimum + " per MAFI unit.", textFont));
            cell.setColspan(1);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        } else {
            cell = new PdfPCell(new Phrase("N/A", textFont));
            cell.setColspan(1);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
        cell = new PdfPCell(new Phrase("Subject To:", subjectToFont));
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Bunker Adjustment Factor:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(baf, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("ECA Surcharge:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(eca, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Terminal Handling (Origin):", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(thc, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Wharfage (Origin):", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(wfg, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Documentation Fee:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(doc, textFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("War Risk Surcharge:", labelFont));
        cell.setColspan(1);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        if (includeWarRisk) {
            cell = new PdfPCell(new Phrase(wRsk, textFont));
            cell.setColspan(1);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        } else {
            cell = new PdfPCell(new Phrase("N/A", textFont));
            cell.setColspan(1);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }

        return table;
    }

    private PdfPTable disclaimerTable(Boolean spotrate, String mtdApproval, String spaceApproval, String overseasResponse) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100f);
        table.setSpacingAfter(10f);
        if (spotrate) {
            cell = new PdfPCell(new Phrase("This rate requires special FMC filing. If you intend to book at this rate you must notify sales in writing prior to booking.", fmcFont));
            cell.setBackgroundColor(BaseColor.YELLOW);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
        if (mtdApproval.equals("Pending") || spaceApproval.equals("Pending") || overseasResponse.equals("Pending")) {
            cell = new PdfPCell(new Phrase("Rate is subject to final operational acceptance.", fmcFont));
            cell.setBackgroundColor(BaseColor.YELLOW);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
        cell = new PdfPCell(new Phrase("Rates quoted are valid for 30 days from date of issue unless otherwise noted.", disclaimerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Any extra charges for special handling are for the shipper's account and arrangement.", disclaimerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Static/towable accessories must move with the main unit for the same rate to apply.", disclaimerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Please always include the group email kam-roro-sales@us.kline.com in any correspondence.", disclaimerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Please reference RQS number when booking with customer service (kamricbcarcus@us.kline.com).", disclaimerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        return table;
    }

    private PdfPTable carrierComments(Boolean includeCarrierComments, String carrierComments) {
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidthPercentage(100f);
        if (includeCarrierComments) {
            cell = new PdfPCell(new Phrase("Carrrier Comments", tableHeadingFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingBottom(10f);
            cell.setBackgroundColor(BaseColor.BLACK);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(carrierComments, textFont));
            cell.setPadding(5f);
            table.addCell(cell);
        }
        return table;
    }

    private PdfPTable packingListTable(JTable packingListTable) throws DocumentException {
        PdfPTable table = new PdfPTable(11);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{5, 2, 2, 2, 2, 3, 2, 2, 2, 3, 3});
        table.setSpacingAfter(10f);

        cell = new PdfPCell(new Phrase("Packing List", tableHeadingFont));
        cell.setBackgroundColor(BaseColor.BLACK);
        cell.setColspan(11);
        cell.setPaddingBottom(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Commodity", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Qty", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("L(cm)", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("W(cm)", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("H(cm)", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Kgs", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("L(in)", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("W(in)", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("H(in)", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("M3", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Lbs", plLabelFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        for (int row = 0; row < packingListTable.getRowCount(); row++) {
            for (int col = 0; col < 11; col++) {
                String packingListData = String.valueOf(packingListTable.getValueAt(row, col));
                if (packingListData.equals("null")) {
                    packingListData = " ";
                    cell = new PdfPCell(new Phrase(packingListData, textFont));
                    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);
                } else {
                    cell = new PdfPCell(new Phrase(packingListData, textFont));
                    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);
                }
            }
        }

        return table;
    }

    private PdfPTable sailingScheduleTable(JTable sailingScheduleTable, Boolean includeSailngSchedule) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        if (includeSailngSchedule) {
            cell = new PdfPCell(new Phrase("Sailing Schedule", tableHeadingFont));
            cell.setPaddingBottom(5);
            cell.setBackgroundColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Vessel/Voyage", rowLabelFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Sail Date", rowLabelFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Transit Time", rowLabelFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Transshipment", rowLabelFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            for (int r = 0; r < sailingScheduleTable.getRowCount(); r++) {
                for (int c = 0; c < 4; c++) {
                    Object sailingScheduleData = sailingScheduleTable.getValueAt(r, c);
                    if (sailingScheduleData == null) {
                        sailingScheduleData = " ";
                        cell = new PdfPCell(new Phrase(String.valueOf(sailingScheduleData), textFont));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    } else {
                        cell = new PdfPCell(new Phrase(String.valueOf(sailingScheduleData), textFont));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }
                }
            }
        }

        return table;
    }

}
