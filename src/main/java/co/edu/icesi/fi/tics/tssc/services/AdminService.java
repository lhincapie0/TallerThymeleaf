package co.edu.icesi.fi.tics.tssc.services;

import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingAdminException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullAdminException;
import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;

public interface AdminService {

	
	public TsscAdmin saveAdmin(TsscAdmin admin) throws NullAdminException;
	public TsscAdmin editAdmin(TsscAdmin admin) throws NullAdminException, NotExistingAdminException;
}
