package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import abstract_classes.AnimalAbstract;
import abstract_classes.FishAbstract;
import animals.Cat;
import animals.Eagle;
import enums.EagleTypesEnum;
import enums.FishColorEnum;
import interfaces.EagleLinkedListInterface;
import interfaces.PenguinsLinkedListInterface;
import interfaces.ZooInterface;

/**
 * Dashboard home screen: population per group, average happiness and the aquarium color mix.
 */
public class OverviewView extends JPanel implements View {
	private static final long serialVersionUID = 1L;

	private final AppContext app;
	private final Widgets.StatCard penguins = new Widgets.StatCard("Penguins", Theme.PENGUIN);
	private final Widgets.StatCard lions = new Widgets.StatCard("Lions", Theme.LION);
	private final Widgets.StatCard tigers = new Widgets.StatCard("Tigers", Theme.TIGER);
	private final Widgets.StatCard eagles = new Widgets.StatCard("Eagles", Theme.EAGLE);
	private final Widgets.StatCard cats = new Widgets.StatCard("Cats", Theme.CAT);
	private final Widgets.StatCard fish = new Widgets.StatCard("Fish", Theme.FISH);
	private final Widgets.StatCard megalodons = new Widgets.StatCard("Megalodons", Theme.MEGALODON);
	private final Widgets.StatCard total = new Widgets.StatCard("All Animals", Theme.ACCENT);
	private final BarChart population = new BarChart("", 0);
	private final BarChart happiness = new BarChart("%", 100);
	private final JPanel colorChips = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
	private final JLabel dominantLabel = Widgets.label("", Theme.BODY, Theme.TEXT_MUTED);

	public OverviewView(AppContext app) {
		super(new BorderLayout(0, 18));
		this.app = app;
		setOpaque(false);
		ZooInterface zoo = app.zoo();
		add(Widgets.header("Zoo Overview",
				zoo.getZooName().trim() + ", " + zoo.getZooAddress().trim()
						+ ". Live numbers from the zoo's data structures."),
				BorderLayout.NORTH);

		JPanel content = new JPanel();
		content.setOpaque(false);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		JPanel cards = new JPanel(new GridLayout(2, 4, 14, 14));
		cards.setOpaque(false);
		for (Widgets.StatCard card : new Widgets.StatCard[] { total, penguins, lions, tigers, eagles, cats, fish,
				megalodons }) {
			cards.add(card);
		}
		content.add(cards);
		content.add(spacer());

		JPanel charts = new JPanel(new GridLayout(1, 2, 14, 0));
		charts.setOpaque(false);
		charts.add(Widgets.card("Population by group", population));
		charts.add(Widgets.card("Average happiness by group", happiness));
		content.add(charts);
		content.add(spacer());

		colorChips.setOpaque(false);
		JPanel colors = new JPanel(new BorderLayout(0, 6));
		colors.setOpaque(false);
		colors.add(colorChips, BorderLayout.CENTER);
		colors.add(dominantLabel, BorderLayout.SOUTH);
		content.add(Widgets.card("Aquarium color mix", colors));

		add(content, BorderLayout.CENTER);
	}

	private static JComponent spacer() {
		JPanel spacer = new JPanel();
		spacer.setOpaque(false);
		spacer.setBorder(new EmptyBorder(7, 0, 7, 0));
		return spacer;
	}

