package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import animals.AquariumFish;
import animals.Cat;
import animals.ClownFish;
import animals.Eagle;
import animals.GoldFish;
import animals.Lion;
import animals.Megaladon;
import animals.Penguin;
import animals.Tiger;
import abstract_classes.PredatoryAnimalsAbstract;
import enums.BodySignatureEnum;
import enums.EagleTypesEnum;
import enums.FishColorEnum;
import interfaces.ZooInterface;
import zoo.ExceptionZoo;

/**
 * Form for adding any animal. It uses the same limits as the console version and the model's own constructors,
 * so an invalid animal is rejected with the project's ExceptionZoo messages.
 */
public class AddAnimalDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private static final String[] SPECIES = { "Penguin", "Lion", "Tiger", "Eagle", "Cat", "Aquarium fish",
			"Gold fish", "Clown fish", "Megalodon" };
	private static final FishColorEnum[] GOLD_FISH_COLORS = { FishColorEnum.BLACK, FishColorEnum.ORANGE,
			FishColorEnum.YELLOW, FishColorEnum.GOLD };

	/** Thrown for input problems found before the model is called. */
	private static class InputException extends Exception {
		private static final long serialVersionUID = 1L;

		InputException(String message) {
			super(message);
		}
	}

	private interface Creator {
		String create() throws InputException, ExceptionZoo;
	}

	private final AppContext app;
	private final JComboBox<String> speciesBox = new JComboBox<>(SPECIES);
	private final CardLayout cards = new CardLayout();
	private final JPanel forms = new JPanel(cards);
	private final JLabel error = Widgets.label(" ", Theme.BODY, Theme.DANGER);
	private final List<Creator> creators = new ArrayList<>();

	public AddAnimalDialog(Window owner, AppContext app) {
		super(owner, "Add an animal", ModalityType.APPLICATION_MODAL);
		this.app = app;
		JPanel root = new JPanel(new BorderLayout(0, 16));
		root.setBackground(Theme.SURFACE);
		root.setBorder(new EmptyBorder(22, 24, 20, 24));

		JPanel top = new JPanel(new BorderLayout(0, 12));
		top.setOpaque(false);
		top.add(Widgets.label("Add an animal", Theme.HEADING, Theme.TEXT), BorderLayout.NORTH);
		JPanel speciesRow = new JPanel(new BorderLayout(12, 0));
		speciesRow.setOpaque(false);
		speciesRow.add(Widgets.label("Species", Theme.BODY_BOLD, Theme.TEXT_MUTED), BorderLayout.WEST);
		style(speciesBox);
		speciesRow.add(speciesBox, BorderLayout.CENTER);
		top.add(speciesRow, BorderLayout.SOUTH);
		root.add(top, BorderLayout.NORTH);

		forms.setOpaque(false);
		buildForms(app.zoo());
		root.add(forms, BorderLayout.CENTER);
		speciesBox.addActionListener(e -> {
			cards.show(forms, (String) speciesBox.getSelectedItem());
			error.setText(" ");
		});

		JButton cancel = Widgets.button("Cancel", false);
		cancel.addActionListener(e -> dispose());
		JButton add = Widgets.button("Add to the zoo", true);
		add.addActionListener(e -> submit());
		JPanel bottom = new JPanel(new BorderLayout(10, 10));
		bottom.setOpaque(false);
		bottom.add(error, BorderLayout.CENTER);
		bottom.add(Widgets.row(10, cancel, add), BorderLayout.EAST);
		root.add(bottom, BorderLayout.SOUTH);

		setContentPane(root);
		getRootPane().setDefaultButton(add);
		setSize(560, 560);
		setLocationRelativeTo(owner);
	}

	/** Pre-selects a species, for example from a screenshot script. */
	public void selectSpecies(String species) {
		speciesBox.setSelectedItem(species);
	}

	private void submit() {
		try {
			String message = creators.get(speciesBox.getSelectedIndex()).create();
			app.log(message);
			app.refreshAll();
			dispose();
		} catch (InputException ex) {
			error.setText(ex.getMessage());
		} catch (ExceptionZoo ex) {
			error.setText("<html>" + ex.getTheIssueTitle() + ": " + ex.getTheIssueDescription().replace("\n", " ")
					+ "</html>");
		}
	}

	private void buildForms(ZooInterface zoo) {
		// Penguin
		Form penguin = new Form();
		JTextField pName = penguin.text("Name", "letters only");
		JTextField pAge = penguin.text("Age", "0 - " + Penguin.LifeSpan + " years");
		JTextField pHeight = penguin.text("Height", "0 < height <= 200 cm");
		addForm("Penguin", penguin, () -> {
			Penguin p = new Penguin(name(pName), intIn(pAge, "Age", 0, Penguin.LifeSpan),
					doubleIn(pHeight, "Height", 0, 200));
			zoo.addNewPeguin(p);
			return "New penguin " + p.getName() + " joined the group.";
		});

		// Lion and tiger
		for (String kind : new String[] { "Lion", "Tiger" }) {
			Form form = new Form();
			JTextField name = form.text("Name", "letters only");
			JComboBox<String> gender = form.combo("Gender", new String[] { "Male", "Female" });
			JTextField age = form.text("Age", "0 - " + PredatoryAnimalsAbstract.LifeSpan + " years");
			JTextField weight = form.text("Weight", "0 < weight <= 500 kg");
			addForm(kind, form, () -> {
				String n = name(name);
				boolean male = gender.getSelectedIndex() == 0;
				int a = intIn(age, "Age", 0, PredatoryAnimalsAbstract.LifeSpan);
				double w = doubleIn(weight, "Weight", 0, 500);
				if (kind.equals("Lion")) {
					zoo.addNewLion(new Lion(n, male, w, a));
				} else {
					zoo.addNewTiger(new Tiger(n, male, w, a));
				}
				return "New " + kind.toLowerCase() + " " + n + " arrived.";
			});
		}

		// Eagle: speed and altitude are random, exactly like the console version
		Form eagle = new Form();
		JTextField eName = eagle.text("Name", "letters only");
		JComboBox<String> eGender = eagle.combo("Gender", new String[] { "Male", "Female" });
		JTextField eAge = eagle.text("Age", "0 - " + Eagle.LifeSpan + " years");
		JTextField eWeight = eagle.text("Weight", "0 < weight <= " + Eagle.MaxEagleWeight + " kg");
		JComboBox<String> eType = eagle.combo("Type", names(EagleTypesEnum.values()));
		eagle.note("Speed and top altitude are generated randomly, as in the console version.");
		addForm("Eagle", eagle, () -> {
			Eagle e = new Eagle(name(eName), eGender.getSelectedIndex() == 0, doubleIn(eWeight, "Weight", 0,
					Eagle.MaxEagleWeight), intIn(eAge, "Age", 0, Eagle.LifeSpan),
					zoo.randomEagleParameters(Eagle.MaxTopSpeed), zoo.randomEagleParameters(Eagle.MaxTopAltitude),
					EagleTypesEnum.values()[eType.getSelectedIndex()]);
			zoo.addEagle(e);
			return "New " + e.getEagleType().name().toLowerCase() + " eagle " + e.getName() + " joined its pack.";
		});

		// Cat
		Form cat = new Form();
		JTextField cName = cat.text("Name", "letters only");
		JComboBox<String> cGender = cat.combo("Gender", new String[] { "Male", "Female" });
		JTextField cAge = cat.text("Age", "0 - " + PredatoryAnimalsAbstract.LifeSpan + " years");
		JTextField cWeight = cat.text("Weight", "0 < weight <= " + Cat.MaxWeight + " kg");
		addForm("Cat", cat, () -> {
			Cat c = new Cat(name(cName), cGender.getSelectedIndex() == 0, doubleIn(cWeight, "Weight", 0, Cat.MaxWeight),
					intIn(cAge, "Age", 0, PredatoryAnimalsAbstract.LifeSpan));
			zoo.addNewCat(c);
			return "New cat " + c.getName() + " was placed in the tree.";
		});

		// Aquarium fish
		Form aquarium = new Form();
		JTextField aLength = aquarium.text("Length", "0 < length <= " + AquariumFish.RANGE_LENGTH_RANDOM_FISH + " cm");
		JTextField aAge = aquarium.text("Age", "0 - " + AquariumFish.LifeSpan + " years");
		JComboBox<String> aSignature = aquarium.combo("Pattern", names(BodySignatureEnum.values()));
		List<JCheckBox> colorBoxes = aquarium.colors("Colors");
		addForm("Aquarium fish", aquarium, () -> {
			List<FishColorEnum> chosen = new ArrayList<>();
			for (int i = 0; i < colorBoxes.size(); i++) {
				if (colorBoxes.get(i).isSelected()) {
					chosen.add(FishColorEnum.values()[i]);
				}
			}
			if (chosen.isEmpty()) {
				throw new InputException("Choose at least one color.");
			}
			zoo.AddOneNewFishToArr(new AquariumFish(intIn(aAge, "Age", 0, AquariumFish.LifeSpan),
					doubleIn(aLength, "Length", 0, AquariumFish.RANGE_LENGTH_RANDOM_FISH),
					chosen.toArray(new FishColorEnum[0]), BodySignatureEnum.values()[aSignature.getSelectedIndex()]));
			return "A new aquarium fish with " + chosen.size() + " colors was added.";
		});

		// Gold fish
		Form gold = new Form();
		JTextField gLength = gold.text("Length", "0 < length <= " + GoldFish.RANGE_LENGTH_RANDOM_GOLD_FISH + " cm");
		JTextField gAge = gold.text("Age", "0 - " + GoldFish.LifeSpan + " years");
		JComboBox<String> gColor = gold.combo("Color", names(GOLD_FISH_COLORS));
		addForm("Gold fish", gold, () -> {
			zoo.AddOneNewFishToArr(new GoldFish(intIn(gAge, "Age", 0, GoldFish.LifeSpan),
					doubleIn(gLength, "Length", 0, GoldFish.RANGE_LENGTH_RANDOM_GOLD_FISH),
					new FishColorEnum[] { GOLD_FISH_COLORS[gColor.getSelectedIndex()] }));
			return "A new gold fish was added.";
		});

		// Clown fish
		Form clown = new Form();
		JTextField clLength = clown.text("Length",
				"0 < length <= " + ClownFish.RANGE_LENGTH_RANDOM_CLOWN_FISH + " cm");
		JTextField clAge = clown.text("Age", "0 - " + ClownFish.LifeSpan + " years");
		addForm("Clown fish", clown, () -> {
			zoo.AddOneNewFishToArr(new ClownFish(intIn(clAge, "Age", 0, ClownFish.LifeSpan),
					doubleIn(clLength, "Length", 0, ClownFish.RANGE_LENGTH_RANDOM_CLOWN_FISH)));
			return "A new clown fish was added.";
		});

		// Megalodon
		Form mega = new Form();
		JTextField mName = mega.text("Name", "letters only");
		JTextField mAge = mega.text("Age", "0 - " + Megaladon.LifeSpan + " years");
		JTextField mLength = mega.text("Length", Megaladon.MinLength + " < length <= " + Megaladon.MaxLength + " m");
		JTextField mWeight = mega.text("Weight", Megaladon.MinWeight + " < weight <= " + Megaladon.MaxWeight + " t");
		JTextField mSpeed = mega.text("Speed", Megaladon.MinSpeed + " < speed <= " + Megaladon.MaxSpeed + " km/h");
		addForm("Megalodon", mega, () -> {
			String n = name(mName);
			int a = intIn(mAge, "Age", 0, Megaladon.LifeSpan);
			int l = intIn(mLength, "Length", Megaladon.MinLength + 1, Megaladon.MaxLength);
			double w = doubleIn(mWeight, "Weight", Megaladon.MinWeight, Megaladon.MaxWeight);
			int s = intIn(mSpeed, "Speed", Megaladon.MinSpeed + 1, Megaladon.MaxSpeed);
			zoo.addMegaladon(new Megaladon(a, l, n, w, s));
			return "Megalodon " + n + " entered the prehistoric section.";
		});
	}

	private void addForm(String species, Form form, Creator creator) {
		JPanel top = new JPanel(new BorderLayout());
		top.setOpaque(false);
		top.add(form.panel, BorderLayout.NORTH);
		forms.add(top, species);
		creators.add(creator);
	}

	private static String[] names(Enum<?>[] values) {
		String[] names = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			String n = values[i].name();
			names[i] = n.charAt(0) + n.substring(1).toLowerCase();
		}
		return names;
	}

	private static String name(JTextField field) throws InputException {
		String text = field.getText().trim();
		if (!text.matches("^[a-zA-Z]+$")) {
			throw new InputException("The name must contain letters only.");
		}
		return text;
	}

	private static int intIn(JTextField field, String label, int min, int max) throws InputException {
		try {
			int value = Integer.parseInt(field.getText().trim());
			if (value < min || value > max) {
				throw new InputException(label + " must be between " + min + " and " + max + ".");
			}
			return value;
		} catch (NumberFormatException e) {
			throw new InputException(label + " must be a whole number.");
		}
	}

	/** Accepts min < value <= max, like the console version. */
	private static double doubleIn(JTextField field, String label, double min, double max) throws InputException {
		try {
			double value = Double.parseDouble(field.getText().trim());
			if (value <= min || value > max) {
				throw new InputException(label + " must be more than " + fmt(min) + " and at most " + fmt(max) + ".");
			}
			return value;
		} catch (NumberFormatException e) {
			throw new InputException(label + " must be a number.");
		}
	}

	private static String fmt(double d) {
		return d == Math.rint(d) ? String.valueOf((long) d) : String.valueOf(d);
	}

	static void style(JComponent c) {
		c.setFont(Theme.BODY);
		c.setBackground(Theme.SURFACE_HIGH);
		c.setForeground(Theme.TEXT);
		if (c instanceof JTextField) {
			((JTextField) c).setCaretColor(Theme.TEXT);
			c.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Theme.BORDER), new EmptyBorder(6, 8, 6, 8)));
		}
		c.setPreferredSize(new Dimension(220, 34));
	}

	/** A two-column form: label on the left, input and hint on the right. */
	private static class Form {
		final JPanel panel = new JPanel(new GridBagLayout());
		private int row;

		Form() {
			panel.setOpaque(false);
		}

		private void addRow(String label, Component input, String hint) {
			GridBagConstraints c = new GridBagConstraints();
			c.gridy = row++;
			c.insets = new Insets(6, 0, 6, 12);
			c.anchor = GridBagConstraints.WEST;
			panel.add(Widgets.label(label, Theme.BODY_BOLD, Theme.TEXT_MUTED), c);
			c.gridx = 1;
			c.weightx = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(6, 0, 6, 0);
			JPanel cell = new JPanel(new BorderLayout(0, 2));
			cell.setOpaque(false);
			cell.add(input, BorderLayout.CENTER);
			if (hint != null) {
				cell.add(Widgets.label(hint, Theme.SMALL, Theme.TEXT_MUTED), BorderLayout.SOUTH);
			}
			panel.add(cell, c);
		}

		JTextField text(String label, String hint) {
			JTextField field = new JTextField();
			style(field);
			addRow(label, field, hint);
			return field;
		}

		JComboBox<String> combo(String label, String[] options) {
			JComboBox<String> box = new JComboBox<>(options);
			style(box);
			addRow(label, box, null);
			return box;
		}

		List<JCheckBox> colors(String label) {
			JPanel grid = new JPanel(new GridLayout(2, 5, 6, 4));
			grid.setOpaque(false);
			List<JCheckBox> boxes = new ArrayList<>();
			for (FishColorEnum color : FishColorEnum.values()) {
				JCheckBox box = new JCheckBox(color.name().charAt(0) + color.name().substring(1).toLowerCase());
				box.setFont(Theme.SMALL);
				box.setForeground(Theme.TEXT);
				box.setOpaque(false);
				box.setFocusPainted(false);
				box.setIcon(new Widgets.DotIcon(darken(Theme.fishColor(color))));
				box.setSelectedIcon(new Widgets.DotIcon(Theme.fishColor(color)) {
					@Override
					public void paintIcon(Component c, java.awt.Graphics g, int x, int y) {
						super.paintIcon(c, g, x, y);
						java.awt.Graphics2D g2 = Widgets.smooth(g.create());
						g2.setColor(Theme.ACCENT);
						g2.drawOval(x - 2, y - 1, 13, 13);
						g2.dispose();
					}
				});
				boxes.add(box);
				grid.add(box);
			}
			addRow(label, grid, "Pick between 1 and 10 colors");
			return boxes;
		}

		void note(String text) {
			GridBagConstraints c = new GridBagConstraints();
			c.gridy = row++;
			c.gridx = 0;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(10, 0, 0, 0);
			panel.add(Widgets.label(text, Theme.SMALL, Theme.TEXT_MUTED), c);
		}

		private static Color darken(Color c) {
			return new Color(c.getRed() / 3, c.getGreen() / 3, c.getBlue() / 3);
		}
	}
}
