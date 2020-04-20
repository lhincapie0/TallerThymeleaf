package co.edu.icesi.fi.tics.tssc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingAdminException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullAdminException;
import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public TsscAdmin saveAdmin(TsscAdmin admin) throws NullAdminException {
		if(admin!= null)
		{
			adminRepository.save(admin);
			return admin;
		}else throw new NullAdminException();
	}



	@Override
	public TsscAdmin editAdmin(TsscAdmin admin) throws NullAdminException, NotExistingAdminException {
		if(admin!= null)
		{
			if(adminRepository.findById(admin.getId())!= null)
			{
				adminRepository.save(admin);
				return admin;
			}else throw new NotExistingAdminException();
		}else throw new NullAdminException();

	}

}
