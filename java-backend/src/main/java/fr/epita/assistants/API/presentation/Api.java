package fr.epita.assistants.API.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.CrossOrigin;
import fr.epita.assistants.API.presentation.contentDTO;
import fr.epita.assistants.API.response.ContentResponse;
import fr.epita.assistants.API.response.ProjectResponse;
import fr.epita.assistants.Singleton;
import fr.epita.assistants.API.response.ExecutionReportResponse;
import fr.epita.assistants.LSP.LspClient;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
		return new ProjectResponse(path, instance.getRootNode(), instance.getAspects());
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

	@GetMapping("/project/getStructure")
	public ProjectResponse getStructure()
	{
		Singleton instance = Singleton.getInstance(null);
		return new ProjectResponse(instance.getPath().toString(), instance.getRootNode(), instance.getAspects());
	}

	@GetMapping("/project/getContent")
	public ContentResponse getContent(){
		Singleton instance = Singleton.getInstance(null);
		String content = instance.getContent();
		return new ContentResponse(instance.getCurrentNode().getPath().toString(), content);
	}

	@PostMapping("/project/updateContent")
	public ContentResponse updateContent(@RequestBody contentDTO newContent) {
		Singleton instance = Singleton.getInstance(null);
		String content = instance.updateContent(newContent.getContent());
		return new ContentResponse(instance.getCurrentNode().getPath().toString(), content);
	}

	@GetMapping("/maven/build")
	public ExecutionReportResponse mavenBuild() {
		return new ExecutionReportResponse(Singleton.getInstance(null).mavenBuild().isSuccess());
	}

	@GetMapping("/maven/run")
	public ExecutionReportResponse mavenRun() {
		return new ExecutionReportResponse(Singleton.getInstance(null).mavenRun().isSuccess());
	}

	@GetMapping("/project/deleteCurrent")
	public ContentResponse deleteCurrent() {
		Singleton instance = Singleton.getInstance(null);
		String content = instance.deleteCurrent();
		return new ContentResponse(instance.getCurrentNode().getPath().toString(), content);
	}

	@GetMapping("/project/deleteFile")
	public ContentResponse deleteFile(@RequestParam String path) {
		Singleton instance = Singleton.getInstance(null);
		String content = instance.deleteFile(path);
		return new ContentResponse(instance.getCurrentNode().getPath().toString(), content);
	}

	@GetMapping("/project/createFile")
	public ContentResponse createFile(@RequestParam String name) {
		Singleton instance = Singleton.getInstance(null);
		instance.createFile(name);
		return new ContentResponse("", "");
	}

	@GetMapping("/project/createDir")
	public ContentResponse createDir(@RequestParam String parentPath, @RequestParam String name) {
		Singleton instance = Singleton.getInstance(null);
		instance.createDir(parentPath, name);
		return new ContentResponse(parentPath, "");
	}
}