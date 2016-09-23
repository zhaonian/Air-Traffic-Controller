
# UtilityFunctionEnum = enumerate(DELTA, CDF)

class UtilityFunctionEnum:
    def __init__(self, DELTA, CDF):
        self.DELTA = DELTA
        self.CDF = CDF

    def __iter__(self):
        return [self.CDF,
                self.DELTA]

    def __getattr__(self, name):
        if name in self.__iter__():
            return name
        else:
            raise AttributeError

# public enum UtilityFunctionEnum {
# 	DELTA, CDF
# }
