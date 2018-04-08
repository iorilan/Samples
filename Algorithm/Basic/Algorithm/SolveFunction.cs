using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Algorithm
{
    public class SolveFunction
    {
        public double Solve(Func<double, double> func, double x1, double x2,double epsilon)
        {
            if (x1 >= x2) return -2;

            if (func(x1) == 0) return x1;
            if (func(x2) == 0) return x2;
            if (func(x1) * func(x2) > 0) return -1;

            while (Math.Abs(func(x1)) > epsilon)
            {
                double m = (x1 + x2) / 2;
                if (func(x1) * func(m) > 0)
                {
                    x1 = m;
                }
                else if(func(x1) * func(m) < 0)
                {
                    x2 = m;
                }
            }
            return x1;



        }
    }
}
