
import FlightCoordination.Flight;
import FlightCoordination.FlightPlan;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Risin
 */
public class TestFlightPlan {
    public static void main(String args[])
    {
        List<Flight> l = new ArrayList<>();
        l.add(new Flight("general"                  , "pass"     , 99.065588 , 0.521137 , 4.000000 , 0.671081 , 0.284618 , 0.665309 , 0.041733));
        l.add(new Flight("general"                  , "pass"     , 99.065588 , 0.521137 , 4.000000 , 0.671081 , 0.284618 , 0.665309 , 0.041733));
        l.add(new Flight("general"                  , "pass"     , 99.065588 , 0.521137 , 4.000000 , 0.671081 , 0.284618 , 0.665309 , 0.041733));
        l.add(new Flight("general"                  , "pass"     , 99.065588 , 0.521137 , 4.000000 , 0.671081 , 0.284618 , 0.665309 , 0.041733));
        l.add(new Flight("general"                  , "pass"     , 99.065588 , 0.521137 , 4.000000 , 0.671081 , 0.284618 , 0.665309 , 0.041733));
        //l.add(new Flight("alan"                     , "alan"     , 71.329610 , 0.930679 , 4.000000 , 0.117261 , 0.181536 , 0.901166 , 0.062637));
        //l.add(new Flight("meany"                    , "meany"    , 24.891589 , 0.910925 , 4.000000 , 0.593279 , 0.511503 , 0.284545 , 0.339193));
        
        FlightPlan fp = new FlightPlan(l);
        System.out.println(fp.getExpectedValue());
    }
}
