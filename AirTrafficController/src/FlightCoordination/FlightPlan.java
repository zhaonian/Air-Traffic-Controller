package FlightCoordination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FlightPlan implements Iterable<Flight>{
	public String probs = "";
    private List<Flight> plan;
	/* Use synch list for thread safety 
	 */
	public FlightPlan(List<Flight> plan){
		this.plan = Collections.synchronizedList(plan);
	}
	public FlightPlan(FlightPlan fp){
		this.plan = fp.getAsList();
	}
	public Double getExpectedValueDeltaFunc(){
		Double reward	        = this.plan.get(0).getReward();
		Double probSucc         = this.plan.get(0).getProbability();
		Double tMax             = this.plan.get(0).getTimeMax();         // T-max is same for all flights in a given placement.
		Double costFlipFail     = this.plan.get(0).getTimeAverageFailure();     // Mean Time used when flight 0 fails.
		Double costFlipSucc     = this.plan.get(0).getTimeAverageSuccess();     // Mean Time used when flight 0 succeeds.
		//Double costFlipFail_std =  this.plan.get(0).getTimeStdDevFailure();
		//Double costFlipSucc_std =  this.plan.get(0).getTimeStdDevSuccess();
		Double timeExpireCoef   = ( (costFlipSucc) < tMax) ?  ParameterCore.NO_PENELTY_COEFF : ParameterCore.TIME_OUT_PENELTY_COEFF/(ParameterCore.TIME_OUT_PENELTY_COEFF + costFlipSucc - tMax)  ;
		Double fullSum		    = (reward*probSucc*timeExpireCoef);
		Double probFailChain    = ( 1.0 - probSucc );
		Double costFailChain    = costFlipFail;
		for ( int i = 1; i < this.plan.size(); i++ ){
			reward	   	        = this.plan.get(i).getReward();
			probSucc            = this.plan.get(i).getProbability();
			tMax                = this.plan.get(i).getTimeMax();
			costFlipFail        = this.plan.get(i).getTimeAverageFailure();
			costFlipSucc        = this.plan.get(i).getTimeAverageSuccess();
			//costFlipFail_std    = this.plan.get(i).getTimeStdDevFailure();
			//costFlipSucc_std    = this.plan.get(i).getTimeStdDevSuccess();
			timeExpireCoef      = ( (costFlipSucc + costFailChain) < tMax) ?  ParameterCore.NO_PENELTY_COEFF : ParameterCore.TIME_OUT_PENELTY_COEFF/((ParameterCore.TIME_OUT_PENELTY_COEFF + costFlipSucc + costFailChain) - tMax);
			//fullSum 		 = fullSum.add(Utils.productHighPrecision(reward,probSucc,probFailChain,timeExpireCoef));
			fullSum 		   += (reward*probSucc*probFailChain*timeExpireCoef);
			probFailChain      *= ( 1.0 - probSucc );
			costFailChain      += costFlipFail;
		}
		return fullSum;
	}
	public Double getExpectedValueCDF(){
		Double reward	          = this.plan.get(0).getReward();		 
		Double probSucc           = this.plan.get(0).getProbability();
		Double tMax               = this.plan.get(0).getTimeMax();         // T-max is same for all flights in a given placement. It is a fixed value.
		Double costFlipFail       = this.plan.get(0).getTimeAverageFailure();     // Mean Time used when flight 0 fails.
		Double costFlipSucc       = this.plan.get(0).getTimeAverageSuccess();     // Mean Time used when flight 0 succeeds.
		Double costFlipFail_std   = this.plan.get(0).getTimeStdDevFailure();
		Double costFlipSucc_std   = this.plan.get(0).getTimeStdDevSuccess();

		//System.out.println(normalCDF);
		//Double normalCDF        = jdistlib.Normal.cumulative(tMax,costFlipSucc,costFlipSucc_std);
		//Double normalCDF        = Gaussian.Phi(tMax, costFlipSucc, ParameterCore.STD_DEV); 
		Double logNormVar		  = Math.log((Math.pow(costFlipSucc_std,2)/Math.pow(costFlipSucc,2)) + 1.0);
		Double logNormMean		  = Math.log(costFlipSucc)-(logNormVar/2.0);
		Double logNormalCDF       = jdistlib.LogNormal.cumulative(tMax, logNormMean, Math.sqrt(logNormVar), true, false);
		
		//System.out.println( "[" + tMax + "," + costFlipSucc + "," + costFlipSucc_std + "]= " + logNormMean + "," + logNormVar + "  " + logNormalCDF );
		
		
		Double fullSum		      = (reward*probSucc*logNormalCDF);
		Double probFailChain      = ( 1.0 - probSucc );
		Double costFailChain      = costFlipFail;
		Double normalCDFFailChain = (Math.pow(costFlipFail_std, 2));
		
		//System.out.println("LN-CDF: " + logNormalCDF);
		//System.out.println("T-Max: " + tMax);
		//System.out.println("F-Sum: " + fullSum + " " + this.plan.get(0).getFid() );
		this.probs = this.plan.get(0).getFid() + " S " + logNormalCDF + "," ;
		for ( int i = 1; i < this.plan.size(); i++ ){
			reward	   	          = this.plan.get(i).getReward();
			probSucc              = this.plan.get(i).getProbability();
			tMax                  = this.plan.get(i).getTimeMax();
			costFlipFail          = this.plan.get(i).getTimeAverageFailure();
			costFlipSucc     	  = this.plan.get(i).getTimeAverageSuccess();
			costFlipFail_std      = this.plan.get(i).getTimeStdDevFailure();
			costFlipSucc_std      = this.plan.get(i).getTimeStdDevSuccess();

			//Double stdTerm      = Math.sqrt(normalCDFFailChain + Math.pow(costFlipSucc_std, 2));
			//normalCDF           = jdistlib.Normal.cumulative(tMax,(costFlipSucc + costFailChain),stdTerm);
			//normalCDF           = Gaussian.Phi(tMax, (costFlipSucc + costFailChain), Math.sqrt(i+1)*ParameterCore.STD_DEV);
			logNormVar		      = Math.log(((normalCDFFailChain+Math.pow(costFlipSucc_std,2))/Math.pow(costFailChain+costFlipSucc,2)) + 1.0);
			logNormMean		      = Math.log(costFailChain+costFlipSucc)-(logNormVar/2.0);
			logNormalCDF          = jdistlib.LogNormal.cumulative(tMax, logNormMean, Math.sqrt(logNormVar), true, false);

		    this.probs           += this.plan.get(i).getFid() + " S " + logNormalCDF + "," ;

			fullSum 		     += (reward*probSucc*probFailChain*logNormalCDF);
			probFailChain        *= ( 1.0 - probSucc );
			costFailChain        += costFlipFail;
			normalCDFFailChain   += (Math.pow(costFlipFail_std, 2));
		}
	    if ( this.probs.length() > 0 ) {
	        this.probs = this.probs.substring(0, this.probs.length() - 1);
	    }
		return fullSum;
	}
	public Double getExpectedValue(){
	    if (this.plan.isEmpty()){
	        return 0.0;
	    }
		if (ParameterCore.UTILITY == UtilityFunctionEnum.CDF){
			return getExpectedValueCDF();
		}
		if (ParameterCore.UTILITY == UtilityFunctionEnum.DELTA){
			return getExpectedValueDeltaFunc();
		}
		throw new AssertionError();
	}
	/*
	 * Is partially ordered according to the ordering enforced by 
	 * Flight.compareTo ?
	 */
	public Boolean isAdmissible(){
		for ( int i = 0; i < this.plan.size(); i++ ){
			if( i < this.plan.size()-1 ){
				if (this.plan.get(i).compareTo(this.plan.get(i+1)) < 0 ){
					return false;
				}
			}
		}
		return true;
	}
	/*
	 * Binary sort to impose partial order. Put into decreasing order.
	 */
	public void makeAdmissible(){
		Collections.sort(this.plan);
		Collections.reverse(this.plan);
	}
	public FlightPlan cloneFlightPlan( ){
		List<Flight> cloneList = Collections.synchronizedList(new ArrayList<Flight>());
		for (Flight f : this.plan){
			cloneList.add( new Flight(f) );
		}
		return new FlightPlan(cloneList);
	}
	public String toString(){
		String agg = "";
		for (Flight f : this.plan){
			agg += f;
		}
		return agg;
	}
	/* To iterate through flight plan directly
	 */
	public Iterator<Flight> iterator() {
		return plan.iterator();
	}
	public List<Flight> getAsList(){
		return this.plan;
	}
	public Integer size(){
		return this.plan.size();
	}
	public Flight get(Integer i){
		return this.plan.get(i);
	}
	public void set(Integer i, Flight f){
		this.plan.set(i, f);
	}
	public void addFlight(Flight f) {
		this.plan.add(f);
	}
}
