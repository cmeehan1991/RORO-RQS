package RORO;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.sun.glass.events.KeyEvent;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import static javafx.application.Platform.exit;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultEditorKit;
import net.proteanit.sql.DbUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

//import org.jdesktop.swingx.table.DatePickerCellEditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cmeehan
 */
public class MainMenu extends javax.swing.JFrame {

    private String username, userID;
    protected static final Connection conn = new DBConnection().connect();
    private static final String userHomeFolder = System.getProperty("user.home");
    private static final Font labelFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private static final Font tableHeadingFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.WHITE);
    private static final Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    private static final Font emailTextFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.UNDERLINE, BaseColor.BLUE);
    private static final Font rowLabelFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
    private static final Font subjectToFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLDITALIC);
    private static final Font disclaimerFont = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLDITALIC);
    private static final Font fmcFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.UNDERLINE);
    private static final Font plLabelFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD);
    protected static JFileChooser jChooser;
    private static final DecimalFormat df = new DecimalFormat(".###");
    private Object l_centimeters_cell, w_centimeters_cell, h_centimeters_cell, l_inches_cell, w_inches_cell, h_inches_cell;
    private double l_centimeters, w_centimeters, h_centimeters, l_inches, w_inches, h_inches, cubic_meters;
    protected static JDatePickerImpl newCustomerContractExpirationDatePicker, existingCustomerContractExpirationDatePicker, validityToDatePicker, validityFromDatePicker;
    private static JDatePanelImpl datePanel1, datePanel2, datePanel3, datePanel4;
    private static UtilDateModel model1, model2, model3, model4;
    private static String totalOutstanding;

    // This object sets the date pickers used
    private static void DatePicker() {
        /*
         * Date picker for new customer
         */
        Rectangle bounds1 = new Rectangle(newCustomerContractExpirationDateTextField.getBounds());
        model1 = new UtilDateModel();
        Properties p1 = new Properties();
        p1.put("text.today", "Today");
        p1.put("text.month", "Month");
        p1.put("text.year", "Year");
        datePanel1 = new JDatePanelImpl(model1, p1);
        newCustomerContractExpirationDatePicker = new JDatePickerImpl(datePanel1, new DateComponentFormatter());
        newCustomerContractExpirationDatePicker.setBounds(bounds1);
        newCustomerContractExpirationDateTextField.setVisible(false);
        jPanel4.add(newCustomerContractExpirationDatePicker);

        /**
         * Date Picker for existing customer
         */
        Rectangle bounds2 = new Rectangle(existingCompanyContractEpirationTextField.getBounds());
        model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        datePanel2 = new JDatePanelImpl(model2, p2);
        existingCustomerContractExpirationDatePicker = new JDatePickerImpl(datePanel2, new DateComponentFormatter());
        existingCustomerContractExpirationDatePicker.setBounds(bounds2);
        existingCompanyContractEpirationTextField.setVisible(false);
        jPanel8.add(existingCustomerContractExpirationDatePicker);

        /**
         * Date pickers for publishing form
         */
        //Validity from date picker 
        Rectangle validityFromBounds = new Rectangle(validityFromButton.getBounds());
        model3 = new UtilDateModel();
        Properties p3 = new Properties();
        p3.put("text.today", "Today");
        p3.put("text.month", "Month");
        p3.put("text.year", "Year");
        datePanel3 = new JDatePanelImpl(model3, p3);
        validityFromDatePicker = new JDatePickerImpl(datePanel3, new DateLabelFormatter());
        Point point0 = validityFromButton.getLocation();
        validityFromDatePicker.setLocation(point0);
        validityFromDatePicker.setBounds(validityFromBounds);
        validityFromButton.setVisible(false);
        publishingCenterPanel.add(validityFromDatePicker);

        //Validity to date picker
        Rectangle validityToBounds = new Rectangle(validityToButton.getBounds());
        model4 = new UtilDateModel();
        Properties p4 = new Properties();
        p4.put("text.today", "Today");
        p4.put("text.month", "Month");
        p4.put("text.year", "Year");
        datePanel4 = new JDatePanelImpl(model4, p4);
        validityToDatePicker = new JDatePickerImpl(datePanel4, new DateLabelFormatter());
        Point point1 = validityToButton.getLocation();
        validityToDatePicker.setLocation(point1);
        validityToDatePicker.setBounds(validityToBounds);
        validityToButton.setVisible(false);
        publishingCenterPanel.add(validityToDatePicker);
    }

    // This object sets the sailing schedule tables
    private static void SailingScheduleTable() {
        TableColumn tshipColumn = newQuoteSailingScheduleTable.getColumnModel().getColumn(3);
        JComboBox tshipComboBox = new JComboBox();
        tshipComboBox.addItem("");
        tshipComboBox.addItem("Yes");
        tshipComboBox.addItem("No");
        tshipColumn.setCellEditor(new DefaultCellEditor(tshipComboBox));

        TableColumn updateTshipColumn = updateEditSailingScheduleTable.getColumnModel().getColumn(3);
        JComboBox updateTshipComboBox = new JComboBox();
        updateTshipComboBox.addItem("");
        updateTshipComboBox.addItem("Yes");
        updateTshipComboBox.addItem("No");
        updateTshipColumn.setCellEditor(new DefaultCellEditor(tshipComboBox));
    }

    public void MainMenuInformation(String username, String userID) {
        this.username = username;
        this.userID = userID;
    }

    public MainMenu() {
        initComponents();
        setIcon();
        setLocationRelativeTo(null);

        jChooser = new JFileChooser();
        DatePicker();
        SailingScheduleTable();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jSlider1 = new javax.swing.JSlider();
        jSeparator14 = new javax.swing.JSeparator();
        jScrollPane9 = new javax.swing.JScrollPane();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        jRadioButton1 = new javax.swing.JRadioButton();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        buttonGroup10 = new javax.swing.ButtonGroup();
        buttonGroup11 = new javax.swing.ButtonGroup();
        buttonGroup12 = new javax.swing.ButtonGroup();
        buttonGroup13 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        userInformationPanel = new javax.swing.JPanel();
        userInformationLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        userIDLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        salesRegionLabel = new javax.swing.JLabel();
        officePhoneLabel = new javax.swing.JLabel();
        officeLocationLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        usernameLabel1 = new javax.swing.JLabel();
        titleLabel1 = new javax.swing.JLabel();
        regionLabel1 = new javax.swing.JLabel();
        phoneLabel1 = new javax.swing.JLabel();
        officeLocationLabel1 = new javax.swing.JLabel();
        emailLabel1 = new javax.swing.JLabel();
        phoneLabel4 = new javax.swing.JLabel();
        mobilePhoneLabel = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        totalQuotesLabel = new javax.swing.JLabel();
        outstandingLabel = new javax.swing.JLabel();
        bookedToDateLabel = new javax.swing.JLabel();
        bookingRatioLabel = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        usernameLabel3 = new javax.swing.JLabel();
        titleLabel3 = new javax.swing.JLabel();
        regionLabel3 = new javax.swing.JLabel();
        phoneLabel3 = new javax.swing.JLabel();
        usernameLabel4 = new javax.swing.JLabel();
        totalQuotesCYTDLabel = new javax.swing.JLabel();
        regionLabel4 = new javax.swing.JLabel();
        totalBookingsCYTDLabel = new javax.swing.JLabel();
        usernameLabel5 = new javax.swing.JLabel();
        totalQuotesWeekLabel = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        outstandingQuotesTable = new javax.swing.JTable();
        jLabel128 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        requireAttentionTable = new javax.swing.JTable();
        selectOutstandingQuoteRequireAttentionButton = new javax.swing.JButton();
        selectOustandingQuoteButton = new javax.swing.JButton();
        jSeparator20 = new javax.swing.JSeparator();
        newQuotePanel = new javax.swing.JPanel();
        newQuoteLabel = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tradeLaneComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        polTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        podTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tshp1TextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tshp2TextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        commodityClassComboBox = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        handlingInstructionsComboBox = new javax.swing.JComboBox();
        newQuoteAccessoriesCheckBox = new javax.swing.JCheckBox();
        commodityDescriptionTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        oftTextField = new javax.swing.JTextField();
        oftMeasurementComboBox = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        bafTextField = new javax.swing.JTextField();
        bafIncludedCheckBox = new javax.swing.JCheckBox();
        jLabel81 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        ecaBAFTextField = new javax.swing.JTextField();
        ecaBafMeasurementComboBox = new javax.swing.JComboBox();
        ecaBafIncludedCheckBox = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        thcTextField = new javax.swing.JTextField();
        thcMeasurementComboBox = new javax.swing.JComboBox();
        thcIncludedCheckBox = new javax.swing.JCheckBox();
        thcAttached = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentsTextArea = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        wfgTextField = new javax.swing.JTextField();
        wfgMeasurementComboBox = new javax.swing.JComboBox();
        wfgIncludedCheckBox = new javax.swing.JCheckBox();
        wfgAttached = new javax.swing.JCheckBox();
        warRiskCheckBox = new javax.swing.JCheckBox();
        spotRateCheckBox = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        newQuoteBookedCheckBox = new javax.swing.JCheckBox();
        newQuoteDenialCheckBox = new javax.swing.JCheckBox();
        documentationFeeComboBox = new javax.swing.JComboBox();
        documentationFeeIncludedCheckBox = new javax.swing.JCheckBox();
        submitNewQuote = new javax.swing.JButton();
        clearNewQuoteForm = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        packingListTable = new javax.swing.JTable();
        jLabel53 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        mafiMinimumCheckBox = new javax.swing.JCheckBox();
        mafiMinimumTextField = new javax.swing.JTextField();
        contractRateCheckBox = new javax.swing.JCheckBox();
        declineComboBox = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jButton7 = new javax.swing.JButton();
        newQuoteCustomerNameLabel = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        bookingNumberTextField = new javax.swing.JTextField();
        newQuoteAddRow = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        contactNameTextField = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        contactEmailTextField = new javax.swing.JTextField();
        jSeparator19 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        calculateCubicMetersButton = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        newQuoteSailingScheduleTable = new javax.swing.JTable();
        jLabel83 = new javax.swing.JLabel();
        includeSailingScheduleCheckBox = new javax.swing.JCheckBox();
        jLabel93 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        shipperCommentsTextArea = new javax.swing.JTextArea();
        includeShipperCommentsCheckBox = new javax.swing.JCheckBox();
        jSeparator24 = new javax.swing.JSeparator();
        updateEditQuotePanel = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        updatePOLTextField = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        updatePODTextField = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        updateTshp1TextField = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        updateCommodityClassComboBox = new javax.swing.JComboBox();
        jLabel64 = new javax.swing.JLabel();
        updateCommodityDescriptionTextField = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        updateHandlingInstructionsComboBox = new javax.swing.JComboBox();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        updateOFTTextField = new javax.swing.JTextField();
        updateBAFTextField = new javax.swing.JTextField();
        updateEcaBafTextField = new javax.swing.JTextField();
        updateTHCTextField = new javax.swing.JTextField();
        updateWfgTextField = new javax.swing.JTextField();
        updateOftUnitComboBox = new javax.swing.JComboBox();
        updateBafIncludedCheckBox = new javax.swing.JCheckBox();
        updateDocumentationFeeComboBox = new javax.swing.JComboBox();
        updateDocFeeIncludedCheckBox = new javax.swing.JCheckBox();
        updateEcaComboBox = new javax.swing.JComboBox();
        updateThcComboBox = new javax.swing.JComboBox();
        updateWfgComboBox = new javax.swing.JComboBox();
        updateEcaIncludedCheckBox = new javax.swing.JCheckBox();
        updateThcIncludedCheckBox = new javax.swing.JCheckBox();
        updateWfgIncludedCheckBox = new javax.swing.JCheckBox();
        updateTradeLane = new javax.swing.JComboBox();
        jLabel76 = new javax.swing.JLabel();
        updateTshp2TextField = new javax.swing.JTextField();
        updateWarRiskCheckBox = new javax.swing.JCheckBox();
        jScrollPane5 = new javax.swing.JScrollPane();
        updateCommentsTextArea = new javax.swing.JTextArea();
        updateSpotRateCheckBox = new javax.swing.JCheckBox();
        updateEditQuoteButton = new javax.swing.JButton();
        updateCancelButton = new javax.swing.JButton();
        updateBookedCheckBox = new javax.swing.JCheckBox();
        jLabel78 = new javax.swing.JLabel();
        updateQuoteIDTextArea = new javax.swing.JTextField();
        updateQuoteIDSearchButton = new javax.swing.JButton();
        updateDeclineCheckBox = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        updateEditQuoteCustomerNameLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        updateThcAttachedCheckBox = new javax.swing.JCheckBox();
        updateWfgAttachedCheckBox = new javax.swing.JCheckBox();
        updateEditQuoteCustomerNameLabel1 = new javax.swing.JLabel();
        updateEditQuoteCustomerNameLabel2 = new javax.swing.JLabel();
        authorLabel = new javax.swing.JLabel();
        lastUpdatedByLabel = new javax.swing.JLabel();
        authorLabel1 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        currentAlphaNumeralLabel = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        updateAlphaNumeralTextField = new javax.swing.JTextField();
        authorLabel2 = new javax.swing.JLabel();
        updateQuoteAccessoriesCheckBox = new javax.swing.JCheckBox();
        jScrollPane12 = new javax.swing.JScrollPane();
        updateEditPackingListTable = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        updateQuoteMAFIMinimumCheckBox = new javax.swing.JCheckBox();
        updateEditMAFIMinimumTextField = new javax.swing.JTextField();
        updateContractRateCheckBox = new javax.swing.JCheckBox();
        updateDeclineComboBox = new javax.swing.JComboBox();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        updateEditQuoteCustomerNameLabel3 = new javax.swing.JLabel();
        editCustomerButton = new javax.swing.JButton();
        quoteFeedbackCheckBox = new javax.swing.JCheckBox();
        quoteFeedbackComboBox = new javax.swing.JComboBox();
        quoteFeedbackTextField = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        updateEditQuoteBookingNumberTextField = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        updateContactEmailTextField = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        updateContactNameTextField = new javax.swing.JTextField();
        jSeparator18 = new javax.swing.JSeparator();
        editQuoteDuplicateRateCheckBox = new javax.swing.JCheckBox();
        updateEditQuoteCustomerNameLabel4 = new javax.swing.JLabel();
        quoteCreatedLabel = new javax.swing.JLabel();
        authorLabel3 = new javax.swing.JLabel();
        updateEditQuoteCustomerNameLabel5 = new javax.swing.JLabel();
        quoteLastUpdatedLabel = new javax.swing.JLabel();
        updateEditQuoteAddRowButton = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        calculateCubicMetersButtonEditPL = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        updateEditSailingScheduleTable = new javax.swing.JTable();
        jLabel84 = new javax.swing.JLabel();
        editQuoteIncludeSailingScheduleCheckBox = new javax.swing.JCheckBox();
        updateEditSailingScheduleAddRowButton = new javax.swing.JButton();
        jLabel95 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        updateShipperCommentsTextArea = new javax.swing.JTextArea();
        editQuoteIncludeShipperCommentsCheckBox = new javax.swing.JCheckBox();
        publishingCenterPanel = new javax.swing.JPanel();
        jLabel100 = new javax.swing.JLabel();
        pQuoteIDTextField = new javax.swing.JTextField();
        pQuoteIDButton = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        pCommodityDescriptionTextField = new javax.swing.JTextField();
        pOftTextField = new javax.swing.JTextField();
        pBafTextField = new javax.swing.JTextField();
        pEcaTextField = new javax.swing.JTextField();
        pThcTextField = new javax.swing.JTextField();
        pWfgTextField = new javax.swing.JTextField();
        kkluNumberTextField = new javax.swing.JTextField();
        jLabel113 = new javax.swing.JLabel();
        pPolTextField = new javax.swing.JTextField();
        jLabel114 = new javax.swing.JLabel();
        pBookingNumberTextField = new javax.swing.JTextField();
        jLabel116 = new javax.swing.JLabel();
        pOftComboBox = new javax.swing.JComboBox();
        pWfgComboBox = new javax.swing.JComboBox();
        pThcComboBox = new javax.swing.JComboBox();
        pEcaComboBox = new javax.swing.JComboBox();
        pDocFeeComboBox = new javax.swing.JComboBox();
        pCommodityClassComboBox = new javax.swing.JComboBox();
        pWfgIncludedCheckBox = new javax.swing.JCheckBox();
        pEcaIncludedCheckBox = new javax.swing.JCheckBox();
        pThcIncludedCheckBox = new javax.swing.JCheckBox();
        pBafIncludedCheckBox = new javax.swing.JCheckBox();
        pDocFeeIncludedCheckBox = new javax.swing.JCheckBox();
        jLabel119 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        pCommentsTextArea = new javax.swing.JTextArea();
        submitToPublishingPDFButton = new javax.swing.JButton();
        cancelSpotFileButton = new javax.swing.JButton();
        jLabel120 = new javax.swing.JLabel();
        pPodTextField = new javax.swing.JTextField();
        pWarRiskCheckBox = new javax.swing.JCheckBox();
        jLabel115 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        pHandlingInstructions = new javax.swing.JComboBox();
        jLabel125 = new javax.swing.JLabel();
        storageTextField = new javax.swing.JTextField();
        storageUnitComboBox = new javax.swing.JComboBox();
        storageIncludedCheckBox = new javax.swing.JCheckBox();
        thcSubjectToTariffCheckBox = new javax.swing.JCheckBox();
        wfgSubjectToTariffCheckBox = new javax.swing.JCheckBox();
        storageSubjectToTariffCheckBox = new javax.swing.JCheckBox();
        ecaSubjectToTariffCheckBox = new javax.swing.JCheckBox();
        bafSubjectToTariffCheckBox = new javax.swing.JCheckBox();
        jLabel121 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        idTypeComboBox = new javax.swing.JComboBox();
        saveChangesPublishingPDFButton = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel138 = new javax.swing.JLabel();
        commodityNumberTextField = new javax.swing.JFormattedTextField();
        jLabel135 = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JFormattedTextField();
        jLabel136 = new javax.swing.JLabel();
        tliNumberTextField = new javax.swing.JFormattedTextField();
        jLabel137 = new javax.swing.JLabel();
        newFilingCompleteButton = new javax.swing.JButton();
        newFilingCompleteButton1 = new javax.swing.JButton();
        expirationDateTextField = new javax.swing.JTextField();
        jLabel139 = new javax.swing.JLabel();
        pQuoteNumberTextField = new javax.swing.JTextField();
        jLabel140 = new javax.swing.JLabel();
        pIDTextField = new javax.swing.JTextField();
        validityFromButton = new javax.swing.JButton();
        validityToButton = new javax.swing.JButton();
        jSeparator22 = new javax.swing.JSeparator();
        searchPanel = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        queryTitleLabel = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        searchResultsTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        exportResultsToExcelButton = new javax.swing.JButton();
        clearResultTable = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        queryNameTextField = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        dateTextField = new javax.swing.JTextField();
        jSeparator21 = new javax.swing.JSeparator();
        savedSearchesButton = new javax.swing.JButton();
        customerInformationPanel = new javax.swing.JPanel();
        newCustomerPanel = new javax.swing.JPanel();
        newCustomerButton = new javax.swing.JButton();
        existingCustomerButton = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        submitNewCustomerInformation = new javax.swing.JButton();
        cancelNewCustomerButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        newCustomerFirstName = new javax.swing.JTextField();
        jLabel143 = new javax.swing.JLabel();
        newCustomerLastName = new javax.swing.JTextField();
        jLabel144 = new javax.swing.JLabel();
        newCustomerOfficePhone = new javax.swing.JTextField();
        jLabel145 = new javax.swing.JLabel();
        newCustomerMobilePhoneTextField = new javax.swing.JTextField();
        newCustomerEmailTextField = new javax.swing.JTextField();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        newCustomerTitleTextField = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        pcOfficeAddressTextField = new javax.swing.JTextField();
        pcSuiteTextField = new javax.swing.JTextField();
        pcCityTextField = new javax.swing.JTextField();
        pcZipTextField = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        pcStateTextField = new javax.swing.JTextField();
        pcCountryTextField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        newCustomerCompanyTextField = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        newCustomerDBATextField = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        newCustomerOTINumberTextField = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        newCustomerContractYesRadioButton = new javax.swing.JRadioButton();
        newCustomerNoContractRadioButton = new javax.swing.JRadioButton();
        jLabel38 = new javax.swing.JLabel();
        newCustomerContractNumberTextField = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        newCustomerContractExpirationDateTextField = new javax.swing.JButton();
        newCustomerFreightForwarderRadioButton = new javax.swing.JRadioButton();
        newCustomerNVOCCRadioButton = new javax.swing.JRadioButton();
        newCustomerBCORadioButton = new javax.swing.JRadioButton();
        newCustomerOtherRadioButton = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        newCustomerAddress1 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        newCustomerAddress2 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        newCustomerCity = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        newCustomerZipCode = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        newCustomerState = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        newCustomerCountry = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        newCustomerMainPhoneTextField = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        newCustomerSecondaryPhoneNumber = new javax.swing.JFormattedTextField();
        jLabel29 = new javax.swing.JLabel();
        newCustomerFaxNumber = new javax.swing.JFormattedTextField();
        jLabel58 = new javax.swing.JLabel();
        newCustomerCompanyEmailTextField = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        newCustomerComments = new javax.swing.JTextArea();
        existingCustomerPanel = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        newCustomerButton1 = new javax.swing.JButton();
        existingCustomerButton1 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        searchExistingCustomersButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel52 = new javax.swing.JLabel();
        existingCompanyNameTextField = new javax.swing.JTextField();
        searchExistingCustomersButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel152 = new javax.swing.JLabel();
        existingCompanyFirstNameTextField = new javax.swing.JTextField();
        jLabel153 = new javax.swing.JLabel();
        existingCompanyLastNameTextField = new javax.swing.JTextField();
        jLabel154 = new javax.swing.JLabel();
        existingCompanyOfficePhoneTextField = new javax.swing.JTextField();
        jLabel155 = new javax.swing.JLabel();
        existingCompanyMobilePhoneTextField = new javax.swing.JTextField();
        jLabel156 = new javax.swing.JLabel();
        existingCompanyEmailTextField = new javax.swing.JTextField();
        jLabel157 = new javax.swing.JLabel();
        existingCompanyTitleTextField = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        updatePCAddressTextfield = new javax.swing.JTextField();
        updatePCSuiteTextField = new javax.swing.JTextField();
        updatePCCityTextField = new javax.swing.JTextField();
        updatePCZipTextField = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        updatePCStateTextField = new javax.swing.JTextField();
        updatePCCountryTextField = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        existingCompanyMainPhoneTextField = new javax.swing.JFormattedTextField();
        jLabel41 = new javax.swing.JLabel();
        existingCompanySecondaryPhoneTextField = new javax.swing.JFormattedTextField();
        jLabel45 = new javax.swing.JLabel();
        existingCompanyFaxNumberTextField = new javax.swing.JFormattedTextField();
        jLabel102 = new javax.swing.JLabel();
        existingCompanyMainEmailTextField = new javax.swing.JTextField();
        existingCompanyAddress1TextField = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        existingCompanyAddress2TextField = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        existingCompanyCityTextField = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        existingCompanyZipTextField = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        existingCompanyStateTextField = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        existingCompanyCountryTextField = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel160 = new javax.swing.JLabel();
        existingCompanyCompanyTextField = new javax.swing.JTextField();
        jLabel161 = new javax.swing.JLabel();
        existingCompanyDBATextField = new javax.swing.JTextField();
        jLabel162 = new javax.swing.JLabel();
        existingCompanyOTINumberTextField = new javax.swing.JTextField();
        jLabel163 = new javax.swing.JLabel();
        existingCompanyContractYesRadioButton = new javax.swing.JRadioButton();
        existingCompanyNoContractRadioButton = new javax.swing.JRadioButton();
        jLabel164 = new javax.swing.JLabel();
        existingCompanyContractNumberTextField = new javax.swing.JTextField();
        jLabel167 = new javax.swing.JLabel();
        existingCompanyIDTextField = new javax.swing.JTextField();
        jLabel168 = new javax.swing.JLabel();
        existingCompanyContractEpirationTextField = new javax.swing.JButton();
        existingCustomerFreightForwarderRadioButton = new javax.swing.JRadioButton();
        existingCustomerNVOCCRadioButton = new javax.swing.JRadioButton();
        existingCustomerBCORadioButton = new javax.swing.JRadioButton();
        existingCustomerOtherRadioButton = new javax.swing.JRadioButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        existingCompanyCommentsTextArea = new javax.swing.JTextArea();
        editUpdateCustomerInformationButton = new javax.swing.JButton();
        editUpdateCustomerQuotesButton = new javax.swing.JButton();
        editUpdateCustomerBookingsButton = new javax.swing.JButton();
        UserInformationCenterButton = new javax.swing.JButton();
        newQuoteButton = new javax.swing.JButton();
        updateEditQuoteButton1 = new javax.swing.JButton();
        publishingCenterButton = new javax.swing.JButton();
        searchCenterButton = new javax.swing.JButton();
        customerInformationCenterButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        newCustomerMenuItem = new javax.swing.JMenu();
        newQuoteMenuItem = new javax.swing.JMenuItem();
        NewCustMenuItem = new javax.swing.JMenuItem();
        salesCenterMenuItem = new javax.swing.JMenuItem();
        closeApplicationMenuItem = new javax.swing.JMenuItem();
        EditMenuItem = new javax.swing.JMenu();
        copyMenuItem = new javax.swing.JMenuItem();
        cutMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        jMenu4.setText("jMenu4");

        jRadioButton1.setText("jRadioButton1");

        jMenuItem2.setText("jMenuItem2");
        jPopupMenu1.add(jMenuItem2);

        jLabel46.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel46.setText("Address 1:");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("\"K\" Line RORO ");
        setBackground(new java.awt.Color(51, 51, 51));
        setFocusCycleRoot(false);
        setForeground(new java.awt.Color(51, 51, 51));
        setMaximumSize(new java.awt.Dimension(125000, 125000));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane15.setBorder(null);
        jScrollPane15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setForeground(new java.awt.Color(51, 51, 51));
        mainPanel.setAutoscrolls(true);
        mainPanel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mainPanel.setLayout(new java.awt.CardLayout());

        userInformationPanel.setForeground(new java.awt.Color(255, 255, 255));

        userInformationLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        userInformationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userInformationLabel.setText("User Information");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        userIDLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        userIDLabel.setText("N/A");

        titleLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        titleLabel.setText("N/A");

        salesRegionLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        salesRegionLabel.setText("N/A");

        officePhoneLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        officePhoneLabel.setText("N/A");

        officeLocationLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        officeLocationLabel.setText("N/A");

        emailLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        emailLabel.setText("N/A");

        jLabel126.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel126.setText("User Information");
        jLabel126.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        usernameLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        usernameLabel1.setText("User ID:");

        titleLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        titleLabel1.setText("Title:");

        regionLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        regionLabel1.setText("Sales Region:");

        phoneLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        phoneLabel1.setText("Office Phone:");

        officeLocationLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        officeLocationLabel1.setText("Office Location:");

        emailLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        emailLabel1.setText("Email:");

        phoneLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        phoneLabel4.setText("Mobile Phone:");

        mobilePhoneLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        mobilePhoneLabel.setText("N/A");

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel39.setText("Username:");

        usernameLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        usernameLabel.setText("N/A");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel126, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phoneLabel1)
                            .addComponent(phoneLabel4)
                            .addComponent(regionLabel1)
                            .addComponent(titleLabel1)
                            .addComponent(usernameLabel1)
                            .addComponent(officeLocationLabel1)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(officePhoneLabel)
                            .addComponent(salesRegionLabel)
                            .addComponent(titleLabel)
                            .addComponent(userIDLabel)
                            .addComponent(mobilePhoneLabel)
                            .addComponent(officeLocationLabel)
                            .addComponent(usernameLabel)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(emailLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailLabel)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {emailLabel1, officeLocationLabel1, phoneLabel1, phoneLabel4, regionLabel1, titleLabel1, usernameLabel1});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel126)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userIDLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(usernameLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(usernameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(titleLabel1)
                            .addComponent(titleLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(regionLabel1)
                            .addComponent(salesRegionLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneLabel1)
                            .addComponent(officePhoneLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneLabel4)
                            .addComponent(mobilePhoneLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(officeLocationLabel1)
                            .addComponent(officeLocationLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel1)
                    .addComponent(emailLabel)))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        totalQuotesLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        totalQuotesLabel.setText("N/A");

        outstandingLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        outstandingLabel.setText("N/A");

        bookedToDateLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookedToDateLabel.setText("N/A");

        bookingRatioLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookingRatioLabel.setText("N/A");

        jLabel127.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel127.setText("Quoting Information");
        jLabel127.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        usernameLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        usernameLabel3.setText("Total Quotes to Date:");

        titleLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        titleLabel3.setText("Outstanding Quotes:");

        regionLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        regionLabel3.setText("Total Bookings to Date:");

        phoneLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        phoneLabel3.setText("Booking Ratio:");

        usernameLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        usernameLabel4.setText("Total Quotes CYTD:");

        totalQuotesCYTDLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        totalQuotesCYTDLabel.setText("N/A");

        regionLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        regionLabel4.setText("Total Bookings CYTD:");

        totalBookingsCYTDLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        totalBookingsCYTDLabel.setText("N/A");

        usernameLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        usernameLabel5.setText("Total Quotes This Week:");

        totalQuotesWeekLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        totalQuotesWeekLabel.setText("N/A");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phoneLabel3)
                            .addComponent(regionLabel3)
                            .addComponent(titleLabel3)
                            .addComponent(usernameLabel3)
                            .addComponent(usernameLabel4)
                            .addComponent(regionLabel4)
                            .addComponent(usernameLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalQuotesWeekLabel)
                            .addComponent(bookedToDateLabel)
                            .addComponent(bookingRatioLabel)
                            .addComponent(totalBookingsCYTDLabel)
                            .addComponent(totalQuotesLabel)
                            .addComponent(totalQuotesCYTDLabel)
                            .addComponent(outstandingLabel)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel127)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(usernameLabel3)
                    .addComponent(totalQuotesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(usernameLabel4)
                    .addComponent(totalQuotesCYTDLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel5)
                    .addComponent(totalQuotesWeekLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(titleLabel3)
                    .addComponent(outstandingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regionLabel4)
                    .addComponent(totalBookingsCYTDLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(regionLabel3)
                    .addComponent(bookedToDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(phoneLabel3)
                    .addComponent(bookingRatioLabel))
                .addGap(0, 0, 0))
        );

        outstandingQuotesTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        outstandingQuotesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Quote ID", "Date Quoted", "Company", "Commodity Description", "Comodity Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        outstandingQuotesTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(outstandingQuotesTable);

        jLabel128.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setText("Outstanding Quotes");

        jLabel130.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setText("Quotes Requiring Attention (over 2 weeks old)");

        requireAttentionTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        requireAttentionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Quote ID", "Date Last Updated", "Company", "Commodity Type", "Commodity Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(requireAttentionTable);

        selectOutstandingQuoteRequireAttentionButton.setText("Select");
        selectOutstandingQuoteRequireAttentionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectOutstandingQuoteRequireAttentionButtonActionPerformed(evt);
            }
        });

        selectOustandingQuoteButton.setText("Select");
        selectOustandingQuoteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectOustandingQuoteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userInformationPanelLayout = new javax.swing.GroupLayout(userInformationPanel);
        userInformationPanel.setLayout(userInformationPanelLayout);
        userInformationPanelLayout.setHorizontalGroup(
            userInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userInformationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(userInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userInformationLabel, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(userInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(userInformationPanelLayout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 1156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 1158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(selectOustandingQuoteButton)
                    .addComponent(selectOutstandingQuoteRequireAttentionButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(userInformationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 1211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        userInformationPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel1, jPanel2});

        userInformationPanelLayout.setVerticalGroup(
            userInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userInformationPanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(userInformationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(userInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jLabel128)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectOustandingQuoteButton)
                .addGap(5, 5, 5)
                .addComponent(jLabel130)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectOutstandingQuoteRequireAttentionButton)
                .addContainerGap())
        );

        userInformationPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane6, jScrollPane7});

        userInformationPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel1, jPanel2});

        mainPanel.add(userInformationPanel, "card5");

        newQuotePanel.setAutoscrolls(true);

        newQuoteLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        newQuoteLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newQuoteLabel.setText("New Quote");
        newQuoteLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel79.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel79.setText("Company Name:");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setText("Trade Lane");

        tradeLaneComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tradeLaneComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Trans-Atlantic EB", "Trans-Atlantic WB", "North Atlantic Shuttle EB", "North Atlantic Shuttle WB", "NAX EB", "NAX WB", "ECAMS NB", "ECAMS SB", "Foreign to Foreign", "Other (Import Only)" }));
        tradeLaneComboBox.setSelectedItem(0
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("POL");

        polTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        polTextField.setComponentPopupMenu(jPopupMenu1);
        polTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                polTextFieldFocusGained(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("POD");

        podTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Tshp1");

        tshp1TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("Tshp2");

        tshp2TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setText("Commodity Class");

        commodityClassComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        commodityClassComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Automobile/POV", "Automobile/POV (up to 14 cubic meters)", "Automobile/POV (14 - 17 cubic meters)", "Automobile/POV (17 - 25 cubic meters)", "Automobile/POV (up to 16 cubic meters)", "Automobile/POV (16 - 20 cubic meters)", "Automobile/POV (20 - 25 cubic meters)", "New Automobile", "New Automobile (up to 14 cubic meters)", "New Automobile (14 - 17 cubic meters)", "New Automobile (17 - 25 cubic meters)", "New Automobile (up to 16 cubic meters)", "New Automobile (16 - 20 cubic meters)", "New Automobile (20 - 25 cubic meters)", "Motorhome(s)", "Truck(s)", "Bus(es)", "Van(s)", "Chassis", "Travel trailer(s)", "Utility trailer(s)", "5th wheel trailer(s)", "Agriculture Equipment", "Construction Equipment", "Forestry Equipment", "Mining Equipment", "Handling Equipment", "Crane(s)", "Tractor(s)", "Boat(s) on Trailer", "Boat(s) on Cradle", "Static", "Static Machinery", "Special Purpose Vehicles", "Motorcycle(s)", "All-Terrain Vehicle(s)", "Jet Ski(s) (up to 2 cbm)", "Aircraft" }));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel8.setText("Handling Instructions");

        handlingInstructionsComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        handlingInstructionsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Self-propelled", "Towable", "Static", "MAFI", "Forkliftable", "Static (MAFI)", "Static (Forkliftable)" }));

        newQuoteAccessoriesCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        newQuoteAccessoriesCheckBox.setText("Accessories");

        commodityDescriptionTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel7.setText("Commodity Description");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel9.setText("OFT");

        jLabel17.setText("$");

        oftTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        oftMeasurementComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        oftMeasurementComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));
        oftMeasurementComboBox.setNextFocusableComponent(bafTextField);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel10.setText("BAF");

        bafTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        bafIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bafIncludedCheckBox.setText("Included");
        bafIncludedCheckBox.setNextFocusableComponent(ecaBAFTextField);

        jLabel81.setText("%");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel11.setText("ECA");

        jLabel74.setText("$");

        ecaBAFTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        ecaBafMeasurementComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        ecaBafMeasurementComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));

        ecaBafIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        ecaBafIncludedCheckBox.setText("Included");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel12.setText("THC");

        jLabel75.setText("$");

        thcTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        thcMeasurementComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        thcMeasurementComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));

        thcIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        thcIncludedCheckBox.setText("Included");

        thcAttached.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        thcAttached.setText("Attached to Email");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Internal Comments");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        commentsTextArea.setColumns(15);
        commentsTextArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        commentsTextArea.setLineWrap(true);
        commentsTextArea.setRows(10);
        commentsTextArea.setWrapStyleWord(true);
        commentsTextArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        commentsTextArea.setLineWrap(true);
        jScrollPane1.setViewportView(commentsTextArea);

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel13.setText("WFG");

        jLabel77.setText("$");

        wfgTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        wfgMeasurementComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        wfgMeasurementComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));

        wfgIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        wfgIncludedCheckBox.setText("Included");

        wfgAttached.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        wfgAttached.setText("Attached to Email");

        warRiskCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        warRiskCheckBox.setText("War Risk");

        spotRateCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        spotRateCheckBox.setText("Spot Rate");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel14.setText("Documentation Fee");

        newQuoteBookedCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        newQuoteBookedCheckBox.setText("Booked");

        newQuoteDenialCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        newQuoteDenialCheckBox.setText("Decline");

        documentationFeeComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        documentationFeeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "$25 per B/L", "$50 per B/L", "$75 per B/L" }));

        documentationFeeIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        documentationFeeIncludedCheckBox.setText("Inclusive");
        documentationFeeIncludedCheckBox.setActionCommand("Included");

        submitNewQuote.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        submitNewQuote.setText("Submit");
        submitNewQuote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitNewQuoteActionPerformed(evt);
            }
        });

        clearNewQuoteForm.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        clearNewQuoteForm.setText("Cancel");
        clearNewQuoteForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearNewQuoteFormActionPerformed(evt);
            }
        });

        packingListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Commodity", "Qty", "L(cm)", "W(cm)", "H(cm)", "Kgs", "L(in)", "W(in)", "H(in)", "CBM", "Lbs"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        packingListTable.getTableHeader().setReorderingAllowed(false);
        packingListTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                packingListTableKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                packingListTableKeyTyped(evt);
            }
        });
        jScrollPane11.setViewportView(packingListTable);
        if (packingListTable.getColumnModel().getColumnCount() > 0) {
            packingListTable.getColumnModel().getColumn(0).setMinWidth(150);
            packingListTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        jLabel53.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Packing List");
        jLabel53.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/search_icon.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        mafiMinimumCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        mafiMinimumCheckBox.setText("MAFI minimum: $");

        mafiMinimumTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        contractRateCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        contractRateCheckBox.setText("Contract Rate");

        declineComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        declineComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "No Service", "OFT", "Response", "Schedule", "Space", "Cargo Size", "Operational Restrictions", "Other (See Comments)" }));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton7.setText("Clear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        newQuoteCustomerNameLabel.setText("N/A");

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator15.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator16.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator16.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator17.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator17.setOrientation(javax.swing.SwingConstants.VERTICAL);

        newQuoteAddRow.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        newQuoteAddRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RORO/add_row_icon.png"))); // NOI18N
        newQuoteAddRow.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        newQuoteAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newQuoteAddRowActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel20.setText("Contact Name: ");

        contactNameTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel42.setText("Contact Email:");

        contactEmailTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/upload_icon.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        calculateCubicMetersButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        calculateCubicMetersButton.setForeground(new java.awt.Color(240, 240, 240));
        calculateCubicMetersButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/math_icon.png"))); // NOI18N
        calculateCubicMetersButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        calculateCubicMetersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateCubicMetersButtonActionPerformed(evt);
            }
        });

        newQuoteSailingScheduleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Vessel/Voyage", "Sail Date", "T/T", "Trans-shipment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        newQuoteSailingScheduleTable.getTableHeader().setReorderingAllowed(false);
        newQuoteSailingScheduleTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newQuoteSailingScheduleTableKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                newQuoteSailingScheduleTableKeyTyped(evt);
            }
        });
        jScrollPane13.setViewportView(newQuoteSailingScheduleTable);
        if (newQuoteSailingScheduleTable.getColumnModel().getColumnCount() > 0) {
            newQuoteSailingScheduleTable.getColumnModel().getColumn(0).setMinWidth(150);
            newQuoteSailingScheduleTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            newQuoteSailingScheduleTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            newQuoteSailingScheduleTable.getColumnModel().getColumn(2).setMaxWidth(50);
        }

        jLabel83.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setText("Schedule");
        jLabel83.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel93.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setText("Carrier's Comments");
        jLabel93.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        shipperCommentsTextArea.setColumns(15);
        shipperCommentsTextArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        shipperCommentsTextArea.setLineWrap(true);
        shipperCommentsTextArea.setRows(10);
        shipperCommentsTextArea.setWrapStyleWord(true);
        shipperCommentsTextArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        commentsTextArea.setLineWrap(true);
        jScrollPane3.setViewportView(shipperCommentsTextArea);

        jSeparator24.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator24.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator24.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout newQuotePanelLayout = new javax.swing.GroupLayout(newQuotePanel);
        newQuotePanel.setLayout(newQuotePanelLayout);
        newQuotePanelLayout.setHorizontalGroup(
            newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newQuotePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator19)
                    .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(newQuotePanelLayout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel75)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(thcTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(thcMeasurementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(thcIncludedCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(thcAttached)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel77)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(wfgTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(7, 7, 7)
                            .addComponent(wfgMeasurementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(wfgIncludedCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(wfgAttached))
                        .addGroup(newQuotePanelLayout.createSequentialGroup()
                            .addComponent(warRiskCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(documentationFeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(documentationFeeIncludedCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(contractRateCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spotRateCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(newQuoteBookedCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(bookingNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(newQuoteDenialCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(declineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newQuotePanelLayout.createSequentialGroup()
                                .addComponent(jLabel79)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newQuoteCustomerNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newQuotePanelLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(oftTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(oftMeasurementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bafTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jLabel81)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bafIncludedCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel74)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ecaBAFTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ecaBafMeasurementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ecaBafIncludedCheckBox))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newQuotePanelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(commodityDescriptionTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newQuotePanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(commodityClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(handlingInstructionsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newQuoteAccessoriesCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mafiMinimumCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mafiMinimumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newQuotePanelLayout.createSequentialGroup()
                                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newQuotePanelLayout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(contactNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel42)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(contactEmailTextField))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newQuotePanelLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tradeLaneComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(polTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(podTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tshp1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tshp2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(newQuotePanelLayout.createSequentialGroup()
                            .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(newQuotePanelLayout.createSequentialGroup()
                                    .addComponent(includeSailingScheduleCheckBox)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(newQuotePanelLayout.createSequentialGroup()
                                    .addComponent(includeShipperCommentsCheckBox)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(newQuotePanelLayout.createSequentialGroup()
                                    .addComponent(submitNewQuote)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(clearNewQuoteForm)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(newQuoteAddRow)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(calculateCubicMetersButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1))
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                                .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(newQuoteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bafTextField, bookingNumberTextField, ecaBAFTextField, thcTextField, wfgTextField});

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tshp1TextField, tshp2TextField});

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {clearNewQuoteForm, submitNewQuote});

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ecaBafMeasurementComboBox, oftMeasurementComboBox, thcMeasurementComboBox, wfgMeasurementComboBox});

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel19, jScrollPane1, jScrollPane13});

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel12, jLabel13, jLabel9});

        newQuotePanelLayout.setVerticalGroup(
            newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newQuotePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newQuoteLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton7)
                    .addComponent(jButton6)
                    .addComponent(jLabel79)
                    .addComponent(newQuoteCustomerNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(contactNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(contactEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tradeLaneComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(polTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(podTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(tshp1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tshp2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(commodityClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(handlingInstructionsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newQuoteAccessoriesCheckBox)
                    .addComponent(mafiMinimumCheckBox)
                    .addComponent(mafiMinimumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(commodityDescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(oftTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(oftMeasurementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(bafTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81)
                    .addComponent(bafIncludedCheckBox)
                    .addComponent(jLabel11)
                    .addComponent(jLabel74)
                    .addComponent(ecaBAFTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ecaBafMeasurementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ecaBafIncludedCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(jLabel75)
                    .addComponent(thcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thcMeasurementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thcIncludedCheckBox)
                    .addComponent(thcAttached)
                    .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel77)
                    .addComponent(wfgTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wfgMeasurementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wfgIncludedCheckBox)
                    .addComponent(wfgAttached))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(warRiskCheckBox)
                    .addComponent(jLabel14)
                    .addComponent(documentationFeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentationFeeIncludedCheckBox)
                    .addComponent(contractRateCheckBox)
                    .addComponent(spotRateCheckBox)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newQuoteBookedCheckBox)
                    .addComponent(newQuoteDenialCheckBox)
                    .addComponent(declineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookingNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(newQuotePanelLayout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(newQuotePanelLayout.createSequentialGroup()
                        .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(includeSailingScheduleCheckBox)
                            .addComponent(jLabel83))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(includeShipperCommentsCheckBox)
                            .addComponent(jLabel93))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(submitNewQuote)
                        .addComponent(clearNewQuoteForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(newQuoteAddRow)
                        .addComponent(jButton1)
                        .addComponent(calculateCubicMetersButton)))
                .addContainerGap())
        );

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {newQuoteBookedCheckBox, newQuoteDenialCheckBox, spotRateCheckBox, warRiskCheckBox});

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {clearNewQuoteForm, newQuoteAddRow, submitNewQuote});

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bookingNumberTextField, thcTextField});

        newQuotePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton6, jButton7});

        mainPanel.add(newQuotePanel, "card2");
        newQuotePanel.getAccessibleContext().setAccessibleName("newQuotePanel\n");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Update/Edit Quote");

        jLabel59.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel59.setText("Trade Lane");

        jLabel60.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel60.setText("POL");

        updatePOLTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel61.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel61.setText("POD");

        updatePODTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel62.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel62.setText("Tshp1");

        updateTshp1TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel63.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel63.setText("Commodity Class");

        updateCommodityClassComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateCommodityClassComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Automobile/POV", "Automobile/POV (up to 14 cubic meters)", "Automobile/POV (14 - 17 cubic meters)", "Automobile/POV (17 - 25 cubic meters)", "Automobile/POV (up to 16 cubic meters)", "Automobile/POV (16 - 20 cubic meters)", "Automobile/POV (20 - 25 cubic meters)", "New Automobile", "New Automobile (up to 14 cubic meters)", "New Automobile (14 - 17 cubic meters)", "New Automobile (17 - 25 cubic meters)", "New Automobile (up to 16 cubic meters)", "New Automobile (16 - 20 cubic meters)", "New Automobile (20 - 25 cubic meters)", "Motorhome(s)", "Truck(s)", "Bus(es)", "Van(s)", "Chassis", "Travel trailer(s)", "Utility trailer(s)", "5th wheel trailer(s)", "Agriculture Equipment", "Construction Equipment", "Forestry Equipment", "Mining Equipment", "Handling Equipment", "Crane(s)", "Tractor(s)", "Boat(s) on Trailer", "Boat(s) on Cradle", "Static", "Static Machinery", "Special Purpose Vehicles", "Motorcycle(s)", "All-Terrain Vehicle(s)", "Jet Ski(s) (up to 2 cbm)", "Aircraft" }));

        jLabel64.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel64.setText("Commodity Description");

        updateCommodityDescriptionTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel65.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel65.setText("Handling Instructions");

        updateHandlingInstructionsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Self-propelled", "Towable", "Static", "MAFI", "Forkliftable", "Static (MAFI)", "Static (Forkliftable)" }));

        jLabel66.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel66.setText("OFT");

        jLabel67.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel67.setText("BAF");

        jLabel68.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel68.setText("ECA");

        jLabel69.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel69.setText("THC");

        jLabel70.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel70.setText("WFG");

        jLabel71.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel71.setText("Documentation Fee");

        updateOFTTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateOFTTextField.setNextFocusableComponent(updateOftUnitComboBox);

        updateBAFTextField.setNextFocusableComponent(updateBafIncludedCheckBox);

        updateEcaBafTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateEcaBafTextField.setNextFocusableComponent(updateEcaComboBox);

        updateTHCTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        updateWfgTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        updateOftUnitComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateOftUnitComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));
        updateOftUnitComboBox.setNextFocusableComponent(updateBAFTextField);

        updateBafIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateBafIncludedCheckBox.setText("Included");
        updateBafIncludedCheckBox.setNextFocusableComponent(updateEcaBafTextField);

        updateDocumentationFeeComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateDocumentationFeeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "$25 per B/L", "$50 per B/L", "$75 per B/L", " " }));

        updateDocFeeIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateDocFeeIncludedCheckBox.setText("Included");

        updateEcaComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateEcaComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));

        updateThcComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateThcComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));

        updateWfgComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateWfgComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));

        updateEcaIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateEcaIncludedCheckBox.setText("Included");

        updateThcIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateThcIncludedCheckBox.setText("Included");

        updateWfgIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateWfgIncludedCheckBox.setText("Included");

        updateTradeLane.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateTradeLane.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Trans-Atlantic EB", "Trans-Atlantic WB", "North Atlantic Shuttle EB", "North Atlantic Shuttle WB", "NAX EB", "NAX WB", "ECAMS NB", "ECAMS SB", "Foreign to Foreign" }));

        jLabel76.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel76.setText("Tshp2");

        updateTshp2TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        updateWarRiskCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateWarRiskCheckBox.setText("War Risk");

        updateCommentsTextArea.setColumns(15);
        updateCommentsTextArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateCommentsTextArea.setLineWrap(true);
        updateCommentsTextArea.setRows(5);
        updateCommentsTextArea.setWrapStyleWord(true);
        updateCommentsTextArea.setPreferredSize(new java.awt.Dimension(169, 174));
        updateCommentsTextArea.setLineWrap(true);
        jScrollPane5.setViewportView(updateCommentsTextArea);

        updateSpotRateCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateSpotRateCheckBox.setText("Spot Rate");

        updateEditQuoteButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updateEditQuoteButton.setText("Update");
        updateEditQuoteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEditQuoteButtonActionPerformed(evt);
            }
        });

        updateCancelButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updateCancelButton.setText("Cancel");
        updateCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCancelButtonActionPerformed(evt);
            }
        });

        updateBookedCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateBookedCheckBox.setText("Booked");

        jLabel78.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel78.setText("Quote ID:");

        updateQuoteIDTextArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        updateQuoteIDSearchButton.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateQuoteIDSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/search_icon.png"))); // NOI18N
        updateQuoteIDSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateQuoteIDSearchButtonActionPerformed(evt);
            }
        });

        updateDeclineCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateDeclineCheckBox.setText("Decline");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Internal Comments");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        updateEditQuoteCustomerNameLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateEditQuoteCustomerNameLabel.setText("N/A");

        jLabel16.setText("$");

        jLabel18.setText("$");

        jLabel57.setText("$");

        jLabel72.setText("$");

        jLabel73.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel73.setText("%");

        updateThcAttachedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateThcAttachedCheckBox.setText("Attached to Email");

        updateWfgAttachedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateWfgAttachedCheckBox.setText("Attached to Email");

        updateEditQuoteCustomerNameLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateEditQuoteCustomerNameLabel1.setText("Author:");

        updateEditQuoteCustomerNameLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateEditQuoteCustomerNameLabel2.setText("Last Updated By:");

        authorLabel.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        authorLabel.setText("N/A");

        lastUpdatedByLabel.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        lastUpdatedByLabel.setText("N/A");

        authorLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        authorLabel1.setText("|");

        jLabel124.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel124.setText("Current Alpha Numeral:");

        currentAlphaNumeralLabel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        currentAlphaNumeralLabel.setText("N/A");

        jLabel129.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel129.setText("Updated Alpha Numeral:");

        updateAlphaNumeralTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        authorLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        authorLabel2.setText("|");

        updateQuoteAccessoriesCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateQuoteAccessoriesCheckBox.setText("Accessories");

        updateEditPackingListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Commodity", "Qty", "L(cm)", "W(cm)", "H(cm)", "Kgs", "L(in)", "W(in)", "H(in)", "CBM", "Lbs", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        updateEditPackingListTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane12.setViewportView(updateEditPackingListTable);
        if (updateEditPackingListTable.getColumnModel().getColumnCount() > 0) {
            updateEditPackingListTable.getColumnModel().getColumn(0).setPreferredWidth(150);
            updateEditPackingListTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(3).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(4).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(5).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(6).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(7).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(8).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(9).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(10).setPreferredWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(10).setMaxWidth(50);
            updateEditPackingListTable.getColumnModel().getColumn(11).setMinWidth(-9999);
            updateEditPackingListTable.getColumnModel().getColumn(11).setPreferredWidth(-9999);
            updateEditPackingListTable.getColumnModel().getColumn(11).setMaxWidth(-9999);
        }

        jLabel54.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Packing List");
        jLabel54.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        updateQuoteMAFIMinimumCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateQuoteMAFIMinimumCheckBox.setText("MAFI Minimum: $");

        updateEditMAFIMinimumTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        updateContractRateCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateContractRateCheckBox.setText("Contract Rate");

        updateDeclineComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateDeclineComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "No Service", "OFT", "Response", "Schedule", "Space", "Cargo Size", "Operational Restrictions", "Other (See Comments)" }));

        jSeparator10.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        updateEditQuoteCustomerNameLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateEditQuoteCustomerNameLabel3.setText("Company Name:");

        editCustomerButton.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        editCustomerButton.setText("Edit");
        editCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCustomerButtonActionPerformed(evt);
            }
        });

        quoteFeedbackCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        quoteFeedbackCheckBox.setText("Feedback");

        quoteFeedbackComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Rate Not Competitive", "Scheduling", "Moved with Other Carrier", "No Feedback From Owner", "No Feedback from FF/NVOCC", "Not Enough Space", "Operational Restrictions", "Other" }));

        quoteFeedbackTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jSeparator9.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel80.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel80.setText("Contact Email:");

        updateContactEmailTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel82.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel82.setText("Contact Name:");

        updateContactNameTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jSeparator18.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator18.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator18.setOrientation(javax.swing.SwingConstants.VERTICAL);

        editQuoteDuplicateRateCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        editQuoteDuplicateRateCheckBox.setText("Duplicate Rate");

        updateEditQuoteCustomerNameLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateEditQuoteCustomerNameLabel4.setText("Date Created:");

        quoteCreatedLabel.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        quoteCreatedLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        quoteCreatedLabel.setText("N/A");

        authorLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        authorLabel3.setText("|");

        updateEditQuoteCustomerNameLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        updateEditQuoteCustomerNameLabel5.setText("Last Updated:");

        quoteLastUpdatedLabel.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        quoteLastUpdatedLabel.setText("N/A");

        updateEditQuoteAddRowButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updateEditQuoteAddRowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RORO/add_row_icon.png"))); // NOI18N
        updateEditQuoteAddRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEditQuoteAddRowButtonActionPerformed(evt);
            }
        });

        calculateCubicMetersButtonEditPL.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        calculateCubicMetersButtonEditPL.setForeground(new java.awt.Color(240, 240, 240));
        calculateCubicMetersButtonEditPL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/math_icon.png"))); // NOI18N
        calculateCubicMetersButtonEditPL.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        calculateCubicMetersButtonEditPL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateCubicMetersButtonEditPLActionPerformed(evt);
            }
        });

        updateEditSailingScheduleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Vessel/Voyage", "Sail Date", "T/T", "Trans-shipment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        updateEditSailingScheduleTable.getTableHeader().setReorderingAllowed(false);
        updateEditSailingScheduleTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                updateEditSailingScheduleTableKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                updateEditSailingScheduleTableKeyTyped(evt);
            }
        });
        jScrollPane14.setViewportView(updateEditSailingScheduleTable);
        if (updateEditSailingScheduleTable.getColumnModel().getColumnCount() > 0) {
            updateEditSailingScheduleTable.getColumnModel().getColumn(0).setMinWidth(150);
            updateEditSailingScheduleTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        jLabel84.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setText("Schedule");
        jLabel84.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        updateEditSailingScheduleAddRowButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updateEditSailingScheduleAddRowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RORO/add_row_icon.png"))); // NOI18N
        updateEditSailingScheduleAddRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEditSailingScheduleAddRowButtonActionPerformed(evt);
            }
        });

        jLabel95.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel95.setText("Shipper Comments");
        jLabel95.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        updateShipperCommentsTextArea.setColumns(15);
        updateShipperCommentsTextArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        updateShipperCommentsTextArea.setLineWrap(true);
        updateShipperCommentsTextArea.setRows(5);
        updateShipperCommentsTextArea.setWrapStyleWord(true);
        updateShipperCommentsTextArea.setPreferredSize(new java.awt.Dimension(169, 174));
        updateCommentsTextArea.setLineWrap(true);
        jScrollPane17.setViewportView(updateShipperCommentsTextArea);

        javax.swing.GroupLayout updateEditQuotePanelLayout = new javax.swing.GroupLayout(updateEditQuotePanel);
        updateEditQuotePanel.setLayout(updateEditQuotePanelLayout);
        updateEditQuotePanelLayout.setHorizontalGroup(
            updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(jLabel82)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateContactNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel80)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateContactEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateCommodityDescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateOFTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateOftUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateBAFTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateBafIncludedCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEcaBafTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEcaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEcaIncludedCheckBox))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(jLabel69)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateTHCTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateThcComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateThcIncludedCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateThcAttachedCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateWfgTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateWfgComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateWfgIncludedCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateWfgAttachedCheckBox))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(updateEditQuoteCustomerNameLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(authorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(authorLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEditQuoteCustomerNameLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastUpdatedByLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel124)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentAlphaNumeralLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(authorLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel129)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateAlphaNumeralTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEditQuoteCustomerNameLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quoteCreatedLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(authorLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEditQuoteCustomerNameLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quoteLastUpdatedLabel))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateCommodityClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateHandlingInstructionsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateQuoteAccessoriesCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateQuoteMAFIMinimumCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEditMAFIMinimumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateTradeLane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updatePOLTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updatePODTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateTshp1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel76)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateTshp2TextField))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(updateWarRiskCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateDocumentationFeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateDocFeeIncludedCheckBox)
                        .addGap(4, 4, 4)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateContractRateCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateSpotRateCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editQuoteDuplicateRateCheckBox))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(jLabel78)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateQuoteIDTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateQuoteIDSearchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEditQuoteCustomerNameLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEditQuoteCustomerNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editCustomerButton))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(updateBookedCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEditQuoteBookingNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateDeclineCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateDeclineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quoteFeedbackCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quoteFeedbackComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quoteFeedbackTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                                    .addComponent(editQuoteIncludeSailingScheduleCheckBox)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(updateEditSailingScheduleAddRowButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                                .addComponent(editQuoteIncludeShipperCommentsCheckBox)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator5)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updateEditQuotePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(updateEditQuoteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateCancelButton)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(updateEditQuoteAddRowButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calculateCubicMetersButtonEditPL)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        updateEditQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {updatePODTextField, updatePOLTextField, updateTshp1TextField, updateTshp2TextField});

        updateEditQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {updateBAFTextField, updateEcaBafTextField, updateEditQuoteBookingNumberTextField, updateOFTTextField, updateTHCTextField, updateWfgTextField});

        updateEditQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel16, jLabel18, jLabel57, jLabel73});

        updateEditQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {updateEcaComboBox, updateOftUnitComboBox, updateThcComboBox, updateWfgComboBox});

        updateEditQuotePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel54, jScrollPane12});

        updateEditQuotePanelLayout.setVerticalGroup(
            updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel78)
                    .addComponent(updateQuoteIDTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateQuoteIDSearchButton)
                    .addComponent(updateEditQuoteCustomerNameLabel3)
                    .addComponent(updateEditQuoteCustomerNameLabel)
                    .addComponent(editCustomerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel82)
                    .addComponent(updateContactNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80)
                    .addComponent(updateContactEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(updateEditQuoteCustomerNameLabel4)
                    .addComponent(quoteLastUpdatedLabel)
                    .addComponent(updateEditQuoteCustomerNameLabel5)
                    .addComponent(quoteCreatedLabel)
                    .addComponent(updateEditQuoteCustomerNameLabel1)
                    .addComponent(authorLabel)
                    .addComponent(authorLabel1)
                    .addComponent(updateEditQuoteCustomerNameLabel2)
                    .addComponent(lastUpdatedByLabel)
                    .addComponent(jLabel124)
                    .addComponent(currentAlphaNumeralLabel)
                    .addComponent(authorLabel2)
                    .addComponent(jLabel129)
                    .addComponent(updateAlphaNumeralTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(authorLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(updatePOLTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61)
                    .addComponent(updatePODTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62)
                    .addComponent(updateTshp1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel76)
                    .addComponent(updateTshp2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59)
                    .addComponent(updateTradeLane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(updateCommodityClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(updateHandlingInstructionsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateQuoteAccessoriesCheckBox)
                    .addComponent(updateQuoteMAFIMinimumCheckBox)
                    .addComponent(updateEditMAFIMinimumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(updateCommodityDescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel66)
                    .addComponent(jLabel16)
                    .addComponent(updateOFTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateOftUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67)
                    .addComponent(updateBAFTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73)
                    .addComponent(updateBafIncludedCheckBox)
                    .addComponent(jLabel68)
                    .addComponent(jLabel72)
                    .addComponent(updateEcaBafTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateEcaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateEcaIncludedCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel69)
                    .addComponent(jLabel57)
                    .addComponent(updateTHCTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateThcComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateThcIncludedCheckBox)
                    .addComponent(updateThcAttachedCheckBox)
                    .addComponent(jLabel70)
                    .addComponent(updateWfgTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(updateWfgComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateWfgIncludedCheckBox)
                    .addComponent(updateWfgAttachedCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(updateWarRiskCheckBox)
                    .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71)
                    .addComponent(updateDocumentationFeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateDocFeeIncludedCheckBox)
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateContractRateCheckBox)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateSpotRateCheckBox)
                    .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editQuoteDuplicateRateCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(updateBookedCheckBox)
                    .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateDeclineCheckBox)
                    .addComponent(updateDeclineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quoteFeedbackCheckBox)
                    .addComponent(quoteFeedbackComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quoteFeedbackTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateEditQuoteBookingNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(editQuoteIncludeSailingScheduleCheckBox)
                    .addComponent(updateEditSailingScheduleAddRowButton)
                    .addComponent(jLabel84)
                    .addComponent(jLabel54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(updateEditQuotePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel95)
                            .addComponent(editQuoteIncludeShipperCommentsCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(updateEditQuoteAddRowButton)
                        .addComponent(calculateCubicMetersButtonEditPL))
                    .addGroup(updateEditQuotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(updateCancelButton)
                        .addComponent(updateEditQuoteButton)))
                .addContainerGap())
        );

        updateEditQuotePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {authorLabel, authorLabel1, authorLabel2, currentAlphaNumeralLabel, editCustomerButton, jLabel124, jLabel129, jLabel78, lastUpdatedByLabel, updateAlphaNumeralTextField, updateEditQuoteCustomerNameLabel, updateEditQuoteCustomerNameLabel1, updateEditQuoteCustomerNameLabel2, updateEditQuoteCustomerNameLabel3, updateQuoteIDSearchButton, updateQuoteIDTextArea});

        updateEditQuotePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {quoteCreatedLabel, updateEditQuoteCustomerNameLabel4});

        mainPanel.add(updateEditQuotePanel, "card3");

        jLabel100.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel100.setText("Publishing Center");

        pQuoteIDTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        pQuoteIDButton.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pQuoteIDButton.setText("Go");
        pQuoteIDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pQuoteIDButtonActionPerformed(evt);
            }
        });

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

        jLabel105.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel105.setText("Commodity Class");

        jLabel106.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel106.setText("Commodity Description");

        jLabel107.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel107.setText("Comments:");
        jLabel107.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel108.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel108.setText("Bunker Adjustment:");

        jLabel109.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel109.setText("ECA Surcharge:");

        jLabel110.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel110.setText("Terminal Handling:");

        jLabel111.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel111.setText("Wharfage");

        jLabel112.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel112.setText("Documentation Fee");

        pOftTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        pBafTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pBafTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pBafTextFieldKeyTyped(evt);
            }
        });

        pEcaTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pEcaTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pEcaTextFieldKeyTyped(evt);
            }
        });

        pThcTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pThcTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pThcTextFieldKeyTyped(evt);
            }
        });

        pWfgTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pWfgTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pWfgTextFieldKeyTyped(evt);
            }
        });

        jLabel113.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel113.setText("POL");

        jLabel114.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel114.setText("POD");

        pBookingNumberTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel116.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel116.setText("Validity From/To:");

        pOftComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pOftComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));

        pWfgComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pWfgComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));
        pWfgComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pWfgComboBoxItemStateChanged(evt);
            }
        });

        pThcComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pThcComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));
        pThcComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pThcComboBoxItemStateChanged(evt);
            }
        });

        pEcaComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pEcaComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));
        pEcaComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pEcaComboBoxItemStateChanged(evt);
            }
        });

        pDocFeeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "$25 per B/L", "$50 per B/L", "$75 per B/L" }));
        pDocFeeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pDocFeeComboBoxItemStateChanged(evt);
            }
        });
        pDocFeeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pDocFeeComboBoxActionPerformed(evt);
            }
        });

        pCommodityClassComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Automobile/POV", "Automobile/POV (up to 14 cubic meters)", "Automobile/POV (14 - 17 cubic meters)", "Automobile/POV (17 - 25 cubic meters)", "Automobile/POV (up to 16 cubic meters)", "Automobile/POV (16 - 20 cubic meters)", "Automobile/POV (20 - 25 cubic meters)", "New Automobile", "New Automobile (up to 14 cubic meters)", "New Automobile (14 - 17 cubic meters)", "New Automobile (17 - 25 cubic meters)", "New Automobile (up to 16 cubic meters)", "New Automobile (16 - 20 cubic meters)", "New Automobile (20 - 25 cubic meters)", "Motorhome(s)", "Truck(s)", "Bus(es)", "Van(s)", "Chassis", "Travel trailer(s)", "Utility trailer(s)", "5th wheel trailer(s)", "Agriculture Equipment", "Construction Equipment", "Forestry Equipment", "Mining Equipment", "Handling Equipment", "Crane(s)", "Tractor(s)", "Boat(s) on Trailer", "Boat(s) on Cradle", "Static", "Static Machinery", "Special Purpose Vehicles", "Motorcycle(s)", "All-Terrain Vehicle(s)", "Jet Ski(s) (up to 2 cbm)" }));

        pWfgIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pWfgIncludedCheckBox.setText("Included");
        pWfgIncludedCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pWfgIncludedCheckBoxItemStateChanged(evt);
            }
        });

        pEcaIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pEcaIncludedCheckBox.setText("Included");
        pEcaIncludedCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pEcaIncludedCheckBoxItemStateChanged(evt);
            }
        });

        pThcIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pThcIncludedCheckBox.setText("Included");
        pThcIncludedCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pThcIncludedCheckBoxItemStateChanged(evt);
            }
        });

        pBafIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pBafIncludedCheckBox.setText("Included");
        pBafIncludedCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pBafIncludedCheckBoxItemStateChanged(evt);
            }
        });
        pBafIncludedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pBafIncludedCheckBoxActionPerformed(evt);
            }
        });

        pDocFeeIncludedCheckBox.setText("Included");
        pDocFeeIncludedCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pDocFeeIncludedCheckBoxItemStateChanged(evt);
            }
        });

        jLabel119.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel119.setText("Tariff Number(KKLU):");

        jLabel117.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel117.setText("Ocean Freight:");

        pCommentsTextArea.setColumns(20);
        pCommentsTextArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        pCommentsTextArea.setRows(5);
        pCommentsTextArea.setWrapStyleWord(true);
        pCommentsTextArea.setMaximumSize(new java.awt.Dimension(20, 10));
        pCommentsTextArea.setLineWrap(true);
        jScrollPane4.setViewportView(pCommentsTextArea);

        submitToPublishingPDFButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        submitToPublishingPDFButton.setText("Submit");
        submitToPublishingPDFButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitToPublishingPDFButtonActionPerformed(evt);
            }
        });

        cancelSpotFileButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        cancelSpotFileButton.setText("Cancel");
        cancelSpotFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelSpotFileButtonActionPerformed(evt);
            }
        });

        jLabel120.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel120.setText("Booking Number:");

        pWarRiskCheckBox.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        pWarRiskCheckBox.setText("War Risk");
        pWarRiskCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pWarRiskCheckBoxActionPerformed(evt);
            }
        });

        jLabel115.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel115.setText("%");

        jLabel123.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel123.setText("Handling Instructions");

        pHandlingInstructions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "Self-propelled", "Towable", "Static", "MAFI", "Forkliftable", "Static (MAFI)", "Static (Forkliftable)" }));

        jLabel125.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel125.setText("Storage:");

        storageTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        storageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                storageTextFieldKeyTyped(evt);
            }
        });

        storageUnitComboBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        storageUnitComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N/A", "W/M", "Unit", "CBM", "Short Ton", "Metric Ton", "MAFI", "per 40 cubic ft", "FAS", "Subject to local charges", "Linear Foot" }));
        storageUnitComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                storageUnitComboBoxItemStateChanged(evt);
            }
        });

        storageIncludedCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        storageIncludedCheckBox.setText("Included");
        storageIncludedCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                storageIncludedCheckBoxItemStateChanged(evt);
            }
        });

        thcSubjectToTariffCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        thcSubjectToTariffCheckBox.setText("Subject to Tariff");
        thcSubjectToTariffCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                thcSubjectToTariffCheckBoxItemStateChanged(evt);
            }
        });

        wfgSubjectToTariffCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        wfgSubjectToTariffCheckBox.setText("Subject to Tariff");
        wfgSubjectToTariffCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wfgSubjectToTariffCheckBoxItemStateChanged(evt);
            }
        });

        storageSubjectToTariffCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        storageSubjectToTariffCheckBox.setText("Subject to Tariff");
        storageSubjectToTariffCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                storageSubjectToTariffCheckBoxItemStateChanged(evt);
            }
        });

        ecaSubjectToTariffCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        ecaSubjectToTariffCheckBox.setText("Subject to Tariff");
        ecaSubjectToTariffCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ecaSubjectToTariffCheckBoxItemStateChanged(evt);
            }
        });

        bafSubjectToTariffCheckBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bafSubjectToTariffCheckBox.setText("Subject to Tariff");
        bafSubjectToTariffCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bafSubjectToTariffCheckBoxItemStateChanged(evt);
            }
        });

        jLabel121.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel121.setText("$");

        jLabel131.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel131.setText("$");

        jLabel132.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel132.setText("$");

        jLabel133.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel133.setText("$");

        jLabel134.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel134.setText("$");

        idTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RQS", "PID" }));

        saveChangesPublishingPDFButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        saveChangesPublishingPDFButton.setText("Update Filing");
        saveChangesPublishingPDFButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveChangesPublishingPDFButtonActionPerformed(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "KAM Publishing Only", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel138.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel138.setText("Commodity #:");

        jLabel135.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel135.setText("Description:");

        jLabel136.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel136.setText("Expiration:");

        jLabel137.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel137.setText("TLI #:");

        newFilingCompleteButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        newFilingCompleteButton.setText("Save & Send New Filing");
        newFilingCompleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFilingCompleteButtonActionPerformed(evt);
            }
        });

        newFilingCompleteButton1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        newFilingCompleteButton1.setText("Save & Send Existing Filing");
        newFilingCompleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFilingCompleteButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel138)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commodityNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel135)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel137)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tliNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel136)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expirationDateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(381, 381, 381)
                        .addComponent(newFilingCompleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newFilingCompleteButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel136)
                            .addComponent(expirationDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newFilingCompleteButton)
                            .addComponent(newFilingCompleteButton1)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(tliNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel137))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(commodityNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel138))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel135)))
                .addContainerGap())
        );

        jLabel139.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel139.setText("RQS");

        pQuoteNumberTextField.setEditable(false);
        pQuoteNumberTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel140.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel140.setText("PID");

        pIDTextField.setEditable(false);
        pIDTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        validityFromButton.setText("jButton1");

        validityToButton.setText("jButton1");

        jSeparator22.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator22.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout publishingCenterPanelLayout = new javax.swing.GroupLayout(publishingCenterPanel);
        publishingCenterPanel.setLayout(publishingCenterPanelLayout);
        publishingCenterPanelLayout.setHorizontalGroup(
            publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator8)
                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                        .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel132))
                            .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel133))
                            .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                .addComponent(jLabel125)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel134))
                            .addComponent(jLabel108)
                            .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel121))
                            .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                .addComponent(jLabel117)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel131)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pOftTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pBafTextField)
                            .addComponent(pEcaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(storageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pThcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pWfgTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pOftComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                        .addComponent(pThcComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pThcIncludedCheckBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(thcSubjectToTariffCheckBox))
                                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                        .addComponent(pWfgComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pWfgIncludedCheckBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(wfgSubjectToTariffCheckBox))
                                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                        .addComponent(storageUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(storageIncludedCheckBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(storageSubjectToTariffCheckBox))
                                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                        .addComponent(pEcaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pEcaIncludedCheckBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ecaSubjectToTariffCheckBox))
                                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel115)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pBafIncludedCheckBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bafSubjectToTariffCheckBox)))
                                .addGap(18, 18, 18)
                                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                        .addComponent(jLabel112)
                        .addGap(18, 18, 18)
                        .addComponent(pDocFeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pDocFeeIncludedCheckBox))
                    .addComponent(pWarRiskCheckBox)
                    .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 1145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 1184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                            .addComponent(submitToPublishingPDFButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(saveChangesPublishingPDFButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelSpotFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                            .addComponent(idTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(pQuoteIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pQuoteIDButton))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, publishingCenterPanelLayout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                        .addComponent(jLabel106)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pCommodityDescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                        .addComponent(jLabel105)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pCommodityClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel123)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pHandlingInstructions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                        .addComponent(jLabel116)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(validityFromButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(validityToButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                        .addComponent(jLabel119)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kkluNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel113)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pPolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel114)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pPodTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                        .addComponent(jLabel120)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pBookingNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel139)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pQuoteNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel140)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        publishingCenterPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {pBafTextField, pEcaTextField, pOftTextField, pThcTextField, pWfgTextField, storageTextField});

        publishingCenterPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelSpotFileButton, submitToPublishingPDFButton});

        publishingCenterPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {pDocFeeComboBox, pEcaComboBox, pOftComboBox, pThcComboBox, pWfgComboBox, storageUnitComboBox});

        publishingCenterPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel108, jLabel109, jLabel110, jLabel111, jLabel112, jLabel117, jLabel125});

        publishingCenterPanelLayout.setVerticalGroup(
            publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel100)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pQuoteIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pQuoteIDButton)
                    .addComponent(idTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pBookingNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel139)
                    .addComponent(pQuoteNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel140)
                    .addComponent(pIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel120))
                .addGap(18, 18, 18)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(validityFromButton)
                    .addComponent(validityToButton)
                    .addComponent(jLabel116))
                .addGap(18, 18, 18)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kkluNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel113)
                    .addComponent(pPolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel114)
                    .addComponent(jLabel119)
                    .addComponent(pPodTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel105)
                    .addComponent(pCommodityClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel123)
                    .addComponent(pHandlingInstructions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel106)
                    .addComponent(pCommodityDescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pOftTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pOftComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel131)
                    .addComponent(jLabel117))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pBafTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel115)
                    .addComponent(pBafIncludedCheckBox)
                    .addComponent(bafSubjectToTariffCheckBox)
                    .addComponent(jLabel108)
                    .addComponent(jLabel107))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(publishingCenterPanelLayout.createSequentialGroup()
                        .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(pEcaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pEcaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pEcaIncludedCheckBox)
                            .addComponent(ecaSubjectToTariffCheckBox)
                            .addComponent(jLabel121)
                            .addComponent(jLabel109))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(pThcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pThcComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pThcIncludedCheckBox)
                            .addComponent(thcSubjectToTariffCheckBox)
                            .addComponent(jLabel132)
                            .addComponent(jLabel110))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(pWfgComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pWfgIncludedCheckBox)
                            .addComponent(pWfgTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wfgSubjectToTariffCheckBox)
                            .addComponent(jLabel133)
                            .addComponent(jLabel111))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(storageUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(storageIncludedCheckBox)
                            .addComponent(storageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(storageSubjectToTariffCheckBox)
                            .addComponent(jLabel134)
                            .addComponent(jLabel125)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel112)
                    .addComponent(pDocFeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pDocFeeIncludedCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pWarRiskCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(publishingCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitToPublishingPDFButton)
                    .addComponent(cancelSpotFileButton)
                    .addComponent(saveChangesPublishingPDFButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(publishingCenterPanel, "card7");

        searchPanel.setLayout(new java.awt.CardLayout());

        queryTitleLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        queryTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        queryTitleLabel.setText("Search");

        searchResultsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ));
        jScrollPane10.setViewportView(searchResultsTable);

        jButton2.setText("New Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        exportResultsToExcelButton.setText("Export to Excel");
        exportResultsToExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportResultsToExcelButtonActionPerformed(evt);
            }
        });

        clearResultTable.setText("Clear Results");
        clearResultTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearResultTableActionPerformed(evt);
            }
        });

        jLabel55.setText("Query Name:");

        queryNameTextField.setEditable(false);

        jLabel56.setText("Date:");

        dateTextField.setEditable(false);

        savedSearchesButton.setText("Saved Searches");
        savedSearchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savedSearchesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator21)
                    .addComponent(queryTitleLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(savedSearchesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exportResultsToExcelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearResultTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(queryNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(queryTitleLabel)
                .addGap(1, 1, 1)
                .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(clearResultTable)
                    .addComponent(exportResultsToExcelButton)
                    .addComponent(queryNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56)
                    .addComponent(savedSearchesButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                .addContainerGap())
        );

        searchPanel.add(jPanel11, "card2");

        mainPanel.add(searchPanel, "card4");

        customerInformationPanel.setLayout(new java.awt.CardLayout());

        newCustomerButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        newCustomerButton.setText("New Customer");
        newCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCustomerButtonActionPerformed(evt);
            }
        });

        existingCustomerButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        existingCustomerButton.setText("Existing Customer");
        existingCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingCustomerButtonActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("New Customer");
        jLabel22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        submitNewCustomerInformation.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        submitNewCustomerInformation.setText("Submit");
        submitNewCustomerInformation.setNextFocusableComponent(cancelNewCustomerButton);
        submitNewCustomerInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitNewCustomerInformationActionPerformed(evt);
            }
        });

        cancelNewCustomerButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        cancelNewCustomerButton.setText("Cancel ");
        cancelNewCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewCustomerButtonActionPerformed(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Primary Contact Information"));

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel24.setText("First Name:");

        newCustomerFirstName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerFirstName.setNextFocusableComponent(newCustomerLastName);

        jLabel143.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel143.setText("Last Name:");

        newCustomerLastName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerLastName.setNextFocusableComponent(newCustomerOfficePhone);

        jLabel144.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel144.setText("Office Phone:");

        newCustomerOfficePhone.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerOfficePhone.setNextFocusableComponent(newCustomerMobilePhoneTextField);

        jLabel145.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel145.setText("Mobile Phone:");

        newCustomerMobilePhoneTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerMobilePhoneTextField.setNextFocusableComponent(newCustomerEmailTextField);

        newCustomerEmailTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerEmailTextField.setNextFocusableComponent(newCustomerTitleTextField);

        jLabel146.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel146.setText("Email:");

        jLabel147.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel147.setText("Title/Position:");

        newCustomerTitleTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel92.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel92.setText("Office Address:");

        jLabel94.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel94.setText("Suite/Apt.:");

        jLabel96.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel96.setText("City/County:");

        jLabel97.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel97.setText("Zip Code:");

        jLabel98.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel98.setText("State/Province:");

        jLabel99.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel99.setText("Country:");

        pcCountryTextField.setName(""); // NOI18N
        pcCountryTextField.setNextFocusableComponent(newCustomerCompanyTextField);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel143)
                    .addComponent(jLabel144)
                    .addComponent(jLabel145)
                    .addComponent(jLabel146)
                    .addComponent(jLabel147)
                    .addComponent(jLabel94)
                    .addComponent(jLabel92)
                    .addComponent(jLabel96)
                    .addComponent(jLabel97))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newCustomerLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(newCustomerOfficePhone, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(newCustomerMobilePhoneTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(newCustomerEmailTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(newCustomerTitleTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(newCustomerFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(pcOfficeAddressTextField)
                    .addComponent(pcSuiteTextField)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pcZipTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(pcCityTextField, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel99)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pcCountryTextField))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel98)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pcStateTextField)))))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel143, jLabel144, jLabel145, jLabel146, jLabel147, jLabel24, jLabel92, jLabel94, jLabel96, jLabel97});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(newCustomerFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel143)
                    .addComponent(newCustomerLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel144)
                    .addComponent(newCustomerOfficePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel145)
                    .addComponent(newCustomerMobilePhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel146)
                    .addComponent(newCustomerEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel147)
                    .addComponent(newCustomerTitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(pcOfficeAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(pcSuiteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel96)
                    .addComponent(pcCityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98)
                    .addComponent(pcStateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel97)
                    .addComponent(pcZipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99)
                    .addComponent(pcCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel146, jLabel147});

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Company Information"));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel25.setText("Company:");

        newCustomerCompanyTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerCompanyTextField.setNextFocusableComponent(newCustomerDBATextField);

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel26.setText("DBA:");

        newCustomerDBATextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerDBATextField.setNextFocusableComponent(newCustomerOTINumberTextField);

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel30.setText("OTI Number:");

        newCustomerOTINumberTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerOTINumberTextField.setNextFocusableComponent(newCustomerContractYesRadioButton);

        jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel37.setText("Contract:");

        buttonGroup1.add(newCustomerContractYesRadioButton);
        newCustomerContractYesRadioButton.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerContractYesRadioButton.setText("Yes");
        newCustomerContractYesRadioButton.setNextFocusableComponent(newCustomerNoContractRadioButton);
        newCustomerContractYesRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newCustomerContractYesRadioButtonMouseClicked(evt);
            }
        });

        buttonGroup1.add(newCustomerNoContractRadioButton);
        newCustomerNoContractRadioButton.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerNoContractRadioButton.setText("No");
        newCustomerNoContractRadioButton.setNextFocusableComponent(newCustomerContractNumberTextField);
        newCustomerNoContractRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newCustomerNoContractRadioButtonMouseClicked(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel38.setText("S/C Number:");

        newCustomerContractNumberTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        newCustomerContractNumberTextField.setNextFocusableComponent(newCustomerMainPhoneTextField);

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel43.setText("Expiration:");

        newCustomerContractExpirationDateTextField.setText("jButton1");
        newCustomerContractExpirationDateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCustomerContractExpirationDateTextFieldActionPerformed(evt);
            }
        });

        buttonGroup2.add(newCustomerFreightForwarderRadioButton);
        newCustomerFreightForwarderRadioButton.setText("FF");

        buttonGroup2.add(newCustomerNVOCCRadioButton);
        newCustomerNVOCCRadioButton.setText("NVOCC");

        buttonGroup2.add(newCustomerBCORadioButton);
        newCustomerBCORadioButton.setText("BCO");

        buttonGroup2.add(newCustomerOtherRadioButton);
        newCustomerOtherRadioButton.setText("Other");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel30)
                    .addComponent(jLabel38)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(newCustomerContractYesRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newCustomerNoContractRadioButton))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(newCustomerOTINumberTextField)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(newCustomerFreightForwarderRadioButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(newCustomerNVOCCRadioButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(newCustomerBCORadioButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(newCustomerOtherRadioButton))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(newCustomerContractNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel43)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(newCustomerContractExpirationDateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                        .addComponent(newCustomerCompanyTextField)
                        .addComponent(newCustomerDBATextField)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel25, jLabel26, jLabel30, jLabel38});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {newCustomerContractNumberTextField, newCustomerOTINumberTextField});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newCustomerCompanyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(newCustomerDBATextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel30)
                    .addComponent(newCustomerOTINumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newCustomerFreightForwarderRadioButton)
                    .addComponent(newCustomerNVOCCRadioButton)
                    .addComponent(newCustomerBCORadioButton)
                    .addComponent(newCustomerOtherRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel37)
                    .addComponent(newCustomerContractYesRadioButton)
                    .addComponent(newCustomerNoContractRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(newCustomerContractNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel43)
                        .addComponent(newCustomerContractExpirationDateTextField)))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Company Contact Information"));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel31.setText("Street Address:");

        newCustomerAddress1.setNextFocusableComponent(newCustomerAddress2);

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel32.setText("Suite/Apt.: ");

        newCustomerAddress2.setNextFocusableComponent(newCustomerCity);

        jLabel33.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel33.setText("City:");

        newCustomerCity.setNextFocusableComponent(newCustomerState);

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel35.setText("Zip:");

        newCustomerZipCode.setNextFocusableComponent(newCustomerCountry);

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel34.setText("State/Province:");

        newCustomerState.setNextFocusableComponent(newCustomerZipCode);

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel36.setText("Country:");

        newCustomerCountry.setNextFocusableComponent(newCustomerComments);

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel27.setText("Main Phone:");

        newCustomerMainPhoneTextField.setNextFocusableComponent(newCustomerSecondaryPhoneNumber);

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel28.setText("Secondary Phone:");

        newCustomerSecondaryPhoneNumber.setNextFocusableComponent(newCustomerFaxNumber);

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel29.setText("FAX:");

        newCustomerFaxNumber.setNextFocusableComponent(newCustomerCompanyEmailTextField);

        jLabel58.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel58.setText("Website:");

        newCustomerCompanyEmailTextField.setNextFocusableComponent(newCustomerAddress1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel33)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31)
                    .addComponent(jLabel58)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(newCustomerAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(newCustomerCompanyEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(newCustomerAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(newCustomerFaxNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(newCustomerCity, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(newCustomerZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel34)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(newCustomerState, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel36)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(newCustomerCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(newCustomerSecondaryPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newCustomerMainPhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel27)
                    .addComponent(newCustomerMainPhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel28)
                    .addComponent(newCustomerSecondaryPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel29)
                    .addComponent(newCustomerFaxNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel58)
                    .addComponent(newCustomerCompanyEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel31)
                    .addComponent(newCustomerAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(newCustomerAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(newCustomerCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(newCustomerState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel35)
                    .addComponent(newCustomerZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(newCustomerCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {newCustomerAddress1, newCustomerAddress2, newCustomerCity, newCustomerCompanyEmailTextField, newCustomerCountry, newCustomerFaxNumber, newCustomerMainPhoneTextField, newCustomerSecondaryPhoneNumber, newCustomerState, newCustomerZipCode});

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Comments"));

        newCustomerComments.setColumns(20);
        newCustomerComments.setRows(5);
        newCustomerComments.setNextFocusableComponent(submitNewCustomerInformation);
        newCustomerComments.setLineWrap(true);
        jScrollPane2.setViewportView(newCustomerComments);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout newCustomerPanelLayout = new javax.swing.GroupLayout(newCustomerPanel);
        newCustomerPanel.setLayout(newCustomerPanelLayout);
        newCustomerPanelLayout.setHorizontalGroup(
            newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newCustomerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.CENTER, newCustomerPanelLayout.createSequentialGroup()
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(newCustomerPanelLayout.createSequentialGroup()
                            .addGroup(newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(newCustomerButton)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(existingCustomerButton))))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(newCustomerPanelLayout.createSequentialGroup()
                .addGap(455, 455, 455)
                .addComponent(submitNewCustomerInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelNewCustomerButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        newCustomerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelNewCustomerButton, submitNewCustomerInformation});

        newCustomerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {existingCustomerButton, newCustomerButton});

        newCustomerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel3, jPanel4, jPanel5, jPanel6});

        newCustomerPanelLayout.setVerticalGroup(
            newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newCustomerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newCustomerButton)
                    .addComponent(existingCustomerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitNewCustomerInformation)
                    .addComponent(cancelNewCustomerButton))
                .addContainerGap())
        );

        customerInformationPanel.add(newCustomerPanel, "card2");

        existingCustomerPanel.setPreferredSize(new java.awt.Dimension(500, 129));

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Existing Customer");
        jLabel23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        newCustomerButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        newCustomerButton1.setText("New Customer");
        newCustomerButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCustomerButton1ActionPerformed(evt);
            }
        });

        existingCustomerButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        existingCustomerButton1.setText("Existing Customer");
        existingCustomerButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingCustomerButton1ActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel44.setText("Company ID or Name");

        searchExistingCustomersButton.setText("Go");
        searchExistingCustomersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchExistingCustomersButtonActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        searchExistingCustomersButton1.setText("Clear");
        searchExistingCustomersButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchExistingCustomersButton1ActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Primary Contact Information"));

        jLabel152.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel152.setText("First Name:");

        existingCompanyFirstNameTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel153.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel153.setText("Last Name:");

        existingCompanyLastNameTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel154.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel154.setText("Office Phone:");

        existingCompanyOfficePhoneTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyOfficePhoneTextField.setNextFocusableComponent(existingCompanyMobilePhoneTextField);

        jLabel155.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel155.setText("Mobile Phone:");

        existingCompanyMobilePhoneTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyMobilePhoneTextField.setNextFocusableComponent(existingCompanyEmailTextField);

        jLabel156.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel156.setText("Email:");

        existingCompanyEmailTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyEmailTextField.setNextFocusableComponent(existingCompanyTitleTextField);

        jLabel157.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel157.setText("Title/Position:");

        existingCompanyTitleTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel85.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel85.setText("Office Address:");

        jLabel86.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel86.setText("Suite/Apt.:");

        jLabel87.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel87.setText("City/County:");

        jLabel88.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel88.setText("Zip Code:");

        jLabel89.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel89.setText("Country:");

        jLabel90.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel90.setText("State/Province:");

        updatePCCountryTextField.setNextFocusableComponent(existingCompanyCompanyTextField);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel153))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel152)
                            .addComponent(jLabel154)
                            .addComponent(jLabel155)
                            .addComponent(jLabel156)
                            .addComponent(jLabel157))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(existingCompanyTitleTextField)
                            .addComponent(existingCompanyEmailTextField)
                            .addComponent(existingCompanyMobilePhoneTextField)
                            .addComponent(existingCompanyOfficePhoneTextField)
                            .addComponent(existingCompanyLastNameTextField)
                            .addComponent(existingCompanyFirstNameTextField)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel85)
                            .addComponent(jLabel86)
                            .addComponent(jLabel87)
                            .addComponent(jLabel88))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updatePCAddressTextfield)
                            .addComponent(updatePCSuiteTextField)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(updatePCCityTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(updatePCZipTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel90)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(updatePCStateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel89)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(updatePCCountryTextField)))))))
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel152, jLabel153, jLabel154, jLabel155, jLabel156, jLabel157, jLabel85, jLabel86, jLabel87, jLabel88});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel152)
                    .addComponent(existingCompanyFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel153)
                    .addComponent(existingCompanyLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel154)
                    .addComponent(existingCompanyOfficePhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel155)
                    .addComponent(existingCompanyMobilePhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel156)
                    .addComponent(existingCompanyEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel157)
                    .addComponent(existingCompanyTitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85)
                    .addComponent(updatePCAddressTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86)
                    .addComponent(updatePCSuiteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel87)
                    .addComponent(updatePCCityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90)
                    .addComponent(updatePCStateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(updatePCZipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89)
                    .addComponent(updatePCCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Company Contact Information"));

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel40.setText("Main Phone:");

        existingCompanyMainPhoneTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyMainPhoneTextField.setNextFocusableComponent(existingCompanySecondaryPhoneTextField);

        jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel41.setText("Secondary Phone:");

        existingCompanySecondaryPhoneTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanySecondaryPhoneTextField.setNextFocusableComponent(existingCompanyFaxNumberTextField);

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel45.setText("FAX:");

        existingCompanyFaxNumberTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyFaxNumberTextField.setNextFocusableComponent(existingCompanyEmailTextField);

        jLabel102.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel102.setText("Website:");

        existingCompanyMainEmailTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyMainEmailTextField.setNextFocusableComponent(existingCompanyAddress1TextField);

        existingCompanyAddress1TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyAddress1TextField.setNextFocusableComponent(existingCompanyAddress2TextField);

        jLabel47.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel47.setText("Suite/Apt.:");

        existingCompanyAddress2TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyAddress2TextField.setNextFocusableComponent(existingCompanyCityTextField);

        jLabel48.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel48.setText("City:");

        existingCompanyCityTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyCityTextField.setNextFocusableComponent(existingCompanyStateTextField);

        jLabel49.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel49.setText("Zip:");

        existingCompanyZipTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyZipTextField.setNextFocusableComponent(existingCompanyCountryTextField);

        jLabel50.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel50.setText("State/Province:");

        existingCompanyStateTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyStateTextField.setNextFocusableComponent(existingCompanyZipTextField);

        jLabel51.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel51.setText("Country:");

        existingCompanyCountryTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyCountryTextField.setNextFocusableComponent(existingCompanyCommentsTextArea);

        jLabel91.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel91.setText("Street Address:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel49)
                    .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(existingCompanyZipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel51)
                        .addGap(2, 2, 2)
                        .addComponent(existingCompanyCountryTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(existingCompanyCityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(existingCompanyStateTextField))
                    .addComponent(existingCompanyAddress2TextField)
                    .addComponent(existingCompanyAddress1TextField)
                    .addComponent(existingCompanyFaxNumberTextField)
                    .addComponent(existingCompanySecondaryPhoneTextField)
                    .addComponent(existingCompanyMainPhoneTextField)
                    .addComponent(existingCompanyMainEmailTextField))
                .addGap(14, 30, Short.MAX_VALUE))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel102, jLabel40, jLabel41, jLabel45, jLabel47, jLabel48});

        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel40)
                    .addComponent(existingCompanyMainPhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel41)
                    .addComponent(existingCompanySecondaryPhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(existingCompanyFaxNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(existingCompanyMainEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(existingCompanyAddress1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel91))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(existingCompanyAddress2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel50)
                        .addComponent(existingCompanyStateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel48)
                        .addComponent(existingCompanyCityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel49)
                        .addComponent(existingCompanyZipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel51)
                        .addComponent(existingCompanyCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {existingCompanyAddress1TextField, existingCompanyAddress2TextField, existingCompanyCityTextField, existingCompanyCountryTextField, existingCompanyFaxNumberTextField, existingCompanyMainEmailTextField, existingCompanyMainPhoneTextField, existingCompanySecondaryPhoneTextField, existingCompanyStateTextField, existingCompanyZipTextField});

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Company Information"));

        jLabel160.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel160.setText("Company:");

        existingCompanyCompanyTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyCompanyTextField.setNextFocusableComponent(existingCompanyDBATextField);

        jLabel161.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel161.setText("DBA:");

        existingCompanyDBATextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyDBATextField.setNextFocusableComponent(existingCompanyOTINumberTextField);

        jLabel162.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel162.setText("OTI Number:");

        existingCompanyOTINumberTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyOTINumberTextField.setNextFocusableComponent(existingCompanyContractYesRadioButton);

        jLabel163.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel163.setText("Contract:");

        buttonGroup4.add(existingCompanyContractYesRadioButton);
        existingCompanyContractYesRadioButton.setText("Yes");
        existingCompanyContractYesRadioButton.setNextFocusableComponent(existingCompanyNoContractRadioButton);
        existingCompanyContractYesRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                existingCompanyContractYesRadioButtonMouseClicked(evt);
            }
        });
        existingCompanyContractYesRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingCompanyContractYesRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup4.add(existingCompanyNoContractRadioButton);
        existingCompanyNoContractRadioButton.setText("No");
        existingCompanyNoContractRadioButton.setNextFocusableComponent(existingCompanyContractNumberTextField);
        existingCompanyNoContractRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                existingCompanyNoContractRadioButtonMouseClicked(evt);
            }
        });

        jLabel164.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel164.setText("S/C Number:");

        existingCompanyContractNumberTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyContractNumberTextField.setNextFocusableComponent(existingCompanyMainPhoneTextField);

        jLabel167.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel167.setText("ID:");

        existingCompanyIDTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyIDTextField.setNextFocusableComponent(existingCompanyDBATextField);

        jLabel168.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel168.setText("Expiration:");

        existingCompanyContractEpirationTextField.setText("jButton1");

        buttonGroup5.add(existingCustomerFreightForwarderRadioButton);
        existingCustomerFreightForwarderRadioButton.setText("FF");

        buttonGroup5.add(existingCustomerNVOCCRadioButton);
        existingCustomerNVOCCRadioButton.setText("NVOCC");

        buttonGroup5.add(existingCustomerBCORadioButton);
        existingCustomerBCORadioButton.setText("BCO");

        buttonGroup5.add(existingCustomerOtherRadioButton);
        existingCustomerOtherRadioButton.setText("Other");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel164)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(existingCompanyContractNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel168)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(existingCompanyContractEpirationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel160, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel161)
                            .addComponent(jLabel162)
                            .addComponent(jLabel167, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel163))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(existingCompanyDBATextField)
                            .addComponent(existingCompanyCompanyTextField)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(existingCompanyContractYesRadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(existingCompanyNoContractRadioButton))
                                    .addComponent(existingCompanyIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(existingCompanyOTINumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(existingCustomerFreightForwarderRadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(existingCustomerNVOCCRadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(existingCustomerBCORadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(existingCustomerOtherRadioButton)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel160, jLabel161, jLabel162, jLabel164, jLabel167});

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {existingCompanyContractNumberTextField, existingCompanyOTINumberTextField});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(existingCompanyIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel167))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(existingCompanyCompanyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel160))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel161)
                    .addComponent(existingCompanyDBATextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(existingCustomerFreightForwarderRadioButton)
                    .addComponent(existingCustomerNVOCCRadioButton)
                    .addComponent(existingCustomerBCORadioButton)
                    .addComponent(existingCustomerOtherRadioButton)
                    .addComponent(jLabel162)
                    .addComponent(existingCompanyOTINumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(existingCompanyContractYesRadioButton)
                    .addComponent(existingCompanyNoContractRadioButton)
                    .addComponent(jLabel163))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel164)
                    .addComponent(existingCompanyContractNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel168)
                    .addComponent(existingCompanyContractEpirationTextField))
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Comments"));

        existingCompanyCommentsTextArea.setColumns(20);
        existingCompanyCommentsTextArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        existingCompanyCommentsTextArea.setLineWrap(true);
        existingCompanyCommentsTextArea.setRows(5);
        existingCompanyCommentsTextArea.setWrapStyleWord(true);
        existingCompanyCommentsTextArea.setLineWrap(true);
        jScrollPane8.setViewportView(existingCompanyCommentsTextArea);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addContainerGap())
        );

        editUpdateCustomerInformationButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        editUpdateCustomerInformationButton.setText("Save Changes");
        editUpdateCustomerInformationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUpdateCustomerInformationButtonActionPerformed(evt);
            }
        });

        editUpdateCustomerQuotesButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        editUpdateCustomerQuotesButton.setText("Previous Quotes");
        editUpdateCustomerQuotesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUpdateCustomerQuotesButtonActionPerformed(evt);
            }
        });

        editUpdateCustomerBookingsButton.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        editUpdateCustomerBookingsButton.setText("Previous Bookings");
        editUpdateCustomerBookingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUpdateCustomerBookingsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout existingCustomerPanelLayout = new javax.swing.GroupLayout(existingCustomerPanel);
        existingCustomerPanel.setLayout(existingCustomerPanelLayout);
        existingCustomerPanelLayout.setHorizontalGroup(
            existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(existingCustomerPanelLayout.createSequentialGroup()
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(existingCustomerPanelLayout.createSequentialGroup()
                        .addComponent(newCustomerButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(existingCustomerButton1))
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(existingCustomerPanelLayout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(existingCompanyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchExistingCustomersButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchExistingCustomersButton1)))
                .addContainerGap())
            .addGroup(existingCustomerPanelLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addGroup(existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(existingCustomerPanelLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, existingCustomerPanelLayout.createSequentialGroup()
                        .addComponent(editUpdateCustomerInformationButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editUpdateCustomerQuotesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editUpdateCustomerBookingsButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, existingCustomerPanelLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(existingCustomerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addGap(12, 12, 12))
        );

        existingCustomerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {existingCustomerButton1, newCustomerButton1});

        existingCustomerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel10, jPanel7, jPanel8, jPanel9});

        existingCustomerPanelLayout.setVerticalGroup(
            existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(existingCustomerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(existingCustomerButton1)
                    .addComponent(newCustomerButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(searchExistingCustomersButton)
                    .addComponent(existingCompanyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchExistingCustomersButton1))
                .addGroup(existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(existingCustomerPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(existingCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(editUpdateCustomerInformationButton)
                            .addComponent(editUpdateCustomerQuotesButton)
                            .addComponent(editUpdateCustomerBookingsButton)))
                    .addGroup(existingCustomerPanelLayout.createSequentialGroup()
                        .addGap(698, 698, 698)
                        .addComponent(jLabel52)))
                .addContainerGap())
        );

        customerInformationPanel.add(existingCustomerPanel, "card3");

        mainPanel.add(customerInformationPanel, "card6");

        UserInformationCenterButton.setText("User Information");
        UserInformationCenterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserInformationCenterButtonActionPerformed(evt);
            }
        });

        newQuoteButton.setText("New Quote");
        newQuoteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newQuoteButtonActionPerformed(evt);
            }
        });

        updateEditQuoteButton1.setText("Update/Edit Quote");
        updateEditQuoteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEditQuoteButton1ActionPerformed(evt);
            }
        });

        publishingCenterButton.setText("Publishing Center");
        publishingCenterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publishingCenterButtonActionPerformed(evt);
            }
        });

        searchCenterButton.setText("Search Center");
        searchCenterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCenterButtonActionPerformed(evt);
            }
        });

        customerInformationCenterButton.setText("Customer Center");
        customerInformationCenterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerInformationCenterButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UserInformationCenterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(newQuoteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(updateEditQuoteButton1)
                .addGap(0, 0, 0)
                .addComponent(publishingCenterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(searchCenterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(customerInformationCenterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1250, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newQuoteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(updateEditQuoteButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(publishingCenterButton, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(searchCenterButton, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(customerInformationCenterButton, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(UserInformationCenterButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jScrollPane15.setViewportView(jPanel13);

        getContentPane().add(jScrollPane15);

        jMenuBar1.setBackground(java.awt.Color.red);
        jMenuBar1.setForeground(java.awt.Color.red);

        newCustomerMenuItem.setText("File");
        newCustomerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCustomerMenuItemActionPerformed(evt);
            }
        });

        newQuoteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newQuoteMenuItem.setText("New Quote");
        newQuoteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newQuoteMenuItemActionPerformed(evt);
            }
        });
        newCustomerMenuItem.add(newQuoteMenuItem);

        NewCustMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        NewCustMenuItem.setText("New Customer");
        NewCustMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewCustMenuItemActionPerformed(evt);
            }
        });
        newCustomerMenuItem.add(NewCustMenuItem);

        salesCenterMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        salesCenterMenuItem.setText("Sales Center");
        salesCenterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesCenterMenuItemActionPerformed(evt);
            }
        });
        newCustomerMenuItem.add(salesCenterMenuItem);

        closeApplicationMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        closeApplicationMenuItem.setText("Close");
        closeApplicationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeApplicationMenuItemActionPerformed(evt);
            }
        });
        newCustomerMenuItem.add(closeApplicationMenuItem);

        jMenuBar1.add(newCustomerMenuItem);

        EditMenuItem.setText("Edit");

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyMenuItem.setText("Copy");
        copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });
        EditMenuItem.add(copyMenuItem);

        cutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutMenuItem.setText("Cut");
        cutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuItemActionPerformed(evt);
            }
        });
        EditMenuItem.add(cutMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteMenuItem.setText("Paste");
        pasteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuItemActionPerformed(evt);
            }
        });
        EditMenuItem.add(pasteMenuItem);

        jMenuBar1.add(EditMenuItem);

        setJMenuBar(jMenuBar1);

        getAccessibleContext().setAccessibleDescription("");
        getAccessibleContext().setAccessibleParent(mainPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeApplicationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeApplicationMenuItemActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeApplicationMenuItemActionPerformed

    private void newQuoteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newQuoteButtonActionPerformed
        // TODO add your handling code here:
        newQuotePanel.setVisible(true);
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(false);
        searchPanel.setVisible(false);
        customerInformationPanel.setVisible(false);
        publishingCenterPanel.setVisible(false);

    }//GEN-LAST:event_newQuoteButtonActionPerformed

    private void updateEditQuoteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEditQuoteButton1ActionPerformed
        // TODO add your handling code here:
        newQuotePanel.setVisible(false);
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(true);
        searchPanel.setVisible(false);
        customerInformationPanel.setVisible(false);
        publishingCenterPanel.setVisible(false);

        //Clears the update quote panel inputs and tables. 
        ClearUpdateQuotePanel();

    }//GEN-LAST:event_updateEditQuoteButton1ActionPerformed

    private void existingCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingCustomerButtonActionPerformed
        // TODO add your handling code here:
        existingCustomerPanel.setVisible(true);
        newCustomerPanel.setVisible(false);

        ClearExistingCustomerPanel();
        ClearNewCustomerPanel();
    }//GEN-LAST:event_existingCustomerButtonActionPerformed

    private void newCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCustomerButtonActionPerformed
        // TODO add your handling code here:
        newCustomerPanel.setVisible(true);
        existingCustomerPanel.setVisible(false);

        ClearNewCustomerPanel();
        ClearExistingCustomerPanel();
    }//GEN-LAST:event_newCustomerButtonActionPerformed

    private void submitNewCustomerInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitNewCustomerInformationActionPerformed
        // TODO add your handling code here:
        String customerFirstName = newCustomerFirstName.getText();
        String customerLastName = newCustomerLastName.getText();
        String officePhone = newCustomerOfficePhone.getText();
        String mobilePhone = newCustomerMobilePhoneTextField.getText();
        String email = newCustomerEmailTextField.getText();
        String title = newCustomerTitleTextField.getText();
        String contact_office_address = pcOfficeAddressTextField.getText();
        String contact_office_suite = pcSuiteTextField.getText();
        String contact_office_city = pcCityTextField.getText();
        String contact_office_state = pcStateTextField.getText();
        String contact_office_zip_code = pcZipTextField.getText();
        String contact_office_country = pcCountryTextField.getText();
        String company = newCustomerCompanyTextField.getText();
        String dba = newCustomerDBATextField.getText();
        String oti = newCustomerOTINumberTextField.getText();
        Boolean freight_forwarder = newCustomerFreightForwarderRadioButton.isSelected();
        Boolean NVOCC = newCustomerNVOCCRadioButton.isSelected();
        Boolean BCO = newCustomerBCORadioButton.isSelected();
        Boolean other = newCustomerOtherRadioButton.isSelected();
        String mainPhone = newCustomerMainPhoneTextField.getText();
        String secondaryPhone = newCustomerSecondaryPhoneNumber.getText();
        String fax = newCustomerFaxNumber.getText();
        String address1 = newCustomerAddress1.getText();
        String address2 = newCustomerAddress2.getText();
        String city = newCustomerCity.getText();
        String state = newCustomerState.getText();
        String zip = newCustomerZipCode.getText();
        String country = newCustomerCountry.getText();
        Boolean contractYes = newCustomerContractYesRadioButton.isSelected();
        Boolean contractNo = newCustomerNoContractRadioButton.isSelected();
        String contractNumber = newCustomerContractNumberTextField.getText();
        String contractExpirationDate = newCustomerContractExpirationDatePicker.getJFormattedTextField().getText();
        String comments = newCustomerComments.getText();
        Boolean contract = null;
        if (contractYes == true && contractNo == false) {
            contract = true;
        } else if (contractNo == true && contractYes == false) {
            contract = false;
        } else if (contractNo == false && contractYes == false) {
            contract = false;
        }

        Connection conn = new DBConnection().connect();
        String checkSql = "SELECT * FROM rorocustomers WHERE company='" + company + "';";

        try {
            PreparedStatement ps1 = conn.prepareStatement(checkSql);
            ResultSet rs1 = ps1.executeQuery(checkSql);
            if (rs1.next()) {
                JOptionPane.showMessageDialog(null, company + " already exists");
            } else {

                String sql = "INSERT INTO rorocustomers (firstname, lastname, officephone, mobilephone, email, title, contact_office_address, contact_office_address2, contact_office_city, contact_office_state, contact_office_zip_code, contact_office_country, company, dba, oti, freight_forwarder, NVOCC, BCO, Other, mainphone, secondaryphone, fax, address1, address2, city, state, zip, country, contract, contractnumber, contractExpiration, comments) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, customerFirstName);
                    ps.setString(2, customerLastName);
                    ps.setString(3, officePhone);
                    ps.setString(4, mobilePhone);
                    ps.setString(5, email);
                    ps.setString(6, title);
                    ps.setString(7, contact_office_address);
                    ps.setString(8, contact_office_suite);
                    ps.setString(9, contact_office_city);
                    ps.setString(10, contact_office_state);
                    ps.setString(11, contact_office_zip_code);
                    ps.setString(12, contact_office_country);
                    ps.setString(13, company);
                    ps.setString(14, dba);
                    ps.setString(15, oti);
                    ps.setBoolean(16, freight_forwarder);
                    ps.setBoolean(17, NVOCC);
                    ps.setBoolean(18, BCO);
                    ps.setBoolean(19, other);
                    ps.setString(20, mainPhone);
                    ps.setString(21, secondaryPhone);
                    ps.setString(22, fax);
                    ps.setString(23, address1);
                    ps.setString(24, address2);
                    ps.setString(25, city);
                    ps.setString(26, state);
                    ps.setString(27, zip);
                    ps.setString(28, country);
                    ps.setBoolean(29, contract);
                    ps.setString(30, contractNumber);
                    ps.setString(31, contractExpirationDate);
                    ps.setString(32, comments);

                    ps.executeUpdate();
                    ResultSet keys = ps.getGeneratedKeys();
                    int lastKey = 1;
                    while (keys.next()) {
                        lastKey = keys.getInt(1);
                    }
                    JOptionPane.showMessageDialog(null, company + " has been successfully uploaded to the database. ID: " + lastKey);

                    ClearExistingCustomerPanel();
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }
            }
            ClearNewCustomerPanel();
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error submitting new customer:\n" + e.getMessage());
        }
    }//GEN-LAST:event_submitNewCustomerInformationActionPerformed

    private void cancelNewCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewCustomerButtonActionPerformed
        // Clears the new customer panel and resets the inputs
        ClearNewCustomerPanel();
    }//GEN-LAST:event_cancelNewCustomerButtonActionPerformed

    private void newQuoteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newQuoteMenuItemActionPerformed
        // Shows the new quote panel
        newQuotePanel.setVisible(true);
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(false);
        searchPanel.setVisible(false);
        customerInformationPanel.setVisible(false);
    }//GEN-LAST:event_newQuoteMenuItemActionPerformed

    private void NewCustMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewCustMenuItemActionPerformed
        // TODO add your handling code here:
        newQuotePanel.setVisible(false);
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(false);
        searchPanel.setVisible(false);
        customerInformationPanel.setVisible(true);
    }//GEN-LAST:event_NewCustMenuItemActionPerformed

    private void publishingCenterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publishingCenterButtonActionPerformed
        /**
         * The Publishing Center allows users to create spot rates based off of
         * previously made quotes Also allows publishing to view the spot rate
         * and assign TLI #, etc. and send publishing confirmation
         */

        // Display the publishing center
        newQuotePanel.setVisible(false);
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(false);
        searchPanel.setVisible(false);
        customerInformationPanel.setVisible(false);
        publishingCenterPanel.setVisible(true);

        // Set all fieds to blank/false by default when the page is loaded
        pQuoteIDTextField.setText("");
        validityFromDatePicker.getJFormattedTextField().setText("");
        validityToDatePicker.getJFormattedTextField().setText("");
        pBookingNumberTextField.setText("");
        pQuoteNumberTextField.setText("");
        pIDTextField.setText("");
        kkluNumberTextField.setText("");
        pPolTextField.setText("");
        pPodTextField.setText("");
        pCommodityClassComboBox.setSelectedIndex(0);
        pHandlingInstructions.setSelectedIndex(0);
        pCommodityDescriptionTextField.setText("");
        pOftTextField.setText("");
        pOftComboBox.setSelectedItem("");
        pBafTextField.setText("");
        pBafIncludedCheckBox.setSelected(false);
        bafSubjectToTariffCheckBox.setSelected(false);
        pEcaTextField.setText("");
        pEcaComboBox.setSelectedItem("");
        pEcaIncludedCheckBox.setSelected(false);
        ecaSubjectToTariffCheckBox.setSelected(false);
        pThcTextField.setText("");
        pThcComboBox.setSelectedIndex(0);
        pThcIncludedCheckBox.setSelected(false);
        thcSubjectToTariffCheckBox.setSelected(false);
        pWfgTextField.setText("");
        pWfgComboBox.setSelectedIndex(0);
        pWfgIncludedCheckBox.setSelected(false);
        wfgSubjectToTariffCheckBox.setSelected(false);
        pDocFeeComboBox.setSelectedIndex(0);
        pWarRiskCheckBox.setSelected(false);
        storageTextField.setText("");
        storageUnitComboBox.setSelectedIndex(0);
        storageIncludedCheckBox.setSelected(false);
        storageSubjectToTariffCheckBox.setSelected(false);
        pCommentsTextArea.setText("");
        commodityNumberTextField.setText("");
        descriptionTextField.setText("");
        tliNumberTextField.setText("");
        expirationDateTextField.setText("");
    }//GEN-LAST:event_publishingCenterButtonActionPerformed

    private void pQuoteIDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pQuoteIDButtonActionPerformed
        // Select quote by ID
        Connection conn = new DBConnection().connect();

        String idType = idTypeComboBox.getSelectedItem().toString();
        String ID = pQuoteIDTextField.getText();

        if (idType.equals("PID")) {
            String sql = "SELECT * FROM spotrates WHERE ID=" + ID;
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String pid = rs.getString("ID");
                    String validityFrom = rs.getString("validityFrom");
                    String validityTo = rs.getString("validityTo");
                    String tariffNumber = rs.getString("tariffNumber");
                    String pol = rs.getString("pol");
                    String pod = rs.getString("pod");
                    String bookingNumber = rs.getString("bookingNumber");
                    String commClass = rs.getString("commClass");
                    String handlingInstructions = rs.getString("handlingInstructions");
                    String commDesc = rs.getString("commDesc");
                    String oft = rs.getString("oft");
                    String oftUnit = rs.getString("oftUnit");
                    String baf = rs.getString("baf");
                    Boolean bafIncluded = rs.getBoolean("bafIncluded");
                    Boolean bafPerTariff = rs.getBoolean("bafPerTariff");
                    String ecaBaf = rs.getString("ecaBaf");
                    String ecaBafUnit = rs.getString("ecaBafUnit");
                    Boolean ecaIncluded = rs.getBoolean("ecaIncluded");
                    Boolean ecaPerTariff = rs.getBoolean("ecaPerTariff");
                    String thc = rs.getString("thc");
                    String thcUnit = rs.getString("thcUnit");
                    Boolean thcIncluded = rs.getBoolean("thcIncluded");
                    Boolean thcPerTariff = rs.getBoolean("thcPerTariff");
                    String wfg = rs.getString("wfg");
                    String wfgUnit = rs.getString("wfgUnit");
                    Boolean wfgPerTariff = rs.getBoolean("wfgPerTariff");
                    Boolean wfgIncluded = rs.getBoolean("wfgIncluded");
                    String docFee = rs.getString("docFee");
                    Boolean docFeeIncluded = rs.getBoolean("docFeeIncluded");
                    String storage = rs.getString("storage");
                    String storageUnit = rs.getString("storageUnit");
                    Boolean storageIncluded = rs.getBoolean("storageIncluded");
                    Boolean storagePerTariff = rs.getBoolean("storagePerTariff");
                    String comments = rs.getString("comments");
                    String quoteID = rs.getString("quoteID");
                    Boolean warRisk = rs.getBoolean("warRisk");
                    String commodityNumber = rs.getString("commodityNumber");
                    String description = rs.getString("description");
                    String tliNumber = rs.getString("tliNumber");
                    String expiration = rs.getString("expiration");

                    validityFromDatePicker.getJFormattedTextField().setText(validityFrom);
                    validityToDatePicker.getJFormattedTextField().setText(validityTo);
                    pQuoteNumberTextField.setText(quoteID);
                    pIDTextField.setText(pid);
                    kkluNumberTextField.setText(tariffNumber);
                    pPolTextField.setText(pol);
                    pPodTextField.setText(pod);
                    pCommodityClassComboBox.setSelectedItem(commClass);
                    pHandlingInstructions.setSelectedItem(handlingInstructions);
                    pCommodityDescriptionTextField.setText(commDesc);
                    pOftTextField.setText(oft);
                    pOftComboBox.setSelectedItem(oftUnit);
                    if (bafIncluded == true) {
                        pBafTextField.setText("");
                        pBafIncludedCheckBox.setSelected(bafIncluded);
                    } else if (bafPerTariff == true) {
                        pBafTextField.setText("");
                        bafSubjectToTariffCheckBox.setSelected(bafPerTariff);
                    } else {
                        pBafTextField.setText(baf);
                    }
                    if (ecaIncluded == true) {
                        pEcaTextField.setText("");
                        pEcaComboBox.setSelectedIndex(0);
                        pEcaIncludedCheckBox.setSelected(ecaIncluded);
                    } else if (ecaPerTariff == true) {
                        pEcaTextField.setText("");
                        pEcaComboBox.setSelectedIndex(0);
                        ecaSubjectToTariffCheckBox.setSelected(ecaPerTariff);
                    } else {
                        pEcaTextField.setText(ecaBaf);
                        pEcaComboBox.setSelectedItem(ecaBafUnit);
                    }
                    if (thcIncluded == true) {
                        pThcTextField.setText("");
                        pThcComboBox.setSelectedIndex(0);
                        pThcIncludedCheckBox.setSelected(thcIncluded);
                    } else if (thcPerTariff == true) {
                        pThcTextField.setText("");
                        pThcComboBox.setSelectedIndex(0);
                        thcSubjectToTariffCheckBox.setSelected(thcPerTariff);
                    } else {
                        pThcTextField.setText(thc);
                        pThcComboBox.setSelectedItem(thcUnit);
                    }
                    if (wfgIncluded == true) {
                        pWfgTextField.setText("");
                        pWfgComboBox.setSelectedIndex(0);
                        pWfgIncludedCheckBox.setSelected(wfgIncluded);
                    } else if (wfgPerTariff == true) {
                        pWfgTextField.setText("");
                        pWfgComboBox.setSelectedIndex(0);
                        wfgSubjectToTariffCheckBox.setSelected(wfgPerTariff);
                    } else {
                        pWfgTextField.setText(ecaBaf);
                        pWfgComboBox.setSelectedItem(wfgUnit);
                    }
                    if (storageIncluded == true) {
                        storageTextField.setText("");
                        storageUnitComboBox.setSelectedIndex(0);
                        storageIncludedCheckBox.setSelected(storageIncluded);
                    } else if (storagePerTariff == true) {
                        storageTextField.setText("");
                        storageUnitComboBox.setSelectedIndex(0);
                        storageSubjectToTariffCheckBox.setSelected(storagePerTariff);
                    } else {
                        storageTextField.setText(storage);
                        storageUnitComboBox.setSelectedItem(storageUnit);
                    }
                    pDocFeeComboBox.setSelectedItem(docFee);
                    pDocFeeIncludedCheckBox.setSelected(docFeeIncluded);
                    pWarRiskCheckBox.setSelected(warRisk);
                    pCommentsTextArea.setText("");
                    warRiskCheckBox.setSelected(warRisk);
                    commodityNumberTextField.setText(commodityNumber);
                    descriptionTextField.setText(description);
                    tliNumberTextField.setText(tliNumber);
                    expirationDateTextField.setText(expiration);
                    if (bookingNumber.equals("")) {
                        pBookingNumberTextField.setText("TBA");
                        pBookingNumberTextField.setText(bookingNumber);
                    }

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else {
            String sql = "SELECT * FROM allquotes WHERE id=" + ID;

            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String tradeLane = rs.getString("tradeLane");
                    String pol = rs.getString("pol");
                    String pod = rs.getString("pod");
                    String commodityClass = rs.getString("comm_class");
                    String commodityDescription = rs.getString("comm_description");
                    String handlingInstructions = rs.getString("handling_instructions");
                    String oft = rs.getString("rate");
                    String oftUnit = rs.getString("rate_unit");
                    String baf = rs.getString("baf");
                    Boolean bafIncluded = rs.getBoolean("bafIncluded");
                    Boolean bafAttached = rs.getBoolean("bafPerTariff");
                    String eca = rs.getString("eca_baf");
                    String ecaUnit = rs.getString("eca_unit");
                    Boolean ecaIncluded = rs.getBoolean("ecaIncluded");
                    Boolean ecaAttached = rs.getBoolean("ecaPerTariff");
                    String pThc = null;
                    String thc = rs.getString("thc");
                    String thcUnit = rs.getString("thc_unit");
                    Boolean thcAttachedToEmail = rs.getBoolean("thcAttached");
                    Boolean thcIncluded = rs.getBoolean("thcIncluded");
                    String pWfg = null;
                    String wfg = rs.getString("wfg");
                    String wfgUnit = rs.getString("wfg_unit");
                    Boolean wfgAttachedToEmail = rs.getBoolean("wfgAttached");
                    Boolean wfgIncluded = rs.getBoolean("wfgIncluded");
                    String documentationFee = rs.getString("doc_fee");
                    Boolean documentationFeeIncluded = rs.getBoolean("documentationFeeIncluded");
                    String storage = rs.getString("storage");
                    String storageUnit = rs.getString("storageUnit");
                    Boolean storageIncluded = rs.getBoolean("storageIncluded");
                    Boolean storageAttached = rs.getBoolean("storagePerTariff");
                    Boolean warRisk = rs.getBoolean("war_risk");
                    String bookingNumber = rs.getString("bookingNumber");
                    String commodityNumber = rs.getString("commodityNumber");
                    String description = rs.getString("description");
                    String tliNumber = rs.getString("tliNumber");
                    String expiration = rs.getString("expiration");
                    String quoteID = rs.getString("ID");
                    String pid = rs.getString("publishingID");

                    /*
                     *********************************************************************************
                     *Sets valid date to today and expiration date to 30 days from today
                     *********************************************************************************
                     */
                    String validDate = new SimpleDateFormat("MM/dd/YYYY").format(Calendar.getInstance().getTime());
                    Calendar calReturn = Calendar.getInstance();
                    calReturn.add(Calendar.DATE, 30);
                    String expireDate = new SimpleDateFormat("MM/dd/YYYY").format(calReturn.getTime());
                    /*
                     *********************************************************************************
                     *********************************************************************************
                     *********************************************************************************
                     */

                    String kkluNumber = null;
                    switch (tradeLane) {
                        case "Trans-Atlantic EB":
                            kkluNumber = "132";
                            break;
                        case "North Atlantic Shuttle EB":
                            kkluNumber = "132";
                            break;
                        case "NAX WB":
                            kkluNumber = "132";
                            break;
                        case "ECAMS SB":
                            kkluNumber = "132";
                            break;
                        case "Trans-Atlantic WB":
                            kkluNumber = "133";
                            break;
                        case "North Atlantic Shuttle WB":
                            kkluNumber = "133";
                            break;
                        case "NAX EB":
                            kkluNumber = "133";
                            break;
                        case "ECAMS NB":
                            kkluNumber = "133";
                            break;
                        default:
                            break;
                    }

                    if (documentationFee.equals("Inclusive")) {
                        documentationFeeIncluded = true;
                    }
                    pQuoteNumberTextField.setText(quoteID);
                    pIDTextField.setText(pid);
                    storageSubjectToTariffCheckBox.setSelected(true);
                    validityFromDatePicker.getJFormattedTextField().setText(validDate);
                    validityToDatePicker.getJFormattedTextField().setText(expireDate);
                    kkluNumberTextField.setText(kkluNumber);
                    pPolTextField.setText(pol);
                    pPodTextField.setText(pod);
                    pCommodityClassComboBox.setSelectedItem(commodityClass);
                    pHandlingInstructions.setSelectedItem(handlingInstructions);
                    pCommodityDescriptionTextField.setText(commodityDescription);
                    pOftTextField.setText(oft);
                    pOftComboBox.setSelectedItem(oftUnit);
                    if (bafIncluded == true) {
                        pBafTextField.setText("");
                        pBafIncludedCheckBox.setSelected(bafIncluded);
                    } else if (bafAttached == true) {
                        pBafTextField.setText("");
                        bafSubjectToTariffCheckBox.setSelected(bafAttached);
                    } else {
                        pBafTextField.setText(baf);
                    }
                    if (ecaIncluded == true) {
                        pEcaTextField.setText("");
                        pEcaComboBox.setSelectedIndex(0);
                        pEcaIncludedCheckBox.setSelected(ecaIncluded);
                    } else if (ecaAttached == true) {
                        pEcaTextField.setText("");
                        pEcaComboBox.setSelectedIndex(0);
                        ecaSubjectToTariffCheckBox.setSelected(ecaAttached);
                    } else {
                        pEcaTextField.setText(eca);
                        pEcaComboBox.setSelectedItem(ecaUnit);
                    }
                    if (thcIncluded == true) {
                        pThcTextField.setText("");
                        pThcComboBox.setSelectedIndex(0);
                        pThcIncludedCheckBox.setSelected(thcIncluded);
                    } else if (thcAttachedToEmail == true) {
                        pThcTextField.setText("");
                        pThcComboBox.setSelectedIndex(0);
                        thcSubjectToTariffCheckBox.setSelected(thcAttachedToEmail);
                    } else {
                        pThcTextField.setText(thc);
                        pThcComboBox.setSelectedItem(thcUnit);
                    }
                    if (wfgIncluded == true) {
                        pWfgTextField.setText("");
                        pWfgComboBox.setSelectedIndex(0);
                        pWfgIncludedCheckBox.setSelected(wfgIncluded);
                    } else if (wfgAttachedToEmail == true) {
                        pWfgTextField.setText("");
                        pWfgComboBox.setSelectedIndex(0);
                        wfgSubjectToTariffCheckBox.setSelected(wfgAttachedToEmail);
                    } else {
                        pWfgTextField.setText(wfg);
                        pWfgComboBox.setSelectedItem(wfgUnit);
                    }
                    if (storageIncluded == true) {
                        storageTextField.setText("");
                        storageUnitComboBox.setSelectedIndex(0);
                        storageIncludedCheckBox.setSelected(storageIncluded);
                    } else if (storageAttached == true) {
                        storageTextField.setText("");
                        storageUnitComboBox.setSelectedIndex(0);
                        storageSubjectToTariffCheckBox.setSelected(storageAttached);
                    } else {
                        storageTextField.setText(storage);
                        storageUnitComboBox.setSelectedItem(storageUnit);
                    }
                    pDocFeeComboBox.setSelectedItem(documentationFee);
                    pDocFeeIncludedCheckBox.setSelected(documentationFeeIncluded);
                    pWarRiskCheckBox.setSelected(warRisk);
                    commodityNumberTextField.setText(commodityNumber);
                    descriptionTextField.setText(description);
                    tliNumberTextField.setText(tliNumber);
                    expirationDateTextField.setText(expiration);
                    if (bookingNumber.equals("")) {
                        pBookingNumberTextField.setText("TBA");
                    } else if (!bookingNumber.equals("")) {
                        pBookingNumberTextField.setText(bookingNumber);
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_pQuoteIDButtonActionPerformed

    private void submitToPublishingPDFButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitToPublishingPDFButtonActionPerformed

        String validityFrom = validityFromDatePicker.getJFormattedTextField().getText();
        String validityTo = validityToDatePicker.getJFormattedTextField().getText();
        String kkluNumber = kkluNumberTextField.getText();
        String pol = pPolTextField.getText();
        String pod = pPodTextField.getText();
        String commClass = pCommodityClassComboBox.getSelectedItem().toString();
        String handlingInstructions = pHandlingInstructions.getSelectedItem().toString();
        String commDesc = pCommodityDescriptionTextField.getText();
        String oft = pOftTextField.getText();
        String oftUnit = pOftComboBox.getSelectedItem().toString();
        String baf = null;
        String bafText = pBafTextField.getText();
        Boolean bafIncluded = pBafIncludedCheckBox.isSelected();
        String eca = null;
        String ecaText = pEcaTextField.getText();
        String ecaUnit = pEcaComboBox.getSelectedItem().toString();
        Boolean ecaIncluded = pEcaIncludedCheckBox.isSelected();
        String thc = null;
        String thcText = "$" + pThcTextField.getText();
        String thcUnit = pThcComboBox.getSelectedItem().toString();
        Boolean thcIncluded = pThcIncludedCheckBox.isSelected();
        Boolean thcSubjectToTariff = thcSubjectToTariffCheckBox.isSelected();
        String wfg = null;
        String wfgText = pWfgTextField.getText();
        String wfgUnit = pWfgComboBox.getSelectedItem().toString();
        Boolean wfgIncluded = pWfgIncludedCheckBox.isSelected();
        Boolean wfgSubjectToTariff = wfgSubjectToTariffCheckBox.isSelected();
        String docFee = pDocFeeComboBox.getSelectedItem().toString();
        Boolean docFeeIncluded = pDocFeeIncludedCheckBox.isSelected();
        String comments = pCommentsTextArea.getText();
        Boolean warRisk = pWarRiskCheckBox.isSelected();
        String quoteID = pQuoteNumberTextField.getText();
        String bookingNumber = pBookingNumberTextField.getText();
        String storage = null;
        String storageText = storageTextField.getText();
        String storageUnit = storageUnitComboBox.getSelectedItem().toString();
        Boolean storageIncluded = storageIncludedCheckBox.isSelected();
        Boolean storageSubjectToTariff = storageSubjectToTariffCheckBox.isSelected();
        Boolean bafSubjectToTariff = bafSubjectToTariffCheckBox.isSelected();
        Boolean ecaSubjectToTariff = ecaSubjectToTariffCheckBox.isSelected();
        String commodityNumber = commodityNumberTextField.getText();
        String description = descriptionTextField.getText();
        String tliNumber = tliNumberTextField.getText();
        String expiration = expirationDateTextField.getText();

        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());

        if (bafIncluded != true && bafSubjectToTariff != true) {
            baf = bafText;
        } else if (bafIncluded == true) {
            baf = "Included";
        } else if (bafSubjectToTariff == true) {
            baf = "Subject to Tariff";
        }

        if (ecaIncluded != true && ecaSubjectToTariff != true) {
            eca = ecaText;
        } else if (ecaIncluded == true) {
            eca = "Included";
        } else if (ecaSubjectToTariff == true) {
            eca = "Subject to Tariff";
        } else if (ecaSubjectToTariff == true) {
            eca = "Subject to Tariff";
        }

        if (thcSubjectToTariff == true) {
            thc = "Subject to Tariff";
        } else if (thcSubjectToTariff != true && thcIncluded != true) {
            thc = thcText;
        } else if (thcSubjectToTariff != true && thcIncluded == true) {
            thc = "Included";
        } else if (thcIncluded == true && thcSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        if (wfgSubjectToTariff == true) {
            wfg = "Subject to Tariff";
            wfgUnit = "N/A";
        } else if (wfgSubjectToTariff != true && wfgIncluded != true) {
            wfg = wfgText;
        } else if (wfgSubjectToTariff != true && wfgIncluded == true) {
            wfg = "Included";
        } else if (wfgIncluded == true && wfgSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        if (storageSubjectToTariff == true) {
            storage = "Subject to Tariff";
            storageUnit = "N/A";
        } else if (storageSubjectToTariff != true && storageIncluded != true) {
            storage = storageText;
        } else if (storageIncluded == true && storageSubjectToTariff != true) {
            storage = "Included";
        } else if (storageIncluded == true && storageSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        String sql = "INSERT INTO spotrates (validityFrom, validityTo, tariffNumber, pol, pod, bookingNumber, commClass, handlingInstructions, commDesc, oft, oftUnit, baf, bafIncluded, bafPerTariff, ecaBaf, ecaBafUnit, ecaIncluded, ecaPerTariff, thc, thcUnit,thcIncluded, thcPerTariff, wfg, wfgUnit, wfgIncluded, wfgPerTariff, storage, storageUnit, storageIncluded, storagePerTariff, docFee, docFeeIncluded, comments, quoteID, warRisk, commodityNumber, description, tliNumber, expiration) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, validityFrom);
            ps.setString(2, validityTo);
            ps.setString(3, kkluNumber);
            ps.setString(4, pol);
            ps.setString(5, pod);
            ps.setString(6, bookingNumber);
            ps.setString(7, commClass);
            ps.setString(8, handlingInstructions);
            ps.setString(9, commDesc);
            ps.setString(10, oft);
            ps.setString(11, oftUnit);
            ps.setString(12, baf);
            ps.setBoolean(13, bafIncluded);
            ps.setBoolean(14, bafSubjectToTariff);
            ps.setString(15, eca);
            ps.setString(16, ecaUnit);
            ps.setBoolean(17, ecaIncluded);
            ps.setBoolean(18, ecaSubjectToTariff);
            ps.setString(19, thc);
            ps.setString(20, thcUnit);
            ps.setBoolean(21, thcIncluded);
            ps.setBoolean(22, thcSubjectToTariff);
            ps.setString(23, wfg);
            ps.setString(24, wfgUnit);
            ps.setBoolean(25, wfgIncluded);
            ps.setBoolean(26, wfgSubjectToTariff);
            ps.setString(27, storage);
            ps.setString(28, storageUnit);
            ps.setBoolean(29, storageIncluded);
            ps.setBoolean(30, storageSubjectToTariff);
            ps.setString(31, docFee);
            ps.setBoolean(32, docFeeIncluded);
            ps.setString(33, comments);
            ps.setString(34, quoteID);
            ps.setBoolean(35, warRisk);
            ps.setString(36, commodityNumber);
            ps.setString(37, description);
            ps.setString(38, tliNumber);
            ps.setString(39, expiration);
            // Execute the update
            ps.executeUpdate();

            //Return the auto-generated key
            ResultSet keys = ps.getGeneratedKeys();
            int lastKey = 1;
            while (keys.next()) {
                lastKey = keys.getInt(1);
            }
            int pid = lastKey;

            String addBookingNumber = "UPDATE allquotes SET bookingNumber=?, publishingID=?, commodityNumber=?, description=?, tliNumber=?, expiration=? WHERE ID=?;";
            PreparedStatement psAddBookingNumber = conn.prepareStatement(addBookingNumber);
            psAddBookingNumber.setString(1, bookingNumber);
            psAddBookingNumber.setInt(2, pid);
            psAddBookingNumber.setString(3, commodityNumber);
            psAddBookingNumber.setString(4, description);
            psAddBookingNumber.setString(5, tliNumber);
            psAddBookingNumber.setString(6, expiration);
            psAddBookingNumber.setString(7, quoteID);
            psAddBookingNumber.executeUpdate();
            psAddBookingNumber.closeOnCompletion();

            Double oft1 = (Double) (Double.parseDouble(oft));
            String eca1 = "";
            switch (eca) {
                case "Included":
                    eca1 = "Included";
                    break;
                case "Subject to Tariff":
                    eca1 = "Subject to Tariff";
                    break;
                default:
                    eca1 = eca;
                    break;
            }
            String baf1;
            switch (baf) {
                case "Included":
                    baf1 = "Included";
                    break;
                case "Subject To Tariff":
                    baf1 = "Subject to Tariff";
                    break;
                default:
                    baf1 = baf;
                    break;
            }

            String thc1;

            switch (thc) {
                case "Included":
                    thc1 = thc;
                    break;
                case "Subject to Tariff":
                    thc1 = "Subject to Tariff";
                    break;
                default:
                    thc1 = "$" + thc + " per " + thcUnit;
                    break;
            }

            switch (thcUnit) {
                case "FAS":
                    thc1 = thcUnit;
                    break;
                case "Subject to local charges":
                    thc1 = thcUnit;
                default:
                    break;
            }

            String wfg1;
            switch (wfg) {
                case "Included":
                    wfg1 = wfg;
                    break;
                case "Subject to Tariff":
                    wfg1 = "Subject to Tariff";
                    break;
                default:
                    wfg1 = "$" + wfg + " per " + wfgUnit;
                    break;
            }

            switch (wfgUnit) {
                case "FAS":
                    wfg1 = wfgUnit;
                    break;
                case "Subject to local charges":
                    wfg1 = wfgUnit;
                default:
                    break;
            }

            /*
             ***************************************
             ***************************************
             ***************************************
             */
            String filename = userHomeFolder + "\\Desktop\\Publishing\\" + pol + " To " + pod + "; " + commDesc + " PID" + lastKey + ".xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("KKLU" + kkluNumber);

            sheet.setColumnWidth(0, 650);
            sheet.setColumnWidth(1, 5742);
            sheet.setColumnWidth(2, 5920);
            sheet.setColumnWidth(3, 3668);
            sheet.setColumnWidth(4, 5711);

            //Bold Font
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            CellStyle style = workbook.createCellStyle();
            style.setFont(font);

            //Currency cell type
            CellStyle currency = workbook.createCellStyle();
            currency.setDataFormat((short) 7);

            //Percentage cell type
            CellStyle percentage = workbook.createCellStyle();
            percentage.setDataFormat((short) 0xa);

            sheet.setDisplayGridlines(false);

            //Black medium sized border around cell
            CellStyle blackBorder = workbook.createCellStyle();
            blackBorder.setBorderBottom(CellStyle.BORDER_MEDIUM);
            blackBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderLeft(CellStyle.BORDER_MEDIUM);
            blackBorder.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderRight(CellStyle.BORDER_MEDIUM);
            blackBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderTop(CellStyle.BORDER_MEDIUM);
            blackBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());

            //Red font
            CellStyle redFontStyle = workbook.createCellStyle();
            HSSFFont redFont = workbook.createFont();
            redFont.setColor(HSSFColor.RED.index);
            redFontStyle.setFont(redFont);

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(3).setCellValue(validityFrom);

            HSSFRow row1 = sheet.createRow((short) 1);
            Cell cell = row1.createCell(1);
            cell.setCellValue("FILING REQUEST TO RICLFILE");
            cell.setCellStyle(style);

            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));

            sheet.setPrintGridlines(false);

            HSSFRow row2 = sheet.createRow((short) 2);
            row2.createCell(0).setCellValue("A)");
            row2.createCell(1).setCellValue("Tariff Number(KKLU):");
            row2.createCell(2).setCellValue(kkluNumber);

            HSSFRow space0 = sheet.createRow((short) 3);

            HSSFRow row4 = sheet.createRow((short) 4);
            row4.createCell(0).setCellValue("B)");
            row4.createCell(1).setCellValue("Commodity:");
            row4.createCell(2).setCellValue(commDesc);

            HSSFRow space2 = sheet.createRow((short) 5);

            HSSFRow row5 = sheet.createRow((short) 6);
            row5.createCell(0).setCellValue("C)");
            row5.createCell(1).setCellValue("POL:");
            row5.createCell(2).setCellValue(pol);

            HSSFRow space3 = sheet.createRow((short) 7);

            HSSFRow row6 = sheet.createRow((short) 8);
            row6.createCell(0).setCellValue("D)");
            row6.createCell(1).setCellValue("POD:");
            row6.createCell(2).setCellValue(pod);

            HSSFRow space4 = sheet.createRow((short) 9);

            HSSFRow row7 = sheet.createRow((short) 10);
            row7.createCell(0).setCellValue("E)");
            row7.createCell(1).setCellValue("Rate:");
            Cell rate = row7.createCell(2);
            rate.setCellValue(oft1);
            rate.setCellStyle(currency);

            HSSFRow space5 = sheet.createRow((short) 11);

            HSSFRow row8 = sheet.createRow((short) 12);
            row8.createCell(0).setCellValue("F)");
            row8.createCell(1).setCellValue("Rate Basis:");
            row8.createCell(2).setCellValue(oftUnit);

            HSSFRow space6 = sheet.createRow((short) 13);

            HSSFRow row9 = sheet.createRow((short) 14);
            row9.createCell(0).setCellValue("G)");
            row9.createCell(1).setCellValue("BAF:");
            Cell Baf = row9.createCell(2);
            switch (baf1) {
                case "Included":
                    Baf.setCellValue("Included");
                    break;
                case "Subject to Tariff":
                    Baf.setCellValue("Subject to Tariff");
                    break;
                default:
                    Baf.setCellValue(baf1);
                    Baf.setCellStyle(percentage);
                    break;
            }

            HSSFRow space7 = sheet.createRow((short) 15);

            HSSFRow row10 = sheet.createRow((short) 16);
            row10.createCell(0).setCellValue("H)");
            row10.createCell(1).setCellValue("ECA BAF:");
            Cell ecaBaf = row10.createCell(2);
            switch (eca) {
                case "Included":
                    ecaBaf.setCellValue("Included");
                    break;
                case "Subject to Tariff":
                    ecaBaf.setCellValue("Subject to Tariff");
                    break;
                default:
                    ecaBaf.setCellValue(eca1 + " per " + ecaUnit);
                    ecaBaf.setCellStyle(currency);
                    break;
            }

            HSSFRow space8 = sheet.createRow((short) 17);

            HSSFRow row11 = sheet.createRow((short) 18);
            row11.createCell(0).setCellValue("I)");
            row11.createCell(1).setCellValue("THC/WFG:");
            row11.createCell(2).setCellValue(thc1 + " / " + wfg1);

            HSSFRow space = sheet.createRow((short) 19);

            HSSFRow row12 = sheet.createRow((short) 20);
            row12.createCell(0).setCellValue("J)");
            row12.createCell(1).setCellValue("Storage:");
            row12.createCell(2).setCellValue(storage);

            HSSFRow space10 = sheet.createRow((short) 21);

            HSSFRow row13 = sheet.createRow((short) 22);
            row13.createCell(0).setCellValue("K)");
            row13.createCell(1).setCellValue("Doc Fee:");
            row13.createCell(2).setCellValue(docFee);

            HSSFRow space11 = sheet.createRow((short) 23);

            HSSFRow row14 = sheet.createRow((short) 24);
            row14.createCell(0).setCellValue("L)");
            row14.createCell(1).setCellValue("War Risk:");

            HSSFRow space12 = sheet.createRow((short) 25);

            if (warRisk == true) {
                String warRiskPercentage = "3%";
                row14.createCell(2).setCellValue(warRiskPercentage);
            } else if (warRisk != true) {
                String warRiskPercentage = "N/A";
                row14.createCell(2).setCellValue(warRiskPercentage);
            }

            HSSFRow row15 = sheet.createRow((short) 26);
            row15.createCell(0).setCellValue("M)");
            row15.createCell(1).setCellValue("Validity");
            row15.createCell(2).setCellValue("Effective: " + validityFrom);

            HSSFRow row16 = sheet.createRow((short) 27);
            row16.createCell(2).setCellValue("Expiration: " + validityTo);

            HSSFRow space13 = sheet.createRow((short) 28);

            HSSFRow row17 = sheet.createRow((short) 29);
            row17.createCell(0).setCellValue("N)");
            row17.createCell(1).setCellValue("Remarks");
            row17.createCell(2).setCellValue(comments);

            HSSFRow space14 = sheet.createRow((short) 30);

            HSSFRow row18 = sheet.createRow((short) 31);
            row18.createCell(0).setCellValue("O)");
            row18.createCell(1).setCellValue("Booking #:");
            row18.createCell(2).setCellValue(bookingNumber);

            HSSFRow space15 = sheet.createRow((short) 32);

            HSSFRow row19 = sheet.createRow((short) 33);
            row19.createCell(0).setCellValue("P)");
            row19.createCell(1).setCellValue("RQS #:");
            row19.createCell(2).setCellValue(quoteID);

            HSSFRow space16 = sheet.createRow((short) 34);

            HSSFRow row20 = sheet.createRow((short) 35);
            row20.createCell(0).setCellValue("Q)");
            row20.createCell(1).setCellValue("PID #:");
            row20.createCell(2).setCellValue(pid);

            HSSFRow space17 = sheet.createRow((short) 36);

            HSSFRow space18 = sheet.createRow((short) 37);

            HSSFRow row21 = sheet.createRow((short) 38);
            row21.createCell(0).setCellValue("");
            row21.createCell(1).setCellValue("For RICLFILE Use Only");

            HSSFRow space19 = sheet.createRow((short) 39);

            HSSFRow row22 = sheet.createRow((short) 40);
            Cell comm = row22.createCell(1);
            comm.setCellValue("Commodity #:");
            comm.setCellStyle(redFontStyle);
            row22.createCell(2).setCellValue(commodityNumber);
            Cell desc = row22.createCell(3);
            desc.setCellValue("Description:");
            desc.setCellStyle(redFontStyle);
            row22.createCell(4).setCellValue(description);

            HSSFRow space20 = sheet.createRow((short) 41);

            HSSFRow row24 = sheet.createRow((short) 42);
            Cell TLI = row24.createCell(1);
            TLI.setCellValue("TLI #:");
            TLI.setCellStyle(redFontStyle);
            row24.createCell(2).setCellValue(tliNumber);

            HSSFRow space21 = sheet.createRow((short) 43);

            HSSFRow row26 = sheet.createRow((short) 44);
            Cell exp = row26.createCell(1);
            exp.setCellValue("Expiration: ");
            exp.setCellStyle(redFontStyle);
            row26.createCell(2).setCellValue(expiration);

            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                workbook.write(fileOut);
            }
            System.out.print("Your excel file has been generate");

            String spotRateId = String.valueOf(lastKey);

            JOptionPane.showMessageDialog(null, "The spot filing (PID" + spotRateId + ") for " + quoteID + " has been succsefully generated.");
            pQuoteIDTextField.setText("");
            validityFromDatePicker.getJFormattedTextField().setText("");
            validityToDatePicker.getJFormattedTextField().setText("");
            kkluNumberTextField.setText("");
            pPolTextField.setText("");
            pPodTextField.setText("");
            pCommodityClassComboBox.setSelectedIndex(0);
            pHandlingInstructions.setSelectedIndex(0);
            pCommodityDescriptionTextField.setText("");
            pOftTextField.setText("");
            pOftComboBox.setSelectedItem("");
            bafSubjectToTariffCheckBox.setSelected(false);
            pBafTextField.setText("");
            pBafIncludedCheckBox.setSelected(false);
            pEcaTextField.setText("");
            pEcaComboBox.setSelectedIndex(0);
            pEcaIncludedCheckBox.setSelected(false);
            ecaSubjectToTariffCheckBox.setSelected(false);
            pThcTextField.setText("");
            pThcComboBox.setSelectedIndex(0);
            pThcIncludedCheckBox.setSelected(false);
            thcSubjectToTariffCheckBox.setSelected(false);
            pWfgTextField.setText("");
            pWfgComboBox.setSelectedIndex(0);
            pWfgIncludedCheckBox.setSelected(false);
            wfgSubjectToTariffCheckBox.setSelected(false);
            pDocFeeComboBox.setSelectedIndex(0);
            pWarRiskCheckBox.setSelected(false);
            storageSubjectToTariffCheckBox.setSelected(false);
            pCommentsTextArea.setText("");
            pBookingNumberTextField.setText("");
            commodityNumberTextField.setText("");
            descriptionTextField.setText("");
            tliNumberTextField.setText("");
            expirationDateTextField.setText("");
            pQuoteNumberTextField.setText("");
            pIDTextField.setText("");

        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_submitToPublishingPDFButtonActionPerformed

    private void clearNewQuoteFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearNewQuoteFormActionPerformed
        //Clear new quote form
        //customerNameList.setSelectedIndex(-1);
        newQuoteCustomerNameLabel.setText("N/A");
        contactNameTextField.setText("");
        contactEmailTextField.setText("");
        tradeLaneComboBox.setSelectedIndex(0);
        polTextField.setText("");
        podTextField.setText("");
        tshp1TextField.setText("");
        tshp2TextField.setText("");
        commodityClassComboBox.setSelectedIndex(0);
        handlingInstructionsComboBox.setSelectedIndex(0);
        mafiMinimumCheckBox.setSelected(false);
        mafiMinimumTextField.setText("");
        commodityDescriptionTextField.setText("");
        oftTextField.setText("");
        oftMeasurementComboBox.setSelectedIndex(0);
        bafTextField.setText("");
        bafIncludedCheckBox.setSelected(false);
        ecaBAFTextField.setText("");
        ecaBafMeasurementComboBox.setSelectedIndex(0);
        ecaBafIncludedCheckBox.setSelected(false);
        thcTextField.setText("");
        thcMeasurementComboBox.setSelectedIndex(0);
        thcIncludedCheckBox.setSelected(false);
        wfgTextField.setText("");
        wfgMeasurementComboBox.setSelectedIndex(0);
        wfgIncludedCheckBox.setSelected(false);
        documentationFeeComboBox.setSelectedIndex(0);
        documentationFeeIncludedCheckBox.setSelected(false);
        commentsTextArea.setText("");
        warRiskCheckBox.setSelected(false);
        spotRateCheckBox.setSelected(false);
        newQuoteDenialCheckBox.setSelected(false);
        newQuoteBookedCheckBox.setSelected(false);
        thcAttached.setSelected(false);
        wfgAttached.setSelected(false);
        bookingNumberTextField.setText("");
        contractRateCheckBox.setSelected(false);
        int rows = packingListTable.getRowCount();
        int cols = packingListTable.getColumnCount();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                packingListTable.setValueAt("", r, c);
            }
        }
        DefaultTableModel dtm = (DefaultTableModel) packingListTable.getModel();
        dtm.setRowCount(0);
        for (int r = 0; r < newQuoteSailingScheduleTable.getRowCount(); r++) {
            for (int c = 0; c < newQuoteSailingScheduleTable.getColumnCount(); c++) {
                newQuoteSailingScheduleTable.setValueAt("", r, c);
            }
        }
        packingListTable.setModel(dtm);
        shipperCommentsTextArea.setText("");
        includeShipperCommentsCheckBox.setSelected(false);

    }//GEN-LAST:event_clearNewQuoteFormActionPerformed

//New Quote submit button
    private void submitNewQuoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitNewQuoteActionPerformed

        String SQLId = "SELECT userID FROM authorized_users WHERE username=?";
        try {
            PreparedStatement idPS = conn.prepareStatement(SQLId);
            idPS.setString(1, username);
            ResultSet idRS = idPS.executeQuery();
            if (idRS.next()) {
                String id = idRS.getString("userID");

                System.out.println("ID:" + id);
                //Get input from new quote form
                String newQuoteCustomerName = newQuoteCustomerNameLabel.getText();
                String newQuoteContactName = contactNameTextField.getText();
                String newQuoteContactEmail = contactEmailTextField.getText();
                String tradeLane = tradeLaneComboBox.getSelectedItem().toString();
                String pol = polTextField.getText();
                String pod = podTextField.getText();
                String tshp1 = tshp1TextField.getText();
                String tshp2 = tshp2TextField.getText();
                String commodityClass = commodityClassComboBox.getSelectedItem().toString();
                String commodityDescription = commodityDescriptionTextField.getText();
                String handlingInstructions = handlingInstructionsComboBox.getSelectedItem().toString();
                String OFT = oftTextField.getText();
                String oftMeasurement = oftMeasurementComboBox.getSelectedItem().toString();
                String BAF = bafTextField.getText();
                Boolean bafIncluded = bafIncludedCheckBox.isSelected();
                String ecaBAF = ecaBAFTextField.getText();
                String ecaBafMeasurement = ecaBafMeasurementComboBox.getSelectedItem().toString();
                Boolean ecaIncluded = ecaBafIncludedCheckBox.isSelected();
                String thc = thcTextField.getText();
                String thcMeasurement = thcMeasurementComboBox.getSelectedItem().toString();
                Boolean thcIncluded = thcIncludedCheckBox.isSelected();
                Boolean thcAttachment = thcAttached.isSelected();
                String wfg = wfgTextField.getText();
                String wfgMeasurement = wfgMeasurementComboBox.getSelectedItem().toString();
                Boolean wfgIncluded = wfgIncludedCheckBox.isSelected();
                Boolean wfgAttachment = wfgAttached.isSelected();
                String documentationFee = documentationFeeComboBox.getSelectedItem().toString();
                Boolean documentationFeeIncluded = documentationFeeIncludedCheckBox.isSelected();
                Boolean warRisk = warRiskCheckBox.isSelected();
                String comments = commentsTextArea.getText();
                String customerName = null;
                String CN = null;
                Boolean denial = newQuoteDenialCheckBox.isSelected();
                Boolean spotRate = spotRateCheckBox.isSelected();
                Boolean booked = newQuoteBookedCheckBox.isSelected();
                Boolean accessories = newQuoteAccessoriesCheckBox.isSelected();
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
                Boolean mafiMinimum = mafiMinimumCheckBox.isSelected();
                String mafiMinimumCharge = mafiMinimumTextField.getText();
                String reason_for_decline = null;
                String deny = declineComboBox.getSelectedItem().toString();
                Boolean contract_rate = contractRateCheckBox.isSelected();
                String bookingNumber = " ";
                Boolean includeSailingSchedule = includeSailingScheduleCheckBox.isSelected();
                Boolean includeCarrierRemarks = includeShipperCommentsCheckBox.isSelected();
                String carrierComments = shipperCommentsTextArea.getText();

                if (booked == true) {
                    bookingNumber = bookingNumberTextField.getText();
                } else {
                    bookingNumber = " ";
                }

                if (newQuoteCustomerName.equals("N/A")) {
                    customerName = "N/A";
                    System.out.println(customerName);
                } else if (!newQuoteCustomerName.equals("N/A")) {
                    customerName = newQuoteCustomerName;
                    System.out.println(customerName);
                }

                if (customerName.equals("")) {
                    CN = "N/A";
                    System.out.println(customerName);
                } else if (!customerName.equals("")) {
                    CN = customerName;
                    System.out.println(customerName);
                }
                if (denial == true && deny.equals("N/A")) {
                    JOptionPane.showMessageDialog(null, "You must select a reason for declining this cargo");
                } else if (denial = true && !deny.equals("N/A")) {
                    reason_for_decline = declineComboBox.getSelectedItem().toString();
                } else if (denial != true) {
                    reason_for_decline = "N/A";
                }

                String sql = "INSERT INTO allquotes (tradeLane, pol, pod, tshp1, tshp2, comm_class, handling_instructions, comm_description, rate, rate_unit, baf, eca_baf, eca_unit, thc, thc_unit, wfg, wfg_unit, doc_fee, war_risk, spot_rate, user_ID, booked, date, DATE_QUOTED, comments, deny, customerName, thcIncluded, wfgIncluded, thcAttached, wfgAttached, bafIncluded, ecaIncluded, documentationFeeIncluded, accessories, mafiMinimum, mafiMinimumCharge, reason_for_decline, contract_rate, bookedUserID, bookingNumber, contactName, contactEmail, carrierComments)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                try {

                    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, tradeLane);
                    ps.setString(2, pol);
                    ps.setString(3, pod);
                    ps.setString(4, tshp1);
                    ps.setString(5, tshp2);
                    ps.setString(6, commodityClass);
                    ps.setString(7, handlingInstructions);
                    ps.setString(8, commodityDescription);
                    ps.setString(9, OFT);
                    ps.setString(10, oftMeasurement);
                    ps.setString(11, BAF);
                    ps.setString(12, ecaBAF);
                    ps.setString(13, ecaBafMeasurement);
                    ps.setString(14, thc);
                    ps.setString(15, thcMeasurement);
                    ps.setString(16, wfg);
                    ps.setString(17, wfgMeasurement);
                    ps.setString(18, documentationFee);
                    ps.setBoolean(19, warRisk);
                    ps.setBoolean(20, spotRate);
                    ps.setString(21, id);
                    ps.setBoolean(22, booked);
                    ps.setString(23, timeStamp);
                    ps.setString(24, timeStamp);
                    ps.setString(25, comments);
                    ps.setBoolean(26, denial);
                    ps.setString(27, CN);
                    ps.setBoolean(28, thcIncluded);
                    ps.setBoolean(29, wfgIncluded);
                    ps.setBoolean(30, thcAttachment);
                    ps.setBoolean(31, wfgAttachment);
                    ps.setBoolean(32, bafIncluded);
                    ps.setBoolean(33, ecaIncluded);
                    ps.setBoolean(34, documentationFeeIncluded);
                    ps.setBoolean(35, accessories);
                    ps.setBoolean(36, mafiMinimum);
                    ps.setString(37, mafiMinimumCharge);
                    ps.setString(38, reason_for_decline);
                    ps.setBoolean(39, contract_rate);
                    if (booked == true) {
                        ps.setString(40, id);
                    } else {
                        ps.setString(40, "");
                    }
                    ps.setString(41, bookingNumber);
                    ps.setString(42, newQuoteContactName);
                    ps.setString(43, newQuoteContactEmail);
                    ps.setString(44, carrierComments);
                    ps.executeUpdate();
                    ResultSet keys = ps.getGeneratedKeys();
                    int lastKey = 1;
                    while (keys.next()) {
                        lastKey = keys.getInt(1);
                    }
                    
                    int rows = packingListTable.getRowCount();
                    for (int row = 0; row < rows; row++) {
                        Object commodity = packingListTable.getValueAt(row, 0);
                        Object quantity = packingListTable.getValueAt(row, 1);
                        Object l = packingListTable.getValueAt(row, 2);
                        Object w = packingListTable.getValueAt(row, 3);
                        Object h = packingListTable.getValueAt(row, 4);
                        Object kgs = packingListTable.getValueAt(row, 5);
                        Object lin = packingListTable.getValueAt(row, 6);
                        Object win = packingListTable.getValueAt(row, 7);
                        Object hin = packingListTable.getValueAt(row, 8);
                        Object cbm = packingListTable.getValueAt(row, 9);
                        Object lbs = packingListTable.getValueAt(row, 10);
                        String c = null;
                        String q = null;
                        String L = null;
                        String W = null;
                        String H = null;
                        String Lin = null;
                        String Hin = null;
                        String Win = null;
                        String m3 = null;
                        String K = null;
                        String lb = null;

                        if (commodity == null) {
                            c = "";
                        } else if (commodity != null) {
                            c = String.valueOf(commodity);
                            System.out.println(c);
                        }
                        if (quantity == null) {
                            q = "";
                        } else if (quantity != null) {
                            q = String.valueOf(quantity);
                        }
                        if (l == null) {
                            L = "";
                        } else if (l != null) {
                            L = String.valueOf(l);
                        }
                        if (w == null) {
                            W = "";
                        } else if (w != null) {
                            W = String.valueOf(w);
                        }
                        if (h == null) {
                            H = "";
                        } else if (h != null) {
                            H = String.valueOf(h);
                        }
                        if (w == null) {
                            W = "";
                        } else if (w != null) {
                            W = String.valueOf(w);
                        }
                        if (cbm == null) {
                            m3 = "";
                        } else if (cbm != null) {
                            m3 = String.valueOf(cbm);
                        }
                        if (kgs == null) {
                            K = "";
                        } else if (kgs != null) {
                            K = String.valueOf(kgs);
                        }
                        if (lbs == null) {
                            lb = "";
                        } else if (lbs != null) {
                            lb = String.valueOf(lbs);
                        }
                        if (lin == null) {
                            Lin = "";
                        } else if (lin != null) {
                            Lin = String.valueOf(lin);
                        }
                        if (win == null) {
                            Win = "";
                        } else if (win != null) {
                            Win = String.valueOf(win);
                        }
                        if (hin == null) {
                            Hin = "";
                        } else if (hin != null) {
                            Hin = String.valueOf(hin);
                        }

                        String SQL = "INSERT INTO packinglist (quoteID, commodity, quantity, l, w, h, kgs, length_inches, width_inches, height_inches, cbm, lbs) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

                        try {
                            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                                pstmt.setInt(1, lastKey);
                                pstmt.setString(2, c);
                                pstmt.setString(3, q);
                                pstmt.setString(4, L);
                                pstmt.setString(5, W);
                                pstmt.setString(6, H);
                                pstmt.setString(7, K);
                                pstmt.setString(8, Lin);
                                pstmt.setString(9, Win);
                                pstmt.setString(10, Hin);
                                pstmt.setString(11, m3);
                                pstmt.setString(12, lb);
                                pstmt.addBatch();
                                pstmt.executeBatch();
                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                        }

                        if (includeSailingSchedule == true) {
                            int ssRows = newQuoteSailingScheduleTable.getRowCount();
                            int ssCols = newQuoteSailingScheduleTable.getColumnCount();
                            for (int r = 0; r < ssRows; r++) {
                                String vessel_voyage = String.valueOf(newQuoteSailingScheduleTable.getValueAt(r, 0));
                                String sail_date = String.valueOf(newQuoteSailingScheduleTable.getValueAt(r, 1));
                                String transit_time = String.valueOf(newQuoteSailingScheduleTable.getValueAt(r, 2));
                                String trans_shipment = String.valueOf(newQuoteSailingScheduleTable.getValueAt(r, 3));

                                String sailingScheduleSQL = "INSERT INTO quote_schedule (quote_id, alpha_character, vessel_voyage, sail_date, transit_time, trans_shipment) VALUES(?,?,?,?,?,?)";

                                try {
                                    PreparedStatement psSailingScheduleSQL = conn.prepareStatement(sailingScheduleSQL);
                                    psSailingScheduleSQL.setInt(1, lastKey);
                                    psSailingScheduleSQL.setString(2, " ");
                                    psSailingScheduleSQL.setString(3, vessel_voyage);
                                    psSailingScheduleSQL.setString(4, sail_date);
                                    psSailingScheduleSQL.setString(5, transit_time);
                                    psSailingScheduleSQL.setString(6, trans_shipment);
                                    psSailingScheduleSQL.addBatch();
                                    psSailingScheduleSQL.executeBatch();
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                    JOptionPane.showMessageDialog(null, "There is an error with the sailing schedule.", "Error!", JOptionPane.ERROR_MESSAGE);
                                }

                            }
                        }
                    }

                    //Displays quote ID in window pane
                    JOptionPane.showMessageDialog(null, "Quote ID:" + lastKey);


                    /*
                     *
                     *Create PDF displaying quote information using iText
                     *
                     */
                    //Declare  variables for combination prior to creating PDF
                    int quoteID = lastKey; //Assigned by DB
                    String POL = pol;
                    String POD = pod;
                    String tshpPorts = "";
                    String transShipment = ""; //Will output portPairs + tshpPorts
                    String displayOFT = "$" + OFT + " per " + oftMeasurement;
                    String displayOFTAccessories = "";
                    String bunker = "";//BAF
                    String eca = "";//ECA BAF
                    String thcPDF = "";//THC
                    String wfgPDF = "";//WFG
                    String docFeePDF = "";//Doc Fee
                    String warRiskPDF = "";
                    String disclaimers = "";
                    String mafiDisclaimer = "";
                    String quoteStatus = "";

                    //Adding accessories charges
                    if (accessories == true) {
                        displayOFTAccessories = displayOFT;
                    }

                    //Arrange trans-shipment ports
                    if (!tshp1.equals("") && tshp2.equals("")) {
                        tshpPorts = tshp1;
                    } else if (!tshp1.equals("") && !tshp2.equals("")) {
                        tshpPorts = tshp1 + " & " + tshp2;
                    }

                    //Arrange complete port pairs, POL, POD, & trans-shipment ports
                    if (!tshpPorts.equals("")) {
                        transShipment = tshpPorts;
                    } else {
                        transShipment = "N/A";
                    }

                    //Assign bunker amount
                    if (bafIncluded == true) {
                        bunker = "Included";
                    } else if (!BAF.equals("")) {
                        bunker = BAF + "%";
                    }

                    //Assign ECA amount
                    if (ecaIncluded == true) {
                        eca = "Included";
                    } else if (!ecaBAF.equals("")) {
                        eca = "$" + ecaBAF + " per " + ecaBafMeasurement;
                    }

                    //THC value
                    if (thcIncluded == true) {
                        thcPDF = "Included";
                    } else if (!thc.equals("")) {
                        thcPDF = "$" + thc + " per " + thcMeasurement;
                    } else if (thcAttachment == true) {
                        thcPDF = "See attachment";
                    } else if (thcMeasurement.equals("FAS")) {
                        thcPDF = "FAS";
                    } else if (thcMeasurement.equals("Subject to local charges")) {
                        thcPDF = "Subject to local charges";
                    }

                    //wfgPDF
                    if (wfgIncluded == true) {
                        wfgPDF = "Included";
                    } else if (!wfg.equals("")) {
                        wfgPDF = "$" + wfg + " per " + wfgMeasurement;
                    } else if (wfgAttachment == true) {
                        wfgPDF = "See attachment";
                    } else if (wfgMeasurement.equals("FAS")) {
                        wfgPDF = "FAS";
                    } else if (wfgMeasurement.equals("Subject to local charges")) {
                        wfgPDF = "Subject to local charges";
                    }

                    //docFee
                    if (documentationFeeIncluded == true) {
                        docFeePDF = "Included";
                    } else {
                        docFeePDF = documentationFee;
                    }
                    if (warRisk == true) {
                        warRiskPDF = "3%/OFT";
                    }

                    if (mafiMinimum == true) {
                        mafiDisclaimer = "MAFI minimum charge of $" + mafiMinimumCharge + " per MAFI unit";
                    }

                    if (booked == true) {
                        quoteStatus = "Booked";
                    } else if (denial == true) {
                        quoteStatus = "Decline";
                    } else {
                        quoteStatus = "Active";
                    }

                    new QuotePDf().QuotePDF(String.valueOf(quoteID), timeStamp, newQuoteContactName, customerName, newQuoteContactEmail, quoteStatus, username, pol, pod, tshpPorts, commodityClass, handlingInstructions, accessories, commodityDescription, displayOFT, mafiMinimum, mafiMinimumCharge, bunker, eca, thcPDF, wfgPDF, docFeePDF, warRisk, warRiskPDF, includeCarrierRemarks, includeSailingSchedule, carrierComments, newQuoteSailingScheduleTable, packingListTable, spotRate, contract_rate);

                } catch (SQLException | HeadlessException | IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                }

                //Confirmation that the rate has been stored in desktop folder and on the group drive
                JOptionPane.showMessageDialog(this, "You can find the rate in the desktop folder and on the group S: drive");

                //Clears all input fields upon record insertion
                newQuoteCustomerNameLabel.setText("N/A");
                contactNameTextField.setText("");
                contactEmailTextField.setText("");
                tradeLaneComboBox.setSelectedIndex(0);
                polTextField.setText("");
                podTextField.setText("");
                tshp1TextField.setText("");
                tshp2TextField.setText("");
                commodityClassComboBox.setSelectedIndex(0);
                handlingInstructionsComboBox.setSelectedIndex(0);
                commodityDescriptionTextField.setText("");
                oftTextField.setText("");
                oftMeasurementComboBox.setSelectedIndex(0);
                bafTextField.setText("");
                bafIncludedCheckBox.setSelected(false);
                ecaBAFTextField.setText("");
                ecaBafMeasurementComboBox.setSelectedIndex(0);
                ecaBafIncludedCheckBox.setSelected(false);
                thcTextField.setText("");
                thcMeasurementComboBox.setSelectedIndex(0);
                thcIncludedCheckBox.setSelected(false);
                wfgTextField.setText("");
                wfgMeasurementComboBox.setSelectedIndex(0);
                wfgIncludedCheckBox.setSelected(false);
                documentationFeeComboBox.setSelectedIndex(0);
                documentationFeeIncludedCheckBox.setSelected(false);
                commentsTextArea.setText("");
                warRiskCheckBox.setSelected(false);
                spotRateCheckBox.setSelected(false);
                newQuoteDenialCheckBox.setSelected(false);
                newQuoteBookedCheckBox.setSelected(false);
                thcAttached.setSelected(false);
                wfgAttached.setSelected(false);
                newQuoteAccessoriesCheckBox.setSelected(false);
                contractRateCheckBox.setSelected(false);
                DefaultTableModel mdl = (DefaultTableModel) packingListTable.getModel();
                mdl.setRowCount(0);
                packingListTable.setModel(mdl);
                for (int r = 0; r < packingListTable.getRowCount(); r++) {
                    for (int c = 0; c < 11; c++) {
                        packingListTable.setValueAt("", r, c);
                    }
                }

                for (int r = 0; r < newQuoteSailingScheduleTable.getRowCount(); r++) {
                    for (int c = 0; c < newQuoteSailingScheduleTable.getColumnCount(); c++) {
                        newQuoteSailingScheduleTable.setValueAt("", r, c);
                    }
                }
                includeSailingScheduleCheckBox.setSelected(false);
                mafiMinimumCheckBox.setSelected(false);
                mafiMinimumTextField.setText("");
                newQuoteDenialCheckBox.setSelected(false);
                declineComboBox.setSelectedIndex(0);
                bookingNumberTextField.setText("");
                shipperCommentsTextArea.setText("");
                includeShipperCommentsCheckBox.setSelected(false);
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "There has been an error with the upload. Error: " + e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }


    }//GEN-LAST:event_submitNewQuoteActionPerformed


    private void searchExistingCustomersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchExistingCustomersButtonActionPerformed
        //Connect to DB
        Connection conn = new DBConnection().connect();

        String searchInput = existingCompanyNameTextField.getText();
        //String sql = "select ID, firstname, company from rorocustomers WHERE company='Uni International';";

        String sql = "SELECT ID, firstname, lastname, company FROM rorocustomers WHERE company LIKE ? OR ID LIKE ?;";

        existingCustomer ec = new existingCustomer();

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchInput + "%");
            ps.setString(2, "%" + searchInput + "%");
            ResultSet rs = ps.executeQuery();

            //Display results on table
            existingCustomer.existingCustomerTable.setModel(DbUtils.resultSetToTableModel(rs));

            //Format column headings
            TableColumnModel tcm = existingCustomer.existingCustomerTable.getColumnModel();
            tcm.getColumn(0).setHeaderValue("Quote ID");
            tcm.getColumn(1).setHeaderValue("Contact First Name");
            tcm.getColumn(2).setHeaderValue("Contact Last Name");
            tcm.getColumn(3).setHeaderValue("Company");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());

        }

        ec.setVisible(
                true);
    }//GEN-LAST:event_searchExistingCustomersButtonActionPerformed

    private void existingCustomerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingCustomerButton1ActionPerformed
        // TODO add your handling code here:

        existingCompanyNameTextField.setText("");
        existingCompanyIDTextField.setText("");
        existingCompanyFirstNameTextField.setText("");
        existingCompanyLastNameTextField.setText("");
        existingCompanyOfficePhoneTextField.setText("");
        existingCompanyMobilePhoneTextField.setText("");
        existingCompanyEmailTextField.setText("");
        existingCompanyTitleTextField.setText("");
        existingCompanyCompanyTextField.setText("");
        existingCompanyDBATextField.setText("");
        existingCompanyOTINumberTextField.setText("");
        existingCustomerFreightForwarderRadioButton.setSelected(false);
        existingCustomerNVOCCRadioButton.setSelected(false);
        existingCustomerBCORadioButton.setSelected(false);
        existingCustomerOtherRadioButton.setSelected(false);
        existingCompanyMainPhoneTextField.setText("");
        existingCompanySecondaryPhoneTextField.setText("");
        existingCompanyFaxNumberTextField.setText("");
        existingCompanyMainEmailTextField.setText("");
        existingCompanyAddress1TextField.setText("");
        existingCompanyAddress2TextField.setText("");
        existingCompanyCityTextField.setText("");
        existingCompanyStateTextField.setText("");
        existingCompanyZipTextField.setText("");
        existingCompanyCountryTextField.setText("");
        existingCompanyContractNumberTextField.setText("");
        existingCompanyCommentsTextArea.setText("");
        existingCustomerContractExpirationDatePicker.getJFormattedTextField().setText("");


    }//GEN-LAST:event_existingCustomerButton1ActionPerformed

    private void newCustomerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCustomerButton1ActionPerformed
        // TODO add your handling code here:
        existingCustomerPanel.setVisible(false);
        newCustomerPanel.setVisible(true);

        newCustomerFirstName.setText("");
        newCustomerLastName.setText("");
        newCustomerOfficePhone.setText("");
        newCustomerMobilePhoneTextField.setText("");
        newCustomerEmailTextField.setText("");
        newCustomerTitleTextField.setText("");
        newCustomerCompanyTextField.setText("");
        newCustomerDBATextField.setText("");
        newCustomerOTINumberTextField.setText("");
        newCustomerMainPhoneTextField.setText("");
        newCustomerSecondaryPhoneNumber.setText("");
        newCustomerFaxNumber.setText("");
        newCustomerAddress1.setText("");
        newCustomerAddress2.setText("");
        newCustomerCity.setText("");
        newCustomerCountry.setText("");
        newCustomerZipCode.setText("");
        newCustomerState.setText("");
        newCustomerContractYesRadioButton.setSelected(false);
        newCustomerNoContractRadioButton.setSelected(false);
        newCustomerContractNumberTextField.setText("");
        newCustomerComments.setText("");

    }//GEN-LAST:event_newCustomerButton1ActionPerformed

    private void newCustomerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCustomerMenuItemActionPerformed
        newQuotePanel.setVisible(false);
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(false);
        searchPanel.setVisible(false);
        customerInformationPanel.setVisible(true);
    }//GEN-LAST:event_newCustomerMenuItemActionPerformed

    private void salesCenterMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesCenterMenuItemActionPerformed
        newQuotePanel.setVisible(false);
        userInformationPanel.setVisible(true);
        updateEditQuotePanel.setVisible(false);
        searchPanel.setVisible(false);
        customerInformationPanel.setVisible(false);
    }//GEN-LAST:event_salesCenterMenuItemActionPerformed

    private void UserInformationCenterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserInformationCenterButtonActionPerformed
        newQuotePanel.setVisible(false);
        userInformationPanel.setVisible(true);
        updateEditQuotePanel.setVisible(false);
        searchPanel.setVisible(false);
        customerInformationPanel.setVisible(false);
        publishingCenterPanel.setVisible(false);

        new UserInformationActivity().UpdateUserInformationActivity(userID, username);
    }//GEN-LAST:event_UserInformationCenterButtonActionPerformed

    private void searchCenterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCenterButtonActionPerformed
        newQuotePanel.setVisible(false);
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(false);
        searchPanel.setVisible(true);
        customerInformationPanel.setVisible(false);
        publishingCenterPanel.setVisible(false);

        //Clears the result table
        DefaultTableModel model = (DefaultTableModel) searchResultsTable.getModel();
        int cols = searchResultsTable.getColumnCount();
        int rows = searchResultsTable.getRowCount();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                model.setValueAt("", r, c);
            }
        }
        queryNameTextField.setText("");
        dateTextField.setText("");

    }//GEN-LAST:event_searchCenterButtonActionPerformed

    private void pWarRiskCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pWarRiskCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pWarRiskCheckBoxActionPerformed

    private void editUpdateCustomerInformationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUpdateCustomerInformationButtonActionPerformed
        // Saves changes made to existing customer to DB
        Connection conn = new DBConnection().connect();

        String firstname = existingCompanyFirstNameTextField.getText();
        String lastname = existingCompanyLastNameTextField.getText();
        String officephone = existingCompanyOfficePhoneTextField.getText();
        String mobilephone = existingCompanyMobilePhoneTextField.getText();
        String email = existingCompanyEmailTextField.getText();
        String title = existingCompanyTitleTextField.getText();
        String contact_office_address = pcOfficeAddressTextField.getText();
        String contact_office_suite = updatePCSuiteTextField.getText();
        String contact_office_city = updatePCCityTextField.getText();
        String contact_office_state = updatePCStateTextField.getText();
        String contact_office_zip_code = updatePCZipTextField.getText();
        String contact_office_country = updatePCCountryTextField.getText();
        String companyname = existingCompanyCompanyTextField.getText();
        String dba = existingCompanyDBATextField.getText();
        String oti = existingCompanyOTINumberTextField.getText();
        Boolean freight_forwarder = existingCustomerFreightForwarderRadioButton.isSelected();
        Boolean NVOCC = existingCustomerNVOCCRadioButton.isSelected();
        Boolean BCO = existingCustomerBCORadioButton.isSelected();
        Boolean Other = existingCustomerOtherRadioButton.isSelected();
        Boolean contractyes = existingCompanyContractYesRadioButton.isSelected();
        Boolean contractno = existingCompanyNoContractRadioButton.isSelected();
        Boolean contract = false;
        if (contractyes == true) {
            contract = true;
        } else if (contractno == true || (contractyes != true && contractno != true)) {
            contract = false;
        }
        String contractnumber = existingCompanyContractNumberTextField.getText();
        String mainphone = existingCompanyMainPhoneTextField.getText();
        String secondaryphone = existingCompanySecondaryPhoneTextField.getText();
        String fax = existingCompanyFaxNumberTextField.getText();
        String groupemail = existingCompanyMainEmailTextField.getText();
        String address1 = existingCompanyAddress1TextField.getText();
        String address2 = existingCompanyAddress2TextField.getText();
        String city = existingCompanyCityTextField.getText();
        String zip = existingCompanyZipTextField.getText();
        String state = existingCompanyStateTextField.getText();
        String country = existingCompanyCountryTextField.getText();
        String comments = existingCompanyCommentsTextArea.getText();
        String ID = existingCompanyIDTextField.getText();
        String contractExpiration = existingCustomerContractExpirationDatePicker.getJFormattedTextField().getText();

        String sql = "UPDATE rorocustomers SET firstname=?, lastname=?, officephone=?, mobilephone=?, email=?, title=?, contact_office_address=?, contact_office_address2=?, contact_office_city=?, contact_office_state=?, contact_office_zip_code=?, contact_office_country=?, birthday=?, company=?, dba=?, oti=?,freight_forwarder=?, NVOCC=?, BCO=?, Other=?, mainphone=?, secondaryphone=?, fax=?, address1=?, address2=?, city=?, state=?, zip=?, country=?, contract=?, contractnumber=?, contractExpiration=?, comments=?, mainemail=? WHERE ID=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, officephone);
            ps.setString(4, mobilephone);
            ps.setString(5, email);
            ps.setString(6, title);
            ps.setString(7, contact_office_address);
            ps.setString(8, contact_office_suite);
            ps.setString(9, contact_office_city);
            ps.setString(10, contact_office_state);
            ps.setString(11, contact_office_zip_code);
            ps.setString(12, contact_office_country);
            ps.setString(13, "");
            ps.setString(14, companyname);
            ps.setString(15, dba);
            ps.setString(16, oti);
            ps.setBoolean(17, freight_forwarder);
            ps.setBoolean(18, NVOCC);
            ps.setBoolean(19, BCO);
            ps.setBoolean(20, Other);
            ps.setString(21, mainphone);
            ps.setString(22, secondaryphone);
            ps.setString(23, fax);
            ps.setString(24, address1);
            ps.setString(25, address2);
            ps.setString(26, city);
            ps.setString(27, state);
            ps.setString(28, zip);
            ps.setString(29, country);
            ps.setBoolean(30, contract);
            ps.setString(31, contractnumber);
            ps.setString(32, contractExpiration);
            ps.setString(33, comments);
            ps.setString(34, groupemail);
            ps.setString(35, ID);
            ps.executeUpdate(); // Execute the update
            ps.closeOnCompletion(); //Close the statement on completion

            JOptionPane.showMessageDialog(null, companyname + " ID: " + ID + " has been successfully updated!");
            ClearExistingCustomerPanel(); // Clear the inputs
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error!");
            System.out.println(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));

        }


    }//GEN-LAST:event_editUpdateCustomerInformationButtonActionPerformed

    private void searchExistingCustomersButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchExistingCustomersButton1ActionPerformed
        ClearExistingCustomerPanel();
    }//GEN-LAST:event_searchExistingCustomersButton1ActionPerformed

    private void selectOustandingQuoteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectOustandingQuoteButtonActionPerformed
        int selectedRowIndex = outstandingQuotesTable.getSelectedRow();
        String quoteID = String.valueOf(outstandingQuotesTable.getValueAt(selectedRowIndex, 0));
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(true);
        updateQuoteInformation(quoteID);
    }//GEN-LAST:event_selectOustandingQuoteButtonActionPerformed

    private void selectOutstandingQuoteRequireAttentionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectOutstandingQuoteRequireAttentionButtonActionPerformed
        int selectedRowIndex = requireAttentionTable.getSelectedRow();
        String quoteID = String.valueOf(requireAttentionTable.getValueAt(selectedRowIndex, 0));
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(true);
        updateQuoteInformation(quoteID);
    }//GEN-LAST:event_selectOutstandingQuoteRequireAttentionButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //setLocationRelativeTo(null);
        DBSearch DBS = new DBSearch();
        DBS.setLocationRelativeTo(this);
        DBS.setVisible(true);
        Calendar calendar = Calendar.getInstance();
        String date = new SimpleDateFormat("MM/dd/YYYY").format(calendar.getTime());
        DBSearch.dateTextField.setText(date);

    }//GEN-LAST:event_jButton2ActionPerformed


    private void updateQuoteIDSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateQuoteIDSearchButtonActionPerformed
        //Get quoteID from search field
        String quoteID = updateQuoteIDTextArea.getText();
        // Fill in the quote information 
        updateQuoteInformation(quoteID);


    }//GEN-LAST:event_updateQuoteIDSearchButtonActionPerformed

    protected static void updateQuoteInformation(String quoteID) {
        //SQL to get quote based on ID
        String sql = "SELECT * FROM allquotes WHERE ID=?";
        try { // Execute the sql, return data and populate the panel
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, quoteID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { // If the query returns results
                String customerName = rs.getString("customerName");
                String tradeLane = rs.getString("tradeLane");
                String pol = rs.getString("pol");
                String pod = rs.getString("pod");
                String tshp1 = rs.getString("tshp1");
                String tshp2 = rs.getString("tshp2");
                String commodityClass = rs.getString("comm_class");
                String commodityDescription = rs.getString("comm_description");
                String handlingInstructions = rs.getString("handling_instructions");
                Boolean accessories = rs.getBoolean("accessories");
                String oft = rs.getString("rate");
                String oftUnit = rs.getString("rate_unit");
                String baf = rs.getString("baf");
                Boolean bafIncluded = rs.getBoolean("bafIncluded");
                String eca = rs.getString("eca_baf");
                String ecaUnit = rs.getString("eca_unit");
                Boolean ecaIncluded = rs.getBoolean("ecaIncluded");
                String thc = rs.getString("thc");
                String thcUnit = rs.getString("thc_unit");
                Boolean thcIncluded = rs.getBoolean("thcIncluded");
                String wfg = rs.getString("wfg");
                String wfgUnit = rs.getString("wfg_unit");
                Boolean wfgIncluded = rs.getBoolean("wfgIncluded");
                String documentationFee = rs.getString("doc_fee");
                Boolean documentationFeeIncluded = rs.getBoolean("documentationFeeIncluded");
                Boolean warRisk = rs.getBoolean("war_risk");
                Boolean spotRate = rs.getBoolean("spot_rate");
                Boolean booked = rs.getBoolean("booked");
                String bookingNumber = rs.getString("bookingNumber");
                String comments = rs.getString("comments");
                Boolean thcAttachedToEmail = rs.getBoolean("thcAttached");
                Boolean wfgAttachedToEmail = rs.getBoolean("wfgAttached");
                String authorID = rs.getString("user_ID");
                String updaterID = rs.getString("updateUserID");
                String currentAlphaNumeral = rs.getString("alpha_numeral");
                Boolean deny = rs.getBoolean("deny");
                Boolean mafi = rs.getBoolean("mafiMinimum");
                String mafiCharge = rs.getString("mafiMinimumCharge");
                Boolean contractRate = rs.getBoolean("contract_rate");
                String reason_for_decline = rs.getString("reason_for_decline");
                Boolean feedback = rs.getBoolean("feedback");
                String feedbackType = rs.getString("feedbackType");
                String feedbackDescription = rs.getString("feedbackDescription");
                Boolean duplicate = rs.getBoolean("duplicateRate");
                String contactName = rs.getString("contactName");
                String contactEmail = rs.getString("contactEmail");
                String dateQuoted = rs.getString("date");
                String dateUpdated = rs.getString("dateUpdated");
                String carrierComments = rs.getString("carrierComments");

                //Fill Packing List
                insertPackingListInformation(conn, quoteID);

                // Fill the sailing schedule table
                insertSailingScheduleInformation(conn, quoteID, currentAlphaNumeral);

                //Get current alph-numeral from DB and display or display N/A if none exists
                if (currentAlphaNumeral == null || currentAlphaNumeral.equals("")) {
                    currentAlphaNumeralLabel.setText("N/A");
                } else if (!currentAlphaNumeral.equals("")) {
                    currentAlphaNumeralLabel.setText(currentAlphaNumeral);
                }

                String current = currentAlphaNumeralLabel.getText();

                if (current.equals("") || current.equals("N/A")) {
                    updateAlphaNumeralTextField.setText("A");
                } else if (!current.equals("") || current.equals("N/A")) {
                    int charValue = current.charAt(0);
                    String next = String.valueOf((char) (charValue + 1));
                    updateAlphaNumeralTextField.setText(next);
                }

                if (ecaIncluded == true) {
                    eca = "";
                    ecaUnit = "N/A";
                }
                if (thcIncluded == true) {
                    thc = "";
                    thcUnit = "N/A";
                }
                if (wfgIncluded == true) {
                    wfg = "";
                    wfgUnit = "N/A";
                }
                if (documentationFeeIncluded == true) {
                    documentationFee = "N/A";
                }

                //Returns the value of the author and displays on authorLabel
                String sqlAuthor = "SELECT * FROM authorized_users WHERE userID ='" + authorID + "';";
                PreparedStatement ps2 = conn.prepareStatement(sqlAuthor);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String authorFN = rs2.getString("firstName");
                    String authorLN = rs2.getString("lastName");
                    String author = authorLN + ", " + authorFN;
                    authorLabel.setText(author);
                }

                //Returns the value of the updater and displays on updatedby label
                String updateAuthor = "SELECT * FROM authorized_users WHERE userID='" + updaterID + "';";
                PreparedStatement ps3 = conn.prepareStatement(updateAuthor);
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    String updaterFN = rs3.getString("firstName");
                    String updaterLN = rs3.getString("lastName");
                    String updater = updaterLN + ", " + updaterFN;
                    lastUpdatedByLabel.setText(updater);
                }

                updateQuoteIDTextArea.setText(quoteID);
                updateEditQuoteCustomerNameLabel.setText(customerName);
                updateTradeLane.setSelectedItem(tradeLane);
                updatePOLTextField.setText(pol);
                updatePODTextField.setText(pod);
                updateTshp1TextField.setText(tshp1);
                updateTshp2TextField.setText(tshp2);
                updateCommodityClassComboBox.setSelectedItem(commodityClass);
                updateCommodityDescriptionTextField.setText(commodityDescription);
                updateHandlingInstructionsComboBox.setSelectedItem(handlingInstructions);
                updateQuoteAccessoriesCheckBox.setSelected(accessories);
                updateOFTTextField.setText(oft);
                updateOftUnitComboBox.setSelectedItem(oftUnit);
                updateBAFTextField.setText(baf);
                updateBafIncludedCheckBox.setSelected(bafIncluded);
                updateEcaBafTextField.setText(eca);
                updateEcaComboBox.setSelectedItem(ecaUnit);
                updateEcaIncludedCheckBox.setSelected(ecaIncluded);
                updateTHCTextField.setText(thc);
                updateThcComboBox.setSelectedItem(thcUnit);
                updateThcIncludedCheckBox.setSelected(thcIncluded);
                updateThcAttachedCheckBox.setSelected(thcAttachedToEmail);
                updateWfgTextField.setText(wfg);
                updateWfgComboBox.setSelectedItem(wfgUnit);
                updateWfgIncludedCheckBox.setSelected(wfgIncluded);
                updateWfgAttachedCheckBox.setSelected(wfgAttachedToEmail);
                updateDocumentationFeeComboBox.setSelectedItem(documentationFee);
                updateDocFeeIncludedCheckBox.setSelected(documentationFeeIncluded);
                updateWarRiskCheckBox.setSelected(warRisk);
                updateSpotRateCheckBox.setSelected(spotRate);
                editQuoteDuplicateRateCheckBox.setSelected(duplicate);
                updateBookedCheckBox.setSelected(booked);
                updateEditQuoteBookingNumberTextField.setText(bookingNumber);
                updateCommentsTextArea.setText(comments);
                updateDeclineCheckBox.setSelected(deny);
                updateQuoteMAFIMinimumCheckBox.setSelected(mafi);
                updateEditMAFIMinimumTextField.setText(mafiCharge);
                updateContractRateCheckBox.setSelected(contractRate);
                quoteCreatedLabel.setText(dateQuoted);
                quoteLastUpdatedLabel.setText(dateUpdated);
                if (reason_for_decline.equals("") || reason_for_decline == null) {
                    updateDeclineComboBox.setSelectedIndex(0);
                } else {
                    updateDeclineComboBox.setSelectedItem(reason_for_decline);
                }
                quoteFeedbackCheckBox.setSelected(feedback);
                if (feedbackType == null) {
                    quoteFeedbackComboBox.setSelectedIndex(0);
                } else {
                    quoteFeedbackComboBox.setSelectedItem(feedbackType);
                }
                quoteFeedbackTextField.setText(feedbackDescription);
                updateContactNameTextField.setText(contactName);
                updateContactEmailTextField.setText(contactEmail);
                updateShipperCommentsTextArea.setText(carrierComments);
            }

        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void insertPackingListInformation(Connection conn, String quoteID) {
        //SQL to retreive information from the packing list schema based on the quote ID 
        String packingList = "SELECT commodity AS 'Commodity', quantity AS 'Qty', l AS 'L(cm)', w AS 'W(cm)', h AS 'H(cm)', kgs AS 'Kgs', length_inches AS'L(in)', width_inches AS 'W(in)', height_inches AS 'H(in)', cbm AS 'CBM', lbs AS 'Lbs', ID FROM packingList WHERE quoteID =?";
        try {
            PreparedStatement packingListData = conn.prepareStatement(packingList);
            packingListData.setString(1, quoteID);
            ResultSet rs1 = packingListData.executeQuery();
            updateEditPackingListTable.setModel(DbUtils.resultSetToTableModel(rs1));
            TableColumnModel tcm = MainMenu.updateEditPackingListTable.getColumnModel();
            tcm.getColumn(11).setMaxWidth(-9999);
            tcm.getColumn(11).setMinWidth(-9999);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:\n" + e.getMessage(), "Packing List Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void insertSailingScheduleInformation(Connection conn, String quoteID, String currentAlphaNumeral) {
        String getSailingScheduleSQL = "SELECT vessel_voyage AS 'Vessel/Voyage', sail_date AS 'Sail Date', transit_time AS 'T/T', trans_shipment AS 'Transshipment', ID FROM quote_schedule WHERE  quote_ID=? AND alpha_character=?";
        try {
            PreparedStatement psGetSailingSchedule = conn.prepareStatement(getSailingScheduleSQL);
            psGetSailingSchedule.setString(1, quoteID);
            psGetSailingSchedule.setString(2, currentAlphaNumeral);
            ResultSet rsGetSailingSchedule = psGetSailingSchedule.executeQuery();
            updateEditSailingScheduleTable.setModel(DbUtils.resultSetToTableModel(rsGetSailingSchedule));
            TableColumnModel tcm = updateEditSailingScheduleTable.getColumnModel();
            tcm.getColumn(4).setMaxWidth(-9999);
            tcm.getColumn(4).setMinWidth(-9999);
            if (rsGetSailingSchedule.next()) {
                TableColumn updateTshipColumn = updateEditSailingScheduleTable.getColumnModel().getColumn(3);
                JComboBox updateTshipComboBox = new JComboBox();
                updateTshipComboBox.addItem("");
                updateTshipComboBox.addItem("Yes");
                updateTshipComboBox.addItem("No");
                updateTshipComboBox.setSelectedItem(rsGetSailingSchedule.getString("Transshipment"));
                updateTshipColumn.setCellEditor(new DefaultCellEditor(updateTshipComboBox));
            }
            SailingScheduleTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage(), "Sailing Schedule Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCancelButtonActionPerformed
        // Cancel update & clear all fields
        ClearUpdateQuotePanel();

    }//GEN-LAST:event_updateCancelButtonActionPerformed

//Update quote button
    private void updateEditQuoteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEditQuoteButtonActionPerformed

        //Establish new connection with DB
        Connection conn = new DBConnection().connect();

        String customerName = updateEditQuoteCustomerNameLabel.getText();
        String contactName = updateContactNameTextField.getText();
        String contactEmail = updateContactEmailTextField.getText();
        String tradeLane = updateTradeLane.getSelectedItem().toString();
        String pol = updatePOLTextField.getText();
        String pod = updatePODTextField.getText();
        String tshp1 = updateTshp1TextField.getText();
        String tshp2 = updateTshp2TextField.getText();
        String commodityClass = updateCommodityClassComboBox.getSelectedItem().toString();
        String commodityDescription = updateCommodityDescriptionTextField.getText();
        String handlingInstruction = updateHandlingInstructionsComboBox.getSelectedItem().toString();
        String oft = updateOFTTextField.getText();
        String oftUnit = updateOftUnitComboBox.getSelectedItem().toString();
        String baf = updateBAFTextField.getText();
        Boolean bafInclude = updateBafIncludedCheckBox.isSelected();
        String eca = updateEcaBafTextField.getText();
        String ecaUnit = updateEcaComboBox.getSelectedItem().toString();
        Boolean ecaInclude = updateEcaIncludedCheckBox.isSelected();
        String thc = updateTHCTextField.getText();
        String thcUnit = updateThcComboBox.getSelectedItem().toString();
        Boolean thcIncluded = updateThcIncludedCheckBox.isSelected();
        Boolean thcAttachedToEmail = updateThcAttachedCheckBox.isSelected();
        String wfg = updateWfgTextField.getText();
        String wfgUnit = updateWfgComboBox.getSelectedItem().toString();
        Boolean wfgIncluded = updateWfgIncludedCheckBox.isSelected();
        Boolean wfgAttachedToEmail = updateWfgAttachedCheckBox.isSelected();
        String documentationFee = updateDocumentationFeeComboBox.getSelectedItem().toString();
        Boolean documentationFeeIncluded = updateDocFeeIncludedCheckBox.isSelected();
        Boolean warRisk = updateWarRiskCheckBox.isSelected();
        Boolean spotRate = updateSpotRateCheckBox.isSelected();
        Boolean duplicateRate = editQuoteDuplicateRateCheckBox.isSelected();
        Boolean booked = updateBookedCheckBox.isSelected();
        String bookingNumber = "";
        String comments = updateCommentsTextArea.getText();
        Boolean decline = updateDeclineCheckBox.isSelected();
        String quoteID = updateQuoteIDTextArea.getText();
        Boolean accessories = updateQuoteAccessoriesCheckBox.isSelected();
        String dateUpdated = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        String timeStamp = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        String updateAlphaNumeral = updateAlphaNumeralTextField.getText();
        String reason_for_decline = "N/A";
        String deny = updateDeclineComboBox.getSelectedItem().toString();
        Boolean contract_rate = updateContractRateCheckBox.isSelected();
        Boolean feedback = quoteFeedbackCheckBox.isSelected();
        String feedbackType = quoteFeedbackComboBox.getSelectedItem().toString();
        String feedbackDescription = quoteFeedbackTextField.getText();
        Boolean updateIncludeSailingSchedule = editQuoteIncludeSailingScheduleCheckBox.isSelected();
        Boolean includeCarrierRemarks = editQuoteIncludeShipperCommentsCheckBox.isSelected();
        String carrierComments = updateShipperCommentsTextArea.getText();

        if (booked == true) {
            bookingNumber = updateEditQuoteBookingNumberTextField.getText();
        } else {
            bookingNumber = "";
        }

        if (feedback == true && feedbackType.equals("N/A")) {
            JOptionPane.showMessageDialog(null, "You must select a feedback type");
        } else if (feedback == true && feedbackType.equals("Other") && feedbackDescription.equals("")) {
            JOptionPane.showMessageDialog(null, "Please add a short description");
        }

        if (decline == true && deny.equals("N/A")) {
            JOptionPane.showMessageDialog(null, "You must select a reason for declining this cargo");
        } else if (decline = true && !deny.equals("N/A")) {
            reason_for_decline = declineComboBox.getSelectedItem().toString();
        } else if (decline != true) {
            reason_for_decline = "N/A";
        }

        Boolean mafiMinimum = updateQuoteMAFIMinimumCheckBox.isSelected();
        String mafiMinimumCharge = updateEditMAFIMinimumTextField.getText();

        if (bafInclude == true) {
            baf = "";

        }
        if (ecaInclude == true) {
            eca = "Inclusive";
            ecaUnit = "";
        }
        if (thcIncluded == true) {
            thc = "Inclusive";
            thcUnit = "";
        }
        if (wfgIncluded == true) {
            wfg = "";
            wfgUnit = "";

        }
        if (documentationFeeIncluded == true) {
            documentationFee = "Inclusive";
        }
        if (updateThcAttachedCheckBox.isSelected() == true) {
            thc = "";
            thcUnit = "";
            updateThcAttachedCheckBox.setSelected(true);
            updateThcIncludedCheckBox.setSelected(false);
        }
        if (updateWfgAttachedCheckBox.isSelected() == true) {
            wfg = "";
            wfgUnit = "";
            updateWfgAttachedCheckBox.setSelected(true);
            updateWfgIncludedCheckBox.setSelected(false);
        }

        if (updateAlphaNumeral.equals("")) {
            JOptionPane.showMessageDialog(null, "You must enter an alpha-numerical value before you can submit the update!");
            exit();
        } else {

            //Get id from authorized_users;
            String IDsql = "SELECT * FROM authorized_users WHERE username=?";
            try {
                PreparedStatement ps1 = conn.prepareStatement(IDsql);
                ps1.setString(1, username);
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    String ID = rs1.getString("userID");

                    //Enter updated quote into allquotes Database with new timestamp and alphanumeral
                    String sql = "UPDATE allquotes SET tradeLane=?,pol=?, pod=?, tshp1=?, tshp2=?, comm_class=?, handling_instructions=?, comm_description=?, rate=?, rate_unit=?, baf=?, eca_baf=?, eca_unit=?, thc=?, thc_unit=?, wfg=?, wfg_unit=?, doc_fee=?, war_risk=?, spot_rate=?, booked=?, comments=?, dateUpdated=?, DATE_UPDATED=?, deny=?, updateUserID=?, thcIncluded=?, wfgIncluded=?, thcAttached=?, wfgAttached=?, bafIncluded=?, ecaIncluded=?, documentationFeeIncluded=?, alpha_numeral=?, accessories=?, mafiMinimum=?, mafiMinimumCharge=?, reason_for_decline=?,contract_rate=?, customerName=?, feedback=?, feedbackType=?, feedbackDescription=?, bookingNumber=?, contactName=?, contactEmail=?, duplicateRate=?, carrierComments=? WHERE ID=?";

                    try {
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, tradeLane);
                        ps.setString(2, pol);
                        ps.setString(3, pod);
                        ps.setString(4, tshp1);
                        ps.setString(5, tshp2);
                        ps.setString(6, commodityClass);
                        ps.setString(7, handlingInstruction);
                        ps.setString(8, commodityDescription);
                        ps.setString(9, oft);
                        ps.setString(10, oftUnit);
                        ps.setString(11, baf);
                        ps.setString(12, eca);
                        ps.setString(13, ecaUnit);
                        ps.setString(14, thc);
                        ps.setString(15, thcUnit);
                        ps.setString(16, wfg);
                        ps.setString(17, wfgUnit);
                        ps.setString(18, documentationFee);
                        ps.setBoolean(19, warRisk);
                        ps.setBoolean(20, spotRate);
                        ps.setBoolean(21, booked);
                        ps.setString(22, comments);
                        ps.setString(23, dateUpdated);
                        ps.setString(24, dateUpdated);
                        ps.setBoolean(25, decline);
                        ps.setString(26, ID);
                        ps.setBoolean(27, thcIncluded);
                        ps.setBoolean(28, wfgIncluded);
                        ps.setBoolean(29, thcAttachedToEmail);
                        ps.setBoolean(30, wfgAttachedToEmail);
                        ps.setBoolean(31, bafInclude);
                        ps.setBoolean(32, ecaInclude);
                        ps.setBoolean(33, documentationFeeIncluded);
                        ps.setString(34, updateAlphaNumeral);
                        ps.setBoolean(35, accessories);
                        ps.setBoolean(36, mafiMinimum);
                        ps.setString(37, mafiMinimumCharge);
                        ps.setString(38, reason_for_decline);
                        ps.setBoolean(39, contract_rate);
                        ps.setString(40, customerName);
                        ps.setBoolean(41, feedback);
                        ps.setString(42, feedbackType);
                        ps.setString(43, feedbackDescription);
                        ps.setString(44, bookingNumber);
                        ps.setString(45, contactName);
                        ps.setString(46, contactEmail);
                        ps.setBoolean(47, duplicateRate);
                        ps.setString(48, carrierComments);
                        ps.setString(49, quoteID);

                        // Execute the update
                        ps.executeUpdate();

                        //If this cargo is booked then set the booked user Id to the current user
                        if (booked == true) {
                            String bookedSQL = "UPDATE allquotes SET bookedUserID=? WHERE ID=?;";
                            try {
                                PreparedStatement psBooked = conn.prepareStatement(bookedSQL);
                                psBooked.setString(1, ID);
                                psBooked.setString(2, quoteID);
                                psBooked.executeUpdate();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            String bookedSQL = "UPDATE allquotes SET bookedUserID='N/A' WHERE ID='" + quoteID + "';";
                            try {
                                PreparedStatement psBooked = conn.prepareStatement(bookedSQL);
                                psBooked.setString(1, "N/A");
                                psBooked.setString(2, quoteID);
                                psBooked.executeUpdate();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        int rows = updateEditPackingListTable.getRowCount(); //Get the number of rows on the current packing list table
                        int cols = updateEditPackingListTable.getColumnCount(); //Get the number of columns on the current packing list table
                        for (int row = 0; row < rows; row++) {
                            for (int col = 0; col < cols; col++) {
                                String commodity = (String) updateEditPackingListTable.getValueAt(row, 0);
                                String quantity = (String) updateEditPackingListTable.getValueAt(row, 1);
                                String l = (String) updateEditPackingListTable.getValueAt(row, 2);
                                String w = (String) updateEditPackingListTable.getValueAt(row, 3);
                                String h = (String) updateEditPackingListTable.getValueAt(row, 4);
                                String kgs = (String) updateEditPackingListTable.getValueAt(row, 5);
                                String lin = (String) updateEditPackingListTable.getValueAt(row, 6);
                                String win = (String) updateEditPackingListTable.getValueAt(row, 7);
                                String hin = (String) updateEditPackingListTable.getValueAt(row, 8);
                                String cbm = (String) updateEditPackingListTable.getValueAt(row, 9);
                                String lbs = (String) updateEditPackingListTable.getValueAt(row, 10);
                                String tableColumnID = String.valueOf(updateEditPackingListTable.getValueAt(row, 11));//String.valueOf(updateEditPackingListTable.getValueAt(row, col));
                                String c = null;
                                String q = null;
                                String L = null;
                                String W = null;
                                String H = null;
                                String Lin = null;
                                String Hin = null;
                                String Win = null;
                                String m3 = null;
                                String K = null;
                                String lb = null;

                                if (commodity.equals("null")) {
                                    c = "";
                                } else if (!commodity.equals("null")) {
                                    c = commodity;
                                }
                                if (quantity == null) {
                                    q = "";
                                } else if (quantity != null) {
                                    q = quantity;
                                }
                                if (l == null) {
                                    L = "";
                                } else if (l != null) {
                                    L = l;
                                }
                                if (w == null) {
                                    W = "";
                                } else if (w != null) {
                                    W = w;
                                }
                                if (h == null) {
                                    H = "";
                                } else if (h != null) {
                                    H = h;
                                }
                                if (w == null) {
                                    W = "";
                                } else if (w != null) {
                                    W = w;
                                }
                                if (cbm == null) {
                                    m3 = "";
                                } else if (cbm != null) {
                                    m3 = cbm;
                                }
                                if (kgs == null) {
                                    K = "";
                                } else if (kgs != null) {
                                    K = kgs;
                                }
                                if (lbs == null) {
                                    lb = "";
                                } else if (lbs != null) {
                                    lb = lbs;
                                }
                                if (lin == null) {
                                    Lin = "";
                                } else if (lin != null) {
                                    Lin = lin;
                                }
                                if (win == null) {
                                    Win = "";
                                } else if (win != null) {
                                    Win = win;
                                }
                                if (hin == null) {
                                    Hin = "";
                                } else if (hin != null) {
                                    Hin = hin;
                                }

                                String SQL = "UPDATE packinglist SET commodity=?, quantity=?, l=?, w=?, h=?, kgs=?, length_inches=?, width_inches=?, height_inches=?, cbm=?, lbs=?  WHERE quoteID=? AND ID=?";

                                try {
                                    PreparedStatement pstmt = conn.prepareStatement(SQL);
                                    pstmt.setString(1, c);
                                    pstmt.setString(2, q);
                                    pstmt.setString(3, L);
                                    pstmt.setString(4, W);
                                    pstmt.setString(5, H);
                                    pstmt.setString(6, K);
                                    pstmt.setString(7, Lin);
                                    pstmt.setString(8, Win);
                                    pstmt.setString(9, Hin);
                                    pstmt.setString(10, m3);
                                    pstmt.setString(11, lb);
                                    pstmt.setString(12, quoteID);
                                    pstmt.setString(13, tableColumnID);
                                    pstmt.addBatch();
                                    pstmt.executeBatch();

                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, "Table Error: " + e.getMessage());
                                }
                            }

                        }
                        if (updateIncludeSailingSchedule == true) {
                            int ssRows = updateEditSailingScheduleTable.getRowCount();
                            for (int r = 0; r < ssRows; r++) {
                                String vessel_voyage = String.valueOf(updateEditSailingScheduleTable.getValueAt(r, 0));
                                String sail_date = String.valueOf(updateEditSailingScheduleTable.getValueAt(r, 1));
                                String transit_time = String.valueOf(updateEditSailingScheduleTable.getValueAt(r, 2));
                                String trans_shipment = String.valueOf(updateEditSailingScheduleTable.getValueAt(r, 3));

                                String updateSailingScheduleSQL = "INSERT INTO quote_schedule (quote_id, alpha_character, vessel_voyage, sail_date, transit_time, trans_shipment) VALUES(?,?,?,?,?,?)";

                                try {
                                    PreparedStatement psUpdateSailingScheduleSQL = conn.prepareStatement(updateSailingScheduleSQL);
                                    psUpdateSailingScheduleSQL.setString(1, quoteID);
                                    psUpdateSailingScheduleSQL.setString(2, updateAlphaNumeral);
                                    psUpdateSailingScheduleSQL.setString(3, vessel_voyage);
                                    psUpdateSailingScheduleSQL.setString(4, sail_date);
                                    psUpdateSailingScheduleSQL.setString(5, transit_time);
                                    psUpdateSailingScheduleSQL.setString(6, trans_shipment);
                                    psUpdateSailingScheduleSQL.addBatch();
                                    psUpdateSailingScheduleSQL.executeBatch();
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                    JOptionPane.showMessageDialog(null, "There is an error with the sailing schedule.", "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }

                        JOptionPane.showMessageDialog(null, "Quote ID: " + quoteID + updateAlphaNumeral + " has been successfully updated");

                        /*
                         *
                         *Create PDF displaying quote information using iText
                         *
                         */
                        //Declare all variables for PDF output
                        String portPairs = pol + " - " + pod;
                        String tshpPorts = "";
                        String displayOFT = "$" + oft + " " + oftUnit;
                        String bunker = "";
                        String thcPDF = "";
                        String wfgPDF = "";
                        String documentationFeePdf = "";
                        String quoteStatus = "";
                        String warRiskPDF = null;

                        //Arrange trans-shipment ports
                        if (!tshp1.equals("") && tshp2.equals("")) {
                            tshpPorts = tshp1;
                        } else if (!tshp1.equals("") && !tshp2.equals("")) {
                            tshpPorts = tshp1 + " & " + tshp2;
                        }
                        //Assign bunker amount
                        if (bafInclude == true) {
                            bunker = "Included";
                        } else if (!baf.equals("")) {
                            bunker = baf + "%";
                        }

                        //Assign ECA amount
                        if (ecaInclude == true) {
                            eca = "Included";
                        } else if (!eca.equals("")) {
                            eca = "$" + eca + " per " + ecaUnit;
                        }

                        //THC value
                        if (thcIncluded == true) {
                            thcPDF = "Included";
                        } else if (!thc.equals("")) {
                            thcPDF = "$" + thc + " per " + thcUnit;
                        } else if (tradeLane.equals("Foreign to Foreign") && thcIncluded == false) {
                            thcPDF = "Subject to local charges";
                        } else if (thcAttachedToEmail == true) {
                            thcPDF = "See attachment";
                        } else if (thcUnit.equals("Subject to local charges")) {
                            thcPDF = "Subject to local charges";
                        }

                        //wfgPDF
                        if (wfgIncluded == true) {
                            wfgPDF = "Included";
                        } else if (!wfg.equals("")) {
                            wfgPDF = "$" + wfg + " per " + wfgUnit;
                        } else if (tradeLane.equals("Foreign to Foreign") && wfgIncluded == false) {
                            wfgPDF = "Subject to local charges";
                        } else if (wfgAttachedToEmail == true) {
                            wfgPDF = "See attachment";
                        } else if (wfgUnit.equals("Subject to local charges")) {
                            wfgPDF = "Subject to local charges";
                        }

                        if (documentationFeeIncluded != true) {
                            documentationFeePdf = documentationFee;
                        } else {
                            documentationFeePdf = "Included";
                        }

                        if (warRisk) {
                            warRiskPDF = "3$/OFT";
                        }

                        if (booked == true) {
                            quoteStatus = "Booked";
                        } else if (decline == true) {
                            quoteStatus = "Decline";
                        } else {
                            quoteStatus = "Active";
                        }

                        new QuotePDf().QuotePDF(String.valueOf(quoteID + updateAlphaNumeral), timeStamp, contactName, customerName, contactEmail, quoteStatus, username, pol, pod, tshpPorts, commodityClass, handlingInstruction, accessories, commodityDescription, displayOFT, mafiMinimum, mafiMinimumCharge, bunker, eca, thcPDF, wfgPDF, documentationFeePdf, warRisk, warRiskPDF, includeCarrierRemarks, updateIncludeSailingSchedule, carrierComments, updateEditSailingScheduleTable, updateEditPackingListTable, spotRate, contract_rate);

                    } catch (SQLException | HeadlessException | IOException ex) {
                        System.out.println("Second Try: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Error! \n" + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(null, "Quote Updated", "This quote has been added to the S: Drive", JOptionPane.INFORMATION_MESSAGE);

                    // Clear the update quote panel inputs and reset the updqte quote panel
                    ClearUpdateQuotePanel();
                }
            } catch (SQLException | HeadlessException e) {
                System.out.println("First Try " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_updateEditQuoteButtonActionPerformed

    private void editUpdateCustomerQuotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUpdateCustomerQuotesButtonActionPerformed
        // TODO add your handling code here:
        Connection conn = new DBConnection().connect();
        String company = existingCompanyCompanyTextField.getText();
        String SQL = "SELECT ID AS 'Quote ID', IF(date <=dateUpdated, dateUpdated, date) AS 'Last Updated', tradeLane AS 'Trade Lane', pol AS 'POL', pod AS 'POD', comm_class AS 'Commodity Class', handling_instructions AS 'Handling Instructions', comm_description AS 'Description', booked AS 'Booked', deny AS 'Declined', comments AS 'Comments' FROM allquotes WHERE customerName='" + company + "';";
        existingCustomerDataPreviousQuotes ecpq = new existingCustomerDataPreviousQuotes();
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery(SQL);

            // Set the table to display the results
            existingCustomerDataPreviousQuotes.customerQuotesTable.setModel(DbUtils.resultSetToTableModel(rs));

            // Set the column names
            TableColumnModel tcm = existingCustomerDataPreviousQuotes.customerQuotesTable.getColumnModel();
            tcm.getColumn(0).setHeaderValue("ID");
            tcm.getColumn(1).setHeaderValue("Last Updated");
            tcm.getColumn(2).setHeaderValue("Trade Lane");
            tcm.getColumn(3).setHeaderValue("POL");
            tcm.getColumn(4).setHeaderValue("POD");
            tcm.getColumn(5).setHeaderValue("Commodity Class");
            tcm.getColumn(6).setHeaderValue("Handling Instructions");
            tcm.getColumn(7).setHeaderValue("Description");
            tcm.getColumn(8).setHeaderValue("Booked");
            tcm.getColumn(9).setHeaderValue("Decline");
            tcm.getColumn(10).setHeaderValue("Comments");

            // Set the Title of the panel
            existingCustomerDataPreviousQuotes.title.setText(existingCompanyCompanyTextField.getText() + "'s Previous Quotes");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }

        ecpq.setVisible(true);


    }//GEN-LAST:event_editUpdateCustomerQuotesButtonActionPerformed

    private void editUpdateCustomerBookingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUpdateCustomerBookingsButtonActionPerformed
        /**
         * Displays all previous bookings by current customer
         */

        //Establish connection to database by calling DBConnection class
        Connection conn = new DBConnection().connect();

        existingCustomerDataBookings eb = new existingCustomerDataBookings();
        String companyName = existingCompanyCompanyTextField.getText();

        String sql = "SELECT ID AS 'Quote ID', bookingNumber AS 'Booking Number', IF(date <= dateUpdated, dateUpdated, date) AS 'Date Booked', tradeLane AS 'Trade Lane', CONCAT(pol,'/',pod) AS 'POL/POD', CONCAT(comm_class,' ',handling_instructions) AS 'Commodity Class', comm_description AS 'Commodity Description' FROM allquotes WHERE customerName=? AND booked=?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, companyName);
            ps.setInt(2, 1);
            ResultSet rs = ps.executeQuery();
            existingCustomerDataBookings.customerBookingTable.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumnModel tcm = existingCustomerDataBookings.customerBookingTable.getColumnModel();
            tcm.getColumn(0).setHeaderValue("Quote ID");
            tcm.getColumn(1).setHeaderValue("Booking Number");
            tcm.getColumn(2).setHeaderValue("Date Booked");
            tcm.getColumn(3).setHeaderValue("Trade Lane");
            tcm.getColumn(4).setHeaderValue("POL/POD");
            tcm.getColumn(5).setHeaderValue("Commodity Class");
            tcm.getColumn(6).setHeaderValue("Description");
            existingCustomerDataBookings.title.setText(existingCompanyCompanyTextField.getText() + "'s Previous Bookings");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        eb.setVisible(true);
    }//GEN-LAST:event_editUpdateCustomerBookingsButtonActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        newQuoteCustomerName nn = new newQuoteCustomerName();
        nn.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void cancelSpotFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelSpotFileButtonActionPerformed
        // Clears spot filing page inputs
        pQuoteIDTextField.setText("");
        validityFromDatePicker.getJFormattedTextField().setText("");
        validityToDatePicker.getJFormattedTextField().setText("");
        kkluNumberTextField.setText("");
        pPolTextField.setText("");
        pPodTextField.setText("");
        pCommodityClassComboBox.setSelectedIndex(0);
        pHandlingInstructions.setSelectedIndex(0);
        pCommodityDescriptionTextField.setText("");
        pOftTextField.setText("");
        pOftComboBox.setSelectedItem("");
        pBafTextField.setText("");
        pBafIncludedCheckBox.setSelected(false);
        bafSubjectToTariffCheckBox.setSelected(false);
        pEcaTextField.setText("");
        pEcaComboBox.setSelectedItem("");
        pEcaIncludedCheckBox.setSelected(false);
        ecaSubjectToTariffCheckBox.setSelected(false);
        pThcTextField.setText("");
        pThcComboBox.setSelectedIndex(0);
        pThcIncludedCheckBox.setSelected(false);
        thcSubjectToTariffCheckBox.setSelected(false);
        pWfgTextField.setText("");
        pWfgComboBox.setSelectedIndex(0);
        pWfgIncludedCheckBox.setSelected(false);
        wfgSubjectToTariffCheckBox.setSelected(false);
        pDocFeeComboBox.setSelectedIndex(0);
        pWarRiskCheckBox.setSelected(false);
        storageSubjectToTariffCheckBox.setSelected(false);
        pCommentsTextArea.setText("");
        pBookingNumberTextField.setText("");
        commodityNumberTextField.setText("");
        descriptionTextField.setText("");
        tliNumberTextField.setText("");
        expirationDateTextField.setText("");
        pQuoteNumberTextField.setText("");
        pIDTextField.setText("");
    }//GEN-LAST:event_cancelSpotFileButtonActionPerformed

    private void pasteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteMenuItemActionPerformed
        // TODO add your handling code here:
        JMenuItem jmi = new JMenuItem(new DefaultEditorKit.PasteAction());

    }//GEN-LAST:event_pasteMenuItemActionPerformed

    private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuItemActionPerformed
        // TODO add your handling code here:
        JMenuItem jmi = new JMenuItem(new DefaultEditorKit.CopyAction());

    }//GEN-LAST:event_copyMenuItemActionPerformed

    private void cutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutMenuItemActionPerformed
        // TODO add your handling code here:
        JMenuItem jmi = new JMenuItem(new DefaultEditorKit.CutAction());
    }//GEN-LAST:event_cutMenuItemActionPerformed

    private void newCustomerNoContractRadioButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newCustomerNoContractRadioButtonMouseClicked
        // TODO add your handling code here:
        newCustomerNoContractRadioButton.setSelected(true);
        newCustomerContractYesRadioButton.setSelected(false);
    }//GEN-LAST:event_newCustomerNoContractRadioButtonMouseClicked

    private void newCustomerContractYesRadioButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newCustomerContractYesRadioButtonMouseClicked
        // TODO add your handling code here:
        newCustomerNoContractRadioButton.setSelected(false);
        newCustomerContractYesRadioButton.setSelected(true);
    }//GEN-LAST:event_newCustomerContractYesRadioButtonMouseClicked

    private void existingCompanyContractYesRadioButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingCompanyContractYesRadioButtonMouseClicked
        // TODO add your handling code here:
        existingCompanyContractYesRadioButton.setSelected(true);
        existingCompanyNoContractRadioButton.setSelected(false);
    }//GEN-LAST:event_existingCompanyContractYesRadioButtonMouseClicked

    private void existingCompanyNoContractRadioButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingCompanyNoContractRadioButtonMouseClicked
        existingCompanyContractYesRadioButton.setSelected(false);
        existingCompanyNoContractRadioButton.setSelected(true);
    }//GEN-LAST:event_existingCompanyNoContractRadioButtonMouseClicked

    private void exportResultsToExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportResultsToExcelButtonActionPerformed
        /**
         * Exports search results to .xlsx excel document to the users desktop
         */
        // Get the query name and assign it to the exported excel document
        String queryName = queryNameTextField.getText();

        // Get current date and time of the export
        Calendar calendar = Calendar.getInstance();
        String date = new SimpleDateFormat("YYYY.MM.dd HH.mm.ss").format(calendar.getTime());

        // Sets the title of the excel document to concat the query name and current datetime
        String queryTitle = queryName + " " + date;

        // Saves the file to the user's desktop directory. 
        String file = userHomeFolder + "\\Desktop\\" + queryTitle + ".xlsx";
        try {
            //Generate the .xlsx workbook
            Workbook wb = new XSSFWorkbook();
            //Generates the new sheet
            Sheet sheet = wb.createSheet("RQS RAW DATA");
            //Header row created at sheet line 1
            Row headerRow = sheet.createRow(0);
            //First data row created at line two
            Row row = sheet.createRow(1);
            //Initialize Cell
            Cell cell;
            //Get the table model from RQS
            TableModel model = searchResultsTable.getModel();
            // Get the header values from RQS table and assign to headerRow
            for (int headings = 0; headings < model.getColumnCount(); headings++){
                headerRow.createCell(headings).setCellValue(model.getColumnName(headings));
            }
            //Get the data from RQS and fill in excel sheet starting at row 2 on excel sheet
            for (int rows = 0; rows < model.getRowCount(); rows++){//For each row in RQS
                for (int cols = 0; cols < model.getColumnCount(); cols++){//For each column in each row
                    //Assign cell value to corresponding cells in excel sheet
                    //If the cell is not blank or null
                    if(model.getValueAt(rows, cols) != null){
                        row.createCell(cols).setCellValue(model.getValueAt(rows,cols).toString());           
                    }else{
                        row.createCell(cols).setCellValue("");
                    }
                }
                row = sheet.createRow(rows + 2);
            }
            wb.write(new FileOutputStream(file));
            wb.close();
            JOptionPane.showMessageDialog(this, "The results have been saved to your desktop", "Successful Export", JOptionPane.INFORMATION_MESSAGE);
            
            
           
        } catch (IOException | HeadlessException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }//GEN-LAST:event_exportResultsToExcelButtonActionPerformed

    private void customerInformationCenterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerInformationCenterButtonActionPerformed
        newQuotePanel.setVisible(false);
        userInformationPanel.setVisible(false);
        updateEditQuotePanel.setVisible(false);
        searchPanel.setVisible(false);
        customerInformationPanel.setVisible(true);
        publishingCenterPanel.setVisible(false);

        newCustomerFirstName.setText("");
        newCustomerLastName.setText("");
        newCustomerOfficePhone.setText("");
        newCustomerMobilePhoneTextField.setText("");
        newCustomerEmailTextField.setText("");
        newCustomerTitleTextField.setText("");
        newCustomerCompanyTextField.setText("");
        newCustomerDBATextField.setText("");
        newCustomerOTINumberTextField.setText("");
        newCustomerFreightForwarderRadioButton.setSelected(false);
        newCustomerNVOCCRadioButton.setSelected(false);
        newCustomerBCORadioButton.setSelected(false);
        newCustomerOtherRadioButton.setSelected(false);
        newCustomerMainPhoneTextField.setText("");
        newCustomerSecondaryPhoneNumber.setText("");
        newCustomerFaxNumber.setText("");
        newCustomerAddress1.setText("");
        newCustomerAddress2.setText("");
        newCustomerCity.setText("");
        newCustomerCountry.setText("");
        newCustomerZipCode.setText("");
        newCustomerState.setText("");
        newCustomerContractYesRadioButton.setSelected(false);
        newCustomerNoContractRadioButton.setSelected(true);
        newCustomerContractNumberTextField.setText("");
        newCustomerContractExpirationDatePicker.getJFormattedTextField().setText("");
        newCustomerComments.setText("");
        existingCompanyNameTextField.setText("");
        existingCompanyIDTextField.setText("");
        existingCompanyFirstNameTextField.setText("");
        existingCompanyLastNameTextField.setText("");
        existingCompanyOfficePhoneTextField.setText("");
        existingCompanyMobilePhoneTextField.setText("");
        existingCompanyEmailTextField.setText("");
        existingCompanyTitleTextField.setText("");
        existingCompanyCompanyTextField.setText("");
        existingCompanyDBATextField.setText("");
        existingCompanyOTINumberTextField.setText("");
        existingCustomerFreightForwarderRadioButton.setSelected(false);
        existingCustomerNVOCCRadioButton.setSelected(false);
        existingCustomerBCORadioButton.setSelected(false);
        existingCustomerOtherRadioButton.setSelected(false);
        existingCompanyMainPhoneTextField.setText("");
        existingCompanySecondaryPhoneTextField.setText("");
        existingCompanyFaxNumberTextField.setText("");
        existingCompanyMainEmailTextField.setText("");
        existingCompanyAddress1TextField.setText("");
        existingCompanyAddress2TextField.setText("");
        existingCompanyCityTextField.setText("");
        existingCompanyStateTextField.setText("");
        existingCompanyZipTextField.setText("");
        existingCompanyCountryTextField.setText("");
        existingCompanyContractNumberTextField.setText("");
        existingCompanyCommentsTextArea.setText("");
        existingCustomerContractExpirationDatePicker.getJFormattedTextField().setText("");


    }//GEN-LAST:event_customerInformationCenterButtonActionPerformed

    private void editCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCustomerButtonActionPerformed
        // TODO add your handling code here:
        updateEditQuoteCustomerName ueqcn = new updateEditQuoteCustomerName();
        ueqcn.setVisible(true);
    }//GEN-LAST:event_editCustomerButtonActionPerformed

    private void clearResultTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearResultTableActionPerformed
        //Clears the result table
        DefaultTableModel model = (DefaultTableModel) searchResultsTable.getModel();
        int cols = searchResultsTable.getColumnCount();
        int rows = searchResultsTable.getRowCount();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                model.setValueAt("", r, c);
            }
        }
        queryNameTextField.setText("");
        dateTextField.setText("");
    }//GEN-LAST:event_clearResultTableActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        newQuoteCustomerNameLabel.setText("N/A");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void pDocFeeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pDocFeeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pDocFeeComboBoxActionPerformed

    private void newQuoteAddRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newQuoteAddRowActionPerformed
        DefaultTableModel model = (DefaultTableModel) packingListTable.getModel();
        model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", ""});


    }//GEN-LAST:event_newQuoteAddRowActionPerformed

    private void saveChangesPublishingPDFButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveChangesPublishingPDFButtonActionPerformed
        // Save spot rate changes
        Connection conn = new DBConnection().connect();
        String validityFrom = validityFromDatePicker.getJFormattedTextField().getText();
        String validityTo = validityToDatePicker.getJFormattedTextField().getText();
        String kkluNumber = kkluNumberTextField.getText();
        String pol = pPolTextField.getText();
        String pod = pPodTextField.getText();
        String commClass = pCommodityClassComboBox.getSelectedItem().toString();
        String handlingInstructions = pHandlingInstructions.getSelectedItem().toString();
        String commDesc = pCommodityDescriptionTextField.getText();
        String oft = pOftTextField.getText();
        String oftUnit = pOftComboBox.getSelectedItem().toString();
        String baf = null;
        String bafText = pBafTextField.getText();
        Boolean bafIncluded = pBafIncludedCheckBox.isSelected();
        String eca = null;
        String ecaText = pEcaTextField.getText();
        String ecaUnit = pEcaComboBox.getSelectedItem().toString();
        Boolean ecaIncluded = pEcaIncludedCheckBox.isSelected();
        String thc = null;
        String thcText = "$" + pThcTextField.getText();
        String thcUnit = pThcComboBox.getSelectedItem().toString();
        Boolean thcIncluded = pThcIncludedCheckBox.isSelected();
        Boolean thcSubjectToTariff = thcSubjectToTariffCheckBox.isSelected();
        String wfg = null;
        String wfgText = pWfgTextField.getText();
        String wfgUnit = pWfgComboBox.getSelectedItem().toString();
        Boolean wfgIncluded = pWfgIncludedCheckBox.isSelected();
        Boolean wfgSubjectToTariff = wfgSubjectToTariffCheckBox.isSelected();
        String docFee = pDocFeeComboBox.getSelectedItem().toString();
        Boolean docFeeIncluded = pDocFeeIncludedCheckBox.isSelected();
        String comments = pCommentsTextArea.getText();
        Boolean warRisk = pWarRiskCheckBox.isSelected();
        String quoteID = pQuoteNumberTextField.getText();
        String bookingNumber = pBookingNumberTextField.getText();
        String storage = null;
        String storageText = storageTextField.getText();
        String storageUnit = storageUnitComboBox.getSelectedItem().toString();
        Boolean storageIncluded = storageIncludedCheckBox.isSelected();
        Boolean storageSubjectToTariff = storageSubjectToTariffCheckBox.isSelected();
        Boolean bafSubjectToTariff = bafSubjectToTariffCheckBox.isSelected();
        Boolean ecaSubjectToTariff = ecaSubjectToTariffCheckBox.isSelected();
        String commodityNumber = commodityNumberTextField.getText();
        String description = descriptionTextField.getText();
        String tliNumber = tliNumberTextField.getText();
        String expiration = expirationDateTextField.getText();
        String ID = pQuoteIDTextField.getText();

        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());

        if (bafIncluded != true && bafSubjectToTariff != true) {
            baf = bafText;
        } else if (bafIncluded == true) {
            baf = "Included";
        } else if (bafSubjectToTariff == true) {
            baf = "Subject to Tariff";
        }

        if (ecaIncluded != true && ecaSubjectToTariff != true) {
            eca = ecaText;
        } else if (ecaIncluded == true) {
            eca = "Included";
        } else if (ecaSubjectToTariff == true) {
            eca = "Subject to Tariff";
        } else if (ecaSubjectToTariff == true) {
            eca = "Subject to Tariff";
        }

        if (thcSubjectToTariff == true) {
            thc = "Subject to Tariff";
        } else if (thcSubjectToTariff != true && thcIncluded != true) {
            thc = thcText;
        } else if (thcSubjectToTariff != true && thcIncluded == true) {
            thc = "Included";
        } else if (thcIncluded == true && thcSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        if (wfgSubjectToTariff == true) {
            wfg = "Subject to Tariff";
            wfgUnit = "N/A";
        } else if (wfgSubjectToTariff != true && wfgIncluded != true) {
            wfg = wfgText;
        } else if (wfgSubjectToTariff != true && wfgIncluded == true) {
            wfg = "Included";
        } else if (wfgIncluded == true && wfgSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        if (storageSubjectToTariff == true) {
            storage = "Subject to Tariff";
            storageUnit = "N/A";
        } else if (storageSubjectToTariff != true && storageIncluded != true) {
            storage = storageText;
        } else if (storageIncluded == true && storageSubjectToTariff != true) {
            storage = "Included";
        } else if (storageIncluded == true && storageSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        String sql = "UPDATE spotrates SET validityFrom=?, validityTo=?, tariffNumber=?, pol=?, pod=?, bookingNumber=?, commClass=?, handlingInstructions=?, commDesc=?, oft=?, oftUnit=?, baf=?,bafIncluded=?, bafPerTariff=?, ecaBaf=?, ecaBafUnit=?, ecaIncluded=?, ecaPerTariff=?, thc=?, thcUnit=?, thcIncluded=?, thcPerTariff=?, wfg=?, wfgUnit=?, wfgIncluded=?, wfgPerTariff=?, storage=?, storageUnit=?, storageIncluded=?, storagePerTariff=?, docFee=?, docFeeIncluded=?, comments=?, quoteID=?, warRisk=?, commodityNumber=?, description=?, tliNumber=?, expiration=? WHERE ID=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, validityFrom);
            ps.setString(2, validityTo);
            ps.setString(3, kkluNumber);
            ps.setString(4, pol);
            ps.setString(5, pod);
            ps.setString(6, bookingNumber);
            ps.setString(7, commClass);
            ps.setString(8, handlingInstructions);
            ps.setString(9, commDesc);
            ps.setString(10, oft);
            ps.setString(11, oftUnit);
            ps.setString(12, baf);
            ps.setBoolean(13, bafIncluded);
            ps.setBoolean(14, bafSubjectToTariff);
            ps.setString(15, eca);
            ps.setString(16, ecaUnit);
            ps.setBoolean(17, ecaIncluded);
            ps.setBoolean(18, ecaSubjectToTariff);
            ps.setString(19, thc);
            ps.setString(20, thcUnit);
            ps.setBoolean(21, thcIncluded);
            ps.setBoolean(22, thcSubjectToTariff);
            ps.setString(23, wfg);
            ps.setString(24, wfgUnit);
            ps.setBoolean(25, wfgIncluded);
            ps.setBoolean(26, wfgSubjectToTariff);
            ps.setString(27, storage);
            ps.setString(28, storageUnit);
            ps.setBoolean(29, storageIncluded);
            ps.setBoolean(30, storageSubjectToTariff);
            ps.setString(31, docFee);
            ps.setBoolean(32, docFeeIncluded);
            ps.setString(33, comments);
            ps.setString(34, quoteID);
            ps.setBoolean(35, warRisk);
            ps.setString(36, commodityNumber);
            ps.setString(37, description);
            ps.setString(38, tliNumber);
            ps.setString(39, expiration);
            ps.setString(40, ID);

            ps.executeUpdate();

            String addBookingNumber = "UPDATE allquotes SET bookingNumber='" + bookingNumber + "', publishingID='" + ID + "' WHERE ID='" + quoteID + "';";
            PreparedStatement psAddBookingNumber = conn.prepareStatement(addBookingNumber);
            psAddBookingNumber.executeUpdate(addBookingNumber);

            Double oft1 = (Double) (Double.parseDouble(oft));
            String eca1 = "";
            switch (eca) {
                case "Included":
                    eca1 = "Included";
                    break;
                case "Subject to Tariff":
                    eca1 = "Subject to Tariff";
                    break;
                default:
                    eca1 = "$" + eca + " " + ecaUnit;
                    break;
            }
            String baf1;
            switch (baf) {
                case "Included":
                    baf1 = "Included";
                    break;
                case "Subject To Tariff":
                    baf1 = "Subject to Tariff";
                    break;
                default:
                    baf1 = baf + "%";
                    break;
            }

            String thc1;

            switch (thc) {
                case "Included":
                    thc1 = thc;
                    break;
                case "Subject to Tariff":
                    thc1 = "Subject to Tariff";
                    break;
                default:
                    thc1 = "$" + thc + " per " + thcUnit;
                    break;
            }

            switch (thcUnit) {
                case "FAS":
                    thc1 = thcUnit;
                    break;
                case "Subject to local charges":
                    thc1 = thcUnit;
                default:
                    break;
            }

            String wfg1;
            switch (wfg) {
                case "Included":
                    wfg1 = wfg;
                    break;
                case "Subject to Tariff":
                    wfg1 = "Subject to Tariff";
                    break;
                default:
                    wfg1 = "$" + wfg + " per " + wfgUnit;
                    break;
            }

            switch (wfgUnit) {
                case "FAS":
                    wfg1 = wfgUnit;
                    break;
                case "Subject to local charges":
                    wfg1 = wfgUnit;
                default:
                    break;
            }

            /*
             ***************************************
             ***************************************
             ***************************************
             */
            String filename = userHomeFolder + "\\Desktop\\Publishing\\" + pol + " To " + pod + "; " + commDesc + " PID" + ID + ".xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("KKLU" + kkluNumber);

            sheet.setColumnWidth(0, 650);
            sheet.setColumnWidth(1, 5742);
            sheet.setColumnWidth(2, 5920);
            sheet.setColumnWidth(3, 3668);
            sheet.setColumnWidth(4, 5711);

            //Bold Font
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            CellStyle style = workbook.createCellStyle();
            style.setFont(font);

            //Currency cell type
            CellStyle currency = workbook.createCellStyle();
            currency.setDataFormat((short) 7);

            //Percentage cell type
            CellStyle percentage = workbook.createCellStyle();
            percentage.setDataFormat((short) 0xa);

            sheet.setDisplayGridlines(false);

            //Black medium sized border around cell
            CellStyle blackBorder = workbook.createCellStyle();
            blackBorder.setBorderBottom(CellStyle.BORDER_MEDIUM);
            blackBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderLeft(CellStyle.BORDER_MEDIUM);
            blackBorder.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderRight(CellStyle.BORDER_MEDIUM);
            blackBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderTop(CellStyle.BORDER_MEDIUM);
            blackBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());

            //Red font
            CellStyle redFontStyle = workbook.createCellStyle();
            HSSFFont redFont = workbook.createFont();
            redFont.setColor(HSSFColor.RED.index);
            redFontStyle.setFont(redFont);

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(3).setCellValue(validityFrom);

            HSSFRow row1 = sheet.createRow((short) 1);
            Cell cell = row1.createCell(1);
            cell.setCellValue("FILING REQUEST TO RICLFILE");
            cell.setCellStyle(style);

            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));

            sheet.setPrintGridlines(false);

            HSSFRow row2 = sheet.createRow((short) 2);
            row2.createCell(0).setCellValue("A)");
            row2.createCell(1).setCellValue("Tariff Number(KKLU):");
            row2.createCell(2).setCellValue(kkluNumber);

            HSSFRow space0 = sheet.createRow((short) 3);

            HSSFRow row4 = sheet.createRow((short) 4);
            row4.createCell(0).setCellValue("B)");
            row4.createCell(1).setCellValue("Commodity:");
            row4.createCell(2).setCellValue(commDesc);

            HSSFRow space2 = sheet.createRow((short) 5);

            HSSFRow row5 = sheet.createRow((short) 6);
            row5.createCell(0).setCellValue("C)");
            row5.createCell(1).setCellValue("POL:");
            row5.createCell(2).setCellValue(pol);

            HSSFRow space3 = sheet.createRow((short) 7);

            HSSFRow row6 = sheet.createRow((short) 8);
            row6.createCell(0).setCellValue("D)");
            row6.createCell(1).setCellValue("POD:");
            row6.createCell(2).setCellValue(pod);

            HSSFRow space4 = sheet.createRow((short) 9);

            HSSFRow row7 = sheet.createRow((short) 10);
            row7.createCell(0).setCellValue("E)");
            row7.createCell(1).setCellValue("Rate:");
            Cell rate = row7.createCell(2);
            rate.setCellValue(oft1);
            rate.setCellStyle(currency);

            HSSFRow space5 = sheet.createRow((short) 11);

            HSSFRow row8 = sheet.createRow((short) 12);
            row8.createCell(0).setCellValue("F)");
            row8.createCell(1).setCellValue("Rate Basis:");
            row8.createCell(2).setCellValue(oftUnit);

            HSSFRow space6 = sheet.createRow((short) 13);

            HSSFRow row9 = sheet.createRow((short) 14);
            row9.createCell(0).setCellValue("G)");
            row9.createCell(1).setCellValue("BAF:");
            Cell Baf = row9.createCell(2);
            switch (baf1) {
                case "Included":
                    Baf.setCellValue("Included");
                    break;
                case "Subject to Tariff":
                    Baf.setCellValue("Subject to Tariff");
                    break;
                default:
                    Baf.setCellValue(baf1);
                    break;
            }

            HSSFRow space7 = sheet.createRow((short) 15);

            HSSFRow row10 = sheet.createRow((short) 16);
            row10.createCell(0).setCellValue("H)");
            row10.createCell(1).setCellValue("ECA BAF:");
            Cell ecaBaf = row10.createCell(2);
            switch (eca) {
                case "Included":
                    ecaBaf.setCellValue("Included");
                    break;
                case "Subject to Tariff":
                    ecaBaf.setCellValue("Subject to Tariff");
                    break;
                default:
                    ecaBaf.setCellValue("$" + eca1 + " per " + ecaUnit);
                    ecaBaf.setCellStyle(currency);
                    break;
            }

            HSSFRow space8 = sheet.createRow((short) 17);

            HSSFRow row11 = sheet.createRow((short) 18);
            row11.createCell(0).setCellValue("I)");
            row11.createCell(1).setCellValue("THC/WFG:");
            row11.createCell(2).setCellValue(thc1 + " / " + wfg1);

            HSSFRow space = sheet.createRow((short) 19);

            HSSFRow row12 = sheet.createRow((short) 20);
            row12.createCell(0).setCellValue("J)");
            row12.createCell(1).setCellValue("Storage:");
            row12.createCell(2).setCellValue(storage);

            HSSFRow space10 = sheet.createRow((short) 21);

            HSSFRow row13 = sheet.createRow((short) 22);
            row13.createCell(0).setCellValue("K)");
            row13.createCell(1).setCellValue("Doc Fee:");
            row13.createCell(2).setCellValue(docFee);

            HSSFRow space11 = sheet.createRow((short) 23);

            HSSFRow row14 = sheet.createRow((short) 24);
            row14.createCell(0).setCellValue("L)");
            row14.createCell(1).setCellValue("War Risk:");

            HSSFRow space12 = sheet.createRow((short) 25);

            if (warRisk == true) {
                String warRiskPercentage = "3%";
                row14.createCell(2).setCellValue(warRiskPercentage);
            } else if (warRisk != true) {
                String warRiskPercentage = "N/A";
                row14.createCell(2).setCellValue(warRiskPercentage);
            }

            HSSFRow row15 = sheet.createRow((short) 26);
            row15.createCell(0).setCellValue("M)");
            row15.createCell(1).setCellValue("Validity");
            row15.createCell(2).setCellValue("Effective: " + validityFrom);

            HSSFRow row16 = sheet.createRow((short) 27);
            row16.createCell(2).setCellValue("Expiration: " + validityTo);

            HSSFRow space13 = sheet.createRow((short) 28);

            HSSFRow row17 = sheet.createRow((short) 29);
            row17.createCell(0).setCellValue("N)");
            row17.createCell(1).setCellValue("Remarks");
            row17.createCell(2).setCellValue(comments);

            HSSFRow space14 = sheet.createRow((short) 30);

            HSSFRow row18 = sheet.createRow((short) 31);
            row18.createCell(0).setCellValue("O)");
            row18.createCell(1).setCellValue("Booking #:");
            row18.createCell(2).setCellValue(bookingNumber);

            HSSFRow space15 = sheet.createRow((short) 32);

            HSSFRow row19 = sheet.createRow((short) 33);
            row19.createCell(0).setCellValue("P)");
            row19.createCell(1).setCellValue("RQS #:");
            row19.createCell(2).setCellValue(quoteID);

            HSSFRow space16 = sheet.createRow((short) 34);

            HSSFRow row20 = sheet.createRow((short) 35);
            row20.createCell(0).setCellValue("Q)");
            row20.createCell(1).setCellValue("PID #:");
            row20.createCell(2).setCellValue(ID);

            HSSFRow space17 = sheet.createRow((short) 36);

            HSSFRow space18 = sheet.createRow((short) 37);

            HSSFRow row21 = sheet.createRow((short) 38);
            row21.createCell(0).setCellValue("");
            row21.createCell(1).setCellValue("For RICLFILE Use Only");

            HSSFRow space19 = sheet.createRow((short) 39);

            HSSFRow row22 = sheet.createRow((short) 40);
            Cell comm = row22.createCell(1);
            comm.setCellValue("Commodity #:");
            comm.setCellStyle(redFontStyle);
            row22.createCell(2).setCellValue(commodityNumber);
            Cell desc = row22.createCell(3);
            desc.setCellValue("Description:");
            desc.setCellStyle(redFontStyle);
            row22.createCell(4).setCellValue(description);

            HSSFRow space20 = sheet.createRow((short) 41);

            HSSFRow row24 = sheet.createRow((short) 42);
            Cell TLI = row24.createCell(1);
            TLI.setCellValue("TLI #:");
            TLI.setCellStyle(redFontStyle);
            row24.createCell(2).setCellValue(tliNumber);

            HSSFRow space21 = sheet.createRow((short) 43);

            HSSFRow row26 = sheet.createRow((short) 44);
            Cell exp = row26.createCell(1);
            exp.setCellValue("Expiration: ");
            exp.setCellStyle(redFontStyle);
            row26.createCell(2).setCellValue(expiration);

            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                workbook.write(fileOut);
            }
            System.out.print("Your excel file has been generate");

            String spotRateId = String.valueOf(ID);

            JOptionPane.showMessageDialog(null, "PID" + ID + " has been successfully update.");
            pQuoteIDTextField.setText("");
            validityFromDatePicker.getJFormattedTextField().setText("");
            validityToDatePicker.getJFormattedTextField().setText("");
            kkluNumberTextField.setText("");
            pPolTextField.setText("");
            pPodTextField.setText("");
            pCommodityClassComboBox.setSelectedIndex(0);
            pHandlingInstructions.setSelectedIndex(0);
            pCommodityDescriptionTextField.setText("");
            pOftTextField.setText("");
            pOftComboBox.setSelectedItem("");
            bafSubjectToTariffCheckBox.setSelected(false);
            pBafTextField.setText("");
            pBafIncludedCheckBox.setSelected(false);
            pEcaTextField.setText("");
            pEcaComboBox.setSelectedItem("");
            pEcaIncludedCheckBox.setSelected(false);
            ecaSubjectToTariffCheckBox.setSelected(false);
            pThcTextField.setText("");
            pThcComboBox.setSelectedIndex(0);
            pThcIncludedCheckBox.setSelected(false);
            thcSubjectToTariffCheckBox.setSelected(false);
            pWfgTextField.setText("");
            pWfgComboBox.setSelectedIndex(0);
            pWfgIncludedCheckBox.setSelected(false);
            wfgSubjectToTariffCheckBox.setSelected(false);
            pDocFeeComboBox.setSelectedIndex(0);
            pWarRiskCheckBox.setSelected(false);
            storageSubjectToTariffCheckBox.setSelected(false);
            pCommentsTextArea.setText("");
            pBookingNumberTextField.setText("");
            storageTextField.setText("");
            storageUnitComboBox.setSelectedIndex(0);
            storageIncludedCheckBox.setSelected(false);
            commodityNumberTextField.setText("");
            descriptionTextField.setText("");
            tliNumberTextField.setText("");
            expirationDateTextField.setText("");
            pQuoteNumberTextField.setText("");
            pIDTextField.setText("");

        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_saveChangesPublishingPDFButtonActionPerformed

    private void newFilingCompleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFilingCompleteButtonActionPerformed
        /**
         * Allows publishing to add TLI, etc. to spot filing Sends confirmation
         * email to sales upon accept
         */

        // Connect to databse
        Connection conn = new DBConnection().connect();

        // Get value from inputs and assign to strings. 
        String validityFrom = validityFromDatePicker.getJFormattedTextField().getText();
        String validityTo = validityToDatePicker.getJFormattedTextField().getText();
        String kkluNumber = kkluNumberTextField.getText();
        String pol = pPolTextField.getText();
        String pod = pPodTextField.getText();
        String commClass = pCommodityClassComboBox.getSelectedItem().toString();
        String handlingInstructions = pHandlingInstructions.getSelectedItem().toString();
        String commDesc = pCommodityDescriptionTextField.getText();
        String oft = pOftTextField.getText();
        String oftUnit = pOftComboBox.getSelectedItem().toString();
        String baf = null;
        String bafText = pBafTextField.getText();
        Boolean bafIncluded = pBafIncludedCheckBox.isSelected();
        String eca = null;
        String ecaText = pEcaTextField.getText();
        String ecaUnit = pEcaComboBox.getSelectedItem().toString();
        Boolean ecaIncluded = pEcaIncludedCheckBox.isSelected();
        String thc = null;
        String thcText = "$" + pThcTextField.getText();
        String thcUnit = pThcComboBox.getSelectedItem().toString();
        Boolean thcIncluded = pThcIncludedCheckBox.isSelected();
        Boolean thcSubjectToTariff = thcSubjectToTariffCheckBox.isSelected();
        String wfg = null;
        String wfgText = pWfgTextField.getText();
        String wfgUnit = pWfgComboBox.getSelectedItem().toString();
        Boolean wfgIncluded = pWfgIncludedCheckBox.isSelected();
        Boolean wfgSubjectToTariff = wfgSubjectToTariffCheckBox.isSelected();
        String docFee = pDocFeeComboBox.getSelectedItem().toString();
        Boolean docFeeIncluded = pDocFeeIncludedCheckBox.isSelected();
        String comments = pCommentsTextArea.getText();
        Boolean warRisk = pWarRiskCheckBox.isSelected();
        String quoteID = pQuoteNumberTextField.getText();
        String bookingNumber = pBookingNumberTextField.getText();
        String storage = null;
        String storageText = storageTextField.getText();
        String storageUnit = storageUnitComboBox.getSelectedItem().toString();
        Boolean storageIncluded = storageIncludedCheckBox.isSelected();
        Boolean storageSubjectToTariff = storageSubjectToTariffCheckBox.isSelected();
        Boolean bafSubjectToTariff = bafSubjectToTariffCheckBox.isSelected();
        Boolean ecaSubjectToTariff = ecaSubjectToTariffCheckBox.isSelected();
        String commodityNumber = commodityNumberTextField.getText();
        String description = descriptionTextField.getText();
        String tliNumber = tliNumberTextField.getText();
        String expirationDate = expirationDateTextField.getText();
        String PID = pIDTextField.getText();

        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());

        if (bafIncluded != true && ecaSubjectToTariff != true) {
            baf = bafText;
        } else if (bafIncluded == true) {
            baf = "Included";
        } else if (bafSubjectToTariff == true) {
            baf = "Subject to Tariff";
        }

        if (ecaIncluded != true && ecaSubjectToTariff != true) {
            eca = ecaText;
        } else if (ecaIncluded == true) {
            eca = "Included";
        } else if (ecaSubjectToTariff == true) {
            eca = "Subject to Tariff";
        } else if (ecaSubjectToTariff == true) {
            eca = "Subject to Tariff";
        }

        if (thcSubjectToTariff == true) {
            thc = "Subject to Tariff";
        } else if (thcSubjectToTariff != true && thcIncluded != true) {
            thc = thcText;
        } else if (thcSubjectToTariff != true && thcIncluded == true) {
            thc = "Included";
        } else if (thcIncluded == true && thcSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        if (wfgSubjectToTariff == true) {
            wfg = "Subject to Tariff";
            wfgUnit = "N/A";
        } else if (wfgSubjectToTariff != true && wfgIncluded != true) {
            wfg = wfgText;
        } else if (wfgSubjectToTariff != true && wfgIncluded == true) {
            wfg = "Included";
        } else if (wfgIncluded == true && wfgSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        if (storageSubjectToTariff == true) {
            storage = "Subject to Tariff";
            storageUnit = "N/A";
        } else if (storageSubjectToTariff != true && storageIncluded != true) {
            storage = storageText;
        } else if (storageIncluded == true && storageSubjectToTariff != true) {
            storage = "Included";
        } else if (storageIncluded == true && storageSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        String sql = "UPDATE spotrates SET validityFrom=?, validityTo=?, tariffNumber=?, pol=?, pod=?, bookingNumber=?, commClass=?, handlingInstructions=?, commDesc=?, oft=?, oftUnit=?, baf=?,bafIncluded=?, bafPerTariff=?, ecaBaf=?, ecaBafUnit=?, ecaIncluded=?, ecaPerTariff=?, thc=?, thcUnit=?, thcIncluded=?, thcPerTariff=?, wfg=?, wfgUnit=?, wfgIncluded=?, wfgPerTariff=?, storage=?, storageUnit=?, storageIncluded=?, storagePerTariff=?, docFee=?, docFeeIncluded=?, comments=?, quoteID=?, warRisk=?, commodityNumber=?, description=?, tliNumber=?, expiration=? WHERE ID=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, validityFrom);
            ps.setString(2, validityTo);
            ps.setString(3, kkluNumber);
            ps.setString(4, pol);
            ps.setString(5, pod);
            ps.setString(6, bookingNumber);
            ps.setString(7, commClass);
            ps.setString(8, handlingInstructions);
            ps.setString(9, commDesc);
            ps.setString(10, oft);
            ps.setString(11, oftUnit);
            ps.setString(12, baf);
            ps.setBoolean(13, bafIncluded);
            ps.setBoolean(14, bafSubjectToTariff);
            ps.setString(15, eca);
            ps.setString(16, ecaUnit);
            ps.setBoolean(17, ecaIncluded);
            ps.setBoolean(18, ecaSubjectToTariff);
            ps.setString(19, thc);
            ps.setString(20, thcUnit);
            ps.setBoolean(21, thcIncluded);
            ps.setBoolean(22, thcSubjectToTariff);
            ps.setString(23, wfg);
            ps.setString(24, wfgUnit);
            ps.setBoolean(25, wfgIncluded);
            ps.setBoolean(26, wfgSubjectToTariff);
            ps.setString(27, storage);
            ps.setString(28, storageUnit);
            ps.setBoolean(29, storageIncluded);
            ps.setBoolean(30, storageSubjectToTariff);
            ps.setString(31, docFee);
            ps.setBoolean(32, docFeeIncluded);
            ps.setString(33, comments);
            ps.setString(34, quoteID);
            ps.setBoolean(35, warRisk);
            ps.setString(36, commodityNumber);
            ps.setString(37, description);
            ps.setString(38, tliNumber);
            ps.setString(39, expirationDate);
            ps.setString(40, PID);

            ps.executeUpdate();

            //Return the auto-generated key
            ResultSet keys = ps.getGeneratedKeys();
            int lastKey = 1;
            while (keys.next()) {
                lastKey = keys.getInt(1);
            }

            String addBookingNumber = "UPDATE allquotes SET bookingNumber='" + bookingNumber + "', publishingID='" + lastKey + "', commodityNumber='" + commodityNumber + "', description = '" + description + "', tliNumber='" + tliNumber + "', expiration='" + expirationDate + "' WHERE ID='" + quoteID + "';";
            PreparedStatement psAddBookingNumber = conn.prepareStatement(addBookingNumber);
            psAddBookingNumber.executeUpdate(addBookingNumber);

            Double oft1 = (Double) (Double.parseDouble(oft));
            String eca1 = "";
            switch (eca) {
                case "Included":
                    eca1 = "Included";
                    break;
                case "Subject to Tariff":
                    eca1 = "Subject to Tariff";
                    break;
                default:
                    eca1 = "$" + eca + " " + ecaUnit;
                    break;
            }
            String baf1;
            switch (baf) {
                case "Included":
                    baf1 = "Included";
                    break;
                case "Subject To Tariff":
                    baf1 = "Subject to Tariff";
                    break;
                default:
                    baf1 = baf + "%";
                    break;
            }

            String thc1;

            switch (thc) {
                case "Included":
                    thc1 = thc;
                    break;
                case "Subject to Tariff":
                    thc1 = "Subject to Tariff";
                    break;
                default:
                    thc1 = "$" + thc + " per " + thcUnit;
                    break;
            }

            switch (thcUnit) {
                case "FAS":
                    thc1 = thcUnit;
                    break;
                case "Subject to local charges":
                    thc1 = thcUnit;
                default:
                    break;
            }

            String wfg1;
            switch (wfg) {
                case "Included":
                    wfg1 = wfg;
                    break;
                case "Subject to Tariff":
                    wfg1 = "Subject to Tariff";
                    break;
                default:
                    wfg1 = "$" + wfg + " per " + wfgUnit;
                    break;
            }

            switch (wfgUnit) {
                case "FAS":
                    wfg1 = wfgUnit;
                    break;
                case "Subject to local charges":
                    wfg1 = wfgUnit;
                default:
                    break;
            }

            /*
             ***************************************
             ***************************************
             ***************************************
             */
            String filename = userHomeFolder + "\\Desktop\\Publishing\\" + pol + " To " + pod + "; " + commDesc + " PID" + lastKey + ".xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("KKLU" + kkluNumber);

            sheet.setColumnWidth(0, 650);
            sheet.setColumnWidth(1, 5742);
            sheet.setColumnWidth(2, 5920);
            sheet.setColumnWidth(3, 3668);
            sheet.setColumnWidth(4, 5711);

            //Bold Font
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            CellStyle style = workbook.createCellStyle();
            style.setFont(font);

            //Currency cell type
            CellStyle currency = workbook.createCellStyle();
            currency.setDataFormat((short) 7);

            //Percentage cell type
            CellStyle percentage = workbook.createCellStyle();
            percentage.setDataFormat((short) 0xa);

            sheet.setDisplayGridlines(false);

            //Black medium sized border around cell
            CellStyle blackBorder = workbook.createCellStyle();
            blackBorder.setBorderBottom(CellStyle.BORDER_MEDIUM);
            blackBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderLeft(CellStyle.BORDER_MEDIUM);
            blackBorder.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderRight(CellStyle.BORDER_MEDIUM);
            blackBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderTop(CellStyle.BORDER_MEDIUM);
            blackBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());

            //Red font
            CellStyle redFontStyle = workbook.createCellStyle();
            HSSFFont redFont = workbook.createFont();
            redFont.setColor(HSSFColor.RED.index);
            redFontStyle.setFont(redFont);

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(3).setCellValue(validityFrom);

            HSSFRow row1 = sheet.createRow((short) 1);
            Cell cell = row1.createCell(1);
            cell.setCellValue("FILING REQUEST TO RICLFILE");
            cell.setCellStyle(style);

            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));

            sheet.setPrintGridlines(false);

            HSSFRow row2 = sheet.createRow((short) 2);
            row2.createCell(0).setCellValue("A)");
            row2.createCell(1).setCellValue("Tariff Number(KKLU):");
            row2.createCell(2).setCellValue(kkluNumber);

            HSSFRow space0 = sheet.createRow((short) 3);

            HSSFRow row4 = sheet.createRow((short) 4);
            row4.createCell(0).setCellValue("B)");
            row4.createCell(1).setCellValue("Commodity:");
            row4.createCell(2).setCellValue(commDesc);

            HSSFRow space2 = sheet.createRow((short) 5);

            HSSFRow row5 = sheet.createRow((short) 6);
            row5.createCell(0).setCellValue("C)");
            row5.createCell(1).setCellValue("POL:");
            row5.createCell(2).setCellValue(pol);

            HSSFRow space3 = sheet.createRow((short) 7);

            HSSFRow row6 = sheet.createRow((short) 8);
            row6.createCell(0).setCellValue("D)");
            row6.createCell(1).setCellValue("POD:");
            row6.createCell(2).setCellValue(pod);

            HSSFRow space4 = sheet.createRow((short) 9);

            HSSFRow row7 = sheet.createRow((short) 10);
            row7.createCell(0).setCellValue("E)");
            row7.createCell(1).setCellValue("Rate:");
            Cell rate = row7.createCell(2);
            rate.setCellValue(oft1);
            rate.setCellStyle(currency);

            HSSFRow space5 = sheet.createRow((short) 11);

            HSSFRow row8 = sheet.createRow((short) 12);
            row8.createCell(0).setCellValue("F)");
            row8.createCell(1).setCellValue("Rate Basis:");
            row8.createCell(2).setCellValue(oftUnit);

            HSSFRow space6 = sheet.createRow((short) 13);

            HSSFRow row9 = sheet.createRow((short) 14);
            row9.createCell(0).setCellValue("G)");
            row9.createCell(1).setCellValue("BAF:");
            Cell Baf = row9.createCell(2);
            switch (baf1) {
                case "Included":
                    Baf.setCellValue("Included");
                    break;
                case "Subject to Tariff":
                    Baf.setCellValue("Subject to Tariff");
                    break;
                default:
                    Baf.setCellValue(baf1);
                    Baf.setCellStyle(percentage);
                    break;
            }

            HSSFRow space7 = sheet.createRow((short) 15);

            HSSFRow row10 = sheet.createRow((short) 16);
            row10.createCell(0).setCellValue("H)");
            row10.createCell(1).setCellValue("ECA BAF:");
            Cell ecaBaf = row10.createCell(2);
            switch (eca) {
                case "Included":
                    ecaBaf.setCellValue("Included");
                    break;
                case "Subject to Tariff":
                    ecaBaf.setCellValue("Subject to Tariff");
                    break;
                default:
                    ecaBaf.setCellValue("$" + eca1 + " per " + ecaUnit);
                    ecaBaf.setCellStyle(currency);
                    break;
            }

            HSSFRow space8 = sheet.createRow((short) 17);

            HSSFRow row11 = sheet.createRow((short) 18);
            row11.createCell(0).setCellValue("I)");
            row11.createCell(1).setCellValue("THC/WFG:");
            row11.createCell(2).setCellValue(thc1 + " / " + wfg1);

            HSSFRow space = sheet.createRow((short) 19);

            HSSFRow row12 = sheet.createRow((short) 20);
            row12.createCell(0).setCellValue("J)");
            row12.createCell(1).setCellValue("Storage:");
            row12.createCell(2).setCellValue(storage);

            HSSFRow space10 = sheet.createRow((short) 21);

            HSSFRow row13 = sheet.createRow((short) 22);
            row13.createCell(0).setCellValue("K)");
            row13.createCell(1).setCellValue("Doc Fee:");
            row13.createCell(2).setCellValue(docFee);

            HSSFRow space11 = sheet.createRow((short) 23);

            HSSFRow row14 = sheet.createRow((short) 24);
            row14.createCell(0).setCellValue("L)");
            row14.createCell(1).setCellValue("War Risk:");

            HSSFRow space12 = sheet.createRow((short) 25);

            if (warRisk == true) {
                String warRiskPercentage = "3%";
                row14.createCell(2).setCellValue(warRiskPercentage);
            } else if (warRisk != true) {
                String warRiskPercentage = "N/A";
                row14.createCell(2).setCellValue(warRiskPercentage);
            }

            HSSFRow row15 = sheet.createRow((short) 26);
            row15.createCell(0).setCellValue("M)");
            row15.createCell(1).setCellValue("Validity");
            row15.createCell(2).setCellValue("Effective: " + validityFrom);

            HSSFRow row16 = sheet.createRow((short) 27);
            row16.createCell(2).setCellValue("Expiration: " + validityTo);

            HSSFRow space13 = sheet.createRow((short) 28);

            HSSFRow row17 = sheet.createRow((short) 29);
            row17.createCell(0).setCellValue("N)");
            row17.createCell(1).setCellValue("Remarks");
            row17.createCell(2).setCellValue(comments);

            HSSFRow space14 = sheet.createRow((short) 30);

            HSSFRow row18 = sheet.createRow((short) 31);
            row18.createCell(0).setCellValue("O)");
            row18.createCell(1).setCellValue("Booking #:");
            row18.createCell(2).setCellValue(bookingNumber);

            HSSFRow space15 = sheet.createRow((short) 32);

            HSSFRow row19 = sheet.createRow((short) 33);
            row19.createCell(0).setCellValue("P)");
            row19.createCell(1).setCellValue("RQS #:");
            row19.createCell(2).setCellValue(quoteID);

            HSSFRow space16 = sheet.createRow((short) 34);

            HSSFRow row20 = sheet.createRow((short) 35);
            row20.createCell(0).setCellValue("Q)");
            row20.createCell(1).setCellValue("PID #:");
            row20.createCell(2).setCellValue(lastKey);

            HSSFRow space17 = sheet.createRow((short) 36);

            HSSFRow space18 = sheet.createRow((short) 37);

            HSSFRow row21 = sheet.createRow((short) 38);
            row21.createCell(0).setCellValue("");
            row21.createCell(1).setCellValue("For RICLFILE Use Only");

            HSSFRow space19 = sheet.createRow((short) 39);

            HSSFRow row22 = sheet.createRow((short) 40);
            Cell comm = row22.createCell(1);
            comm.setCellValue("Commodity #:");
            comm.setCellStyle(redFontStyle);
            row22.createCell(2).setCellValue(commodityNumber);
            Cell desc = row22.createCell(3);
            desc.setCellValue("Description:");
            desc.setCellStyle(redFontStyle);
            row22.createCell(4).setCellValue(description);

            HSSFRow space20 = sheet.createRow((short) 41);

            HSSFRow row24 = sheet.createRow((short) 42);
            Cell TLI = row24.createCell(1);
            TLI.setCellValue("TLI #:");
            TLI.setCellStyle(redFontStyle);
            row24.createCell(2).setCellValue(tliNumber);

            HSSFRow space21 = sheet.createRow((short) 43);

            HSSFRow row26 = sheet.createRow((short) 44);
            Cell exp = row26.createCell(1);
            exp.setCellValue("Expiration: ");
            exp.setCellStyle(redFontStyle);
            row26.createCell(2).setCellValue(expirationDate);

            System.out.print("Your excel file has been generate");

            String spotRateId = String.valueOf(lastKey);

            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                workbook.write(fileOut);
            }
            try {

                // Initialize the properties class for sending SMTP
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp01.us.kline.com"); //Assign the smtp host
                props.put("mail.smtp.port", "25"); //Assign the port 465 = SSL, 25 != SSL

                //Initialize the Session and assign the properties to the session
                Session session = Session.getDefaultInstance(props, null);

                // Create the standardized email message sent
                String msgBody = "Sales,\n\n        Confirm spot rate (PID" + spotRateId + ") for RQS" + quoteID + " has been published.\n\n\nSincerely,\n\n KAM RICBCAR Publishing ";
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("kamricbcarpublishing@us.kline.com", "RORO Publishing Confirmation"));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress("connor.meehan@us.kline.com"));
                msg.setSubject("RQS New Spot Rate Confirmation");
                msg.setText(msgBody);
                Transport.send(msg);
                System.out.println("Success! " + msg);
            } catch (MessagingException | UnsupportedEncodingException e) {
                System.out.println("Error " + e.getMessage());
            }

            JOptionPane.showMessageDialog(null, "The spot filing for " + quoteID + " has been succsefully generated.");
            pQuoteIDTextField.setText("");
            validityFromDatePicker.getJFormattedTextField().setText("");
            validityToDatePicker.getJFormattedTextField().setText("");
            kkluNumberTextField.setText("");
            pPolTextField.setText("");
            pPodTextField.setText("");
            pCommodityClassComboBox.setSelectedIndex(0);
            pHandlingInstructions.setSelectedIndex(0);
            pCommodityDescriptionTextField.setText("");
            pOftTextField.setText("");
            pOftComboBox.setSelectedItem("");
            bafSubjectToTariffCheckBox.setSelected(false);
            pBafTextField.setText("");
            pBafIncludedCheckBox.setSelected(false);
            pEcaTextField.setText("");
            pEcaComboBox.setSelectedItem("");
            pEcaIncludedCheckBox.setSelected(false);
            ecaSubjectToTariffCheckBox.setSelected(false);
            pThcTextField.setText("");
            pThcComboBox.setSelectedIndex(0);
            pThcIncludedCheckBox.setSelected(false);
            thcSubjectToTariffCheckBox.setSelected(false);
            pWfgTextField.setText("");
            pWfgComboBox.setSelectedIndex(0);
            pWfgIncludedCheckBox.setSelected(false);
            wfgSubjectToTariffCheckBox.setSelected(false);
            pDocFeeComboBox.setSelectedIndex(0);
            pWarRiskCheckBox.setSelected(false);
            storageSubjectToTariffCheckBox.setSelected(false);
            pCommentsTextArea.setText("");
            pBookingNumberTextField.setText("");
            commodityNumberTextField.setText("");
            descriptionTextField.setText("");
            tliNumberTextField.setText("");
            expirationDateTextField.setText("");
            pQuoteNumberTextField.setText("");
            pIDTextField.setText("");

        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }


    }//GEN-LAST:event_newFilingCompleteButtonActionPerformed

    private void newFilingCompleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFilingCompleteButton1ActionPerformed
        /**
         * Allows Publishing to update a spot rate Send confirmation for update
         * to sales
         */

        // Connect to databse
        Connection conn = new DBConnection().connect();

        // Get value from inputs and assign to strings. 
        String validityFrom = validityFromDatePicker.getJFormattedTextField().getText();
        String validityTo = validityToDatePicker.getJFormattedTextField().getText();
        String kkluNumber = kkluNumberTextField.getText();
        String pol = pPolTextField.getText();
        String pod = pPodTextField.getText();
        String commClass = pCommodityClassComboBox.getSelectedItem().toString();
        String handlingInstructions = pHandlingInstructions.getSelectedItem().toString();
        String commDesc = pCommodityDescriptionTextField.getText();
        String oft = pOftTextField.getText();
        String oftUnit = pOftComboBox.getSelectedItem().toString();
        String baf = null;
        String bafText = pBafTextField.getText();
        Boolean bafIncluded = pBafIncludedCheckBox.isSelected();
        String eca = null;
        String ecaText = pEcaTextField.getText();
        String ecaUnit = pEcaComboBox.getSelectedItem().toString();
        Boolean ecaIncluded = pEcaIncludedCheckBox.isSelected();
        String thc = null;
        String thcText = "$" + pThcTextField.getText();
        String thcUnit = pThcComboBox.getSelectedItem().toString();
        Boolean thcIncluded = pThcIncludedCheckBox.isSelected();
        Boolean thcSubjectToTariff = thcSubjectToTariffCheckBox.isSelected();
        String wfg = null;
        String wfgText = pWfgTextField.getText();
        String wfgUnit = pWfgComboBox.getSelectedItem().toString();
        Boolean wfgIncluded = pWfgIncludedCheckBox.isSelected();
        Boolean wfgSubjectToTariff = wfgSubjectToTariffCheckBox.isSelected();
        String docFee = pDocFeeComboBox.getSelectedItem().toString();
        Boolean docFeeIncluded = pDocFeeIncludedCheckBox.isSelected();
        String comments = pCommentsTextArea.getText();
        Boolean warRisk = pWarRiskCheckBox.isSelected();
        String quoteID = pQuoteNumberTextField.getText();
        String bookingNumber = pBookingNumberTextField.getText();
        String storage = null;
        String storageText = storageTextField.getText();
        String storageUnit = storageUnitComboBox.getSelectedItem().toString();
        Boolean storageIncluded = storageIncludedCheckBox.isSelected();
        Boolean storageSubjectToTariff = storageSubjectToTariffCheckBox.isSelected();
        Boolean bafSubjectToTariff = bafSubjectToTariffCheckBox.isSelected();
        Boolean ecaSubjectToTariff = ecaSubjectToTariffCheckBox.isSelected();
        String commodityNumber = commodityNumberTextField.getText();
        String description = descriptionTextField.getText();
        String tliNumber = tliNumberTextField.getText();
        String expirationDate = expirationDateTextField.getText();
        String pid = pIDTextField.getText();

        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());

        if (bafIncluded != true && ecaSubjectToTariff != true) {
            baf = bafText;
        } else if (bafIncluded == true) {
            baf = "Included";
        } else if (bafSubjectToTariff == true) {
            baf = "Subject to Tariff";
        }

        if (ecaIncluded != true && ecaSubjectToTariff != true) {
            eca = ecaText;
        } else if (ecaIncluded == true) {
            eca = "Included";
        } else if (ecaSubjectToTariff == true) {
            eca = "Subject to Tariff";
        } else if (ecaSubjectToTariff == true) {
            eca = "Subject to Tariff";
        }

        if (thcSubjectToTariff == true) {
            thc = "Subject to Tariff";
        } else if (thcSubjectToTariff != true && thcIncluded != true) {
            thc = thcText;
        } else if (thcSubjectToTariff != true && thcIncluded == true) {
            thc = "Included";
        } else if (thcIncluded == true && thcSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        if (wfgSubjectToTariff == true) {
            wfg = "Subject to Tariff";
            wfgUnit = "N/A";
        } else if (wfgSubjectToTariff != true && wfgIncluded != true) {
            wfg = wfgText;
        } else if (wfgSubjectToTariff != true && wfgIncluded == true) {
            wfg = "Included";
        } else if (wfgIncluded == true && wfgSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        if (storageSubjectToTariff == true) {
            storage = "Subject to Tariff";
            storageUnit = "N/A";
        } else if (storageSubjectToTariff != true && storageIncluded != true) {
            storage = storageText;
        } else if (storageIncluded == true && storageSubjectToTariff != true) {
            storage = "Included";
        } else if (storageIncluded == true && storageSubjectToTariff == true) {
            JOptionPane.showMessageDialog(null, "You can only select one");
        }

        String sql = "UPDATE spotrates SET validityFrom=?, validityTo=?, tariffNumber=?, pol=?, pod=?, bookingNumber=?, commClass=?, handlingInstructions=?, commDesc=?, oft=?, oftUnit=?, baf=?, bafIncluded=?, bafPerTariff=?, ecaBaf=?, ecaBafUnit=?, ecaIncluded=?, ecaPerTariff=?, thc=?, thcUnit=?, thcIncluded=?, thcPerTariff=?, wfg=?, wfgUnit=?, wfgIncluded=?, wfgPerTariff=?, storage=?, storageUnit=?, storageIncluded=?, storagePerTariff=?, docFee=?, docFeeIncluded=?, comments=?, quoteID=?, warRisk=?, commodityNumber=?, description=?, tliNumber=?, expiration=?  WHERE ID=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, validityFrom);
            ps.setString(2, validityTo);
            ps.setString(3, kkluNumber);
            ps.setString(4, pol);
            ps.setString(5, pod);
            ps.setString(6, bookingNumber);
            ps.setString(7, commClass);
            ps.setString(8, handlingInstructions);
            ps.setString(9, commDesc);
            ps.setString(10, oft);
            ps.setString(11, oftUnit);
            ps.setString(12, baf);
            ps.setBoolean(13, bafIncluded);
            ps.setBoolean(14, bafSubjectToTariff);
            ps.setString(15, eca);
            ps.setString(16, ecaUnit);
            ps.setBoolean(17, ecaIncluded);
            ps.setBoolean(18, ecaSubjectToTariff);
            ps.setString(19, thc);
            ps.setString(20, thcUnit);
            ps.setBoolean(21, thcIncluded);
            ps.setBoolean(22, thcSubjectToTariff);
            ps.setString(23, wfg);
            ps.setString(24, wfgUnit);
            ps.setBoolean(25, wfgIncluded);
            ps.setBoolean(26, wfgSubjectToTariff);
            ps.setString(27, storage);
            ps.setString(28, storageUnit);
            ps.setBoolean(29, storageIncluded);
            ps.setBoolean(30, storageSubjectToTariff);
            ps.setString(31, docFee);
            ps.setBoolean(32, docFeeIncluded);
            ps.setString(33, comments);
            ps.setString(34, quoteID);
            ps.setBoolean(35, warRisk);
            ps.setString(36, commodityNumber);
            ps.setString(37, description);
            ps.setString(38, tliNumber);
            ps.setString(39, expirationDate);
            ps.setString(40, pid);

            ps.executeUpdate();

            String addBookingNumber = "UPDATE allquotes SET bookingNumber='" + bookingNumber + "', publishingID='" + pid + "', commodityNumber='" + commodityNumber + "', description = '" + description + "', tliNumber='" + tliNumber + "', expiration='" + expirationDate + "' WHERE ID='" + quoteID + "';";
            PreparedStatement psAddBookingNumber = conn.prepareStatement(addBookingNumber);
            psAddBookingNumber.executeUpdate(addBookingNumber);

            Double oft1 = (Double) (Double.parseDouble(oft));
            String eca1 = "";
            switch (eca) {
                case "Included":
                    eca1 = "Included";
                    break;
                case "Subject to Tariff":
                    eca1 = "Subject to Tariff";
                    break;
                default:
                    eca1 = "$" + eca + " " + ecaUnit;
                    break;
            }
            String baf1;
            switch (baf) {
                case "Included":
                    baf1 = "Included";
                    break;
                case "Subject To Tariff":
                    baf1 = "Subject to Tariff";
                    break;
                default:
                    baf1 = baf + "%";
                    break;
            }

            String thc1;

            switch (thc) {
                case "Included":
                    thc1 = thc;
                    break;
                case "Subject to Tariff":
                    thc1 = "Subject to Tariff";
                    break;
                default:
                    thc1 = "$" + thc + " per " + thcUnit;
                    break;
            }

            switch (thcUnit) {
                case "FAS":
                    thc1 = thcUnit;
                    break;
                case "Subject to local charges":
                    thc1 = thcUnit;
                default:
                    break;
            }

            String wfg1;
            switch (wfg) {
                case "Included":
                    wfg1 = wfg;
                    break;
                case "Subject to Tariff":
                    wfg1 = "Subject to Tariff";
                    break;
                default:
                    wfg1 = "$" + wfg + " per " + wfgUnit;
                    break;
            }

            switch (wfgUnit) {
                case "FAS":
                    wfg1 = wfgUnit;
                    break;
                case "Subject to local charges":
                    wfg1 = wfgUnit;
                default:
                    break;
            }

            /*
             ***************************************
             ***************************************
             ***************************************
             */
            String filename = userHomeFolder + "\\Desktop\\Publishing\\" + pol + " To " + pod + "; " + commDesc + " PID" + pid + ".xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("KKLU" + kkluNumber);

            sheet.setColumnWidth(0, 650);
            sheet.setColumnWidth(1, 5742);
            sheet.setColumnWidth(2, 5920);
            sheet.setColumnWidth(3, 3668);
            sheet.setColumnWidth(4, 5711);

            //Bold Font
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            CellStyle style = workbook.createCellStyle();
            style.setFont(font);

            //Currency cell type
            CellStyle currency = workbook.createCellStyle();
            currency.setDataFormat((short) 7);

            //Percentage cell type
            CellStyle percentage = workbook.createCellStyle();
            percentage.setDataFormat((short) 0xa);

            sheet.setDisplayGridlines(false);

            //Black medium sized border around cell
            CellStyle blackBorder = workbook.createCellStyle();
            blackBorder.setBorderBottom(CellStyle.BORDER_MEDIUM);
            blackBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderLeft(CellStyle.BORDER_MEDIUM);
            blackBorder.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderRight(CellStyle.BORDER_MEDIUM);
            blackBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
            blackBorder.setBorderTop(CellStyle.BORDER_MEDIUM);
            blackBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());

            //Red font
            CellStyle redFontStyle = workbook.createCellStyle();
            HSSFFont redFont = workbook.createFont();
            redFont.setColor(HSSFColor.RED.index);
            redFontStyle.setFont(redFont);

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(3).setCellValue(validityFrom);

            HSSFRow row1 = sheet.createRow((short) 1);
            Cell cell = row1.createCell(1);
            cell.setCellValue("FILING REQUEST TO RICLFILE");
            cell.setCellStyle(style);

            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));

            sheet.setPrintGridlines(false);

            HSSFRow row2 = sheet.createRow((short) 2);
            row2.createCell(0).setCellValue("A)");
            row2.createCell(1).setCellValue("Tariff Number(KKLU):");
            row2.createCell(2).setCellValue(kkluNumber);

            HSSFRow space0 = sheet.createRow((short) 3);

            HSSFRow row4 = sheet.createRow((short) 4);
            row4.createCell(0).setCellValue("B)");
            row4.createCell(1).setCellValue("Commodity:");
            row4.createCell(2).setCellValue(commDesc);

            HSSFRow space2 = sheet.createRow((short) 5);

            HSSFRow row5 = sheet.createRow((short) 6);
            row5.createCell(0).setCellValue("C)");
            row5.createCell(1).setCellValue("POL:");
            row5.createCell(2).setCellValue(pol);

            HSSFRow space3 = sheet.createRow((short) 7);

            HSSFRow row6 = sheet.createRow((short) 8);
            row6.createCell(0).setCellValue("D)");
            row6.createCell(1).setCellValue("POD:");
            row6.createCell(2).setCellValue(pod);

            HSSFRow space4 = sheet.createRow((short) 9);

            HSSFRow row7 = sheet.createRow((short) 10);
            row7.createCell(0).setCellValue("E)");
            row7.createCell(1).setCellValue("Rate:");
            Cell rate = row7.createCell(2);
            rate.setCellValue(oft1);
            rate.setCellStyle(currency);

            HSSFRow space5 = sheet.createRow((short) 11);

            HSSFRow row8 = sheet.createRow((short) 12);
            row8.createCell(0).setCellValue("F)");
            row8.createCell(1).setCellValue("Rate Basis:");
            row8.createCell(2).setCellValue(oftUnit);

            HSSFRow space6 = sheet.createRow((short) 13);

            HSSFRow row9 = sheet.createRow((short) 14);
            row9.createCell(0).setCellValue("G)");
            row9.createCell(1).setCellValue("BAF:");
            Cell Baf = row9.createCell(2);
            switch (baf1) {
                case "Included":
                    Baf.setCellValue("Included");
                    break;
                case "Subject to Tariff":
                    Baf.setCellValue("Subject to Tariff");
                    break;
                default:
                    Baf.setCellValue(baf1);
                    Baf.setCellStyle(percentage);
                    break;
            }

            HSSFRow space7 = sheet.createRow((short) 15);

            HSSFRow row10 = sheet.createRow((short) 16);
            row10.createCell(0).setCellValue("H)");
            row10.createCell(1).setCellValue("ECA BAF:");
            Cell ecaBaf = row10.createCell(2);
            switch (eca) {
                case "Included":
                    ecaBaf.setCellValue("Included");
                    break;
                case "Subject to Tariff":
                    ecaBaf.setCellValue("Subject to Tariff");
                    break;
                default:
                    ecaBaf.setCellValue("$" + eca1 + " per " + ecaUnit);
                    ecaBaf.setCellStyle(currency);
                    break;
            }

            HSSFRow space8 = sheet.createRow((short) 17);

            HSSFRow row11 = sheet.createRow((short) 18);
            row11.createCell(0).setCellValue("I)");
            row11.createCell(1).setCellValue("THC/WFG:");
            row11.createCell(2).setCellValue(thc1 + " / " + wfg1);

            HSSFRow space = sheet.createRow((short) 19);

            HSSFRow row12 = sheet.createRow((short) 20);
            row12.createCell(0).setCellValue("J)");
            row12.createCell(1).setCellValue("Storage:");
            row12.createCell(2).setCellValue(storage);

            HSSFRow space10 = sheet.createRow((short) 21);

            HSSFRow row13 = sheet.createRow((short) 22);
            row13.createCell(0).setCellValue("K)");
            row13.createCell(1).setCellValue("Doc Fee:");
            row13.createCell(2).setCellValue(docFee);

            HSSFRow space11 = sheet.createRow((short) 23);

            HSSFRow row14 = sheet.createRow((short) 24);
            row14.createCell(0).setCellValue("L)");
            row14.createCell(1).setCellValue("War Risk:");

            HSSFRow space12 = sheet.createRow((short) 25);

            if (warRisk == true) {
                String warRiskPercentage = "3%";
                row14.createCell(2).setCellValue(warRiskPercentage);
            } else if (warRisk != true) {
                String warRiskPercentage = "N/A";
                row14.createCell(2).setCellValue(warRiskPercentage);
            }

            HSSFRow row15 = sheet.createRow((short) 26);
            row15.createCell(0).setCellValue("M)");
            row15.createCell(1).setCellValue("Validity");
            row15.createCell(2).setCellValue("Effective: " + validityFrom);

            HSSFRow row16 = sheet.createRow((short) 27);
            row16.createCell(2).setCellValue("Expiration: " + validityTo);

            HSSFRow space13 = sheet.createRow((short) 28);

            HSSFRow row17 = sheet.createRow((short) 29);
            row17.createCell(0).setCellValue("N)");
            row17.createCell(1).setCellValue("Remarks");
            row17.createCell(2).setCellValue(comments);

            HSSFRow space14 = sheet.createRow((short) 30);

            HSSFRow row18 = sheet.createRow((short) 31);
            row18.createCell(0).setCellValue("O)");
            row18.createCell(1).setCellValue("Booking #:");
            row18.createCell(2).setCellValue(bookingNumber);

            HSSFRow space15 = sheet.createRow((short) 32);

            HSSFRow row19 = sheet.createRow((short) 33);
            row19.createCell(0).setCellValue("P)");
            row19.createCell(1).setCellValue("RQS #:");
            row19.createCell(2).setCellValue(quoteID);

            HSSFRow space16 = sheet.createRow((short) 34);

            HSSFRow row20 = sheet.createRow((short) 35);
            row20.createCell(0).setCellValue("Q)");
            row20.createCell(1).setCellValue("PID #:");
            row20.createCell(2).setCellValue(pid);

            HSSFRow space17 = sheet.createRow((short) 36);

            HSSFRow space18 = sheet.createRow((short) 37);

            HSSFRow row21 = sheet.createRow((short) 38);
            row21.createCell(0).setCellValue("");
            row21.createCell(1).setCellValue("For RICLFILE Use Only");

            HSSFRow space19 = sheet.createRow((short) 39);

            HSSFRow row22 = sheet.createRow((short) 40);
            Cell comm = row22.createCell(1);
            comm.setCellValue("Commodity #:");
            comm.setCellStyle(redFontStyle);
            row22.createCell(2).setCellValue(commodityNumber);
            Cell desc = row22.createCell(3);
            desc.setCellValue("Description:");
            desc.setCellStyle(redFontStyle);
            row22.createCell(4).setCellValue(description);

            HSSFRow space20 = sheet.createRow((short) 41);

            HSSFRow row24 = sheet.createRow((short) 42);
            Cell TLI = row24.createCell(1);
            TLI.setCellValue("TLI #:");
            TLI.setCellStyle(redFontStyle);
            row24.createCell(2).setCellValue(tliNumber);

            HSSFRow space21 = sheet.createRow((short) 43);

            HSSFRow row26 = sheet.createRow((short) 44);
            Cell exp = row26.createCell(1);
            exp.setCellValue("Expiration: ");
            exp.setCellStyle(redFontStyle);
            row26.createCell(2).setCellValue(expirationDate);

            System.out.print("Your excel file has been generate");

            String spotRateId = String.valueOf(pid);

            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                workbook.write(fileOut);
            }
            try {

                // Initialize the properties class for sending SMTP
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp01.us.kline.com"); //Assign the smtp host
                props.put("mail.smtp.port", "25"); //Assign the port 465 = SSL, 25 != SSL

                //Initialize the Session and assign the properties to the session
                Session session = Session.getDefaultInstance(props, null);

                // Create the standardized email message sent
                String msgBody = "Sales,\n\n        Confirm spot rate (PID" + spotRateId + ") for RQS" + quoteID + " has been updated.\n\n\nSincerely,\n\n KAM RICBCAR Publishing ";
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("kamricbcarpublishing@us.kline.com", "RORO Publishing Confirmation"));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress("connor.meehan@us.kline.com"));
                msg.setSubject("RQS" + quoteID + " Updated Spot Rate PID" + spotRateId + " Confirmation");
                msg.setText(msgBody);
                Transport.send(msg);
                System.out.println("Success! " + msg);
            } catch (MessagingException | UnsupportedEncodingException e) {
                System.out.println("Error " + e.getMessage());
            }

            JOptionPane.showMessageDialog(null, "The spot filing for " + quoteID + " has been succsefully updated.");
            pQuoteIDTextField.setText("");
            validityFromDatePicker.getJFormattedTextField().setText("");
            validityToDatePicker.getJFormattedTextField().setText("");
            kkluNumberTextField.setText("");
            pPolTextField.setText("");
            pPodTextField.setText("");
            pCommodityClassComboBox.setSelectedIndex(0);
            pHandlingInstructions.setSelectedIndex(0);
            pCommodityDescriptionTextField.setText("");
            pOftTextField.setText("");
            pOftComboBox.setSelectedItem("");
            bafSubjectToTariffCheckBox.setSelected(false);
            pBafTextField.setText("");
            pBafIncludedCheckBox.setSelected(false);
            pEcaTextField.setText("");
            pEcaComboBox.setSelectedItem("");
            pEcaIncludedCheckBox.setSelected(false);
            ecaSubjectToTariffCheckBox.setSelected(false);
            pThcTextField.setText("");
            pThcComboBox.setSelectedIndex(0);
            pThcIncludedCheckBox.setSelected(false);
            thcSubjectToTariffCheckBox.setSelected(false);
            pWfgTextField.setText("");
            pWfgComboBox.setSelectedIndex(0);
            pWfgIncludedCheckBox.setSelected(false);
            wfgSubjectToTariffCheckBox.setSelected(false);
            pDocFeeComboBox.setSelectedIndex(0);
            pWarRiskCheckBox.setSelected(false);
            storageSubjectToTariffCheckBox.setSelected(false);
            pCommentsTextArea.setText("");
            pBookingNumberTextField.setText("");
            commodityNumberTextField.setText("");
            descriptionTextField.setText("");
            tliNumberTextField.setText("");
            expirationDateTextField.setText("");
            pQuoteIDTextField.setText("");
            pIDTextField.setText("");

        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));

        }

    }//GEN-LAST:event_newFilingCompleteButton1ActionPerformed

    private void packingListTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_packingListTableKeyPressed
        // TODO add your handling code here:
        //int code = evt.getKeyCode();
        //System.out.println(code);
    }//GEN-LAST:event_packingListTableKeyPressed

    private void packingListTableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_packingListTableKeyTyped
        // TODO add your handling code here:
        if (evt.isControlDown()) {
            if (KeyEvent.VK_V == 86) {
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringBuilder sb = new StringBuilder();
                sb.append(clpbrd);
                System.out.println(String.valueOf(clpbrd));
            } else if (KeyEvent.VK_C == 67) {
            } else {
                System.out.println("Fail again");
            }
        }
    }//GEN-LAST:event_packingListTableKeyTyped

    private void newCustomerContractExpirationDateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCustomerContractExpirationDateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newCustomerContractExpirationDateTextFieldActionPerformed

    private void bafSubjectToTariffCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bafSubjectToTariffCheckBoxItemStateChanged
        pBafTextField.setText("");
    }//GEN-LAST:event_bafSubjectToTariffCheckBoxItemStateChanged

    private void ecaSubjectToTariffCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ecaSubjectToTariffCheckBoxItemStateChanged
        pEcaTextField.setText("");
        pEcaComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_ecaSubjectToTariffCheckBoxItemStateChanged

    private void thcSubjectToTariffCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_thcSubjectToTariffCheckBoxItemStateChanged
        pThcTextField.setText("");
        pThcComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_thcSubjectToTariffCheckBoxItemStateChanged

    private void wfgSubjectToTariffCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wfgSubjectToTariffCheckBoxItemStateChanged
        pWfgTextField.setText("");
        pWfgComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_wfgSubjectToTariffCheckBoxItemStateChanged

    private void storageSubjectToTariffCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_storageSubjectToTariffCheckBoxItemStateChanged
        storageTextField.setText("");
    }//GEN-LAST:event_storageSubjectToTariffCheckBoxItemStateChanged

    private void pBafIncludedCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pBafIncludedCheckBoxItemStateChanged
        pBafTextField.setText("");
    }//GEN-LAST:event_pBafIncludedCheckBoxItemStateChanged

    private void pEcaIncludedCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pEcaIncludedCheckBoxItemStateChanged
        pEcaTextField.setText("");
        pEcaComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_pEcaIncludedCheckBoxItemStateChanged

    private void pThcIncludedCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pThcIncludedCheckBoxItemStateChanged
        pThcTextField.setText("");
        pThcComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_pThcIncludedCheckBoxItemStateChanged

    private void pWfgIncludedCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pWfgIncludedCheckBoxItemStateChanged
        pWfgTextField.setText("");
        pWfgComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_pWfgIncludedCheckBoxItemStateChanged

    private void storageIncludedCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_storageIncludedCheckBoxItemStateChanged
        storageTextField.setText("");
    }//GEN-LAST:event_storageIncludedCheckBoxItemStateChanged

    private void pBafTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pBafTextFieldKeyTyped
        pBafIncludedCheckBox.setSelected(false);
        bafSubjectToTariffCheckBox.setSelected(false);
    }//GEN-LAST:event_pBafTextFieldKeyTyped

    private void pEcaTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pEcaTextFieldKeyTyped
        pEcaIncludedCheckBox.setSelected(false);
        ecaSubjectToTariffCheckBox.setSelected(false);
    }//GEN-LAST:event_pEcaTextFieldKeyTyped

    private void pThcTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pThcTextFieldKeyTyped
        pThcIncludedCheckBox.setSelected(false);
        thcSubjectToTariffCheckBox.setSelected(false);
    }//GEN-LAST:event_pThcTextFieldKeyTyped

    private void pWfgTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pWfgTextFieldKeyTyped
        pWfgIncludedCheckBox.setSelected(false);
        wfgSubjectToTariffCheckBox.setSelected(false);
    }//GEN-LAST:event_pWfgTextFieldKeyTyped

    private void storageTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_storageTextFieldKeyTyped
        storageIncludedCheckBox.setSelected(false);
        storageSubjectToTariffCheckBox.setSelected(false);
    }//GEN-LAST:event_storageTextFieldKeyTyped

    private void pEcaComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pEcaComboBoxItemStateChanged
        pEcaIncludedCheckBox.setSelected(false);
        ecaSubjectToTariffCheckBox.setSelected(false);
    }//GEN-LAST:event_pEcaComboBoxItemStateChanged

    private void pThcComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pThcComboBoxItemStateChanged
        pThcIncludedCheckBox.setSelected(false);
        thcSubjectToTariffCheckBox.setSelected(false);
    }//GEN-LAST:event_pThcComboBoxItemStateChanged

    private void pWfgComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pWfgComboBoxItemStateChanged
        pWfgIncludedCheckBox.setSelected(false);
        wfgSubjectToTariffCheckBox.setSelected(false);
    }//GEN-LAST:event_pWfgComboBoxItemStateChanged

    private void storageUnitComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_storageUnitComboBoxItemStateChanged
        storageIncludedCheckBox.setSelected(false);
        storageSubjectToTariffCheckBox.setSelected(false);
    }//GEN-LAST:event_storageUnitComboBoxItemStateChanged

    private void pDocFeeIncludedCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pDocFeeIncludedCheckBoxItemStateChanged
        pDocFeeComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_pDocFeeIncludedCheckBoxItemStateChanged

    private void pDocFeeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pDocFeeComboBoxItemStateChanged
        pDocFeeIncludedCheckBox.setSelected(false);
    }//GEN-LAST:event_pDocFeeComboBoxItemStateChanged

    private void updateEditQuoteAddRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEditQuoteAddRowButtonActionPerformed
        /**
         * This function will allow the user to add rows to the packing list on
         * the update/edit page
         */

        //Get the Quote ID
        String quoteID = updateQuoteIDTextArea.getText();

        //Connect to the database
        Connection conn = new DBConnection().connect();

        /**
         * When the add row button is clicked a new row will be added to the
         * packinglist table The Quote ID will also be added on the same line
         */
        //SQL to insert a new line in the packinglist table
        String addLineSql = "INSERT INTO packinglist(quoteID) VALUES(?)";
        try {
            PreparedStatement ps = conn.prepareStatement(addLineSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, quoteID);
            int rs = ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            int lastKey = 1;
            while (keys.next()) {
                lastKey = keys.getInt(1);
            }
            DefaultTableModel model = (DefaultTableModel) updateEditPackingListTable.getModel();
            model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", lastKey});
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_updateEditQuoteAddRowButtonActionPerformed

    private void savedSearchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savedSearchesButtonActionPerformed
        SavedSearches ss = new SavedSearches();

        //Open the DBConnection
        Connection conn = new DBConnection().connect();

        //SQL to populate the saved searches table
        String getSearches = "SELECT ID, query_name AS 'Query Name', date AS 'Date', user AS 'Username' FROM saved_searches_name;";

        try {
            PreparedStatement psGetSearches = conn.prepareStatement(getSearches);
            ResultSet rsGetSearches = psGetSearches.executeQuery();

            // Populate table with saved searches
            SavedSearches.savedSearchesTable.setModel(DbUtils.resultSetToTableModel(rsGetSearches));
        } catch (Exception ex) {
            System.out.println("Error! getSearches: " + ex.getMessage());
        }

        ss.setVisible(true);
    }//GEN-LAST:event_savedSearchesButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new ExcelToJTable().chooseData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void calculateCubicMetersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateCubicMetersButtonActionPerformed
        findCubicMetersNewQuote();
    }//GEN-LAST:event_calculateCubicMetersButtonActionPerformed

    private void calculateCubicMetersButtonEditPLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateCubicMetersButtonEditPLActionPerformed
        findCubicMetersUpdateQuote();
    }//GEN-LAST:event_calculateCubicMetersButtonEditPLActionPerformed

    private void pBafIncludedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pBafIncludedCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBafIncludedCheckBoxActionPerformed

    private void polTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_polTextFieldFocusGained

    }//GEN-LAST:event_polTextFieldFocusGained

    private void newQuoteSailingScheduleTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newQuoteSailingScheduleTableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_newQuoteSailingScheduleTableKeyPressed

    private void newQuoteSailingScheduleTableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newQuoteSailingScheduleTableKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_newQuoteSailingScheduleTableKeyTyped

    private void existingCompanyContractYesRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingCompanyContractYesRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingCompanyContractYesRadioButtonActionPerformed

    private void updateEditSailingScheduleTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_updateEditSailingScheduleTableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateEditSailingScheduleTableKeyPressed

    private void updateEditSailingScheduleTableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_updateEditSailingScheduleTableKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_updateEditSailingScheduleTableKeyTyped

    private void updateEditSailingScheduleAddRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEditSailingScheduleAddRowButtonActionPerformed

        /**
         * This function will allow the user to add rows to the packing list on
         * the update/edit page
         */
        //Get the Quote ID
        String quote_ID = updateQuoteIDTextArea.getText();

        //Connect to the database
        Connection conn = new DBConnection().connect();

        /**
         * When the add row button is clicked a new row will be added to the
         * update quote schedule table The Quote ID will also be added on the
         * same line
         */
        //SQL to insert a new line in the packinglist table
        String addLineSql = "INSERT INTO quote_schedule (quote_id) VALUES(?)";
        try {
            PreparedStatement ps = conn.prepareStatement(addLineSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, quote_ID);
            int rs = ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            int lastKey = 1;
            while (keys.next()) {
                lastKey = keys.getInt(1);
            }
            DefaultTableModel ssModel = (DefaultTableModel) updateEditSailingScheduleTable.getModel();
            ssModel.addRow(new Object[]{"", "", "", "", lastKey});
            SailingScheduleTable();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_updateEditSailingScheduleAddRowButtonActionPerformed

    protected void ClearUpdateQuotePanel() {
        // Clears the update quote panel inputs and resets all the tables to their default values
        updateQuoteIDTextArea.setText("");
        updateEditQuoteCustomerNameLabel.setText("N/A");
        updateContactNameTextField.setText("");
        updateContactEmailTextField.setText("");
        authorLabel.setText("N/A");
        lastUpdatedByLabel.setText("N/A");
        updateAlphaNumeralTextField.setText("");
        currentAlphaNumeralLabel.setText("N/A");
        quoteCreatedLabel.setText("N/A");
        quoteLastUpdatedLabel.setText("N/A");
        updateTradeLane.setSelectedIndex(0);
        updatePOLTextField.setText("");
        updatePODTextField.setText("");
        updateTshp1TextField.setText("");
        updateTshp2TextField.setText("");
        updateCommodityClassComboBox.setSelectedIndex(0);
        updateHandlingInstructionsComboBox.setSelectedIndex(0);
        updateQuoteAccessoriesCheckBox.setSelected(false);
        updateQuoteMAFIMinimumCheckBox.setSelected(false);
        updateEditMAFIMinimumTextField.setText("");
        updateCommodityDescriptionTextField.setText("");
        updateOFTTextField.setText("");
        updateOftUnitComboBox.setSelectedIndex(0);
        updateBAFTextField.setText("");
        updateBafIncludedCheckBox.setSelected(false);
        updateEcaBafTextField.setText("");
        updateEcaComboBox.setSelectedIndex(0);
        updateEcaIncludedCheckBox.setSelected(false);
        updateTHCTextField.setText("");
        updateThcComboBox.setSelectedIndex(0);
        updateThcIncludedCheckBox.setSelected(false);
        updateThcAttachedCheckBox.setSelected(false);
        updateWfgTextField.setText("");
        updateWfgComboBox.setSelectedIndex(0);
        updateWfgIncludedCheckBox.setSelected(false);
        updateWfgAttachedCheckBox.setSelected(false);
        updateWarRiskCheckBox.setSelected(false);
        updateDocumentationFeeComboBox.setSelectedIndex(0);
        updateDocFeeIncludedCheckBox.setSelected(false);
        updateContractRateCheckBox.setSelected(false);
        updateSpotRateCheckBox.setSelected(false);
        editQuoteDuplicateRateCheckBox.setSelected(false);
        updateBookedCheckBox.setSelected(false);
        updateEditQuoteBookingNumberTextField.setText("");
        updateDeclineCheckBox.setSelected(false);
        updateDeclineComboBox.setSelectedIndex(0);
        quoteFeedbackCheckBox.setSelected(false);
        quoteFeedbackComboBox.setSelectedIndex(0);
        quoteFeedbackTextField.setText("");

        // Reset the sailing schedule table
        editQuoteIncludeSailingScheduleCheckBox.setSelected(false);
        int ssRows = updateEditSailingScheduleTable.getRowCount();
        int ssCols = updateEditSailingScheduleTable.getColumnCount();
        for (int r = 0; r < ssRows; r++) {
            for (int c = 0; c < ssCols; c++) {
                updateEditSailingScheduleTable.setValueAt("", r, c);
            }
        }
        DefaultTableModel uessdtm = (DefaultTableModel) updateEditSailingScheduleTable.getModel();
        uessdtm.setRowCount(0);
        updateEditSailingScheduleTable.setModel(uessdtm);
        updateCommentsTextArea.setText("");
        editQuoteIncludeShipperCommentsCheckBox.setSelected(false);
        updateShipperCommentsTextArea.setText("");

        // Reset the packing list table
        int Rows = updateEditPackingListTable.getRowCount();
        int Cols = updateEditPackingListTable.getColumnCount();
        for (int r = 0; r < Rows; r++) {
            for (int c = 0; c < Cols; c++) {
                updateEditPackingListTable.setValueAt("", r, c);
            }
        }
        DefaultTableModel mdl = (DefaultTableModel) updateEditPackingListTable.getModel();
        mdl.setRowCount(0);
        updateEditPackingListTable.setModel(mdl);

    }

    protected void ClearExistingCustomerPanel() {
        // Clear/reset all inputs on the existing customer panel
        existingCompanyNameTextField.setText("");
        existingCompanyFirstNameTextField.setText("");
        existingCompanyLastNameTextField.setText("");
        existingCompanyOfficePhoneTextField.setText("");
        existingCompanyMobilePhoneTextField.setText("");
        existingCompanyEmailTextField.setText("");
        existingCompanyTitleTextField.setText("");
        updatePCAddressTextfield.setText("");
        updatePCSuiteTextField.setText("");
        updatePCCityTextField.setText("");
        updatePCStateTextField.setText("");
        updatePCZipTextField.setText("");
        updatePCCountryTextField.setText("");
        existingCompanyIDTextField.setText("");
        existingCompanyCompanyTextField.setText("");
        existingCompanyDBATextField.setText("");
        existingCompanyOTINumberTextField.setText("");
        existingCustomerFreightForwarderRadioButton.setSelected(false);
        existingCustomerNVOCCRadioButton.setSelected(false);
        existingCustomerBCORadioButton.setSelected(false);
        existingCustomerOtherRadioButton.setSelected(false);
        existingCompanyContractYesRadioButton.setSelected(false);
        existingCompanyNoContractRadioButton.setSelected(false);
        existingCompanyContractNumberTextField.setText("");
        existingCustomerContractExpirationDatePicker.getJFormattedTextField().setText("");
        existingCompanyMainPhoneTextField.setText("");
        existingCompanySecondaryPhoneTextField.setText("");
        existingCompanyFaxNumberTextField.setText("");
        existingCompanyMainEmailTextField.setText("");
        existingCompanyAddress1TextField.setText("");
        existingCompanyAddress2TextField.setText("");
        existingCompanyCityTextField.setText("");
        existingCompanyStateTextField.setText("");
        existingCompanyZipTextField.setText("");
        existingCompanyCountryTextField.setText("");
        existingCompanyCommentsTextArea.setText("");
    }

    public void findCubicMetersNewQuote() {
        TableModel mdl = packingListTable.getModel();
        for (int r = 0; r < mdl.getRowCount(); r++) {
            l_centimeters_cell = mdl.getValueAt(r, 2);
            w_centimeters_cell = mdl.getValueAt(r, 3);
            h_centimeters_cell = mdl.getValueAt(r, 4);
            l_inches_cell = mdl.getValueAt(r, 6);
            w_inches_cell = mdl.getValueAt(r, 7);
            h_inches_cell = mdl.getValueAt(r, 8);
            if (l_centimeters_cell == null || l_centimeters_cell == "" || l_centimeters_cell.equals("")) {
                l_centimeters_cell = 0.0;
            }
            if (w_centimeters_cell == null || w_centimeters_cell == "" || w_centimeters_cell.equals("")) {
                w_centimeters_cell = 0.0;
            }
            if (h_centimeters_cell == null || h_centimeters_cell == "" || h_centimeters_cell.equals("")) {
                h_centimeters_cell = 0.0;
            }
            if (l_inches_cell == null || l_inches_cell == "") {
                l_inches_cell = 0.0;
            }
            if (w_inches_cell == null || w_inches_cell == "") {
                w_inches_cell = 0.0;
            }
            if (h_inches_cell == null || h_inches_cell == "") {
                h_inches_cell = 0.0;
            }
            l_centimeters = (Double) (Double.parseDouble(l_centimeters_cell.toString()));
            w_centimeters = (Double) (Double.parseDouble(w_centimeters_cell.toString()));
            h_centimeters = (Double) (Double.parseDouble(h_centimeters_cell.toString()));
            l_inches = (Double) (Double.parseDouble(l_inches_cell.toString()));
            w_inches = (Double) (Double.parseDouble(w_inches_cell.toString()));
            h_inches = (Double) (Double.parseDouble(h_inches_cell.toString()));

            if (l_centimeters == 0.0) {
                cubic_meters = (l_inches * w_inches * h_inches) / 61024;
            } else {
                cubic_meters = (l_centimeters * w_centimeters * h_centimeters) / 1000000;
            }
            Object formated_cubic_meters = df.format(cubic_meters);

            mdl.setValueAt(formated_cubic_meters, r, 9);
        }
    }

    protected void ClearNewCustomerPanel() {
        newCustomerFirstName.setText("");
        newCustomerLastName.setText("");
        newCustomerOfficePhone.setText("");
        newCustomerMobilePhoneTextField.setText("");
        newCustomerEmailTextField.setText("");
        newCustomerTitleTextField.setText("");
        pcOfficeAddressTextField.setText("");
        pcSuiteTextField.setText("");
        pcCityTextField.setText("");
        pcStateTextField.setText("");
        pcZipTextField.setText("");
        pcCountryTextField.setText("");
        newCustomerCompanyTextField.setText("");
        newCustomerDBATextField.setText("");
        newCustomerOTINumberTextField.setText("");
        newCustomerFreightForwarderRadioButton.setSelected(false);
        newCustomerNVOCCRadioButton.setSelected(false);
        newCustomerBCORadioButton.setSelected(false);
        newCustomerOtherRadioButton.setSelected(false);
        newCustomerContractYesRadioButton.setSelected(false);
        newCustomerNoContractRadioButton.setSelected(true);
        newCustomerContractNumberTextField.setText("");
        newCustomerContractExpirationDatePicker.getJFormattedTextField().setText("");
        newCustomerMainPhoneTextField.setText("");
        newCustomerSecondaryPhoneNumber.setText("");
        newCustomerFaxNumber.setText("");
        newCustomerCompanyEmailTextField.setText("");
        newCustomerAddress1.setText("");
        newCustomerAddress2.setText("");
        newCustomerCity.setText("");
        newCustomerState.setText("");
        newCustomerZipCode.setText("");
        newCustomerCountry.setText("");
        newCustomerComments.setText("");
    }

    protected void findCubicMetersUpdateQuote() {
        TableModel mdl = updateEditPackingListTable.getModel();
        for (int r = 0; r < mdl.getRowCount(); r++) {
            l_centimeters_cell = mdl.getValueAt(r, 2);
            w_centimeters_cell = mdl.getValueAt(r, 3);
            h_centimeters_cell = mdl.getValueAt(r, 4);
            l_inches_cell = mdl.getValueAt(r, 6);
            w_inches_cell = mdl.getValueAt(r, 7);
            h_inches_cell = mdl.getValueAt(r, 8);
            if (l_centimeters_cell == null || l_centimeters_cell.equals("")) {
                l_centimeters_cell = 0;
            }
            if (w_centimeters_cell == null || w_centimeters_cell.equals("")) {
                w_centimeters_cell = 0;
            }
            if (h_centimeters_cell == null || h_centimeters_cell.equals("")) {
                h_centimeters_cell = 0;
            }
            if (l_inches_cell == null || l_inches_cell.equals("")) {
                l_inches_cell = 0;
            }
            if (w_inches_cell == null || w_inches_cell.equals("")) {
                w_inches_cell = 0;
            }
            if (h_inches_cell == null || h_inches_cell.equals("")) {
                h_inches_cell = 0;
            }
            l_centimeters = (Double) (Double.parseDouble(l_centimeters_cell.toString()));
            w_centimeters = (Double) (Double.parseDouble(w_centimeters_cell.toString()));
            h_centimeters = (Double) (Double.parseDouble(h_centimeters_cell.toString()));
            l_inches = (Double) (Double.parseDouble(l_inches_cell.toString()));
            w_inches = (Double) (Double.parseDouble(w_inches_cell.toString()));
            h_inches = (Double) (Double.parseDouble(h_inches_cell.toString()));

            if (l_centimeters == 0.0) {
                cubic_meters = (l_inches * w_inches * h_inches) / 61024;
            } else {
                cubic_meters = (l_centimeters * w_centimeters * h_centimeters) / 1000000;
            }
            Object formated_cubic_meters = df.format(cubic_meters);

            mdl.setValueAt(formated_cubic_meters, r, 9);
        }
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("drive_green_project.jpg")));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JMenu EditMenuItem;
    public static javax.swing.JMenuItem NewCustMenuItem;
    public static javax.swing.JButton UserInformationCenterButton;
    public static javax.swing.JLabel authorLabel;
    public static javax.swing.JLabel authorLabel1;
    public static javax.swing.JLabel authorLabel2;
    public static javax.swing.JLabel authorLabel3;
    public static javax.swing.JCheckBox bafIncludedCheckBox;
    public static javax.swing.JCheckBox bafSubjectToTariffCheckBox;
    public static javax.swing.JTextField bafTextField;
    public static javax.swing.JLabel bookedToDateLabel;
    public static javax.swing.JTextField bookingNumberTextField;
    public static javax.swing.JLabel bookingRatioLabel;
    public static javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.ButtonGroup buttonGroup10;
    public static javax.swing.ButtonGroup buttonGroup11;
    public static javax.swing.ButtonGroup buttonGroup12;
    public static javax.swing.ButtonGroup buttonGroup13;
    public static javax.swing.ButtonGroup buttonGroup2;
    public static javax.swing.ButtonGroup buttonGroup3;
    public static javax.swing.ButtonGroup buttonGroup4;
    public static javax.swing.ButtonGroup buttonGroup5;
    public static javax.swing.ButtonGroup buttonGroup6;
    public static javax.swing.ButtonGroup buttonGroup7;
    public static javax.swing.ButtonGroup buttonGroup8;
    public static javax.swing.ButtonGroup buttonGroup9;
    public static javax.swing.JButton calculateCubicMetersButton;
    public static javax.swing.JButton calculateCubicMetersButtonEditPL;
    public static javax.swing.JButton cancelNewCustomerButton;
    public static javax.swing.JButton cancelSpotFileButton;
    public static javax.swing.JButton clearNewQuoteForm;
    public static javax.swing.JButton clearResultTable;
    public static javax.swing.JMenuItem closeApplicationMenuItem;
    public static javax.swing.JTextArea commentsTextArea;
    public static javax.swing.JComboBox commodityClassComboBox;
    public static javax.swing.JTextField commodityDescriptionTextField;
    public static javax.swing.JFormattedTextField commodityNumberTextField;
    public static javax.swing.JTextField contactEmailTextField;
    public static javax.swing.JTextField contactNameTextField;
    public static javax.swing.JCheckBox contractRateCheckBox;
    public static javax.swing.JMenuItem copyMenuItem;
    public static javax.swing.JLabel currentAlphaNumeralLabel;
    public static javax.swing.JButton customerInformationCenterButton;
    public static javax.swing.JPanel customerInformationPanel;
    public static javax.swing.JMenuItem cutMenuItem;
    public static javax.swing.JTextField dateTextField;
    public static javax.swing.JComboBox declineComboBox;
    public static javax.swing.JFormattedTextField descriptionTextField;
    public static javax.swing.JComboBox documentationFeeComboBox;
    public static javax.swing.JCheckBox documentationFeeIncludedCheckBox;
    public static javax.swing.JTextField ecaBAFTextField;
    public static javax.swing.JCheckBox ecaBafIncludedCheckBox;
    public static javax.swing.JComboBox ecaBafMeasurementComboBox;
    public static javax.swing.JCheckBox ecaSubjectToTariffCheckBox;
    public static javax.swing.JButton editCustomerButton;
    public static javax.swing.JCheckBox editQuoteDuplicateRateCheckBox;
    public static javax.swing.JCheckBox editQuoteIncludeSailingScheduleCheckBox;
    public static javax.swing.JCheckBox editQuoteIncludeShipperCommentsCheckBox;
    public static javax.swing.JButton editUpdateCustomerBookingsButton;
    public static javax.swing.JButton editUpdateCustomerInformationButton;
    public static javax.swing.JButton editUpdateCustomerQuotesButton;
    public static javax.swing.JLabel emailLabel;
    public static javax.swing.JLabel emailLabel1;
    public static javax.swing.JTextField existingCompanyAddress1TextField;
    public static javax.swing.JTextField existingCompanyAddress2TextField;
    public static javax.swing.JTextField existingCompanyCityTextField;
    public static javax.swing.JTextArea existingCompanyCommentsTextArea;
    public static javax.swing.JTextField existingCompanyCompanyTextField;
    public static javax.swing.JButton existingCompanyContractEpirationTextField;
    public static javax.swing.JTextField existingCompanyContractNumberTextField;
    public static javax.swing.JRadioButton existingCompanyContractYesRadioButton;
    public static javax.swing.JTextField existingCompanyCountryTextField;
    public static javax.swing.JTextField existingCompanyDBATextField;
    public static javax.swing.JTextField existingCompanyEmailTextField;
    public static javax.swing.JFormattedTextField existingCompanyFaxNumberTextField;
    public static javax.swing.JTextField existingCompanyFirstNameTextField;
    public static javax.swing.JTextField existingCompanyIDTextField;
    public static javax.swing.JTextField existingCompanyLastNameTextField;
    public static javax.swing.JTextField existingCompanyMainEmailTextField;
    public static javax.swing.JFormattedTextField existingCompanyMainPhoneTextField;
    public static javax.swing.JTextField existingCompanyMobilePhoneTextField;
    public static javax.swing.JTextField existingCompanyNameTextField;
    public static javax.swing.JRadioButton existingCompanyNoContractRadioButton;
    public static javax.swing.JTextField existingCompanyOTINumberTextField;
    public static javax.swing.JTextField existingCompanyOfficePhoneTextField;
    public static javax.swing.JFormattedTextField existingCompanySecondaryPhoneTextField;
    public static javax.swing.JTextField existingCompanyStateTextField;
    public static javax.swing.JTextField existingCompanyTitleTextField;
    public static javax.swing.JTextField existingCompanyZipTextField;
    public static javax.swing.JRadioButton existingCustomerBCORadioButton;
    public static javax.swing.JButton existingCustomerButton;
    public static javax.swing.JButton existingCustomerButton1;
    public static javax.swing.JRadioButton existingCustomerFreightForwarderRadioButton;
    public static javax.swing.JRadioButton existingCustomerNVOCCRadioButton;
    public static javax.swing.JRadioButton existingCustomerOtherRadioButton;
    public static javax.swing.JPanel existingCustomerPanel;
    public static javax.swing.JTextField expirationDateTextField;
    public static javax.swing.JButton exportResultsToExcelButton;
    public static javax.swing.JComboBox handlingInstructionsComboBox;
    public static javax.swing.JComboBox idTypeComboBox;
    public static javax.swing.JCheckBox includeSailingScheduleCheckBox;
    public static javax.swing.JCheckBox includeShipperCommentsCheckBox;
    public static javax.swing.JButton jButton1;
    public static javax.swing.JButton jButton2;
    public static javax.swing.JButton jButton6;
    public static javax.swing.JButton jButton7;
    public static javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel10;
    public static javax.swing.JLabel jLabel100;
    public static javax.swing.JLabel jLabel102;
    public static javax.swing.JLabel jLabel105;
    public static javax.swing.JLabel jLabel106;
    public static javax.swing.JLabel jLabel107;
    public static javax.swing.JLabel jLabel108;
    public static javax.swing.JLabel jLabel109;
    public static javax.swing.JLabel jLabel11;
    public static javax.swing.JLabel jLabel110;
    public static javax.swing.JLabel jLabel111;
    public static javax.swing.JLabel jLabel112;
    public static javax.swing.JLabel jLabel113;
    public static javax.swing.JLabel jLabel114;
    public static javax.swing.JLabel jLabel115;
    public static javax.swing.JLabel jLabel116;
    public static javax.swing.JLabel jLabel117;
    public static javax.swing.JLabel jLabel119;
    public static javax.swing.JLabel jLabel12;
    public static javax.swing.JLabel jLabel120;
    public static javax.swing.JLabel jLabel121;
    public static javax.swing.JLabel jLabel123;
    public static javax.swing.JLabel jLabel124;
    public static javax.swing.JLabel jLabel125;
    public static javax.swing.JLabel jLabel126;
    public static javax.swing.JLabel jLabel127;
    public static javax.swing.JLabel jLabel128;
    public static javax.swing.JLabel jLabel129;
    public static javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel130;
    public static javax.swing.JLabel jLabel131;
    public static javax.swing.JLabel jLabel132;
    public static javax.swing.JLabel jLabel133;
    public static javax.swing.JLabel jLabel134;
    public static javax.swing.JLabel jLabel135;
    public static javax.swing.JLabel jLabel136;
    public static javax.swing.JLabel jLabel137;
    public static javax.swing.JLabel jLabel138;
    public static javax.swing.JLabel jLabel139;
    public static javax.swing.JLabel jLabel14;
    public static javax.swing.JLabel jLabel140;
    public static javax.swing.JLabel jLabel143;
    public static javax.swing.JLabel jLabel144;
    public static javax.swing.JLabel jLabel145;
    public static javax.swing.JLabel jLabel146;
    public static javax.swing.JLabel jLabel147;
    public static javax.swing.JLabel jLabel15;
    public static javax.swing.JLabel jLabel152;
    public static javax.swing.JLabel jLabel153;
    public static javax.swing.JLabel jLabel154;
    public static javax.swing.JLabel jLabel155;
    public static javax.swing.JLabel jLabel156;
    public static javax.swing.JLabel jLabel157;
    public static javax.swing.JLabel jLabel16;
    public static javax.swing.JLabel jLabel160;
    public static javax.swing.JLabel jLabel161;
    public static javax.swing.JLabel jLabel162;
    public static javax.swing.JLabel jLabel163;
    public static javax.swing.JLabel jLabel164;
    public static javax.swing.JLabel jLabel167;
    public static javax.swing.JLabel jLabel168;
    public static javax.swing.JLabel jLabel17;
    public static javax.swing.JLabel jLabel18;
    public static javax.swing.JLabel jLabel19;
    public static javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel jLabel20;
    public static javax.swing.JLabel jLabel21;
    public static javax.swing.JLabel jLabel22;
    public static javax.swing.JLabel jLabel23;
    public static javax.swing.JLabel jLabel24;
    public static javax.swing.JLabel jLabel25;
    public static javax.swing.JLabel jLabel26;
    public static javax.swing.JLabel jLabel27;
    public static javax.swing.JLabel jLabel28;
    public static javax.swing.JLabel jLabel29;
    public static javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel jLabel30;
    public static javax.swing.JLabel jLabel31;
    public static javax.swing.JLabel jLabel32;
    public static javax.swing.JLabel jLabel33;
    public static javax.swing.JLabel jLabel34;
    public static javax.swing.JLabel jLabel35;
    public static javax.swing.JLabel jLabel36;
    public static javax.swing.JLabel jLabel37;
    public static javax.swing.JLabel jLabel38;
    public static javax.swing.JLabel jLabel39;
    public static javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel40;
    public static javax.swing.JLabel jLabel41;
    public static javax.swing.JLabel jLabel42;
    public static javax.swing.JLabel jLabel43;
    public static javax.swing.JLabel jLabel44;
    public static javax.swing.JLabel jLabel45;
    public static javax.swing.JLabel jLabel46;
    public static javax.swing.JLabel jLabel47;
    public static javax.swing.JLabel jLabel48;
    public static javax.swing.JLabel jLabel49;
    public static javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel50;
    public static javax.swing.JLabel jLabel51;
    public static javax.swing.JLabel jLabel52;
    public static javax.swing.JLabel jLabel53;
    public static javax.swing.JLabel jLabel54;
    public static javax.swing.JLabel jLabel55;
    public static javax.swing.JLabel jLabel56;
    public static javax.swing.JLabel jLabel57;
    public static javax.swing.JLabel jLabel58;
    public static javax.swing.JLabel jLabel59;
    public static javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel60;
    public static javax.swing.JLabel jLabel61;
    public static javax.swing.JLabel jLabel62;
    public static javax.swing.JLabel jLabel63;
    public static javax.swing.JLabel jLabel64;
    public static javax.swing.JLabel jLabel65;
    public static javax.swing.JLabel jLabel66;
    public static javax.swing.JLabel jLabel67;
    public static javax.swing.JLabel jLabel68;
    public static javax.swing.JLabel jLabel69;
    public static javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel70;
    public static javax.swing.JLabel jLabel71;
    public static javax.swing.JLabel jLabel72;
    public static javax.swing.JLabel jLabel73;
    public static javax.swing.JLabel jLabel74;
    public static javax.swing.JLabel jLabel75;
    public static javax.swing.JLabel jLabel76;
    public static javax.swing.JLabel jLabel77;
    public static javax.swing.JLabel jLabel78;
    public static javax.swing.JLabel jLabel79;
    public static javax.swing.JLabel jLabel8;
    public static javax.swing.JLabel jLabel80;
    public static javax.swing.JLabel jLabel81;
    public static javax.swing.JLabel jLabel82;
    public static javax.swing.JLabel jLabel83;
    public static javax.swing.JLabel jLabel84;
    public static javax.swing.JLabel jLabel85;
    public static javax.swing.JLabel jLabel86;
    public static javax.swing.JLabel jLabel87;
    public static javax.swing.JLabel jLabel88;
    public static javax.swing.JLabel jLabel89;
    public static javax.swing.JLabel jLabel9;
    public static javax.swing.JLabel jLabel90;
    public static javax.swing.JLabel jLabel91;
    public static javax.swing.JLabel jLabel92;
    public static javax.swing.JLabel jLabel93;
    public static javax.swing.JLabel jLabel94;
    public static javax.swing.JLabel jLabel95;
    public static javax.swing.JLabel jLabel96;
    public static javax.swing.JLabel jLabel97;
    public static javax.swing.JLabel jLabel98;
    public static javax.swing.JLabel jLabel99;
    public static javax.swing.JMenu jMenu3;
    public static javax.swing.JMenu jMenu4;
    public static javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JMenuItem jMenuItem2;
    public static javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel10;
    public static javax.swing.JPanel jPanel11;
    public static javax.swing.JPanel jPanel12;
    public static javax.swing.JPanel jPanel13;
    public static javax.swing.JPanel jPanel2;
    public static javax.swing.JPanel jPanel3;
    public static javax.swing.JPanel jPanel4;
    public static javax.swing.JPanel jPanel5;
    public static javax.swing.JPanel jPanel6;
    public static javax.swing.JPanel jPanel7;
    public static javax.swing.JPanel jPanel8;
    public static javax.swing.JPanel jPanel9;
    public static javax.swing.JPopupMenu jPopupMenu1;
    public static javax.swing.JRadioButton jRadioButton1;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JScrollPane jScrollPane10;
    public static javax.swing.JScrollPane jScrollPane11;
    public static javax.swing.JScrollPane jScrollPane12;
    public static javax.swing.JScrollPane jScrollPane13;
    public static javax.swing.JScrollPane jScrollPane14;
    public static javax.swing.JScrollPane jScrollPane15;
    public static javax.swing.JScrollPane jScrollPane17;
    public static javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JScrollPane jScrollPane5;
    public static javax.swing.JScrollPane jScrollPane6;
    public static javax.swing.JScrollPane jScrollPane7;
    public static javax.swing.JScrollPane jScrollPane8;
    public static javax.swing.JScrollPane jScrollPane9;
    public static javax.swing.JSeparator jSeparator1;
    public static javax.swing.JSeparator jSeparator10;
    public static javax.swing.JSeparator jSeparator11;
    public static javax.swing.JSeparator jSeparator12;
    public static javax.swing.JSeparator jSeparator13;
    public static javax.swing.JSeparator jSeparator14;
    public static javax.swing.JSeparator jSeparator15;
    public static javax.swing.JSeparator jSeparator16;
    public static javax.swing.JSeparator jSeparator17;
    public static javax.swing.JSeparator jSeparator18;
    public static javax.swing.JSeparator jSeparator19;
    public static javax.swing.JSeparator jSeparator2;
    public static javax.swing.JSeparator jSeparator20;
    public static javax.swing.JSeparator jSeparator21;
    public static javax.swing.JSeparator jSeparator22;
    public static javax.swing.JSeparator jSeparator24;
    public static javax.swing.JSeparator jSeparator3;
    public static javax.swing.JSeparator jSeparator4;
    public static javax.swing.JSeparator jSeparator5;
    public static javax.swing.JSeparator jSeparator6;
    public static javax.swing.JSeparator jSeparator7;
    public static javax.swing.JSeparator jSeparator8;
    public static javax.swing.JSeparator jSeparator9;
    public static javax.swing.JSlider jSlider1;
    public static javax.swing.JTextField kkluNumberTextField;
    public static javax.swing.JLabel lastUpdatedByLabel;
    public static javax.swing.JCheckBox mafiMinimumCheckBox;
    public static javax.swing.JTextField mafiMinimumTextField;
    public static javax.swing.JPanel mainPanel;
    public static javax.swing.JLabel mobilePhoneLabel;
    public static javax.swing.JTextField newCustomerAddress1;
    public static javax.swing.JTextField newCustomerAddress2;
    public static javax.swing.JRadioButton newCustomerBCORadioButton;
    public static javax.swing.JButton newCustomerButton;
    public static javax.swing.JButton newCustomerButton1;
    public static javax.swing.JTextField newCustomerCity;
    public static javax.swing.JTextArea newCustomerComments;
    public static javax.swing.JTextField newCustomerCompanyEmailTextField;
    public static javax.swing.JTextField newCustomerCompanyTextField;
    public static javax.swing.JButton newCustomerContractExpirationDateTextField;
    public static javax.swing.JTextField newCustomerContractNumberTextField;
    public static javax.swing.JRadioButton newCustomerContractYesRadioButton;
    public static javax.swing.JTextField newCustomerCountry;
    public static javax.swing.JTextField newCustomerDBATextField;
    public static javax.swing.JTextField newCustomerEmailTextField;
    public static javax.swing.JFormattedTextField newCustomerFaxNumber;
    public static javax.swing.JTextField newCustomerFirstName;
    public static javax.swing.JRadioButton newCustomerFreightForwarderRadioButton;
    public static javax.swing.JTextField newCustomerLastName;
    public static javax.swing.JFormattedTextField newCustomerMainPhoneTextField;
    public static javax.swing.JMenu newCustomerMenuItem;
    public static javax.swing.JTextField newCustomerMobilePhoneTextField;
    public static javax.swing.JRadioButton newCustomerNVOCCRadioButton;
    public static javax.swing.JRadioButton newCustomerNoContractRadioButton;
    public static javax.swing.JTextField newCustomerOTINumberTextField;
    public static javax.swing.JTextField newCustomerOfficePhone;
    public static javax.swing.JRadioButton newCustomerOtherRadioButton;
    public static javax.swing.JPanel newCustomerPanel;
    public static javax.swing.JFormattedTextField newCustomerSecondaryPhoneNumber;
    public static javax.swing.JTextField newCustomerState;
    public static javax.swing.JTextField newCustomerTitleTextField;
    public static javax.swing.JTextField newCustomerZipCode;
    public static javax.swing.JButton newFilingCompleteButton;
    public static javax.swing.JButton newFilingCompleteButton1;
    public static javax.swing.JCheckBox newQuoteAccessoriesCheckBox;
    public static javax.swing.JButton newQuoteAddRow;
    public static javax.swing.JCheckBox newQuoteBookedCheckBox;
    public static javax.swing.JButton newQuoteButton;
    public static javax.swing.JLabel newQuoteCustomerNameLabel;
    public static javax.swing.JCheckBox newQuoteDenialCheckBox;
    public static javax.swing.JLabel newQuoteLabel;
    public static javax.swing.JMenuItem newQuoteMenuItem;
    public static javax.swing.JPanel newQuotePanel;
    public static javax.swing.JTable newQuoteSailingScheduleTable;
    public static javax.swing.JLabel officeLocationLabel;
    public static javax.swing.JLabel officeLocationLabel1;
    public static javax.swing.JLabel officePhoneLabel;
    public static javax.swing.JComboBox oftMeasurementComboBox;
    public static javax.swing.JTextField oftTextField;
    public static javax.swing.JLabel outstandingLabel;
    public static javax.swing.JTable outstandingQuotesTable;
    public static javax.swing.JCheckBox pBafIncludedCheckBox;
    public static javax.swing.JTextField pBafTextField;
    public static javax.swing.JTextField pBookingNumberTextField;
    public static javax.swing.JTextArea pCommentsTextArea;
    public static javax.swing.JComboBox pCommodityClassComboBox;
    public static javax.swing.JTextField pCommodityDescriptionTextField;
    public static javax.swing.JComboBox pDocFeeComboBox;
    public static javax.swing.JCheckBox pDocFeeIncludedCheckBox;
    public static javax.swing.JComboBox pEcaComboBox;
    public static javax.swing.JCheckBox pEcaIncludedCheckBox;
    public static javax.swing.JTextField pEcaTextField;
    public static javax.swing.JComboBox pHandlingInstructions;
    public static javax.swing.JTextField pIDTextField;
    public static javax.swing.JComboBox pOftComboBox;
    public static javax.swing.JTextField pOftTextField;
    public static javax.swing.JTextField pPodTextField;
    public static javax.swing.JTextField pPolTextField;
    public static javax.swing.JButton pQuoteIDButton;
    public static javax.swing.JTextField pQuoteIDTextField;
    public static javax.swing.JTextField pQuoteNumberTextField;
    public static javax.swing.JComboBox pThcComboBox;
    public static javax.swing.JCheckBox pThcIncludedCheckBox;
    public static javax.swing.JTextField pThcTextField;
    public static javax.swing.JCheckBox pWarRiskCheckBox;
    public static javax.swing.JComboBox pWfgComboBox;
    public static javax.swing.JCheckBox pWfgIncludedCheckBox;
    public static javax.swing.JTextField pWfgTextField;
    public static javax.swing.JTable packingListTable;
    public static javax.swing.JMenuItem pasteMenuItem;
    public static javax.swing.JTextField pcCityTextField;
    public static javax.swing.JTextField pcCountryTextField;
    public static javax.swing.JTextField pcOfficeAddressTextField;
    public static javax.swing.JTextField pcStateTextField;
    public static javax.swing.JTextField pcSuiteTextField;
    public static javax.swing.JTextField pcZipTextField;
    public static javax.swing.JLabel phoneLabel1;
    public static javax.swing.JLabel phoneLabel3;
    public static javax.swing.JLabel phoneLabel4;
    public static javax.swing.JTextField podTextField;
    public static javax.swing.JTextField polTextField;
    public static javax.swing.JButton publishingCenterButton;
    public static javax.swing.JPanel publishingCenterPanel;
    public static javax.swing.JTextField queryNameTextField;
    public static javax.swing.JLabel queryTitleLabel;
    public static javax.swing.JLabel quoteCreatedLabel;
    public static javax.swing.JCheckBox quoteFeedbackCheckBox;
    public static javax.swing.JComboBox quoteFeedbackComboBox;
    public static javax.swing.JTextField quoteFeedbackTextField;
    public static javax.swing.JLabel quoteLastUpdatedLabel;
    public static javax.swing.JLabel regionLabel1;
    public static javax.swing.JLabel regionLabel3;
    public static javax.swing.JLabel regionLabel4;
    public static javax.swing.JTable requireAttentionTable;
    public static javax.swing.JMenuItem salesCenterMenuItem;
    public static javax.swing.JLabel salesRegionLabel;
    public static javax.swing.JButton saveChangesPublishingPDFButton;
    public static javax.swing.JButton savedSearchesButton;
    public static javax.swing.JButton searchCenterButton;
    public static javax.swing.JButton searchExistingCustomersButton;
    public static javax.swing.JButton searchExistingCustomersButton1;
    public static javax.swing.JPanel searchPanel;
    public static javax.swing.JTable searchResultsTable;
    public static javax.swing.JButton selectOustandingQuoteButton;
    public static javax.swing.JButton selectOutstandingQuoteRequireAttentionButton;
    public static javax.swing.JTextArea shipperCommentsTextArea;
    public static javax.swing.JCheckBox spotRateCheckBox;
    public static javax.swing.JCheckBox storageIncludedCheckBox;
    public static javax.swing.JCheckBox storageSubjectToTariffCheckBox;
    public static javax.swing.JTextField storageTextField;
    public static javax.swing.JComboBox storageUnitComboBox;
    public static javax.swing.JButton submitNewCustomerInformation;
    public static javax.swing.JButton submitNewQuote;
    public static javax.swing.JButton submitToPublishingPDFButton;
    public static javax.swing.JCheckBox thcAttached;
    public static javax.swing.JCheckBox thcIncludedCheckBox;
    public static javax.swing.JComboBox thcMeasurementComboBox;
    public static javax.swing.JCheckBox thcSubjectToTariffCheckBox;
    public static javax.swing.JTextField thcTextField;
    public static javax.swing.JLabel titleLabel;
    public static javax.swing.JLabel titleLabel1;
    public static javax.swing.JLabel titleLabel3;
    public static javax.swing.JFormattedTextField tliNumberTextField;
    public static javax.swing.JLabel totalBookingsCYTDLabel;
    public static javax.swing.JLabel totalQuotesCYTDLabel;
    public static javax.swing.JLabel totalQuotesLabel;
    public static javax.swing.JLabel totalQuotesWeekLabel;
    public static javax.swing.JComboBox tradeLaneComboBox;
    public static javax.swing.JTextField tshp1TextField;
    public static javax.swing.JTextField tshp2TextField;
    public static javax.swing.JTextField updateAlphaNumeralTextField;
    public static javax.swing.JTextField updateBAFTextField;
    public static javax.swing.JCheckBox updateBafIncludedCheckBox;
    public static javax.swing.JCheckBox updateBookedCheckBox;
    public static javax.swing.JButton updateCancelButton;
    public static javax.swing.JTextArea updateCommentsTextArea;
    public static javax.swing.JComboBox updateCommodityClassComboBox;
    public static javax.swing.JTextField updateCommodityDescriptionTextField;
    public static javax.swing.JTextField updateContactEmailTextField;
    public static javax.swing.JTextField updateContactNameTextField;
    public static javax.swing.JCheckBox updateContractRateCheckBox;
    public static javax.swing.JCheckBox updateDeclineCheckBox;
    public static javax.swing.JComboBox updateDeclineComboBox;
    public static javax.swing.JCheckBox updateDocFeeIncludedCheckBox;
    public static javax.swing.JComboBox updateDocumentationFeeComboBox;
    public static javax.swing.JTextField updateEcaBafTextField;
    public static javax.swing.JComboBox updateEcaComboBox;
    public static javax.swing.JCheckBox updateEcaIncludedCheckBox;
    public static javax.swing.JTextField updateEditMAFIMinimumTextField;
    public static javax.swing.JTable updateEditPackingListTable;
    public static javax.swing.JButton updateEditQuoteAddRowButton;
    public static javax.swing.JTextField updateEditQuoteBookingNumberTextField;
    public static javax.swing.JButton updateEditQuoteButton;
    public static javax.swing.JButton updateEditQuoteButton1;
    public static javax.swing.JLabel updateEditQuoteCustomerNameLabel;
    public static javax.swing.JLabel updateEditQuoteCustomerNameLabel1;
    public static javax.swing.JLabel updateEditQuoteCustomerNameLabel2;
    public static javax.swing.JLabel updateEditQuoteCustomerNameLabel3;
    public static javax.swing.JLabel updateEditQuoteCustomerNameLabel4;
    public static javax.swing.JLabel updateEditQuoteCustomerNameLabel5;
    public static javax.swing.JPanel updateEditQuotePanel;
    public static javax.swing.JButton updateEditSailingScheduleAddRowButton;
    public static javax.swing.JTable updateEditSailingScheduleTable;
    public static javax.swing.JComboBox updateHandlingInstructionsComboBox;
    public static javax.swing.JTextField updateOFTTextField;
    public static javax.swing.JComboBox updateOftUnitComboBox;
    public static javax.swing.JTextField updatePCAddressTextfield;
    public static javax.swing.JTextField updatePCCityTextField;
    public static javax.swing.JTextField updatePCCountryTextField;
    public static javax.swing.JTextField updatePCStateTextField;
    public static javax.swing.JTextField updatePCSuiteTextField;
    public static javax.swing.JTextField updatePCZipTextField;
    public static javax.swing.JTextField updatePODTextField;
    public static javax.swing.JTextField updatePOLTextField;
    public static javax.swing.JCheckBox updateQuoteAccessoriesCheckBox;
    public static javax.swing.JButton updateQuoteIDSearchButton;
    public static javax.swing.JTextField updateQuoteIDTextArea;
    public static javax.swing.JCheckBox updateQuoteMAFIMinimumCheckBox;
    public static javax.swing.JTextArea updateShipperCommentsTextArea;
    public static javax.swing.JCheckBox updateSpotRateCheckBox;
    public static javax.swing.JTextField updateTHCTextField;
    public static javax.swing.JCheckBox updateThcAttachedCheckBox;
    public static javax.swing.JComboBox updateThcComboBox;
    public static javax.swing.JCheckBox updateThcIncludedCheckBox;
    public static javax.swing.JComboBox updateTradeLane;
    public static javax.swing.JTextField updateTshp1TextField;
    public static javax.swing.JTextField updateTshp2TextField;
    public static javax.swing.JCheckBox updateWarRiskCheckBox;
    public static javax.swing.JCheckBox updateWfgAttachedCheckBox;
    public static javax.swing.JComboBox updateWfgComboBox;
    public static javax.swing.JCheckBox updateWfgIncludedCheckBox;
    public static javax.swing.JTextField updateWfgTextField;
    public static javax.swing.JLabel userIDLabel;
    public static javax.swing.JLabel userInformationLabel;
    public static javax.swing.JPanel userInformationPanel;
    public static javax.swing.JLabel usernameLabel;
    public static javax.swing.JLabel usernameLabel1;
    public static javax.swing.JLabel usernameLabel3;
    public static javax.swing.JLabel usernameLabel4;
    public static javax.swing.JLabel usernameLabel5;
    public static javax.swing.JButton validityFromButton;
    public static javax.swing.JButton validityToButton;
    public static javax.swing.JCheckBox warRiskCheckBox;
    public static javax.swing.JCheckBox wfgAttached;
    public static javax.swing.JCheckBox wfgIncludedCheckBox;
    public static javax.swing.JComboBox wfgMeasurementComboBox;
    public static javax.swing.JCheckBox wfgSubjectToTariffCheckBox;
    public static javax.swing.JTextField wfgTextField;
    // End of variables declaration//GEN-END:variables

}