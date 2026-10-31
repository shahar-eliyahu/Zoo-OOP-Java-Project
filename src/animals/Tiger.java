package animals;

import abstract_classes.PredatoryAnimalsAbstract;
import interfaces.TrioMethodInterface_ageOneYear_makeNoise_feed;
import zoo.ExceptionZoo;

public class Tiger extends PredatoryAnimalsAbstract implements TrioMethodInterface_ageOneYear_makeNoise_feed {
	public static double MaxWeight = 500;
	
	public Tiger(String name, boolean gender, double weight, int age) throws ExceptionZoo {
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
		return howMuchThePredatorEat;
	}

	@Override
	public String makeNoise() {
		return "roar";
	}

	@Override
	public String toString() {
		return "Animal type: Tiger [" + super.toString() + "]";
	}

}
