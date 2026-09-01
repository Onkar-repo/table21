package info.ogkapps.table21.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.ogkapps.table21.entity.Tables;

//Interface Definition begins here...
@Repository
public interface TablesRepository extends JpaRepository<Tables, Long> {

//Derived query methods begins here...
 Optional<Tables> findTableStatusByTableUserAndTableNumber(Long tableUser, Short tableNumber);
 Optional<Tables> findTableBillIdByTableUserAndTableNumberAndTableStatus(Long tableUser,Short tableNumber,String tableStatus);
 void deleteByTableBillId(Long tableBillId);
//Custom query methods begins here...

}
