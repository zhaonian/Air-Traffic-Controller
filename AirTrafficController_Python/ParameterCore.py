"""
This file contains all the core constant variables. Organize all of them into ParameterCore class.
"""

from FlightPlanBootstrapSplitEnum import FlightPlanBootstrapSplitEnum
from FlightPlanBootstrapOrderEnum import FlightPlanBootstrapOrderEnum
from UtilityFunctionEnum import UtilityFunctionEnum
import csv
import multiprocessing

class ParameterCore:                                                                           # public final class ParameterCore {
    BOOTSTRAP_METHOD           = FlightPlanBootstrapSplitEnum.ORDERED_SPLIT                    # public static final FlightPlanBootstrapSplitEnum BOOTSTRAP_METHOD = FlightPlanBootstrapSplitEnum.ORDERED_SPLIT;
    BOOTSTRAP_ORDER            = FlightPlanBootstrapOrderEnum.BY_EXPECT                        # public static final FlightPlanBootstrapOrderEnum BOOTSTRAP_ORDER  = FlightPlanBootstrapOrderEnum.BY_EXPECT;
    UTILITY                    = UtilityFunctionEnum.CDF                                       # public static final UtilityFunctionEnum UTILITY        			  = UtilityFunctionEnum.CDF;
    CLOSE_TO_ZERO              = 0.00001                                                       # public static final Double  CLOSE_TO_ZERO     		   			  = Double.MIN_VALUE; //0.00001;
    IMPROVEMENT_THRESHOLD      = 0.00001                                                       # public static final Double IMPROVEMENT_THRESHOLD 	   			  = Double.MIN_VALUE;
    NO_REWARD_DEMERIT_COEFF    = 0.01000                                                       # public static final Double  NO_REWARD_DEMERIT_COEFF    		 	  = 0.01000;
    TIME_OUT_PENELTY_COEFF     = 0.10000                                                       # public static final Double  TIME_OUT_PENELTY_COEFF     		 	  = 0.10000;
    NO_PENELTY_COEFF           = 1.00000                                                       # public static final Double  NO_PENELTY_COEFF 	       		 	  = 1.00000;
    PROB_RANDOR_BASE           = 0.16000                                                       # public static final Double  PROB_RANDOR_BASE                      = 0.16000;
    PROB_RANDOR_MIN            = 0.00200                                                       # public static final Double  PROB_RANDOR_MIN      		 	      = 0.00200;
    LOCAL_SEARCH_WINDOW        = 65536                                                         # public static final Integer LOCAL_SEARCH_WINDOW 	   		 	  = 65536;
    BRUTE_BOUND                = 1                                                             # public static final Integer BRUTE_BOUND 	   		   		 	  = 1;
    MIN_FLIGHTS_TO_TRY_IMPROVE = 1                                                             # public static final Integer MIN_FLIGHTS_TO_TRY_IMPROVE 		 	  = 1;
    CORES                      = multiprocessing.cpu_count()                                   # public static final Integer CORES							 	  = Runtime.getRuntime().availableProcessors();
    SIG_FIGS                   = 8                                                             # public static final Integer SIG_FIGS                   		 	  = 8;
    DEBUG                      = 0                                                             # public static final Integer DEBUG                      		 	  = 0;
    RUN_PARALLEL               = True                                                          # public static final Boolean RUN_PARALLEL			  			  = true;
    SKIP_NODAT                 = True                                                          # public static final Boolean SKIP_NODAT				   		  	  = true;
    csv = csv.writer("result.csv", delimiter=',', quoting=csv.QUOTE_NONE)                      # public static final CSV csv                            		 	  = CSV.separator(',').noQuote().create();
    SKIP_FIRST_ROW             = True                                                          # public static final Boolean SKIP_FIRST_ROW			   		  	  = true;
    DO_SLS                     = "s"                                                           # public static final String DO_SLS                      		 	  = "s";
    DO_BFS                     = "b"                                                           # public static final String DO_BFS                      		 	  = "b";
    LOG_LOC                    = "./WO.log"                                                    # public static final String  LOG_LOC		  			   		  	  = "./WO.log";

    USAGE                      = "Usage: ./Main.py (s|b) waterfallSize inFile.csv outFile.csv\n" \
                                          + "Where s is the character literal \"s\" and indicates that SLS is to be run.\n" \
                                          + "Where b is the character literal \"b\" and indicates that brute force search is to be run.\n" \
                                          + "Where waterfallSize is an integer indicating the maximum allowable size of the waterfall (put in a huge number for an unbounded waterfall).\n" \
                                          + "Where inFile is the fully qualified name of the flights csv file, with read permissions.\n" \
                                          + "Where each row of inFile but the first looks like: \"placementToken,flight_id,CPM,fillRateProbability,timeMaxInSec,meanTimeFailureInSec,meanTimeSuccessInSec,stdDevTimeFailureInSec,stdDevTimeSuccessInSec\".\n" \
                                          + "The first row of the file is assumed to have header data and is skipped.\n" \
                                          + "Where outFile is the fully qualified name of the flight output csv file with write permissions.\n" \
                                          + "Where csv delimiter is ','.\n" \
                                          + "Where csv has no quote character.\n" \
                                          + "***Warning, running brute force search for even fairly small values is intractable and will run *forever*."
