package co.edu.icesi.fi.tics.tssc.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import co.edu.icesi.fi.tics.tssc.model.TsscSprint;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@Repository
public class StoryRepositoryImpl implements StoryRepository{

	private Map<Long, TsscStory> stories;	

	public StoryRepositoryImpl() {
		stories = new HashMap<Long, TsscStory>();
	}
	@Override
	public TsscStory saveStory(TsscStory story) {
		stories.put(story.getId(),story);
		return story;
	}

	@Override
	public TsscStory editStory(TsscStory story) {
		TsscStory existingStory = stories.get(story.getId());
		stories.replace(story.getId(), story, existingStory);
		return story;
	}
	
	@Override
	public TsscStory getStory(Long id) {
		return stories.get(id);
		
	}

}
