package com.killeent;

import com.killeent.Tree.BinaryTreeNode;
import com.killeent.Tree.Tree;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for algorithms implemented in {@link com.killeent.Tree.Tree}.
 *
 * @author Trevor Killeen (2014)
 */
public class TreeTest {

    /**
     * Test for {@link com.killeent.Tree.Tree#symmetric}.
     */

    // todo: implement this

    /**
     * Tests for traversals.
     */

    /**
     * Tests traversing an empty (null) tree.
     */
    @Test
    public void testTraverseNullTree() {
        List<Object> expected = new ArrayList<Object>();
        testTraversals(null, expected, expected, expected);
    }

    /**
     * Tests traversing a single element tree.
     */
    @Test
    public void testTraverseSingleElementTree() {
        List<Integer> expected = Arrays.asList(1);
        testTraversals(new BinaryTreeNode<Integer>(1, null, null), expected, expected, expected);
    }

    /**
     * Tests traversing a simple tree of three elements:
     *
     *      2
     *     / \
     *   1    3
     */
    @Test
    public void testTraverseSimpleTree() {
        List<Integer> preExpected = Arrays.asList(2, 1, 3);
        List<Integer> inExpected = Arrays.asList(1, 2, 3);
        List<Integer> postExpected = Arrays.asList(1, 3, 2);
        BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(
                2,
                new BinaryTreeNode<Integer>(1, null, null),
                new BinaryTreeNode<Integer>(3, null, null));
        testTraversals(root, preExpected, inExpected, postExpected);
    }

    /**
     * Tests for proper pre-, in- and post-order traversal implementations for the specified
     * tree. Implementations tested
     *
     * {@link com.killeent.Tree.Tree#preOrder(com.killeent.Tree.BinaryTreeNode)},
     * {@link com.killeent.Tree.Tree#preOrderIterative(com.killeent.Tree.BinaryTreeNode)},
     * {@link com.killeent.Tree.Tree#inOrder(com.killeent.Tree.BinaryTreeNode)},
     * {@link com.killeent.Tree.Tree#inOrderIterative(com.killeent.Tree.BinaryTreeNode)},
     * {@link com.killeent.Tree.Tree#postOrder(com.killeent.Tree.BinaryTreeNode)},
     * {@link com.killeent.Tree.Tree#postOrderIterative(com.killeent.Tree.BinaryTreeNode)}.
     *
     * @param root The tree to test.
     * @param preOrder Expected pre-order traversal.
     * @param inOrder Expected in-order traversal.
     * @param postOrder Expected post-order traversal.
     */
    private <T> void testTraversals(BinaryTreeNode<T> root,
                                    List<T> preOrder,
                                    List<T> inOrder,
                                    List<T> postOrder) {
        TestUtil.assertListEquals(preOrder, Tree.preOrder(root));
        TestUtil.assertListEquals(preOrder, Tree.preOrderIterative(root));
        TestUtil.assertListEquals(inOrder, Tree.inOrder(root));
        TestUtil.assertListEquals(inOrder, Tree.inOrderIterative(root));
        TestUtil.assertListEquals(postOrder, Tree.postOrder(root));
        TestUtil.assertListEquals(postOrder, Tree.postOrderIterative(root));
    }

}
