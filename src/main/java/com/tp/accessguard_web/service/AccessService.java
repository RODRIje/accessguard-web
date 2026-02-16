package com.tp.accessguard_web.service;

import com.tp.accessguard_web.dto.AccessCheckRequest;
import com.tp.accessguard_web.dto.AccessCheckResponse;
import com.tp.accessguard_web.dto.AccessEventResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessService {

    AccessCheckResponse checkAccess(AccessCheckRequest request);

    List<AccessEventResponse> getEventsByPerson(String badgeId);
}
