import random
import csv
import decimal
from ParameterCore import ParameterCore
from Flight import *
import numpy

class Utils:                              # public class Utils {
    def __init__(self):
        self.rewardMedian = 0.0           # public static Double rewardMedian   ;
        self.fillMedian = 0.0             # public static Double fillMedian     ;
        self.timeMaxMedian = 0.0          # public static Double timeMaxMedian  ;
        self.timeAverageFailMedian = 0.0  # public static Double timeAverageFailMedian
        self.timeAverageSuccMedian = 0.0  # public static Double timeAverageSuccMedian
        self.timeStdDevFailMedian = 0.0   # public static Double timeStdDevFailMedian
        self.timeStdDevSuccMedian = 0.0   # public static Double timeStdDevSuccMedian
        self.random = 0                   # public static Random rand = new Random()  ;

    def preProcessFlights(self, fl):
        def removeAll(base_list, rem_list=[]):
            for i in range(len(rem_list)):
                for j in range(len(base_list)):
                    if base_list[j] == rem_list[i]:
                        del base_list[j]
                        break
        # equivalent of removeAll function

        toRemove = []
        for f in fl:
            if f.getReward() <= ParameterCore.ParameterCore.CLOSE_TO_ZERO:
                f.setReward(self.rewardMedian * ParameterCore.ParameterCore().NO_REWARD_DEMERIT_COEFF)
                f.setNoDat(True)
            if f.getProbability() <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                f.setProbability(self.fillMedian)
                f.setNoDat(True)
            if f.getTimeMax() <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                f.setTimeMax(self.timeMaxMedian)
                f.setNoDat(True)
            if f.getTimeAverageFailure() <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                if f.getTimeAverageSuccess() > ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                    f.setTimeAverageFailure(f.getTimeAverageSuccess)
                else:
                    f.setTimeAverageFailure(self.timeAverageFailMedian)
            if f.getTimeAverageSuccess <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                if f.getTimeAverageFailure() > ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                    f.setTimeAverageSuccess(f.getTimeAverageFailure)
                else:
                    f.setTimeAverageSuccess(self.timeAverageSuccMedian)
            if f.getTimeStdDevFailure() <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                if f.getTimeStdDevSuccess() > ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                    f.setTimeStdDevFailure(f.getTimeStdDevSuccess)
                else:
                    f.setTimeStdDevFailure(self.timeStdDevFailMedian)
            if f.getTimeStdDevSuccess() <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                if f.getTimeStdDevFailure() > ParameterCore.ParameterCore().CLOSE_TO_ZERO:
                    f.setTimeStdDevSuccess(f.getTimeStdDevFailure())
                else:
                    f.setTimeStdDevSuccess(self.timeStdDevSuccMedian)
            if f.getNoDat() and ParameterCore.ParameterCore().SKIP_NODAT:
                    toRemove.append(f)
            if ParameterCore.ParameterCore().SKIP_NODAT:
                removeAll(fl, toRemove)

    def partitionToPlacements(self, allFlights):
        pHash = dict()
        for f in allFlights:
            if f.getPlacementToken() in pHash.keys():
                p = pHash[f.getPlacementToken]
                p.addFlight(f)
            else:
                nfp = FlightPlan([])
                pHash.update({f.getPlacementToken: Placement(f.getPlacementToken(), nfp)})
                nfp.addFlight(f)

    def optimizePlacement(self, waterfallSizeLimit, control, allPlacements):
        # clearFile(ParameterCore.LOG_LOC)
        logger = PrintWriter(FileWriter(ParameterCore.ParameterCore().LOG_LOC))
        semaphore = Semaphore(ParameterCore.ParameterCore().CORES)
        alot = []
        done = 1
        if ParameterCore.ParameterCore().RUN_PARALLEL:
            for p in allPlacements:
                alot.append(OptimizingThread(waterfallSizeLimit, control, semaphore, p))
                alot[]

    def doProcessRow(self, rowIndex):
        if ParameterCore.ParameterCore().SKIP_FIRST_ROW and rowIndex == 0:
            return False
        else:
            return True

    def readFlightsCSV(self, csv, inFiNa):
        flights = []
        self.rewardMedian = None
        self.fillMedian = None
        self.timeAverageFailMedian = None
        self.timeAverageSuccMedian = None
        self.timeMaxMedian = None
        rewList = []
        fillList = []
        tMaxList = []
        atFailList = []
        atSuccList = []
        stdtFailList = []
        stdtSuccList = []

        rewList.append(0.0)
        fillList.append(0.0)
        tMaxList.append(0.0)
        atFailList.append(0.0)
        atSuccList.append(0.0)
        stdtFailList.append(0.0)
        stdtSuccList.append(0.0)

        # still have to implement a bunch of code that I don't know how it works

        self.rewardMedian = numpy.median(rewList)
        self.fillMedian = numpy.median(fillList)
        self.timeMaxMedian = numpy.median(tMaxList)
        self.timeAverageFailMedian = numpy.median(atFailList)
        self.timeAverageSuccMedian = numpy.median(atSuccList)
        self.timeStdDevFailMedian = numpy.median(stdtFailList)
        self.timeStdDevSuccMedian = numpy.median(stdtSuccList)

        return flights


    def mergeLists(self, l1, l2):
        c = []
        for i in l1:
            c.append(i)
        for i in l2:
            c.append(i)
        return c


    def Median(self, values):
        values.sort()
        if (len(values) % 2)== 1:
            return values[(len(values)+1)/2-1]
        else:
            lower = values[len(values)/2-1]
            upper = values[len(values)/2]
            return (lower+upper)/2.0


    def isDouble(self, string):
        return type(string) == type(float())


    def productHighPrecision(self, *dbls):
        base = decimal.Decimal(1.0)
        for d in dbls:
            base *= decimal.Decimal(d)
        return base


    def sumHighPrecision(self, dbls):
        base = decimal.Decimal(1.0)
        for d in dbls:
            base += decimal.Decimal(d)
        return base


    def nextGaussian(mean, std_dev):
        return random.gauss(0.0, 1)*(std_dev**2) + mean


    def roundDouble(value, sigFigs):
        return round(value * (10**sigFigs))/(10**sigFigs)