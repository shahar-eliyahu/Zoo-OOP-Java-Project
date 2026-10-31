package zoo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import abstract_classes.*;
import animals.*;
import enums.*;
import interfaces.*;

public class ZooAplication {
	public static Scanner scan = new Scanner(System.in);
	public static DecimalFormat format = new DecimalFormat("#0.00");
//	public static Zoo zoo = new Zoo(" Afeka Zoo ", " Mifza Kadesh 38 ");
//	public static Zoo zoo = Zoo.getZooInstance(" Afeka Zoo ", " Mifza Kadesh 38 ");
	public static ZooInterface zoo = Zoo.getZooInstance(" Afeka Zoo ", " Mifza Kadesh 38 ");

	public static void main(String[] args) throws ExceptionZoo, InterruptedException {
		zoo.zooContant();
		int choice = 0;
		System.out.println();
		do {
			programStartAndOpening();
			choice = checkInputStringToInt();
			switch (choice) {
			case 1:
				case1_ShowZooStats();
				System.out.println();
				break;
			case 2:
				case2_ShowAllPeguinsInTheZoo();
				System.out.println();
				break;
			case 3:
				case3_AddNewPeguinToTheZoo();
				System.out.println();
				break;
			case 4:
				case4_ShowAllPredetoryAnimalsInTheZoo();
				System.out.println();
				break;
			case 5:
				case5_AddNewPredetoryAnimalToTheZoo();
				System.out.println();
				break;
			case 6:
				case6_ShowAllFishInTheZoo();
				System.out.println();
				break;
			case 7:
				case7_AddNewFishToTheZoo_RandomOrOneByOne();
				System.out.println();
				break;
			case 8:
				case8_FeedAllAnimalInTheZoo();
				System.out.println();
				break;
			case 9:
				case9_ListenToAllAnimalsInTheZoo();
				System.out.println();
				break;
			case 10:
				case10_ageOneYear();
				System.out.println();
				break;
			case 11:
				case11_ResortThePenguins();
				System.out.println();
				break;
			case 12:
				case12_NewAnimals();
				System.out.println();
				break;
			default:
				if (choice == 0) {
					System.out.println("Good Bye!!");
				} else {
					System.out.println("Enter a Valid Option Number.");
					System.out.println();
				}
				break;
			}

		} while (choice != 0);

	}

	public static void mainManeuPrint() {
		System.out.println("\t1) Zoo Info.");
		System.out.println("\t2) See All Penguins.");
		System.out.println("\t3) Add A New Penguin.");
		System.out.println("\t4) See All Predatory Animals.");
		System.out.println("\t5) Add A New Predatory Animals.");
		System.out.println("\t6) See All The Fish In The Aquarium.");
		System.out.println("\t7) Add Fish To The Aquarium. ");
		System.out.println("\t8) Feed The Animals.");
		System.out.println("\t9) Listen To The Animals Voices.");
		System.out.println("\t10) Move Forward A Year (Next Year).");
		System.out.println("\t11) Change Penguin Sorting Strategy.");
		System.out.println("\t12) The New Animals Section.");
		System.out.println("\tENTER 0 TO EXIT.");
	}

	public static void programStartAndOpening() {
		System.out.println("Welcome To Our Zoo!");
		System.out.println("Please Enter The Number Of Your Choice:");
		mainManeuPrint();
	}

	public static void case1_ShowZooStats() {
		System.out.println("\tZoo Name: " + zoo.getZooName());
		System.out.println("\tZoo Address: " + zoo.getZooAddress() + "\n");
		System.out.println("\tLions Amount In The Zoo: " + zoo.getLionsArr().size());
		System.out.println("\tTigers Amount In The Zoo: " + zoo.getTigersArr().size());
		System.out.println("\tPenguins Amount In The Zoo: " + zoo.getPenguinHord().countPenguinsInList());
		System.out.println("\n\tTotal Fish Amount In The Zoo: " + zoo.getFishArr().size());
		System.out.println("\t\tAquarium Fish Amount: " + zoo.theNumberOfAquariumFish());
		System.out.println("\t\tGold Fish Amount: " + zoo.theNumberOfGoldFish());
		System.out.println("\t\tClown Fish Amount: " + zoo.theNumberOfClownFish());
		System.out.println("\n\tEagles Amount: " + zoo.howMuchEaglesInTheZoo());
		System.out.println("\tCats Amount: " + zoo.howMuchCatsInTheZoo());
		System.out.println("\tMegalodons Amount: " + zoo.getMegaladon().size());
		System.out.println();
	}

