package info.ogkapps.table21.service;

import org.springframework.stereotype.Service;

import info.ogkapps.table21.entity.Users;
import info.ogkapps.table21.repository.UsersRepository;

@Service
public class UsersService {

	private final UsersRepository usersRepository;

	public UsersService(UsersRepository usersRepository) {
		super();
		this.usersRepository = usersRepository;
	}

	public boolean saveIfNotExist(String userName, String userEmail, String userPassword) {

		if (usersRepository.existsByUserEmail(userEmail)) {
			return false;
		}

		else {
			usersRepository.save(new Users(userName, userEmail, userPassword));
			return true;
		}
	}

	public boolean userAuthenticated(String userEmail, String userPassword) {

		try {
			System.out.println("entered userAuthenticated()");
			String up = usersRepository.findByUserEmail(userEmail)
								  .orElse(new Users("", "", ""))
								  .getUserPassword();
								  //.equals(userPassword);
			
			System.out.println(up);
			return up.equals(userPassword);

		} catch (Exception e) {
			System.out.println("exception in userAuthenticated()");
			return false;

		}

	}

}
