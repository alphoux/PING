package fr.epita.assistants.API.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.epita.assistants.API.response.ContentResponse;
import fr.epita.assistants.Singleton;

@RestController
public class Api {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/load")
	public String loadProject(@RequestParam String path){
		System.out.println("Loading " + path);
		if (Singleton.alreadyInstantiated())
		{
			Singleton.getInstance(null).LoadPath(path);
		}
		else
		{
			Singleton.getInstance(path);
		}
		return "Successfull Loaded project";
	}
	/**
	 * MakeActive a file in the IDE
	 * @param path that represents a file
	 * @return Content of the file
	 */

	@GetMapping("/makeActive")
	public ContentResponse makeActive(@RequestParam String path) {
		String content = Singleton.getInstance(null).makeActive(path);
		return new ContentResponse(content);
	}

}