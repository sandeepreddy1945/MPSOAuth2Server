/**
 * 
 */
package com.app.mps.oauth;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.mps.oauth.security.model.User;
import com.app.mps.oauth.service.UserDetailsSaveService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Rest Service For User Related Stuff . Authenticates with basic Auth of admin
 * credentials.
 * 
 * @author Sandeep Reddy Battula
 *
 */
@Api
@RestController
public class MPSUserRest {

	@Autowired
	private UserDetailsSaveService uds;

	private ObjectMapper mapper = new ObjectMapper();

	@PostMapping(path = "/api/user/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "End Point To Add User using Basic Auth")
	public ResponseEntity<User> addUser(@RequestBody User user2) throws IOException {

		user2 = uds.saveUser(user2);

		return new ResponseEntity<User>(user2, HttpStatus.CREATED);
	}

	@GetMapping(path = "/api/user/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "End Point to Fetch Users List")
	@PreAuthorize(value = "")
	public ResponseEntity<List<User>> fetchAllUsers() throws JsonProcessingException {
		List<User> users = uds.fetchAllUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@DeleteMapping(path = "/api/user/delete")
	@ApiOperation("Delete End Point. Deletes User By User Id.")
	public ResponseEntity<String> deleteUserById(@RequestParam Long userId) {
		uds.deleteUser(userId);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	@PostMapping(path = "/api/user/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete End Point. Deletes User By User Details")
	@ResponseBody
	public ResponseEntity<String> deleteUser(@RequestBody String user) throws IOException {
		User user2 = mapper.readerFor(User.class).readValue(user);
		uds.deleteUser(user2);

		return new ResponseEntity<String>(mapper.writeValueAsString(user2), HttpStatus.OK);
	}

	@PostMapping(path = "/api/user/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update User End Point. Updates USer By User Details.")
	@ResponseBody
	public ResponseEntity<User> updateUser(@RequestBody User user) throws IOException {

		uds.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
