package com.app.mps.oauth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.app.mps.oauth.model.Employee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//TODO this is an example remove in mere future.
@Api
@Controller
public class EmployeeController {

	private List<Employee> employees = new ArrayList<>();

	@ApiOperation(value = "For Reteriving the APi Employess List ")
	@GetMapping("/employee")
	@ResponseBody
	// @PreAuthorize("hasAuthority('admin')")
	public Optional<Employee> getEmployee(@RequestParam String email) {
		return employees.stream().filter(x -> x.getEmail().equals(email)).findAny();
	}

	@PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void postMessage(@RequestBody Employee employee) {
		employees.add(employee);
	}

}
