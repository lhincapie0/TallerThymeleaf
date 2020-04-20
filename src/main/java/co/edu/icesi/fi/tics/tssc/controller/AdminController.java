package co.edu.icesi.fi.tics.tssc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.edu.icesi.fi.tics.tssc.services.AdminServiceImpl;

@Controller
public class AdminController {

	
	AdminServiceImpl adminService;
	
	@Autowired
	public AdminController(AdminServiceImpl adminService)
	{
		this.adminService = adminService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "/loginAdmin";
	}
	
}
