package co.edu.icesi.fi.tics.tssc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

public interface GameRepository extends CrudRepository<TsscGame, Long>{
	
	public Iterable<TsscGame> findByIdTopic(@Param("idTopic") long idTopic);

}
