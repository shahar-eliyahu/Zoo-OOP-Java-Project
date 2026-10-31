package zoo;

import java.util.Random;
import interfaces.*;
import sorting_strategics.*;
import abstract_classes.*;
import animals.*;
import enums.*;
import java.util.ArrayList;

public class Zoo implements ZooInterface{

	private String zooName;
	private String zooAddress;
	private ArrayList<Lion> lionArr = new ArrayList<Lion>();
	private ArrayList<Tiger> tigerArr = new ArrayList<Tiger>();
	private ArrayList<FishAbstract> fishArr = new ArrayList<FishAbstract>();
	private EagleLinkedListInterface baldEaglePack;
	private EagleLinkedListInterface goldenEaglePack;
	private EagleLinkedListInterface blackEaglePack;
	private EagleLinkedListInterface tempResortEaglePack;
	private PenguinsLinkedListInterface penguinHord;
	private PenguinsLinkedListInterface tempPenguinList;
	private ArrayList<Megaladon> megaladon = new ArrayList<Megaladon>();
	private CatBinaryTreeInterface cats;
	public static Random random = new Random();
	private int sortFormPenguin;

	private static Zoo zooInstance;

	private Zoo(String zooName, String zooAddress) {
		this.zooName = zooName;
		this.zooAddress = zooAddress;
		this.sortFormPenguin = 2;
	}

	public static Zoo getZooInstance(String zooName, String zooAddress) {
		if (zooInstance == null) {
			zooInstance = new Zoo(zooName, zooAddress);
		}
		return zooInstance;
	}

//		initialize the hard coded information
	public void zooContant() throws ExceptionZoo {
		this.penguinHord = new PenguinLinkedList();
		this.baldEaglePack = new EagleLinkedListImplamentation();
		this.goldenEaglePack = new EagleLinkedListImplamentation();
		this.blackEaglePack = new EagleLinkedListImplamentation();
		this.cats = new CatBinaryTree();

		penguinHord.addPenguin(new Penguin("Tom", 6, 200));
		penguinHord.addPenguin(new Penguin("Lak", 2, 120));
		penguinHord.addPenguin(new Penguin("Pak", 1, 10));
		penguinHord.addPenguin(new Penguin("AAA", 6, 100));
		penguinHord.addPenguin(new Penguin("AAA", 6, 110));
		penguinHord.addPenguin(new Penguin("BBB", 2, 123));
		penguinHord.addPenguin(new Penguin("CCC", 1, 12));
		penguinHord.addPenguin(new Penguin("Aaa", 6, 75));
		penguinHord.addPenguin(new Penguin("aaa", 2, 129));

		this.lionArr.add(new Lion("Lion", true, 175, 5));
		this.lionArr.add(new Lion("Subato", true, 200, 10));
		this.lionArr.add(new Lion("Nala", false, 175, 5));
		this.lionArr.add(new Lion("Mika", false, 120, 2));

		this.tigerArr.add(new Tiger("Tigro", true, 175, 5));
		this.tigerArr.add(new Tiger("Nigro", true, 200, 10));
		this.tigerArr.add(new Tiger("Pigro", false, 175, 5));
		this.tigerArr.add(new Tiger("Ligro", false, 120, 2));

		addNumbrOfFish(10);

		addEagle(new Eagle("Chunky Chuk", false, 10.0, 2, 200, 1500, EagleTypesEnum.BALD));
		addEagle(new Eagle("Chungy buk", false, 1.5, 3, 300, 2500, EagleTypesEnum.BLACK));
		addEagle(new Eagle("Punky Puk", true, 9.0, 9, 250, 1530, EagleTypesEnum.BALD));
		addEagle(new Eagle("Chun Chu", true, 9.6, 20, 205, 150, EagleTypesEnum.GOLDEN));

		addEagle(new Eagle("hunky huk", false, 8.0, 25, 350, 150, EagleTypesEnum.GOLDEN));
		addEagle(new Eagle("unky Chuk", true, 7.9, 30, 350, 3000, EagleTypesEnum.BALD));
		addEagle(new Eagle("Chuny Chuk", false, 6.6, 7, 350, 3000, EagleTypesEnum.BLACK));
		addEagle(new Eagle("ky uk", true, 2.0, 30, 350, 3000, EagleTypesEnum.GOLDEN));

		addEagle(new Eagle("Cky Ch", true, 2.2, 18, 235, 1500, EagleTypesEnum.BLACK));
		addEagle(new Eagle("Cunky Ck", true, 1.2, 19, 212, 1200, EagleTypesEnum.BALD));
		addEagle(new Eagle("Chky Chk", false, 1.9, 12, 100, 2500, EagleTypesEnum.GOLDEN));
		addEagle(new Eagle("Chy Chk", false, 1.7, 21, 50, 159, EagleTypesEnum.BLACK));

		cats.add(new Cat("AAAA", false, 7, 15));
		cats.add(new Cat("BBB", false, 5, 7));
		cats.add(new Cat("CCC", true, 11, 7));
		cats.add(new Cat("DDD", true, 10, 9));
		cats.add(new Cat("EEE", true, 11, 10));
		cats.add(new Cat("FFF", true, 7, 5));
		cats.add(new Cat("HHH", false, 7, 4));
		cats.add(new Cat("GGG", false, 5, 11));
		cats.add(new Cat("III", true, 11, 12));
		cats.add(new Cat("JJJ", true, 10, 3));
		cats.add(new Cat("KKK", true, 11, 2));
		cats.add(new Cat("LLL", true, 7, 13));
		cats.add(new Cat("MMM", false, 5, 15));
		cats.add(new Cat("NNN", true, 11, 15));
		cats.add(new Cat("ZZZ", true, 10, 15));

		megaladon.add(new Megaladon(59, 15, "BigDeath", 60, 45));

	}

