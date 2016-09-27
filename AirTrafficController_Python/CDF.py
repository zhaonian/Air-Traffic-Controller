#import scipy.stats
from math import *

# table
#norm_cdf = []
#x_min = -2.0
#x_max = 2.0
#n_samps = 5000 
#dx = float(x_max - x_min)/float(n_samps)
#
#def stdnormpdf(x):
#    return 1.0/sqrt(2.0*pi) * exp(-x*x/2.0)
#
#def init_stdnorm():
#    for i in range(0, n_samps):
#        norm_cdf.append(scipy.stats.norm.cdf(x_min + i*dx, 0, 1))
#    return

# compute using table
#def lognormcdf_t(x, sigma, miu):
#    z = (log(x) - miu)/float(sigma)
#    if z >= x_max or z <= x_min:
#        return 1
#    n = int(floor((z - x_min)/dx))
#    delta_x = z - (x_min + n*dx)
#    y0 = norm_cdf[n]
#    dy = norm_cdf[n + 1] - norm_cdf[n]
#    return y0 + dy/dx * delta_x


def erfc(x):
    z = abs(x)
    t = 1. / (1. + 0.5*z)
    r = t * exp(-z*z-1.26551223+t*(1.00002368+t*(.37409196+
                    t*(.09678418+t*(-.18628806+t*(.27886807+
                    t*(-1.13520398+t*(1.48851587+t*(-.82215223+
                    t*.17087277)))))))))
    if (x >= 0.):
        return r
    else:
        return 2. - r

# compute through erfc expansion
def lognormcdf_erfc(x, sigma, miu):
    return 1 - 0.5*erfc((log(x) - miu)/(2**0.5*sigma))

def lognormcdf(x, sigma, miu):
    return lognormcdf_erfc(x, sigma, miu)

#def test_cdf_table():
#    a = scipy.stats.lognorm.cdf(4, 1, 2)
#    b = lognormcdf(4, 1, 2)
#    c = lognormcdf_t(4, 1, 2)
#    print(a)
#    print(b)
#    print(c)
#
#init_stdnorm()
#test_cdf_table()
