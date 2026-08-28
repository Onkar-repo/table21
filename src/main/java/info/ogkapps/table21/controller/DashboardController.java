package info.ogkapps.table21.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import info.ogkapps.table21.dto.DashboardDTO;
import info.ogkapps.table21.dto.LoadBilledItemsDTO;
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
	
	@ResponseBody
	@PostMapping("/dashboard/load")
	public LoadBilledItemsDTO dashboardPost(@RequestBody DashboardDTO dashboardDTO, HttpSession session){
		if (session.getAttribute(dashboardDTO.billUser)!=null && session.getAttribute(dashboardDTO.billUser).equals(dashboardDTO.billUser)) {
			return null; // temp returning null	
		}
		else {
			return null; // temp returning null
		}
	}
}