	public void addMegaladon(Megaladon megaladon) {
		this.megaladon.add(megaladon);
	}

	public String stringOfMegaladon() {
		String stringToPrint = "";
		for (int i = 0; i < this.megaladon.size(); i++) {
			stringToPrint += this.megaladon.get(i).toString() + "\n";
		}
		return stringToPrint;
	}

	public int howMuchTheMegaladonEats() {
		int foodAmount = 0;
		for (int i = 0; i < this.megaladon.size(); i++) {
			foodAmount += this.megaladon.get(i).feed();
		}
		return foodAmount;
	}

	public void addNewCat(Cat cat) {
		cats.add(cat);
	}

	public int howMuchCatsInTheZoo() {
		return cats.countCats();
	}

	public String returnStringOfAllTheCats() {
		String catsInZoo = "The Ordered View Of The Cats:\n" + cats.printCatOrdered()
				+ "\nThe Cats Family Tree In The Zoo:\n" + cats.printTree() + "\n";
		return catsInZoo;
	}

	public int randomEagleParameters(int maxParameterValue) {
		int randomParameter = random.nextInt(maxParameterValue);
		if (randomParameter == 0) {
			randomParameter++;
		}
		return randomParameter;
	}

//	gets the eagle info and send it to the right eagle pack
	public void addEagle(Eagle eagle) {
		int locationInEagleTypeEnum = 0;
		boolean foundEalgeType = false;
		while (!foundEalgeType) {
			if (eagle.getEagleType().compareTo(EagleTypesEnum.values()[locationInEagleTypeEnum]) == 0) {
				foundEalgeType = true;
			} else {
				locationInEagleTypeEnum++;
			}
		}
		switch (locationInEagleTypeEnum) {
		case 0:
			baldEaglePack.addEagle(eagle);
			break;
		case 1:
			goldenEaglePack.addEagle(eagle);
			break;
		case 2:
			blackEaglePack.addEagle(eagle);
			break;
		}

	}

//	returns the count of all the eagles from all types in the zoo
	public int howMuchEaglesInTheZoo() {
		return baldEaglePack.countEaglesInList() + goldenEaglePack.countEaglesInList()
				+ blackEaglePack.countEaglesInList();
	}

//	returns a string that contains all the toString from all the eagles
	public String StringOfAllEaglesInTheZoo() {
		String allEaglse = "";
		allEaglse += baldEaglePack.printEagleList() + "\n" + goldenEaglePack.printEagleList() + "\n"
				+ blackEaglePack.printEagleList() + "\n";
		return allEaglse;
	}

//	resorting all the eagle in the specific array of eagles
	private void eagleReSortAfterRemovingOne(EagleLinkedListInterface EaglePack) {
		this.tempResortEaglePack = new EagleLinkedListImplamentation();
		tempResortEaglePack = EaglePack.copy();
		EaglePack.clear();
		for (int i = 0; i < tempResortEaglePack.countEaglesInList(); i++) {
			addEagle(tempResortEaglePack.findByIndex(i));
		}
		tempResortEaglePack.clear();
	}

//	this function gets the way of sorting and sorts the penguins by the information
	public void penguinSortWay(int sortFormPenguin) {
		if (sortFormPenguin < 1 || sortFormPenguin > 3) {
			return; // only 1 (name), 2 (height) and 3 (age) are valid strategies
		}
		this.sortFormPenguin = sortFormPenguin;
		this.tempPenguinList = new PenguinLinkedList();
		tempPenguinList = penguinHord.copy();
		penguinHord.clear();
		penguinHord.setSortFormPenguin(sortFormPenguin);
		for (int i = 0; i < tempPenguinList.countPenguinsInList(); i++) {
			penguinHord.addPenguin(tempPenguinList.findByIndex(i));
		}
		tempPenguinList.clear();
	}

//		returns how much all the fish in the aquarium eats together
	public double howMuchAllTheFishEat() {
		double totalFood = 0;
		for (int i = 0; i < fishArr.size(); i++) {
			totalFood += fishArr.get(i).feed();
			fishArr.get(i).setHappiness(100);
		}
		return totalFood;
	}

