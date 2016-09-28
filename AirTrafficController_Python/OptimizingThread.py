import threading
import ParameterCore
import Utils
import Placement

class OptimizingThread:
    def __init__(self, wfs, ctrl, sem, p):
        self.wfs    = wfs
        self.ctrl   = ctrl
        self.sem    = sem
        self.p      = p

    def run(self):
        try:
            self.sem.acquire()
            Utils.Utils().optimizePlacement(self.wfs, self.ctrl, self.p)
            self.sem.release()
        except Exception as e:
            print(e)

    def getP(self):
        return self.p

    def getPid(self):
        return self.p.getPid()

    def getExp(self):
        return self.p.getFp().getExpectedValue()




