package info.ogkapps.table21.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import info.ogkapps.table21.dto.ReportsDTO;
import info.ogkapps.table21.entity.CompletedBilledItems;
import info.ogkapps.table21.entity.Items;
import info.ogkapps.table21.repository.CompletedBilledItemsRepository;
import info.ogkapps.table21.repository.ItemsRepository;
import info.ogkapps.table21.repository.UsersRepository;

@Service
public class ReportsService {

	private final CompletedBilledItemsRepository completedBilledItemsRepository;
	private final ItemsRepository itemsRepository;
	private final UsersRepository usersRepository;

	public ReportsService(CompletedBilledItemsRepository completedBilledItemsRepository,
			ItemsRepository itemsRepository, UsersRepository usersRepository) {
		super();
		this.completedBilledItemsRepository = completedBilledItemsRepository;
		this.itemsRepository = itemsRepository;
		this.usersRepository = usersRepository;
	}

	public String getTotalIncome(ReportsDTO reportsDTO) {

		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			List<CompletedBilledItems> bi = completedBilledItemsRepository.findByCbiUserId(uid);

			return bi.stream()
					.map((a) -> a.getCbiQuantity() * a.getCbiCost() + a.getCbiQuantity() * a.getCbiCost() * a.getCbiGst())
					.reduce(0, (a, b) -> a + b).toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "0"; // temp
		}
	}
	
	public String getTotalItems(ReportsDTO reportsDTO) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			List<Items> i = itemsRepository.findByItemUser(uid);
			
			return "" + i.size();
		} catch (Exception e) {
			e.printStackTrace();
			return null; //temp
		}
	}
	
	public String getTotalDateRangedIncome(ReportsDTO reportsDTO) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			LocalDateTime from = LocalDateTime.parse(reportsDTO.from, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime to = LocalDateTime.parse(reportsDTO.to, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			List<CompletedBilledItems> cbiList = completedBilledItemsRepository.findByCbiUserIdAndCbiBillCreatedAtBetween(uid, from, to);
			return cbiList.stream()
					.map((a) -> a.getCbiQuantity() * a.getCbiCost() + a.getCbiQuantity() * a.getCbiCost() * a.getCbiGst())
					.reduce(0, (a, b) -> a + b).toString();
		} catch (Exception e) {
			e.printStackTrace();
return null; // temp
		}
	}

	public List<Map<String, String>> getReports(ReportsDTO reportsDTO) {
		Map<String, String> reportMap = new LinkedHashMap<>();
		List<Map<String, String>> listOfMap = new ArrayList<>();

		switch (reportsDTO.report) {

		case "Total Income":
			reportMap.put("Total Income", getTotalIncome(reportsDTO));
			break;
		case "Custom Date Ranged Income":
			reportMap.put("Custom Date Ranged Income", getTotalDateRangedIncome(reportsDTO));
			break;
		case "Total Items":
			reportMap.put("Total Items", getTotalItems(reportsDTO));
			break;
			
			

		}

		listOfMap.add(reportMap);

		return listOfMap;
	}
}
