package info.ogkapps.table21.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import info.ogkapps.table21.service.UsersService;
import jakarta.servlet.http.HttpSession;
import tools.jackson.databind.JsonNode;

@Controller
public class UsersController {

	private final UsersService usersService;
	private JavaMailSender mailSender;

	public UsersController(UsersService usersService, JavaMailSender mailSender) {
		super();
		this.usersService = usersService;
		this.mailSender = mailSender;
	}

	public void sendSimpleEmail(String toEmail, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("onkarkulak@gmail.com");
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}

	/* Sign Up Page Section */

	@GetMapping("/signup")
	public String signupGet() {
		return "signup";
	}

	@ResponseBody
	@GetMapping("/recover")
	public String recoverGet(@RequestParam String billUser) {
		try {

			String up = usersService.getPasswordByEmail(billUser);

			if (up == null) {
				return "Failed: User does not exists.";
			}
			if (up.equals("err")) {
				return "Failed: Exception";
			}
			sendSimpleEmail(billUser, "Table21", "This is your password: " + up);
			return "Password shared on the registered email.";
		} catch (Exception e) {
			return "Failed: " + e.getMessage();
		}
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
			return "error";
		}
	}

	/* Log In Page Section */

	@GetMapping("/login")
	public String loginGet() {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public String loginPost(@RequestBody JsonNode jsonNode, HttpSession session) {
		try {
			System.out.println("entered loginPost()");

			String userEmail = jsonNode.get("userEmail").stringValue();
			String userPassword = jsonNode.get("userPassword").stringValue();

			if (usersService.userAuthenticated(userEmail, userPassword)) {
				session.setAttribute(userEmail, userEmail);
				session.setMaxInactiveInterval(1200);
				System.out.println("entered true auth");
				return "sessionStarted";
			} else {
				System.out.println("entered false auth");
				return "wrongCred";
			}

		} catch (Exception e) {
			System.out.println("entered error");
			return "error";
		}
	}

	@ResponseBody
	@PostMapping("/logout")
	public String logoutPost(@RequestBody String billUser, HttpSession session) {
		try {

			if (session != null && session.getAttribute(billUser) != null
					&& session.getAttribute(billUser).equals(billUser)) {
				session.invalidate();
				return "done";
			} else {
				return "Requested without authentication.";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed: " + e.getMessage();
		}
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unknown");
	}

}
