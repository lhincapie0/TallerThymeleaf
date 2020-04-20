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

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.GameValidation;
import co.edu.icesi.fi.tics.tssc.model.TopicValidation;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.AdminServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

@Controller
public class GameController {
	
	private GameServiceImpl gameService;
	private TopicServiceImpl topicService;
	
	@Autowired
	public GameController(	GameServiceImpl gameService, TopicServiceImpl topicService)
	{
		this.gameService = gameService;
		this.topicService = topicService;
	}
	

	@GetMapping("/games/")
	public String indexUser(Model model) {
		model.addAttribute("games", gameService.findAll());
		model.addAttribute("topics", topicService.findAll());
		return "games/index";
	}

	@GetMapping("games/add")
	public String addGame(Model model)
	{
		model.addAttribute("game", new TsscGame());
		TsscTopic mock = new TsscTopic();
		mock.setName("No relacionar tema");
		model.addAttribute("topics", topicService.findAll());
		model.addAttribute("mock", mock);
		return "games/add-game";
	}
	
	@PostMapping("games/add")
	public String saveTopic(@RequestParam(value = "action", required = true) String action, @Validated(GameValidation.class) @ModelAttribute("game") TsscGame game,
			BindingResult bindingResult, Model model) throws NotEnoughGroupsException, NotEnoughSprintsException, NullGameException, NotExistingTopic 
	{
		if(!action.equals("Cancelar"))
		{
			if(bindingResult.hasErrors())
			{
				TsscTopic mock = new TsscTopic();
				mock.setName("No relacionar tema");
				model.addAttribute("topics", topicService.findAll());
				model.addAttribute("mock", mock);
				return "games/add-game";
			}else
			{
				gameService.saveGame(game, game.getTsscTopic());
			}
		}else {
			return "games/index";
		}
		return "redirect:/games/";
	}
	
	@GetMapping("/games/del/{id}")
	public String deleteGame(@PathVariable("id") long id) {
		TsscGame game = gameService.findGameById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Game Id: "+ id));
		gameService.deleteGame(game);
		return "redirect:/games/";
	}
	

	
	@GetMapping("/games/edit/{id}")
	public String editGameShowView(@PathVariable("id") long id, Model model) {
		Optional<TsscGame> game = gameService.findGameById(id);
		if (game == null)
			throw new IllegalArgumentException("Invalid Game Id: " + id);
		model.addAttribute("game", game.get());
		model.addAttribute("topics", topicService.findAll());
		return "games/edit-game";
	}
	
	
	@PostMapping("/games/edit/{id}")
	public String editGame( @PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action, @Validated(GameValidation.class) @ModelAttribute("game") TsscGame game, BindingResult bindingResult,
			Model model) throws NotExistingGameException, NullGameException, NotEnoughGroupsException, NotEnoughSprintsException, NotExistingTopic {
		if (action != null && !action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {
				return "/games/edit-game";
			} else {
				gameService.editGame(game);
			}
		}
		return "redirect:/games/";
	}

	
	@GetMapping("/games/getGames/{id}")
	public String getGamesByTopic(@PathVariable long id, Model model)
	{
		model.addAttribute("games", gameService.findByIdTopic(id));
		return "games/indexGamesTopic";
	}

	
	

}
