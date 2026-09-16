package info.ogkapps.table21.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import info.ogkapps.table21.dto.ReportsDTO;
import info.ogkapps.table21.service.ReportsService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ReportsController {
	
	private final ReportsService reportsService;

	public ReportsController(ReportsService reportsService) {
		super();
		this.reportsService = reportsService;
	}
	
	
	@GetMapping("/dashboard/reportspage")
	public String getReportsPage(@RequestParam("billUser") String billUser, HttpSession session) {
		if (session.getAttribute(billUser) != null && session.getAttribute(billUser).equals(billUser)) {
			return "reports";
		} else {
			return "login";
		}
	}
	
	@ResponseBody
	@PostMapping("/reports")
	List<Map<String, String>> getReports(@RequestBody ReportsDTO reportsDTO, HttpSession session){
		if (session.getAttribute(reportsDTO.billUser)!=null && session.getAttribute(reportsDTO.billUser).equals(reportsDTO.billUser)) {
			
			return reportsService.getReports(reportsDTO);
			
		}
		else {
			Map<String, String> em = new HashMap<>(1);
			em.put("message", "Requested without authentication.");
			List<Map<String, String>> ed = new LinkedList<>();
			ed.add(em);
			return ed;
		}
	}
}
