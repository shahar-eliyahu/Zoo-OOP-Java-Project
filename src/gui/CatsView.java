package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import animals.Cat;
import interfaces.CatTreeNode;

/**
 * Cats are stored in a binary search tree keyed by age. The tree is drawn exactly as it is stored.
 */
public class CatsView extends JPanel implements View {
	private static final long serialVersionUID = 1L;

	private final AppContext app;
	private final DefaultTableModel model = Widgets.model(new String[] { "Name", "Age", "Weight", "Happiness" },
			new Class<?>[] { String.class, Integer.class, Double.class, Integer.class });
	private final TreeCanvas tree = new TreeCanvas();
	private final JLabel treeInfo = Widgets.label("", Theme.SMALL, Theme.TEXT_MUTED);

	public CatsView(AppContext app) {
		super(new BorderLayout(0, 18));
		this.app = app;
		setOpaque(false);
		add(Widgets.header("Cats",
				"A custom binary search tree keyed by age. The list on the left is an in-order traversal of the tree."),
				BorderLayout.NORTH);
		JTable table = Widgets.table(model);
		table.getColumnModel().getColumn(3).setCellRenderer(new Widgets.HappinessRenderer());
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);

		JPanel treeBody = new JPanel(new BorderLayout(0, 8));
		treeBody.setOpaque(false);
		treeBody.add(treeInfo, BorderLayout.NORTH);
		treeBody.add(tree, BorderLayout.CENTER);

		JPanel grid = new JPanel(new GridBagLayout());
		grid.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.weightx = 0.36;
		c.insets = new Insets(0, 0, 0, 14);
		JPanel listCard = Widgets.card("In-order traversal", Widgets.scroll(table));
		listCard.setPreferredSize(new Dimension(300, 100));
		grid.add(listCard, c);
		c.weightx = 0.64;
		c.insets = new Insets(0, 0, 0, 0);
		JPanel treeCard = Widgets.card("The tree as stored", treeBody);
		treeCard.setPreferredSize(new Dimension(520, 100));
		grid.add(treeCard, c);
		add(grid, BorderLayout.CENTER);
	}

	@Override
	public void refresh() {
		model.setRowCount(0);
		List<Cat> cats = app.zoo().getCats().getCatsInOrder();
		for (Cat c : cats) {
			model.addRow(new Object[] { c.getName(), c.getAge(), c.getWeight(), c.getHappiness() });
		}
		tree.setRoot(app.zoo().getCats().getTreeSnapshot());
		treeInfo.setText(cats.size() + " nodes, depth " + tree.depth()
				+ ". The shape depends on the order the cats were added; the tree is not self-balancing.");
	}

	/**
	 * Draws the binary tree scaled to fit the panel: x position from the in-order index, y position from the depth.
	 */
	private static class TreeCanvas extends JComponent {
		private static final long serialVersionUID = 1L;
		private static final int MARGIN = 6;

		private CatTreeNode root;
		private final Map<CatTreeNode, int[]> positions = new HashMap<>();
		private int columns;
		private int levels;

		TreeCanvas() {
			setPreferredSize(new Dimension(400, 300));
		}

		void setRoot(CatTreeNode root) {
			this.root = root;
			positions.clear();
			columns = 0;
			levels = 0;
			layout(root, 0);
			repaint();
		}

		int depth() {
			return levels;
		}

		private void layout(CatTreeNode node, int level) {
			if (node == null) {
				return;
			}
			layout(node.left(), level + 1);
			positions.put(node, new int[] { columns++, level });
			levels = Math.max(levels, level + 1);
			layout(node.right(), level + 1);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = Widgets.smooth(g.create());
			if (root == null) {
				g2.setColor(Theme.TEXT_MUTED);
				g2.setFont(Theme.BODY);
				g2.drawString("No cats in the zoo.", MARGIN, 24);
				g2.dispose();
				return;
			}
			double colStep = (getWidth() - 2.0 * MARGIN) / columns;
			double rowStep = (getHeight() - 2.0 * MARGIN) / levels;
			int nodeW = (int) Math.min(96, colStep - 4);
			int nodeH = (int) Math.min(40, rowStep - 10);
			Layout layout = new Layout(colStep, rowStep, nodeW, nodeH);

			g2.setStroke(new BasicStroke(1.5f));
			g2.setColor(Theme.BORDER);
			drawEdges(g2, root, layout);
			drawNodes(g2, root, layout);
			g2.dispose();
		}

		private static class Layout {
			final double colStep;
			final double rowStep;
			final int nodeW;
			final int nodeH;

			Layout(double colStep, double rowStep, int nodeW, int nodeH) {
				this.colStep = colStep;
				this.rowStep = rowStep;
				this.nodeW = nodeW;
				this.nodeH = nodeH;
			}
		}

		private int cx(CatTreeNode node, Layout l) {
			return (int) (MARGIN + positions.get(node)[0] * l.colStep + l.colStep / 2);
		}

		private int top(CatTreeNode node, Layout l) {
			return (int) (MARGIN + positions.get(node)[1] * l.rowStep + (l.rowStep - l.nodeH) / 2);
		}

		private void drawEdges(Graphics2D g2, CatTreeNode node, Layout l) {
			for (CatTreeNode child : new CatTreeNode[] { node.left(), node.right() }) {
				if (child != null) {
					g2.drawLine(cx(node, l), top(node, l) + l.nodeH, cx(child, l), top(child, l));
					drawEdges(g2, child, l);
				}
			}
		}

		private void drawNodes(Graphics2D g2, CatTreeNode node, Layout l) {
			if (node == null) {
				return;
			}
			int x = cx(node, l) - l.nodeW / 2;
			int y = top(node, l);
			Cat cat = node.cat();
			g2.setColor(Theme.SURFACE_HIGH);
			g2.fillRoundRect(x, y, l.nodeW, l.nodeH, 10, 10);
			g2.setColor(node == root ? Theme.ACCENT : Theme.CAT);
			g2.drawRoundRect(x, y, l.nodeW, l.nodeH, 10, 10);

			float size = l.nodeW >= 70 ? 12f : l.nodeW >= 46 ? 10.5f : 9f;
			g2.setFont(Theme.SMALL.deriveFont(Font.BOLD, size));
			FontMetrics fm = g2.getFontMetrics();
			g2.setColor(Theme.TEXT);
			String name = cat.getName();
			int textY = y + l.nodeH / 2 - 1;
			g2.drawString(name, x + (l.nodeW - fm.stringWidth(name)) / 2, textY);
			g2.setFont(Theme.SMALL.deriveFont(size - 1));
			fm = g2.getFontMetrics();
			g2.setColor(Theme.TEXT_MUTED);
			String age = l.nodeW >= 60 ? "age " + cat.getAge() : String.valueOf(cat.getAge());
			g2.drawString(age, x + (l.nodeW - fm.stringWidth(age)) / 2, textY + fm.getAscent() + 1);
			drawNodes(g2, node.left(), l);
			drawNodes(g2, node.right(), l);
		}
	}
}
