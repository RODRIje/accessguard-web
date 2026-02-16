package com.tp.accessguard_web.dto;

public class PersonResponseDTO {

    private Long id;
    private String name;
    private String lastName;
    private String badgeId;
    private String document;
    private String status;

    public PersonResponseDTO(Long id, String name, String lastName, String badgeId, String document, String status) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.badgeId = badgeId;
        this.document = document;
        this.status = status;
    }

    public PersonResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
