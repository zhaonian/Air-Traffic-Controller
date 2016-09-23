package FlightCoordination;

public class Placement implements Comparable<Placement>{
	String	   pid; //Placement Identifier.
	FlightPlan fp ; //Flight plan for the placement.
	
	public int compareTo(Placement other){
		return this.pid.compareTo(other.getPid());
	}
	//public Placement (String pid){
	//	this.pid = pid;
	//}
	public Placement (String pid, FlightPlan fp){
		this.pid = pid;
		this.fp  = fp;
	}
	public FlightPlan setFp(FlightPlan fp){
		if (fp == null){
			throw new AssertionError();
		}
		return this.fp = fp;
	}
	public String getPid(){
		return this.pid;
	}
	public FlightPlan getFp(){
		return this.fp;
	}
	public void addFlight(Flight f) {
		this.fp.addFlight(f);
	}
	public int size() {
		return fp.size();
	}
}