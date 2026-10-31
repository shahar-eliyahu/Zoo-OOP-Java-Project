package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import animals.Penguin;
import interfaces.PenguinsLinkedListInterface;

/**
 * Penguins are kept in a custom sorted linked list; the user can switch the sorting strategy.
 */
public class PenguinsView extends JPanel implements View {
	private static final long serialVersionUID = 1L;

	private static final String[] STRATEGIES = { "Name", "Height", "Age" };
	private static final String[] DESCRIPTIONS = { "Sorted by name (A to Z, then a to z)",
			"Sorted by height, the tallest penguin leads the group", "Sorted by age, youngest first" };

	private final AppContext app;
	private final DefaultTableModel model = Widgets.model(
			new String[] { "#", "Name", "Age", "Height (cm)", "Happiness", "Role" },
			new Class<?>[] { Integer.class, String.class, Integer.class, Double.class, Integer.class, String.class });
	private final JButton[] strategyButtons = new JButton[3];
	private final JPanel headerHolder = new JPanel(new BorderLayout());

	public PenguinsView(AppContext app) {
		super(new BorderLayout(0, 18));
		this.app = app;
		setOpaque(false);
		headerHolder.setOpaque(false);
		add(headerHolder, BorderLayout.NORTH);

		JTable table = Widgets.table(model);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(4).setCellRenderer(new Widgets.HappinessRenderer());
		table.getColumnModel().getColumn(4).setPreferredWidth(180);
		add(Widgets.card(null, Widgets.scroll(table)), BorderLayout.CENTER);
	}

	private void rebuildHeader(int current) {
		JComponent[] controls = new JComponent[STRATEGIES.length + 1];
		controls[0] = Widgets.label("Sort by:", Theme.BODY, Theme.TEXT_MUTED);
		for (int i = 0; i < STRATEGIES.length; i++) {
			int strategy = i + 1;
			strategyButtons[i] = Widgets.button(STRATEGIES[i], strategy == current);
			strategyButtons[i].addActionListener(e -> {
				app.zoo().penguinSortWay(strategy);
				app.log("Penguins re-sorted by " + STRATEGIES[strategy - 1].toLowerCase() + ".");
				app.refreshAll();
			});
			controls[i + 1] = strategyButtons[i];
		}
		headerHolder.removeAll();
		headerHolder.add(Widgets.header("Penguins", DESCRIPTIONS[current - 1] + ". Stored in a custom linked list.",
				controls));
		headerHolder.revalidate();
		headerHolder.repaint();
	}

	@Override
	public void refresh() {
		PenguinsLinkedListInterface list = app.zoo().getPenguinHord();
		int strategy = list.getSortFormPenguin();
		rebuildHeader(strategy);
		model.setRowCount(0);
		for (int i = 0; i < list.countPenguinsInList(); i++) {
			Penguin p = list.findByIndex(i);
			String role = (strategy == 2 && i == 0) ? "Leader" : "";
			model.addRow(new Object[] { i + 1, p.getName(), p.getAge(), p.getHeight(), p.getHappiness(), role });
		}
	}
}
