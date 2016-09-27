"""
This is where I start the test. Run the project here!

I tried to translate the files as similar to the original Java codes as possible.
I named all the files same names as the Java ones, and most of the Python functions work in similar ways as the Java ones do, and
I also tried to organize the functions in the same order as the ones in Java.

Some additional Python libraries I used: Scipy, Numpy, itertools.

I used these libraries to calculate some mathematical and statistical data such as
        Cumulative Distribution Function (scipy.stats.lognorm.cdf in FlightPlan.py Line 74),
        RNG (numpy.random.shuffle in FlightPlanCoordinator.py Line 47),
        Combinations and Permutations (itertools.combinations in FlightCombinator.py Line 10) and or so...

"""

import Testing
import CLI

#if __name__ == '__main__':
def Main():
    """
    Choose the test file you want to run here
    """

    # class Main:                                                             # public final class Main
    #     def __init__(self):                                                 # public static void main(String[] args)
    #         return
    #
    #     def main(self, *args):

    """
        try:
            Testing.Testing().runTestXe()                                # Testing.runTestXe();
            CLI.CLI().runCLISim(args)                                    # CLI.runCLISim(args);

        except Exception as e2:                                          # catch (FileNotFoundException e2)
            print(e2)                                                    # e2.printStackTrace();

        #except UnsupportedEncodingException as e2:                      # catch (UnsupportedEncodingException e2)
        #    e2.printStackTrace()                                        # e2.printStackTrace();

        #except IOException as e:                                        # catch (IOException e)
        #    e.printStackTrace()                                         # e.printStackTrace();

        #except InterruptedException as e:                               # catch (InterruptedException e)
        #    e.printStackTrace()                                         # e.printStackTrace()
    """

    #Testing.Testing().runTestOne()
    # Testing.Testing().runTestTwo()
    # Testing.Testing().runTestThree()
    # Testing.Testing().runTestFour()
    # Testing.Testing().runTestFive()
    # Testing.Testing().runTestSix()

    #Testing.runTestSeven()
    #Testing.runTestEight()
    #Testing.runTestNine()
    #Testing.runTestTen()
    #Testing.runTestEleven()
    #Testing.runTestTwelve()

    Testing.Testing().runTestZero() ### home made test!

Main()



