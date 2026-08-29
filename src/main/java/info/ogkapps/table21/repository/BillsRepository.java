package info.ogkapps.table21.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.ogkapps.table21.entity.Bills;

//Interface Definition begins here...
@Repository
public interface BillsRepository extends JpaRepository<Bills, Long>{

//Derived query methods begins here...
	
//Custom query methods begins here...

}
