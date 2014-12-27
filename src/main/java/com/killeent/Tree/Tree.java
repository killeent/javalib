package com.killeent.Tree;

/**
 * Various algorithms that operate over trees.
 *
 * @author Trevor Killeen (2014)
 */
public class Tree {

    /**
     * Tests whether the tree rooted at root is symmetric (i.e. its left and subtrees are
     * mirror images of each other).
     *
     * @param root The root of the tree to consider.
     * @return true if the tree is symmetric, otherwise false.
     */
    public static <T> boolean symmetric(BinaryTreeNode<T> root) {
        return (root == null) || symmetric(root.getLeft(), root.getRight());
    }

    /**
     * Helper function for checking if a tree is symmetric or not. Compares nodes in the
     * left and right subtrees of the tree to see if they match up.
     *
     * @return true if the left and right subtrees rooted at left and right are symmetric,
     * otherwise false.
     */
    private static <T> boolean symmetric(BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null || right == null) {
            return false;
        } else {
            return left.getData().equals(right.getData()) &&
                    symmetric(left.getLeft(), right.getRight()) &&
                    symmetric(left.getRight(), right.getLeft());
        }
    }
}
