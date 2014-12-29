package com.killeent.Tree;

/**
 * Represents an individual node in a Binary Search Tree (BST).
 * 
 * @author Trevor Killeen (2014)
 */
public class BinarySearchTreeNode<T extends Comparable<T>> {

    private T data;                     // data at this node
    private BinarySearchTreeNode<T> left;     // left child
    private BinarySearchTreeNode<T> right;    // right child

    /**
     * Constructs a new binary tree node with the specified values.
     *
     * @param data Value to store at this node.
     * @param left Left subtree of the node.
     * @param right Right subtree of the node.
     */
    public BinarySearchTreeNode(T data, BinarySearchTreeNode<T> left, BinarySearchTreeNode<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    // Getters & Setters for data fields

    /**
     * @return The data stored at this node.
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data at this node to be data.
     * @param data The new data value.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return The left subtree of this node.
     */
    public BinarySearchTreeNode<T> getLeft() {
        return left;
    }

    /**
     * Sets the left subtree of this node to be left.
     * @param left The new left subtree.
     */
    public void setLeft(BinarySearchTreeNode<T> left) {
        this.left = left;
    }

    /**
     * @return The right subtree of this node.
     */
    public BinarySearchTreeNode<T> getRight() {
        return right;
    }

    /**
     * Sets the right subtree of this node to be left.
     * @param right The new left subtree.
     */
    public void setRight(BinarySearchTreeNode<T> right) {
        this.right = right;
    }
    
}
