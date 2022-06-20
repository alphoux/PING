package fr.epita.assistants.entities;

import fr.epita.assistants.myide.domain.entity.Node;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class NodeClass implements Node {

    private Path path_;
    private List<Node> childrens_ = new ArrayList<>();
    private Types type_;

    public NodeClass(Path path) {

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

}
