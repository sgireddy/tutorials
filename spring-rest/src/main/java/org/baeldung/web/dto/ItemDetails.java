package org.baeldung.web.dto;


public class ItemDetails {
    private final String description;

    public ItemDetails(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
