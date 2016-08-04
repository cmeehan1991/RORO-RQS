package Excel;

// Original code thanks to: http://niravjavadeveloper.blogspot.com/2012/01/import-excel-data-into-jtable.html
import Sales.MainMenu;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Read more: http://niravjavadeveloper.blogspot.com/2012/01/import-excel-data-into-jtable.html#ixzz3yeTwKfYi
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cmeehan
 */
public class ExcelToJTable {
    private static final String USER_HOME_FOLDER = System.getProperty("user.home");
    
    static JTable table;
    static JScrollPane scroll;

    // Header is Vector contains table Column
    static Vector headers = new Vector();

    //Model is used to construct JTable
    static DefaultTableModel model = null;

    // data is Vector contains Data from Excel File
    static JFileChooser jChooser;
    static Vector data = new Vector();
    private static Vector dataTemp;
    private static final Vector columns = new Vector();

    /**
     * Creates new form excelToJTable
     */
    public ExcelToJTable() {
        jChooser = new JFileChooser(USER_HOME_FOLDER+"/Desktop/");
        
    }

    public void chooseData() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Microsoft Excel","xls","xlsx");
        jChooser.setFileFilter(filter);
        jChooser.showOpenDialog(null);
        
        File file = jChooser.getSelectedFile();
        if (!file.getName().endsWith("xls") && !file.getName().endsWith("xlsx")) { // If the file is not an Excel file
            JOptionPane.showMessageDialog(null, "Please only select Excel (.xls/.xlsx) files", "Error", JOptionPane.ERROR_MESSAGE);
        } else { //If the file is an excel file
            fillData(file);
            System.out.println(file);
            try {
                data.addElement(dataTemp);
                model = new DefaultTableModel(data, columns);
                MainMenu.packingListTable.setModel(model);
            } catch (Exception ex) {
                System.out.print(Arrays.toString(ex.getStackTrace()));
            }
        }
    }

    // Able to do xlsx thanks to @Redspart: http://stackoverflow.com/questions/24209492/adding-excel-information-to-jtable-not-creating-new-rows-xlsx-java
    static void fillData(File file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (row.getRowNum() == 0) {
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        columns.add(cell.getStringCellValue());
                    }
                } else {
                    dataTemp = new Vector();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch(cell.getCellType()){
                            case Cell.CELL_TYPE_STRING:
                                dataTemp.add(cell.getStringCellValue());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                dataTemp.add(cell.getNumericCellValue());
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                dataTemp.add("");
                                break;
                            default:
                                break;
                        }
                    }
                    data.add(dataTemp);
                    fs.close();
                }
            }

        } catch (Exception ex) {
            System.out.print("Cause: \n" + ex.getCause() + "\n Message: \n" + ex.getMessage() + "\n Stack Trace: \n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    /* void fillData(File file) {
     Workbook wb = null;
     try {
     try {
     wb = Workbook.getWorkbook(file);
     System.out.println(wb);
     } catch (IOException | BiffException ex) {
     System.out.println("Second try statement in fillData: " + ex.getMessage()+"\n"+Arrays.toString(ex.getStackTrace()));
     }
     Sheet sheet = wb.getSheet(0);
            
     headers.clear();
     for(int i = 0; i < sheet.getColumns(); i++){
     Cell cell1 = sheet.getCell(i, 0);
     headers.add(cell1.getContents());
     }
     data.clear();
     for(int j=1; j<sheet.getRows(); j++){
     Vector d = new Vector();
     for(int i=0; i<sheet.getColumns(); i++){
     Cell cell = sheet.getCell(i,j);
     d.add(cell.getContents());
     }
     d.add("\n");
     data.add(d);
     }
     } catch (Exception ex) {
     System.out.println("First try statement in fillData: " + ex.getMessage()+"\n"+Arrays.toString(ex.getStackTrace()));
     }
     }*/
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
            java.util.logging.Logger.getLogger(ExcelToJTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
