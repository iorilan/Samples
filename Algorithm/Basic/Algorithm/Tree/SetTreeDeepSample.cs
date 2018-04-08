using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Algorithm.Tree
{
    public class BinaryTreeNode
    {
        public string Name { get; set; }
        public BinaryTreeNode LeftChild { get; set; }
        public BinaryTreeNode RightChild { get; set; }
        public int LeftDeep = 0;
        public int RightDeep = 0;
    }

    public class TreeNode
    {
        public string Name { get; set; }
        public TreeNode ParrentNode { get; set; }
        public List<TreeNode> LstChildren { get; set; }
        public int Deep { get; set; }

        public void AddChildren(TreeNode node)
        {
            LstChildren.Add(node);
            node.ParrentNode = this;
        }
    }

    public class SetTreeDeepSample
    {
        #region tree

        public void SetTreeDeep(TreeNode current)
        {
            current.Deep = GetDeepth(current);
            if (!current.LstChildren.Any())
                return;

            foreach (var children in current.LstChildren)
            {
                SetTreeDeep(children);
            }
        }

        public int GetDeepth(TreeNode node)
        {
            if (node == null)
                return 0;

            if (node.LstChildren.Any())
            {
                var deepArr = node.LstChildren.Select(GetDeepth).ToList();
                var maxChildDeep = deepArr.Max() + 1;
                return maxChildDeep;
            }
            return 0;
        }

        #endregion

        #region Binary Tree

        #endregion

    }
}
