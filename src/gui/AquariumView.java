package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import abstract_classes.FishAbstract;
import animals.AquariumFish;
import animals.ClownFish;
import animals.GoldFish;
import enums.BodySignatureEnum;
import enums.FishColorEnum;
import zoo.ExceptionZoo;

/**
 * An animated aquarium: every fish in the zoo's fish list is drawn with its own colors, pattern and size.
 */
public class AquariumView extends JPanel implements View {
	private static final long serialVersionUID = 1L;
	private static final int MAX_DRAWN_FISH = 80;

	private final AppContext app;
	private final Tank tank = new Tank();
	private final DefaultTableModel model = Widgets.model(
			new String[] { "Type", "Colors", "Pattern", "Age", "Length (cm)", "Daily food", "Happiness" },
			new Class<?>[] { String.class, String.class, String.class, Integer.class, String.class, String.class,
					Integer.class });

	public AquariumView(AppContext app) {
		super(new BorderLayout(0, 18));
		this.app = app;
		setOpaque(false);
		JButton addRandom = Widgets.button("Add 10 random fish", true);
		addRandom.addActionListener(e -> {
			try {
				app.zoo().addNumbrOfFish(10);
				app.log("10 random fish were added to the aquarium.");
			} catch (ExceptionZoo ex) {
				app.log("Could not add fish: " + ex.getTheIssueTitle());
			}
			app.refreshAll();
		});
		add(Widgets.header("Aquarium",
				"Aquarium, gold and clown fish all extend FishAbstract. Colors and patterns come from the enums.",
				addRandom), BorderLayout.NORTH);

		JTable table = Widgets.table(model);
		table.getColumnModel().getColumn(6).setCellRenderer(new Widgets.HappinessRenderer());
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(260);

		JPanel center = new JPanel(new BorderLayout(0, 14));
		center.setOpaque(false);
		JPanel tankCard = Widgets.card(null, tank);
		tankCard.setPreferredSize(new Dimension(800, 360));
		center.add(tankCard, BorderLayout.NORTH);
		center.add(Widgets.card("All fish", Widgets.scroll(table)), BorderLayout.CENTER);
		add(center, BorderLayout.CENTER);
	}

	@Override
	public void refresh() {
		List<FishAbstract> fish = app.zoo().getFishArr();
		model.setRowCount(0);
		for (FishAbstract f : fish) {
			StringBuilder colors = new StringBuilder();
			for (FishColorEnum c : f.getColor()) {
				if (colors.length() > 0) {
					colors.append(", ");
				}
				colors.append(c.name().toLowerCase());
			}
			model.addRow(new Object[] { typeName(f), colors.toString(), f.getSignature().name().toLowerCase(),
					f.getAge(), f.formatter.format(f.getLength()), f.formatter.format(f.feed()), f.getHappiness() });
		}
		tank.sync(fish);
	}

	private static String typeName(FishAbstract f) {
		if (f instanceof ClownFish) {
			return "Clown fish";
		} else if (f instanceof GoldFish) {
			return "Gold fish";
		}
		return "Aquarium fish";
	}

	/** The animated tank. */
	private static class Tank extends JComponent {
		private static final long serialVersionUID = 1L;

		private static class Swimmer {
			double x;
			double y;
			double speed;
			double phase;
			boolean right;
		}

		private static class Bubble {
			double x;
			double y;
			double r;
			double speed;
		}

		private final Random random = new Random(7);
		private final Map<FishAbstract, Swimmer> swimmers = new IdentityHashMap<>();
		private final List<FishAbstract> drawn = new ArrayList<>();
		private final List<Bubble> bubbles = new ArrayList<>();
		private int totalFish;
		private double time;

		Tank() {
			setPreferredSize(new Dimension(800, 320));
			for (int i = 0; i < 26; i++) {
				bubbles.add(newBubble(true));
			}
			Timer timer = new Timer(33, e -> step());
			timer.start();
		}

		void sync(List<FishAbstract> fish) {
			totalFish = fish.size();
			drawn.clear();
			for (int i = 0; i < fish.size() && i < MAX_DRAWN_FISH; i++) {
				FishAbstract f = fish.get(i);
				drawn.add(f);
				swimmers.computeIfAbsent(f, k -> newSwimmer());
			}
			swimmers.keySet().retainAll(drawn);
			repaint();
		}

		private Swimmer newSwimmer() {
			Swimmer s = new Swimmer();
			s.x = random.nextDouble();
			s.y = 0.12 + random.nextDouble() * 0.66;
			s.speed = 0.0007 + random.nextDouble() * 0.0016;
			s.phase = random.nextDouble() * Math.PI * 2;
			s.right = random.nextBoolean();
			return s;
		}

