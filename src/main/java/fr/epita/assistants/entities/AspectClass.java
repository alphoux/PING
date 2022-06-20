package fr.epita.assistants.entities;
import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static fr.epita.assistants.myide.domain.entity.Mandatory.Aspects.*;

public class AspectClass  implements Aspect {
    Aspect.Type type;

    public AspectClass (Type type) {
        this.type = type;
    }

    /**
     * @return The type of the Aspect.
     */
    public Aspect.Type getType() {
        return type;
    }

    /**
     * @return The list of features associated with the Aspect.
     */
    public @NotNull List<Feature> getFeatureList() {
        List<Feature> features = new ArrayList<>();

        switch (type) {
            case ANY:
                // Base features for a project
                for (Feature.Type feature : Mandatory.Features.Any.values()) {
                    features.add(new FeatureClass(feature));
                }
                break;
            case MAVEN:
                // Maven specific features
                for (Feature.Type feature : Mandatory.Features.Maven.values()) {
                    features.add(new FeatureClass(feature));
                }
            case GIT:
                // Git specific features
                for (Feature.Type feature : Mandatory.Features.Git.values()) {
                    features.add(new FeatureClass(feature));
                }
        }

        return features;
    }
}
