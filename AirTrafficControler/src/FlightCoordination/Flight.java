package FlightCoordination;

public class Flight implements Comparable<Flight>{
	private String pTok		   ;
	private String fid		   ;
	private Double timeMax	   ;
	private Double reward	   ;
	private Double probability ;
	private Double timeAverageFailure ;
	private Double timeAverageSuccess ;
	private Double timeStdDevFailure ;
	private Double timeStdDevSuccess ;
	private Boolean noDat	   ;
	
	public Flight (String ptok, 
				   String fid, 
				   Double r, 
				   Double p, 
				   Double t_m, 
				   Double t_f, 
				   Double t_s,
				   Double t_f_std,
				   Double t_s_std){
		
		this.pTok	 	 		= ptok; 
		this.fid		 		= fid;
		this.reward 	 		= r;
		this.probability 		= p  ;
		this.timeMax     	 	= t_m; 
		this.timeAverageFailure = t_f;
		this.timeAverageSuccess = t_s;
		this.timeStdDevFailure  = t_f_std;
		this.timeStdDevSuccess  = t_s_std;
		this.noDat	     		= false;
	}
	/* Use to clone the object.
	 */
	public Flight (Flight cloneMe){
		this.pTok		 		= cloneMe.getPlacementToken();
		this.fid		 	 	= cloneMe.getFid();
		this.reward 	 		= cloneMe.getReward();
		this.probability  	 	= cloneMe.getProbability();
		this.timeMax    		= cloneMe.getTimeMax();
		this.timeAverageFailure = cloneMe.getTimeAverageFailure();
		this.timeAverageSuccess = cloneMe.getTimeAverageSuccess();
		this.timeStdDevFailure  = cloneMe.getTimeStdDevFailure();
		this.timeStdDevSuccess  = cloneMe.getTimeStdDevSuccess();
		this.noDat	     		= cloneMe.getNoDat();
	}
	public Double isolatedSuccessCDF(){
		Double normalCDF = jdistlib.Normal.cumulative(timeMax, timeAverageSuccess, this.timeStdDevSuccess); 
		return (reward*probability*normalCDF);
	}
	/*
	 Compares this object with the specified object for 
	 order. Returns a negative integer, zero, or a positive 
	 integer as this object is less than, equal to, or greater 
	 than the specified object. 
	 */
	public int compareTo(Flight other){
		Double cmp = 0.0;
		if (ParameterCore.BOOTSTRAP_ORDER == FlightPlanBootstrapOrderEnum.BY_EXPECT){
		    cmp = this.isolatedSuccessCDF() - other.isolatedSuccessCDF();
		}
		if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
    		cmp = this.getReward() - other.getReward();
    	    if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
    	        cmp = this.getProbability() - other.getProbability();
    		    if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
    		    	cmp = this.getTimeMax() - other.getTimeMax();
    			    if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
    			    	cmp = -1.0*(this.getTimeAverageFailure() - other.getTimeAverageFailure());
    				    if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
    				    	cmp = -1.0*(this.getTimeAverageSuccess() - other.getTimeAverageSuccess());
    	    			    if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
    	    			    	cmp = -1.0*(this.getTimeStdDevFailure() - other.getTimeStdDevFailure());
    	    				    if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
    	    				    	cmp = -1.0*(this.getTimeStdDevSuccess() - other.getTimeStdDevSuccess());
    	    				    }
    	    			    }
    				    }
    			    }
    		    }
    	    }
		}
		return (int) Math.signum(cmp);
	}
	public String toString(){
		return String.format("%-24s , %-8s , %8f , %8f ; %8f , %8f , %8f %8f , %8f\n",
				this.pTok,this.fid,this.reward,this.probability,this.timeMax,
				this.timeAverageFailure,this.timeAverageSuccess,
				this.timeStdDevFailure,this.timeStdDevSuccess);
	}
	@Override 
	public int hashCode(){
	    return this.fid.hashCode();
	}
	@Override 
	public boolean equals(Object other){
	    Flight postCast = (Flight) other; 
	    return this.fid.equals( postCast.fid );
	}
	public String getPlacementToken() {
		return pTok;
	}
	public String getFid(){
		return this.fid;
	}
	public Double getReward(){
		return this.reward;
	}
	public Double getProbability(){
		return this.probability;
	}
	public Double getTimeMax() {
		return timeMax;
	}
	public Boolean getNoDat() {
		return noDat;
	}
	public Double getTimeAverageFailure() {
		return timeAverageFailure;
	}
	public Double getTimeAverageSuccess() {
		return timeAverageSuccess;
	}
	public Double getTimeFailureStdDev() {
		return timeStdDevFailure;
	}
	public Double getTimeSuccessStdDev() {
		return timeStdDevSuccess;
	}
	public Double getTimeStdDevFailure() {
		return timeStdDevFailure;
	}
	public Double getTimeStdDevSuccess() {
		return timeStdDevSuccess;
	}
	public void setReward(Double r){
		this.reward = r;
	}
	public void setProbability(Double p){
		this.probability = p;
	}
	public void setTimeMax(Double timeMax) {
		this.timeMax = timeMax;
	}
	public void setTimeAverageFailure(Double timeFailure) {
		this.timeAverageFailure = timeFailure;
	}
	public void setTimeAverageSuccess(Double timeSuccess) {
		this.timeAverageSuccess = timeSuccess;
	}
	public void setTimeStdDevFailure(Double timeStdDevFailure) {
		this.timeStdDevFailure = timeStdDevFailure;
	}
	public void setTimeStdDevSuccess(Double timeStdDevSuccess) {
		this.timeStdDevSuccess = timeStdDevSuccess;
	}
	public void setNoDat(Boolean b){
		this.noDat = b;
	}
}