		private Bubble newBubble(boolean anywhere) {
			Bubble b = new Bubble();
			b.x = random.nextDouble();
			b.y = anywhere ? random.nextDouble() : 1.05;
			b.r = 1.5 + random.nextDouble() * 4;
			b.speed = 0.002 + random.nextDouble() * 0.004;
			return b;
		}

		private void step() {
			time += 0.033;
			for (Map.Entry<FishAbstract, Swimmer> entry : swimmers.entrySet()) {
				Swimmer s = entry.getValue();
				// sad fish swim more slowly
				double mood = entry.getKey().getHappiness() < 40 ? 0.45 : 1.0;
				s.x += (s.right ? 1 : -1) * s.speed * mood;
				if (s.x > 1.04) {
					s.right = false;
				} else if (s.x < -0.04) {
					s.right = true;
				}
			}
			for (int i = 0; i < bubbles.size(); i++) {
				Bubble b = bubbles.get(i);
				b.y -= b.speed;
				if (b.y < -0.05) {
					bubbles.set(i, newBubble(false));
				}
			}
			if (isShowing()) {
				repaint();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = Widgets.smooth(g.create());
			int w = getWidth();
			int h = getHeight();
			g2.setClip(new java.awt.geom.RoundRectangle2D.Double(0, 0, w, h, 14, 14));
			g2.setPaint(new GradientPaint(0, 0, new Color(0x1C6EA4), 0, h, new Color(0x0A2A43)));
			g2.fillRect(0, 0, w, h);

			// light rays
			g2.setColor(new Color(255, 255, 255, 14));
			for (int i = 0; i < 5; i++) {
				int x = (int) (w * (0.1 + i * 0.2) + Math.sin(time * 0.4 + i) * 20);
				g2.fillPolygon(new Polygon(new int[] { x - 18, x + 18, x + 90, x - 30 }, new int[] { 0, 0, h, h }, 4));
			}

			// sand and plants
			int sandTop = h - 34;
			g2.setPaint(new GradientPaint(0, sandTop, new Color(0xC9A86A), 0, h, new Color(0x8C6E3C)));
			Path2D sand = new Path2D.Double();
			sand.moveTo(0, h);
			for (int x = 0; x <= w; x += 20) {
				sand.lineTo(x, sandTop + Math.sin(x * 0.02) * 5);
			}
			sand.lineTo(w, h);
			sand.closePath();
			g2.fill(sand);
			for (int i = 0; i < 9; i++) {
				drawPlant(g2, (int) (w * (0.05 + i * 0.115)), sandTop + 6, 50 + (i * 37) % 60, i);
			}

			// bubbles
			g2.setStroke(new BasicStroke(1.2f));
			for (Bubble b : bubbles) {
				g2.setColor(new Color(255, 255, 255, 90));
				g2.draw(new Ellipse2D.Double(b.x * w + Math.sin(time * 2 + b.r) * 3, b.y * h, b.r * 2, b.r * 2));
			}

			// fish
			for (FishAbstract f : drawn) {
				Swimmer s = swimmers.get(f);
				if (s != null) {
					double y = s.y * (sandTop - 30) + Math.sin(time * 1.5 + s.phase) * 4;
					drawFish(g2, f, s.x * w, y, s.right);
				}
			}

			g2.setClip(null);
			g2.setFont(Theme.SMALL);
			g2.setColor(new Color(255, 255, 255, 170));
			String caption = totalFish == 0 ? "The aquarium is empty"
					: totalFish > MAX_DRAWN_FISH ? "Showing " + MAX_DRAWN_FISH + " of " + totalFish + " fish"
							: totalFish + " fish swimming";
			g2.drawString(caption, 14, 20);
			g2.dispose();
		}

		private void drawPlant(Graphics2D g2, int baseX, int baseY, int height, int seed) {
			g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			for (int blade = 0; blade < 3; blade++) {
				Path2D path = new Path2D.Double();
				path.moveTo(baseX + blade * 6, baseY);
				for (int k = 1; k <= 6; k++) {
					double sway = Math.sin(time * 1.2 + k * 0.6 + seed + blade) * k * 1.6;
					path.lineTo(baseX + blade * 6 + sway, baseY - height * k / 6.0 * (0.7 + blade * 0.15));
				}
				g2.setColor(blade == 1 ? new Color(0x2E8B57) : new Color(0x3CB371));
				g2.draw(path);
			}
		}

		private static double sizeOf(FishAbstract f) {
			if (f instanceof ClownFish) {
				return 20 + f.getLength() / ClownFish.RANGE_LENGTH_RANDOM_CLOWN_FISH * 8;
			} else if (f instanceof GoldFish) {
				return 22 + f.getLength() / GoldFish.RANGE_LENGTH_RANDOM_GOLD_FISH * 14;
			}
			return 24 + Math.log1p(f.getLength()) / Math.log1p(AquariumFish.RANGE_LENGTH_RANDOM_FISH) * 22;
		}

		private void drawFish(Graphics2D g, FishAbstract f, double cx, double cy, boolean right) {
			double h = sizeOf(f);
			double w = h * 1.8;
			Graphics2D g2 = (Graphics2D) g.create();
			AffineTransform at = new AffineTransform();
			at.translate(cx, cy);
			if (!right) {
				at.scale(-1, 1);
			}
			g2.transform(at);

			FishColorEnum[] colors = f.getColor();
			Color main = Theme.fishColor(f instanceof ClownFish ? FishColorEnum.ORANGE : colors[0]);

			// tail and fin
			double wag = Math.sin(time * 8 + cx * 0.05) * h * 0.12;
			Path2D tail = new Path2D.Double();
			tail.moveTo(-w * 0.42, 0);
			tail.lineTo(-w * 0.78, -h * 0.48 + wag);
			tail.lineTo(-w * 0.70, wag * 0.3);
			tail.lineTo(-w * 0.78, h * 0.48 + wag);
			tail.closePath();
			g2.setColor(main.darker());
			g2.fill(tail);
			Path2D fin = new Path2D.Double();
			fin.moveTo(-w * 0.15, -h * 0.42);
			fin.quadTo(0, -h * 0.85, w * 0.15, -h * 0.4);
			fin.closePath();
			g2.fill(fin);

			// body with the fish's colors as bands
			Shape body = new Ellipse2D.Double(-w / 2, -h / 2, w, h);
			Shape oldClip = g2.getClip();
			g2.clip(body);
			if (f instanceof ClownFish) {
				g2.setColor(main);
				g2.fill(body);
				for (double bx : new double[] { -0.22, 0.14 }) {
					g2.setColor(Theme.fishColor(FishColorEnum.BLACK));
					g2.fillRect((int) (bx * w - 1), (int) (-h), (int) (w * 0.13 + 2), (int) (h * 2));
					g2.setColor(Theme.fishColor(FishColorEnum.WHITE));
					g2.fillRect((int) (bx * w + 2), (int) (-h), (int) (w * 0.13 - 4), (int) (h * 2));
				}
			} else {
				double bandWidth = w / colors.length;
				for (int i = 0; i < colors.length; i++) {
					g2.setColor(Theme.fishColor(colors[i]));
					g2.fill(new java.awt.geom.Rectangle2D.Double(-w / 2 + i * bandWidth, -h / 2, bandWidth + 1, h));
				}
				drawPattern(g2, f.getSignature(), w, h);
			}
			// soft shading
			g2.setPaint(new GradientPaint(0, (float) (-h / 2), new Color(255, 255, 255, 70), 0, (float) (h / 2),
					new Color(0, 0, 0, 70)));
			g2.fill(body);
			g2.setClip(oldClip);
			g2.setColor(new Color(0, 0, 0, 110));
			g2.setStroke(new BasicStroke(1f));
			g2.draw(body);

			// eye
			g2.setColor(Color.WHITE);
			g2.fill(new Ellipse2D.Double(w * 0.24, -h * 0.18, h * 0.24, h * 0.24));
			g2.setColor(Color.BLACK);
			g2.fill(new Ellipse2D.Double(w * 0.29, -h * 0.12, h * 0.12, h * 0.12));
			g2.dispose();
		}

		private static void drawPattern(Graphics2D g2, BodySignatureEnum signature, double w, double h) {
			g2.setColor(new Color(0, 0, 0, 70));
			switch (signature) {
			case STRIPES:
				for (double x = -w / 2 + w * 0.12; x < w / 2; x += w * 0.16) {
					g2.fill(new java.awt.geom.Rectangle2D.Double(x, -h / 2, w * 0.05, h));
				}
				break;
			case SPOTS:
				for (int i = 0; i < 6; i++) {
					double x = -w * 0.3 + (i % 3) * w * 0.2;
					double y = -h * 0.2 + (i / 3) * h * 0.3;
					g2.fill(new Ellipse2D.Double(x, y, h * 0.2, h * 0.2));
				}
				break;
			case POINTS:
				for (double x = -w * 0.35; x < w * 0.35; x += w * 0.1) {
					for (double y = -h * 0.3; y < h * 0.3; y += h * 0.2) {
						g2.fill(new Ellipse2D.Double(x, y, 2.5, 2.5));
					}
				}
				break;
			default:
				break;
			}
		}
	}
}
