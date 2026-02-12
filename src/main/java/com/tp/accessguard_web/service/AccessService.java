package com.tp.accessguard_web.service;

import com.tp.accessguard_web.dto.AccessCheckResponse;

import java.time.LocalDateTime;

public interface AccessService {

    AccessCheckResponse checkAccess(String badgeId, String sectorCode, LocalDateTime ts);
}
