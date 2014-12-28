package com.killeent.Tree;

import java.util.LinkedList;
import java.util.List;

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

    /**
     * Generates a list of elements resulting from traversing the binary tree using
     * a pre-order traversal.
     *
     * @param root The tree to traverse.
     * @return The list of elements in the order that they appear from a pre-order traversal.
     */
    public static <T> List<T> preOrder(BinaryTreeNode<T> root) {
        List<T> result = new LinkedList<T>();
        preOrder(root, result);
        return result;
    }

    /**
     * Recursive helper function for pre-order traversal.
     */
    private static <T> void preOrder(BinaryTreeNode<T> root, List<T> result) {
        if (root != null) {
            result.add(root.getData());
            preOrder(root.getLeft(), result);
            preOrder(root.getRight(), result);
        }
    }

    /**
     * Generates a list of elements resulting from traversing the binary tree using
     * a in-order traversal.
     *
     * @param root The tree to traverse.
     * @return The list of elements in the order that they appear from a in-order traversal.
     */
    public static <T> List<T> inOrder(BinaryTreeNode<T> root) {
        List<T> result = new LinkedList<T>();
        inOrder(root, result);
        return result;
    }

    /**
     * Recursive helper function for in-order traversal.
     */
    private static <T> void inOrder(BinaryTreeNode<T> root, List<T> result) {
        if (root != null) {
            inOrder(root.getLeft(), result);
            result.add(root.getData());
            inOrder(root.getRight(), result);
        }
    }

    /**
     * Generates a list of elements resulting from traversing the binary tree using
     * a post-order traversal.
     *
     * @param root The tree to traverse.
     * @return The list of elements in the order that they appear from a post-order traversal.
     */
    public static <T> List<T> postOrder(BinaryTreeNode<T> root) {
        List<T> result = new LinkedList<T>();
        postOrder(root, result);
        return result;
    }

    /**
     * Recursive helper function for post-order traversal.
     */
    private static <T> void postOrder(BinaryTreeNode<T> root, List<T> result) {
        if (root != null) {
            postOrder(root.getLeft(), result);
            postOrder(root.getRight(), result);
            result.add(root.getData());
        }
    }
}
