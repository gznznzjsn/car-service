package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.UserDao;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.domain.exception.UniqueResourceException;
import com.gznznzjsn.carservice.domain.user.Role;
import com.gznznzjsn.carservice.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getNonExisting() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.get(1L));

        verify(userDao).findById(anyLong());
    }

    @Test
    public void getExisting() {
        User user = User.builder()
                .id(1L)
                .email("e@mail.com")
                .name("userName")
                .role(Role.USER)
                .password("pass")
                .build();
        when(userDao.findById(1L)).thenReturn(Optional.of(user));

        User returnedUser = userService.get(1L);

        verify(userDao).findById(1L);
        assertEquals(user, returnedUser);
    }

    @Test
    public void getByEmailNonExisting() {
        when(userDao.findByEmail("e@mail.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByEmail("e@mail.com"));

        verify(userDao).findByEmail(anyString());
    }

    @Test
    public void getByEmailExisting() {
        User user = User.builder()
                .id(1L)
                .email("e@mail.com")
                .name("userName")
                .role(Role.USER)
                .password("pass")
                .build();
        when(userDao.findByEmail("e@mail.com")).thenReturn(Optional.of(user));

        User returnedUser = userService.getByEmail("e@mail.com");

        verify(userDao).findByEmail("e@mail.com");
        assertEquals(user, returnedUser);
    }

    @Test
    public void createAlreadyCreated() {
        User userWithTakenEmail = User.builder().email("e@mail.com").build();
        when(userDao.findByEmail("e@mail.com")).thenReturn(Optional.of(new User()));

        assertThrows(UniqueResourceException.class, () -> userService.create(userWithTakenEmail));

        verify(userDao).findByEmail("e@mail.com");
        verify(userDao, never()).create(any());
    }

    @Test
    public void createCorrect() {
        User user = User.builder()
                .email("e@mail.com")
                .name("userName")
                .role(Role.USER)
                .password("pass")
                .build();
        when(userDao.findByEmail("e@mail.com")).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            ((User) invocation.getArgument(0)).setId(1L);
            return null;
        }).when(userDao).create(user);

        userService.create(user);

        assertEquals(1L, user.getId());
        verify(userDao).findByEmail("e@mail.com");
        verify(userDao).create(user);
    }
}