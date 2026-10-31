package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import abstract_classes.PredatoryAnimalsAbstract;

/**
 * Lions and tigers, both built on the shared PredatoryAnimalsAbstract class.
 */
public class PredatorsView extends JPanel implements View {
	private static final long serialVersionUID = 1L;

	private final AppContext app;
	private final DefaultTableModel lions = newModel();
	private final DefaultTableModel tigers = newModel();

	public PredatorsView(AppContext app) {
		super(new BorderLayout(0, 18));
		this.app = app;
		setOpaque(false);
		add(Widgets.header("Lions and Tigers",
				"Both extend PredatoryAnimalsAbstract. Daily food depends on gender, age and weight."),
				BorderLayout.NORTH);
		JPanel grid = new JPanel(new GridLayout(2, 1, 0, 14));
		grid.setOpaque(false);
		grid.add(Widgets.card("Lions", Widgets.scroll(newTable(lions))));
		grid.add(Widgets.card("Tigers", Widgets.scroll(newTable(tigers))));
		add(grid, BorderLayout.CENTER);
	}

	private static DefaultTableModel newModel() {
		return Widgets.model(new String[] { "Name", "Gender", "Age", "Weight (kg)", "Daily food (kg)", "Happiness" },
				new Class<?>[] { String.class, String.class, Integer.class, Double.class, Double.class,
						Integer.class });
	}

	private static JTable newTable(DefaultTableModel model) {
		JTable table = Widgets.table(model);
		table.getColumnModel().getColumn(5).setCellRenderer(new Widgets.HappinessRenderer());
		table.getColumnModel().getColumn(5).setPreferredWidth(180);
		return table;
	}

	private static void fill(DefaultTableModel model, List<? extends PredatoryAnimalsAbstract> animals) {
		model.setRowCount(0);
		for (PredatoryAnimalsAbstract a : animals) {
			model.addRow(new Object[] { a.getName(), Widgets.gender(a.isGender()), a.getAge(), a.getWeight(),
					a.feed(), a.getHappiness() });
		}
	}

	@Override
	public void refresh() {
		fill(lions, app.zoo().getLionsArr());
		fill(tigers, app.zoo().getTigersArr());
	}
}
