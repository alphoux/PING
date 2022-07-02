package fr.epita.assistants.API.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.epita.assistants.API.response.ContentResponse;
import fr.epita.assistants.API.response.ProjectResponse;
import fr.epita.assistants.Singleton;

@RestController
public class Api {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/project/load")
	public ProjectResponse loadProject(@RequestParam String path){
		System.out.println("Loading " + path);
		Singleton instance;
		if (Singleton.alreadyInstantiated())
		{
			instance = Singleton.getInstance(null);
			instance.LoadPath(path);
		}
		else
		{
			instance = Singleton.getInstance(path);
		}
		return new ProjectResponse(path, instance.getRootNode());
	}
	/**
	 * MakeActive a file in the IDE
	 * @param path that represents a file
	 * @return Content of the file
	 */

	@GetMapping("/project/makeActive")
	public ContentResponse makeActive(@RequestParam String path) {
		String content = Singleton.getInstance(null).makeActive(path);
		return new ContentResponse(path, content);
	}

	@GetMapping("/project/getContent")
	public ContentResponse getContent(){
		Singleton instance = Singleton.getInstance(null);
		String content = instance.getContent();
		return new ContentResponse(instance.getRootNode().getPath().toString(), content);
	}
}