package co.edu.icesi.fi.tics.tssc.dao;

import java.util.List;

import co.edu.icesi.fi.tics.tssc.model.TsscStory;

public interface ITsscStoryDao {

	public void save(TsscStory entity);
	public void update(TsscStory enity);
	public void delete(TsscStory entity);
	public List<TsscStory> findAll();
	public TsscStory findById(long id);
	public List<TsscStory> findByGame(long idGame);

	
}
