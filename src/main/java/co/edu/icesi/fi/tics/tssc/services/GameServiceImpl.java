package co.edu.icesi.fi.tics.tssc.services;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.dao.TsscGameDao;
import co.edu.icesi.fi.tics.tssc.dao.TsscTopicDao;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.GameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.TopicRepository;

@Service
public class GameServiceImpl implements GameService{

	/**@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private TopicRepository topicRepository;**/
	

	@Autowired
	private TsscTopicDao topicDao;
	@Autowired
	private TsscGameDao gameDao;
	
	@Override
	@Transactional
	public TsscGame saveGame(TsscGame game, TsscTopic topic) throws NotEnoughGroupsException, 
	NotEnoughSprintsException, NullGameException, NotExistingTopic{
		if(game!=null)
		{
			if(game.getNGroups()>0)
			{
				if(game.getNSprints()>0)
				{
					if(topic== null)
					{
						gameDao.save(game);
						return game;
					}else
					{
						if(topicDao.findById(topic.getId())!= null)
						{
							game.setTsscTopic(topic);
							game.setIdTopic(game.getTsscTopic().getId());

							gameDao.save(game);
							return game;	
						}else throw new NotExistingTopic();
					}
				}else throw new NotEnoughSprintsException();
			}else throw new NotEnoughGroupsException();
		}else throw new NullGameException();
	}

	@Override
	@Transactional
	public TsscGame editGame(TsscGame game) throws NotExistingGameException, NullGameException, NotEnoughGroupsException, NotEnoughSprintsException, NotExistingTopic {

		if(game != null)
		{
			TsscGame existingGame = gameDao.findById(game.getId());
			if(gameDao.findById(game.getId())!= null)
			{
				
				if(game.getNGroups()>0)
				{
					if(game.getNSprints()>0)
					{
						if(game.getTsscTopic() != null)
						{
							if(topicDao.findById(game.getTsscTopic().getId()) != null)
							{
								game.setIdTopic(game.getTsscTopic().getId());
								gameDao.update(game);
								return game;
							}else throw new NotExistingTopic();
						}else
						{
							gameDao.save(game);
							return game;
						}
					}else throw new NotEnoughSprintsException();
				}else throw new NotEnoughGroupsException();
				
			}else throw new NotExistingGameException();
			
		}else throw new NullGameException();
		
		
	}

	
	@Transactional
	public TsscGame saveGame2(TsscGame game, TsscTopic topic) throws NotEnoughGroupsException, 
	NotEnoughSprintsException, NullGameException, NotExistingTopic, NullTopicException{
		if(game!=null)
		{
			if(game.getNGroups()>0)
			{
				if(game.getNSprints()>0)
				{
					//The topic is obligatory, so we changed it, because it the first method it was optional.
					if(topic!= null)
					{
						if(topicDao.findById(topic.getId())!= null)
						{
						
							ArrayList<TsscStory> stories =(ArrayList<TsscStory>) topic.getTsscStories();
							ArrayList<TsscStory> st = new ArrayList<TsscStory>();
							for(int i = 0; i<stories.size();i++)
							{
								st.add(stories.get(i));
							}
							game.setTsscStories(st);
							ArrayList<TsscTimecontrol> timecontrols =(ArrayList<TsscTimecontrol>) topic.getTsscTimecontrols();
							ArrayList<TsscTimecontrol> tc = new ArrayList<TsscTimecontrol>();
							for(int i = 0;i<timecontrols.size();i++)
							{
								tc.add(timecontrols.get(i));
							}
							game.setTsscTimecontrol(tc);
							gameDao.save(game);
							return game;
						}else throw new NotExistingTopic();
					}else throw new NullTopicException();
					
				}else throw new NotEnoughSprintsException();
			}else throw new NotEnoughGroupsException();
		}else throw new NullGameException();
	}

	public Iterable<TsscGame> findAll()
	{
		return gameDao.findAll();
	}
	
	@Override
	public TsscGame findGameById(long id) {

		return gameDao.findById(id);
	}

	@Override
	public void deleteGame(TsscGame game) {
		gameDao.delete(game);
	}
	
	
	@Override
	public Iterable<TsscGame> findByIdTopic( long idTopic){
		return gameDao.findByTopic(idTopic);
	}

}
