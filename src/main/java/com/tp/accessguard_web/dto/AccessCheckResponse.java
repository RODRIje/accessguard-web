package com.tp.accessguard_web.dto;

public class AccessCheckResponse {
    private boolean allow;
    private String reason;

    public AccessCheckResponse() {
    }

    public AccessCheckResponse(boolean allow, String reason) {
        this.allow = allow;
        this.reason = reason;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
