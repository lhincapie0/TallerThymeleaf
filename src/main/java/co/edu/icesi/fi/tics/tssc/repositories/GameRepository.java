package co.edu.icesi.fi.tics.tssc.repositories;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;

public interface GameRepository {
	
	public TsscGame saveGame(TsscGame game);
	public TsscGame editGame(TsscGame game);
	public TsscGame getGame(Long id);

}
