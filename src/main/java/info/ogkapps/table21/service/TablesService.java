package info.ogkapps.table21.service;

import java.util.List;

import org.springframework.stereotype.Service;
import info.ogkapps.table21.dto.BilledItemsDTO;
import info.ogkapps.table21.dto.LoadBilledItemsDTO;
import info.ogkapps.table21.entity.BilledItems;
import info.ogkapps.table21.entity.Bills;
import info.ogkapps.table21.entity.Items;
import info.ogkapps.table21.entity.Tables;
import info.ogkapps.table21.entity.Users;
import info.ogkapps.table21.repository.BilledItemsRepository;
import info.ogkapps.table21.repository.BillsRepository;
import info.ogkapps.table21.repository.ItemsRepository;
import info.ogkapps.table21.repository.TablesRepository;
import info.ogkapps.table21.repository.UsersRepository;

@Service
public class TablesService {

	private final TablesRepository tablesRepository;
	private final UsersRepository usersRepository;
	private final BillsRepository billsRepository;
	private final BilledItemsRepository billedItemsRepository;
	private final ItemsRepository itemsRepository;

	public TablesService(TablesRepository tablesRepository, UsersRepository usersRepository,
			BillsRepository billsRepository, BilledItemsRepository billedItemsRepository,
			ItemsRepository itemsRepository) {
		super();
		this.tablesRepository = tablesRepository;
		this.usersRepository = usersRepository;
		this.billsRepository = billsRepository;
		this.billedItemsRepository = billedItemsRepository;
		this.itemsRepository = itemsRepository;
	}


	public LoadBilledItemsDTO loadItems(String userEmail, String tableNumber) {

		Long uid;
		String status;
		try {
			uid = usersRepository.findByUserEmail(userEmail).orElse(new Users("", "", "")).getUserId();
			status = tablesRepository.findTableStatusByTableUserAndTableNumber(uid, Short.parseShort(tableNumber)).map(Tables::getTableStatus).orElse(null);
			LoadBilledItemsDTO lbidto = new LoadBilledItemsDTO();
			if (uid!=null) {
			
				if (status==null) {
					System.out.println("entered inside uid!null & status null");
					long bn = System.currentTimeMillis();
					tablesRepository.save(new Tables(uid, Short.parseShort(tableNumber), "Occupied", bn));
					billsRepository.save(new Bills(bn, uid, Short.parseShort(tableNumber), "Pending"));
					
					lbidto.billNumber = String.valueOf(bn);
					lbidto.billStatus = "Pending";
					lbidto.billTable = tableNumber;
					lbidto.billTotal = "0";
					lbidto.billUser = userEmail;
					lbidto.requestType = "Load";
					lbidto.tableStatus = "Occupied";
					return lbidto;
				}
				else {
					Long bn = tablesRepository.findTableBillIdByTableUserAndTableNumberAndTableStatus(uid, Short.parseShort(tableNumber), "Occupied").map(Tables::getTableBillId).orElse(null);
					List<BilledItems> bi = billedItemsRepository.findByBilledItemParent(bn);
					
					lbidto.billNumber = String.valueOf(bn);
					lbidto.billStatus = "Pending";
					lbidto.billTable = tableNumber;
					
					lbidto.billUser = userEmail;
					lbidto.requestType = "Load";
					lbidto.tableStatus = "Occupied";
					
					int t=0;
					
					if (bi!=null) {
						for (BilledItems tbi : bi) 
						 {
							long itemid =  tbi.getBilledItemIdentity();
							Items oit = itemsRepository.findById(itemid).get();
							
							String nesti1 =   tbi.getBilledItemSerial().toString();
							String nesti2 =   tbi.getBilledItemQuantity().toString();
							String nesti3 =  oit.getItemName();
							String nesti4 =  String.valueOf(oit.getItemCost()+oit.getItemGST());
														  
							lbidto.itemList.add(new BilledItemsDTO(nesti1,nesti2,nesti3,nesti4));
							
							t+=(oit.getItemCost() + oit.getItemGST());
						}
					}
					lbidto.billTotal = String.valueOf(t);
					return lbidto;
					
				}

			}
			
			
						
		} catch (NullPointerException npe) {

		}
		catch(NumberFormatException nfe) {
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
}
