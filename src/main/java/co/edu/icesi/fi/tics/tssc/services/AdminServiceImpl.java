package co.edu.icesi.fi.tics.tssc.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.dao.TsscAdminDao;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingAdminException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullAdminException;
import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
/**	@Autowired
	private AdminRepository adminRepository;
	**/
	@Autowired 
	private TsscAdminDao adminDao;

	@Override
	@Transactional
	public TsscAdmin saveAdmin(TsscAdmin admin) throws NullAdminException {
		if(admin!= null)
		{
			adminDao.save(admin);
			return admin;
		}else throw new NullAdminException();
	}



	@Override
	@Transactional
	public TsscAdmin editAdmin(TsscAdmin admin) throws NullAdminException, NotExistingAdminException {
		if(admin!= null)
		{
			if(adminDao.findById(admin.getId())!= null)
			{
				adminDao.update(admin);
				return admin;
			}else throw new NotExistingAdminException();
		}else throw new NullAdminException();

	}

}
