package co.edu.icesi.fi.tics.tssc.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;

public interface AdminRepository extends CrudRepository<TsscAdmin, Long> {
	
	Optional<TsscAdmin> findByUser(String user);
	Optional<TsscAdmin> findByUsername(String username);

	
	

}
