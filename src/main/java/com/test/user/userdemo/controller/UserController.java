package com.test.user.userdemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.user.userdemo.error.UserNotFoundException;
import com.test.user.userdemo.model.ErrorMessage;
import com.test.user.userdemo.model.UserData;
import com.test.user.userdemo.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController

public class UserController {
	@Autowired
	UserService userservice;

	Logger log = LoggerFactory.getLogger(UserController.class);
	
	
	/**
	* <h1>Get All User Details!</h1>
	* The User Details Application implements an application that
	* simply displays "User Details!" to the standard output.
	* <p>
	* @author  Vibhuti Pandey
	* @version 4.6.1
	* @since   2020-06-13
	*/
	@GetMapping("/users/all")
	public ResponseEntity<List<UserData>> getAllUsers() {
		List<UserData> list = userservice.getAllUsers();
		log.info("Get user data:");
		return new ResponseEntity<List<UserData>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation("Get All User Order By Name")
	@GetMapping("/users/sort")
	public ResponseEntity<List<UserData>> getEmployeesByOrder() {
		List<UserData> list = userservice.getEmployeesByOrder();
		log.info("Get user data in sorting order:");
		return ResponseEntity.ok(list);
	}

	
	/**
	* <h1>Add user Details!</h1>
	* The User Details Application implements an application that
	* simply displays "Add user!" to the standard output.
	* <p>
	* @author  Vibhuti Pandey
	* @version 4.6.1
	* @since   2020-06-13
	*/
	@ApiOperation(value = "Add user")
	@PostMapping("/users/{userid}")
	public ResponseEntity<UserData> createorUpdateuser(@RequestBody UserData user) {
		UserData updated = userservice.createorUpdateuser(user);
		log.info("Add user data :" + updated.getUserName());
		return new ResponseEntity<UserData>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation("Below 5 years")
	@GetMapping("/users/below")
	public ResponseEntity<List<UserData>> userBelowfive() {
		List<UserData> list = userservice.findbelowfiveexperience();
		log.info("List below 5 years user data :" + list);
		return new ResponseEntity<List<UserData>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation("Above 10 years")
	@GetMapping("/users/above")
	public ResponseEntity<List<UserData>> getByExpten() {
		List<UserData> list = userservice.findabovetenexperience();
		log.info("List above 10 years user data :" + list);
		return new ResponseEntity<List<UserData>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation("Mid level Experience")
	@GetMapping("/users/mid")
	public ResponseEntity<List<UserData>> getByExpbetween() {
		List<UserData> list = userservice.getByExpbetween();
		log.info("List of mid level user data :" + list);
		return new ResponseEntity<List<UserData>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	
	/**
	* <h1>Delete user Details!</h1>
	* The User Details Application implements an application that
	* simply displays "Delete user!" to the standard output.
	* <p>
	* @author  Vibhuti Pandey
	* @version 4.6.1
	* @since   2020-06-13
	*/
	@ApiOperation(value = "delete a user")
	@DeleteMapping("/users/{userid}")
	public HttpStatus deleteuser(@PathVariable("userid") int userid) throws UserNotFoundException {
		userservice.deleteUserId(userid);
		log.info("Delete user data in sorting order:" + userid);
		return HttpStatus.FORBIDDEN;
	}

	@ApiOperation("All User using PageRequest")
	@RequestMapping(value = "/users/page", method = RequestMethod.GET)
	public List<UserData> getAllItemCategoryByPage(@RequestParam("page") int pageIndex,
			@RequestParam("size") int pageSize) {
		log.info("User data with pagination:" );
		return userservice.getAllItemCategoriesByPageable(PageRequest.of(pageIndex, pageSize)).getContent();
	}

	@ApiOperation(value = "Search a user with an ID ", response = UserData.class)
	@RequestMapping("/user/{userid}")
	public ResponseEntity<UserData> getuserid(@PathVariable("userid") int userid) throws UserNotFoundException {
		UserData user = null;
		user = userservice.getuserById(userid);
		if (user.getUserid() == 0) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setErrorCode(404);
			errorMessage.setErrorMessage("No userID record exist for given userid");
			log.error("No userID record exist for given ="+userid);
			return new ResponseEntity(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} else {
			log.info("user data based on userId:");
			return new ResponseEntity<UserData>(user, new HttpHeaders(), HttpStatus.OK);
		}

	}
}
