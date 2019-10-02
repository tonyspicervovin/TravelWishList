package com.tony.travelwishlist;

import java.text.DateFormat;
import java.util.Date;

public class Place {
    private String name;
    private Date dateCreated;
    private String reasonVisit;

    Place(String name, String reasonVisit) {
        this.name = name;
        this.dateCreated = new Date();
        this.reasonVisit = reasonVisit;
    }

    public String getName() {
        return name;
    }
    public String getReasonVisit() {
        return reasonVisit;
    }
    public String getDateCreated() {
        return DateFormat.getDateInstance().format(dateCreated);
    }
}
// place object, has attributes name, current date and reason for visiting