	public static void case2_ShowAllPeguinsInTheZoo() throws InterruptedException {
		SpecialPrints.PhotoPrintingPenguin();
		System.out.println(zoo.getPenguinHord().printPenguinList());
	}

	public static void case3_AddNewPeguinToTheZoo() {
		int age = 0;
		double height = 0;
		String name;
		do {
			System.out.println("Please Enter The Penguins Name (a string containing only alphabets): ");
			name = scan.nextLine();
		} while (!isValidAlphabetString(name));
		System.out.println("Please Enter The Penguins Age(In Integers): ");
		age = checkInputStringToInt();
		System.out.println("Please Enter The Penguins Height(0 < height <= 200)[In CM]: ");
		height = checkInputStringToDouble();
		try {
			zoo.addNewPeguin(new Penguin(name, age, height));
			System.out.println("Congrats we have a new penguin!!");
		} catch (ExceptionZoo e) {
			System.out.println(e.getTheIssueTitle());
			System.out.println(e.getTheIssueDescription());
			System.out.println("\nThe penguin with the name < " + name + " > is not validated.");
			System.out.println("Please try again!");
		}

	}

	public static void case4_ShowAllPredetoryAnimalsInTheZoo() throws InterruptedException {
		SpecialPrints.PhotoPrintingLion();
		System.out.println();
		System.out.println(zoo.returnStringOfAllPredetoryAnimals());
	}

	public static int checkInputStringToInt() {
		boolean success = false;
		int num = 0;
		while (!success) {
			try {
				String choiceText = scan.nextLine();
				num = Integer.parseInt(choiceText);
				success = true;
			} catch (Exception inputTextException) {
				System.out.println("You should type a number!");
			}
		}
		return num;
	}

	public static double checkInputStringToDouble() {
		boolean success = false;
		double num = 0;
		while (!success) {
			try {
				String choiceText = scan.nextLine();
				num = Double.parseDouble(choiceText);
				success = true;
			} catch (Exception inputTextException) {
				System.out.println("You should type a number!");
			}
		}
		return num;
	}

	public static void case5_AddNewPredetoryAnimalToTheZoo() throws ExceptionZoo {
		int whatAnimalToAddChoice = 0;
		System.out.println("Do you want to add a Tiger or a Lion [Choose 1 or 2]?");
		System.out.println("\t1) Lion ");
		System.out.println("\t2) Tiger ");
		whatAnimalToAddChoice = checkInputStringToInt();
		while (whatAnimalToAddChoice != 1 && whatAnimalToAddChoice != 2) {
			System.out.println("Enter a valid number!");
			whatAnimalToAddChoice = checkInputStringToInt();
		}
		if (whatAnimalToAddChoice == 1) {
			AddNewLionToTheZoo();
		} else {
			AddNewTigerToTheZoo();
		}

	}

	public static void AddNewLionToTheZoo() throws ExceptionZoo {
		int age = getNewPredatorAge();
		String name = getNewPredatorName();
		Boolean gender = getNewPredatorGender();
		double weight = getNewPredatorWeight(Lion.MaxWeight);
		Lion newlion = new Lion(name, gender, weight, age);
		zoo.addNewLion(newlion);
		System.out.println("The Lion Entered Successfully! ");
	}

	public static void AddNewEgleToTheZoo() throws ExceptionZoo {
		int age = getNewEagleAge();
		String name = getNewPredatorName();
		Boolean gender = getNewPredatorGender();
		double weight = getNewEagleWeight();
		EagleTypesEnum eagleType = getNewEagleEnum();
		int speed = zoo.randomEagleParameters(Eagle.MaxTopSpeed);
		int altitude = zoo.randomEagleParameters(Eagle.MaxTopAltitude);
		zoo.addEagle(new Eagle(name, gender, weight, age, speed, altitude, eagleType));
		System.out.println("The Eagle Entered Successfully! ");
	}

