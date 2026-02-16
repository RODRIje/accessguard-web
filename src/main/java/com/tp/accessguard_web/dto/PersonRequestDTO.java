package com.tp.accessguard_web.dto;

public class PersonRequestDTO {

    private String name;
    private String lastName;
    private String badgeId;
    private String document;

    public PersonRequestDTO(String name, String lastName, String badgeId, String document) {
        this.name = name;
        this.lastName = lastName;
        this.badgeId = badgeId;
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