	public int theNumberOfAquariumFish() {
		int count = 0;
		for (int i = 0; i < fishArr.size(); i++) {
			if (fishArr.get(i) instanceof AquariumFish) {
				count++;
			}
		}
		return count;
	}

	public int theNumberOfGoldFish() {
		int count = 0;
		for (int i = 0; i < fishArr.size(); i++) {
			if (fishArr.get(i) instanceof GoldFish) {
				count++;
			}
		}
		return count;
	}

	public int theNumberOfClownFish() {
		int count = 0;
		for (int i = 0; i < fishArr.size(); i++) {
			if (fishArr.get(i) instanceof ClownFish) {
				count++;
			}
		}
		return count;
	}

//		creates randomly 1 Aquarium Fish at a time 
	private AquariumFish creatingRandomAquariumFish() throws ExceptionZoo {
		int age;
		double length;
		age = (random.nextInt(AquariumFish.LifeSpan));
		length = (random.nextDouble(AquariumFish.RANGE_LENGTH_RANDOM_FISH));

		int randomFishColorEnumLenght = (random.nextInt(10) + 1);
		BodySignatureEnum fishBodySignature;
		FishColorEnum[] arrayOfTheColorsThatWeChooseRandomly = new FishColorEnum[randomFishColorEnumLenght];
		FishColorEnum[] fishColorEnumArr = FishColorEnum.values();
		BodySignatureEnum[] bodySignatureEnumsArr = BodySignatureEnum.values();

//		TO Avoid Repetitive colors we making an array list with the location of every color in the Enum and every random removing the location
		int sizeOfTheListOfAvilableNumbersOfColorIndex = 10;
		ArrayList<Integer> listOfAvilableNumbersOfColorIndex = new ArrayList<Integer>(
				sizeOfTheListOfAvilableNumbersOfColorIndex);
		for (int i = 0; i < sizeOfTheListOfAvilableNumbersOfColorIndex; i++) {
			listOfAvilableNumbersOfColorIndex.add(i);
		}
		int locationInTheMainColorArray = 0;
		Random rand = new Random();
		while (listOfAvilableNumbersOfColorIndex.size() > 0) {
			int index = rand.nextInt(listOfAvilableNumbersOfColorIndex.size());
			if (locationInTheMainColorArray < arrayOfTheColorsThatWeChooseRandomly.length) {
				arrayOfTheColorsThatWeChooseRandomly[locationInTheMainColorArray] = fishColorEnumArr[listOfAvilableNumbersOfColorIndex
						.get(index)];
			}
			locationInTheMainColorArray++;
			listOfAvilableNumbersOfColorIndex.remove(index);
		}
		// there are 4 fish body signature (like: spots, strips ex.) here we will get 1
		// randomly for the fish
		fishBodySignature = bodySignatureEnumsArr[random.nextInt(bodySignatureEnumsArr.length)];

		AquariumFish newFish = new AquariumFish(age, length, arrayOfTheColorsThatWeChooseRandomly, fishBodySignature);

		return newFish;
	}

//	 	creates randomly 1 Gold Fish at a time 
	private GoldFish creatingRandomGoldFish() throws ExceptionZoo {
		int age, randomColorNumber;
		double length;
		age = (random.nextInt(GoldFish.LifeSpan));
		length = (random.nextDouble(GoldFish.RANGE_LENGTH_RANDOM_GOLD_FISH));
		FishColorEnum[] theColorThatWeChooseRandomly = new FishColorEnum[1];
		FishColorEnum[] fishColorEnumArr = FishColorEnum.values();
		randomColorNumber = random.nextInt(4);
		if (randomColorNumber == 0) {
			theColorThatWeChooseRandomly[0] = fishColorEnumArr[0];
		} else if (randomColorNumber == 1) {
			theColorThatWeChooseRandomly[0] = fishColorEnumArr[5];
		} else if (randomColorNumber == 2) {
			theColorThatWeChooseRandomly[0] = fishColorEnumArr[7];
		} else {
			theColorThatWeChooseRandomly[0] = fishColorEnumArr[3];
		}

		GoldFish newFish = new GoldFish(age, length, theColorThatWeChooseRandomly);
		return newFish;
	}

//		creates randomly 1 Clown Fish at a time
	private ClownFish creatingRandomClownFish() throws ExceptionZoo {
		int age;
		double length;
		age = (random.nextInt(ClownFish.LifeSpan));
		length = (random.nextDouble(ClownFish.RANGE_LENGTH_RANDOM_CLOWN_FISH));
		ClownFish newFish = new ClownFish(age, length);
		return newFish;
	}

//		the function gets the users wanted amount of fish to be entered randomly
	public void addNumbrOfFish(int numOfFish) throws ExceptionZoo {
		// activates "numOfFish" times the creatingRandomFish function and enter every
		// time the fish to the array
		int randomFishType;
		FishAbstract newFish;
		for (int i = 0; i < numOfFish; i++) {
			randomFishType = random.nextInt(3);
			if (randomFishType == 1) {
				newFish = creatingRandomAquariumFish();
			} else if (randomFishType == 2) {
				newFish = creatingRandomGoldFish();
			} else {
				newFish = creatingRandomClownFish();
			}
			fishArr.add(newFish);
		}
	}

//		gets the information that the user entered about the new fish and imports it to the fish array
	public void AddOneNewFishToArr(FishAbstract newFish) {
		fishArr.add(newFish);
	}

//		return string with all the fish in the aquarium
	public String returnStringOfArrFish() {
		String allFish = "";
		for (int i = 0; i < fishArr.size(); i++) {

			allFish += fishArr.get(i).toString() + "\n";

		}
		return allFish;

	}

//		this function running on the fish array like a cube: we comparing 1 color to all the fish than move to the next color 
	public String returnStringOfAllColorFish() {
		String allColorFish = "";
		for (FishColorEnum currentColor : FishColorEnum.values()) {
			boolean foundColor = false;
			for (int i = 0; i < fishArr.size() && !foundColor; i++) {
				for (FishColorEnum fishColor : fishArr.get(i).getColor()) {
					if (fishColor == currentColor) {
						foundColor = true;
						break;
					}
				}
			}
			if (foundColor) {
				allColorFish += currentColor + "\n";
			}
		}

		return allColorFish + "\n" + returnStringOfDominantColorFish();

	}

//	 	the function running on all the fish colors and counting the 2 most used colors
	public String returnStringOfDominantColorFish() {
		String dominant = "";
		String color1 = "", color2 = "";
		int count1 = 0, count2 = 0, temp = 0;
		for (int currentColor = 0; currentColor < FishColorEnum.values().length; currentColor++) {
			for (int theFishPlaceInTheArray = 0; theFishPlaceInTheArray < fishArr.size(); theFishPlaceInTheArray++) {
				for (int theColorPlaceInTheFish = 0; theColorPlaceInTheFish < fishArr.get(theFishPlaceInTheArray)
						.getColor().length; theColorPlaceInTheFish++) {
					if (fishArr.get(theFishPlaceInTheArray).getColor()[theColorPlaceInTheFish] == FishColorEnum
							.values()[currentColor]) {
						temp++;
					}
				}
			}
			if (currentColor == 0) {
				count1 = temp;
				color1 += FishColorEnum.values()[currentColor];
			}
			if (currentColor == 1) {
				count2 = temp;
				color2 += FishColorEnum.values()[currentColor];
			}
			if (currentColor > 1) {
				if (count1 < temp) {
					if (count2 < count1) {
						count2 = temp;
						color2 = "";
						color2 += FishColorEnum.values()[currentColor];
					} else {
						count1 = temp;
						color1 = "";
						color1 += FishColorEnum.values()[currentColor];
					}

				} else if (count2 < temp) {
					if (count1 < count2) {
						count1 = temp;
						color1 = "";
						color1 += FishColorEnum.values()[currentColor];
					} else {
						count2 = temp;
						color2 = "";
						color2 += FishColorEnum.values()[currentColor];
					}

				}
			}
			temp = 0;
		}
		dominant = "The 2 Dominant Colors are:\n" + color1 + ", " + color2;
		return dominant;
	}

//		this function returns the total amount of food that all the penguins ate; every penguin eats one fish;
	public int totalPenguinsFood() {
		int totalFood = 0;
		for (int i = 0; i < penguinHord.countPenguinsInList(); i++) {
			totalFood += (int) penguinHord.findByIndex(i).feed();
			penguinHord.findByIndex(i).setHappiness(100);
		}
		return totalFood;
	}

