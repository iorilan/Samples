using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Algorithm
{
    /// <summary>
    /// not implemented 
    /// </summary>
    public class KMP
    {
        private int[] GetNextArr(string str)
        {
            var arr = new int[str.Length];
            var startIndex = 0;
            var nextIndex = 1;
            arr[0] = 0;
            while (nextIndex < str.Length + 1)
            {
                if (nextIndex != 1 && str[startIndex] == str[nextIndex - 1])
                {
                    startIndex++;
                    arr[nextIndex - 1] = startIndex + 1;
                }
                else
                {
                    arr[nextIndex - 1] = 0;
                    startIndex = 0;
                }
                nextIndex++;
            }
            return arr;
        }

        public int FirstIndexOf(string strIn, string strAll)
        {
            int[] nextArr = GetNextArr(strIn);

            int matchCount = 0;
            for (int i = 0, j = 0; i < strAll.Length ; i++, j++)
            {
                if (strIn[j] == strAll[i])
                {
                    matchCount++;
                }
                else
                {
                    j = 0;
                    matchCount = 0;
                }

                if (matchCount == strIn.Length)
                    return i;
            }
            return -1;
        }
    }
}
