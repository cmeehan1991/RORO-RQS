/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputStyles;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

/**
 *
 * @author cmeehan
 */
public class Fonts {
     public static final Font LABEL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    public static final Font TABLE_HEADING_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.WHITE);
    public static final Font TEXT_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    public static final Font EMAIL_TEXT_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.UNDERLINE, BaseColor.BLUE);
    public static final Font ROW_LABEL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
    public static final Font SUBJECT_TO_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLDITALIC);
    public static final Font DISCLAIMER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLDITALIC);
    public static final Font FMC_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.UNDERLINE);
    public static final Font PL_LABEL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD);
}
