package info.ogkapps.table21.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.ogkapps.table21.entity.CompletedBilledItems;

@Repository
public interface CompletedBilledItemsRepository extends JpaRepository<CompletedBilledItems, Long> {

}
