package fr.epita.assistants.entities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.PullCommand;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Any;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Git;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Maven;
import fr.epita.assistants.myide.domain.entity.Project;

public class FeatureClass implements Feature{
    
    Type type;
    
    FeatureClass(Type type){
        this.type = type;
    }

    String concat_args(Object[] params) {
        String ret = "";
        for (Object arg : params) {
            if (ret instanceof String) {
                ret += " " + arg;
            }
        }
        return ret;
    }
    
    private void recursivefind(String tofind, Path dir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    recursivefind(tofind, file);
                } else {
                    BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
                    try {
                        String line = br.readLine();
                        int i = 0;
                        while (line != null) {
                            if (line.contains(tofind)) {
                                System.out.println(file.toString()+" Line nbr: "+i+" :" + line);
                            }
                            line = br.readLine();
                            i++;
                        }
                    } finally {
                        br.close();
                    }
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
        }
    }

    private void exec_cmd(String cmd,String dir)
    {
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("/bin/sh","-c","cd " + dir + " && " + cmd + " && cd -");

        try {
            Process process = processBuilder.start();
            
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            int exitCode = process.waitFor();
        } catch (Exception e) {
        } 
    }


    private void recursivedelete(List<String> files, Path dir, Path root) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                for (String line : files) {
                    if (file.toString().equals(root + "/" + line)) {
                        if (Files.isDirectory(file)) {
                            Files.walk(file)
                                    .sorted(Comparator.reverseOrder())
                                    .map(Path::toFile)
                                    .forEach(File::delete);
                        }
                        else
                        {
                        Files.delete(file);
                        }
                    } else {
                        if (Files.isDirectory(file)) {
                            recursivedelete(files, file, root);
                        }
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
            recursivedelete(content_file, project.getRootNode().getPath(),project.getRootNode().getPath());
        }
        else if (type == Any.DIST)
        {
            List<String> content_file = Files.lines(Paths.get(project.getRootNode().getPath()+ "/.myideignore")).collect(Collectors.toList());
            recursivedelete(content_file, project.getRootNode().getPath(),project.getRootNode().getPath());
            final Path sourceDir = project.getRootNode().getPath();
            String zipFileName = project.getRootNode().getPath().toString().concat(".zip");
            
            final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                    try {
                        Path targetFile = sourceDir.relativize(file);
                        outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
                        byte[] bytes = Files.readAllBytes(file);
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.closeEntry();
                    } catch (IOException e) {
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            outputStream.close();
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
            PullCommand pull = git.pull();
            pull.call();
        }
        else if (type == Git.COMMIT)
        {
            org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.open(new File(project.getRootNode().getPath()+"/.git"));
            CommitCommand commit = git.commit();
            commit.setMessage(concat_args(params));
            commit.call();

        }
        else if (type == Git.PUSH)
        {
            org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.open(new File(project.getRootNode().getPath()+"/.git"));
            git.push().call();
        }
        else if (type == Maven.COMPILE)
        {
            exec_cmd("mvn compile" + concat_args(params),project.getRootNode().getPath().toString());
        }
        else if (type == Maven.CLEAN)
        {
            exec_cmd("mvn clean" + concat_args(params),project.getRootNode().getPath().toString());
        }
        else if (type == Maven.TEST)
        {
            exec_cmd("mvn test" + concat_args(params),project.getRootNode().getPath().toString());
        }
        else if (type == Maven.PACKAGE)
        {
            exec_cmd("mvn package" + concat_args(params),project.getRootNode().getPath().toString());

        }
        else if (type == Maven.INSTALL)
        {
            exec_cmd("mvn install" + concat_args(params),project.getRootNode().getPath().toString());
        }
        else if (type == Maven.EXEC)
        {
            exec_cmd("mvn exec"+ concat_args(params),project.getRootNode().getPath().toString());

        }
        else if (type == Maven.TREE) 
        {
            exec_cmd("mvn dependency:tree" + concat_args(params),project.getRootNode().getPath().toString());

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
