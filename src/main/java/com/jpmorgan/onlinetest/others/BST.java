package com.jpmorgan.onlinetest.others;

/*
二叉树的数据结构：

class TreeNode{
    int val;
    //左孩子
    TreeNode left;
    //右孩子
    TreeNode right;
}
二叉树的题目普遍可以用递归和迭代的方式来解

1.求二叉树的最大深度
int maxDeath(TreeNode node){
    if(node==null){
        return 0;
    }
    int left = maxDeath(node.left);
    int right = maxDeath(node.right);
    return Math.max(left,right) + 1;
}
2.求二叉树的最小深度
    int getMinDepth(TreeNode root){
        if(root == null){
            return 0;
        }
        return getMin(root);
    }
    int getMin(TreeNode root){
        if(root == null){
            return Integer.MAX_VALUE;
        }
        if(root.left == null&&root.right == null){
            return 1;
        }
        return Math.min(getMin(root.left),getMin(root.right)) + 1;
    }
3,求二叉树中节点的个数
    int numOfTreeNode(TreeNode root){
        if(root == null){
            return 0;

        }
        int left = numOfTreeNode(root.left);
        int right = numOfTreeNode(root.right);
        return left + right + 1;
    }
4,求二叉树中叶子节点的个数
    int numsOfNoChildNode(TreeNode root){
        if(root == null){
            return 0;
        }
        if(root.left==null&&root.right==null){
            return 1;
        }
        return numsOfNodeTreeNode(root.left)+numsOfNodeTreeNode(root.right);

    }


    两个二叉树是否完全相同
    boolean isSameTreeNode(TreeNode t1,TreeNode t2){
        if(t1==null&&t2==null){
            return true;
        }
        else if(t1==null||t2==null){
            return false;
        }
        if(t1.val != t2.val){
            return false;
        }
        boolean left = isSameTreeNode(t1.left,t2.left);
        boolean right = isSameTreeNode(t1.right,t2.right);
        return left&&right;

    }
 */
public class BST {
}
