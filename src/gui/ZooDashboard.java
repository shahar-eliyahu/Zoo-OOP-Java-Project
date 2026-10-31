package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import interfaces.ZooInterface;
import zoo.ExceptionZoo;
import zoo.Zoo;

/**
 * Graphical front end for the zoo. It works only through {@link ZooInterface}, the same interface the console
 * application uses, so every number on screen comes from the project's own classes and data structures.
 *
 * Run normally to open the window, or with {@code --screenshots <folder>} to save a PNG of every screen.
 */
public class ZooDashboard extends JFrame implements AppContext {
	private static final long serialVersionUID = 1L;

	private final ZooInterface zoo;
	private final CardLayout pages = new CardLayout();
	private final JPanel content = new JPanel(pages);
	private final Map<String, JComponent> views = new LinkedHashMap<>();
	private final List<NavItem> navItems = new ArrayList<>();
	private final JTextArea logArea = new JTextArea();
	private final JLabel yearLabel = Widgets.label("", Theme.BODY_BOLD, Theme.ACCENT);
	private int year = 1;

	public ZooDashboard(ZooInterface zoo) {
		super("Zoo Management System");
		this.zoo = zoo;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Theme.BACKGROUND);
		setLayout(new BorderLayout());

		views.put("Overview", new OverviewView(this));
		views.put("Penguins", new PenguinsView(this));
		views.put("Lions & Tigers", new PredatorsView(this));
		views.put("Eagles", new EaglesView(this));
		views.put("Cats", new CatsView(this));
		views.put("Aquarium", new AquariumView(this));
		views.put("Megalodon", new MegalodonView(this));

		add(buildSidebar(), BorderLayout.WEST);

		JPanel main = new JPanel(new BorderLayout(0, 0));
		main.setBackground(Theme.BACKGROUND);
		main.add(buildTopBar(), BorderLayout.NORTH);
		content.setOpaque(false);
		content.setBorder(new EmptyBorder(6, 28, 16, 28));
		for (Map.Entry<String, JComponent> entry : views.entrySet()) {
			content.add(entry.getValue(), entry.getKey());
		}
		main.add(content, BorderLayout.CENTER);
		main.add(buildLog(), BorderLayout.SOUTH);
		add(main, BorderLayout.CENTER);

