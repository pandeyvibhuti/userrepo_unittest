package com.test.user.userdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.user.userdemo.error.UserNotFoundException;
import com.test.user.userdemo.model.UserData;
import com.test.user.userdemo.repository.UserRepository;
import com.test.user.userdemo.service.UserService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@Autowired
	private UserService service;

	@MockBean
	private UserRepository repository;
	private static final int ID = 1;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllUserTest() {
		when(repository.findAll())
				.thenReturn(Stream.of(new UserData(1, "sohan", 2, "junior"), new UserData(2, "ravi", 3, "junior"))
						.collect(Collectors.toList()));
		assertEquals(2, service.getAllUsers().size());
	}

	@Test
	public void saveUserTest() {
		UserData user = new UserData(25, "nisha", 17, "senior");
		when(repository.save(user)).thenReturn(user);
		assertEquals(user, service.createorUpdateuser(user));
	}

	@Test
	public void findUserByIDtest() throws UserNotFoundException {
		Optional<UserData> u1 = Optional.of(new UserData(25, "nisha", 17, "senior"));
		when(repository.findById(25)).thenReturn(u1);
		UserData u2 = service.getuserById(25);
		assertEquals("nisha", u2.getUserName());
	}

	@Test
	public void updateUserTest() throws UserNotFoundException {
		Optional<UserData> u1 = Optional.of(new UserData(25, "nisha", 17, "senior"));
		UserData u2 = service.getuserById(25);
		when(repository.save(u2)).thenReturn(u2);
		assertNotNull(u2.getUserid());
	}

	@Test
	public void deleteUserTest() throws UserNotFoundException {
		Optional<UserData> u1 = Optional.of(new UserData(25, "nisha", 17, "senior"));
		when(repository.findById(25)).thenReturn(u1);
		service.deleteUserId(25);
		UserData u2 = service.getuserById(25);
		assertEquals(25, u2.getUserid());

	}
}
