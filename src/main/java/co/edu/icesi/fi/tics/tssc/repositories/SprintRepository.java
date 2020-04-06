package co.edu.icesi.fi.tics.tssc.repositories;

import co.edu.icesi.fi.tics.tssc.model.TsscSprint;

public interface SprintRepository {

	public void saveSprint(TsscSprint sprint);
	public void editSprint(TsscSprint sprint);
}
