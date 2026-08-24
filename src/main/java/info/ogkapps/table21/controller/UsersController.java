package info.ogkapps.table21.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import info.ogkapps.table21.service.UsersService;
import tools.jackson.databind.JsonNode;

//  Class Definition begins here...
@Controller
public class UsersController {

	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		super();
		this.usersService = usersService;
	}

	
	/*Sign Up Page Section*/
	
	@GetMapping("/signup")
	public String signupGet() {
		return "signup";
	}

	@PostMapping("/signup")
	@ResponseBody
	public String signupPost(@RequestBody JsonNode jsonNode) {
		try {
			String userName = jsonNode.get("userName").stringValue();
			String userEmail = jsonNode.get("userEmail").stringValue();
			String userPassword = jsonNode.get("userPassword").stringValue();
			return usersService.saveIfNotExist(userName, userEmail, userPassword) ? "saved" : "exists";

		} catch (Exception e) {
			return "unknown";
		}
	}
	
	/*Log In Page Section*/
	
	@GetMapping("/login")
	public String loginGet() {
		return "login";
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unknown");
	}

}
