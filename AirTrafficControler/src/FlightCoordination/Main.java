package FlightCoordination;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public final class Main {
	public static void main(String[] args) {
		/*  */
		try{
			//CLI.runCLI(args);
		    Testing.runTestXe();
		    CLI.runCLISim(args);
		}
		catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} 
		catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Testing.runTestOne();
		//Testing.runTestTwo();
		//Testing.runTestThree();
		//Testing.runTestFour();
		//Testing.runTestFive();
		//Testing.runTestSix();
		
		//Testing.runTestSeven();
		//Testing.runTestEight();
		//Testing.runTestNine();
		//Testing.runTestTen();
		//Testing.runTestEleven();
		//Testing.runTestTwelve();
	}
}
