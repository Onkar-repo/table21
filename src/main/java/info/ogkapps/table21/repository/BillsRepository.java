package info.ogkapps.table21.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.ogkapps.table21.entity.Bills;

@Repository
public interface BillsRepository extends JpaRepository<Bills, Long>{

}
