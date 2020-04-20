package co.edu.icesi.fi.tics.tssc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.icesi.fi.tics.tssc.model.TsscStory;

public interface StoryRepository extends CrudRepository<TsscStory, Long> {

	public Iterable<TsscStory> findByIdGame(@Param("idGame") long idGame);
}
