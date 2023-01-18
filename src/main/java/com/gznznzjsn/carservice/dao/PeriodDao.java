package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.Specialization;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PeriodDao {

    void delete(Long periodId);

    Optional<Period> readBy(LocalDateTime arrivalTime, Specialization specialization, int totalDuration);

    void update(Period period);

    Optional<Period> read(Long periodId);

}
