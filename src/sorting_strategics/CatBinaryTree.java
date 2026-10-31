package sorting_strategics;

import animals.Cat;
import comporators.*;
import interfaces.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CatBinaryTree implements CatBinaryTreeInterface{
	public static Random random = new Random();

	private class BinaryTreeNode {
		private Cat cat;
		private BinaryTreeNode left;
		private BinaryTreeNode right;
		private BinaryTreeNode parent;

		public BinaryTreeNode(Cat Cat) {
			this.cat = Cat;
		}

		public Cat getCat() {
			return cat;
		}

		public BinaryTreeNode getLeft() {
			return left;
		}

		public void setLeft(BinaryTreeNode left) {
			this.left = left;
		}

		public BinaryTreeNode getRight() {
			return right;
		}

		public void setRight(BinaryTreeNode right) {
			this.right = right;
		}

		@SuppressWarnings("unused")
		public BinaryTreeNode getParent() {
			return parent;
		}

		public void setParent(BinaryTreeNode parent) {
			this.parent = parent;
		}
	}

	private BinaryTreeNode root;
	private CatAgeComperator comperAge = new CatAgeComperator();

	public CatBinaryTree() {
		this.root = null;
	}

	public void add(Cat cat) {
		this.root = this.add(this.root, cat);
	}

	private BinaryTreeNode add(BinaryTreeNode node, Cat cat) {
		if (node == null) {
			return new BinaryTreeNode(cat);
		} else if (comperAge.compare(cat, node.getCat()) > 0) {
			node.setLeft(add(node.getLeft(), cat));
			node.getLeft().setParent(node);
		} else if (comperAge.compare(cat, node.getCat()) < 0) {
			node.setRight(add(node.getRight(), cat));
			node.getRight().setParent(node);
		} else if (comperAge.compare(cat, node.getCat()) == 0) {
			node.setRight(add(node.getRight(), cat));
			node.getRight().setParent(node);
		}
		return node;
	}

	private int randomHappynessReduction() {
		int reduction = (random.nextInt(Cat.LifeSpan) + 1) * 2;
		return reduction;
	}

	public String printCatOrdered() {
		return this.printCatOrdered(this.root);
	}

	private String printCatOrdered(BinaryTreeNode node) {
		if (node == null) {
			return "";
		} else {
			String print = printCatOrdered(node.getLeft());
			print += node.getCat().toString() + "\n";
			print += printCatOrdered(node.getRight());
			return print;
		}

	}

	public String ageCatOneYear() {
		String removedCats = ageAndRemove(root);
		resortTree(); // After aging and removing, resort the tree if needed
		if (removedCats.isBlank() || removedCats.isEmpty()) {
			return " No Cats Were Removed!\n";
		} else {
			return removedCats;
		}
	}

	private String ageAndRemove(BinaryTreeNode node) {
		String removedCat = "";
		if (node != null) {

			// Age the current cat
			node.getCat().ageOneYear();
			node.getCat().setHappiness(node.getCat().getHappiness() - randomHappynessReduction());
			// Check conditions for removal
			if (node.getCat().getAge() > Cat.LifeSpan || node.getCat().getHappiness() <= 0) {
				removedCat += "\t" + node.getCat().getName() + ", "
						+ (node.getCat().getAge() > Cat.LifeSpan ? ("after age: " + (node.getCat().getAge() - 1))
								: ("at age: " + node.getCat().getAge()))
						+ ", it was " + (node.getCat().getHappiness() <= 0 ? "SAD!" : "HAPPY") + "\n";
			}
			removedCat += ageAndRemove(node.getLeft());
			removedCat += ageAndRemove(node.getRight());
		}
		return removedCat;
	}

	private void resortTree() {
		List<Cat> catList = new ArrayList<>();
		inOrderTraversal(root, catList);
		buildTree(catList);
	}

	private void buildTree(List<Cat> catList) {
		clear();
		for (int i = 0; i < catList.size(); i++) {
			if (catList.get(i).getAge() <= Cat.LifeSpan && catList.get(i).getHappiness() > 0) {
				add(catList.get(i));
			}
		}
	}

	public int countCats() {
		return countCats(root);
	}

	private int countCats(BinaryTreeNode node) {
		if (node == null) {
			return 0;
		} else {
			int count = 1; // Count the current cat
			count += countCats(node.getLeft()); // Recursively count cats in the left subtree
			count += countCats(node.getRight()); // Recursively count cats in the right subtree
			return count;
		}
	}

	public double feedCats() {
		return feedCats(root);
	}

	private double feedCats(BinaryTreeNode node) {
		if (node == null) {
			return 0;
		} else {
			double foodAmount = node.getCat().feed(); // Count the current cat
			node.getCat().setHappiness(100);
			foodAmount += feedCats(node.getLeft()); // Recursively count cats in the left subtree
			foodAmount += feedCats(node.getRight()); // Recursively count cats in the right subtree
			return foodAmount;
		}
	}

	public String makeSound() {
		return makeSound(root) + " ";
	}

	private String makeSound(BinaryTreeNode node) {
		if (node == null) {
			return "";
		} else {
			String noise = " " + node.getCat().makeNoise() + ","; // Count the current cat
			noise += makeSound(node.getLeft()); // Recursively count cats in the left subtree
			noise += makeSound(node.getRight()); // Recursively count cats in the right subtree
			return noise;
		}
	}

	private void inOrderTraversal(BinaryTreeNode node, List<Cat> catList) {
		if (node != null) {
			inOrderTraversal(node.getLeft(), catList);
			catList.add(node.getCat());
			inOrderTraversal(node.getRight(), catList);
		}
	}

	public String printTree() {
//		this.printTree(this.root, "");
		return this.printTree(this.root, "");
	}

	private String printTree(BinaryTreeNode node, String prefix) {
//		String print = "";
		if (node == null) {
			return "";
		} else {
			String print = prefix + "|_ " + stringForBinaryTree(node) + "\n";
			print += printTree(node.getLeft(), prefix + "|  ");
			print += printTree(node.getRight(), prefix + "|  ");
			return print;
		}

	}

	private String stringForBinaryTree(BinaryTreeNode node) {
		return node.getCat().getName() + "(age:" + node.getCat().getAge() + ")";
	}

	public void clear() {
		this.root = null;
	}

	@Override
	public List<Cat> getCatsInOrder() {
		List<Cat> ordered = new ArrayList<>();
		inOrderTraversal(root, ordered);
		return ordered;
	}

	@Override
	public CatTreeNode getTreeSnapshot() {
		return snapshot(root);
	}

	private CatTreeNode snapshot(BinaryTreeNode node) {
		if (node == null) {
			return null;
		}
		return new CatTreeNode(node.getCat(), snapshot(node.getLeft()), snapshot(node.getRight()));
	}
}
