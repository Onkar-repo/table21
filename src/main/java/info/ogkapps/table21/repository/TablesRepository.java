package info.ogkapps.table21.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.ogkapps.table21.entity.Tables;

//Interface Definition begins here...
public interface TablesRepository extends JpaRepository<Tables, Long> {

//Derived query methods begins here...
 String findTableStatusByTableUserAndTableNumber(Long tableUser, Short tableNumber);
 Long findTableBillIdByTableUserAndTableNumberAndTableStatus(Long tableUser,Short tableNumber,String tableStatus);
//Custom query methods begins here...

}