	public int totalEagleFood() {
		return totalEagleFood(baldEaglePack) + totalEagleFood(blackEaglePack) + totalEagleFood(goldenEaglePack);
	}

	public int totalCatsFood() {
		return (int) cats.feedCats();
	}

//	this function returns the total amount of food that all the Eagle ate;
	public int totalEagleFood(EagleLinkedListInterface EaglePack) {
		int totalFood = 0;
		for (int i = 0; i < EaglePack.countEaglesInList(); i++) {
			totalFood += (int) EaglePack.findByIndex(i).feed();
			EaglePack.findByIndex(i).setHappiness(100);
		}
		return totalFood;
	}

//		this function gets the amount of food that every Lion eat and sums it;
	public int howMuchAllTheLionsEat() {
		int totalFood = 0;
		for (int i = 0; i < lionArr.size(); i++) {
			totalFood += lionArr.get(i).feed();
			lionArr.get(i).setHappiness(100);
		}

		return totalFood;
	}

//	this function gets the amount of food that every Tiger eat and sums it;
	public int howMuchAllTheTigersEat() {
		int totalFood = 0;
		for (int i = 0; i < tigerArr.size(); i++) {
			totalFood += tigerArr.get(i).feed();
			tigerArr.get(i).setHappiness(100);
		}

		return totalFood;
	}

//	 	by getting all the information about the Lion from the user in the UI the function adds a new lion to the ArrayList of lions
	public void addNewLion(Lion newLion) {
		lionArr.add(newLion);
	}

// 	by getting all the information about the Tiger from the user in the UI the function adds a new lion to the ArrayList of lions
	public void addNewTiger(Tiger newTiger) {
		tigerArr.add(newTiger);
	}

//		the function running on the lion array list and print every lion
	public String returnStringOfAllPredetoryAnimals() {
		String allPredetoryAnimals = "";
		for (int i = 0; i < lionArr.size(); i++) {

			allPredetoryAnimals += lionArr.get(i).toString() + "\n";

		}
		allPredetoryAnimals += "\n";

		for (int i = 0; i < tigerArr.size(); i++) {

			allPredetoryAnimals += tigerArr.get(i).toString() + "\n";

		}
		return allPredetoryAnimals + "\n" + StringOfAllEaglesInTheZoo();

	}

//	 	this function gets all the information from the user and add it to the penguin linked list
	public void addNewPeguin(Penguin penguin) {
		penguinHord.addPenguin(penguin);
	}

//		this function gets every animal sound and return a String with all the sounds (All Penguins, All PredetorAnimal, All Fish)  
	public String makeNoise() {
		String noise = "";
		for (int i = 0; i < penguinHord.countPenguinsInList(); i++) {
			noise += penguinHord.findByIndex(i).makeNoise() + ", ";
		}
		for (int i = 0; i < baldEaglePack.countEaglesInList(); i++) {
			noise += baldEaglePack.findByIndex(i).makeNoise() + ", ";
		}
		for (int i = 0; i < goldenEaglePack.countEaglesInList(); i++) {
			noise += goldenEaglePack.findByIndex(i).makeNoise() + ", ";
		}
		for (int i = 0; i < blackEaglePack.countEaglesInList(); i++) {
			noise += blackEaglePack.findByIndex(i).makeNoise() + ", ";
		}
		for (int i = 0; i < tigerArr.size(); i++) {
			noise += tigerArr.get(i).makeNoise() + ", ";
		}
		for (int i = 0; i < lionArr.size(); i++) {
			noise += lionArr.get(i).makeNoise() + ", ";
		}
		noise += cats.makeSound();
		for (int i = 0; i < fishArr.size(); i++) {
			noise += fishArr.get(i).makeNoise() + ", ";
		}
		for (int i = 0; i < megaladon.size(); i++) {
			noise += megaladon.get(i).makeNoise() + (i != megaladon.size() - 1 ? ", " : "!!! ");
		}
		return noise;
	}

//	this function add one year to all the animals and decrease the happiness level,
//	if an animal is bigger than its LifeSpan or the Happiness level <= 0, it "Dies" ==> removed
	public String ageOneYear() {
		String deadAnimals = "The removed animals are:\n";
		deadAnimals += "Lions:\n" + ageOneYearLion() + "\n";
		deadAnimals += "Tigers:\n" + ageOneYearTiger() + "\n";
		deadAnimals += "Penguins:\n" + ageOneYearPenguin() + "\n";
		deadAnimals += "Bald Eagles:\n" + ageOneYearEagles(baldEaglePack) + "\n";
		deadAnimals += "Golden Eagles:\n" + ageOneYearEagles(goldenEaglePack) + "\n";
		deadAnimals += "Black Eagles:\n" + ageOneYearEagles(blackEaglePack) + "\n";
		deadAnimals += "Cats:\n" + cats.ageCatOneYear() + "\n";
		deadAnimals += "Megalodons:\n" + ageOneYearMegalagon() + "\n";
		deadAnimals += "Number of Fish that were removed: " + ageOneYearFish() + "\n";
		return deadAnimals;
	}

