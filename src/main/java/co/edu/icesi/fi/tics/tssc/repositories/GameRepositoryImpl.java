package co.edu.icesi.fi.tics.tssc.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;

@Repository
public class GameRepositoryImpl implements GameRepository {

	private Map<Long, TsscGame> games;	
	
	public GameRepositoryImpl() {
		games = new HashMap<Long, TsscGame>();
	}

	
	
	@Override
	public TsscGame saveGame(TsscGame game) {
		
		games.put(game.getId(), game);
		return game;

	}

	@Override
	public TsscGame editGame(TsscGame game) {
		TsscGame existingGame = games.get(game.getId());
		games.replace(game.getId(), game, existingGame);
		return game;
	}



	@Override
	public TsscGame getGame(Long id) {
		return games.get(id);
	}

}
