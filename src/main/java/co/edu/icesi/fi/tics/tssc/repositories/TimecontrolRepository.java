package co.edu.icesi.fi.tics.tssc.repositories;

import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

public interface TimecontrolRepository {

	public void saveTimecontrol(TsscTimecontrol timecontrol);
	public void editTimeControl(TsscTimecontrol timecontrol);
}