	public static EagleTypesEnum getNewEagleEnum() throws ExceptionZoo {
		EagleTypesEnum eagleType;
		int enumChoice = 0;
		do {
			System.out.println("Choose 1 Of The Next Eagle Types: ");
			System.out.println("\t1)" + EagleTypesEnum.values()[0]);
			System.out.println("\t2)" + EagleTypesEnum.values()[1]);
			System.out.println("\t3)" + EagleTypesEnum.values()[2]);
			enumChoice = checkInputStringToInt();
			if (enumChoice < 0 || enumChoice > 3) {
				System.out.println("You cannot Enter this number please try again.");
			}
		} while (enumChoice < 0 || enumChoice > 3);
		if (enumChoice == 1) {
			eagleType = EagleTypesEnum.values()[0];
			return eagleType;
		} else if (enumChoice == 2) {
			eagleType = EagleTypesEnum.values()[1];
			return eagleType;
		} else {
			eagleType = EagleTypesEnum.values()[2];
			return eagleType;
		}
	}

	public static double getNewEagleWeight() throws ExceptionZoo {
		System.out.println("Enter Eagle Weight in kg(0 < weight <= " + Eagle.MaxEagleWeight + "):");
		double weight = checkInputStringToDouble();
		while (weight <= 0 || weight > Eagle.MaxEagleWeight) {
			System.out.println("Incorrect Weight Please Enter Again.");
			weight = checkInputStringToDouble();
		}
		return weight;
	}

	public static int getNewEagleAge() throws ExceptionZoo {
		double getAge;
		int age;
		System.out.println("Enter Eagle Age[In Integers 0 - " + Eagle.LifeSpan + "]:");
		do {
			getAge = checkInputStringToDouble();
			age = (int) getAge;
			if (age < 0 || age > Eagle.LifeSpan) {
				System.out.println("You cannot Enter this age please try again.");
			}
		} while (age < 0 || age > Eagle.LifeSpan);
		return age;

	}

	public static String getNewPredatorName() throws ExceptionZoo {
		String name;
		do {
			System.out.println("Please Enter The Predator Name (a string containing only alphabets): ");
			name = scan.nextLine();
		} while (!isValidAlphabetString(name));
		return name;

	}

	public static int getNewPredatorAge() throws ExceptionZoo {
		double getAge;
		int age;
		System.out.println("Enter Predator Age[In Integers 0 - " + PredatoryAnimalsAbstract.LifeSpan + "]:");
		do {
			getAge = checkInputStringToDouble();
			age = (int) getAge;
			if (age < 0 || age > PredatoryAnimalsAbstract.LifeSpan) {
				System.out.println("You cannot Enter this age please try again.");
			}
		} while (age < 0 || age > PredatoryAnimalsAbstract.LifeSpan);
		return age;

	}

	public static Boolean getNewPredatorGender() throws ExceptionZoo {
		Boolean gender;
		System.out.println("Choose The Number Of Your Predator Gender: ");
		System.out.println("\t1) MALE");
		System.out.println("\t2) FEMALE");
		int numOfGender = checkInputStringToInt();
		while (numOfGender != 1 && numOfGender != 2) {
			System.out.println("Please Enter 1 or 2!");
			System.out.println("\t1) MALE");
			System.out.println("\t2) FEMALE");
			numOfGender = checkInputStringToInt();
		}
		if (numOfGender == 1) {
			gender = true;
		} else {
			gender = false;
		}
		return gender;

	}

	public static double getNewPredatorWeight(double maxWeight) throws ExceptionZoo {
		System.out.println("Enter Predator Weight in kg(0 < weight <= " + maxWeight + "):");
		double weight = checkInputStringToDouble();
		while (weight <= 0 || weight > maxWeight) {
			System.out.println("Incorrect Weight Please Enter Again.");
			weight = checkInputStringToDouble();
		}
		return weight;
	}

