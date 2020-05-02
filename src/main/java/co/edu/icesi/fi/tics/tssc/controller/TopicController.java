package co.edu.icesi.fi.tics.tssc.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TopicValidation;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

@Controller
public class TopicController {
	
	private TopicServiceImpl topicService;
	
	@Autowired
	public TopicController( TopicServiceImpl topicService)
	{
		this.topicService = topicService;
	}
	

	@GetMapping("/topics/")
	public String indexTopic(Model model) {
		System.out.println(topicService.findAll());
		model.addAttribute("topics", topicService.findAll());
		
	
		return "topics/index";
	}

	@GetMapping("topics/add")
	public String addTopic(Model model)
	{
		model.addAttribute("topic", new TsscTopic());
		return "topics/add-topic";
	}
	
	@PostMapping("topics/add")
	public String saveTopic(@RequestParam(value = "action", required = true) String action, @Validated(TopicValidation.class) @ModelAttribute("topic") TsscTopic topic,
			BindingResult bindingResult, Model model) throws NullTopicException, NotEnoughGroupsException, NotEnoughSprintsException
	{
		if(!action.equals("Cancelar"))
		{
			if(bindingResult.hasErrors())
			{
				return "topics/add-topic";
			}else
			{
				topicService.saveTopic(topic);
			}
		}else {
			return "topics/index";
		}
		return "redirect:/topics/";
	}
	

	@GetMapping("/topics/edit/{id}")
	public String editTopicShowView(@PathVariable("id") long id, Model model) {
		TsscTopic topic = topicService.findTopicById(id);
		if (topic == null)
			throw new IllegalArgumentException("Invalid Topic Id:" + id);
		model.addAttribute("topic", topic);
		model.addAttribute("mode", "edit1");
		return "topics/edit-topic";
	}
	
	@GetMapping("/topics/edit2/{id}")
	public String editTopic2ShowView(@PathVariable("id") long id, Model model) {
		TsscTopic topic = topicService.findTopicById(id);
		if (topic == null)
			throw new IllegalArgumentException("Invalid Topic Id:" + id);
		model.addAttribute("topic", topic);
		model.addAttribute("mode", null);
		return "topics/edit-topic";
	}
	
	@PostMapping("/topics/edit/{id}")
	public String editTopic( @PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action, @Validated(TopicValidation.class) @ModelAttribute("topic") TsscTopic topic, BindingResult bindingResult,
			Model model) throws NullTopicException, NotExistingTopic, NotEnoughSprintsException, NotEnoughGroupsException {
		if (action != null && !action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {
				return "/topics/edit-topic";
			} else {
				topicService.editTopic(topic);
			}
		}
		if(action.equals("Actualizar Tema"))
		{
			return "redirect:/topics/";
		}else {
			return "redirect:/games/";

		}
	}

	
	@GetMapping("/topics/del/{id}")
	public String deleteTopic(@PathVariable("id") long id)
	{
		TsscTopic topic = topicService.findTopicById(id);
		if(topic == null) {
		 new IllegalArgumentException("Invalidad Topic id: " + id);
		}
		topicService.deleteTopic(topic);
		return "redirect:/topics/";
	}
	

}
