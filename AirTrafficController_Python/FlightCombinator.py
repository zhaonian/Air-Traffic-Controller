"""
This file contains the algorithm of how all the FlightPlan possibilities are calculated.
"""

import itertools
from math import factorial


class FlightCombinator:                                         # public class FlightCombinator implements Iterable<FlightPlan>{
    def __init__(self, lf, r):
        self.lf   = lf
        self.r    = r
        self.i    = 0
        self.comb = list(itertools.combinations(lf, r))

        self.perm = []
        for x in self.comb:                                     # Calculate all the possible combinations
            self.perm.extend(list(itertools.permutations(x)))   # and permutations and put them in a list.





    def has_next(self):
        """
        Test if a certain combination still has permutation.

        Return:
            a boolean expression of if a certain combination has run out of permutations or not.
        """
        return self.i < factorial(len(self.lf))/factorial(len(self.lf) -self.r)

    def get_next(self):
        """
        Get the permutation of a certain combination.

        Return:
            new_i: a permuted tuple
        """
        new_i = self.perm[self.i]
        self.i += 1
        return new_i


# def test_FC():
#    fc = FlightCombinator([2, 3,1,1, 1], 3)
#
#    while fc.has_next():
#        print(fc.get_next())
#
# test_FC()
#
#
