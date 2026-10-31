package abstract_classes;

import interfaces.TrioMethodInterface_ageOneYear_makeNoise_feed;

public abstract class AnimalAbstract implements TrioMethodInterface_ageOneYear_makeNoise_feed {
	protected int age;
	protected int happiness;

	public AnimalAbstract(int age) {
		this.age = age;
		this.happiness = 100;
	}

	public int getHappiness() {
		return happiness;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}
}
