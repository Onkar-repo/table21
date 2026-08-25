package info.ogkapps.table21.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

// Class definition begins here...
@Controller
public class DashboardController {

	@GetMapping("/dashboard")
	public String dashboardGet(@RequestParam("userEmail") String userEmail ,HttpSession session) {
		if (session.getAttribute(userEmail)!=null && session.getAttribute(userEmail).equals(userEmail)) {
			return "dashboard";	
		}
		else {
			return "login";
		}
		
	}
}
