package fr.epita.assistants.entities;

import fr.epita.assistants.myide.domain.entity.Node;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class NodeClass implements Node {

    public static int population = 0;
    private Path path_;
    private List<Node> childrens_ = new ArrayList<>();
    private Types type_;
    private int id_;
    public NodeClass(Path path) {
        this.id_ = population;
        population++;
        path_ = path;
        File dir = new File(path.toString());
        File[] content = dir.listFiles();
        if (content == null) // it means it's a file
        {
            type_ = Types.FILE;
        }
        else
        {
            type_ = Types.FOLDER;
            for (File f : content)
            {
                childrens_.add(new NodeClass(f.toPath()));
            }
        }
    }

    @Override
    public Path getPath() {
        return path_;
    }

    @Override
    public Type getType() {
        return type_;
    }

    @Override
    public List<@NotNull Node> getChildren() {
        return childrens_;
    }

    @Override
    public int getId() {
        return this.id_;
    }

}
