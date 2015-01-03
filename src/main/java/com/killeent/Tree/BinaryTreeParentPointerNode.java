package com.killeent.Tree;

/**
 * Represents an individual node in a Binary Tree. Also has a pointer to its parent.
 *
 * @author Trevor Killeen (2015)
 */
public class BinaryTreeParentPointerNode<T> extends BinaryTreeNode<T> {

    private BinaryTreeParentPointerNode<T> parent;  // parent of this node

    /**
     * Constructs a new binary tree node with the specified values.
     *
     * @param data Value to store at this node.
     * @param left Left subtree of the node.
     * @param right Right subtree of the node.
     * @param parent Parent of this node.
     */
    public BinaryTreeParentPointerNode(T data,
                                       BinaryTreeParentPointerNode<T> left,
                                       BinaryTreeParentPointerNode<T> right,
                                       BinaryTreeParentPointerNode<T> parent) {
        super(data, left, right);
        this.parent = parent;
    }

    // Getters and setters for data fields

    /**
     * @return The parent of this node.
     */
    public BinaryTreeParentPointerNode<T> getParent() {
        return parent;
    }

    /**
     * Sets the parent node of this subtree to be parent.
     *
     * @param parent the new parent.
     */
    public void setParent(BinaryTreeParentPointerNode<T> parent) {
        this.parent = parent;
    }
}