	public static void AddNewTigerToTheZoo() throws ExceptionZoo {
		int age = getNewPredatorAge();
		String name = getNewPredatorName();
		Boolean gender = getNewPredatorGender();
		double weight = getNewPredatorWeight(Tiger.MaxWeight);
		Tiger newTiger = new Tiger(name, gender, weight, age);
		zoo.addNewTiger(newTiger);
		System.out.println("The Tiger Entered Successfully! ");
	}

	public static void AddNewCatToTheZoo() throws ExceptionZoo {
		int age = getNewPredatorAge();
		String name = getNewPredatorName();
		Boolean gender = getNewPredatorGender();
		double weight = getNewPredatorWeight(Cat.MaxWeight);
		Cat newCat = new Cat(name, gender, weight, age);
		zoo.addNewCat(newCat);
		System.out.println("The Cat Entered Successfully! ");
	}

	public static void case6_ShowAllFishInTheZoo() throws InterruptedException {
		SpecialPrints.PhotoPrintingFish();
		System.out.println();
		System.out.println(zoo.returnStringOfArrFish());
		System.out.println(zoo.returnStringOfAllColorFish());
		System.out.println();
	}

	public static void case7_AddNewFishToTheZoo_RandomOrOneByOne() throws ExceptionZoo {
		int choiceHowToAddAFish;
		addFishManue();
		choiceHowToAddAFish = checkInputStringToInt();
		while (choiceHowToAddAFish != 1 && choiceHowToAddAFish != 2) {
			System.out.println("Enter a Valid Option Number.");
			System.out.println();
			addFishManue();
			choiceHowToAddAFish = checkInputStringToInt();
		}
		switch (choiceHowToAddAFish) {
		case 1:
			manualyAddFishManue();
			int fishType;
			fishType = checkInputStringToInt();
			while (fishType != 1 && fishType != 2 && fishType != 3) {
				System.out.println("Enter a Valid Option Number.");
				System.out.println();
				manualyAddFishManue();
				fishType = checkInputStringToInt();
			}
			switch (fishType) {
			case 1:
				enterManualyAquariumFish();
				break;
			case 2:
				enterManualyGoldFish();
				break;
			case 3:
				enterManualyClownFish();
				break;
			default:
				break;
			}
			break;
		case 2:
			randomlyEnteredFishDatta();
			break;
		default:
			break;
		}

	}

	public static void addFishManue() {
		System.out.println("Choose The Number Of How To Add A Fish:");
		System.out.println("\t1) Manually Enter The Data.");
		System.out.println("\t2) Randomly Enter The Data By Entering The number Of Fish You Like To Add.");
	}

	public static void manualyAddFishManue() {
		System.out.println("What fish type do you want to add[Choose 1, 2 or 3]:");
		System.out.println("\t1)Aquarium Fish");
		System.out.println("\t2)Gold Fish");
		System.out.println("\t3)Clown Fish");

	}

	public static void enterManualyGoldFish() throws ExceptionZoo {
		int age;
		System.out.println("Enter fish length in CM [number between: 0.00 < length <= 50.00]:");
		double lengthfish = checkInputStringToDouble();
		while (lengthfish <= 0 || lengthfish > 50) {
			System.out.println("Invalid Number Please Enter Again.");
			lengthfish = checkInputStringToDouble();
		}
		System.out.println("Enter fish Age[In Integers 0 - " + GoldFish.LifeSpan + "]:");
		age = checkInputStringToInt();
		while (age < 0 || age > GoldFish.LifeSpan) {
			System.out.println("Invalid Number Please Enter Again.");
			age = checkInputStringToInt();
		}
		System.out.println("Enter which color the fish is[Choose one from the below numbers]:");
		System.out.println("\t1) " + FishColorEnum.values()[0]);
		System.out.println("\t2) " + FishColorEnum.values()[3]);
		System.out.println("\t3) " + FishColorEnum.values()[5]);
		System.out.println("\t4) " + FishColorEnum.values()[7]);
		int colorChoice;
		FishColorEnum[] coloerArr = new FishColorEnum[1];
		colorChoice = checkInputStringToInt();
		while (colorChoice != 1 && colorChoice != 2 && colorChoice != 3 && colorChoice != 4) {
			System.out.println("Enter a Valid Option Number.");
			System.out.println();
			System.out.println("Enter which color the fish is[Choose one from the below numbers]:");
			System.out.println("\t1) " + FishColorEnum.values()[0]);
			System.out.println("\t2) " + FishColorEnum.values()[3]);
			System.out.println("\t3) " + FishColorEnum.values()[5]);
			System.out.println("\t4) " + FishColorEnum.values()[7]);
			colorChoice = checkInputStringToInt();
		}
		switch (colorChoice) {
		case 1:
			coloerArr[0] = FishColorEnum.values()[0];
			break;
		case 2:
			coloerArr[0] = FishColorEnum.values()[3];
			break;
		case 3:
			coloerArr[0] = FishColorEnum.values()[5];
			break;
		case 4:
			coloerArr[0] = FishColorEnum.values()[7];
			break;
		default:
			break;
		}
		zoo.AddOneNewFishToArr(new GoldFish(age, lengthfish, coloerArr));
		System.out.println("The Fish Entered Successfully! ");
	}