		setSize(1380, 900);
		setMinimumSize(new Dimension(1100, 720));
		setLocationRelativeTo(null);
		log("Welcome to " + zoo.getZooName().trim() + ". The zoo was loaded with its starting animals.");
		refreshAll();
		showPage("Overview");
	}

	// ------------------------------------------------------------------ layout

	private JComponent buildSidebar() {
		JPanel sidebar = new JPanel();
		sidebar.setBackground(Theme.SIDEBAR);
		sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
		sidebar.setBorder(new EmptyBorder(24, 16, 20, 16));
		sidebar.setPreferredSize(new Dimension(230, 100));

		JLabel logo = Widgets.label(zoo.getZooName().trim(), Theme.TITLE, Theme.TEXT);
		logo.setIcon(new LogoIcon());
		logo.setIconTextGap(10);
		logo.setAlignmentX(Component.LEFT_ALIGNMENT);
		sidebar.add(logo);
		JLabel tagline = Widgets.label("Zoo Management System", Theme.SMALL, Theme.TEXT_MUTED);
		tagline.setBorder(new EmptyBorder(4, 2, 26, 0));
		tagline.setAlignmentX(Component.LEFT_ALIGNMENT);
		sidebar.add(tagline);

		Color[] colors = { Theme.ACCENT, Theme.PENGUIN, Theme.LION, Theme.EAGLE, Theme.CAT, Theme.FISH,
				Theme.MEGALODON };
		int i = 0;
		for (String name : views.keySet()) {
			NavItem item = new NavItem(name, colors[i++]);
			navItems.add(item);
			sidebar.add(item);
			sidebar.add(Box.createVerticalStrut(4));
		}
		sidebar.add(Box.createVerticalGlue());
		JLabel footer = Widgets.label("<html>Java OOP project<br>Swing interface</html>", Theme.SMALL,
				Theme.TEXT_MUTED);
		footer.setAlignmentX(Component.LEFT_ALIGNMENT);
		sidebar.add(footer);
		return sidebar;
	}

	private JComponent buildTopBar() {
		JPanel bar = new JPanel(new BorderLayout());
		bar.setOpaque(false);
		bar.setBorder(new EmptyBorder(20, 28, 14, 28));

		JPanel left = new JPanel(new BorderLayout(0, 2));
		left.setOpaque(false);
		left.add(Widgets.label(zoo.getZooAddress().trim(), Theme.SMALL, Theme.TEXT_MUTED), BorderLayout.NORTH);
		left.add(yearLabel, BorderLayout.CENTER);
		bar.add(left, BorderLayout.WEST);

		JButton feed = Widgets.button("Feed all", false);
		feed.addActionListener(e -> showDialog(feedDialog()));
		JButton listen = Widgets.button("Listen", false);
		listen.addActionListener(e -> showDialog(listenDialog()));
		JButton nextYear = Widgets.button("Next year", false);
		nextYear.addActionListener(e -> showDialog(nextYearDialog()));
		JButton add = Widgets.button("+ Add animal", true);
		add.addActionListener(e -> new AddAnimalDialog(this, this).setVisible(true));
		bar.add(Widgets.row(10, feed, listen, nextYear, add), BorderLayout.EAST);
		return bar;
	}

	private JComponent buildLog() {
		logArea.setEditable(false);
		logArea.setFont(Theme.SMALL);
		logArea.setForeground(Theme.TEXT_MUTED);
		logArea.setBackground(Theme.SIDEBAR);
		logArea.setBorder(new EmptyBorder(8, 12, 8, 12));
		logArea.setRows(3);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Theme.SIDEBAR);
		panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
		JLabel title = Widgets.label("ACTIVITY", Theme.SMALL.deriveFont(java.awt.Font.BOLD), Theme.TEXT_MUTED);
		title.setBorder(new EmptyBorder(8, 28, 0, 0));
		panel.add(title, BorderLayout.NORTH);
		JPanel inner = new JPanel(new BorderLayout());
		inner.setOpaque(false);
		inner.setBorder(new EmptyBorder(0, 16, 6, 16));
		inner.add(logArea);
		panel.add(inner, BorderLayout.CENTER);
		return panel;
	}

	private void showPage(String name) {
		pages.show(content, name);
		for (NavItem item : navItems) {
			item.setActive(item.name.equals(name));
		}
	}

	// ------------------------------------------------------------------ AppContext

	@Override
	public ZooInterface zoo() {
		return zoo;
	}

	@Override
	public void log(String message) {
		String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		String text = time + "   " + message + "\n" + logArea.getText();
		String[] lines = text.split("\n");
		StringBuilder kept = new StringBuilder();
		for (int i = 0; i < Math.min(3, lines.length); i++) {
			kept.append(lines[i]).append(i < 2 ? "\n" : "");
		}
		logArea.setText(kept.toString().trim());
	}

	@Override
	public void refreshAll() {
		yearLabel.setText("Year " + year);
		for (JComponent view : views.values()) {
			((View) view).refresh();
		}
		content.revalidate();
		content.repaint();
	}

	// ------------------------------------------------------------------ actions

	private JDialog feedDialog() {
		DecimalFormat format = new DecimalFormat("#0.##");
		Object[][] rows = { { "Penguins", zoo.totalPenguinsFood(), "fish" },
				{ "Lions", zoo.howMuchAllTheLionsEat(), "kg of meat" },
				{ "Tigers", zoo.howMuchAllTheTigersEat(), "kg of meat" },
				{ "Fish", format.format(zoo.howMuchAllTheFishEat()), "food packages" },
				{ "Eagles", zoo.totalEagleFood(), "kg of meat" }, { "Cats", zoo.totalCatsFood(), "cans, fish or steak" },
				{ "Megalodons", zoo.howMuchTheMegaladonEats(), "kg of fish" } };
		DefaultTableModel model = Widgets.model(new String[] { "Group", "Amount", "Unit" },
				new Class<?>[] { String.class, String.class, String.class });
		for (Object[] row : rows) {
			model.addRow(new Object[] { row[0], String.valueOf(row[1]), row[2] });
		}
		JTable table = Widgets.table(model);
		table.setPreferredScrollableViewportSize(new Dimension(500, 7 * 32 + 2));
		log("All animals were fed. Every animal is back to 100% happiness.");
		refreshAll();
		return dialog("Feeding time", "Today's food, calculated by each animal's feed() method.",
				Widgets.scroll(table));
	}

	private JDialog listenDialog() {
		JTextArea area = new JTextArea(zoo.makeNoise());
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setEditable(false);
		area.setFont(Theme.BODY);
		area.setForeground(Theme.TEXT);
		area.setBackground(Theme.SURFACE_HIGH);
		area.setBorder(new EmptyBorder(12, 14, 12, 14));
		area.setRows(9);
		area.setColumns(46);
		log("Everyone made a sound. Polymorphism at work: one makeNoise() call per animal.");
		return dialog("Listen to the zoo", "Each animal answers through its own makeNoise() implementation.",
				Widgets.scroll(area));
	}

	private JDialog nextYearDialog() {
		String report = zoo.ageOneYear();
		year++;
		int removed = 0;
		for (String line : report.split("\n")) {
			if (line.startsWith("\t") && !line.contains("No one removed")) {
				removed++;
			}
			if (line.startsWith("Number of Fish that were removed: ")) {
				removed += Integer.parseInt(line.replaceAll("\\D", ""));
			}
		}
		log("Year " + year + " began. " + removed + (removed == 1 ? " animal" : " animals")
				+ " left the zoo because of age or low happiness.");
		refreshAll();
		JTextArea area = new JTextArea(report.replace("The removed animals are:\n", "").trim());
		area.setEditable(false);
		area.setFont(Theme.MONO);
		area.setForeground(Theme.TEXT);
		area.setBackground(Theme.SURFACE_HIGH);
		area.setBorder(new EmptyBorder(12, 14, 12, 14));
		area.setRows(16);
		area.setColumns(44);
		return dialog("Welcome to year " + year,
				"Every animal aged one year and lost some happiness. These animals left the zoo:",
				Widgets.scroll(area));
	}

	private JDialog dialog(String title, String subtitle, JComponent body) {
		JDialog dialog = new JDialog(this, title, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		JPanel root = new JPanel(new BorderLayout(0, 14));
		root.setBackground(Theme.SURFACE);
		root.setBorder(new EmptyBorder(22, 24, 20, 24));
		JPanel top = new JPanel(new BorderLayout(0, 4));
		top.setOpaque(false);
		top.add(Widgets.label(title, Theme.HEADING, Theme.TEXT), BorderLayout.NORTH);
		top.add(Widgets.label(subtitle, Theme.BODY, Theme.TEXT_MUTED), BorderLayout.CENTER);
		root.add(top, BorderLayout.NORTH);
		root.add(body, BorderLayout.CENTER);
		JButton close = Widgets.button("Close", true);
		close.addActionListener(e -> dialog.dispose());
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setOpaque(false);
		bottom.add(close, BorderLayout.EAST);
		root.add(bottom, BorderLayout.SOUTH);
		dialog.setContentPane(root);
		dialog.getRootPane().setDefaultButton(close);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		return dialog;
	}

	private void showDialog(JDialog dialog) {
		dialog.setVisible(true);
	}

	// ------------------------------------------------------------------ small components

	/** One entry of the side navigation. */
	private class NavItem extends JComponent {
		private static final long serialVersionUID = 1L;
		final String name;
		private final Color color;
		private boolean active;
		private boolean hover;

		NavItem(String name, Color color) {
			this.name = name;
			this.color = color;
			setAlignmentX(Component.LEFT_ALIGNMENT);
			setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
			setPreferredSize(new Dimension(198, 42));
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					showPage(NavItem.this.name);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					hover = true;
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					hover = false;
					repaint();
				}
			});
		}

		void setActive(boolean active) {
			this.active = active;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = Widgets.smooth(g.create());
			if (active || hover) {
				g2.setColor(active ? Theme.SURFACE_HIGH : Theme.SURFACE);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
			}
			if (active) {
				g2.setColor(color);
				g2.fillRoundRect(0, 10, 4, getHeight() - 20, 4, 4);
			}
			g2.setColor(color);
			g2.fillOval(16, getHeight() / 2 - 5, 10, 10);
			g2.setFont(active ? Theme.BODY_BOLD : Theme.BODY);
			g2.setColor(active ? Theme.TEXT : Theme.TEXT_MUTED);
			FontMetrics fm = g2.getFontMetrics();
			g2.drawString(name, 38, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
			g2.dispose();
		}
	}

	/** A small paw print drawn with circles. */
	private static class LogoIcon implements javax.swing.Icon {
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = Widgets.smooth(g.create());
			g2.setColor(Theme.ACCENT);
			g2.fillRoundRect(x, y, 34, 34, 10, 10);
			g2.setColor(Theme.BACKGROUND);
			g2.fillOval(x + 10, y + 15, 14, 12);
			g2.fillOval(x + 6, y + 9, 6, 7);
			g2.fillOval(x + 12, y + 5, 6, 7);
			g2.fillOval(x + 18, y + 5, 6, 7);
			g2.fillOval(x + 23, y + 9, 6, 7);
			g2.dispose();
		}

		@Override
		public int getIconWidth() {
			return 34;
		}

		@Override
		public int getIconHeight() {
			return 34;
		}
	}

	// ------------------------------------------------------------------ startup

	private static void applyDarkDefaults() {
		UIManager.put("ComboBox.background", Theme.SURFACE_HIGH);
		UIManager.put("ComboBox.foreground", Theme.TEXT);
		UIManager.put("ComboBox.selectionBackground", Theme.BORDER);
		UIManager.put("ComboBox.selectionForeground", Theme.TEXT);
		UIManager.put("ComboBox.buttonBackground", Theme.SURFACE_HIGH);
		UIManager.put("ComboBox.buttonDarkShadow", Theme.BORDER);
		UIManager.put("ComboBox.buttonShadow", Theme.SURFACE_HIGH);
		UIManager.put("ComboBox.buttonHighlight", Theme.SURFACE_HIGH);
		UIManager.put("ComboBox.disabledBackground", Theme.SURFACE_HIGH);
		UIManager.put("ComboBox.border", BorderFactory.createLineBorder(Theme.BORDER));
		UIManager.put("ScrollBar.thumb", Theme.BORDER);
		UIManager.put("ScrollBar.track", Theme.SURFACE);
		UIManager.put("ScrollBar.thumbDarkShadow", Theme.SURFACE);
		UIManager.put("ScrollBar.thumbShadow", Theme.BORDER);
		UIManager.put("ScrollBar.thumbHighlight", Theme.BORDER);
		UIManager.put("ScrollBar.background", Theme.SURFACE);
		UIManager.put("ScrollBar.width", 10);
		UIManager.put("Panel.background", Theme.BACKGROUND);
		UIManager.put("ToolTip.background", Theme.SURFACE_HIGH);
		UIManager.put("ToolTip.foreground", Theme.TEXT);
	}

	private static ZooInterface createZoo() throws ExceptionZoo {
		ZooInterface zoo = Zoo.getZooInstance(" Afeka Zoo ", " Mifza Kadesh 38 ");
		zoo.zooContant();
		return zoo;
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("awt.useSystemAAFontSettings", "on");
		applyDarkDefaults();
		ZooInterface zoo = createZoo();
		if (args.length >= 2 && args[0].equals("--screenshots")) {
			ScreenshotTour.run(zoo, new File(args[1]));
			return;
		}
		SwingUtilities.invokeLater(() -> new ZooDashboard(zoo).setVisible(true));
	}

	// ------------------------------------------------------------------ screenshots for the portfolio

	/** Opens the window, visits every screen and saves PNG images, then exits. */
	private static final class ScreenshotTour {
		private ScreenshotTour() {
		}

		static void run(ZooInterface zoo, File folder) throws Exception {
			folder.mkdirs();
			ZooDashboard[] holder = new ZooDashboard[1];
			SwingUtilities.invokeAndWait(() -> {
				holder[0] = new ZooDashboard(zoo);
				holder[0].setVisible(true);
			});
			ZooDashboard frame = holder[0];
			Thread.sleep(800);

			int index = 1;
			for (String name : new ArrayList<>(frame.views.keySet())) {
				SwingUtilities.invokeAndWait(() -> frame.showPage(name));
				Thread.sleep(name.equals("Aquarium") ? 1500 : 500);
				save(frame, new File(folder, String.format("%02d-%s.png", index++, slug(name))));
			}

			SwingUtilities.invokeAndWait(() -> frame.showPage("Overview"));
			capture(frame, () -> {
				AddAnimalDialog d = new AddAnimalDialog(frame, frame);
				d.selectSpecies("Aquarium fish");
				return d;
			}, new File(folder, String.format("%02d-add-animal.png", index++)));
			capture(frame, frame::feedDialog, new File(folder, String.format("%02d-feeding.png", index++)));
			capture(frame, frame::nextYearDialog, new File(folder, String.format("%02d-next-year.png", index++)));
			capture(frame, frame::listenDialog, new File(folder, String.format("%02d-listen.png", index++)));

			SwingUtilities.invokeAndWait(() -> frame.showPage("Overview"));
			Thread.sleep(400);
			save(frame, new File(folder, String.format("%02d-overview-after-a-year.png", index)));
			SwingUtilities.invokeAndWait(frame::dispose);
			System.out.println("Screenshots saved to " + folder.getAbsolutePath());
			System.exit(0);
		}

		private interface DialogFactory {
			JDialog create();
		}

		private static void capture(ZooDashboard frame, DialogFactory factory, File file) throws Exception {
			JDialog[] holder = new JDialog[1];
			SwingUtilities.invokeAndWait(() -> {
				holder[0] = factory.create();
				holder[0].setModalityType(java.awt.Dialog.ModalityType.MODELESS);
				holder[0].setVisible(true);
			});
			Thread.sleep(500);
			save(holder[0], file);
			SwingUtilities.invokeAndWait(holder[0]::dispose);
		}

		private static void save(Window window, File file) throws Exception {
			BufferedImage[] image = new BufferedImage[1];
			SwingUtilities.invokeAndWait(() -> {
				JComponent root = (JComponent) (window instanceof JFrame ? ((JFrame) window).getRootPane()
						: ((JDialog) window).getRootPane());
				BufferedImage img = new BufferedImage(root.getWidth(), root.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g = img.createGraphics();
				root.paint(g);
				g.dispose();
				image[0] = img;
			});
			ImageIO.write(image[0], "png", file);
		}

		private static String slug(String name) {
			return name.toLowerCase().replace("&", "and").replaceAll("[^a-z0-9]+", "-");
		}
	}
}
