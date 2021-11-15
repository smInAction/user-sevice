package com.sm.userservice.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.applicationinsights.TelemetryClient;
import com.sm.userservice.dao.UserRepository;
import com.sm.userservice.model.SiteUser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserRepository userRepository;
	@Autowired
    private TelemetryClient telemetryClient;

	
	@GetMapping("/users")
	  public Flux<SiteUser> getAllUsers(@RequestParam(required = false) String fname) {
		logger.info("retrieving users...");
	    try {
	      if (fname == null) {
	    	  return userRepository.findAll();
	      }
	      else {
	    	  return userRepository.findByFname(fname);
	      }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
	  }

	  @GetMapping("/users/{id}")
	  public Mono<ResponseEntity<SiteUser>> getUserById(@PathVariable("id") int id) {
		  telemetryClient.trackEvent(String.format("get user by ID: {%s}", id));
		  Mono<SiteUser> userData = userRepository.findById(id);
		  return userData.map(result -> ResponseEntity.ok(result)).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	  }

	  @PostMapping("/users")
	  public Mono<ResponseEntity<SiteUser>> createUser(@RequestBody SiteUser user) {
		logger.info("new user==>"+user);
    	Mono<SiteUser> libUser = userRepository.save(new SiteUser(user.getFname(), user.getLname(), user.getAge()));
    	logger.info("new user created...");
    	return libUser.map(result -> ResponseEntity.ok(result))
    			.defaultIfEmpty(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	  }

	  @PutMapping("/users/{id}")
	  public Mono<ResponseEntity<SiteUser>> updateUser(@PathVariable("id") int id, @RequestBody SiteUser user) {
	    Mono<SiteUser> userData = userRepository.findById(id);
	    logger.info("updating user...");
	    return userData.map(result -> ResponseEntity.ok(result))
	    	.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	  }

	  @DeleteMapping("/users/{id}")
	  public Mono<ResponseEntity<Object>> deleteUser(@PathVariable("id") int id) {
		  logger.info("deleting user...");
		  return userRepository.deleteById(id).map(result -> new ResponseEntity<>(HttpStatus.NO_CONTENT))
	    		  .defaultIfEmpty(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	  }
	  
	  @DeleteMapping("/users")
	  public Mono<ResponseEntity<Object>> deleteUsers() {
	      return userRepository.deleteAll().map(result -> new ResponseEntity<>(HttpStatus.NO_CONTENT))
	    		  .defaultIfEmpty(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	  }
}
