import java.util.*;

/*
* Class that contains various static helper functions / variables to be used in other classes.
*/
public class Helper {

    /*
    * elementReferenceMap contains the symbol and valency of various substituents as the value, with the key being
    * how the substituents are represented in IUPAC names
    * the entire structure of this map is subject to change in the future
    */
    public static Map<String, Pair> elementReferenceMap = initializeElementReferenceMap();

    private static Map<String, Pair> initializeElementReferenceMap() {
        Map<String, Pair> map = new HashMap<>();
        map.put("chloro", new Pair("Cl", 1));
        map.put("fluoro", new Pair("F", 8));
        map.put("bromo", new Pair("Br", 6));
        return map;
    }

    // function that returns the alkane name from the given IUPAC name, if it exists
    public static String getAlkaneName(String name) {

        if (name == null) {
            throw new RuntimeException("Invalid Name! (name = null)");
        }

        String[] alkanes = {
                "methane", "ethane", "propane", "butane", "pentane",
                "hexane", "heptane", "octane", "nonane", "decane"
        };

        // IUPAC names have the alkane names at the end, so we use the ends with function for each of
        // the possible IUPAC name
        for (String alkane : alkanes) {
            if (name.endsWith(alkane)) {
                return alkane;
            }
        }

        return "";

    }


    // function that extracts the substituents and their positions from the given IUPAC name
    // and returns them in a hash map
    public static LinkedHashMap<String, List<Integer>> getSubstituents(String name) {

        // the names of substituents end when the name of the alkane start
        // eg: 1,2-Dichloroethane, where all characters before 'ethane' represent the substituent positions and names
        // thus we see where the name of the alkanes start and remove it

        if (name == null) {
            throw new RuntimeException("Invalid Name! (name = null)");
        }

        String[] alkanes = {
                "methane", "ethane", "propane", "butane", "pentane",
                "hexane", "heptane", "octane", "nonane", "decane"
        };

        int index = -1;
        for (String alkane : alkanes) {
            if (name.endsWith(alkane)) {
                index = name.indexOf(alkane); // get the index where the name of the alkanes start
                break;
            }
        }

        if(index == -1) {
            throw new RuntimeException("Alkane name not found!\n");
        }

        // create a substring that does not contain the alkane name, and remove the hyphens for easier processing
        String substituentsString = name.substring(0, index).replace("-", "");

        // create a new hash map to store the substitient names as key and positions as value
        LinkedHashMap<String, List<Integer>> substituents = new LinkedHashMap<>();

        // positions is a list of integers
        List<Integer> positions = new ArrayList<>();

        // iterate through each character of the substituents string
        int i = 0;
        while (i < substituentsString.length()) {
            // if a character is a digit, that means the positions will be given in the next few characters
            if (Character.isDigit(substituentsString.charAt(i))) {
                // thus the positions for a substituent ends if the character at j is not a number or a comma
                // positions are divided with a comma delimiter in IUPAC names
                // eg: 1,2-Dichloroethane, where 1 and 2 are positions and are divided using a comma
                int j = i;
                while (j < substituentsString.length() && (Character.isDigit(substituentsString.charAt(j)) || substituentsString.charAt(j) == ',')) {
                    j++;
                }

                // since the comma is delimiter, we can split the substring into an array of strings using the split function
                String[] nums = substituentsString.substring(i, j).split(",");

                // however, now positions are in string datatype, we need them in integer datatype thus we
                // convert them to integers and add them to the positions list
                for (String num : nums) {
                    positions.add(Integer.parseInt(num));
                }

                // i now stars from j (after positions have been noted down)
                i = j;
            }
            // if a character is not a digit, that means the name of a subtituent will be given in the next few characters
            else {
                // thus the name of a substituent ends if the character at j is a number or a comma
                int j = i;
                while (j < substituentsString.length() && !Character.isDigit(substituentsString.charAt(j)) && substituentsString.charAt(j) != ',') {
                    j++;
                }
                String substituentName = substituentsString.substring(i, j);

                // place the substituent positions as value and substituent name in the hash map
                // this is being done after finding substituent name as in IUPAC names substituent names are
                // always given after their positions
                substituents.put(substituentName, new ArrayList<>(positions));

                // we clear the positions list for storing the positions of other substituents later on as we iterate
                // through the IUPAC name's characters
                positions.clear();

                // i now starts from j (after name has been noted down)
                i = j;
            }
        }

        // return the hashmap
        return substituents;

    }

    // function that increments the frequency of a key in the given hashmap, where the value is the list
    public static void incrementFrequency(Map<List<Integer>, Integer> map, List<Integer> list) {

        List<Integer> key = new ArrayList<>(list); // make a new list to prevent modification issues

        // if the map already has the value at given key, it takes the value, or it takes the value to be 0
        // and increments it, and places it the new value at the key
        map.put(key, map.getOrDefault(key, 0) + 1);

    }

}
