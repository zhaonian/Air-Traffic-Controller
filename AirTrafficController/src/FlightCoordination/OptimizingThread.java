package FlightCoordination;

import java.util.concurrent.Semaphore;

public class OptimizingThread extends Thread{
	Integer wfs; 
	String ctrl;
	Semaphore sem; 
	Placement p;
	public OptimizingThread(Integer wfs, String ctrl, Semaphore sem, Placement p){
		super("OptimizingThread_");
		this.wfs  = wfs;
		this.ctrl = ctrl;
		this.sem  = sem;
		this.p    = p;
	}
	public void run(){
		try {
			sem.acquire();
				Utils.optimizePlacement(wfs,ctrl,p);
			sem.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}
    public Placement getP(){
        return this.p;
    }
	public String getPid(){
		return p.getPid();
	}
	public double getExp(){
		return p.getFp().getExpectedValue();
	}
}
