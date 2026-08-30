package info.ogkapps.table21.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import info.ogkapps.table21.dto.ItemsDTO;
import info.ogkapps.table21.dto.RegisterItemsDTO;
import info.ogkapps.table21.entity.Items;
import info.ogkapps.table21.repository.ItemsRepository;
import info.ogkapps.table21.repository.UsersRepository;

@Service
public class ItemsService {
	private final ItemsRepository itemsRepository;
	private final UsersRepository usersRepository;


	public ItemsService(ItemsRepository itemsRepository, UsersRepository usersRepository) {
		super();
		this.itemsRepository = itemsRepository;
		this.usersRepository = usersRepository;
	}


	public String registerItems(RegisterItemsDTO registerItemsDTO) {
		try {
		Long uid = usersRepository.findByUserEmail(registerItemsDTO.billUser).get().getUserId();
		
		for (ItemsDTO idto : registerItemsDTO.items) {
			
			
			if (!(itemsRepository.existsByItemCode(idto.itemCode) || itemsRepository.existsByItemName(idto.itemName))) {
				Items singleItem =  new Items(idto.itemCode, idto.itemName, Integer.parseInt(idto.itemCost), uid);
				itemsRepository.save(singleItem);	
			} 
				
		}
		return "saved";
		}
		catch (Exception e) {
			return "failed";
		}
	}
	
	public List<ItemsDTO> getAllItems(String billUser){
		
		Long uid = usersRepository.findByUserEmail(billUser).get().getUserId();
		List<Items> items = itemsRepository.findByItemUser(uid);
		List<ItemsDTO> itsdto = new LinkedList<>();
		for (Items i : items) {
			itsdto.add(new ItemsDTO(i.getItemCode(), i.getItemName(), ""));
		}
		return itsdto;
	}
}
