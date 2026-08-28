package info.ogkapps.table21.service;

import info.ogkapps.table21.dto.LoadBilledItemsDTO;
import info.ogkapps.table21.entity.BilledItems;
import info.ogkapps.table21.entity.Bills;
import info.ogkapps.table21.entity.Tables;
import info.ogkapps.table21.entity.Users;
import info.ogkapps.table21.repository.BilledItemsRepository;
import info.ogkapps.table21.repository.BillsRepository;
import info.ogkapps.table21.repository.ItemsRepository;
import info.ogkapps.table21.repository.TablesRepository;
import info.ogkapps.table21.repository.UsersRepository;

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
			status = tablesRepository.findTableStatusByTableUserAndTableNumber(uid, Short.parseShort(tableNumber));
			
			if (uid!=null) {
			
				if (status==null) {
					long bn = System.currentTimeMillis();
					tablesRepository.save(new Tables(uid, Short.parseShort(tableNumber), status, bn));
					billsRepository.save(new Bills(bn, uid, Short.parseShort(tableNumber), status));
					LoadBilledItemsDTO lbidto = new LoadBilledItemsDTO();
					lbidto.billNumber = String.valueOf(bn);
					lbidto.billStatus = "Pending";
					lbidto.billTable = tableNumber;
					lbidto.billTotal = "0";
					lbidto.billUser = userEmail;
					lbidto.requestType = "Load";
					lbidto.tableStatus = status;
					return lbidto;
				}
				else {
					long bn = tablesRepository.findTableBillIdByTableUserAndTableNumberAndTableStatus(uid, Short.parseShort(tableNumber), "Occupied");
					BilledItems bi[] = billedItemsRepository.findByBilledItemParent(bn);
					
				}

			}
			
			
						
		} catch (NullPointerException npe) {

		}
		catch(NumberFormatException nfe) {
			
		}

		return null;
	}
}
