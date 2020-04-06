package co.edu.icesi.fi.tics.tssc.repositories;

import co.edu.icesi.fi.tics.tssc.model.TsscStory;

public interface StoryRepository {

	
	public TsscStory saveStory(TsscStory story);
	public TsscStory editStory(TsscStory story);
	public TsscStory getStory(Long id);
}
