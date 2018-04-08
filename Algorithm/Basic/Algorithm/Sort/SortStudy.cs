using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Algorithm.Sort
{
    public class SortStudy
    {
        #region bubble
        public List<int> BubbleSort(List<int> arr)
        {
            var sortedArr = new List<int>();
            arr.ForEach(sortedArr.Add);

            if (arr.Count < 2) return arr;

            for (var i = sortedArr.Count; i > 0; i--)
            {
                for (var j = 1; j < i; j++)
                {
                    if (sortedArr[j - 1] > sortedArr[j])
                    {
                        int tmp = sortedArr[j - 1];
                        sortedArr[j - 1] = sortedArr[j];
                        sortedArr[j] = tmp;
                    }
                }
            }
            return sortedArr;
        }
        #endregion

        #region selection
        public List<int> SelectionSort(List<int> arr)
        {
            var sortedArr = new List<int>();
            arr.ForEach(sortedArr.Add);
            if (arr.Count < 2) return arr;

            for (var i = 0; i < sortedArr.Count; i++)
            {
                var minIndex = i;
                var min = sortedArr[minIndex];
                for (var j = i; j < sortedArr.Count; j++)
                {
                    if (min > sortedArr[j])
                    {
                        min = sortedArr[j];
                        minIndex = j;
                    }

                }
                if (i != minIndex)
                {
                    var tmp = sortedArr[i];
                    sortedArr[i] = sortedArr[minIndex];
                    sortedArr[minIndex] = tmp;
                }
            }
            return sortedArr;
        }
        #endregion

        #region insertion
        public List<int> InsertionSort(List<int> arr)
        {
            var sortedArr = new List<int>();

            if (arr.Count < 2) return arr;
            if (arr[0] > arr[1])
            {
                sortedArr.Add(arr[1]);
                sortedArr.Add(arr[0]);
            }
            else
            {
                sortedArr.Add(arr[0]);
                sortedArr.Add(arr[1]);
            }
            for (var i = 2; i < arr.Count; i++)
            {
                var inserted = false;
                if (arr[i] < sortedArr[0])
                {
                    sortedArr.Insert(0, arr[i]);
                    continue;
                }
                for (var j = sortedArr.Count - 1; j > 0; j--)
                {
                    if (arr[i] > sortedArr[j - 1] && arr[i] <= sortedArr[j])
                    {
                        //do insertion
                        sortedArr.Insert(j, arr[i]);
                        inserted = true;
                    }
                }
                if (!inserted)
                {
                    if (arr[i] < sortedArr[sortedArr.Count - 1]) sortedArr.Insert(0, arr[i]);
                    else sortedArr.Add(arr[i]);
                }
            }
            return sortedArr;
        }
        #endregion

        #region Shell
        public List<int> ShellSort(List<int> arr)
        {
            var sortedArr = new List<int>();
            arr.ForEach(sortedArr.Add);

            if (sortedArr.Count < 2) return sortedArr;

            var index = sortedArr.Count / 2;
            while (index >= 1)
            {
                for (var i = index; i < sortedArr.Count; i++)
                {
                    for (var j = i; j >= index; j -= index)
                    {
                        if (sortedArr[j] < sortedArr[j - index])
                        {
                            var tmp = sortedArr[j];
                            sortedArr[j] = sortedArr[j - index];
                            sortedArr[j - index] = tmp;
                        }
                    }

                }
                index /= 2;
            }
            return sortedArr;
        }
        #endregion

        #region Heap Sort
        public List<int> HeapSort(List<int> arr)
        {

            var ret = new List<int>();

            while (arr.Count > 0)
            {
                for (int i = arr.Count / 2 - 1; i >= 0; i--)
                {
                    if (i > 0)
                        HeapMerge(arr, i);

                    HeapMerge(arr, i * 2 + 1);

                    if (i * 2 + 2 <= arr.Count - 1)
                        HeapMerge(arr, i * 2 + 2);

                   
                }
     
                ret.Add(arr[0]);
                arr.RemoveAt(0);
            }

            return ret;
        }

        //construct heap
        private void HeapMerge(List<int> arr,int index)
        {
            if (index > 0 && arr[index] < arr[(index-1)/2])
            {
                var tmp = arr[index];
                arr[index] = arr[(index-1)/2];
                arr[(index-1)/2] = tmp;
                HeapMerge(arr,(index-1)/2);
            }
        }
        #endregion

        #region Merge
        public List<int> MergeSort(List<int> arr)
        {
            List<int> lastOne = null;
            if (arr.Count % 2 != 0)
            {
                lastOne = new List<int> { arr[arr.Count - 1] };
                arr.RemoveAt(arr.Count - 1);
            }

            var groups = new Queue<List<int>>();
            foreach (var a in arr)
            {
                groups.Enqueue(new List<int> { a });
            }

            if (lastOne == null)
            {
                while (groups.Count > 1)
                {
                    var ret = Merge2Arr(groups.Dequeue(), groups.Dequeue());
                    groups.Enqueue(ret);
                }
                return groups.Dequeue();
            }

            while (groups.Count > 1)
            {
                var ret = Merge2Arr(groups.Dequeue(), groups.Dequeue());
                groups.Enqueue(ret);
            }
            return Merge2Arr(groups.Dequeue(), lastOne);
        }

        private List<int> Merge2Arr(List<int> arr1, List<int> arr2)
        {
            var ret = new List<int>();
            if (arr1.Count == 1)
            {
                if (arr1[0] > arr2[0])
                {
                    ret.Add(arr2[0]);
                    ret.Add(arr1[0]);
                }
                else
                {
                    ret.Add(arr1[0]);
                    ret.Add(arr2[0]);
                }
                return ret;
            }

            if (arr1[0] > arr2[0])
            {
                arr2.ForEach(ret.Add);
                
                for (int j = 0; j < arr1.Count; j++)
                {
                    var isInserted = false;
                    for (int i = 0; i < ret.Count - 1; i++)
                    {
                        if (ret[i] < arr1[j] && ret[i + 1] >= arr1[j])
                        {
                            ret.Insert(i+1, arr1[j]);
                            isInserted = true;
                            break;
                        }
                    }
                    if (!isInserted)
                    {
                        if(arr1[j] < ret[ret.Count - 1]) ret.Insert(0,arr1[j]);
                        else   ret.Add(arr1[j]);
                        
                    }
                      
                }
            }
            else
            {
                arr1.ForEach(ret.Add);
                for (int j = 0; j < arr2.Count; j++)
                {
                    var isInserted = false;
                    for (int i = 0; i < ret.Count - 1; i++)
                    {
                        if (ret[i] < arr2[j] && ret[i + 1] >= arr2[j])
                        {
                            ret.Insert(i+1, arr2[j]);
                            isInserted = true;
                            break;
                        }
                    }
                    if (!isInserted)
                    {
                        if (arr2[j] < ret[ret.Count - 1]) ret.Insert(0, arr2[j]);
                        else ret.Add(arr2[j]);
                    }
                }
            }
            return ret;
        }

        #endregion

        #region Quick

        public List<int> QuickSort(List<int> arr)
        {
            var right = arr.Count-1;
            Quick_Sort(arr,0,right);
            return arr;
        }
     
        public void Quick_Sort(List<int> arr, int left, int right)
        {
            if (left >= right) return;

            
            var refNum = arr[left];
            var low = left;
            var high = right;

            while (low < high)
            {
                while (low < high && arr[high] >= refNum) 
                    high--;
                if (low < high)
                    arr[low++] = arr[high];

                while (low < high && arr[low] < refNum)
                    low++;
                if (low < high)
                    arr[high--] = arr[low];
            }
            arr[low] = refNum;

            Quick_Sort(arr,left,right - 1);
            Quick_Sort(arr,left + 1,right);
        } 

        #endregion
    }
}
