public class CompoundSubstituent {

    String substituentName = ""; // subtituent name of the compound
    Element[] compound; // element array that represents the skeleton of the compound
    Element sourceCarbon; // the carbon to which this substituent will be connected to

    CompoundSubstituent(String substituentName, Element sourceCarbon) {
        this.substituentName = substituentName.toLowerCase();
        this.sourceCarbon = sourceCarbon;
        getOrganicCompoundSubstituent();
    }

    void getOrganicCompoundSubstituent() {

        String prefix = Helper.getAlkylName(substituentName);
        switch (prefix) {
            case "methyl":
                constructSubstituentSkeleton(1);
                break;
            case "ethyl":
                constructSubstituentSkeleton(2);
                break;
            case "propyl":
                constructSubstituentSkeleton(3);
                break;
            case "butyl":
                constructSubstituentSkeleton(4);
                break;
            case "pentyl":
                constructSubstituentSkeleton(5);
                break;
            case "hexyl":
                constructSubstituentSkeleton(6);
                break;
            case "heptyl":
                constructSubstituentSkeleton(7);
                break;
            case "octyl":
                constructSubstituentSkeleton(8);
                break;
            case "nonyl":
                constructSubstituentSkeleton(9);
                break;
            case "decyl":
                constructSubstituentSkeleton(10);
                break;
        }

    }

    void constructSubstituentSkeleton(int skeletonSize) {

        compound = new Element[skeletonSize];

        Element[] startChainConnections = new Element[4];
        startChainConnections[0] = sourceCarbon;
        startChainConnections[1] = new Element("H", 1);
        startChainConnections[2] = new Element("H", 1);

        if(compound[0] == null) {
            compound[0] = new Element("C", 4);
        }

        if(skeletonSize == 1) {
            startChainConnections[3] = new Element("H", 1);
            compound[0].setConnections(startChainConnections);
            return;
        }
        else {
            if(compound[1] == null) {
                compound[1] = new Element("C", 4);
            }
            startChainConnections[3] = compound[1];
            compound[0].setConnections(startChainConnections);
        }

        Element[] endChainConnections = new Element[4];

        if(compound[compound.length - 2] == null)
            compound[compound.length - 2] = new Element("C", 4);

        endChainConnections[0] = compound[compound.length - 2];
        endChainConnections[1] = new Element("H", 1);
        endChainConnections[2] = new Element("H", 1);
        endChainConnections[3] = new Element("H", 1);

        if(compound[compound.length - 1] == null)
            compound[compound.length - 1] = new Element("C", 4);

        compound[compound.length - 1].setConnections(endChainConnections);

        for(int i = 1; i < compound.length - 1; i++) {
            if (compound[i] == null) {
                compound[i] = new Element("C", 4);
            }
            if (compound[i + 1] == null && i + 1 < compound.length) {
                compound[i + 1] = new Element("C", 4);
            }

            // allocate space for the connections of the carbon that lies in the middle
            Element[] midChainConnections = new Element[4];
            midChainConnections[0] = compound[i - 1]; // first connection would be the carbon that lies to the left
            midChainConnections[3] = compound[i + 1]; // fourth connection would be the carbon that lies to the right
            // second and third connections would just be hydrogen by default
            midChainConnections[1] = new Element("H", 1);
            midChainConnections[2] = new Element("H", 1);

            // set connection for the ith carbon
            compound[i].setConnections(midChainConnections);
        }

    }

}