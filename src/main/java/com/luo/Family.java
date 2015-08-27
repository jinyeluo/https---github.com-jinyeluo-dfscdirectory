//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.luo;

import java.util.ArrayList;
import java.util.List;

public class Family {
    private String lastName;
    private List<String> firstNames = new ArrayList();
    private Address address;
    private List<String> emails = new ArrayList();
    private List<String> phones = new ArrayList();
    private List<String> cellPhones = new ArrayList();
    private List<String> usfss = new ArrayList();
    private List<String> memberships = new ArrayList();

    public Family(String aLastName, Address aAddress) {
        this.lastName = aLastName;
        this.address = aAddress;
    }

    public void addRecord(Record aRecord) {
        this.firstNames.add(aRecord.getFirst());
        this.memberships.add(aRecord.getMembership());
        if (!this.isEmpty(aRecord.getEmail()) && !this.emails.contains(aRecord.getEmail())) {
            this.emails.add(aRecord.getEmail());
        }

        if (!this.isEmpty(aRecord.getPhone()) && !this.phones.contains(aRecord.getPhone())) {
            this.phones.add(aRecord.getPhone());
        }

        if (!this.isEmpty(aRecord.getCellPhone()) && !this.cellPhones.contains(aRecord.getCellPhone())) {
            this.cellPhones.add(aRecord.getCellPhone());
        }

        this.usfss.add(aRecord.getUsfs());
    }

    private boolean isEmpty(String aStr) {
        return aStr == null || aStr.trim().isEmpty();
    }

    public void addOtherLastName(String aOtherLastName) {
        for (int i = 0; i < this.memberships.size(); ++i) {
            String membership = (String) this.memberships.get(i);
            if (membership.isEmpty()) {
                this.memberships.set(i, "see: " + aOtherLastName);
                break;
            }
        }

    }

    public String getLastName() {
        return this.lastName;
    }

    public List<String> getFirstNames() {
        return this.firstNames;
    }

    public Address getAddress() {
        return this.address;
    }

    public List<String> getEmails() {
        return this.emails;
    }

    public List<String> getPhones() {
        return this.phones;
    }

    public List<String> getCellPhones() {
        return this.cellPhones;
    }

    public List<String> getUsfss() {
        return this.usfss;
    }

    public List<String> getMemberships() {
        return this.memberships;
    }

    public boolean isSameFamily(Record aRecord) {
        return aRecord.getLast().equals(this.lastName) && this.address.equals(aRecord.getAddress());
    }
}
