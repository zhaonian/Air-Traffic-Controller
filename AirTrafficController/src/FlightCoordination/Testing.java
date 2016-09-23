package FlightCoordination;

import java.util.ArrayList;
import java.util.Random;

public class Testing {
	static Random r = new Random();
	public static Double RandFloat(Double rangeMin, Double rangeMax){
		return new Double( rangeMin + (rangeMax - rangeMin) * r.nextDouble() ) ;
	}
	private static Double RandFloat(int i, int j) { 
		return RandFloat( Double.valueOf(i), Double.valueOf(j) );
	}
	static void runTestTwo() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("A", "eeny",1.63,0.05854485, 4.0,0.32344483,0.30113836,0.05,0.05));
		flights.add(new Flight("A", "meany", 1.22,0.05854485, 4.0,0.37196133,0.42373474,0.05,0.05));
		flights.add(new Flight("A", "miny", 0.95,0.05854485, 4.0,0.31246721,0.29693714,0.05,0.05));
		flights.add(new Flight("A", "moe",0.83,0.05854485, 4.0,0.3752598,0.44219739,0.05,0.05));
		flights.add(new Flight("A", "alan",0.49,0.05854485, 4.0,0.29347719,0.2925326,0.05,0.05));
		flights.add(new Flight("A", "quxtor",0.06,0.05854485, 4.0,0.87964961,1.13957305,0.05,0.05));
		flights.add(new Flight("A", "ASC",0.38,0.05854485, 4.0,0.3675989,0.39454905,0.05,0.05));
		flights.add(new Flight("A", "bim",0.05,0.05854485, 4.0,0.64982964,1.10665875,0.05,0.05));
		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp.getExpectedValue());
	}
   static void runTestXe() {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        flights.add(new Flight("0eb8b0dedb77de2167db6ba5", "S5", 0.72,    0.0345,  0.421875, 0.27282894966268917,0.29824280440496576,0.09169717566535723,0.09355136815084707));
        flights.add(new Flight("0eb8b0dedb77de2167db6ba5", "S9",1.07,0.0253,0.421875,0.6560800617036124,0.5547780990600586,0.9246118648703918,0.5475421832328597));
        FlightPlan fp = new FlightPlan(flights);
        System.out.println(fp.getExpectedValue());
    }
	static void runTestThree() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("0eb8b0dedb77de2167db6ba5", "4326", 0.49,	0.042, 4.0,0.2925326,0.29347719,0.05,0.05));
		flights.add(new Flight("0eb8b0dedb77de2167db6ba5", "3033", 0.49,	0.02785, 4.0,734.49725816,101.35715702,0.05,0.05));
		flights.add(new Flight("0eb8b0dedb77de2167db6ba5", "4164", 0.38,	0.0499, 4.0,0.39454905,0.3675989,0.05,0.05));
		flights.add(new Flight("0eb8b0dedb77de2167db6ba5", "4556", 0.06,	0.0541, 4.0,1.13957305,0.87964961,0.05,0.05));

		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp.getExpectedValue());
	}
	static void runTestFour() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3028",	1.63,	0.0541,	 4.0,0.30113836,	0.32344483,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3030",	1.22,	0.0541,	 4.0,0.42373474,	0.37196133,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3029",	0.95,	0.0588,	 4.0,0.29693714,	0.31246721,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3032",	0.66,	0.02785,	 4.0,0.42034578,	0.38505907,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3031",	0.83,	0.02785,	 4.0,0.44219739,	0.3752598,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"4326",	0.49,	0.02785,	 4.0,0.2925326,	0.29347719,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"4164",	0.38,	0.02785,	 4.0,0.39454905,	0.3675989,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3033",	0.49,	0.1429,	 4.0,734.49725816,	101.35715702,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"4383",	0.89,	0.0313,	 4.0,1.25553429,	0.74496123,0.05,0.05));

		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
	}
	static void runTestFive() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3028",	1.63,	0.0541,	 4.0, 0.30113836,	0.32344483,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3030",	1.22,	0.0541,	 4.0,0.42373474,	0.37196133,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3029",	0.95,	0.0588,	 4.0,0.29693714,	0.31246721,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3032",	0.66,	0.02785,	 4.0,0.42034578,	0.38505907,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3031",	0.83,	0.02785,	 4.0,0.44219739,	0.3752598,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"4326",	0.49,	0.02785,	 4.0,0.2925326,	0.29347719,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"4164",	0.38,	0.02785,	 4.0,0.39454905,	0.3675989,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"4383",	0.89,	0.0313,	 4.0,1.25553429,	0.74496123,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3033",	0.49,	0.1429,	 4.0,734.49725816,	101.35715702,0.05,0.05));

		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
	}
	static void runTestSix() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3028",	1.63,	0.0541, 4.0,	0.30113836,	0.32344483,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3030",	1.22,	0.0541,	4.0,0.42373474,	0.37196133,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3029",	0.95,	0.0588, 4.0,	0.29693714,	0.31246721,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3032",	0.66,	0.02785, 4.0,	0.42034578,	0.38505907,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3031",	0.83,	0.02785, 4.0,	0.44219739,	0.3752598,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"4326",	0.49,	0.02785, 4.0,	0.2925326,	0.29347719,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"3033",	0.49,	0.1429,	 4.0, 734.49725816,	101.35715702,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"4164",	0.38,	0.02785, 4.0,	0.39454905,	0.3675989,0.05,0.05));
		flights.add(new Flight("251182d52b07c4cefff065e7",	"4383",	0.89,	0.0313,	4.0, 1.25553429,	0.74496123,0.05,0.05));

		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
	}
	static void runTestSeven() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("9234fd317191d086cbdd3c41","3028",1.63,0.02785,4.0,0.3011384,0.3234448,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","3030",1.22,0.02785,4.0,0.4237347,0.3719613,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","3029",0.95,0.02785,4.0,0.2969371,0.3124672,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","3032",0.66,0.02785,4.0,0.4203458,0.3850591,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","3031",0.83,0.02785,4.0,0.4421974,0.3752598,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","4326",0.49,0.02785,4.0,0.2925326,0.2934772,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","4644",0.08,0.02785,4.0,0.2028612,0.2950972,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","4164",0.38,0.02785,4.0,0.3945491,0.3675989,0.05,0.05));
		
		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
	}
	static void runTestEight() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("9234fd317191d086cbdd3c41","3028",1.63,0.02785,4.0,0.3011384,0.3234448,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","3030",1.22,0.02785,4.0,0.4237347,0.3719613,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","3029",0.95,0.02785,4.0,0.2969371,0.3124672,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","3031",0.83,0.02785,4.0,0.4421974,0.3752598,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","3032",0.66,0.02785,4.0,0.4203458,0.3850591,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","4326",0.49,0.02785,4.0,0.2925326,0.2934772,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","4644",0.08,0.02785,4.0,0.2028612,0.2950972,0.05,0.05));
		flights.add(new Flight("9234fd317191d086cbdd3c41","4164",0.38,0.02785,4.0,0.3945491,0.3675989,0.05,0.05));
		
		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
	}
	static void runTestNine() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3028",1.63, 0.0208,4.0, 0.3011384, 0.3234448,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3030",1.22, 0.0383,4.0, 0.4237347, 0.3719613,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3029",0.95, 0.0124,4.0, 0.2969371, 0.3124672,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","4164",0.38, 0.0268,4.0, 0.3945491, 0.3675989,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3031",0.83, 0.0126,4.0, 0.4421974, 0.3752598,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","4326",0.49, 0.0071,4.0, 0.2925326, 0.2934772,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3032",0.66, 0.0092,4.0, 0.4203458, 0.3850591,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","4667",0.20, 0.0154,4.0, 0.2596956, 0.4300505,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3033",0.49, 0.4758,4.0, 734.4972582, 101.3571570,0.05,0.05));
		
		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
	}
	static void runTestTen() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3028",1.63, 0.0208,4.0, 0.3011384, 0.3234448,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3030",1.22, 0.0383,4.0, 0.4237347, 0.3719613,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3029",0.95, 0.0124,4.0, 0.2969371, 0.3124672,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3031",0.83, 0.0126,4.0, 0.4421974, 0.3752598,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","4164",0.38, 0.0268,4.0, 0.3945491, 0.3675989,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","4326",0.49, 0.0071,4.0, 0.2925326, 0.2934772,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3032",0.66, 0.0092,4.0, 0.4203458, 0.3850591,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","4667",0.20, 0.0154,4.0, 0.2596956, 0.4300505,0.05,0.05));
		flights.add(new Flight("cbabbea7ed59aaa61c777c3c","3033",0.49, 0.4758,4.0, 734.4972582, 101.3571570,0.05,0.05));
		
		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
	}
	static void runTestEleven() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("114d876cfbfa3fce78991a84","3030",1.22,0.0074,2.0,0.42940611,0.75495791,0.2047179,0.2047179));
		flights.add(new Flight("114d876cfbfa3fce78991a84","3031",0.83,0.0106,2.0,0.49093633,0.56047702,0.18181765,0.18181765));
		flights.add(new Flight("114d876cfbfa3fce78991a84","3028",1.63,0.0125,2.0,0.56780781,0.37132096,0.19683836,0.19683836));
		flights.add(new Flight("114d876cfbfa3fce78991a84","4557",0.05,0.0145,2.0,0.16029846,0.67079592,0.01089316,0.01089316));

		FlightPlan fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
		
		flights = new ArrayList<Flight>();
		flights.add(new Flight("114d876cfbfa3fce78991a84","3028",1.63,0.0125,2.0,0.56780781,0.37132096,0.19683836,0.19683836));
		flights.add(new Flight("114d876cfbfa3fce78991a84","3030",1.22,0.0074,2.0,0.42940611,0.75495791,0.2047179,0.2047179));
		flights.add(new Flight("114d876cfbfa3fce78991a84","3031",0.83,0.0106,2.0,0.49093633,0.56047702,0.18181765,0.18181765));
		flights.add(new Flight("114d876cfbfa3fce78991a84","4557",0.05,0.0145,2.0,0.16029846,0.67079592,0.01089316,0.01089316));

		fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
	}

	static void runTestTwelve() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("11fb9285d31da9ab795fb607","3290",1360000.0,0.0548,10.0,0.79781551,0.79781551,1.16968098,0.79781551));
		flights.add(new Flight("11fb9285d31da9ab795fb607","4093",670000.0,0.0035,10.0,0.03407255,0.03407255,0.00698578,0.03407255));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3030",1170000.0,0.0069,10.0,0.52741788,0.52741788,0.55686976,0.52741788));
		flights.add(new Flight("11fb9285d31da9ab795fb607","5513",1140000.0,0.0833,10.0,0.18444774,0.17728198,0.01533386,0.17728198));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3319",670000.0,0.1688,10.0,0.38750608,0.30966683,0.13981872,0.30966683));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3031",840000.0,0.0153,10.0,0.4483129,0.56925893,0.31089018,0.56925893));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3032",680000.0,0.0156,10.0,0.40123396,0.43157005,0.14115745,0.43157005));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3029",990000.0,0.0186,10.0,0.98340172,7.0945859,1.55311162,7.0945859));
		flights.add(new Flight("11fb9285d31da9ab795fb607","4326",540000.0,0.0444,10.0,0.4206826,0.4206826,0.58979695,0.4206826));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3028",1400000.0,0.0181,10.0,1.54994354,2.57576303,3.73447313,2.57576303));
		flights.add(new Flight("11fb9285d31da9ab795fb607","5406",60000.0,0.0905,10.0,0.04451106,0.04451106,0.09444492,0.04451106));
		flights.add(new Flight("11fb9285d31da9ab795fb607","4644",90000.0,0.1409,10.0,0.24232119,0.16677999,0.23434971,0.16677999));
		FlightPlan fp = new FlightPlan(flights);		
		/*

		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
		
		flights = new ArrayList<Flight>();
		flights.add(new Flight("11fb9285d31da9ab795fb607","3290",1360000.0,0.0548,10.0,0.79781551,0.79781551,1.16968098,0.79781551));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3030",1170000.0,0.0069,10.0,0.52741788,0.52741788,0.55686976,0.52741788));
		flights.add(new Flight("11fb9285d31da9ab795fb607","5513",1140000.0,0.0833,10.0,0.18444774,0.17728198,0.01533386,0.17728198));
		flights.add(new Flight("11fb9285d31da9ab795fb607","4093",670000.0,0.0035,10.0,0.03407255,0.03407255,0.00698578,0.03407255));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3319",670000.0,0.1688,10.0,0.38750608,0.30966683,0.13981872,0.30966683));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3031",840000.0,0.0153,10.0,0.4483129,0.56925893,0.31089018,0.56925893));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3032",680000.0,0.0156,10.0,0.40123396,0.43157005,0.14115745,0.43157005));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3029",990000.0,0.0186,10.0,0.98340172,7.0945859,1.55311162,7.0945859));
		flights.add(new Flight("11fb9285d31da9ab795fb607","4326",540000.0,0.0444,10.0,0.4206826,0.4206826,0.58979695,0.4206826));
		flights.add(new Flight("11fb9285d31da9ab795fb607","3028",1400000.0,0.0181,10.0,1.54994354,2.57576303,3.73447313,2.57576303));
		flights.add(new Flight("11fb9285d31da9ab795fb607","5406",60000.0,0.0905,10.0,0.04451106,0.04451106,0.09444492,0.04451106));
		flights.add(new Flight("11fb9285d31da9ab795fb607","4644",90000.0,0.1409,10.0,0.24232119,0.16677999,0.23434971,0.16677999));
		
		fp = new FlightPlan(flights);
		System.out.println(fp);
		System.out.println(fp.getExpectedValue());
		*/
		FlightPlanCoordinator fpc = new FlightPlanCoordinator(fp, null);
	    fp = fpc.runSLS();
	}

 	static void runTestOne() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.add(new Flight("eeny", "eeny", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		flights.add(new Flight("meany", "meany", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		flights.add(new Flight("miny", "miny", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		flights.add(new Flight("moe", "moe", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		flights.add(new Flight("alan", "alan", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		flights.add(new Flight("quxtor", "quxtor", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		flights.add(new Flight("bim", "bim", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		flights.add(new Flight("bam", "bam", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		flights.add(new Flight("boom", "boom", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		//flights.add(new Flight("snig", "snig", RandFloat(0, 1), RandFloat(1, 100), RandFloat(0, 1),RandFloat(0, 1),0.05,0.05));
		//flights.add(new Flight("snag", "snag", RandFloat(0, 1), RandFloat(1, 100), RandFloat(0, 1),RandFloat(0, 1),0.05,0.05));
		//flights.add(new Flight("snoom", "snoom", RandFloat(0, 1), RandFloat(1, 100), RandFloat(0, 1),RandFloat(0, 1),0.05,0.05));
		FlightPlan fp = new FlightPlan(flights);
		ArrayList<Flight> candidates = new ArrayList<Flight>();
		candidates.add(new Flight("modern", "pass", RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("major", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("general", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("steve", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("quin", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("dario", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("mario", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("lario", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("bario", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("snarf", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));
		candidates.add(new Flight("snarfsnarf", "pass",RandFloat(1, 100), RandFloat(0, 1), 4.0, RandFloat(0, 1),RandFloat(0, 1), RandFloat(0, 1),RandFloat(0, 1)));

		FlightPlanCoordinator fpc = new FlightPlanCoordinator(fp, candidates);
		System.out.println("INITIAL");
		for (Flight f : fp) {
			System.out.println(f);
		}
		System.out.println("Is admissible? " + fp.isAdmissible() + 
				" Expected value: " + fp.getExpectedValue());
		
		fp.makeAdmissible();
		System.out.println("MADE ADMISSIBLE");
		for (Flight f : fp) {
			System.out.println(f);
		}
		System.out.println("Is admissible? " + fp.isAdmissible() + 
				" Expected value: " + fp.getExpectedValue());
		
		System.out.println("IMPROVED");
		FlightPlan fpi = fpc.runSLS();
		
		for (Flight f : fpi) {
			System.out.println(f);
		}
		System.out.println("Is admissible? " + fpi.isAdmissible() + 
				" Expected value: " + fpi.getExpectedValue());
		System.out.println("BRUTE FORCE");
		candidates.addAll(flights);
		FlightPlan fp1 = FlightPlanCoordinator.ordSplit(ParameterCore.BRUTE_BOUND-1, candidates);
		fpc = new FlightPlanCoordinator(fp1, new ArrayList<Flight> ());
		FlightPlan fpb = fpc.runBruteForce();

		for (Flight f : fpb) {
			System.out.println(f);
		}
		System.out.println("Is admissible? " + fpb.isAdmissible() + 
				" Expected value: " + fpb.getExpectedValue());
	}
}