	public static void enterManualyClownFish() throws ExceptionZoo {
		int age;
		System.out.println("Enter fish length in CM [number between: 0.00 < length <= 10.00]:");
		double lengthfish = checkInputStringToDouble();
		while (lengthfish <= 0 || lengthfish > 10) {
			System.out.println("Invalid Number Please Enter Again.");
			lengthfish = checkInputStringToDouble();
		}
		System.out.println("Enter fish Age[In Integers 0 - " + ClownFish.LifeSpan + "]:");
		age = checkInputStringToInt();
		while (age < 0 || age > ClownFish.LifeSpan) {
			System.out.println("Invalid Number Please Enter Again.");
			age = checkInputStringToInt();
		}
		zoo.AddOneNewFishToArr(new ClownFish(age, lengthfish));
		System.out.println("The Fish Entered Successfully! ");

	}

	public static void enterManualyAquariumFish() throws ExceptionZoo {
		int age;
		System.out.println("Enter fish length in CM [number between: 0.00 < length <= 500.00]:");
		double lengthfish = checkInputStringToDouble();
		while (lengthfish <= 0 || lengthfish > 500) {
			System.out.println("Invalid Number Please Enter Again.");
			lengthfish = checkInputStringToDouble();
		}
		System.out.println("Enter fish Age[In Integers 0 - " + AquariumFish.LifeSpan + "]:");
		age = checkInputStringToInt();
		while (age < 0 || age > AquariumFish.LifeSpan) {
			System.out.println("Invalid Number Please Enter Again.");
			age = checkInputStringToInt();
		}
		System.out.println("Enter How Much Colors Will The Fish Have[Max Colors == 10]: ");
		int numberOfColorsFishWillHave = checkInputStringToInt();
		while (numberOfColorsFishWillHave <= 0 || numberOfColorsFishWillHave > 10) {
			System.out.println("Enter a Number Between 1 - 10 !!");
			numberOfColorsFishWillHave = checkInputStringToInt();
		}
		FishColorEnum[] coloerArr = new FishColorEnum[numberOfColorsFishWillHave];
		ArrayList<FishColorEnum> availibleColoers = new ArrayList<FishColorEnum>();
		System.out.println("Enter The Number Of The Colors You Want The Fish To Have[only 1 time from every color]:");
		for (int i = 0; i < FishColorEnum.values().length; i++) {
			availibleColoers.add(i, FishColorEnum.values()[i]);
		}
		for (int k = 0; k < numberOfColorsFishWillHave; k++) {
			for (int i = 0; i < availibleColoers.size(); i++) {
				System.out.println((i + 1) + ") " + availibleColoers.get(i));
			}
			System.out.println("Please enter color number:");
			int currentColorNumer = checkInputStringToInt();

			while (currentColorNumer <= 0 || currentColorNumer > availibleColoers.size()) {
				System.out.println("Enter a valid number!!");
				for (int j = 0; j < FishColorEnum.values().length; j++) {
					System.out.println((j + 1) + ") " + FishColorEnum.values()[j]);
				}
				currentColorNumer = checkInputStringToInt();
			}
			coloerArr[k] = availibleColoers.get(currentColorNumer - 1);
			availibleColoers.remove(currentColorNumer - 1);
		}
		System.out.println("Enter Which Body Signature The Fish Have");
		for (int i = 0; i < BodySignatureEnum.values().length; i++) {
			System.out.println(i + 1 + ") " + BodySignatureEnum.values()[i]);
		}
		int BodySignatureEnumNumber = checkInputStringToInt();
		while (BodySignatureEnumNumber < 1 || BodySignatureEnumNumber > 4) {
			System.out.println("Enter a Valid Number!!");
			for (int i = 0; i < BodySignatureEnum.values().length; i++) {
				System.out.println(i + 1 + ") " + BodySignatureEnum.values()[i]);
			}
			BodySignatureEnumNumber = checkInputStringToInt();
		}
		BodySignatureEnum BodySignature = BodySignatureEnum.values()[BodySignatureEnumNumber - 1];
		zoo.AddOneNewFishToArr(new AquariumFish(age, lengthfish, coloerArr, BodySignature));
		System.out.println("The Fish Entered Successfully! ");
	}

