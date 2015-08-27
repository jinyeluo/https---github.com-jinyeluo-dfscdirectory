package com.luo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Book {
    private List<Family> families = new ArrayList();

    public Book() {
    }

    public List<Family> getFamilies() {
        return this.families;
    }

    public void addRecord(Record aRecord) {
        if (this.families.isEmpty()) {
            this.addNewFamily(aRecord);
        } else {
            Family lastFamily = this.families.get(this.families.size() - 1);
            if (lastFamily.isSameFamily(aRecord)) {
                lastFamily.addRecord(aRecord);
            } else {
                this.addNewFamily(aRecord);
            }
        }

    }

    private Family addNewFamily(Record aRecord) {
        ArrayList familyWithSameAddr = new ArrayList();
        Iterator newFamily = this.families.iterator();

        while (newFamily.hasNext()) {
            Family next = (Family) newFamily.next();
            if (next.getAddress().equals(aRecord.getAddress())) {
                next.addOtherLastName(aRecord.getLast());
                familyWithSameAddr.add(next);
            }
        }

        Family newFamily1 = new Family(aRecord.getLast(), aRecord.getAddress());
        newFamily1.addRecord(aRecord);
        Iterator iter = familyWithSameAddr.iterator();

        while (iter.hasNext()) {
            Family familyWithSameAddress = (Family) iter.next();
            newFamily1.addOtherLastName(familyWithSameAddress.getLastName());
        }

        this.families.add(newFamily1);
        return newFamily1;
    }
}
