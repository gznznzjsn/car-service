package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.Specialization;

import java.time.LocalDateTime;

public interface PeriodService {


    Period eraseAppropriate(LocalDateTime arrivalTime, Specialization specialization, int totalDuration);

    Period getBy(LocalDateTime arrivalTime, Specialization specialization, int totalDuration);

    void delete(Long periodId);

    void update(Period period);

    Period get(Long periodId);

}