	private String ageOneYearMegalagon() {
		int randomHappinessDecrease;
		boolean removedAnimal = false;
		String deadAnimals = "";
		if (megaladon != null && megaladon.size() > 0) {
			for (int i = 0; i < megaladon.size(); i++) {
				megaladon.get(i).ageOneYear();
				randomHappinessDecrease = random.nextInt(Megaladon.LifeSpan / 4);
				megaladon.get(i).setHappiness(megaladon.get(i).getHappiness() - randomHappinessDecrease);
				if (megaladon.get(i).getAge() > Megaladon.LifeSpan || megaladon.get(i).getHappiness() <= 0) {
					deadAnimals += "\t" + megaladon.get(i).getName() + ", "
							+ (megaladon.get(i).getAge() > Megaladon.LifeSpan
									? ("after age: " + (megaladon.get(i).getAge() - 1))
									: "at age: " + megaladon.get(i).getAge())
							+ ", it was " + (megaladon.get(i).getHappiness() <= 0 ? "SAD!" : "HAPPY!") + "\n";
					megaladon.remove(i);
					i--;
					removedAnimal = true;
				}
			}
		}
		if (removedAnimal) {
			return deadAnimals;
		} else {
			return deadAnimals = "\tNo one removed";
		}
	}

	private String ageOneYearEagles(EagleLinkedListInterface EaglePack) {
		int randomHappinessDecrease;
		boolean removedAnimal = false;
		String deadAnimals = "";
		if (EaglePack.countEaglesInList() > 0) {
			for (int i = 0; i < EaglePack.countEaglesInList(); i++) {
				EaglePack.findByIndex(i).ageOneYear();
				randomHappinessDecrease = random.nextInt(Eagle.LifeSpan * 2);
				while (randomHappinessDecrease <= 0) {
					randomHappinessDecrease++;
				}
				EaglePack.findByIndex(i)
						.setHappiness(EaglePack.findByIndex(i).getHappiness() - randomHappinessDecrease);
				if (EaglePack.findByIndex(i).getAge() > Eagle.LifeSpan
						|| EaglePack.findByIndex(i).getHappiness() <= 0) {
					deadAnimals += "\t" + EaglePack.findByIndex(i).getName() + ", "
							+ (EaglePack.findByIndex(i).getAge() > Eagle.LifeSpan
									? ("after age: " + (EaglePack.findByIndex(i).getAge() - 1))
									: ("at age: " + EaglePack.findByIndex(i).getAge()))
							+ ", it was " + (EaglePack.findByIndex(i).getHappiness() <= 0 ? "SAD!" : "HAPPY") + "\n";
					EaglePack.removeByIndex(i);
					i--;
					removedAnimal = true;
				}
			}
		}
		if (removedAnimal) {
			eagleReSortAfterRemovingOne(EaglePack);
			return deadAnimals;
		} else {
			return deadAnimals = "\tNo one removed";
		}
	}

