using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Algorithm
{
    public class ToopoNode
    {
        public string NodeValue;
        public int NodeId;
        public List<ToopoNode> TargetNodes;
    }
    public class ToopoSort
    {
        private List<ToopoNode> _resultNodes; 
        public ToopoSort()
        {
            _resultNodes = new List<ToopoNode>();
        }

        public List<ToopoNode> GetSortedList(List<ToopoNode> nodes)
        {
            GetSortedNode(nodes);
            return _resultNodes;
        }

        private void GetSortedNode(List<ToopoNode> nodes)
        {
            if (nodes.Count == 0)
                return;

            //step 1 : get all target nodes
            var targetList = new List<ToopoNode>();
            foreach (var node in nodes)
            {
                foreach (var n in node.TargetNodes)
                {
                    if (!targetList.Contains(n))
                        targetList.Add(n);
                }
            }

            var noHaveInCommingEdgeNodes = nodes.Where(node => !targetList.Contains(node)).ToList();
            if (noHaveInCommingEdgeNodes.Count == 0)
                return;

            //step 2: add not-have-target nodes into result
            foreach (ToopoNode n in noHaveInCommingEdgeNodes)
            {

                if(!_resultNodes.Contains(n))
                _resultNodes.Add(n);

                nodes.Remove(n);
            }

            //step 3:recurs
            GetSortedNode(nodes);
        }

    }
}
