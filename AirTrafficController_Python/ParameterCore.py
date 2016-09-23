## import au.com.bytecode.opencsv.CSV;
import FlightPlanBootstrapSplitEnum
import FlightPlanBootstrapOrderEnum
import UtilityFunctionEnum


class ParameterCore:                                                                                    # public final class ParameterCore {
    def __init__(self, *args):
        self.BOOTSTRAP_METHOD           = FlightPlanBootstrapSplitEnum.ORDERED_SPLIT                    # public static final FlightPlanBootstrapSplitEnum BOOTSTRAP_METHOD = FlightPlanBootstrapSplitEnum.ORDERED_SPLIT;
        self.BOOTSTRAP_ORDER            = FlightPlanBootstrapOrderEnum.BY_EXPECT                        # public static final FlightPlanBootstrapOrderEnum BOOTSTRAP_ORDER  = FlightPlanBootstrapOrderEnum.BY_EXPECT;
        self.UTILITY                    = UtilityFunctionEnum.CDF                                       # public static final UtilityFunctionEnum UTILITY        			  = UtilityFunctionEnum.CDF;
        self.CLOSE_TO_ZERO              = 0.00001                                                       # public static final Double  CLOSE_TO_ZERO     		   			  = Double.MIN_VALUE; //0.00001;
        self.IMPROVEMENT_THRESHOLD      = 0.00001                                                       # public static final Double IMPROVEMENT_THRESHOLD 	   			  = Double.MIN_VALUE;
        self.NO_REWARD_DEMERIT_COEFF    = 0.01000                                                       # public static final Double  NO_REWARD_DEMERIT_COEFF    		 	  = 0.01000;
        self.TIME_OUT_PENELTY_COEFF     = 0.10000                                                       # public static final Double  TIME_OUT_PENELTY_COEFF     		 	  = 0.10000;
        self.NO_PENELTY_COEFF           = 1.00000                                                       # public static final Double  NO_PENELTY_COEFF 	       		 	  = 1.00000;
        self.PROB_RANDOR_BASE           = 0.16000                                                       # public static final Double  PROB_RANDOR_BASE                      = 0.16000;
        self.PROB_RANDOR_MIN            = 0.00200                                                       # public static final Double  PROB_RANDOR_MIN      		 	      = 0.00200;
        self.LOCAL_SEARCH_WINDOW        = 65536                                                         # public static final Integer LOCAL_SEARCH_WINDOW 	   		 	  = 65536;
        self.BRUTE_BOUND                = 1                                                             # public static final Integer BRUTE_BOUND 	   		   		 	  = 1;
        self.MIN_FLIGHTS_TO_TRY_IMPROVE = 1                                                             # public static final Integer MIN_FLIGHTS_TO_TRY_IMPROVE 		 	  = 1;
        #self.CORES                      = Runtime.getRuntime().availableProcessors();                  # public static final Integer CORES							 	  = Runtime.getRuntime().availableProcessors();
        self.SIG_FIGS                   = 8                                                             # public static final Integer SIG_FIGS                   		 	  = 8;
        self.DEBUG                      = 0                                                             # public static final Integer DEBUG                      		 	  = 0;
        self.RUN_PARALLEL               = True                                                          # public static final Boolean RUN_PARALLEL			  			  = true;
        self.SKIP_NODAT                 = True                                                          # public static final Boolean SKIP_NODAT				   		  	  = true;
        #self.csv                        = CSV.separator(',').noQuote().create()                        # public static final CSV csv                            		 	  = CSV.separator(',').noQuote().create();
        self.SKIP_FIRST_ROW             = True                                                          # public static final Boolean SKIP_FIRST_ROW			   		  	  = true;
        self.DO_SLS                     = "s"                                                           # public static final String DO_SLS                      		 	  = "s";
        self.DO_BFS                     = "b"                                                           # public static final String DO_BFS                      		 	  = "b";
        self.LOG_LOC                    = "./WO.log"                                                    # public static final String  LOG_LOC		  			   		  	  = "./WO.log";
        self.USAGE                      = "Usage: ./atc.jar (s|b) waterfallSize inFile.csv outFile.csv\n" \
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