	private String ageOneYearLion() {
		int randomHappinessDecrease;
		boolean removedAnimal = false;
		String deadAnimals = "";
		if (lionArr != null && lionArr.size() > 0) {
			for (int i = 0; i < lionArr.size(); i++) {
				lionArr.get(i).ageOneYear();
				randomHappinessDecrease = random.nextInt(PredatoryAnimalsAbstract.LifeSpan * 2);
				lionArr.get(i).setHappiness(lionArr.get(i).getHappiness() - randomHappinessDecrease);
				if (lionArr.get(i).getAge() > PredatoryAnimalsAbstract.LifeSpan || lionArr.get(i).getHappiness() <= 0) {
					deadAnimals += "\t" + lionArr.get(i).getName() + ", "
							+ (lionArr.get(i).getAge() > PredatoryAnimalsAbstract.LifeSpan
									? ("after age: " + (lionArr.get(i).getAge() - 1))
									: "at age: " + lionArr.get(i).getAge())
							+ ", it was " + (lionArr.get(i).getHappiness() <= 0 ? "SAD!" : "HAPPY!") + "\n";
					lionArr.remove(i);
					i--;
					removedAnimal = true;
				}
			}
		}
		if (removedAnimal) {
			return deadAnimals;
		} else {
			return deadAnimals = "\tNo one removed";
		}
	}

