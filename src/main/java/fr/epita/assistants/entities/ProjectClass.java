package fr.epita.assistants.entities;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;

import java.util.Optional;
import java.util.Set;

public class ProjectClass implements Project {

    private Node rootNode_;
    private Set<Aspect> aspects_;
    private Optional<Feature> features_;

    public ProjectClass(Node rootNode) {
        rootNode_ = rootNode;
    }
    @Override
    public Node getRootNode() {
        return rootNode_;
    }

    @Override
    public Set<Aspect> getAspects() {
        return null;
    }

    @Override
    public Optional<Feature> getFeature(Feature.Type featureType) {
        return Optional.empty();
    }
}
