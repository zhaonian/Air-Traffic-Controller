from FlightPlan import FlightPlan
                                                                                # import java.util.ArrayList;
import numpy
import random
import ParameterCore
import Flight
import FlightCombinator


class FlightPlanCoordinator:                                                    # public class FlightPlanCoordinator {
    def __init__(self, fp, candidates):
        return

    waterFallSize = 0                                                           # private Integer      waterFallSize;
    baseFlightPlan = FlightPlan()                                               # private FlightPlan   baseFlightPlan;
    random = random.random()                                                    # private Random       random;
    coordinates = []                                                            # private List<Flight> candidates;
    temperature = 0.0                                                           # private Double       temperature;
    candidates = list()


    def FlightPlanCoordinator(self, fp, candidates):                            # public FlightPlanCoordinator(FlightPlan fp, List<Flight> candidates) {
        self.random = random.Random()                                           # this.random = new Random();
        self.baseFlightPlan = fp                                                # this.baseFlightPlan = fp;
        self.waterFallSize = len(fp)                                            # this.waterFallSize = fp.size();
        if candidates != None:                                                  # this.candidates = (candidates != null) ? Collections.synchronizedList(candidates) : new ArrayList<Flight>();
            self.candidates = candidates
        self.temperature = ParameterCore.ParameterCore().PROB_RANDOR_BASE       # this.temperature = ParameterCore.PROB_RANDOR_BASE;


    def cloneCandidates(self):                                                  # private List<Flight> cloneCandidates() {
        candid = list()                                                         # List<Flight> candid = Collections.synchronizedList(new ArrayList<Flight>());
        for f in self.candidates:                                               # for (Flight f : this.candidates) {
            candid.append(Flight.Flight(f))                                     # candid.add(new Flight(f));
        return candid                                                           # return candid;


    def randSplit(self, wSize, candidates):                                     # public static FlightPlan randSplit(Integer wSize, List<Flight> candidates) {
        numpy.random.shuffle(candidates)                                        # Collections.shuffle(candidates);
        flist = list()                                                          # List<Flight> flist = new ArrayList<Flight>();
        for i in range(0, wSize):                                               # for (int i = 0; i < wSize; i++) {
            flist.append(candidates[i])                                         # flist.add(candidates.get(i));
        candidates = [x for x in candidates if x not in flist]                  # candidates.removeAll(flist);
        fp = FlightPlan(flist)                                                  # FlightPlan fp = new FlightPlan(flist);
        return fp                                                               # return fp;


    def ordSplit(self, wSize, candidates):                                      # public static FlightPlan ordSplit(Integer wSize, List<Flight> candidates) {
        candidates.sort()                                                       # Collections.sort(candidates);
        candidates.reverse()                                                    # Collections.reverse(candidates);
        flist = list()                                                          # List<Flight> flist = new ArrayList<Flight>();
        for i in range(0, wSize):                                               # for (int i = 0; i < wSize; i++) {
            flist.append(candidates[i])                                         # flist.add(candidates.get(i));
        candidates = [x for x in candidates if x not in flist]                  # candidates.removeAll(flist);
        fp = FlightPlan(flist)                                                  # FlightPlan fp = new FlightPlan(flist);
        return fp                                                               # return fp;


    def runSLS(self):                                           # public FlightPlan runSLS() {
        incumbent = self.sls()                                                       # FlightPlan incumbent = sls();
        if incumbent == None:                                                   # if (incumbent == null) {
            raise AssertionError()                                              # throw new AssertionError();
        return incumbent                                                        # return incumbent;


    def sls(self):
        incumbent = self.baseFlightPlan.cloneFlightPlan()
        incumbentScore = incumbent.getExpectedValue()
        improvement = incumbentScore
        candid = self.cloneCandidates()

        if (len(candid) + incumbent.size()) < ParameterCore.ParameterCore().BRUTE_BOUND:      # if ((candid.size() + incumbent.size()) < ParameterCore.BRUTE_BOUND) {
            return self.runBruteForce()                                                     # return runBruteForce();

        while improvement > ParameterCore.ParameterCore().IMPROVEMENT_THRESHOLD:            # while (improvement.compareTo(ParameterCore.IMPROVEMENT_THRESHOLD) > 0) {
            improvement = 0.0                                                               # improvement = 0.0;
            for i in range(0, ParameterCore.ParameterCore().LOCAL_SEARCH_WINDOW):           # for (int i = 0; i < ParameterCore.LOCAL_SEARCH_WINDOW; i++) {
                if len(candid) > ParameterCore.ParameterCore().MIN_FLIGHTS_TO_TRY_IMPROVE:  # if (candid.size() > ParameterCore.MIN_FLIGHTS_TO_TRY_IMPROVE) {
                    swapInIndex = self.random.randrange(len(candid) - 1)                    # Integer swapInIndex = this.random.nextInt(candid.size() - 1);
                    swapOutIndex = self.random.randrange(int(incumbent) - 1)                # Integer swapOutIndex = this.random.nextInt(incumbent.size() - 1);
                    fpCandidate = incumbent.cloneFlightPlan()                               # FlightPlan fpCandidate = incumbent.cloneFlightPlan();

                    swappedIn = candid[swapInIndex]                                         # Flight swappedIn = candid.get(swapInIndex);
                    swappedOut = fpCandidate[swapOutIndex]                                  # Flight swappedOut = fpCandidate.get(swapOutIndex);
                    fpCandidate.insert(swapOutIndex, swappedIn)                             # fpCandidate.set(swapOutIndex, swappedIn);

                    if random.random() < self.temperature:                                  # if (random.nextDouble() < this.temperature) {
                        self.subSeqShuffle(fpCandidate)                                     # subSeqShuffle(fpCandidate);

                    candScore = fpCandidate.getExpectedValue()                              # Double candScore = fpCandidate.getExpectedValue();
                    if incumbentScore < candScore:                                          # if (incumbentScore.compareTo(candScore) < 0) {
                        improvement = max(improvement, candScore - incumbentScore)          # improvement = Math.max(improvement, candScore - incumbentScore);
                        incumbent = fpCandidate                                             # incumbent = fpCandidate;
                        incumbentScore = candScore                                          # incumbentScore = candScore;
                        self.candidates.remove(swappedIn)                                   # this.candidates.remove(swappedIn);
                        self.candidates.append(swappedOut)                                  # this.candidates.add(swappedOut);

                    else:                                                                   # else {
                        swapInIndex = self.random.randrange(len(incumbent) - 1)             # Integer swapInIndex = this.random.nextInt(incumbent.size() - 1);
                        swapOutIndex = self.random.randrange(len(incumbent) - 1)            # Integer swapOutIndex = this.random.nextInt(incumbent.size() - 1);
                        while (swapOutIndex == swapInIndex and len(incumbent) > 1):         # while (swapOutIndex == swapInIndex && incumbent.size() > 1) {
                            swapOutIndex = self.random.randrange(len(incumbent) - 1)        # swapOutIndex = this.random.nextInt(incumbent.size() - 1);
                        fpCandidate = incumbent.cloneFlightPlan()                           # FlightPlan fpCandidate = incumbent.cloneFlightPlan();

                        swappedIn = fpCandidate[swapInIndex]                                # Flight swappedIn = fpCandidate.get(swapInIndex);
                        swappedOut = fpCandidate[swapOutIndex]                              # Flight swappedOut = fpCandidate.get(swapOutIndex);

                        fpCandidate.insert(swapOutIndex, swappedIn)                         # fpCandidate.set(swapOutIndex, swappedIn);
                        fpCandidate.insert(swapInIndex, swappedOut)                         # fpCandidate.set(swapInIndex, swappedOut);

                        if random.random() < self.temperature:                              # if (random.nextDouble() < this.temperature) {
                            self.subSeqShuffle(fpCandidate)                                 # subSeqShuffle(fpCandidate);

                        candScore = fpCandidate.getExpectedValue()                          # Double candScore = fpCandidate.getExpectedValue();
                        if incumbentScore < candScore:                                      # if (incumbentScore.compareTo(candScore) < 0) {
                            improvement = max(improvement, candScore - incumbentScore)      # improvement = Math.max(improvement, candScore - incumbentScore);
                            incumbent = fpCandidate                                         # incumbent = fpCandidate;
                            incumbentScore = candScore                                      # incumbentScore = candScore;

                    self.temperature = ((ParameterCore.ParameterCore().PROB_RANDOR_BASE - ParameterCore.ParameterCore().PROB_RANDOR_MIN)
                                        - (improvement / incumbentScore)) + ParameterCore.ParameterCore().PROB_RANDOR_MIN               # this.temperature = ((ParameterCore.PROB_RANDOR_BASE - ParameterCore.PROB_RANDOR_MIN) - (improvement / incumbentScore)) + ParameterCore.PROB_RANDOR_MIN;
                    if ParameterCore.ParameterCore().DEBUG < 0:                                                                         # if (ParameterCore.DEBUG < 0) {
                        print(improvement , " " , (1.0 - (improvement / incumbentScore)) , " " , self.temperature)      # System.out.println(improvement + " " + (1.0 - (improvement / incumbentScore)) + " " + temperature);

        print("NO BFS Improvement!")                                                                                    # System.out.println("NO BFS Improvement!");

        return incumbent                                                                                                # return incumbent;


    def subSeqShuffle(self, fpCandidate):                                                         # private void subSeqShuffle(FlightPlan fpCandidate) {
        swapOneIdx = random.randint(len(fpCandidate) - 1)                                                          # Integer swapOneIdx = this.random.nextInt(fpCandidate.size() - 1);
        swapTwoIdx = random.randint(len(fpCandidate) - 1)                                                          # Integer swapTwoIdx = this.random.nextInt(fpCandidate.size() - 1);
        while (swapOneIdx == swapTwoIdx and len(fpCandidate) > 1):                                                      # while (swapOneIdx == swapTwoIdx && fpCandidate.size() > 1) {
            swapOneIdx = random.randint(len(fpCandidate) - 1)                                                     # swapOneIdx = this.random.nextInt(fpCandidate.size() - 1);
        numpy.random.shuffle(list(fpCandidate)[min(swapOneIdx, swapTwoIdx), max(swapOneIdx, swapTwoIdx)])               # Collections.shuffle(fpCandidate.getAsList().subList(Math.min(swapOneIdx, swapTwoIdx), Math.max(swapOneIdx, swapTwoIdx)));


    def runBruteForce(self, *args):                                                                                     # public FlightPlan runBruteForce(List<Flight> allFlights) {
        # if allFlights.size() > ParameterCore.BRUTE_BOUND) {
        # throw new AssertionError("Brute Force Bound Exceeded");
        # }
        if len(args) == 0:
            allFlights = list(FlightPlan.FlightPlan(self.baseFlightPlan))                                               # List<Flight> allFlights = new FlightPlan(this.baseFlightPlan).getAsList();
            allFlights.extend(self.cloneCandidates())                                                                   # allFlights.addAll(cloneCandidates());
            return self.runBruteForce(allFlights)                                                                       # return runBruteForce(allFlights);
        else:
            minFlights = min(self.waterFallSize, len(args))                                                             # Integer minFlights = Math.min(this.waterFallSize, allFlights.size());
            allFlights = args[0, minFlights]
            fc = FlightCombinator.FlightCombinator(allFlights, minFlights)                                                               # FlightCombinator fc = new FlightCombinator(allFlights, minFlights);

            bestPlan = FlightPlan.FlightPlan(allFlights[0, minFlights])                                                 # FlightPlan bestPlan = new FlightPlan(allFlights.subList(0, minFlights));
            bestPlanScore = bestPlan.getExpectedValue()                                                                 # Double bestPlanScore = bestPlan.getExpectedValue();

            for fp in [fc]:                                                                                               # for (FlightPlan fp : fc) {
                expVal = fp.getExpectedValue()                                                                          # Double expVal = fp.getExpectedValue();

                if bestPlanScore < expVal:                                                                              # if (bestPlanScore.compareTo(expVal) < 0) {
                    bestPlan = fp                                                                                       # bestPlan = fp;
                    bestPlanScore = expVal                                                                              # bestPlanScore = expVal;

            if ParameterCore.ParameterCore().DEBUG > 0:                                                                 # if (ParameterCore.DEBUG > 0) {
                print(bestPlan.get(0).getPlacementToken() + " " + bestPlan.getExpectedValue())                          # System.out.println(bestPlan.get(0).getPlacementToken() + " " + bestPlan.getExpectedValue());
                print(bestPlan)                                                                                         # System.out.println(bestPlan);

            return bestPlan                                                                                             # return bestPlan;


















