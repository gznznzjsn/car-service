package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;

import java.time.LocalDateTime;

public interface PeriodService {
    Period eraseAppropriatePeriod(LocalDateTime arrivalTime, Specialization specialization, int totalDuration);
}
