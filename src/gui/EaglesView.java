package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import animals.Eagle;
import enums.EagleTypesEnum;
import interfaces.EagleLinkedListInterface;

/**
 * Eagles live in three packs, one linked list per type, each ordered by a chain of comparators.
 */
public class EaglesView extends JPanel implements View {
	private static final long serialVersionUID = 1L;

	private final AppContext app;
	private final DefaultTableModel[] models = new DefaultTableModel[EagleTypesEnum.values().length];

	public EaglesView(AppContext app) {
		super(new BorderLayout(0, 18));
		this.app = app;
		setOpaque(false);
		add(Widgets.header("Eagles",
				"Three packs, one linked list per type. Each pack is ordered by speed, then age, then top altitude."),
				BorderLayout.NORTH);
		JPanel grid = new JPanel(new GridLayout(3, 1, 0, 14));
		grid.setOpaque(false);
		for (EagleTypesEnum type : EagleTypesEnum.values()) {
			DefaultTableModel model = Widgets.model(
					new String[] { "#", "Name", "Speed (km/h)", "Age", "Top altitude (m)", "Weight (kg)", "Happiness" },
					new Class<?>[] { Integer.class, String.class, Integer.class, Integer.class, Integer.class,
							Double.class, Integer.class });
			models[type.ordinal()] = model;
			JTable table = Widgets.table(model);
			table.setRowHeight(26);
			table.getTableHeader().setPreferredSize(new java.awt.Dimension(10, 28));
			table.getColumnModel().getColumn(0).setMaxWidth(50);
			table.getColumnModel().getColumn(6).setCellRenderer(new Widgets.HappinessRenderer());
			table.getColumnModel().getColumn(6).setPreferredWidth(170);
			String title = type.name().charAt(0) + type.name().substring(1).toLowerCase() + " eagle pack";
			grid.add(Widgets.card(title, Widgets.scroll(table)));
		}
		add(grid, BorderLayout.CENTER);
	}

	@Override
	public void refresh() {
		for (EagleTypesEnum type : EagleTypesEnum.values()) {
			DefaultTableModel model = models[type.ordinal()];
			model.setRowCount(0);
			EagleLinkedListInterface pack = app.zoo().getEaglePack(type);
			for (int i = 0; i < pack.countEaglesInList(); i++) {
				Eagle e = pack.findByIndex(i);
				model.addRow(new Object[] { i + 1, e.getName(), e.getSpeed(), e.getAge(), e.getTopFlyAltitude(),
						e.getWeight(), e.getHappiness() });
			}
		}
	}
}
