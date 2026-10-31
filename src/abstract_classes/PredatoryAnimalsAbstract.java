package abstract_classes;

import zoo.ExceptionZoo;

public abstract class PredatoryAnimalsAbstract extends AnimalAbstract {
	protected double weight;
	protected String name;
	protected boolean gender; // TRUE = MALE || FALSE = FEMALE
	protected final double MALE_FOOD_MULTIPLIER = 0.02;
	protected final double FEMALE_FOOD_MULTIPLIER = 0.03;
	protected int howMuchThePredatorEat;
	public static final int LifeSpan = 15;

	public PredatoryAnimalsAbstract(String name, boolean gender, double weight, int age) throws ExceptionZoo {
		super(age);
		this.gender = gender;
		this.name = name;
		this.weight = weight;
		this.howMuchThePredatorEat = howMuchThePredatorEat();
	}

	@Override
	public void ageOneYear() {
		this.age++;
		this.howMuchThePredatorEat = howMuchThePredatorEat();
	}

	private int howMuchThePredatorEat() {
		if (this.gender) {
			this.howMuchThePredatorEat = (int) (this.MALE_FOOD_MULTIPLIER * this.age * this.weight);

		} else {
			this.howMuchThePredatorEat = (int) (this.FEMALE_FOOD_MULTIPLIER * this.age * this.weight);
		}
		return this.howMuchThePredatorEat;
	}

	@Override
	public String toString() {
		return "age = " + age + ", weight = " + weight + ", name = " + name + ", gender = "
				+ (gender ? " Male" : " Female") + ", happiness level = " + happiness;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}

	public String getName() {
		return name;
	}

	public boolean isGender() {
		return gender;
	}

}
