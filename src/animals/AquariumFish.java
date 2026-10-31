package animals;

import java.util.Arrays;

import abstract_classes.FishAbstract;
import enums.BodySignatureEnum;
import enums.FishColorEnum;
import interfaces.TrioMethodInterface_ageOneYear_makeNoise_feed;
import zoo.ExceptionZoo;

public class AquariumFish extends FishAbstract implements TrioMethodInterface_ageOneYear_makeNoise_feed {
	private final int AGE_DIVISION_TO_EAT = 3;
	private final int STANDARD_FOOD_AMOUNT = 3;
	public static final int LifeSpan = 25;
	public static final int RANGE_LENGTH_RANDOM_FISH = 500; // CENTEMETER

	public AquariumFish(int age, double length, FishColorEnum[] color, BodySignatureEnum signature)
			throws ExceptionZoo {
		super(age, length);
		this.color = color;
		this.signature = signature;
		if ((length > RANGE_LENGTH_RANDOM_FISH || length <= 0) && (age < 0 || age > LifeSpan)) {
			throw new ExceptionZoo("Not Valid Length And Age!",
					"Length must be: 0 < [Length] <= " + RANGE_LENGTH_RANDOM_FISH + "!\nAge Must Be Between 0 to " + LifeSpan + "!");
		} else if (length > RANGE_LENGTH_RANDOM_FISH || length <= 0) {
			throw new ExceptionZoo("Not Valid Length",
					"Length must be: 0 < [Length] <= " + RANGE_LENGTH_RANDOM_FISH + "!");
		} else if (age < 0 || age > LifeSpan) {
			throw new ExceptionZoo("Not Valid Age", "Age Must Be Between 0 to " + LifeSpan + "!");
		}
	}

	@Override
	public String toString() {

		return "[AquariumFish " + ", color = " + Arrays.toString(color) + ", signature = " + signature
				+ super.toString() + "]";
	}

	@Override
	public double feed() {
		double foodAmount;
		if (this.age < AGE_DIVISION_TO_EAT) {
			foodAmount = STANDARD_FOOD_AMOUNT;
			return foodAmount;
		} else {
			foodAmount = this.length + STANDARD_FOOD_AMOUNT;
			return foodAmount;
		}
	}

}
