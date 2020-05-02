package co.edu.icesi.fi.tics.tssc.controller;

import java.util.Optional;

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

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingStory;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.GameValidation;
import co.edu.icesi.fi.tics.tssc.model.StoryValidation;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.SprintRepositoryImpl;
import co.edu.icesi.fi.tics.tssc.repositories.StoryRepository;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.StoryServiceImpl;

@Controller
public class StoryController {

	private StoryServiceImpl storyService;
	private GameServiceImpl gameService;
	
	@Autowired
	public StoryController(StoryServiceImpl storyService, GameServiceImpl gameService)
	{
		this.storyService = storyService;
		this.gameService = gameService;
	}
	
	@GetMapping("/stories/")
	public String indexStory(Model model) {
		model.addAttribute("stories", storyService.findAll());
		model.addAttribute("games", gameService.findAll());

		return "stories/index";
	}
	

	@GetMapping("/stories/list/{id}")
	public String indexStoriesOfGame(@PathVariable("id") long id,Model model) {
		model.addAttribute("stories", storyService.findAll());
		model.addAttribute("games", gameService.findAll());

		return "stories/index";
	}
	

	@GetMapping("stories/add")
	public String addStory(Model model)
	{
		model.addAttribute("story", new TsscStory());
		model.addAttribute("games", gameService.findAll());
		return "stories/add-story";
	}
	

	@PostMapping("stories/add")
	public String saveStory(@RequestParam(value = "action", required = true) String action, @Validated(StoryValidation.class) @ModelAttribute("story") TsscStory story,
			BindingResult bindingResult, Model model) throws NullStoryException, BusinessValueException, InitialSprintException, PriorityException, NullGameException, NotExistingGameException 
	{
		if(!action.equals("Cancelar"))
		{
			if(bindingResult.hasErrors())
			{
				model.addAttribute("games", gameService.findAll());
				return "stories/add-story";
			}else
			{
				storyService.saveStory(story, story.getTsscGame());
			}
		}else {
			return "stories/index";
		}
		return "redirect:/stories/";
	}
	
	@GetMapping("/stories/del/{id}")
	public String deleteStory(@PathVariable("id") long id) {
		TsscStory story =storyService.findStoryById(id);
		if(story == null)
			{
			 new IllegalArgumentException("Invalidad Story Id: "+id);
			}
		storyService.deleteStory(story);
		return "redirect:/stories/";
	}
	

	
	@GetMapping("/stories/edit/{id}")
	public String editStoryShowView(@PathVariable("id") long id, Model model) {
		TsscStory story =storyService.findStoryById(id);
		if(story == null)
			{
			 new IllegalArgumentException("Invalidad Story Id: "+id);
			}
		model.addAttribute("story", story);
		model.addAttribute("games", gameService.findAll());
		return "stories/edit-story";
	}
	
	@PostMapping("/stories/edit/{id}")
	public String editStory( @PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action, @Validated(StoryValidation.class) @ModelAttribute("story") TsscStory story, BindingResult bindingResult,
			Model model) throws NullStoryException, NotExistingStory, BusinessValueException, PriorityException, InitialSprintException, NotExistingGameException  {
		if (action != null && !action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {
				return "/stories/edit-story";
			} else {
				storyService.editStory(story);
			}
		}
		return "redirect:/stories/";
	}
	
	
	@GetMapping("/stories/getStories/{id}")
	public String showStoriesForGames(@PathVariable("id")long id, Model model)
	{
		model.addAttribute("stories", storyService.findByGame(id));
		return "stories/indexStoriesGame";
		
	}
}
