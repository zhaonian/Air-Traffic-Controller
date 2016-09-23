## import Java.util.Iterator;
import FlightPlan


class FlightCombinator:                                                     # public class FlightCombinator implements Iterable<FlightPlan>{
    def __init__(self):
        return

    it1 = None                                                             # Iterator < ICombinatoricsVector < Flight >> it1;
    it2 = None                                                             # Iterator < ICombinatoricsVector < Flight >> it2;
    gen1 = None                                                            # Generator < Flight > gen1;
    gen2 = None                                                            # Generator < Flight > gen2;

    class flightComboIterator:                                              # public class flightComboIterator implements Iterator<FlightPlan> {
        def __init__(self):
            return

        def hasNext(self):                                                           # public boolean hasNext() {
            if (FlightCombinator.it2 != None and FlightCombinator.it2.hasNext()):            # if (it2 != null && it2.hasNext()){
                return FlightCombinator.it2.hasNext()                                        # return it2.hasNext();

            elif FlightCombinator.it2.hasNext():                                             # else if (it1.hasNext()){
                return FlightCombinator.it1.hasNext()                                        # return it1.hasNext();

            return False                                                                     # return false;


        def next(self):                                     # public FlightPlan next() {
            if (FlightCombinator.it2 == None) \
                    and (FlightCombinator.it2hasNext()):                    # if( it2 != null && it2.hasNext() ){
                temp = FlightCombinator.it2.next()                          # ICombinatoricsVector<FlightCoordination.Flight> temp = it2.next();
                flist = temp.getVector()                                    # List<Flight> flist = temp.getVector();
                return FlightPlan.FlightPlan(flist)                                    # return new FlightPlan(flist);

            elif FlightCombinator.it1.hasNext():                            # else if ( it1.hasNext() ){
                temp = FlightCombinator.it1.next()                          # ICombinatoricsVector<FlightCoordination.Flight> temp = it1.next();
                flist = temp.getVector()                                    # List<Flight> flist = temp.getVector();
                comboVec = Factory.createVector(flist)                      # ICombinatoricsVector<Flight> comboVec = Factory.createVector(flist);
                gen2 = Factory.createPermutationGenerator(comboVec)         # gen2 = Factory.createPermutationGenerator(comboVec);
                it2 = gen2.iterator()                                       # it2  = gen2.iterator();
                temp2 = it2.next()                                          # ICombinatoricsVector<FlightCoordination.Flight> temp2 = it2.next();
                flist2 = temp2.getVector()                                  # List<Flight> flist2 = temp2.getVector();
                return FlightPlan.FlightPlan(flist2)                                   # return new FlightPlan(flist2);

            return None                                                     # return null;

        def remove(self):                                           # public void remove() {
            if FlightCombinator.it2 != None \
                    and FlightCombinator.it2.hasNext():                     # if (it2 != null && it2.hasNext()){
                FlightCombinator.it2.remove()                               # it2.remove();

            elif FlightCombinator.it1.hasNext():                            # else if (it1.hasNext()){
                FlightCombinator.it1.remove()                               # it1.remove();


# 	public FlightCombinator(List <Flight> lf, Integer r){
# 		ICombinatoricsVector<Flight> initialVector = Factory.createVector(lf);
# 		this.gen1 = Factory.createSimpleCombinationGenerator(initialVector, r);
# 	}
# 	public Iterator<FlightPlan> iterator() {
# 		this.it1 = this.gen1.iterator();
# 		return new flightComboIterator();
# 	}




