using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Algorithm
{
    public class SearchStudy
    {
        #region mid-search,interpoation-search,fibonacci-search
        public int MidSearch(List<int> arr, int searchValue)
        {
            if (arr == null || arr.Count == 0)
                return -1;
            if (arr.Count == 1)
                return arr[0] == searchValue ? 0 : -1;

            var lowIndex = 0;
            var highIndex = arr.Count - 1;


            while (lowIndex < highIndex)
            {
                if (arr[lowIndex] == searchValue) return lowIndex;
                if (arr[highIndex] == searchValue) return highIndex;

                var midIndex = (lowIndex + highIndex) / 2;
                if (arr[midIndex] == searchValue) return midIndex;

                if (arr[midIndex] < searchValue)
                {
                    lowIndex = midIndex + 1;
                }
                else
                {
                    highIndex = midIndex - 1;
                }
            }

            return -1;
        }

        public int InterpolationSearch(List<int> arr, int searchValue)
        {
            if (arr == null || arr.Count == 0)
                return -1;
            if (arr.Count == 1)
                return arr[0] == searchValue ? 0 : -1;

            var lowIndex = 0;
            var highIndex = arr.Count - 1;


            while (lowIndex < highIndex)
            {
                if (arr[lowIndex] == searchValue) return lowIndex;
                if (arr[highIndex] == searchValue) return highIndex;

                var midIndex = lowIndex + (searchValue - arr[lowIndex]) * (highIndex - lowIndex) / (arr[highIndex] - arr[lowIndex]);
                if (arr[midIndex] == searchValue) return midIndex;

                if (arr[midIndex] < searchValue)
                {
                    lowIndex = midIndex + 1;
                }
                else
                {
                    highIndex = midIndex - 1;
                }
            }

            return -1;
        }

        private List<int> F;
        public SearchStudy()
        {
            F_InitOnce();
        }

        private void F_InitOnce()
        {
            if (F == null)
            {
                F = new List<int>();
                //init Fibonacci array
                F.Add(1);
                F.Add(1);
                for (int i = 2; i < 50; i++)
                {
                    F.Add(F[i - 1] + F[i - 2]);
                }
            }
        }

        public int FibonacciSearch(List<int> arr, int key)
        {
            int low, high, mid, k, n;
            low = 0;
            n = arr.Count;
            high = arr.Count;
            k = 0;

            while (n > F[k] - 1) k++;

            while (low <= high)
            {
                mid = low + F[k - 1] - 1;
                if (key < arr[mid])
                {
                    high = mid - 1;
                    --k;
                }
                else if (key > arr[mid])
                {
                    low = mid + 1;
                    k = k - 2;
                }
                else
                {
                    return n;
                }
            }


            return -1;
        }
        #endregion

        #region Index-Study
        //dense Index
        //Block Index
        //Inverted Index
        #endregion
    }
}
