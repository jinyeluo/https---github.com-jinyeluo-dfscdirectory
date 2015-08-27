//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.luo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

public class Record {
    public static final int INT_10 = 10;
    public static final int INT_7 = 7;
    private String last;
    private String first;
    private String usfs;
    private String phone;
    private String cellPhone;
    private String email;
    private Address address;
    private String membership;

    public Record(Row aRow) {
        short lastCellNum = aRow.getLastCellNum();
        int pos = 0;
        this.last = this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK));
        this.first = this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK));
        this.usfs = this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK));
        this.phone = this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK));
        this.cellPhone = this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK));
        this.email = this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK));
        this.address = new Address();
        this.address.setAddr(this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK)));
        if (pos <= lastCellNum) {
            this.address.setCity(this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK)));
        }

        if (pos <= lastCellNum) {
            this.address.setState(this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK)));
        }

        if (pos <= lastCellNum) {
            this.address.setZip(this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK)));
        }

        if (pos <= lastCellNum) {
            this.membership = this.getValue(aRow.getCell(pos++, Row.CREATE_NULL_AS_BLANK));
        }

        this.phone = this.normalizePhone(this.phone);
        this.cellPhone = this.normalizePhone(this.cellPhone);
    }

    private String getValue(Cell aCell) {
        return this.getStr(aCell);
    }

    private String normalizePhone(String aCellPhone) {
        StringBuilder builder = new StringBuilder(aCellPhone);
        this.removeChar(builder, " ");
        this.removeChar(builder, "_");
        if (builder.length() == Record.INT_10) {
            builder.insert(3, "-");
            builder.insert(INT_7, "-");
        }

        return builder.toString();
    }

    private void removeChar(StringBuilder aBuilder, String cha) {
        for (int pos = aBuilder.indexOf(cha); pos > -1; pos = aBuilder.indexOf(cha)) {
            aBuilder.delete(pos, pos + 1);
        }

    }

    private String getCellValue(Iterator<Cell> aCellIterator) {
        return aCellIterator.hasNext() ? this.getStr((Cell) aCellIterator.next()) : "";
    }

    String getStr(Cell aCell) {
        return aCell.getCellType() == 0 ? Long.toString((long) aCell.getNumericCellValue())
            : aCell.getStringCellValue();
    }

    public String getLast() {
        return this.last;
    }

    public String getFirst() {
        return this.first;
    }

    public String getUsfs() {
        return this.usfs;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getCellPhone() {
        return this.cellPhone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddr() {
        return this.address.getAddr();
    }

    public String getCity() {
        return this.address.getCity();
    }

    public String getState() {
        return this.address.getState();
    }

    public String getZip() {
        return this.address.getZip();
    }

    public String getMembership() {
        return this.membership;
    }

    public String toString() {
        return "Record{last=\'" + this.last + '\'' + ", first=\'" + this.first + '\'' + ", usfs=\'" + this.usfs + '\''
            + ", phone=\'" + this.phone + '\'' + ", cellPhone=\'" + this.cellPhone + '\'' + ", email=\'" + this.email
            + '\'' + ", address=" + this.address + ", membership=\'" + this.membership + '\'' + '}';
    }

    public Address getAddress() {
        return this.address;
    }
}
