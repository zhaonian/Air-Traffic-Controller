
from FlightPlan import FlightPlan


class Placement:
    def __init__(self, pid, fp):
        self.pid = pid
        self.fp  = FlightPlan(fp)

    def compareTo(self, other):
        return (self.pid > other.pid) - (self.pid < other.pid)

    def setFp(self, fp):
        if fp == None:
            raise AssertionError("FlightPlan can't be None!")
        self.fp = FlightPlan(fp)

    def getPid(self):
        return self.pid

    def getFp(self):
        return self.fp

    def addFlight(self, f):
        return self.fp.addFlight(f)

    def size(self):
        return self.fp.size()

    def get(self, index):
        return self.fp.get(index)
