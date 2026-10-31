package animals;

import abstract_classes.AnimalAbstract;
import zoo.ExceptionZoo;

public class Penguin extends AnimalAbstract{
	private double height;
	private String name;
	private Long ID;
	private static long idEnum = 0L;
	public static final int LifeSpan = 6;

	public Penguin(String name, int age, double height) throws ExceptionZoo {
		super(age);
		this.name = name;
		this.height = height;
		if ((height > 200 || height <= 0) && (age < 0 || age > LifeSpan)) {
			throw new ExceptionZoo("Not Valid Height And Age!",
					"Height must be: 0 < [height] <= 200!\nAge Must Be Between 0 to " + LifeSpan + "!");
		} else if (height > 200 || height <= 0) {
			throw new ExceptionZoo("Not Valid Height", "Height must be: 0 < [height] <= 200!");
		} else if (age < 0 || age > LifeSpan) {
			throw new ExceptionZoo("Not Valid Age", "Age Must Be Between 0 to " + LifeSpan + "!");
		}
		this.ID = ++idEnum;

	}

	public Long getID() {
		return ID;
	}

	public void ageOneYear() {
		this.age++;
	}

	public double feed() {
		double howMuchThePenguinEats = 1;
		return howMuchThePenguinEats;
	}

	public String makeNoise() {
		return "squack";
	}

	@Override
	public String toString() {
		return "Animal type: Penguin [age=" + age + ", height=" + height + ", name=" + name + ", happiness level= "
				+ happiness + "]";
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}


}
