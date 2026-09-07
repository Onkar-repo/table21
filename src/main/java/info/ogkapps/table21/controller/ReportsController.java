package info.ogkapps.table21.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import info.ogkapps.table21.dto.ReportsDTO;
import info.ogkapps.table21.service.ReportsService;
import jakarta.servlet.http.HttpSession;

@RestController
public class ReportsController {
	
	private final ReportsService reportsService;

	public ReportsController(ReportsService reportsService) {
		super();
		this.reportsService = reportsService;
	}


	@PostMapping("/reports")
	List<Map<String, String>> getReports(@RequestBody ReportsDTO reportsDTO, HttpSession session){
		if (session.getAttribute(reportsDTO.billUser)!=null && session.getAttribute(reportsDTO.billUser).equals(reportsDTO.billUser)) {
			
			return reportsService.getReports(reportsDTO);
			
		}
		else {
			return null; //temp;
		}
	}
}
