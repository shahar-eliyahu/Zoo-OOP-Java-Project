package animals;

import abstract_classes.AnimalAbstract;

public class Megaladon extends AnimalAbstract {

	public final static int MinSpeed = 18; // kmh
	public final static int MinWeight = 45; // Tons
	public final static int MinLength = 12; // meters
	public final static int MaxSpeed = 50; // kmh
	public final static int MaxWeight = 90; // Tons
	public final static int MaxLength = 20; // meters
	public final static int LifeSpan = 100;
	private String name;
	private double weight;
	private final int PowerOfBite = 182200; // in Newtons
	private int speed;
	private int length;

	public Megaladon(int age, int length, String name, double weight, int speed) {
		super(age);
		this.length = length;
		this.name = name;
		this.weight = weight;
		this.speed = speed;
	}


	public String getName() {
		return name;
	}

	public double getWeight() {
		return weight;
	}

	public int getPowerOfBite() {
		return PowerOfBite;
	}

	public int getSpeed() {
		return speed;
	}

	public int getAge() {
		return age;
	}

	public double getLength() {
		return length;
	}

	@Override
	public double feed() {
		this.happiness = 100;
		return 1134;
	}

	@Override
	public void ageOneYear() {
		this.age++;
	}

	@Override
	public String makeNoise() {
		return "RAAAAAAAA!!!!!!!{scary sounds...people and other animals try to escape}";
	}

	@Override
	public String toString() {
		return "Animal Type: MEGALODON [name = " + name + ", weight = " + weight + " Tons , PowerOfBite = "
				+ PowerOfBite + ", speed = " + speed + ", age = " + age + ", length = " + length + " happiness level: "
				+ happiness + " ]";
	}

}
