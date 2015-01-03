package com.killeent.Tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
     * a recursive pre-order traversal.
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
     * an iterative pre-order traversal.
     *
     * @param root The tree to traverse.
     * @return The list of elements in the order that they appear from a pre-order traversal.
     */
    public static <T> List<T> preOrderIterative(BinaryTreeNode<T> root) {
        List<T> result = new LinkedList<T>();
        if (root != null) {
            Stack<BinaryTreeNode<T>> stack = new Stack<BinaryTreeNode<T>>();
            stack.push(root);

            while (!stack.isEmpty()) {
                BinaryTreeNode<T> curr = stack.pop();
                result.add(curr.getData());

                if (curr.getRight() != null) {
                    stack.push(curr.getRight());
                }
                if (curr.getLeft() != null) {
                    stack.push(curr.getLeft());
                }
            }
        }
        return result;
    }

    /**
     * Generates a list of elements resulting from traversing the binary tree using
     * a recursive in-order traversal.
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
     * an iterative in-order traversal.
     *
     * @param root The tree to traverse.
     * @return The list of elements in the order that they appear from a in-order traversal.
     */
    public static <T> List<T> inOrderIterative(BinaryTreeNode<T> root) {
        List<T> result = new LinkedList<T>();
        if (root != null) {
            Stack<BinaryTreeNode<T>> stack = new Stack<BinaryTreeNode<T>>();
            BinaryTreeNode<T> prev = null;
            stack.push(root);

            while (!stack.isEmpty()) {
                BinaryTreeNode<T> curr = stack.peek();

                if (prev == null || prev.getLeft() == curr || prev.getRight() == curr) {
                    // going down
                    if (curr.getLeft() != null) {
                        // traverse left
                        stack.push(curr.getLeft());
                    } else if (curr.getRight() != null) {
                        // place; recurse right
                        result.add(curr.getData());
                        stack.push(curr.getRight());
                    } else {
                        // leaf; pop and place
                        stack.pop();
                        result.add(curr.getData());
                    }
                } else if (curr.getLeft() == prev) {
                    // returning from left; pop and place
                    stack.pop();
                    result.add(curr.getData());
                    if (curr.getRight() != null) {
                        // traverse right
                        stack.push(curr.getRight());
                    }
                } else {
                    // returning from right; simply pop
                    stack.pop();
                }
                prev = curr;
            }
        }
        return result;
    }

    /**
     * Generates a list of elements resulting from traversing the binary tree using
     * a recursive post-order traversal.
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

    /**
     * Generates a list of elements resulting from traversing the binary tree using
     * an iterative post-order traversal.
     *
     * @param root The tree to traverse.
     * @return The list of elements in the order that they appear from a post-order traversal.
     */
    public static <T> List<T> postOrderIterative(BinaryTreeNode<T> root) {
        List<T> result = new LinkedList<T>();
        if (root != null) {
            Stack<BinaryTreeNode<T>> stack = new Stack<BinaryTreeNode<T>>();
            stack.push(root);

            BinaryTreeNode<T> prev = null;

            while (!stack.isEmpty()) {
                BinaryTreeNode<T> curr = stack.peek();

                if (prev == null || prev.getLeft() == curr || prev.getRight() == curr) {
                    // going down
                    if (curr.getLeft() != null) {
                        // traverse left
                        stack.push(curr.getLeft());
                    } else if (curr.getRight() != null) {
                        // traverse right
                        stack.push(curr.getRight());
                    } else {
                        // leaf node; pop and place
                        stack.pop();
                        result.add(curr.getData());
                    }
                } else if (curr.getLeft() == prev) {
                    // returning from left child
                    if (curr.getRight() != null) {
                        // traverse right if we can
                        stack.push(curr.getRight());
                    } else {
                        // otherwise pop and place
                        stack.pop();
                        result.add(curr.getData());
                    }
                } else {
                    // returning from right child; pop and place
                    stack.pop();
                    result.add(curr.getData());
                }

                prev = curr;
            }
        }
        return result;
    }
}
