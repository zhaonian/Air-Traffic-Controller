package FlightCoordination;


import java.util.Iterator;
import java.util.List;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public class FlightCombinator implements Iterable<FlightPlan>{
	Iterator<ICombinatoricsVector<Flight>> it1;
	Iterator<ICombinatoricsVector<Flight>> it2;
	Generator<Flight> gen1;
    Generator<Flight> gen2;
	
	public class flightComboIterator implements Iterator<FlightPlan> {
	    public boolean hasNext() {
	    	if (it2 != null && it2.hasNext()){
	    		return it2.hasNext();
	    	}
	    	else if (it1.hasNext()){
	    		return it1.hasNext();
	    	}
	    	return false;
	    }
	    public FlightPlan next() {
	    	if( it2 != null && it2.hasNext() ){
		    	ICombinatoricsVector<FlightCoordination.Flight> temp = it2.next();
		    	List<Flight> flist = temp.getVector();
		    	return new FlightPlan(flist);
	    	}
	    	else if ( it1.hasNext() ){
		    	ICombinatoricsVector<FlightCoordination.Flight> temp = it1.next();
		    	List<Flight> flist = temp.getVector();
	    		ICombinatoricsVector<Flight> comboVec = Factory.createVector(flist);
	    		gen2 = Factory.createPermutationGenerator(comboVec);
	    		it2  = gen2.iterator();
		    	ICombinatoricsVector<FlightCoordination.Flight> temp2 = it2.next();
		    	List<Flight> flist2 = temp2.getVector();
		    	return new FlightPlan(flist2);
	    	}
	    	return null;
	    }
	    public void remove() {
	    	if (it2 != null && it2.hasNext()){
	    		it2.remove();
	    	}
	    	else if (it1.hasNext()){
	    		it1.remove();
	    	}
	    }
	}
	public FlightCombinator(List <Flight> lf, Integer r){
		ICombinatoricsVector<Flight> initialVector = Factory.createVector(lf);
		this.gen1 = Factory.createSimpleCombinationGenerator(initialVector, r);
	}
	public Iterator<FlightPlan> iterator() {
		this.it1 = this.gen1.iterator(); 
		return new flightComboIterator();
	}
}