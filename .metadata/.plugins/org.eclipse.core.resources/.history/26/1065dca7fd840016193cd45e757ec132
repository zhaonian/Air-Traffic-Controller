/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlightCoordination;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

/**
 *
 * @author Risin
 */
public class TestCombi {
    
    public static void main(String args[])
    {
        List<Integer> l = new  ArrayList<>();
        l.add(4);
        l.add(5);
        l.add(6);
        l.add(1);
        l.add(1);
        l.add(1);
        
        ICombinatoricsVector<Integer> initialVector = Factory.createVector(l);
	Generator<Integer> gen = Factory.createSimpleCombinationGenerator(initialVector, 3);
        
        Iterator<ICombinatoricsVector<Integer>> it = gen.iterator();
        while (it.hasNext()) {
            List<Integer> temp = it.next().getVector();
	    ICombinatoricsVector<Integer> comboVec = Factory.createVector(temp);
            Generator<Integer> gen2 = Factory.createPermutationGenerator(comboVec);
            Iterator<ICombinatoricsVector<Integer>> it2 = gen2.iterator();
            while (it2.hasNext()) {
                System.out.println(it2.next());
            }
        }
    }
}
