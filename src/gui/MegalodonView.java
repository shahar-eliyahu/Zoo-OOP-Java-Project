package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import animals.Megaladon;

/**
 * The prehistoric section of the zoo.
 */
public class MegalodonView extends JPanel implements View {
	private static final long serialVersionUID = 1L;

	private final AppContext app;
	private final DefaultTableModel model = Widgets.model(
			new String[] { "Name", "Age", "Length (m)", "Weight (t)", "Speed (km/h)", "Bite (N)", "Happiness" },
			new Class<?>[] { String.class, Integer.class, Double.class, Double.class, Integer.class, Integer.class,
					Integer.class });

	public MegalodonView(AppContext app) {
		super(new BorderLayout(0, 18));
		this.app = app;
		setOpaque(false);
		add(Widgets.header("Megalodon",
				"Extends AnimalAbstract directly. Eats " + 1134 + " kg a day and lives up to " + Megaladon.LifeSpan
						+ " years."),
				BorderLayout.NORTH);

		JTable table = Widgets.table(model);
		table.getColumnModel().getColumn(6).setCellRenderer(new Widgets.HappinessRenderer());
		table.getColumnModel().getColumn(6).setPreferredWidth(150);

		JPanel grid = new JPanel(new GridLayout(2, 1, 0, 14));
		grid.setOpaque(false);
		grid.add(Widgets.card(null, new SharkArt()));
		grid.add(Widgets.card("Megalodons in the zoo", Widgets.scroll(table)));
		add(grid, BorderLayout.CENTER);
	}

	@Override
	public void refresh() {
		model.setRowCount(0);
		for (Megaladon m : app.zoo().getMegaladon()) {
			model.addRow(new Object[] { m.getName(), m.getAge(), m.getLength(), m.getWeight(), m.getSpeed(),
					m.getPowerOfBite(), m.getHappiness() });
		}
	}

	/** A simple shark silhouette drawn with paths. */
	private static class SharkArt extends JComponent {
		private static final long serialVersionUID = 1L;

		SharkArt() {
			setPreferredSize(new Dimension(600, 220));
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = Widgets.smooth(g.create());
			int w = getWidth();
			int h = getHeight();
			g2.setPaint(new GradientPaint(0, 0, new Color(0x0E3A5C), 0, h, new Color(0x061726)));
			g2.fillRoundRect(0, 0, w, h, 14, 14);

			double s = Math.min(w / 700.0, h / 230.0);
			g2.translate(w / 2.0 - 330 * s, h / 2.0 - 10 * s);
			g2.scale(s, s);

			Path2D body = new Path2D.Double();
			body.moveTo(0, 0);
			body.curveTo(120, -70, 420, -80, 600, -10);
			body.curveTo(640, 0, 660, 20, 600, 30);
			body.curveTo(420, 70, 140, 60, 0, 0);
			body.closePath();
			Path2D fin = new Path2D.Double();
			fin.moveTo(260, -58);
			fin.lineTo(330, -140);
			fin.lineTo(360, -60);
			fin.closePath();
			Path2D tail = new Path2D.Double();
			tail.moveTo(20, 0);
			tail.lineTo(-90, -90);
			tail.lineTo(-50, 0);
			tail.lineTo(-90, 80);
			tail.closePath();
			Path2D pectoral = new Path2D.Double();
			pectoral.moveTo(390, 40);
			pectoral.lineTo(330, 110);
			pectoral.lineTo(450, 50);
			pectoral.closePath();

			g2.setPaint(new GradientPaint(0, -80, new Color(0x9FB4C7), 0, 60, new Color(0x4D6478)));
			g2.fill(tail);
			g2.fill(fin);
			g2.fill(pectoral);
			g2.fill(body);
			g2.setColor(new Color(0xE9EEF2));
			Path2D belly = new Path2D.Double();
			belly.moveTo(150, 30);
			belly.curveTo(300, 60, 480, 55, 600, 30);
			belly.curveTo(470, 40, 300, 42, 150, 30);
			g2.fill(belly);
			g2.setColor(new Color(0x1B2631));
			g2.fill(new Ellipse2D.Double(540, -18, 12, 12));
			for (int i = 0; i < 5; i++) {
				g2.drawLine(470 - i * 14, -10, 465 - i * 14, 20);
			}
			g2.dispose();
		}
	}
}
