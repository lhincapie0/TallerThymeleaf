package co.edu.icesi.fi.tics.tssc.services;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.dao.TsscGameDao;
import co.edu.icesi.fi.tics.tssc.dao.TsscStoryDao;
import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingStory;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.repositories.GameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.StoryRepository;

@Service
public class StoryServiceImpl implements StoryService{
	
	/**@Autowired
	private StoryRepository storyRepository;
	@Autowired
	private GameRepository gameRepository;**/
	
	@Autowired
	private TsscStoryDao storyDao;
	
	@Autowired
	private TsscGameDao gameDao;

	@Transactional
	@Override
	public TsscStory saveStory(TsscStory story, TsscGame game) throws NullStoryException, BusinessValueException, InitialSprintException, PriorityException, NullGameException, NotExistingGameException {
		
		if(story != null)
		{
			if((story.getBusinessValue().compareTo(BigDecimal.ZERO)!=0))
			 {
				if((story.getInitialSprint().compareTo(BigDecimal.ZERO)!=0))
				 {
					if((story.getPriority().compareTo(BigDecimal.ZERO)!=0))
					 {
						if(game != null)
						{
							if(gameDao.findById(game.getId())!= null)
							{
								story.setTsscGame(game);
								story.setIdGame(game.getId());
								storyDao.save(story);
								return story;
							}else throw new NotExistingGameException();
						
						}else throw new NullGameException();
						
					}else throw new PriorityException();

				}else throw new InitialSprintException();

			}else throw new BusinessValueException();
		}else throw new NullStoryException();
	}

	@Override
	@Transactional
	public TsscStory editStory(TsscStory story) throws NullStoryException, NotExistingStory,BusinessValueException, PriorityException, InitialSprintException, NotExistingGameException {
		if(story != null)
		{
			if(storyDao.findById(story.getId())!= null)
			{
				TsscStory existingStory = storyDao.findById(story.getId());
				if((story.getBusinessValue().compareTo(BigDecimal.ZERO)!=0))
				{
					if((story.getInitialSprint().compareTo(BigDecimal.ZERO)!=0))
					{
						if((story.getPriority().compareTo(BigDecimal.ZERO)!=0))
						{
								if(gameDao.findById(story.getTsscGame().getId())!= null)
								{
									story.setTsscGame(story.getTsscGame());
									story.setIdGame(story.getTsscGame().getId());
									storyDao.update(story);
									
									return story;
								}else throw new NotExistingGameException();
							
						}else throw new PriorityException();
					}else throw new InitialSprintException();

				}else throw new BusinessValueException();
					
			}else throw new NotExistingStory();

		}else throw new NullStoryException();
		
	}
	
	@Override
	public Iterable<TsscStory> findAll()
	{
		return storyDao.findAll();
	}
	
	@Override
	public TsscStory findStoryById(long id) {

		return storyDao.findById(id);
	}

	@Override
	public void deleteStory(TsscStory story)
	{
		storyDao.delete(story);
	}
	
	@Override
	public Iterable<TsscStory> findByGame(long id){
		return storyDao.findByGame(id);
	}
	
}
