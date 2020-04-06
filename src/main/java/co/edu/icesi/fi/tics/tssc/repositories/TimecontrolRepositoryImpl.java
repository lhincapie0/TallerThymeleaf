package co.edu.icesi.fi.tics.tssc.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

@Repository
public class TimecontrolRepositoryImpl implements TimecontrolRepository {

	private Map<Long, TsscTimecontrol> timecontrols;	

	public  TimecontrolRepositoryImpl() {
		timecontrols = new HashMap<Long, TsscTimecontrol>();

	}
	@Override
	public void saveTimecontrol(TsscTimecontrol timecontrol) {
		timecontrols.put(timecontrol.getId(),timecontrol);
	}

	@Override
	public void editTimeControl(TsscTimecontrol timecontrol) {
		TsscTimecontrol existingtimecontrol = timecontrols.get(timecontrol.getId());
		timecontrols.replace(timecontrol.getId(), timecontrol, existingtimecontrol);		
	}

}