	private String ageOneYearTiger() {
		int randomHappinessDecrease;
		boolean removedAnimal = false;
		String deadAnimals = "";
		if (tigerArr != null && tigerArr.size() > 0) {
			for (int i = 0; i < tigerArr.size(); i++) {
				tigerArr.get(i).ageOneYear();
				randomHappinessDecrease = random.nextInt(PredatoryAnimalsAbstract.LifeSpan);
				if (randomHappinessDecrease < 5) {
					randomHappinessDecrease = 5;
				}
				tigerArr.get(i).setHappiness(tigerArr.get(i).getHappiness() - randomHappinessDecrease);
				if (tigerArr.get(i).getAge() > PredatoryAnimalsAbstract.LifeSpan
						|| tigerArr.get(i).getHappiness() <= 0) {
					deadAnimals += "\t" + tigerArr.get(i).getName() + ", "
							+ (tigerArr.get(i).getAge() > PredatoryAnimalsAbstract.LifeSpan
									? ("after age: " + (tigerArr.get(i).getAge() - 1))
									: "at age: " + tigerArr.get(i).getAge())
							+ ", it was " + (tigerArr.get(i).getHappiness() <= 0 ? "SAD!" : "HAPPY") + "\n";
					tigerArr.remove(i);
					i--;
					removedAnimal = true;
				}
			}
		}
		if (removedAnimal) {
			return deadAnimals;
		} else {
			return deadAnimals = "\tNo one removed";
		}
	}