	public static void randomlyEnteredFishDatta() throws ExceptionZoo {

		System.out.println("Enter The Number Of Fish You Want To Enter: ");
		int numberOfFishToAddRandomly = checkInputStringToInt();
		while (numberOfFishToAddRandomly < 1 || numberOfFishToAddRandomly > 300) {
			System.out.println("Enter Number Between 1 - 300 ");
			numberOfFishToAddRandomly = checkInputStringToInt();
		}
		zoo.addNumbrOfFish(numberOfFishToAddRandomly);
		System.out.println("The Fish Entered Successfully! ");
	}

	public static void case8_FeedAllAnimalInTheZoo() {
		System.out.println("The Total Amount of Fish That The Penguins Ate is: " + zoo.totalPenguinsFood());
		System.out.println(
				"The Total Amount of Meat In Kilo Grams That The Lions Ate is: " + zoo.howMuchAllTheLionsEat());
		System.out.println(
				"The Total Amount of Meat In Kilo Grams That The Tigers Ate is: " + zoo.howMuchAllTheTigersEat());
		System.out.println(
				"The Total Amount of Food Packages That The Fish Ate is: " + format.format(zoo.howMuchAllTheFishEat()));
		System.out.println("The Total Amount of Meat In Kilo Grams That The Eagles Ate is: "
				+ format.format(zoo.totalEagleFood()));
		System.out.println("All The Cats Together Ate: " + zoo.totalCatsFood() + " Cans/Fish/Steak ");
		System.out.println("All The Megalodons Together Ate: " + zoo.howMuchTheMegaladonEats()
				+ "Kg of Meat Of Fish/Giant Animal/Sharks And More. ");

	}

	public static void case9_ListenToAllAnimalsInTheZoo() {
		System.out.println(zoo.makeNoise());

	}

	private static boolean isValidAlphabetString(String input) {
		// Check if the input contains only alphabets
		return input.matches("^[a-zA-Z]+$");
	}

	public static void case10_ageOneYear() {
		System.out.println(zoo.ageOneYear());
	}

	public static void case11_ResortThePenguins() {
		int choicetest;
		int currentStrategy = zoo.getPenguinHord().getSortFormPenguin();
		System.out.println("The Current Way Of Sorting Is: " + (currentStrategy == 1 ? "Sort By Name [A - Z , a - z]"
				: currentStrategy == 2 ? "Sort By Height [the leader(the tallest) first]"
						: "Sort By Age [youngest first]"));
		System.out.println("Choose One Of The Next Options:");
		System.out.println("\t1) Sort By Name [A - Z , a - z]");
		System.out.println("\t2) Sort By Height [the leader(the tallest) first]");
		System.out.println("\t3) Sort By Age [youngest first]");
		choicetest = checkInputStringToInt();
		while (choicetest < 1 || choicetest > 3) {
			System.out.println("Enter a Valid Option Number (1 - 3).");
			choicetest = checkInputStringToInt();
		}
		zoo.penguinSortWay(choicetest);
	}

