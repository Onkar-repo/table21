package info.ogkapps.table21.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.ogkapps.table21.entity.CompletedBilledItems;

@Repository
public interface CompletedBilledItemsRepository extends JpaRepository<CompletedBilledItems, Long> {
List<CompletedBilledItems> findByCbiUserId(Long cbiUserId);
List<CompletedBilledItems> findByCbiUserIdAndCbiBillCreatedAtBetween(Long cbiUserId, LocalDateTime startDate, LocalDateTime endDate);
List<CompletedBilledItems> findByCbiUserIdAndCbiName(Long cbiUserId, String cbiName);
List<CompletedBilledItems> findByCbiUserIdAndCbiNameAndCbiBillCreatedAtBetween(Long cbiUserId, String cbiName, LocalDateTime startDate, LocalDateTime endDate);
}
