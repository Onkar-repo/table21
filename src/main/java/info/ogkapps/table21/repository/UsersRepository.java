package info.ogkapps.table21.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.ogkapps.table21.entity.Users;

//  Interface Definition begins here...
@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{

//  Derived query methods begins here...
	boolean existsByUserEmail(String userEmail);
	Optional<Users> findByUserEmail(String userEmail);
//  Custom query methods begins here...
	
}
