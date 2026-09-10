package info.ogkapps.table21.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
					.reduce(0, (a, b) -> a + b)
					.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	public String getTotalItems(ReportsDTO reportsDTO) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			List<Items> i = itemsRepository.findByItemUser(uid);
			
			return "" + i.size();
		} catch (Exception e) {
			e.printStackTrace();
			return "failed: " + e.getMessage();
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
					.reduce(0, (a, b) -> a + b)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
return "failed: " + e.getMessage();
		}
	}
	
	public String getItemwiseTotalIncome(ReportsDTO reportsDTO) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			List<CompletedBilledItems> cbiList = completedBilledItemsRepository.findByCbiUserIdAndCbiName(uid, reportsDTO.itemName);
			return cbiList.stream()
					.map((a) -> a.getCbiQuantity() * a.getCbiCost() + a.getCbiQuantity() * a.getCbiCost() * a.getCbiGst())
					.reduce(0, (a, b) -> a + b)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
return "failed: " + e.getMessage();
		}
	}
	
	public String getItemwiseTotalDateRangedIncome(ReportsDTO reportsDTO) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			LocalDateTime from = LocalDateTime.parse(reportsDTO.from, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime to = LocalDateTime.parse(reportsDTO.to, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			List<CompletedBilledItems> cbiList = completedBilledItemsRepository.findByCbiUserIdAndCbiNameAndCbiBillCreatedAtBetween(uid,reportsDTO.itemName, from, to);
			return cbiList.stream()
					.map((a) -> a.getCbiQuantity() * a.getCbiCost() + a.getCbiQuantity() * a.getCbiCost() * a.getCbiGst())
					.reduce(0, (a, b) -> a + b)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
return "failed: " + e.getMessage();
		}
	}
	
	public String getMostOrLeastSoldItem(ReportsDTO reportsDTO, boolean most) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			List<CompletedBilledItems> cbiList = completedBilledItemsRepository.findByCbiUserId(uid);
			Map<String, Integer> map = cbiList.stream()
			.collect(Collectors.toMap((a)->a.getCbiName(), (b)->b.getCbiQuantity(), (oldV,newV)-> oldV+newV));
			int smal=Integer.MAX_VALUE,larg=Integer.MIN_VALUE;
			String smali="", largi="";
			for (Map.Entry<String, Integer> mapl : map.entrySet()) {
				String key = mapl.getKey();
				Integer val = mapl.getValue();
				System.out.println("K:" + key + " | V:" + val);
				if (val > larg) {
					larg = val;
					largi = key;
				}
				if (val < smal) {
					smal = val;
					smali = key;
				}
			}
			return   most ? largi + ":" + larg : smali + ":" + smal;
		} catch (Exception e) {
			e.printStackTrace();
			return "failed: " + e.getMessage();
		}
	}
	
	public String getMostOrLeastSoldItemDateRanged(ReportsDTO reportsDTO, boolean most) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			LocalDateTime from = LocalDateTime.parse(reportsDTO.from, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime to = LocalDateTime.parse(reportsDTO.to, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			List<CompletedBilledItems> cbiList = completedBilledItemsRepository.findByCbiUserIdAndCbiBillCreatedAtBetween(uid, from, to);
			Map<String, Integer> map = cbiList.stream()
			.collect(Collectors.toMap((a)->a.getCbiName(), (b)->b.getCbiQuantity(), (oldV,newV)-> oldV+newV));
			int smal=Integer.MAX_VALUE,larg=Integer.MIN_VALUE;
			String smali="", largi="";
			for (Map.Entry<String, Integer> mapl : map.entrySet()) {
				String key = mapl.getKey();
				Integer val = mapl.getValue();
				System.out.println("K:" + key + " | V:" + val);
				if (val > larg) {
					larg = val;
					largi = key;
				}
				if (val < smal) {
					smal = val;
					smali = key;
				}
			}
			return   most ? largi + ":" + larg : smali + ":" + smal;
		} catch (Exception e) {
			e.printStackTrace();
			return "failed: " + e.getMessage();
		}
	}
	
	
	
	public String getMostOrLeastExpensiveItem(ReportsDTO reportsDTO, boolean most) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			List<Items> i = itemsRepository.findByItemUser(uid);
			Items temp;
			if (most) {
				temp=i.stream()
						.max((a,b)-> Integer.compare(a.getItemCost(), b.getItemCost()))
						.get();
			}
			else {
				temp=i.stream()
						.min((a,b)-> Integer.compare(a.getItemCost(), b.getItemCost()))
						.get();
			}
			
			return temp.getItemName() + ":" + temp.getItemCost();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "failed: " + e.getMessage();
		}
	}
	
	

	public String getHighestOrLowestBilledAmount(ReportsDTO reportsDTO, boolean most) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			List<CompletedBilledItems> cbiList = completedBilledItemsRepository.findByCbiUserId(uid);
			Map<Long, Integer> map = cbiList.stream()
			.collect(Collectors.toMap((a)->a.getCbiBillId(), (b)->b.getCbiCost()*b.getCbiQuantity() + b.getCbiQuantity()* b.getCbiCost()* b.getCbiGst(), (oldV,newV)-> oldV+newV));
			int smal=Integer.MAX_VALUE,larg=Integer.MIN_VALUE;
			long smali=0, largi=0;
			for (Map.Entry<Long, Integer> mapl : map.entrySet()) {
				Long key = mapl.getKey();
				Integer val = mapl.getValue();
				System.out.println("K:" + key + " | V:" + val);
				if (val > larg) {
					larg = val;
					largi = key;
				}
				if (val < smal) {
					smal = val;
					smali = key;
				}
			}
			return   most ? largi + ":" + larg : smali + ":" + smal;
		} catch (Exception e) {
			e.printStackTrace();
			return "failed: " + e.getMessage();
		}
	}
	
	
	public String getHighestOrLowestBilledAmountDateRanged(ReportsDTO reportsDTO, boolean most) {
		try {
			Long uid = usersRepository.findByUserEmail(reportsDTO.billUser).get().getUserId();
			LocalDateTime from = LocalDateTime.parse(reportsDTO.from, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime to = LocalDateTime.parse(reportsDTO.to, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			List<CompletedBilledItems> cbiList = completedBilledItemsRepository.findByCbiUserIdAndCbiBillCreatedAtBetween(uid, from, to);
			Map<Long, Integer> map = cbiList.stream()
			.collect(Collectors.toMap((a)->a.getCbiBillId(), (b)->b.getCbiCost()*b.getCbiQuantity() + b.getCbiQuantity()* b.getCbiCost()* b.getCbiGst(), (oldV,newV)-> oldV+newV));
			int smal=Integer.MAX_VALUE,larg=Integer.MIN_VALUE;
			long smali=0, largi=0;
			for (Map.Entry<Long, Integer> mapl : map.entrySet()) {
				Long key = mapl.getKey();
				Integer val = mapl.getValue();
				System.out.println("K:" + key + " | V:" + val);
				if (val > larg) {
					larg = val;
					largi = key;
				}
				if (val < smal) {
					smal = val;
					smali = key;
				}
			}
			return   most ? largi + ":" + larg : smali + ":" + smal;
		} catch (Exception e) {
			e.printStackTrace();
			return "failed: " + e.getMessage();
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
		case "Itemwise Total Income":
			reportMap.put("Itemwise Total Income", getItemwiseTotalIncome(reportsDTO));
			break;
		case "Itemwise Custom Date Ranged Income":
			reportMap.put("Itemwise Custom Date Ranged Income", getItemwiseTotalDateRangedIncome(reportsDTO));
			break;
		case "Most Sold Item":
			reportMap.put("Most Sold Item", getMostOrLeastSoldItem(reportsDTO, true));
			break;
		case "Least Sold Item":
			reportMap.put("Least Sold Item", getMostOrLeastSoldItem(reportsDTO, false));
			break;
		case "Most Sold Item Custom Date Ranged":
			reportMap.put("Most Sold Item Custom Date Ranged", getMostOrLeastSoldItemDateRanged(reportsDTO, true));
			break;
		case "Least Sold Item Custom Date Ranged":
			reportMap.put("Least Sold Item Custom Date Ranged", getMostOrLeastSoldItemDateRanged(reportsDTO, false));
			break;
		case "Highest Billed Amount":
			reportMap.put("Highest Billed Amount", getHighestOrLowestBilledAmount(reportsDTO, true));
			break;
		case "Lowest Billed Amount":
			reportMap.put("Lowest Billed Amount", getHighestOrLowestBilledAmount(reportsDTO, false));
			break;
		case "Highest Billed Amount Custom Date Ranged":
			reportMap.put("Highest Billed Amount Custom Date Ranged", getHighestOrLowestBilledAmountDateRanged(reportsDTO, true));
			break;
		case "Lowest Billed Amount Custom Date Ranged":
			reportMap.put("Lowest Billed Amount Custom Date Ranged", getHighestOrLowestBilledAmountDateRanged(reportsDTO, false));
			break;
		case "Total Items":
			reportMap.put("Total Items", getTotalItems(reportsDTO));
			break;
		case "Least Expensive Item":
			reportMap.put("Least Expensive Item", getMostOrLeastExpensiveItem(reportsDTO, false));
			break;	
		case "Most Expensive Item":
			reportMap.put("Most Expensive Item", getMostOrLeastExpensiveItem(reportsDTO, true));
			break;
		case "All":
			reportMap.put("Total Income", getTotalIncome(reportsDTO));
			reportMap.put("Custom Date Ranged Income", getTotalDateRangedIncome(reportsDTO));
			reportMap.put("Itemwise Total Income", getItemwiseTotalIncome(reportsDTO));
			reportMap.put("Itemwise Custom Date Ranged Income", getItemwiseTotalDateRangedIncome(reportsDTO));
			reportMap.put("Most Sold Item", getMostOrLeastSoldItem(reportsDTO, true));
			reportMap.put("Most Sold Item Custom Date Ranged", getMostOrLeastSoldItemDateRanged(reportsDTO, true));
			reportMap.put("Least Sold Item", getMostOrLeastSoldItem(reportsDTO, false));
			reportMap.put("Least Sold Item Custom Date Ranged", getMostOrLeastSoldItemDateRanged(reportsDTO, false));
			reportMap.put("Highest Billed Amount", getHighestOrLowestBilledAmount(reportsDTO, true));
			reportMap.put("Highest Billed Amount Custom Date Ranged", getHighestOrLowestBilledAmountDateRanged(reportsDTO, true));
			reportMap.put("Lowest Billed Amount", getHighestOrLowestBilledAmount(reportsDTO, false));
			reportMap.put("Lowest Billed Amount Custom Date Ranged", getHighestOrLowestBilledAmountDateRanged(reportsDTO, false));
			reportMap.put("Total Items", getTotalItems(reportsDTO));
			reportMap.put("Least Expensive Item", getMostOrLeastExpensiveItem(reportsDTO, false));
			reportMap.put("Most Expensive Item", getMostOrLeastExpensiveItem(reportsDTO, true));
		}

		listOfMap.add(reportMap);

		return listOfMap;
	}
}
