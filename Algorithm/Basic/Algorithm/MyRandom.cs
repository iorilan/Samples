using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Algorithm
{
    public class MyRandom
    {
        public List<int> GetRandomLst(int from, int to)
        {
            var lst = Enumerable.Range(from, to - from + 1);
            return lst.OrderBy(a => Guid.NewGuid()).ToList();
        }
    }
}
