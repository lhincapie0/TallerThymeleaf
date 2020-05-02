package co.edu.icesi.fi.tics.tssc.services;


import java.util.Optional;

import org.springframework.data.repository.query.Param;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface GameService {

	public TsscGame saveGame(TsscGame game, TsscTopic topic) throws 
	NotEnoughGroupsException, NotEnoughSprintsException, NullGameException, NotExistingTopic;
	public TsscGame editGame(TsscGame game) throws NotExistingGameException, NullGameException, NotEnoughGroupsException, NotEnoughSprintsException, NotExistingTopic;
	public Iterable<TsscGame> findAll();
	public TsscGame findGameById(long id);
	public void deleteGame(TsscGame game);
	public Iterable<TsscGame> findByIdTopic( long idTopic);

}
