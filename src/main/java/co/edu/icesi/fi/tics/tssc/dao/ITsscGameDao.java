package co.edu.icesi.fi.tics.tssc.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface ITsscGameDao {
	
	public void delete(TsscGame entity);
	public void save(TsscGame entity);
	public void update (TsscGame entity);
	public TsscGame findById(long id);
	public List<TsscGame> findAll();
	public List<TsscGame> findByName(String name);
	public List<TsscGame> findByDescription(String description);
	public List<TsscGame> findByTopic(long idTopic);
	public List<Object[]> findTopicsByDate(LocalDate date);
	public List<Object[]> findTopicsByDate2(LocalDate date);
	public List<TsscGame> findByDateRange(LocalDate date1, LocalDate date2);
	public List<TsscGame> findByDateAndTimeRange(LocalDate date, LocalTime time1, LocalTime time2);
	public List<TsscGame> findByNoStoriesNoTimeControls(LocalDate date);

}
