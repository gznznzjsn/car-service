package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PeriodDao {
    Optional<Period> erasePeriod(LocalDateTime arrivalTime, Specialization specialization, int totalDuration);
}
