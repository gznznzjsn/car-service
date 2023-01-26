package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.PeriodDao;
import com.gznznzjsn.carservice.domain.Employee;
import com.gznznzjsn.carservice.domain.Period;
import com.gznznzjsn.carservice.domain.Specialization;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.PeriodService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeriodServiceImplTest {

    @Mock
    PeriodDao periodDao;

    @InjectMocks
    PeriodServiceImpl periodService;

    @Test
    void eraseAppropriateNonExisting() {
        when(periodDao.findBy(LocalDateTime.MAX, Specialization.CLEANER, 3)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> periodService.eraseAppropriate(LocalDateTime.MAX, Specialization.CLEANER, 3));

        verify(periodDao).findBy(LocalDateTime.MAX, Specialization.CLEANER, 3);
        verify(periodDao, never()).delete(anyLong());
        verify(periodDao, never()).update(any());
    }

    @Test
    void eraseAppropriateFully() {
        Period period = Period.builder()
                .id(1L)
                .start(1)
                .end(4)
                .date(LocalDate.MAX)
                .employee(Employee.builder()
                        .id(1L)
                        .specialization(Specialization.CLEANER)
                        .name("employeeName")
                        .build())
                .build();
        when(periodDao.findBy(LocalDateTime.MAX, Specialization.CLEANER, 3)).thenReturn(Optional.of(period));

        Period returnedPeriod = periodService.eraseAppropriate(LocalDateTime.MAX, Specialization.CLEANER, 3);

        verify(periodDao).findBy(LocalDateTime.MAX, Specialization.CLEANER, 3);
        verify(periodDao).delete(1L);
        verify(periodDao, never()).update(any());
        assertEquals(period, returnedPeriod);
    }

    @Test
    void eraseAppropriatePartly() {
        PeriodService spyPS = spy(periodService);
        Period period = Period.builder()
                .id(1L)
                .start(1)
                .end(5)
                .date(LocalDate.MAX)
                .employee(Employee.builder()
                        .id(1L)
                        .specialization(Specialization.CLEANER)
                        .name("employeeName")
                        .build())
                .build();
        doReturn(null).when(spyPS).update(any());
        doReturn(period).when(spyPS).getBy(LocalDateTime.MAX, Specialization.CLEANER, 3);

        Period returnedPeriod = spyPS.eraseAppropriate(LocalDateTime.MAX, Specialization.CLEANER, 3);

        verify(spyPS).getBy(LocalDateTime.MAX, Specialization.CLEANER, 3);
        verify(periodDao, never()).delete(1L);
        verify(spyPS).update(Period.builder()
                .id(period.getId())
                .start(period.getStart() + 3)
                .build());
        period.setStart(4);
        assertEquals(period, returnedPeriod);
    }

    @Test
    void getNonExistingBy() {
        when(periodDao.findBy(LocalDateTime.MAX, Specialization.CLEANER, 3)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> periodService.getBy(LocalDateTime.MAX, Specialization.CLEANER, 3));

        verify(periodDao).findBy(LocalDateTime.MAX, Specialization.CLEANER, 3);
    }

    @Test
    void getExistingBy() {
        Period period = Period.builder()
                .id(1L)
                .start(1)
                .end(3)
                .date(LocalDate.MAX)
                .employee(Employee.builder()
                        .id(1L)
                        .specialization(Specialization.CLEANER)
                        .name("employeeName")
                        .build())
                .build();
        when(periodDao.findBy(LocalDateTime.MAX, Specialization.CLEANER, 3)).thenReturn(Optional.of(period));

        Period returnedPeriod = periodService.getBy(LocalDateTime.MAX, Specialization.CLEANER, 3);

        assertEquals(period, returnedPeriod);
        verify(periodDao).findBy(LocalDateTime.MAX, Specialization.CLEANER, 3);
    }

    @Test
    void delete() {
        periodService.delete(1L);

        verify(periodDao).delete(1L);
    }

    @Test
    void updateFull() {
        PeriodService spyPS = spy(periodService);
        Period givenPeriod = Period.builder()
                .id(1L)
                .start(1)
                .end(3)
                .date(LocalDate.MAX)
                .employee(Employee.builder()
                        .id(1L)
                        .specialization(Specialization.CLEANER)
                        .name("employeeName")
                        .build())
                .build();
        doReturn(Period.builder().id(1L).build()).when(spyPS).get(1L);

        Period returnedPeriod = spyPS.update(givenPeriod);

        assertNull(returnedPeriod.getDate());
        givenPeriod.setDate(null);
        assertEquals(givenPeriod, returnedPeriod);
        verify(spyPS).get(1L);
        verify(periodDao).update(any());
    }

    @Test
    void updateNothing() {
        PeriodService spyPS = spy(periodService);
        Period givenPeriod = Period.builder().id(1L).build();
        doReturn(Period.builder().id(1L).build()).when(spyPS).get(1L);

        Period returnedPeriod = spyPS.update(givenPeriod);

        assertEquals(givenPeriod, returnedPeriod);
        verify(spyPS).get(1L);
        verify(periodDao).update(any());
    }

    @Test
    void updateNonExisting() {
        PeriodService spyPS = spy(periodService);
        Period givenPeriod = Period.builder().id(1L).build();
        doThrow(ResourceNotFoundException.class).when(spyPS).get(1L);

        assertThrows(ResourceNotFoundException.class, () -> spyPS.update(givenPeriod));

        verify(spyPS).get(1L);
        verify(periodDao, never()).update(any());
    }

    @Test
    void getNonExisting() {
        when(periodDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> periodService.get(1L));

        verify(periodDao).findById(1L);
    }

    @Test
    void getExisting() {
        Period period = Period.builder()
                .id(1L)
                .start(1)
                .employee(Employee.builder()
                        .id(1L)
                        .name("employeeName")
                        .specialization(Specialization.CLEANER).build())
                .date(LocalDate.MAX)
                .end(3)
                .build();
        when(periodDao.findById(1L)).thenReturn(Optional.of(period));

        Period returnedPeriod = periodService.get(1L);

        verify(periodDao).findById(1L);
        assertEquals(period, returnedPeriod);
    }
}