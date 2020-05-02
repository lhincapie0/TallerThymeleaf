package co.edu.icesi.fi.tics.tssc.dao;

import java.util.List;

import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;

public interface ITsscAdminDao {
	
	public void save(TsscAdmin entity);
	public void update(TsscAdmin enity);
	public void delete(TsscAdmin entity);
	public List<TsscAdmin> findAll();
	public TsscAdmin findById(long id);

}
