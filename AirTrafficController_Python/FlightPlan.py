"""
This module contains all the math methods for calculating the best FlightPlan (ad plan).
"""

import math
import scipy.stats
import Flight
import ParameterCore
import UtilityFunctionEnum


class FlightPlan:
    probs = ""
    plan = []

    def __init__(self, plan):
        #print(plan)
        if type(plan) is list or type(plan) is tuple:
            self.plan = plan
        elif type(plan) is FlightPlan:
            self.plan = plan.plan
        else:
            #print(type(plan))
            raise TypeError("plan wrong!")
                                                                # public FlightPlan(List<Flight> plan){
                                                                # this.plan = Collections.synchronizedList(plan);
                                                                # public FlightPlan(FlightPlan fp){
                                                                # this.plan = fp.getAsList();

    def getExpectedValueDeltaFunc(self):                        # public Double getExpectedValueDeltaFunc(){
        reward          = self.plan[0].getReward()              # Double reward	        = this.plan.get(0).getReward();
        probSucc        = self.plan[0].getProbability()         # Double probSucc         = this.plan.get(0).getProbability();
        tMax            = self.plan[0].getTimeMax()             # Double tMax             = this.plan.get(0).getTimeMax();
        costFlipFail    = self.plan[0].getTimeAverageFailure()  # Double costFlipFail     = this.plan.get(0).getTimeAverageFailure();
        costFlipSucc    = self.plan[0].getTimeAverageSuccess()  # Double costFlipSucc     = this.plan.get(0).getTimeAverageSuccess();

        timeExpireCoef  = ParameterCore.ParameterCore().NO_PENELTY_COEFF if ((costFlipSucc) < tMax) \
            else ParameterCore.ParameterCore().TIME_OUT_PENELTY_COEFF/(ParameterCore.ParameterCore().TIME_OUT_PENELTY_COEFF
                                                                       + costFlipSucc - tMax)
        # Double timeExpireCoef   = ( (costFlipSucc) < tMax) ?  ParameterCore.NO_PENELTY_COEFF :
        # ParameterCore.TIME_OUT_PENELTY_COEFF/(ParameterCore.TIME_OUT_PENELTY_COEFF + costFlipSucc - tMax)  ;

        fullSum         = (reward*probSucc*timeExpireCoef)      # Double fullSum		    = (reward*probSucc*timeExpireCoef);
        probFailChain   = ( 1.0 - probSucc )                    # Double probFailChain    = ( 1.0 - probSucc );
        costFailChain   = costFlipFail                          # Double costFailChain    = costFlipFail;

        for i in range(1, len(self.plan)):                                  # for ( int i = 1; i < this.plan.size(); i++ ){
            reward          = self.plan[i].getReward()                      # reward	   	        = this.plan.get(i).getReward();
            probSucc        = self.plan[i].getProbability()                 # probSucc            = this.plan.get(i).getProbability();
            tMax            = self.plan[i].getTimeMax()                     # tMax                = this.plan.get(i).getTimeMax();
            costFlipFail    = self.plan[i].getTimeAverageFailure()          # costFlipFail        = this.plan.get(i).getTimeAverageFailure();
            costFlipSucc    = self.plan[i].getTimeAverageSuccess()          # costFlipSucc        = this.plan.get(i).getTimeAverageSuccess();

            timeExpireCoef  = ParameterCore.ParameterCore().NO_PENELTY_COEFF if (costFlipSucc + costFailChain) < tMax else ParameterCore.ParameterCore().TIME_OUT_PENELTY_COEFF/((ParameterCore.ParameterCore().TIME_OUT_PENELTY_COEFF + costFlipSucc + costFailChain) - tMax)   # timeExpireCoef      = ( (costFlipSucc + costFailChain) < tMax) ?  ParameterCore.NO_PENELTY_COEFF : ParameterCore.TIME_OUT_PENELTY_COEFF/((ParameterCore.TIME_OUT_PENELTY_COEFF + costFlipSucc + costFailChain) - tMax);
            fullSum         += (reward*probSucc*probFailChain*timeExpireCoef)                                                   # fullSum 		   += (reward*probSucc*probFailChain*timeExpireCoef);
            probFailChain   *= (1.0 - probSucc)                                                                           # probFailChain      *= ( 1.0 - probSucc );
            costFailChain   += costFlipFail                                                                               # costFailChain      += costFlipFail;

        return fullSum                                                                                                  # return fullSum;


    def getExpectedValueCDF(self):                               # public Double getExpectedValueCDF(){
        reward           = self.plan[0].getReward()              # Double reward	        = this.plan.get(0).getReward();
        probSucc         = self.plan[0].getProbability()         # Double probSucc         = this.plan.get(0).getProbability();
        tMax             = self.plan[0].getTimeMax()             # Double tMax             = this.plan.get(0).getTimeMax();
        costFlipFail     = self.plan[0].getTimeAverageFailure()  # Double costFlipFail     = this.plan.get(0).getTimeAverageFailure();
        costFlipSucc     = self.plan[0].getTimeAverageSuccess()  # Double costFlipSucc     = this.plan.get(0).getTimeAverageSuccess();
        costFlipFail_std = self.plan[0].getTimeStdDevFailure()   # Double costFlipFail_std   = this.plan.get(0).getTimeStdDevFailure();
        costFlipSucc_std = self.plan[0].getTimeStdDevSuccess()   # Double costFlipSucc_std   = this.plan.get(0).getTimeStdDevSuccess();

        logNormVar   = math.log(math.pow(costFlipSucc_std, 2)/(math.pow(costFlipSucc, 2)) + 1.0)                          # Double logNormVar		  = Math.log((Math.pow(costFlipSucc_std,2)/Math.pow(costFlipSucc,2)) + 1.0);
        logNormMean  = math.log(costFlipSucc) - (logNormVar / 2.0)                                                       # Double logNormMean		  = Math.log(costFlipSucc)-(logNormVar/2.0);
        #logNormPPF   = scipy.stats.lognorm(0.001, math.sqrt(logNormVar), logNormMean)
        logNormalCDF = scipy.stats.lognorm.cdf(tMax, math.sqrt(logNormVar), logNormMean)

        fullSum             = (reward*probSucc*logNormalCDF)     # Double fullSum		      = (reward*probSucc*logNormalCDF);
        probFailChain       = 1.0 - probSucc                     # Double probFailChain      = ( 1.0 - probSucc );
        costFailChain       = costFlipFail                       # Double costFailChain      = costFlipFail;
        normalCDFFailChain  = (math.pow(costFlipFail_std, 2))    # Double normalCDFFailChain = (Math.pow(costFlipFail_std, 2));

        self.probs = self.plan[0].getFid() + " S ", logNormalCDF, ","     # this.probs = this.plan.get(0).getFid() + " S " + logNormalCDF + "," ;

        for i in range(1, len(self.plan)):                                  # for ( int i = 1; i < this.plan.size(); i++ ){
            reward              = self.plan[i].getReward()                  # reward	   	        = this.plan.get(i).getReward();
            probSucc            = self.plan[i].getProbability()             # probSucc            = this.plan.get(i).getProbability();
            tMax                = self.plan[i].getTimeMax()                 # tMax                = this.plan.get(i).getTimeMax();
            costFlipFail        = self.plan[i].getTimeAverageFailure()      # costFlipFail        = this.plan.get(i).getTimeAverageFailure();
            costFlipSucc        = self.plan[i].getTimeAverageSuccess()      # costFlipSucc        = this.plan.get(i).getTimeAverageSuccess();
            costFlipFail_std    = self.plan[i].getTimeStdDevFailure()       # Double costFlipFail_std   = this.plan.get(0).getTimeStdDevFailure();
            costFlipSucc_std    = self.plan[i].getTimeStdDevSuccess()       # Double costFlipSucc_std   = this.plan.get(0).getTimeStdDevSuccess();

            logNormVar   = math.log(((normalCDFFailChain + math.pow(costFlipSucc_std, 2))/math.pow(costFailChain + costFlipSucc, 2)) + 1.0)         # logNormVar		      = Math.log(((normalCDFFailChain+Math.pow(costFlipSucc_std,2))/Math.pow(costFailChain+costFlipSucc,2)) + 1.0);
            logNormMean  = math.log(costFailChain + costFlipSucc) - (logNormVar/2.0)                                                                # logNormMean		      = Math.log(costFailChain+costFlipSucc)-(logNormVar/2.0);
            #logNormPPF   = scipy.stats.lognorm(0.001, math.sqrt(logNormVar), logNormMean)
            logNormalCDF = scipy.stats.lognorm.cdf(tMax, math.sqrt(logNormVar), logNormMean)                                              # logNormalCDF            = jdistlib.LogNormal.cumulative(tMax, logNormMean, Math.sqrt(logNormVar), true, false);

            self.probs          += self.plan[i].getFid() + " S ", logNormalCDF, ","                                   # this.probs           += this.plan.get(i).getFid() + " S " + logNormalCDF + "," ;

            fullSum             += (reward*probSucc*probFailChain*logNormalCDF)                                         # fullSum 		     += (reward*probSucc*probFailChain*logNormalCDF);
            probFailChain       *= ( 1.0 - probSucc )                                                                   # probFailChain        *= ( 1.0 - probSucc );
            costFailChain       += costFlipFail                                                                         # costFailChain        += costFlipFail;
            normalCDFFailChain  += (math.pow(costFlipFail_std, 2))                                                      # normalCDFFailChain   += (Math.pow(costFlipFail_std, 2))

        if len(self.probs) > 0:                                                 # if ( this.probs.length() > 0 ) {
            self.probs = self.probs[0:(len(self.probs) - 1)]                # this.probs = this.probs.substring(0, this.probs.length() - 1);
        return fullSum                                                          # return fullSum;


    def getExpectedValue(self):
        try:
            if len(self.plan) == 0:                                                         # if (this.plan.isEmpty()){
                return 0.0                                                                  # return 0.0;

            if ParameterCore.ParameterCore().UTILITY == UtilityFunctionEnum.UtilityFunctionEnum().CDF:            # if (ParameterCore.UTILITY == UtilityFunctionEnum.CDF){
                return self.getExpectedValueCDF()                                           # return getExpectedValueCDF();

            if ParameterCore.ParameterCore().UTILITY == UtilityFunctionEnum.UtilityFunctionEnum().DELTA:          # if (ParameterCore.UTILITY == UtilityFunctionEnum.DELTA){
                return self.getExpectedValueDeltaFunc()                                     # return getExpectedValueDeltaFunc();
        except:
            raise AssertionError()                                                          # throw new AssertionError();


    def isAdmissible(self):                                         # public Boolean isAdmissible(){
        for i in range(0, len(self.plan)):                                  # for ( int i = 0; i < this.plan.size(); i++ ){
            if i < len(self.plan) - 1:                                      # if( i < this.plan.size()-1 ){
                if self.plan[i].compareTo(self.plan[i+1]) < 0:              # if (this.plan.get(i).compareTo(this.plan.get(i+1)) < 0 ){
                    return False                                            # return false;
        return True                                                         # return true;


    def makeAdmissible(self):                                               # public void makeAdmissible(){
        #print(type(self.plan))
        self.insertionSort(self.plan)                                                    # Collections.sort(this.plan);
        self.plan.reverse()                                                 # Collections.reverse(this.plan);


    def cloneFlightPlan(self):                                              # public FlightPlan cloneFlightPlan( ){
        cloneList = list()                                                  # List<Flight> cloneList = Collections.synchronizedList(new ArrayList<Flight>());
        for f in self.plan:                                                 # for (Flight f : this.plan){
            cloneList.append(Flight.Flight(f))                              ## cloneList.add( new Flight(f) );
        return FlightPlan(cloneList)                                        # return new FlightPlan(cloneList);


    def toString(self):                                 # public String toString(){
        agg = ""                                        # String agg = "";
        for f in self.plan:                             # for (Flight f : this.plan){
            agg += f.toString()                                    # agg += f;
        return agg                                      # return agg;

    def size(self):
        return len(self.plan)
    
    def getAsList(self):
        #print(self.plan)
        return self.plan


    def insertionSort(self, alist):
        for index in range(1, len(alist)):

            currentvalue = alist[index]
            position = index

            while position > 0 and currentvalue.compareTo(alist[position - 1]) < 0:
                alist[position] = alist[position - 1]
                position = position - 1

            alist[position] = currentvalue

    def get(self, n):
        return self.plan[n]

    def set(self, m, obj):
        self.plan[m] = obj

    def remove(self, obj):
        self.plan.remove(obj)
