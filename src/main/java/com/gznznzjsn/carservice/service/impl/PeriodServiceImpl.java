package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.PeriodDao;
import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.Specialization;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.PeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {

    private final PeriodDao periodDao;

    @Override
    @Transactional
    public Period eraseAppropriate(LocalDateTime arrivalTime, Specialization specialization, int totalDuration) {
        return periodDao.erase(arrivalTime, specialization, totalDuration)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No free time periods for such parameters: arrival time = " + arrivalTime + ", specialization = " + specialization.name() + ", total assignment duration = " + totalDuration)
                );

    }
}
