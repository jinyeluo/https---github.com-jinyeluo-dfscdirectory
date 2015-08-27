package com.luo;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DfscDirectory {
    public static final int INT_100 = 100;
    public static final int FONT_SIZE = 11;
    public static final long LONG_360 = 360L;
    public static final int FONT_SIZE_11 = 11;
    private static XWPFDocument doc;
    private static Book book;

    public DfscDirectory() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java -jar dfsc-1.0.jar [Excel input file name] [Word output file name]");
            System.out.println("For example:java -jar dfsc-1.0.jar DFSC-2014.xls dfsc.docx");
        } else if (checkFile(args[0], args[1])) {
            DfscDirectory dfscDirectory = new DfscDirectory();
            readExcel(args[0]);
            dfscDirectory.printWord(args[1]);
        }

    }

    private static boolean checkFile(String input, String output) {
        if (!(new File(input)).exists()) {
            System.err.println("input file not exist: " + input);
            return false;
        } else if ((new File(output)).exists()) {
            System.err.print("output file already exist: " + output);
            return false;
        } else {
            return true;
        }
    }

    public void printWord(String outFileName) throws Exception {
        doc = new XWPFDocument();
        XWPFTable table = doc.createTable(1, 3);
        table.setInsideHBorder(XWPFBorderType.NONE, 1, 1, "1C7331");
        table.setInsideVBorder(XWPFBorderType.NONE, 1, 1, "1C7331");
        List families = book.getFamilies();

        for (int out = 0; out < families.size(); ++out) {
            Family family = (Family) families.get(out);
            List firstNames = family.getFirstNames();
            byte startingNamePos = 0;
            if (out == 0) {
                this.printNameRow(table.getRow(0), (String) firstNames.get(0), family.getLastName(),
                    (String) family.getUsfss().get(0), (String) family.getMemberships().get(0));
                startingNamePos = 1;
            }

            for (int j = startingNamePos; j < firstNames.size(); ++j) {
                this.printNameRow(table.createRow(), (String) firstNames.get(j), family.getLastName(),
                    (String) family.getUsfss().get(j), (String) family.getMemberships().get(j));
            }

            this.printAddr(table, family);
            this.printPhones(table, family);
            this.printEmail(table, family);
            table.createRow();
        }

        FileOutputStream var9 = new FileOutputStream(outFileName);
        doc.write(var9);
        var9.close();
    }

    private void printEmail(XWPFTable aTable, Family aFamily) {
        if (!aFamily.getEmails().isEmpty()) {
            XWPFTableRow row = aTable.createRow();

            for (int i = 0; i < aFamily.getEmails().size() && i < 3; ++i) {
                String email = (String) aFamily.getEmails().get(i);
                XWPFParagraph paragraph = (XWPFParagraph) row.getCell(i).getParagraphs().get(0);
                XWPFRun run = paragraph.createRun();
                run.setText("Email:" + email);
                if (i == 0) {
                    this.setFormat(row, paragraph, row.getCell(i), run);
                } else {
                    this.setFormat(paragraph, row.getCell(i), run);
                }
            }
        }

    }

    private void printPhones(XWPFTable aTable, Family aFamily) {
        if (aFamily.getPhones().size() > 0 || aFamily.getCellPhones().size() > 0) {
            ArrayList phones = new ArrayList();
            Iterator row = aFamily.getPhones().iterator();

            String i;
            while (row.hasNext()) {
                i = (String) row.next();
                phones.add("Home:" + i);
            }

            row = aFamily.getCellPhones().iterator();

            while (row.hasNext()) {
                i = (String) row.next();
                phones.add("Cell:" + i);
            }

            XWPFTableRow var8 = aTable.createRow();

            for (int var9 = 0; var9 < phones.size() && var9 < 3; ++var9) {
                XWPFParagraph paragraph = (XWPFParagraph) var8.getCell(var9).getParagraphs().get(0);
                XWPFRun run = paragraph.createRun();
                run.setText((String) phones.get(var9));
                if (var9 == 0) {
                    this.setFormat(var8, paragraph, var8.getCell(var9), run);
                } else {
                    this.setFormat(paragraph, var8.getCell(var9), run);
                }
            }
        }

    }

    private void printAddr(XWPFTable aTable, Family aFamily) {
        Address address = aFamily.getAddress();
        XWPFTableRow row = aTable.createRow();
        XWPFParagraph paragraph = (XWPFParagraph) row.getCell(0).getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        run.setText(address.getAddr());
        this.setFormat(row, paragraph, row.getCell(0), run);
        XWPFTableRow row2 = aTable.createRow();
        XWPFParagraph paragraph2 = (XWPFParagraph) row2.getCell(0).getParagraphs().get(0);
        XWPFRun run2 = paragraph2.createRun();
        run2.setText(address.getCity() + ", " + address.getState() + " " + address.getZip());
        this.setFormat(row2, paragraph, row2.getCell(0), run2);
    }

    private void setFormat(XWPFTableRow aRow, XWPFParagraph aParagraph, XWPFTableCell cell, XWPFRun aRun) {
        CTTrPr trPr = aRow.getCtRow().getTrPr();
        if (trPr == null) {
            trPr = aRow.getCtRow().addNewTrPr();
        }

        CTHeight ht = trPr.addNewTrHeight();
        ht.setVal(BigInteger.valueOf(LONG_360));
        this.setFormat(aParagraph, cell, aRun);
    }

    private void setFormat(XWPFParagraph aParagraph, XWPFTableCell cell, XWPFRun aRun) {
        aParagraph.setBorderTop(Borders.NONE);
        aParagraph.setSpacingAfter(0);
        aParagraph.setSpacingAfterLines(0);
        aParagraph.setSpacingBefore(0);
        aParagraph.setSpacingBeforeLines(0);
        cell.setVerticalAlignment(XWPFVertAlign.CENTER);
        aRun.setFontFamily("Times New Roman");
        aRun.setFontSize(FONT_SIZE_11);
        aRun.setTextPosition(0);
    }

    private void printNameRow(XWPFTableRow aRow, String first, String last, String usfs, String memberShip) {
        XWPFTableCell cell = aRow.getCell(0);
        XWPFParagraph p0 = (XWPFParagraph) cell.getParagraphs().get(0);
        XWPFRun r0 = p0.createRun();
        r0.setBold(true);
        r0.setText(first + ", " + last);
        this.setFormat(aRow, p0, cell, r0);
        r0.setTextPosition(0);
        XWPFParagraph p1 = (XWPFParagraph) aRow.getCell(1).getParagraphs().get(0);
        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setText("#" + usfs);
        this.setFormat(p1, aRow.getCell(1), r1);
        XWPFParagraph p2 = (XWPFParagraph) aRow.getCell(2).getParagraphs().get(0);
        XWPFRun r2 = p2.createRun();
        if (memberShip.startsWith("see: ")) {
            r2.setItalic(true);
        } else {
            r2.setBold(true);
        }

        r2.setText(memberShip);
        this.setFormat(p2, aRow.getCell(2), r2);
    }

    static void readExcel(String fileName) throws IOException {
        FileInputStream file = new FileInputStream(new File(fileName));
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        workbook.setMissingCellPolicy(HSSFRow.RETURN_NULL_AND_BLANK);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator rowIterator = sheet.iterator();
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        book = new Book();

        while (rowIterator.hasNext()) {
            Row row = (Row) rowIterator.next();
            book.addRecord(new Record(row));
        }

        System.out.println("finish reading excel");
    }

    private static CTDecimalNumber createSpan(XWPFTableCell aCell) {
        CTTc ctTc = aCell.getCTTc();
        if (ctTc.getTcPr() == null) {
            ctTc.addNewTcPr();
        }

        return ctTc.getTcPr().getGridSpan() == null ? ctTc.getTcPr().addNewGridSpan() : ctTc.getTcPr().getGridSpan();
    }

    public void createSimpleTable() throws Exception {
        doc = new XWPFDocument();
        XWPFTable table = doc.createTable(3, 3);
        table.getRow(1).getCell(1).setText("EXAMPLE OF TABLE");
        XWPFParagraph p1 = (XWPFParagraph) table.getRow(0).getCell(0).getParagraphs().get(0);
        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setText("The quick brown fox");
        r1.setItalic(true);
        r1.setFontFamily("Courier");
        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        r1.setTextPosition(DfscDirectory.INT_100);
        table.getRow(2).getCell(2).setText("only text");
        FileOutputStream out = new FileOutputStream("simpleTable.docx");
        doc.write(out);
        out.close();
    }
}
