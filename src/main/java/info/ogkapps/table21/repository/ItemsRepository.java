package info.ogkapps.table21.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.ogkapps.table21.entity.Items;

//Interface Definition begins here...
@Repository
public interface ItemsRepository extends JpaRepository<Items, Long>{

//  Derived query methods begins here...
boolean existsByItemCode(String itemCode);
boolean existsByItemName(String itemName);
List<Items> findByItemUser(Long itemUser);
	
//  Custom query methods begins here...
	
}
