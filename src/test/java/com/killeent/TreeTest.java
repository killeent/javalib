package com.killeent;

import com.killeent.Tree.BinaryTreeNode;
import com.killeent.Tree.Tree;
import junit.framework.Assert;
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

    /**
     * Tests that an empty tree is symmetric.
     */
    @Test
    public void testSymmetricEmptyTree() {
        Assert.assertTrue(Tree.symmetric(null));
    }

    /**
     * Tests that a single element tree is symmetric.
     */
    @Test
    public void testSymmetricSingleElementTree() {
        Assert.assertTrue(Tree.symmetric(new BinaryTreeNode<Integer>(1, null, null)));
    }

    /**
     * Tests that non-mirrored trees are not symmetric:
     *
     *   1       1              1           1             1
     *  /   AND   \     AND    / \   AND   / \    AND    / \
     * 1           1          1   1       1   1         1   1
     *                         \  \      /    /        /   / \
     *                          1  1    1    1        1   1   1
     */
    @Test
    public void testSymmetricNonMirroredTrees() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(1, null, null),
                null
        );
        Assert.assertFalse(Tree.symmetric(root));

        root = new BinaryTreeNode<Integer>(
                1,
                null,
                new BinaryTreeNode<Integer>(1, null, null)
        );
        Assert.assertFalse(Tree.symmetric(root));

        root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(
                        1,
                        null,
                        new BinaryTreeNode<Integer>(1, null, null)
                ),
                new BinaryTreeNode<Integer>(
                        1,
                        null,
                        new BinaryTreeNode<Integer>(1, null, null)
                )
        );
        Assert.assertFalse(Tree.symmetric(root));

        root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(
                        1,
                        new BinaryTreeNode<Integer>(1, null, null),
                        null
                ),
                new BinaryTreeNode<Integer>(
                        1,
                        new BinaryTreeNode<Integer>(1, null, null),
                        null
                )
        );
        Assert.assertFalse(Tree.symmetric(root));

        root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(
                        1,
                        new BinaryTreeNode<Integer>(1, null, null),
                        null
                ),
                new BinaryTreeNode<Integer>(
                        1,
                        new BinaryTreeNode<Integer>(1, null, null),
                        new BinaryTreeNode<Integer>(1, null, null)
                )
        );
        Assert.assertFalse(Tree.symmetric(root));
    }

    /**
     * Tests that mirrored trees with non-mirrored values are not symmetric.
     */
    @Test
    public void testSymmetricNonMirroredValuesTrees() {
        /**
         *    1
         *   / \
         *  1   2
         */
        BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(1, null, null),
                new BinaryTreeNode<Integer>(2, null, null)
        );
        Assert.assertFalse(Tree.symmetric(root));

        /**
         *      1
         *     / \
         *    3  4
         *   /    \
         *  1      1
         */
        root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(
                        3,
                        new BinaryTreeNode<Integer>(1, null, null),
                        null),
                new BinaryTreeNode<Integer>(
                        4,
                        null,
                        new BinaryTreeNode<Integer>(1, null, null))
        );
        Assert.assertFalse(Tree.symmetric(root));

        /**
         *       1
         *     /  \
         *    1    1
         *   / \  / \
         *  1  1  2  1
         */
        root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(
                        1,
                        new BinaryTreeNode<Integer>(1, null, null),
                        new BinaryTreeNode<Integer>(1, null, null)),
                new BinaryTreeNode<Integer>(
                        1,
                        new BinaryTreeNode<Integer>(2, null, null),
                        new BinaryTreeNode<Integer>(1, null, null))
        );
        Assert.assertFalse(Tree.symmetric(root));

        /**
         *       1
         *     /  \
         *    1    1
         *   / \  / \
         *  2  1  1  1
         */
        root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(
                        1,
                        new BinaryTreeNode<Integer>(2, null, null),
                        new BinaryTreeNode<Integer>(1, null, null)),
                new BinaryTreeNode<Integer>(
                        1,
                        new BinaryTreeNode<Integer>(1, null, null),
                        new BinaryTreeNode<Integer>(1, null, null))
        );
        Assert.assertFalse(Tree.symmetric(root));
    }

    /**
     * Tests for symmetric binary trees.
     */
    @Test
    public void testSymmetricSymmetricTrees() {
        /**
         *   1
         *  / \
         * 1   1
         */
        BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(1, null, null),
                new BinaryTreeNode<Integer>(1, null, null)
        );
        Assert.assertTrue(Tree.symmetric(root));

        /**
         *     1
         *    / \
         *   2   2
         *  /     \
         * 3      3
         */
        root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(
                        2,
                        new BinaryTreeNode<Integer>(3, null, null),
                        null),
                new BinaryTreeNode<Integer>(
                        2,
                        null,
                        new BinaryTreeNode<Integer>(3, null, null))
        );
        Assert.assertTrue(Tree.symmetric(root));

        /**
         *      1
         *    /  \
         *   2    2
         *    \  /
         *    3  3
         */
        root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(
                        2,
                        null,
                        new BinaryTreeNode<Integer>(3, null, null)),
                new BinaryTreeNode<Integer>(
                        2,
                        new BinaryTreeNode<Integer>(3, null, null),
                        null)
        );
        Assert.assertTrue(Tree.symmetric(root));

        /**
         *     1
         *    / \
         *   2   2
         *  / \ / \
         * 3  4 4  3
         */
        root = new BinaryTreeNode<Integer>(
                1,
                new BinaryTreeNode<Integer>(
                        2,
                        new BinaryTreeNode<Integer>(3, null, null),
                        new BinaryTreeNode<Integer>(4, null, null)),
                new BinaryTreeNode<Integer>(
                        2,
                        new BinaryTreeNode<Integer>(4, null, null),
                        new BinaryTreeNode<Integer>(3, null, null))
        );
        Assert.assertTrue(Tree.symmetric(root));
    }

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
     * Tests traversing a tree that is a linked list.
     *
     *        4            1
     *       /              \
     *      3                2
     *     /        AND       \
     *    2                    3
     *   /                      \
     *  1                        4
     */
    @Test
    public void testTraverseListTrees() {
        List<Integer> preExpected = Arrays.asList(4, 3, 2, 1);
        List<Integer> inExpected = Arrays.asList(1, 2, 3, 4);
        List<Integer> postExpected = Arrays.asList(1, 2, 3, 4);
        BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(
                4,
                new BinaryTreeNode<Integer>(
                        3,
                        new BinaryTreeNode<Integer>(
                                2,
                                new BinaryTreeNode<Integer>(1, null, null),
                                null
                        ),
                        null),
                null
        );
        testTraversals(root, preExpected, inExpected, postExpected);

        preExpected = Arrays.asList(1, 2, 3, 4);
        inExpected = Arrays.asList(1, 2, 3, 4);
        postExpected = Arrays.asList(4, 3, 2, 1);
        root = new BinaryTreeNode<Integer>(
                1,
                null,
                new BinaryTreeNode<Integer>(
                        2,
                        null,
                        new BinaryTreeNode<Integer>(
                                3,
                                null,
                                new BinaryTreeNode<Integer>(4, null, null)
                        ))
                );
        testTraversals(root, preExpected, inExpected, postExpected);
    }

    /**
     * Tests traversing a large complex tree.
     *
     *                      7
     *                     / \
     *                    3   9
     *                   /     \
     *                  4      12
     *                 / \     /
     *                1   5   8
     *                        \
     *                        11
     */
    @Test
    public void testTraversalComplexTree() {
        List<Integer> preExpected = Arrays.asList(7, 3, 4, 1, 5, 9, 12, 8, 11);
        List<Integer> inExpected = Arrays.asList(1, 4, 5, 3, 7, 9, 8, 11, 12);
        List<Integer> postExpected = Arrays.asList(1, 5, 4, 3, 11, 8, 12, 9, 7);
        BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(
                7,
                new BinaryTreeNode<Integer>(
                        3,
                        new BinaryTreeNode<Integer>(
                                4,
                                new BinaryTreeNode<Integer>(1, null, null),
                                new BinaryTreeNode<Integer>(5, null, null)),
                        null),

                new BinaryTreeNode<Integer>(
                        9,
                        null,
                        new BinaryTreeNode<Integer>(
                                12,
                                new BinaryTreeNode<Integer>(
                                        8,
                                        null,
                                        new BinaryTreeNode<Integer>(11, null, null)
                                ),
                                null)
                        )
                );
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
