package interfaces;

import java.util.ArrayList;
import abstract_classes.*;
import animals.*;
import enums.EagleTypesEnum;
import zoo.ExceptionZoo;
import zoo.Zoo;

public interface ZooInterface {
	static Zoo getZooInstance(String zooName, String zooAddress) {
		return null;
	}

	void zooContant() throws ExceptionZoo;

	void addMegaladon(Megaladon megaladon);

	String stringOfMegaladon();

	int howMuchTheMegaladonEats();

	void addNewCat(Cat cat);

	int howMuchCatsInTheZoo();

	String returnStringOfAllTheCats();

	int randomEagleParameters(int maxParameterValue);

	void addEagle(Eagle eagle);

	int howMuchEaglesInTheZoo();

	String StringOfAllEaglesInTheZoo();

	void penguinSortWay(int sortFormPenguin);

	double howMuchAllTheFishEat();

	int theNumberOfAquariumFish();

	int theNumberOfGoldFish();

	int theNumberOfClownFish();

	void addNumbrOfFish(int numOfFish) throws ExceptionZoo;

	void AddOneNewFishToArr(FishAbstract newFish);

	String returnStringOfArrFish();

	String returnStringOfAllColorFish();

	String returnStringOfDominantColorFish();

	int totalPenguinsFood();

	int totalEagleFood();

	int totalCatsFood();

	int totalEagleFood(EagleLinkedListInterface EaglePack);

	int howMuchAllTheLionsEat();

	int howMuchAllTheTigersEat();

	void addNewLion(Lion newLion);

	void addNewTiger(Tiger newTiger);

	String returnStringOfAllPredetoryAnimals();

	void addNewPeguin(Penguin penguin);

	String makeNoise();

	String ageOneYear();

	String getZooName();

	String getZooAddress();

	ArrayList<Lion> getLionsArr();

	ArrayList<Megaladon> getMegaladon();

	public ArrayList<Tiger> getTigersArr();

	public PenguinsLinkedListInterface getPenguinHord();

	public PenguinsLinkedListInterface getTempPenguinList();

	public ArrayList<FishAbstract> getFishArr();

	public EagleLinkedListInterface getEaglePack(EagleTypesEnum eagleType);

	public CatBinaryTreeInterface getCats();
}
