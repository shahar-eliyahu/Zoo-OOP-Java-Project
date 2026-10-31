package animals;

import abstract_classes.PredatoryAnimalsAbstract;
import interfaces.TrioMethodInterface_ageOneYear_makeNoise_feed;
import zoo.ExceptionZoo;

public class Lion extends PredatoryAnimalsAbstract implements TrioMethodInterface_ageOneYear_makeNoise_feed {
	private final int MAX_FOOD = 25;
	public static double MaxWeight = 500;

	public Lion(String name, boolean gender, double weight, int age) throws ExceptionZoo {
		super(name, gender, weight, age);
		if ((weight > 500 || weight <= 0) && (age < 0 || age > LifeSpan)) {
			throw new ExceptionZoo("Not Valid Weight And Age!",
					"Weight must be: 0 < [Weight] <= 500!\nAge Must Be: 0 <= Age <= " + LifeSpan + "!");
		} else if (weight > 500 || weight <= 0) {
			throw new ExceptionZoo("Not Valid Weight", "Weight must be: 0 < [Weight] <= " + 500 + "!");
		} else if (age < 0 || age > LifeSpan) {
			throw new ExceptionZoo("Not Valid Age", "Age Must Be: 0 <= Age <= " + LifeSpan + "!");
		}
	}

	@Override
	public double feed() {
		double howMuchTheLionEats;
		howMuchTheLionEats = howMuchThePredatorEat;
		if (howMuchTheLionEats >= MAX_FOOD) {
			howMuchTheLionEats = MAX_FOOD;
		}
		return howMuchTheLionEats;
	}

	@Override
	public String makeNoise() {
		return "ROAR";
	}

	@Override
	public String toString() {
		return "Animal type: Lion [" + super.toString() + "]";
	}
}
