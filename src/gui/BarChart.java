package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * A simple horizontal bar chart drawn with Java2D.
 */
public class BarChart extends JComponent {
	private static final long serialVersionUID = 1L;

	private static class Bar {
		final String label;
		final double value;
		final Color color;

		Bar(String label, double value, Color color) {
			this.label = label;
			this.value = value;
			this.color = color;
		}
	}

	private final List<Bar> bars = new ArrayList<>();
	private final String unit;
	private final double fixedMax;

	/**
	 * @param unit     suffix shown after each value, for example "%"
	 * @param fixedMax the value of a full bar, or 0 to scale to the largest value
	 */
	public BarChart(String unit, double fixedMax) {
		this.unit = unit;
		this.fixedMax = fixedMax;
		setPreferredSize(new Dimension(360, 240));
	}

	public void clear() {
		bars.clear();
	}

	public void add(String label, double value, Color color) {
		bars.add(new Bar(label, value, color));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = Widgets.smooth(g.create());
		if (bars.isEmpty()) {
			g2.dispose();
			return;
		}
		double max = fixedMax;
		if (max <= 0) {
			for (Bar bar : bars) {
				max = Math.max(max, bar.value);
			}
			if (max <= 0) {
				max = 1;
			}
		}
		g2.setFont(Theme.BODY);
		FontMetrics fm = g2.getFontMetrics();
		int labelWidth = 0;
		for (Bar bar : bars) {
			labelWidth = Math.max(labelWidth, fm.stringWidth(bar.label));
		}
		labelWidth += 14;
		int valueWidth = fm.stringWidth("000" + unit) + 12;
		int rowHeight = Math.max(22, getHeight() / bars.size());
		int barHeight = Math.min(18, rowHeight - 10);
		int trackWidth = Math.max(20, getWidth() - labelWidth - valueWidth);

		for (int i = 0; i < bars.size(); i++) {
			Bar bar = bars.get(i);
			int y = i * rowHeight + (rowHeight - barHeight) / 2;
			g2.setColor(Theme.TEXT_MUTED);
			g2.drawString(bar.label, 0, y + (barHeight + fm.getAscent()) / 2 - 2);
			g2.setColor(Theme.SURFACE_HIGH);
			g2.fillRoundRect(labelWidth, y, trackWidth, barHeight, barHeight, barHeight);
			int width = (int) Math.round(trackWidth * Math.min(1.0, bar.value / max));
			if (bar.value > 0) {
				g2.setColor(bar.color);
				g2.fillRoundRect(labelWidth, y, Math.max(barHeight, width), barHeight, barHeight, barHeight);
			}
			g2.setColor(Theme.TEXT);
			String text = (bar.value == Math.rint(bar.value) ? String.valueOf((long) bar.value)
					: String.format("%.1f", bar.value)) + unit;
			g2.drawString(text, labelWidth + trackWidth + 10, y + (barHeight + fm.getAscent()) / 2 - 2);
		}
		g2.dispose();
	}
}
