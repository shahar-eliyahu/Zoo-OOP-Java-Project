package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * Small reusable Swing components styled with {@link Theme}.
 */
public final class Widgets {
	private Widgets() {
	}

	/** Enables anti-aliasing for shapes and text. */
	public static Graphics2D smooth(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		return g2;
	}

	/** A panel with a rounded, filled background. */
	public static class RoundedPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final Color fill;
		private final int radius;

		public RoundedPanel(LayoutManager layout, Color fill, int radius) {
			super(layout);
			this.fill = fill;
			this.radius = radius;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = smooth(g.create());
			g2.setColor(fill);
			g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.setColor(Theme.BORDER);
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.dispose();
			super.paintComponent(g);
		}
	}

	/** A card wrapping any content, with a title above it. */
	public static JPanel card(String title, JComponent content) {
		RoundedPanel panel = new RoundedPanel(new BorderLayout(0, 10), Theme.SURFACE, 18);
		panel.setBorder(new EmptyBorder(16, 18, 18, 18));
		if (title != null) {
			panel.add(label(title, Theme.SUBHEADING, Theme.TEXT), BorderLayout.NORTH);
		}
		panel.add(content, BorderLayout.CENTER);
		return panel;
	}

	public static JLabel label(String text, Font font, Color color) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		label.setForeground(color);
		return label;
	}

	/** A horizontal bar showing a happiness level between 0 and 100. */
	public static class HappinessBar extends JComponent {
		private static final long serialVersionUID = 1L;
		private int value;
		private final boolean showText;

		public HappinessBar(int value, boolean showText) {
			this.value = value;
			this.showText = showText;
			setPreferredSize(new Dimension(120, 18));
		}

		public void setValue(int value) {
			this.value = value;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			paintBar(smooth(g), value, 0, 0, getWidth(), getHeight(), showText);
		}

		static void paintBar(Graphics2D g2, int value, int x, int y, int width, int height, boolean showText) {
			int clamped = Math.max(0, Math.min(100, value));
			int barHeight = Math.min(8, height);
			int textWidth = showText ? 34 : 0;
			int barWidth = Math.max(10, width - textWidth);
			int barY = y + (height - barHeight) / 2;
			g2.setColor(Theme.SURFACE_HIGH);
			g2.fillRoundRect(x, barY, barWidth, barHeight, barHeight, barHeight);
			g2.setColor(Theme.happinessColor(clamped));
			g2.fillRoundRect(x, barY, (int) (barWidth * clamped / 100.0), barHeight, barHeight, barHeight);
			if (showText) {
				g2.setFont(Theme.SMALL);
				g2.setColor(Theme.TEXT_MUTED);
				FontMetrics fm = g2.getFontMetrics();
				String text = clamped + "%";
				g2.drawString(text, x + width - fm.stringWidth(text), y + (height + fm.getAscent()) / 2 - 2);
			}
		}
	}

	/** Table cell renderer that draws an integer cell as a happiness bar. */
	public static class HappinessRenderer extends JComponent implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private int value;
		private boolean selected;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			this.value = value instanceof Number ? ((Number) value).intValue() : 0;
			this.selected = isSelected;
			return this;
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = smooth(g);
			g2.setColor(selected ? Theme.SURFACE_HIGH : Theme.SURFACE);
			g2.fillRect(0, 0, getWidth(), getHeight());
			HappinessBar.paintBar(g2, value, 10, 0, getWidth() - 20, getHeight(), true);
		}
	}

	/** A read-only table styled for the dark theme. */
	public static JTable table(DefaultTableModel model) {
		JTable table = new JTable(model) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setFont(Theme.BODY);
		table.setRowHeight(32);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(true);
		table.setGridColor(Theme.BORDER);
		table.setBackground(Theme.SURFACE);
		table.setForeground(Theme.TEXT);
		table.setSelectionBackground(Theme.SURFACE_HIGH);
		table.setSelectionForeground(Theme.TEXT);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setIntercellSpacing(new Dimension(0, 1));
		table.setFillsViewportHeight(true);
		table.setFocusable(false);

		DefaultTableCellRenderer cells = new DefaultTableCellRenderer();
		cells.setBorder(new EmptyBorder(0, 10, 0, 10));
		cells.setBackground(Theme.SURFACE);
		cells.setForeground(Theme.TEXT);
		table.setDefaultRenderer(Object.class, cells);
		table.setDefaultRenderer(Number.class, cells);
		table.setDefaultRenderer(Integer.class, cells);
		table.setDefaultRenderer(Double.class, cells);

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(10, 34));
		header.setDefaultRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(t, value, false, false, row, column);
				setFont(Theme.SMALL.deriveFont(Font.BOLD));
				setForeground(Theme.TEXT_MUTED);
				setBackground(Theme.SURFACE_HIGH);
				setHorizontalAlignment(SwingConstants.LEFT);
				setBorder(new EmptyBorder(0, 10, 0, 10));
				setText(value == null ? "" : value.toString().toUpperCase());
				return this;
			}
		});
		return table;
	}

	/** Puts a table in a scroll pane that blends with the dark theme. */
	public static JScrollPane scroll(JComponent component) {
		JScrollPane scroll = new JScrollPane(component);
		scroll.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
		scroll.getViewport().setBackground(Theme.SURFACE);
		scroll.setBackground(Theme.SURFACE);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getHorizontalScrollBar().setUnitIncrement(16);
		return scroll;
	}

	/** A table model whose columns report their value types (needed for numeric renderers). */
	public static DefaultTableModel model(String[] columns, Class<?>[] types) {
		return new DefaultTableModel(columns, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};
	}

	/** Rounded button; primary buttons use the accent color. */
	public static JButton button(String text, boolean primary) {
		JButton button = new JButton(text) {
			private static final long serialVersionUID = 1L;
			private boolean hover;

			{
				addMouseListener(new MouseAdapter() {
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

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = smooth(g.create());
				Color fill = primary ? (hover ? Theme.ACCENT.brighter() : Theme.ACCENT)
						: (hover ? Theme.BORDER : Theme.SURFACE_HIGH);
				if (!isEnabled()) {
					fill = Theme.SURFACE_HIGH;
				}
				g2.setColor(fill);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
				g2.setFont(getFont());
				FontMetrics fm = g2.getFontMetrics();
				g2.setColor(primary ? Theme.BACKGROUND : Theme.TEXT);
				g2.drawString(getText(), (getWidth() - fm.stringWidth(getText())) / 2,
						(getHeight() + fm.getAscent() - fm.getDescent()) / 2);
				g2.dispose();
			}
		};
		button.setFont(Theme.BODY_BOLD);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setMargin(new Insets(0, 0, 0, 0));
		FontMetrics fm = button.getFontMetrics(button.getFont());
		button.setPreferredSize(new Dimension(fm.stringWidth(text) + 36, 38));
		return button;
	}

	/** A small colored pill, for example a pack name or a sorting strategy. */
	public static JComponent pill(String text, Color color) {
		JLabel label = new JLabel(text) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = smooth(g.create());
				g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 40));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
				g2.setColor(color);
				g2.setStroke(new BasicStroke(1f));
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
				g2.dispose();
				super.paintComponent(g);
			}
		};
		label.setFont(Theme.SMALL.deriveFont(Font.BOLD));
		label.setForeground(color);
		label.setBorder(new EmptyBorder(4, 10, 4, 10));
		return label;
	}

	/** A row of components laid out left to right. */
	public static JPanel row(int gap, JComponent... components) {
		JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, gap, 0));
		row.setOpaque(false);
		for (JComponent component : components) {
			row.add(component);
		}
		return row;
	}

	/** A summary card with a big number, a caption and an average happiness bar. */
	public static class StatCard extends RoundedPanel {
		private static final long serialVersionUID = 1L;
		private final JLabel number = label("0", Theme.BIG_NUMBER, Theme.TEXT);
		private final JLabel caption = label("", Theme.SMALL, Theme.TEXT_MUTED);
		private final HappinessBar bar = new HappinessBar(100, true);

		public StatCard(String title, Color color) {
			super(new BorderLayout(0, 6), Theme.SURFACE, 18);
			setBorder(new EmptyBorder(14, 16, 14, 16));
			JPanel top = new JPanel(new BorderLayout());
			top.setOpaque(false);
			JLabel name = label(title, Theme.BODY_BOLD, Theme.TEXT_MUTED);
			name.setIcon(new DotIcon(color));
			name.setIconTextGap(8);
			top.add(name, BorderLayout.WEST);
			add(top, BorderLayout.NORTH);
			add(number, BorderLayout.CENTER);
			JPanel bottom = new JPanel(new BorderLayout(0, 4));
			bottom.setOpaque(false);
			bottom.add(caption, BorderLayout.NORTH);
			bottom.add(bar, BorderLayout.SOUTH);
			add(bottom, BorderLayout.SOUTH);
			setPreferredSize(new Dimension(180, 132));
		}

		public void update(int count, int averageHappiness, String captionText) {
			number.setText(String.valueOf(count));
			caption.setText(captionText);
			bar.setValue(averageHappiness);
		}
	}

	/** A small filled circle used as an icon. */
	public static class DotIcon implements javax.swing.Icon {
		private final Color color;

		public DotIcon(Color color) {
			this.color = color;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = smooth(g.create());
			g2.setColor(color);
			g2.fillOval(x, y + 1, 10, 10);
			// a thin outline keeps very dark colors (black, brown) visible on the dark background
			g2.setColor(new Color(255, 255, 255, 90));
			g2.drawOval(x, y + 1, 10, 10);
			g2.dispose();
		}

		@Override
		public int getIconWidth() {
			return 10;
		}

		@Override
		public int getIconHeight() {
			return 12;
		}
	}

	/** A view's page header: title, short description and optional controls on the right. */
	public static JPanel header(String title, String description, JComponent... controls) {
		JPanel header = new JPanel(new BorderLayout(20, 0));
		header.setOpaque(false);
		JPanel text = new JPanel(new BorderLayout(0, 4));
		text.setOpaque(false);
		text.add(label(title, Theme.HEADING, Theme.TEXT), BorderLayout.NORTH);
		text.add(label(description, Theme.BODY, Theme.TEXT_MUTED), BorderLayout.CENTER);
		header.add(text, BorderLayout.CENTER);
		if (controls.length > 0) {
			JPanel right = row(8, controls);
			header.add(right, BorderLayout.EAST);
		}
		return header;
	}

	/** Formats a gender flag the same way as the console version. */
	public static String gender(boolean male) {
		return male ? "Male" : "Female";
	}
}
