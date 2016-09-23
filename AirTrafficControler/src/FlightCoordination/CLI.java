package FlightCoordination;

 import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.io.File;
import java.util.List;

import sun.nio.cs.StandardCharsets;

/** 
 * Command line component of the water fall composer.
 */
public class CLI {
    static HashMap<HashSet<String>, FlightPlan> memo;
	public static void runCLI(String[] args) throws IOException, InterruptedException {
	    Integer waterfallSizeLimit	 	    = null;
		String control           	  		= null;
		String inFileName        	  		= null;
		String outFileName      	  		= null;
		List<Flight> allFlights 	  		= null;
		List<Placement> allPlacements 		= null;
		
		//HashSet<String> memo = new HashSet<String>();
		memo = new HashMap<HashSet<String>, FlightPlan> (); 
		    
		if (args.length < 4) {
			System.out.println(ParameterCore.USAGE);
			System.err.println("Argument Error: There must be 4 arguments.");    
			System.exit(1);
		}
		else{
		    try {
		        control        		= args[0];
		    	waterfallSizeLimit  = Integer.valueOf(args[1]);
		    	inFileName     		= args[2];
		    	outFileName    		= args[3];
		    } catch (Exception e) {
		    	System.err.println("\nArgument Error: " + e.getMessage() + " "
		    			+ e.getLocalizedMessage() + " " + e.getCause());
		        e.printStackTrace();
		        System.exit(1);
		    }	
		    try {
		    	allFlights = Utils.readFlightsCSV(ParameterCore.csv, inFileName);
		    } catch (Exception e) {
		        System.out.println("Error reading from file: " + inFileName);
		    	System.err.println("\nInput File Error: " + e.getMessage() + " "
		    			+ e.getLocalizedMessage() + " " + e.getCause());
		        e.printStackTrace();
		        System.exit(1);
		    }
		    /**** Preprocess input data START****/
			    //Preprocess each flight plan. This fills in missing data items.
			    Utils.preProcessFlights (allFlights);
			    //Partition flights into Placements.
			    allPlacements = Utils.partitionToPlacements(allFlights);
			    //Lexicographic sort Placements.
			    Collections.sort(allPlacements);
		    /**** Preprocess input data END****/
		    
		    /*Run Optimization Start*/
			Utils.optimizePlacements(waterfallSizeLimit, control, allPlacements);
		    /*Run Optimization End*/
		    
		    /**** Output data after processing START****/
	    	List<Flight> writeList = new ArrayList<Flight> ();
	    	for (Placement p : allPlacements){
	    		writeList.addAll(p.getFp().getAsList());
	    		System.out.println(p.pid + " :::: " + p.fp.getExpectedValue() );
	    	}
		    Utils.writeFlightPlanCSV (ParameterCore.csv, outFileName, writeList);
		    System.out.println("Generated file ["+outFileName+"]" + " From data " + "[" + inFileName + "]");
		    /****Output data after processing END****/
		}
	}
	public static int getBinomial(int n, double p) {
	    int x = 0;
	    for(int i = 0; i < n; i++){
	        if( Math.random( ) < p ){
	            x++;
	        }
	    }
	    return x;
	}
	static jdistlib.rng.MersenneTwister twister = new jdistlib.rng.MersenneTwister( System.currentTimeMillis() + (long) Math.random( ) );
	public static Double[] simRun( Flight flight ){
        Double reward           = flight.getReward();        
        Double probSucc         = flight.getProbability();
        Double costFlipFail     = flight.getTimeAverageFailure();     
        Double costFlipSucc     = flight.getTimeAverageSuccess();
        Double costFlipFail_std = flight.getTimeStdDevFailure();
        Double costFlipSucc_std = flight.getTimeStdDevSuccess();
	    Double logNormVar_S     = Math.log( (Math.pow(costFlipSucc_std,2)/Math.pow(costFlipSucc,2)) + 1.0);
        Double logNormMean_S    = Math.log(costFlipSucc) - (logNormVar_S/2.0);
        Double successDelay     = jdistlib.LogNormal.random( logNormMean_S, Math.sqrt( logNormVar_S ), twister );
        Double logNormVar_F     = Math.log( (Math.pow(costFlipFail_std,2) / Math.pow(costFlipFail,2)) + 1.0);
        Double logNormMean_F    = Math.log( costFlipFail )-( logNormVar_F/2.0 );
        Double failureDelay     = jdistlib.LogNormal.random( logNormMean_F, Math.sqrt( logNormVar_F ), twister );
        
        boolean isSuccess = getBinomial(1, probSucc) > 0;
        
        if ( isSuccess ) {
            return new Double[] { reward, successDelay }; 
        }
        else{
            return new Double[] { 0.0, failureDelay };
        }
	}
	private static double calculateAverage(ArrayList<Double> marks) {
	    Double sum = 0.0;
	    if(!marks.isEmpty()) {
	        for (Double mark : marks) {
	            sum += mark;
	        }
	        return sum.doubleValue() / marks.size();
	    }
	    return sum;
	}
   private static double calculateAverage2(ArrayList<Integer> marks) {
        Double sum = 0.0;
        if(!marks.isEmpty()) {
            for (Integer mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }
	
    public static void runCLISim(String[] args) throws IOException, InterruptedException {
        Integer waterfallSizeLimit          = null;
        String control                      = null;
        String inFileName                   = null;
        String outFileName                  = null;
        List<Flight> allFlights             = null;
        List<Placement> allPlacements       = null;
        
        if (args.length < 4) {
            System.out.println(ParameterCore.USAGE);
            System.err.println("Argument Error: There must be 4 arguments.");    
            System.exit(1);
        }
        else{
            try {
                control             = args[0];
                waterfallSizeLimit  = Integer.valueOf(args[1]);
                inFileName          = args[2];
                outFileName         = args[3];
            } catch (Exception e) {
                System.err.println("\nArgument Error: " + e.getMessage() + " "
                        + e.getLocalizedMessage() + " " + e.getCause());
                e.printStackTrace();
                System.exit(1);
            }   
            try {
                allFlights = Utils.readFlightsCSV(ParameterCore.csv, inFileName);
            } catch (Exception e) {
                System.out.println("Error reading from file: " + inFileName);
                System.err.println("\nInput File Error: " + e.getMessage() + " "
                        + e.getLocalizedMessage() + " " + e.getCause());
                e.printStackTrace();
                System.exit(1);
            }
            
            /**** Preprocess input data START ****/
                //Preprocess each flight plan. This fills in missing data items.
                Utils.preProcessFlights (allFlights);
                //Partition flights into Placements.
                allPlacements = Utils.partitionToPlacements(allFlights);
                //Lexicographic sort Placements.
                Collections.sort(allPlacements);
            /**** Preprocess input data END ****/
            //waterfallSizeLimit = 100;
            
            class RunData {
                public Integer window;
                public Double averageReward;
                public Double averageVisitsBeforeFailure;
                public Double averageVisitsBeforeSuccess;
                public Double averagePathLengthBeforeFailure;
                public Double averagePathLengthBeforeSuccess;
                public Double averageTimeAtLastFail;
                public Double averageTimeAtLastSuccess;
                public Integer failCount;
                public Integer successCount;
                
                public String toString(){
                    return (window + "," + averageReward + "," + averageVisitsBeforeFailure + "," + averageVisitsBeforeSuccess + "," + averagePathLengthBeforeFailure + "," + averagePathLengthBeforeSuccess) + "," + averageTimeAtLastFail + "," + averageTimeAtLastSuccess + "," + failCount + "," + successCount;
                }
            }
            	            
            int cityCount = 0;
            double tMax = 0;
            HashMap<Integer,RunData> packs = new HashMap<Integer,RunData> ();
            
            for (Placement p : allPlacements){
                packs.clear();
                for (int j=-1; j<p.getFp().size(); j++){
                    RunData pack = new RunData();
                    pack.window = j;
                    packs.put(j, pack);
                }
                Integer maxWindow = 5;//p.getFp().size();
                HashMap<String, Double[]> drawStore = new HashMap<String, Double[]>();
                for ( int j=-1; j < maxWindow; j++ ){
                    if( j > -1 ){
                        Utils.optimizePlacement(Integer.MAX_VALUE, control, p);
                    }
                    else {
                        Utils.optimizePlacement(Integer.MAX_VALUE, control, p);
                        if( p.getFp().size() > 3 ){
                            Collections.shuffle( p.getFp().getAsList().subList(2, p.getFp().size()) );    
                        }
                    }
                    
                    ArrayList<Double> rewards = new ArrayList<Double> ();
                    ArrayList<Integer> visitsBeforeFailure = new ArrayList<Integer> ();
                    ArrayList<Integer> visitsBeforeSuccess = new ArrayList<Integer> ();
                    ArrayList<Double> pathLengthBeforeFailure = new ArrayList<Double> ();
                    ArrayList<Double> pathLengthBeforeSuccess = new ArrayList<Double> ();
                    ArrayList<Double> timeAtLastFailure = new ArrayList<Double> ();
                    ArrayList<Double> timeAtLastSuccess = new ArrayList<Double> ();
                    Integer failureCount = 0;
                    Integer successCount = 0;
                    
                    for (int i=0; i<waterfallSizeLimit; i++){
                        //System.out.println("S-"+i);
                        cityCount = p.getFp().size();
                        tMax = p.getFp().getAsList().get(0).getTimeMax();
                        FlightPlan myPlan = p.getFp().cloneFlightPlan();
                        ArrayList<String> myTrip = new ArrayList<String> ( );
                        double timeRemain = myPlan.getAsList().get(0).getTimeMax();
                        double timeMax = myPlan.getAsList().get(0).getTimeMax();
                        
                        boolean doStop = false;
                        while( !doStop ){        
                            String myL = "";
                            for (Flight f : myPlan){
                                myL += f.getFid() + " ";
                            }
                            Flight front = myPlan.getAsList().remove( 0 );
                            String key = front.getFid() + i;
                            
                            Double testArr[] = null;
                            if ( drawStore.containsKey(key) ){
                                testArr = drawStore.get(key);
                            }else{
                                testArr = simRun( front );
                                drawStore.put(key, testArr);
                            }
                            //System.out.println(j + " " + i + " Pick: " + front.getFid() + " " + testArr[0] + " " + testArr[1] + " From " + myL);
                            double prevTime = timeRemain;
                            timeRemain -= testArr[1];
                            
                            myTrip.add( front.getFid() );
                            if ( testArr[0] > 0.0 && timeRemain >= 0.0 ){
                                rewards.add( testArr[0] );
                                visitsBeforeSuccess.add( myTrip.size() );
                                pathLengthBeforeSuccess.add( timeMax - timeRemain );
                                timeAtLastSuccess.add( prevTime );
                                successCount += 1;
                                doStop = true;
                                break;
                            } 
                            else if ( myPlan.size() < 1 || timeRemain <= 0.0 ){
                                rewards.add( 0.0 );
                                visitsBeforeFailure.add( myTrip.size() );
                                pathLengthBeforeFailure.add( timeMax - timeRemain );
                                timeAtLastFailure.add( prevTime );
                                failureCount += 1;
                                doStop = true;
                                break;
                            }
                            if ( !doStop ) {
                                for( Flight f : myPlan ){
                                    f.setTimeMax( timeRemain );
                                }
                                if ( j > 0 ){
                                    Placement temp = new Placement( front.getPlacementToken(), myPlan.cloneFlightPlan() );
                                    //Max window size.
                                    //Utils.optimizePlacement( Integer.MAX_VALUE, control, temp );
                                    Utils.optimizePlacement( j, "b", temp );
                                    Flight best = temp.getFp().get(0);
                                    //System.out.println(best);
                                    myPlan.getAsList().remove(best);
                                    myPlan.getAsList().add(0, best);
                                }
                            }
                        }
                        String myL = "";
                        for (String s : myTrip){
                            myL += s + " ";
                        }
                        String fname = inFileName.substring( inFileName.lastIndexOf('/') + 1, inFileName.length() ); 
                        //System.out.println( fname + " " + j + " E-" + i + " " + myL + " " + rewards.get(rewards.size()-1));
                    }
                    
                    RunData rd = packs.get(j);
                    rd.averageReward = calculateAverage( rewards );
                    rd.averagePathLengthBeforeFailure = calculateAverage( pathLengthBeforeFailure );
                    rd.averagePathLengthBeforeSuccess = calculateAverage( pathLengthBeforeSuccess );
                    rd.averageTimeAtLastFail = calculateAverage( timeAtLastFailure );
                    rd.averageTimeAtLastSuccess = calculateAverage( timeAtLastSuccess );
                    rd.averageVisitsBeforeFailure = calculateAverage2( visitsBeforeFailure );
                    rd.averageVisitsBeforeSuccess = calculateAverage2( visitsBeforeSuccess );
                    rd.failCount = failureCount;
                    rd.successCount = successCount;
                
                    for (double avg : rewards){
                        //System.out.println("REW " + avg);
                    }
                    String fname = inFileName.substring( inFileName.lastIndexOf('/') + 1, inFileName.length() );
                    String outS = "";
                    outS += fname + "," + rd ;
                    
                    try {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outFileName, true)));
                        out.println( outS );
                        out.flush();
                        out.close();
                        System.out.println( outS );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}