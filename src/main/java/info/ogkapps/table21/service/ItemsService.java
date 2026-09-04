package info.ogkapps.table21.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import info.ogkapps.table21.dto.AddItemDTO;
import info.ogkapps.table21.dto.BilledItemsDTO;
import info.ogkapps.table21.dto.ClearItemsDTO;
import info.ogkapps.table21.dto.ItemsDTO;
import info.ogkapps.table21.dto.RegisterItemsDTO;
import info.ogkapps.table21.dto.RemoveItemDTO;
import info.ogkapps.table21.entity.BilledItems;
import info.ogkapps.table21.entity.Items;
import info.ogkapps.table21.entity.Tables;
import info.ogkapps.table21.repository.BilledItemsRepository;
import info.ogkapps.table21.repository.ItemsRepository;
import info.ogkapps.table21.repository.TablesRepository;
import info.ogkapps.table21.repository.UsersRepository;

@Service
public class ItemsService {
	private final ItemsRepository itemsRepository;
	private final UsersRepository usersRepository;
	private final BilledItemsRepository billedItemsRepository;
	private final TablesRepository tablesRepository;

	public ItemsService(ItemsRepository itemsRepository, UsersRepository usersRepository,
			BilledItemsRepository billedItemsRepository, TablesRepository tablesRepository) {
		super();
		this.itemsRepository = itemsRepository;
		this.usersRepository = usersRepository;
		this.billedItemsRepository = billedItemsRepository;
		this.tablesRepository = tablesRepository;
	}

	public String registerItems(RegisterItemsDTO registerItemsDTO) {
		try {
			Long uid = usersRepository.findByUserEmail(registerItemsDTO.billUser).get().getUserId();

			for (ItemsDTO idto : registerItemsDTO.items) {

				if (itemsRepository.existsByItemUserAndItemCode(uid, idto.itemCode)
						|| itemsRepository.existsByItemUserAndItemName(uid, idto.itemName)) {
					continue;

				} else {

					Items singleItem = new Items(idto.itemCode, idto.itemName, Integer.parseInt(idto.itemCost), uid);
					itemsRepository.save(singleItem);

				}

			}
			return "saved";
		} catch (Exception e) {
			return "failed";
		}
	}

	public String editItems(RegisterItemsDTO registerItemsDTO) {
		try {
			Long uid = usersRepository.findByUserEmail(registerItemsDTO.billUser).get().getUserId();
			for (ItemsDTO idto : registerItemsDTO.items) {

				if (itemsRepository.existsByItemUserAndItemCode(uid, idto.itemCode)
						|| itemsRepository.existsByItemUserAndItemName(uid, idto.itemName)) {

					Long iid = null;

					List<Items> ti = itemsRepository.findByItemUserAndItemCodeAndItemName(uid, idto.itemCode,
							idto.itemName);
					if (!ti.isEmpty()) {
						iid = ti.getFirst().getItemId();
					} else {
						ti = itemsRepository.findByItemUserAndItemCode(uid, idto.itemCode);
						if (!ti.isEmpty()) {
							iid = ti.getFirst().getItemId();
						} else {
							ti = itemsRepository.findByItemUserAndItemName(uid, idto.itemName);
							if (!ti.isEmpty()) {
								iid = ti.getFirst().getItemId();
							}
						}

					}

					if (iid != null) {
						itemsRepository.save(new Items(iid, idto.itemCode, idto.itemName,
								Integer.parseInt(idto.itemCost), (short) 0, uid));
					}

				}
			}

			return "saved";

		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}

	public List<ItemsDTO> getAllItems(String billUser) {
		try {
			Long uid = usersRepository.findByUserEmail(billUser).get().getUserId();
			List<Items> items = itemsRepository.findByItemUser(uid);
			List<ItemsDTO> itsdto = new LinkedList<>();
			for (Items i : items) {
				itsdto.add(new ItemsDTO(i.getItemCode(), i.getItemName(), ""));
			}
			return itsdto;
		} catch (Exception e) {
			return null; // temp
		}
	}

	public List<BilledItemsDTO> addItemAndReturnAll(AddItemDTO addItemDTO) {
		try {
			Long uid = usersRepository.findByUserEmail(addItemDTO.billUser).get().getUserId();
			List<Items> oneItemList = itemsRepository.findByItemUserAndItemCodeAndItemName(uid, addItemDTO.itemCode,
					addItemDTO.itemName);
			Long iid = oneItemList.getFirst().getItemId();
			Long bid = tablesRepository.findTableBillIdByTableUserAndTableNumberAndTableStatus(uid,
					Short.parseShort(addItemDTO.billTable), "Occupied").map(Tables::getTableBillId).orElse(null);
			billedItemsRepository.save(new BilledItems(bid, (short) 0, Integer.parseInt(addItemDTO.itemQuantity), iid));
			List<BilledItems> billedItems = billedItemsRepository.findByBilledItemParent(bid);
			List<BilledItemsDTO> oidto = new LinkedList<>();
			for (BilledItems i : billedItems) {
				Items tItem = itemsRepository.findById(i.getBilledItemIdentity()).get();
				String p1 = i.getBilledItemPk().toString();
				String p2 = i.getBilledItemQuantity().toString();
				String p3 = tItem.getItemName();
				String p4 = String.valueOf(tItem.getItemCost() * i.getBilledItemQuantity()
						+ tItem.getItemCost() * i.getBilledItemQuantity() * tItem.getItemGST());
				oidto.add(new BilledItemsDTO(p1, p2, p3, p4));
			}
			return oidto;

		} catch (Exception e) {

			return null; // temp
		}

	}

	public List<BilledItemsDTO> removeItemAndReturnAll(RemoveItemDTO removeItemDTO) {
		try {
			Long bid = billedItemsRepository.findById(Long.valueOf(removeItemDTO.itemPk)).get().getBilledItemParent();
			billedItemsRepository.deleteById(Long.valueOf(removeItemDTO.itemPk));
			List<BilledItems> billedItems = billedItemsRepository.findByBilledItemParent(bid);

			List<BilledItemsDTO> oidto = new LinkedList<>();

			if (!billedItems.isEmpty()) {

				for (BilledItems i : billedItems) {
					Items tItem = itemsRepository.findById(i.getBilledItemIdentity()).get();
					String p1 = i.getBilledItemPk().toString();
					String p2 = i.getBilledItemQuantity().toString();
					String p3 = tItem.getItemName();
					String p4 = String.valueOf(tItem.getItemCost() + tItem.getItemCost() * tItem.getItemGST());
					oidto.add(new BilledItemsDTO(p1, p2, p3, p4));
				}
			}
			return oidto;

		} catch (Exception e) {
			e.printStackTrace();
			return null; // temp
		}

	}

	public String clearItems(ClearItemsDTO clearItemsDTO) {
		try {
			Long bid = Long.valueOf(clearItemsDTO.billNumber);
			billedItemsRepository.deleteByBilledItemParent(bid);
			return "done";

		} catch (Exception e) {
			e.printStackTrace();
			return "failed"; // temp
		}
	}
}