	@Override
	public void refresh() {
		ZooInterface zoo = app.zoo();

		PenguinsLinkedListInterface penguinList = zoo.getPenguinHord();
		int penguinCount = penguinList.countPenguinsInList();
		int penguinHappiness = 0;
		for (int i = 0; i < penguinCount; i++) {
			penguinHappiness += penguinList.findByIndex(i).getHappiness();
		}

		int eagleCount = 0;
		int eagleHappiness = 0;
		for (EagleTypesEnum type : EagleTypesEnum.values()) {
			EagleLinkedListInterface pack = zoo.getEaglePack(type);
			for (int i = 0; i < pack.countEaglesInList(); i++) {
				Eagle eagle = pack.findByIndex(i);
				eagleCount++;
				eagleHappiness += eagle.getHappiness();
			}
		}

		List<Cat> catList = zoo.getCats().getCatsInOrder();
		List<FishAbstract> fishList = zoo.getFishArr();

		penguins.update(penguinCount, average(penguinHappiness, penguinCount), "Linked list, sortable");
		lions.update(zoo.getLionsArr().size(), averageOf(zoo.getLionsArr()), "Predators");
		tigers.update(zoo.getTigersArr().size(), averageOf(zoo.getTigersArr()), "Predators");
		eagles.update(eagleCount, average(eagleHappiness, eagleCount), "3 packs, linked lists");
		cats.update(catList.size(), averageOf(catList), "Binary tree by age");
		fish.update(fishList.size(), averageOf(fishList), zoo.theNumberOfAquariumFish() + " aquarium, "
				+ zoo.theNumberOfGoldFish() + " gold, " + zoo.theNumberOfClownFish() + " clown");
		megalodons.update(zoo.getMegaladon().size(), averageOf(zoo.getMegaladon()), "Prehistoric section");

		int all = penguinCount + zoo.getLionsArr().size() + zoo.getTigersArr().size() + eagleCount + catList.size()
				+ fishList.size() + zoo.getMegaladon().size();
		int allHappiness = penguinHappiness + sum(zoo.getLionsArr()) + sum(zoo.getTigersArr()) + eagleHappiness
				+ sum(catList) + sum(fishList) + sum(zoo.getMegaladon());
		total.update(all, average(allHappiness, all), "Across 7 animal groups");

		population.clear();
		population.add("Penguins", penguinCount, Theme.PENGUIN);
		population.add("Lions", zoo.getLionsArr().size(), Theme.LION);
		population.add("Tigers", zoo.getTigersArr().size(), Theme.TIGER);
		population.add("Eagles", eagleCount, Theme.EAGLE);
		population.add("Cats", catList.size(), Theme.CAT);
		population.add("Fish", fishList.size(), Theme.FISH);
		population.add("Megalodons", zoo.getMegaladon().size(), Theme.MEGALODON);
		population.repaint();

		happiness.clear();
		happiness.add("Penguins", average(penguinHappiness, penguinCount), Theme.PENGUIN);
		happiness.add("Lions", averageOf(zoo.getLionsArr()), Theme.LION);
		happiness.add("Tigers", averageOf(zoo.getTigersArr()), Theme.TIGER);
		happiness.add("Eagles", average(eagleHappiness, eagleCount), Theme.EAGLE);
		happiness.add("Cats", averageOf(catList), Theme.CAT);
		happiness.add("Fish", averageOf(fishList), Theme.FISH);
		happiness.add("Megalodons", averageOf(zoo.getMegaladon()), Theme.MEGALODON);
		happiness.repaint();

		refreshColors(zoo, fishList);
	}

	private void refreshColors(ZooInterface zoo, List<FishAbstract> fishList) {
		Map<FishColorEnum, Integer> counts = new EnumMap<>(FishColorEnum.class);
		for (FishAbstract f : fishList) {
			for (FishColorEnum color : f.getColor()) {
				counts.merge(color, 1, Integer::sum);
			}
		}
		colorChips.removeAll();
		for (FishColorEnum color : FishColorEnum.values()) {
			int count = counts.getOrDefault(color, 0);
			if (count > 0) {
				colorChips.add(colorChip(color, count));
			}
		}
		// The dominant colors come from the zoo's own algorithm, the same one the console version prints.
		String dominant = zoo.returnStringOfDominantColorFish();
		String[] lines = dominant.split("\n");
		dominantLabel.setText(fishList.isEmpty() ? "The aquarium is empty."
				: "Dominant colors: " + lines[lines.length - 1]);
		colorChips.revalidate();
		colorChips.repaint();
	}

	private static JComponent colorChip(FishColorEnum color, int count) {
		Color swatch = Theme.fishColor(color);
		JLabel chip = new JLabel(color.name().charAt(0) + color.name().substring(1).toLowerCase() + "  " + count);
		chip.setFont(Theme.BODY);
		chip.setForeground(Theme.TEXT);
		chip.setIcon(new Widgets.DotIcon(swatch));
		chip.setIconTextGap(8);
		Widgets.RoundedPanel wrapper = new Widgets.RoundedPanel(new BorderLayout(), Theme.SURFACE_HIGH, 14);
		wrapper.setBorder(new EmptyBorder(6, 12, 6, 12));
		wrapper.add(chip);
		return wrapper;
	}

	private static int sum(List<? extends AnimalAbstract> animals) {
		int total = 0;
		for (AnimalAbstract animal : animals) {
			total += animal.getHappiness();
		}
		return total;
	}

	private static int averageOf(List<? extends AnimalAbstract> animals) {
		return average(sum(animals), animals.size());
	}

	private static int average(int total, int count) {
		return count == 0 ? 0 : Math.max(0, Math.round((float) total / count));
	}
}
