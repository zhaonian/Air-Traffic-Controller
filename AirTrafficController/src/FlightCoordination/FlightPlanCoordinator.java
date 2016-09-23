package FlightCoordination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FlightPlanCoordinator {
    private Integer      waterFallSize;
    private FlightPlan   baseFlightPlan;
    private Random       random;
    private List<Flight> candidates;
    private Double       temperature;

    // A modification of SLS, a random order is created with this probability.
    // A higher number means more diversification(randomization), a lower number means more intensification.

    /*
     * The base flight plan, and candidates that may be swapped into it.
     */
    public FlightPlanCoordinator(FlightPlan fp, List<Flight> candidates) {
        this.random = new Random();
        this.baseFlightPlan = fp;
        this.waterFallSize = fp.size();
        this.candidates = (candidates != null) ? Collections.synchronizedList(candidates) : new ArrayList<Flight>();
        this.temperature = ParameterCore.PROB_RANDOR_BASE;
    }

    private List<Flight> cloneCandidates() {
        List<Flight> candid = Collections.synchronizedList(new ArrayList<Flight>());
        for (Flight f : this.candidates) {
            candid.add(new Flight(f));
        }
        return candid;
    }

    /*
     * Randomly splits wSize flights from candidates into waterfall. After the operation candidates is modified.
     */
    public static FlightPlan randSplit(Integer wSize, List<Flight> candidates) {
        Collections.shuffle(candidates);
        List<Flight> flist = new ArrayList<Flight>();
        for (int i = 0; i < wSize; i++) {
            flist.add(candidates.get(i));
        }
        candidates.removeAll(flist);
        FlightPlan fp = new FlightPlan(flist);
        return fp;
    }

    /*
     * Splits wSize flights from candidates into waterfall. After the operation candidates is modified. Takes the highest CPM flights.
     */
    public static FlightPlan ordSplit(Integer wSize, List<Flight> candidates) {
        Collections.sort(candidates);
        Collections.reverse(candidates);
        List<Flight> flist = new ArrayList<Flight>();
        for (int i = 0; i < wSize; i++) {
            flist.add(candidates.get(i));
        }
        candidates.removeAll(flist);
        FlightPlan fp = new FlightPlan(flist);
        return fp;
    }

    public FlightPlan runSLS() {
        FlightPlan incumbent = sls();
        if (incumbent == null) {
            throw new AssertionError();
        }
        return incumbent;
    }

    private FlightPlan sls() {
        FlightPlan incumbent = this.baseFlightPlan.cloneFlightPlan();
        Double incumbentScore = incumbent.getExpectedValue();
        Double improvement = incumbentScore;
        List<Flight> candid = cloneCandidates();

        if ((candid.size() + incumbent.size()) < ParameterCore.BRUTE_BOUND) {
            return runBruteForce();
        }
        while (improvement.compareTo(ParameterCore.IMPROVEMENT_THRESHOLD) > 0) {
            improvement = 0.0;
            for (int i = 0; i < ParameterCore.LOCAL_SEARCH_WINDOW; i++) {
                // This block is used when an enforced small size waterfall size is used.
                if (candid.size() > ParameterCore.MIN_FLIGHTS_TO_TRY_IMPROVE) {
                    Integer swapInIndex = this.random.nextInt(candid.size() - 1);
                    Integer swapOutIndex = this.random.nextInt(incumbent.size() - 1);
                    FlightPlan fpCandidate = incumbent.cloneFlightPlan();

                    Flight swappedIn = candid.get(swapInIndex);
                    Flight swappedOut = fpCandidate.get(swapOutIndex);
                    fpCandidate.set(swapOutIndex, swappedIn);

                    // Secondary SLS method.
                    if (random.nextDouble() < this.temperature) {
                        // Collections.shuffle(fpCandidate.getAsList());
                        subSeqShuffle(fpCandidate);
                    }
                    // fpCandidate.makeAdmissible();

                    Double candScore = fpCandidate.getExpectedValue();
                    if (incumbentScore.compareTo(candScore) < 0) {
                        improvement = Math.max(improvement, candScore - incumbentScore);
                        incumbent = fpCandidate;
                        incumbentScore = candScore;
                        this.candidates.remove(swappedIn);
                        this.candidates.add(swappedOut);
                    }
                }
                // This block is used when a full ordering (all elements in waterfall are considered).
                else {
                    Integer swapInIndex = this.random.nextInt(incumbent.size() - 1);
                    Integer swapOutIndex = this.random.nextInt(incumbent.size() - 1);
                    while (swapOutIndex == swapInIndex && incumbent.size() > 1) {
                        swapOutIndex = this.random.nextInt(incumbent.size() - 1);
                    }
                    FlightPlan fpCandidate = incumbent.cloneFlightPlan();

                    Flight swappedIn = fpCandidate.get(swapInIndex);
                    Flight swappedOut = fpCandidate.get(swapOutIndex);

                    fpCandidate.set(swapOutIndex, swappedIn);
                    fpCandidate.set(swapInIndex, swappedOut);

                    // Secondary SLS method.
                    if (random.nextDouble() < this.temperature) {
                        // Collections.shuffle(fpCandidate.getAsList());
                        subSeqShuffle(fpCandidate);
                    }
                    // fpCandidate.makeAdmissible();

                    Double candScore = fpCandidate.getExpectedValue();
                    if (incumbentScore.compareTo(candScore) < 0) {
                        // System.out.println("Replace ");
                        // System.out.println(incumbent);
                        // System.out.println("WITH ");
                        // System.out.println(fpCandidate);
                        improvement = Math.max(improvement, candScore - incumbentScore);
                        incumbent = fpCandidate;
                        incumbentScore = candScore;
                    }
                }
                this.temperature = ((ParameterCore.PROB_RANDOR_BASE - ParameterCore.PROB_RANDOR_MIN) - (improvement / incumbentScore)) + ParameterCore.PROB_RANDOR_MIN;
                // System.out.println("TEMP IS " + temperature);
                if (ParameterCore.DEBUG < 0) {
                    System.out.println(improvement + " " + (1.0 - (improvement / incumbentScore)) + " " + temperature);
                }
            }
        }

        // Danial's finishing touch.
        /*
         * List<Flight> topFlights = incumbent.getAsList().subList(0, ParameterCore.BRUTE_BOUND-1); List<Flight> bottomFlights = incumbent.getAsList().subList(ParameterCore.BRUTE_BOUND-1,incumbent.getAsList().size()); topFlights = runBruteForce(topFlights).getAsList(); incumbent = new FlightPlan(Utils.mergeLists(topFlights, bottomFlights));
         */
        // Danial's finishing touch.
        System.out.println("NO BFS Improvement!");

        return incumbent;
    }

    private void subSeqShuffle(FlightPlan fpCandidate) {
        Integer swapOneIdx = this.random.nextInt(fpCandidate.size() - 1);
        Integer swapTwoIdx = this.random.nextInt(fpCandidate.size() - 1);
        while (swapOneIdx == swapTwoIdx && fpCandidate.size() > 1) {
            swapOneIdx = this.random.nextInt(fpCandidate.size() - 1);
        }
        Collections.shuffle(fpCandidate.getAsList().subList(Math.min(swapOneIdx, swapTwoIdx), Math.max(swapOneIdx, swapTwoIdx)));
    }

    public FlightPlan runBruteForce(List<Flight> allFlights) {
        // if (allFlights.size() > ParameterCore.BRUTE_BOUND){
        // throw new AssertionError("Brute Force Bound Exceeded");
        // }
        Integer minFlights = Math.min(this.waterFallSize, allFlights.size());
        FlightCombinator fc = new FlightCombinator(allFlights, minFlights);
        FlightPlan bestPlan = new FlightPlan(allFlights.subList(0, minFlights));
        Double bestPlanScore = bestPlan.getExpectedValue();

        for (FlightPlan fp : fc) {
            Double expVal = fp.getExpectedValue();
            /*
             * String p = ""; for (Flight f : fp){ p += f.getFid() + " "; } p += expVal; System.out.println(p);
             */
            if (bestPlanScore.compareTo(expVal) < 0) {
                bestPlan = fp;
                bestPlanScore = expVal;
            }
        }
        if (ParameterCore.DEBUG > 0) {
            System.out.println(bestPlan.get(0).getPlacementToken() + " " + bestPlan.getExpectedValue());
            System.out.println(bestPlan);
        }
        return bestPlan;
    }

    public FlightPlan runBruteForce() {
        List<Flight> allFlights = new FlightPlan(this.baseFlightPlan).getAsList();
        allFlights.addAll(cloneCandidates());
        return runBruteForce(allFlights);
    }
}