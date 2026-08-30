package info.ogkapps.table21.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import info.ogkapps.table21.dto.DashboardDTO;
import info.ogkapps.table21.dto.ItemsDTO;
import info.ogkapps.table21.dto.LoadBilledItemsDTO;
import info.ogkapps.table21.dto.RegisterItemsDTO;
import info.ogkapps.table21.service.ItemsService;
import info.ogkapps.table21.service.TablesService;
import jakarta.servlet.http.HttpSession;


// Class definition begins here...
@Controller
public class DashboardController {
	
	private final TablesService tablesService;
	private final ItemsService itemsService;
	
	

	public DashboardController(TablesService tablesService, ItemsService itemsService) {
		super();
		this.tablesService = tablesService;
		this.itemsService = itemsService;
	}

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
	@PostMapping("/dashboard/loadtable")
	public LoadBilledItemsDTO dashboardPost(@RequestBody DashboardDTO dashboardDTO, HttpSession session){
		if (session.getAttribute(dashboardDTO.billUser)!=null && session.getAttribute(dashboardDTO.billUser).equals(dashboardDTO.billUser)) {
			return tablesService.loadItems(dashboardDTO.billUser, dashboardDTO.billTable);	
		}
		else {
			return null; // temp returning null
		}
	}
	
	@ResponseBody
	@PostMapping("/dashboard/registeritems")
	public String dashboardPost(@RequestBody RegisterItemsDTO registerItemsDTO, HttpSession session) {
		System.out.println("entered controller method");
		if (session.getAttribute(registerItemsDTO.billUser)!=null && session.getAttribute(registerItemsDTO.billUser).equals(registerItemsDTO.billUser)) {
			System.out.println("entered session checking if succss");
			return itemsService.registerItems(registerItemsDTO);
		}
		else {
			return null;	// temp
		}
	}
	
	@ResponseBody
	@GetMapping("/dashboard/loaditems")
	public List<ItemsDTO> dashboardGetforItems(@RequestParam("billUser") String billUser, HttpSession session){
		
		return itemsService.getAllItems(billUser);
	}
	
}
