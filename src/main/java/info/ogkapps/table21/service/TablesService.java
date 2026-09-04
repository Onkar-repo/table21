package info.ogkapps.table21.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.ogkapps.table21.dto.BilledItemsDTO;
import info.ogkapps.table21.dto.LoadBilledItemsDTO;
import info.ogkapps.table21.dto.PrintDTO;
import info.ogkapps.table21.entity.BilledItems;
import info.ogkapps.table21.entity.Bills;
import info.ogkapps.table21.entity.CompletedBilledItems;
import info.ogkapps.table21.entity.Items;
import info.ogkapps.table21.entity.Tables;
import info.ogkapps.table21.entity.Users;
import info.ogkapps.table21.repository.BilledItemsRepository;
import info.ogkapps.table21.repository.BillsRepository;
import info.ogkapps.table21.repository.CompletedBilledItemsRepository;
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
	private final CompletedBilledItemsRepository completedBilledItemsRepository;

	public TablesService(TablesRepository tablesRepository, UsersRepository usersRepository,
			BillsRepository billsRepository, BilledItemsRepository billedItemsRepository,
			ItemsRepository itemsRepository, CompletedBilledItemsRepository completedBilledItemsRepository) {
		super();
		this.tablesRepository = tablesRepository;
		this.usersRepository = usersRepository;
		this.billsRepository = billsRepository;
		this.billedItemsRepository = billedItemsRepository;
		this.itemsRepository = itemsRepository;
		this.completedBilledItemsRepository = completedBilledItemsRepository;
	}

	public LoadBilledItemsDTO loadItems(String userEmail, String tableNumber) {

		Long uid;
		String status;
		try {
			uid = usersRepository.findByUserEmail(userEmail).orElse(new Users("", "", "")).getUserId();
			status = tablesRepository.findTableStatusByTableUserAndTableNumber(uid, Short.parseShort(tableNumber))
					.map(Tables::getTableStatus).orElse(null);
			LoadBilledItemsDTO lbidto = new LoadBilledItemsDTO();
			lbidto.itemList = new LinkedList<>();
			if (uid != null) {

				if (status == null) {
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
				} else {
					System.out.println("entered inside  else  of loaditems");
					Long bn = tablesRepository.findTableBillIdByTableUserAndTableNumberAndTableStatus(uid,
							Short.parseShort(tableNumber), "Occupied").map(Tables::getTableBillId).orElse(null);
					List<BilledItems> bi = billedItemsRepository.findByBilledItemParent(bn);

					lbidto.billNumber = String.valueOf(bn);
					lbidto.billStatus = "Pending";
					lbidto.billTable = tableNumber;

					lbidto.billUser = userEmail;
					lbidto.requestType = "Load";
					lbidto.tableStatus = "Occupied";

					int t = 0;

					if (bi != null) {
						System.out.println("entered inside  if bi!=null");
						for (BilledItems tbi : bi) {
							System.out.println("entered inside  loop");
							long itemid = tbi.getBilledItemIdentity();
							Items oit = itemsRepository.findById(itemid).get();

							String nesti1 = tbi.getBilledItemPk().toString();// tbi.getBilledItemSerial().toString();
							String nesti2 = tbi.getBilledItemQuantity().toString();
							String nesti3 = oit.getItemName();
							String nesti4 = String.valueOf(oit.getItemCost() * tbi.getBilledItemQuantity()
									+ oit.getItemCost() * tbi.getBilledItemQuantity() * oit.getItemGST());

							lbidto.itemList.add(new BilledItemsDTO(nesti1, nesti2, nesti3, nesti4));

							t += (oit.getItemCost() * tbi.getBilledItemQuantity()
									+ oit.getItemCost() * tbi.getBilledItemQuantity() * oit.getItemGST());
						}
					}
					lbidto.billTotal = String.valueOf(t);
					return lbidto;

				}

			}

		} catch (NullPointerException npe) {
			System.out.println("entered inside  null pointer exception");
			npe.printStackTrace();

		} catch (NumberFormatException nfe) {
			System.out.println("entered inside  number format exception");
			nfe.printStackTrace();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("reached eom");
		return null;
	}

	@Transactional
	public String completeBill(String billNumber) {
		try {
			Long bid = Long.parseLong(billNumber);
			Bills btc = billsRepository.findById(bid).get();
			btc.setBillStatus("Paid");
			billsRepository.save(btc);
			List<BilledItems> allitems = billedItemsRepository.findByBilledItemParent(bid);
			for (BilledItems bi : allitems) {
				Integer quantity = bi.getBilledItemQuantity();
				Items ti = itemsRepository.findById(bi.getBilledItemIdentity()).get();
				CompletedBilledItems cbi = new CompletedBilledItems(bid, ti.getItemCode(), ti.getItemName(), quantity,
						ti.getItemCost(), ti.getItemGST());
				completedBilledItemsRepository.save(cbi);
			}
			tablesRepository.deleteByTableBillId(bid);
			return "done";
		} catch (Exception e) {
			return "failed"; // temp
		}
	}
	
	public List<PrintDTO> getPrintableItems(String billNumber){
		try {
			Long bid = Long.parseLong(billNumber);
			List<PrintDTO> lpi = new ArrayList<>();
			List<BilledItems> allitems = billedItemsRepository.findByBilledItemParent(bid);
			for (BilledItems bi : allitems) {
				Integer quantity = bi.getBilledItemQuantity();
				Items ti = itemsRepository.findById(bi.getBilledItemIdentity()).get();
				String in= ti.getItemName();
				Integer ic=(ti.getItemCost() * quantity) + (ti.getItemGST() * ti.getItemCost() * quantity);
				lpi.add(new PrintDTO(in, quantity.toString(), ic));
			}
			
			return lpi;
		} catch (Exception e) {
return null; // temp
		}
	}
}
