package sorting_strategics;

import animals.Eagle;
import interfaces.EagleLinkedListInterface;

public class EagleLinkedListImplamentation implements EagleLinkedListInterface {
	private class Node {
		private Node next;
		private Eagle eagle;

		public Node(Eagle eagle) {
			this.eagle = eagle;
		}

		public void setNextEagle(Node next) {
			this.next = next;
		}

		public Node nextEagle() {
			return next;
		}

		public Eagle getEagle() {
			return eagle;
		}

		public Long getEagleID() {
			return eagle.getEagleID();
		}
	}

	private Node leader;
	private Long leaderID;

	public EagleLinkedListImplamentation() {
		this.leader = null;
	}

	@Override
	public int countEaglesInList() {
		int count = 0;
		if (leader == null) {
			return 0;
		}
		Node next = this.leader;

		while (next != null) {
			count++;
			next = next.nextEagle();
		}
		return count;
	}

	@Override
	public void addEagle(Eagle eagle) {
		Node newNodeEagle = new Node(eagle);
		if (leader == null || 0 < comparationTest(eagle, leader.getEagle())) {
			newNodeEagle.setNextEagle(leader);
			leader = newNodeEagle;
			leaderID = leader.getEagleID();
			return;
		}
		Node currentEagle = leader;
		while (currentEagle.nextEagle() != null && comparationTest(currentEagle.nextEagle().getEagle(), eagle) > 0) {
			currentEagle = currentEagle.nextEagle();
		}
		newNodeEagle.setNextEagle(currentEagle.nextEagle());
		currentEagle.setNextEagle(newNodeEagle);

	}

	@Override
	public int comparationTest(Eagle eagle1, Eagle eagle2) {
		int rv;
		rv = compareSpd.compare(eagle1, eagle2);
		if (rv == 0) {
			rv = compareAge.compare(eagle1, eagle2);
			if (rv == 0) {
				rv = compareAlt.compare(eagle1, eagle2);
				if (rv == 0) {
					if (eagle1.getEagleID() > eagle2.getEagleID()) {
						rv = 1;
					} else {
						rv = 0;
					}
				}
			}
		}

		return rv;
	}

	@Override
	public Eagle findByIndex(int index) {
		int currentIndex = 0;
		Node currentEagle = leader;
		while (currentIndex < index && currentEagle != null) {
			currentEagle = currentEagle.nextEagle();
			currentIndex++;
		}
		if (currentEagle == null) {
			return null;
		} else {
			return currentEagle.getEagle();
		}
	}

	@Override
	public Eagle findById(Long id) {
		for (Node current = this.leader; current != null; current = current.nextEagle()) {
			if (current.getEagle().getEagleID().equals(id)) {
				return current.getEagle();
			}
		}
		return null;
	}

	@Override
	public void removeById(Long id) {
		if (this.leader == null) {
			return;
		}
		if (this.leader.getEagle().getEagleID().equals(id)) {
			this.leader = this.leader.nextEagle();
		} else {
			Node previous = this.leader;
			for (Node current = previous.nextEagle(); current != null; current = current
					.nextEagle(), previous = previous.nextEagle()) {
				if (current.getEagle().getEagleID().equals(id)) {
					previous.setNextEagle(current.nextEagle());
				}
			}
		}

	}

	@Override
	public void removeByIndex(int index) {
		if (this.leader == null) {
			return;
		}
		if (index == 0) {
			this.leader = this.leader.nextEagle();
		} else {
			Node previous = this.leader;
			int currentIndex = 1;
			for (Node current = previous.nextEagle(); current != null && currentIndex <= index; current = current
					.nextEagle(), previous = previous.nextEagle(), currentIndex++) {
				if (currentIndex == index) {
					previous.setNextEagle(current.nextEagle());
				}
			}
		}
	}

	@Override
	public void clear() {
		this.leader = null;
	}

	@Override
	public EagleLinkedListInterface copy() {
		EagleLinkedListInterface rv = new EagleLinkedListImplamentation();

		int i = 0;
		Node current = this.leader;
		while (current != null && i++ < countEaglesInList()) {
			rv.addEagle(current.getEagle());
			current = current.nextEagle();
		}
		return rv;
	}

	@Override
	public String printEagleList() {
		Node currentEagle = leader;
		String allEagles = "";
		if (leader == null) {
			return "No Eagles In The Zoo";
		} else {
			while (currentEagle != null) {
				allEagles += currentEagle.getEagle().toString() + "\n";
				currentEagle = currentEagle.nextEagle();
			}
			return allEagles;
		}
	}

	@Override
	public Long getLeaderID() {
		return leaderID;
	}

}