	private String ageOneYearPenguin() {
		int randomHappinessDecrease;
		boolean removedAnimal = false;
		String deadAnimals = "";
		if (penguinHord.countPenguinsInList() > 0) {
			for (int i = 0; i < penguinHord.countPenguinsInList(); i++) {
				penguinHord.findByIndex(i).ageOneYear();
				randomHappinessDecrease = random.nextInt(Penguin.LifeSpan);
				while (randomHappinessDecrease <= 0) {
					randomHappinessDecrease++;
				}
				penguinHord.findByIndex(i)
						.setHappiness(penguinHord.findByIndex(i).getHappiness() - randomHappinessDecrease);
				if (penguinHord.findByIndex(i).getAge() > Penguin.LifeSpan
						|| penguinHord.findByIndex(i).getHappiness() <= 0) {
					deadAnimals += "\t" + penguinHord.findByIndex(i).getName() + ", "
							+ (penguinHord.findByIndex(i).getAge() > Penguin.LifeSpan
									? ("after age: " + (penguinHord.findByIndex(i).getAge() - 1))
									: ("at age: " + penguinHord.findByIndex(i).getAge()))
							+ ", it was " + (penguinHord.findByIndex(i).getHappiness() <= 0 ? "SAD!" : "HAPPY") + "\n";
					penguinHord.removeByIndex(i);
					i--;
					removedAnimal = true;
				}
			}
		}
		if (removedAnimal) {
			if (this.sortFormPenguin == 2) {
				penguinSortWay(this.sortFormPenguin);
			}
			return deadAnimals;
		} else {
			return deadAnimals = "\tNo one removed";
		}
	}

	private int ageOneYearFish() {
		int randomHappinessDecrease;
		int deadAnimals = 0;
		if (fishArr != null && fishArr.size() > 0) {
			for (int i = 0; i < fishArr.size(); i++) {
				FishAbstract currentFish = fishArr.get(i);
				fishArr.get(i).ageOneYear();
				if (currentFish instanceof GoldFish) {
					randomHappinessDecrease = random.nextInt(GoldFish.LifeSpan) + 1;
					fishArr.get(i).setHappiness(fishArr.get(i).getHappiness() - randomHappinessDecrease);
					if (fishArr.get(i).getAge() > GoldFish.LifeSpan || fishArr.get(i).getHappiness() <= 0) {
						deadAnimals++;
						fishArr.remove(i);
						i--;
					}
				} else if (currentFish instanceof AquariumFish) {
					randomHappinessDecrease = random.nextInt(AquariumFish.LifeSpan) + 1;
					fishArr.get(i).setHappiness(fishArr.get(i).getHappiness() - randomHappinessDecrease);
					if (fishArr.get(i).getAge() > AquariumFish.LifeSpan || fishArr.get(i).getHappiness() <= 0) {
						deadAnimals++;
						fishArr.remove(i);
						i--;
					}
				} else if (currentFish instanceof ClownFish) {
					randomHappinessDecrease = random.nextInt(ClownFish.LifeSpan) + 1;
					fishArr.get(i).setHappiness(fishArr.get(i).getHappiness() - randomHappinessDecrease);
					if (fishArr.get(i).getAge() > ClownFish.LifeSpan || fishArr.get(i).getHappiness() <= 0) {
						deadAnimals++;
						fishArr.remove(i);
						i--;
					}
				}
			}
//			return deadAnimals;
		}
		return deadAnimals;
	}

//	================================== FROM HER STARTS ALL THE GETTERS AND SETTERS ===========================================

	public String getZooName() {
		return zooName;
	}

	public String getZooAddress() {
		return zooAddress;
	}

	public ArrayList<Lion> getLionsArr() {
		return lionArr;
	}

	public ArrayList<Megaladon> getMegaladon() {
		return megaladon;
	}

	public ArrayList<Tiger> getTigersArr() {
		return tigerArr;
	}

	public PenguinsLinkedListInterface getPenguinHord() {
		return penguinHord;
	}

	public PenguinsLinkedListInterface getTempPenguinList() {
		return tempPenguinList;
	}

	public ArrayList<FishAbstract> getFishArr() {
		return fishArr;
	}

	public EagleLinkedListInterface getEaglePack(EagleTypesEnum eagleType) {
		switch (eagleType) {
		case BALD:
			return baldEaglePack;
		case GOLDEN:
			return goldenEaglePack;
		default:
			return blackEaglePack;
		}
	}

	public CatBinaryTreeInterface getCats() {
		return cats;
	}

}
