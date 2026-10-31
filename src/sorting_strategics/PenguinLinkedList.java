package sorting_strategics;

import animals.Penguin;
import comporators.PenguinNameComparator;
import interfaces.PenguinsLinkedListInterface;

public class PenguinLinkedList implements PenguinsLinkedListInterface {
	private class Node {
		private Node next;
		private Penguin penguin;

		public Node(Penguin penguin) {
			this.penguin = penguin;
		}

		public void setNextPenguin(Node next) {
			this.next = next;
		}

		public Node nextPenguin() {
			return next;
		}

		public Penguin getPenguin() {
			return penguin;
		}

		public double getPenguinHeight() {
			return penguin.getHeight();
		}

		public int getPenguinAge() {
			return penguin.getAge();
		}

	}

	private Node leader;
	private int sortFormPenguin;
	private Long leaderID;

	public PenguinLinkedList() {
		this.leader = null;
		sortFormPenguin = 2;
	}

	@Override
	public int getSortFormPenguin() {
		return sortFormPenguin;
	}

	@Override
	public void setSortFormPenguin(int sortFormPenguin) {
		this.sortFormPenguin = sortFormPenguin;
	}

	@Override
	public int countPenguinsInList() {
		int count = 0;
		Node next = this.leader;
		while (next != null) {
			count++;
			next = next.nextPenguin();
		}
		return count;
	}

	@Override
	public void addPenguin(Penguin penguin) {
		if (this.sortFormPenguin == 1) {
			sortPenguinByName(penguin);
		} else if (this.sortFormPenguin == 2) {
			sortPenguinByHeight(penguin);
		} else if (this.sortFormPenguin == 3) {
			sortPenguinByAge(penguin);
		}
	}

	@Override
	public void sortPenguinByName(Penguin penguin) {
		PenguinNameComparator compareNames = new PenguinNameComparator();
		Node newNodePenguin = new Node(penguin);
		if (leader == null || 0 > compareNames.compare(penguin, leader.getPenguin())) {
			newNodePenguin.setNextPenguin(leader);
			leader = newNodePenguin;
			return;
		}
		Node currentPenguine = leader;
		while (currentPenguine.nextPenguin() != null
				&& compareNames.compare(currentPenguine.nextPenguin().getPenguin(), penguin) < 0) {
			currentPenguine = currentPenguine.nextPenguin();
		}
		newNodePenguin.setNextPenguin(currentPenguine.nextPenguin());
		currentPenguine.setNextPenguin(newNodePenguin);
	}

	@Override
	public void sortPenguinByHeight(Penguin penguin) {
		Node newNodePenguin = new Node(penguin);

		if ((leader == null)) {
			newNodePenguin.setNextPenguin(leader);
			leader = newNodePenguin;
			this.leaderID = leader.getPenguin().getID();
			return;
		}
		if (penguin.getHeight() >= leader.getPenguinHeight()) {
			if (leader.getPenguinHeight() == penguin.getHeight()) {
				if (leader.getPenguin().getID() > penguin.getID()) {
					newNodePenguin.setNextPenguin(leader);
					leader = newNodePenguin;
					this.leaderID = leader.getPenguin().getID();
					return;
				}
			} else {
				newNodePenguin.setNextPenguin(leader);
				leader = newNodePenguin;
				this.leaderID = leader.getPenguin().getID();
				return;
			}
		}
		Node currentPenguine = leader;
		while (currentPenguine.nextPenguin() != null
				&& currentPenguine.nextPenguin().getPenguinHeight() > penguin.getHeight()) {
			currentPenguine = currentPenguine.nextPenguin();
		}
		newNodePenguin.setNextPenguin(currentPenguine.nextPenguin());
		currentPenguine.setNextPenguin(newNodePenguin);
	}

	@Override
	public void sortPenguinByAge(Penguin penguin) {
		Node newNodePenguin = new Node(penguin);

		if (leader == null || penguin.getAge() < leader.getPenguinAge()) {
			newNodePenguin.setNextPenguin(leader);
			leader = newNodePenguin;
			return;
		}
		Node currentPenguine = leader;
		while (currentPenguine.nextPenguin() != null
				&& currentPenguine.nextPenguin().getPenguinAge() < penguin.getAge()) {
			currentPenguine = currentPenguine.nextPenguin();
		}
		newNodePenguin.setNextPenguin(currentPenguine.nextPenguin());
		currentPenguine.setNextPenguin(newNodePenguin);
	}

	@Override
	public Penguin findByIndex(int index) {
		int currentIndex = 0;
		Node currentPenguine = leader;
		while (currentIndex < index && currentPenguine != null) {
			currentPenguine = currentPenguine.nextPenguin();
			currentIndex++;
		}
		if (currentPenguine == null) {
			return null;
		} else {
			return currentPenguine.getPenguin();
		}
	}

	@Override
	public Penguin findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeByIndex(int index) {
		if (this.leader == null) {
			return;
		}
		if (index == 0) {
			this.leader = this.leader.nextPenguin();
		} else {
			Node previous = this.leader;
			int currentIndex = 1;
			for (Node current = previous.nextPenguin(); current != null && currentIndex <= index; current = current
					.nextPenguin(), previous = previous.nextPenguin(), currentIndex++) {
				if (currentIndex == index) {
					previous.setNextPenguin(current.nextPenguin());
				}
			}
		}

	}

	@Override
	public void clear() {
		this.leader = null;
	}

	@Override
	public PenguinsLinkedListInterface copy() {
		PenguinsLinkedListInterface rv = new PenguinLinkedList();

		int i = 0;
		Node current = this.leader;
		while (current != null && i++ < countPenguinsInList()) {
			rv.addPenguin(current.getPenguin());
			current = current.nextPenguin();
		}

		return rv;
	}

	@Override
	public String printPenguinList() {
		Node currentPenguin = leader;
		String allPenguins = "";
		if (leader == null) {
			return "No Penguins In The Zoo";
		} else {
			while (currentPenguin != null) {
				allPenguins += currentPenguin.getPenguin().toString() + "\n";
				currentPenguin = currentPenguin.nextPenguin();
			}
			return allPenguins;
		}
	}

	@Override
	public Long getLeaderID() {
		return leaderID;
	}

}