	public static int getNewMegaladonAge() throws ExceptionZoo {
//		double getAge;
		int age;
		System.out.println("Enter Megalodon Age[In Integers 0 - " + Megaladon.LifeSpan + "]:");
		do {
			age = checkInputStringToInt();
//			age = (int) getAge;
			if (age < 0 || age > Megaladon.LifeSpan) {
				System.out.println("You cannot Enter this age please try again.");
			}
		} while (age < 0 || age > Megaladon.LifeSpan);
		return age;

	}

	public static double getNewMegaladonWeight() throws ExceptionZoo {
		System.out.println(
				"Enter Megalodon Weight in Tons(" + Megaladon.MinWeight + " < weight <= " + Megaladon.MaxWeight + "):");
		double weight = checkInputStringToDouble();
		while (weight <= Megaladon.MinWeight || weight > Megaladon.MaxWeight) {
			System.out.println("Incorrect Weight Please Enter Again.");
			weight = checkInputStringToDouble();
		}
		return weight;
	}

	public static int getNewMegaladonSpeed() throws ExceptionZoo {
		System.out.println(
				"Enter Megalodon Speed in KMPH(" + Megaladon.MinSpeed + " < Speed <= " + Megaladon.MaxSpeed + "):");
		int speed = checkInputStringToInt();
		while (speed <= Megaladon.MinSpeed || speed > Megaladon.MaxSpeed) {
			System.out.println("Incorrect Speed Please Enter Again.");
			speed = checkInputStringToInt();
		}
		return speed;
	}

	public static int getNewMegaladonLength() throws ExceptionZoo {
		System.out.println(
				"Enter Megalodon Length in meters(" + Megaladon.MinLength + " < Length <= " + Megaladon.MaxLength + "):");
		int length = checkInputStringToInt();
		while (length <= Megaladon.MinLength || length > Megaladon.MaxLength) {
			System.out.println("Incorrect Length Please Enter Again.");
			length = checkInputStringToInt();
		}
		return length;
	}

	public static void AddNewMegaladonToTheZoo() throws ExceptionZoo {
		int age = getNewMegaladonAge();
		int length = getNewMegaladonLength();
		int speed = getNewMegaladonSpeed();
		String name = getNewPredatorName();
		double weight = getNewMegaladonWeight();
		Megaladon Megaladon = new Megaladon(age, length, name, weight, speed);
		zoo.addMegaladon(Megaladon);
		System.out.println("The Megalodon Entered Successfully! ");
	}

	public static void case12_NewAnimals() throws ExceptionZoo, InterruptedException {
		int newAnimalChoice = -1;
		do {
			System.out.println();
			System.out.println("Welcome To The New Animal Section :)\nChoose One From The Options");
			System.out.println("\t1) See All Eagles");
			System.out.println("\t2) Add An Eagle");
			System.out.println("\t3) See All Cats");
			System.out.println("\t4) Add A Cat");
			System.out.println("\t5) See All Megalodons");
			System.out.println("\t6) Add A Megalodon");
			System.out.println("Enter 0 to get back to the main menu.");

			newAnimalChoice = checkInputStringToInt();
			switch (newAnimalChoice) {
			case 1:
				SpecialPrints.PhotoPrintingEagle();
				System.out.println();
				System.out.println(zoo.StringOfAllEaglesInTheZoo());
				System.out.println();
				break;
			case 2:
				AddNewEgleToTheZoo();
				System.out.println();
				break;
			case 3:
				SpecialPrints.PhotoPrintingCat();
				System.out.println();
				System.out.println(zoo.returnStringOfAllTheCats());
				System.out.println();
				break;
			case 4:
				AddNewCatToTheZoo();
				System.out.println();
				break;
			case 5:
				SpecialPrints.PhotoPrintingMegaladon();
				System.out.println();
				System.out.println(zoo.stringOfMegaladon());
				System.out.println();
				break;
			case 6:
				AddNewMegaladonToTheZoo();
				System.out.println();
				break;
			default:
				if (newAnimalChoice == 0) {
					System.out.println("MAIN MENU");
				} else {
					System.out.println("Enter a Valid Option Number.");
					System.out.println();
				}
				break;
			}

		} while (newAnimalChoice != 0);

	}

}
