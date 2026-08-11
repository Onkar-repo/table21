package info.ogkapps.table21.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.ogkapps.table21.entity.Users;

//  Interface Definition begins here...
public interface UsersRepository extends JpaRepository<Users, Long>{

//  Derived query methods begins here...
	boolean existsByEmail(String email);
	
//  Custom query methods begins here...
	
}
