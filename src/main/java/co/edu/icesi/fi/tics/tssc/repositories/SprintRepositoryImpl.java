package co.edu.icesi.fi.tics.tssc.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import co.edu.icesi.fi.tics.tssc.model.TsscSprint;

@Repository
public class SprintRepositoryImpl implements SprintRepository {

	private Map<Long, TsscSprint> sprints;	

	public SprintRepositoryImpl()
	{
		sprints = new HashMap<Long, TsscSprint>();
	}
	
	@Override
	public void saveSprint(TsscSprint sprint) {
		sprints.put(sprint.getId(), sprint);
		
	}

	@Override
	public void editSprint(TsscSprint sprint) {

		TsscSprint existingSprint = sprints.get(sprint.getId());
		sprints.replace(sprint.getId(), sprint, existingSprint);
		
	}

}
