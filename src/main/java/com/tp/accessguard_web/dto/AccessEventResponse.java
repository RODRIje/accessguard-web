package com.tp.accessguard_web.dto;

import java.time.LocalDateTime;

public class AccessEventResponse {

    private String badgeId;
    private String sectorCode;
    private LocalDateTime timestamp;
    private String result;
    private String reason;

    public AccessEventResponse(String badgeId, String sectorCode, LocalDateTime timestamp, String result, String reason) {
        this.badgeId = badgeId;
        this.sectorCode = sectorCode;
        this.timestamp = timestamp;
        this.result = result;
        this.reason = reason;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
