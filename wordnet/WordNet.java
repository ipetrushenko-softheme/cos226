/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WordNet {
    private Digraph digraph;
    private ShortestCommonAncestor SCA;
    private HashMap<Integer, String> idToSynset;
    private HashMap<String, List<Integer>> nounToIds;

    public WordNet(String synsets, String hypernyms) throws FileNotFoundException {
        Scanner synsetScanner = new Scanner(new File(synsets));
        idToSynset = new HashMap<Integer, String>();
        nounToIds = new HashMap<String, List<Integer>>();

        while (synsetScanner.hasNextLine()) {
            String[] relationships = synsetScanner.nextLine().split(",");
            int id = Integer.parseInt(relationships[0]);
            String synset = relationships[1];
            idToSynset.put(id, synset);

            String[] nouns = synset.split(" ");
            for (String noun : nouns) {
                List<Integer> ids = new LinkedList<Integer>();
                if (nounToIds.containsKey(noun)) {
                    ids = nounToIds.get(noun);
                }
                ids.add(id);
                nounToIds.put(noun, ids);
            }
        }

        Scanner hypernymsScanner = new Scanner(new File(hypernyms));
        digraph = new Digraph(idToSynset.size());

        while (hypernymsScanner.hasNextLine()) {
            String[] relationships = hypernymsScanner.nextLine().split(",");
            int v = Integer.parseInt(relationships[0]);

            for (int i = 1; i < relationships.length; ++i) {
                int w = Integer.parseInt(relationships[i]);
                digraph.addEdge(v, w);
            }
        }

        SCA = new ShortestCommonAncestor(digraph);
        synsetScanner.close();
        hypernymsScanner.close();
    }

    // all WordNet nouns
    public Iterable<String> nouns() {
        return nounToIds.keySet();
    }

    // all WordNet synsets
    public Iterable<String> synsets() {
        return idToSynset.values();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounToIds.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        List<Integer> a = nounToIds.get(noun1);
        List<Integer> b = nounToIds.get(noun2);
        int id;
        if (a.size() == 1 && b.size() == 1) {
            id = SCA.ancestor(a.get(0), b.get(0));
        }
        else {
            id = SCA.ancestorSubset(a, b);
        }

        return idToSynset.get(id);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        List<Integer> a = nounToIds.get(noun1);
        List<Integer> b = nounToIds.get(noun2);
        return SCA.lengthSubset(a, b);
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(wordNet.distance("white_marlin", "mileage"));
        System.out.println(wordNet.distance("Black_Plague", "black_marlin"));
        System.out.println(wordNet.distance("American_water_spaniel", "histology"));
        System.out.println(wordNet.distance("Brown_Swiss", "barrel_roll"));
    }
}
