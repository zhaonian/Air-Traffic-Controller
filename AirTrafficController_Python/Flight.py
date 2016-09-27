"""
This module contains all the info of what a Flight object (an ad) contains
"""

import numpy
import ParameterCore
import FlightPlanBootstrapOrderEnum
from CDF import lognormcdf

class Flight(object):
    """
    A Flight object stands for a single ad.
    """

    pTok = ""
    fid = ""
    timeMax = 0.0
    reward = 0.0
    probability = 0.0
    timeAverageFailure = 0.0
    timeAverageSuccess = 0.0
    timeStdDevFailure = 0.0
    timeStdDevSuccess = 0.0
    noDat = False

    def __init__(self, *args):
        """
        According to the Java codes, the Flight can take 1 or more parameters
        """
        if len(args) == 1:
            self.copy_constructor(args)
        else:
            self.regular_constructor(args)

    def copy_constructor(self, obj):
        """
        Args:
            obj: tuple

        """
        cloneMe = obj[0]  # since obj is a tuple, if self method is called, then an obj is the only thing in the tuple

        self.pTok = cloneMe.getPlacementToken()
        self.fid = cloneMe.getFid()
        self.reward = cloneMe.getReward()
        self.probability = cloneMe.getProbability()
        self.timeMax = cloneMe.getTimeMax()
        self.timeAverageFailure = cloneMe.getTimeAverageFailure()
        self.timeAverageSuccess = cloneMe.getTimeAverageSuccess()
        self.timeStdDevFailure = cloneMe.getTimeStdDevFailure()
        self.timeStdDevSuccess = cloneMe.getTimeStdDevSuccess()
        self.noDat = cloneMe.getNoDat()

    def regular_constructor(self, args):
        """
        Args:
            args: all the attributes stored in self
        """
        self.pTok, self.fid, self.reward, self.probability, self.timeMax, self.timeAverageFailure, self.timeAverageSuccess, self.timeStdDevFailure, self.timeStdDevSuccess = args
        self.noDat = False

    def isolatedSuccessCDF(self):                                                                               # public Double isolatedSuccessCDF(){
        #normalCDF = scipy.stats.norm.cdf(self.timeMax, self.timeAverageSuccess, self.timeStdDevSuccess)         # Double normalCDF = jdistlib.Normal.cumulative(timeMax, timeAverageSuccess, this.timeStdDevSuccess);
        normalCDF = lognormcdf(self.timeMax, self.timeAverageSuccess, self.timeStdDevSuccess)
        return self.reward * self.probability * normalCDF                                                       # return (reward*probability*normalCDF);


    def compareTo(self, other):                                                                                 # public int compareTo(Flight other){
        """
        Overload the compareTo method in order to compare Flights

        Args:
            self: Flight
            other: Flight

        Return:
            -1: self < other
            0: self = other
            1 self > other
        """
        cmp = 0.0                                                                                               # Double cmp = 0.0;
        if ParameterCore.ParameterCore().BOOTSTRAP_ORDER == FlightPlanBootstrapOrderEnum.FlightPlanBootstrapOrderEnum().BY_EXPECT:             # if (ParameterCore.BOOTSTRAP_ORDER == FlightPlanBootstrapOrderEnum.BY_EXPECT){
            cmp = self.isolatedSuccessCDF() - other.isolatedSuccessCDF()                                        # cmp = this.isolatedSuccessCDF() - other.isolatedSuccessCDF();
        if abs(cmp) <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:                                             # if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
            cmp = self.getReward() - other.getReward()                                                          # cmp = this.getReward() - other.getReward();
            if abs(cmp) <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:                                         # if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
                cmp = self.getProbability() - other.getProbability()                                            # cmp = this.getProbability() - other.getProbability();
                if abs(cmp) <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:                                     # if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
                    cmp = self.getTimeMax() - other.getTimeMax()                                                # cmp = this.getTimeMax() - other.getTimeMax();
                    if abs(cmp) <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:                                 # if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
                        cmp = -1.0 * (self.getTimeAverageFailure() - other.getTimeAverageFailure())             # cmp = -1.0*(this.getTimeAverageFailure() - other.getTimeAverageFailure());
                        if abs(cmp) <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:                             # if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
                            cmp = -1.0 * (self.getTimeAverageSuccess() - other.getTimeAverageSuccess())         # cmp = -1.0*(this.getTimeAverageSuccess() - other.getTimeAverageSuccess());
                            if abs(cmp) <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:                         # if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
                                cmp = -1.0 * (self.getTimeStdDevFailure() - other.getTimeStdDevFailure())       # cmp = -1.0*(this.getTimeStdDevFailure() - other.getTimeStdDevFailure());
                                if abs(cmp) <= ParameterCore.ParameterCore().CLOSE_TO_ZERO:                     # if (Math.abs(cmp) <= ParameterCore.CLOSE_TO_ZERO){
                                    cmp = -1.0 * (self.getTimeStdDevSuccess() - other.getTimeStdDevSuccess())   # cmp = -1.0*(this.getTimeStdDevSuccess() - other.getTimeStdDevSuccess());
        return int(numpy.sign(cmp))                                                                             # return (int) Math.signum(cmp);


    def toString(self):                                                                                 # public String toString(){
        """
        Return:
            a certain format for Flight objects
        """
        return "{} , {} , {} , {} ; {} , {} , {} , {} , {}\n".format(                                   # return String.format("%-24s , %-8s , %8f , %8f ; %8f , %8f , %8f %8f , %8f\n",
            self.pTok,self.fid,self.reward,self.probability,self.timeMax,                               # this.pTok,this.fid,this.reward,this.probability,this.timeMax,
            self.timeAverageFailure,self.timeAverageSuccess,                                            # this.timeAverageFailure,this.timeAverageSuccess,
            self.timeStdDevFailure,self.timeStdDevSuccess)                                              # this.timeStdDevFailure,this.timeStdDevSuccess);

    def hashcode(self):                                                                                 # public int hashCode(){
        """
        Return:
             the hash value of Flight ID
        """
        return hash(self.fid)                                                                           # return this.fid.hashCode();


    def equals(self, other):
        """
        Args:
            other: Flight

        Return:
            False: self != Flight
            True: self = Flight
        """
        if hasattr(other, "fid"):
            if self.fid == other.fid:
                return True
            else:
                return False
        else:
            return False



    """
    The getter methods of Flight
    """
    def getPlacementToken(self):
        return self.pTok

    def getFid(self):
        return self.fid

    def getReward(self):
        return self.reward

    def getProbability(self):
        return self.probability

    def getTimeMax(self):
        return self.timeMax

    def getNoDat(self):
        return self.noDat

    def getTimeAverageFailure(self):
        return self.timeAverageFailure

    def getTimeAverageSuccess(self):
        return self.timeAverageSuccess

    def getTimeFailureStdDev(self):
        return self.timeStdDevFailure

    def getTimeSuccessStdDev(self):
        return self.timeStdDevSuccess

    def getTimeStdDevFailure(self):
        return self.timeStdDevFailure

    def getTimeStdDevSuccess(self):
        return self.timeStdDevSuccess


    """
    The setter methods of Flight
    """
    def setReward(self, r):
        self.reward = r

    def setProbability(self, p):
        self.probability = p

    def setTimeMax(self, timeMax):
        self.timeMax = timeMax

    def setTimeAverageFailure(self, timeFailure):
        self.timeAverageFailure = timeFailure

    def setTimeAverageSuccess(self, timeSuccess):
        self.timeAverageSuccess = timeSuccess

    def setTimeStdDevFailure(self, timeStdDevFailure):
        self.timeStdDevFailure = timeStdDevFailure

    def setTimeStdDevSuccess(self, timeStdDevSuccess):
        self.timeStdDevSuccess = timeStdDevSuccess

    def setNoDat(self, b):
        self.noDat = b



    def __eq__(self, other):
        """
        Override "=="

        Args:
            other: Flight

        Return:
            True: Flight ID = other ID
            False: Flight ID != other ID
        """
        return self.fid == other.fid
