package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.PeriodDao;
import com.gznznzjsn.carservice.domain.Period;
import com.gznznzjsn.carservice.domain.Specialization;
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
        Period period = getBy(arrivalTime, specialization, totalDuration);
        if (period.getEnd() - period.getStart() == totalDuration) {
            delete(period.getId());
        } else {
            Period updatedPeriod = Period.builder()
                    .id(period.getId())
                    .start(period.getStart() + totalDuration)
                    .build();
            update(updatedPeriod);
        }
        period.setEnd(period.getStart() + totalDuration);
        return period;
    }

    @Override
    @Transactional(readOnly = true)
    public Period getBy(LocalDateTime arrivalTime, Specialization specialization, int totalDuration) {
        return periodDao.findBy(arrivalTime, specialization, totalDuration).orElseThrow(
                () -> new ResourceNotFoundException("No free time periods for such parameters: arrival time = " + arrivalTime + ", specialization = " + specialization.name() + ", total assignment duration = " + totalDuration)
        );
    }

    @Override
    @Transactional
    public void delete(Long periodId) {
        periodDao.delete(periodId);
    }

    @Override
    @Transactional
    public Period update(Period period) {
        Period periodFromRepository = get(period.getId());
        if (period.getEmployee() != null) {
            periodFromRepository.setEmployee(period.getEmployee());
        }
        if (period.getStart() != 0) {
            periodFromRepository.setStart(period.getStart());
        }
        if (period.getEnd() != 0) {
            periodFromRepository.setEnd(period.getEnd());
        }
        periodDao.update(periodFromRepository);
        return periodFromRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Period get(Long periodId) {
        return periodDao.findById(periodId).orElseThrow(
                () -> new ResourceNotFoundException("Periods with id = " + periodId + " not found!")
        );
    }

}
