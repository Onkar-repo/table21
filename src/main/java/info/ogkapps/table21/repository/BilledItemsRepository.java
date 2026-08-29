package info.ogkapps.table21.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.ogkapps.table21.entity.BilledItems;

//Interface Definition begins here...
@Repository
public interface BilledItemsRepository extends JpaRepository<BilledItems, Long>{

//Derived query methods begins here...
List<BilledItems> findByBilledItemParent(Long billedItemParent);

//Custom query methods begins here...
	
}