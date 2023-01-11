package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.PeriodDao;
import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.PeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {

    private final PeriodDao periodDao;

    @Override
    public Period eraseAppropriatePeriod(LocalDateTime arrivalTime, Specialization specialization, int totalDuration) {
        Optional<Period> optionalPeriod = periodDao.erasePeriod(arrivalTime, specialization, totalDuration);
        if (optionalPeriod.isEmpty()) {
            throw new ResourceNotFoundException("No free time periods for such parameters: arrival time = " + arrivalTime + ", specialization = " + specialization.name() + ", total assignment duration = " + totalDuration);
        }
        return optionalPeriod.get();
    }
}
