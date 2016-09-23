package FlightCoordination;

import au.com.bytecode.opencsv.CSV;

public final class ParameterCore {
	private ParameterCore(){
		throw new AssertionError();
	}
	//Per placement bootstrap the initial flightPlan e.g (order by comparison or at random).
	public static final FlightPlanBootstrapSplitEnum BOOTSTRAP_METHOD = FlightPlanBootstrapSplitEnum.ORDERED_SPLIT;
	//Chose the comparison operation for bootstrapping by comparison. Expected value of a success.
	public static final FlightPlanBootstrapOrderEnum BOOTSTRAP_ORDER  = FlightPlanBootstrapOrderEnum.BY_EXPECT;
	//Choice of utility function.
	public static final UtilityFunctionEnum UTILITY        			  = UtilityFunctionEnum.CDF;
	//Precision setting for testing for 0.
	public static final Double  CLOSE_TO_ZERO     		   			  = Double.MIN_VALUE; //0.00001;
	//If an improvement is found that is better by at least this value then continue search for another search window.
	public static final Double IMPROVEMENT_THRESHOLD 	   			  = Double.MIN_VALUE;
	//If there is no reward, the penalty coefficient for the flight (no data). Assuming they weren't filtered out. 
	public static final Double  NO_REWARD_DEMERIT_COEFF    		 	  = 0.01000;
	//If a time out will be encountered at a point in the waterfall, then this coefficient is applied from that point on if DELTA utility is used.
	//Another term proportional to the time take in excess of the maximum bound is also applied.
	public static final Double  TIME_OUT_PENELTY_COEFF     		 	  = 0.10000;
	//Coefficient if there is no penalty. Should be 1.0.
	public static final Double  NO_PENELTY_COEFF 	       		 	  = 1.00000;
	//Standard deviation for CDF if CDF used.
	//public static final Double STD_DEV  				   		 	  = 0.32000;
	//A modification of SLS, a random order is created with this probability. This is the base of the probability. A component is included that accounts for improvements within a window.
	//A higher number means more diversification, a lower number means more intensification.
    //The start setting for choosing what portion of explored solutions are local.
	public static final Double  PROB_RANDOR_BASE                      = 0.16000;
	//At least this probability of a random solution generation.
	public static final Double  PROB_RANDOR_MIN      		 	      = 0.00200;
	//If there is no improvement after LOCAL_SEARCH_WINDOW attempts terminate search.
	public static final Integer LOCAL_SEARCH_WINDOW 	   		 	  = 65536;
	//If the full size of flights for a given placement is below this bound, run brute force search.
	public static final Integer BRUTE_BOUND 	   		   		 	  = 1;
	//If the placement has more than this many flights in it, try to improve it. This must be greater than or equal to 1 or an infinite loop will happen.
	public static final Integer MIN_FLIGHTS_TO_TRY_IMPROVE 		 	  = 1;
	//For primitive parallelization.
	public static final Integer CORES							 	  = Runtime.getRuntime().availableProcessors();
	//Significant figures to print.
	public static final Integer SIG_FIGS                   		 	  = 8;
	//Debug mode.
	public static final Integer DEBUG                      		 	  = 0;
	//Run placements in parallel.
	public static final Boolean RUN_PARALLEL			  			  = true;
	//Remove flights with not CPM, or fill rate data from the flight plans.
	public static final Boolean SKIP_NODAT				   		  	  = true;
	/*CLI constants.*/
	//Format of CSV.
	public static final CSV csv                            		 	  = CSV.separator(',').noQuote().create();
	//The first row is skipped because it is a header.
	public static final Boolean SKIP_FIRST_ROW			   		  	  = true;
	//Run SLS on all placements.
	public static final String DO_SLS                      		 	  = "s";
	//Run BFS on all placements.
	public static final String DO_BFS                      		 	  = "b";
	//Runtime Log file. It is re-used for each run.
	public static final String  LOG_LOC		  			   		  	  = "./WO.log";
	public static final String USAGE = "Usage: ./atc.jar (s|b) waterfallSize inFile.csv outFile.csv\n" +
	        "Where s is the character literal \"s\" and indicates that SLS is to be run.\n" +
	        "Where b is the character literal \"b\" and indicates that brute force search is to be run.\n" +
			"Where waterfallSize is an integer indicating the maximum allowable size of the waterfall (put in a huge number for an unbounded waterfall).\n" +
			"Where inFile is the fully qualified name of the flights csv file, with read permissions.\n" +
			"Where each row of inFile but the first looks like: \"placementToken,flight_id,CPM,fillRateProbability,timeMaxInSec,meanTimeFailureInSec,meanTimeSuccessInSec,stdDevTimeFailureInSec,stdDevTimeSuccessInSec\".\n" +
			"The first row of the file is assumed to have header data and is skipped.\n" + 
			"Where outFile is the fully qualified name of the flight output csv file with write permissions.\n" +
			"Where csv delimiter is ','.\n" +
			"Where csv has no quote character.\n" +
			"***Warning, running brute force search for even fairly small values is intractable and will run *forever*.";
}