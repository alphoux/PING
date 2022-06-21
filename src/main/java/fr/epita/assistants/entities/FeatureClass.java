package fr.epita.assistants.entities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Any;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Git;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Maven;
import fr.epita.assistants.myide.domain.entity.Project;

public class FeatureClass implements Feature{

    FeatureClass(Type type){
        this.type = type;
    }
    Type type;
    
    private void recursivefind(String tofind, Path dir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    recursivefind(tofind, file);
                } else {
                    BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
                    try {
                        String line = br.readLine();

                        while (line != null) {
                            if (line.contains(tofind)) {
                                System.out.println(file.toString()+" :" + line);
                            }
                            line = br.readLine();
                        }
                    } finally {
                        br.close();
                    }
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
        }
    }
    private void recursivedelete(List<String> files, Path dir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    recursivedelete(files, file);
                } else {
                    if (files.contains(file.toString())) {
                        Files.delete(file);
                    }
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
        }
    }

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        try {
        if (type == Any.CLEANUP)
        {

            List<String> content_file = Files.lines(Paths.get(project.getRootNode().getPath()+ "/.myideignore")).collect(Collectors.toList());
            recursivedelete(content_file, project.getRootNode().getPath());
        }
        else if (type == Any.DIST)
        {
            List<String> content_file = Files.lines(Paths.get(project.getRootNode().getPath()+ "/.myideignore")).collect(Collectors.toList());
            recursivedelete(content_file, project.getRootNode().getPath());
            Runtime.getRuntime().exec("tar -czf dist.tar.gz " + project.getRootNode().getPath());
        }
        else if (type == Any.SEARCH)
        {
            if (params.length == 0 && !(params[0] instanceof String))
            {
                return new ExecutionReport() {
                    @Override
                    public boolean isSuccess() {
                        return false;
                    }
                };
            }
            else
            {
                String tofind = (String)params[0];
                recursivefind(tofind, project.getRootNode().getPath());
            }
        }
        else if (type == Git.ADD)
        {
            org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.open(new File(project.getRootNode().getPath()+"/.git"));
            AddCommand add = git.add();
            for (Object param : params) {
                if (param instanceof String) {
                    add.addFilepattern((String) param);
                }
            }
            add.call();
        }
        else if (type == Git.PULL)
        {
            org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.open(new File(project.getRootNode().getPath()+"/.git"));
            git.pull().call();
        }
        else if (type == Git.COMMIT)
        {
            org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.open(new File(project.getRootNode().getPath()+"/.git"));
            CommitCommand commit = git.commit();
            if (params.length > 0 && params[0] instanceof String) {
                commit.setMessage((String) params[0]);
            }
            commit.call();

        }
        else if (type == Git.PUSH)
        {
            org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.open(new File(project.getRootNode().getPath()+"/.git"));
            git.push().call();
        }
        else if (type == Maven.COMPILE)
        {
            Runtime.getRuntime().exec("mvn compile");
        }
        else if (type == Maven.CLEAN)
        {
            Runtime.getRuntime().exec("mvn clean");
        }
        else if (type == Maven.TEST)
        {
            Runtime.getRuntime().exec("mvn test");

        }
        else if (type == Maven.PACKAGE)
        {
            Runtime.getRuntime().exec("mvn package");

        }
        else if (type == Maven.INSTALL)
        {
            Runtime.getRuntime().exec("mvn install");

        }
        else if (type == Maven.EXEC)
        {
            Runtime.getRuntime().exec("mvn exec");

        }
        else if (type == Maven.TREE) 
        {
            Runtime.getRuntime().exec("mvn tree");

        }
        else
        {
            return new ExecutionReport() {
                @Override
                public boolean isSuccess() {
                    return false;
                }
            };
        }
        return new ExecutionReport() {
            @Override
            public boolean isSuccess() {
                return true;
            }
        };
        } catch (Exception e) {
            return new ExecutionReport() {
                @Override
                public boolean isSuccess() {
                    return false;
                }
            };
        } 
    }

    @Override
    public @NotNull Type type() {
        return type;
    }
    
}