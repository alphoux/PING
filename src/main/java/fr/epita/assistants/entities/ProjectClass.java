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
    private Set<Feature> features_;

    public ProjectClass(Node rootNode, Set<Aspect> aspects) {
        rootNode_ = rootNode;
        aspects_ = aspects;

        try {
            for (Aspect aspect : aspects) {
                features_.addAll(aspect.getFeatureList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Node getRootNode() {
        return rootNode_;
    }

    @Override
    public Set<Aspect> getAspects() {
        return aspects_;
    }

    @Override
    public Optional<Feature> getFeature(Feature.Type featureType) {
        return features_.stream().filter(feature -> feature.type() == featureType).findFirst();
    }
}
