package info.ogkapps.table21.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.ogkapps.table21.entity.BilledItems;

//Interface Definition begins here...
public interface BilledItemsRepository extends JpaRepository<BilledItems, Long>{

//Derived query methods begins here...
BilledItems[] findByBilledItemParent(Long billedItemParent);
	
//Custom query methods begins here...
	
}