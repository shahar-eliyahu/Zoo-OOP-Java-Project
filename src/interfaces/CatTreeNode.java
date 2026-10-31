package interfaces;

import animals.Cat;

/**
 * Read-only snapshot of one node of the cats binary tree, used by the GUI to draw the tree.
 */
public record CatTreeNode(Cat cat, CatTreeNode left, CatTreeNode right) {
}
