## import Java.util.Iterator;
#import FlightPlan
import itertools
from math import factorial


class FlightCombinator:                                                     # public class FlightCombinator implements Iterable<FlightPlan>{
    def __init__(self, lf, r):
        self.lf = lf
        self.r  = r
        self.i  = 0
        self.comb = list(itertools.combinations(lf, r))

        self.perm = []
        for x in self.comb:
            self.perm.extend(list(itertools.permutations(x)))



    def has_next(self):
        return self.i < factorial(len(self.lf))/factorial(len(self.lf) -self.r)

    def get_next(self):
        new_i = self.perm[self.i]
        self.i += 1
        return new_i

def test_FC():
    fc = FlightCombinator([1,2,3,5,6,7], 4)
    while fc.has_next():
        print(fc.get_next())


#test_FC()

