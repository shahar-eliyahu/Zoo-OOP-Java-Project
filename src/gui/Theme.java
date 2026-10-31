package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import enums.FishColorEnum;

/**
 * Colors and fonts shared by every screen of the graphical interface.
 */
public final class Theme {
	private Theme() {
	}

	public static final Color BACKGROUND = new Color(0x0F1720);
	public static final Color SIDEBAR = new Color(0x0B1218);
	public static final Color SURFACE = new Color(0x17222D);
	public static final Color SURFACE_HIGH = new Color(0x1F2D3A);
	public static final Color BORDER = new Color(0x2A3A49);
	public static final Color TEXT = new Color(0xE6EDF3);
	public static final Color TEXT_MUTED = new Color(0x8FA3B5);
	public static final Color ACCENT = new Color(0x3DD68C);
	public static final Color ACCENT_DARK = new Color(0x1F8F5A);
	public static final Color WARNING = new Color(0xF2B33D);
	public static final Color DANGER = new Color(0xF0606A);
	public static final Color INFO = new Color(0x5AB0F5);

	/** One color per animal group, used in charts and badges. */
	public static final Color PENGUIN = new Color(0x7CC4FA);
	public static final Color LION = new Color(0xF2B33D);
	public static final Color TIGER = new Color(0xF28C38);
	public static final Color EAGLE = new Color(0xB48EF5);
	public static final Color CAT = new Color(0xF27BB0);
	public static final Color FISH = new Color(0x3DD6C7);
	public static final Color MEGALODON = new Color(0x8FA3B5);

	private static final String FAMILY = pickFamily();

	public static final Font TITLE = new Font(FAMILY, Font.BOLD, 26);
	public static final Font HEADING = new Font(FAMILY, Font.BOLD, 18);
	public static final Font SUBHEADING = new Font(FAMILY, Font.BOLD, 14);
	public static final Font BODY = new Font(FAMILY, Font.PLAIN, 13);
	public static final Font BODY_BOLD = new Font(FAMILY, Font.BOLD, 13);
	public static final Font SMALL = new Font(FAMILY, Font.PLAIN, 11);
	public static final Font BIG_NUMBER = new Font(FAMILY, Font.BOLD, 30);
	public static final Font MONO = new Font(Font.MONOSPACED, Font.PLAIN, 12);

	private static String pickFamily() {
		Set<String> available = new HashSet<>();
		try {
			available.addAll(
					Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
		} catch (Exception ignored) {
			// fall back to the logical font below
		}
		for (String family : new String[] { "Segoe UI", "Inter", "Helvetica Neue", "Roboto", "DejaVu Sans" }) {
			if (available.contains(family)) {
				return family;
			}
		}
		return Font.SANS_SERIF;
	}

	/** Maps the project's fish color enum to a screen color. */
	public static Color fishColor(FishColorEnum color) {
		switch (color) {
		case BLACK:
			return new Color(0x2B2B2B);
		case WHITE:
			return new Color(0xF4F4F4);
		case GREEN:
			return new Color(0x4CAF50);
		case ORANGE:
			return new Color(0xFF8C1A);
		case BLUE:
			return new Color(0x2F7BEF);
		case YELLOW:
			return new Color(0xFFD93B);
		case BROWN:
			return new Color(0x8B5A2B);
		case GOLD:
			return new Color(0xE8B923);
		case RED:
			return new Color(0xE53935);
		default:
			return new Color(0x22D3EE);
		}
	}

	/** Green for happy animals, amber in the middle, red when the animal is close to leaving. */
	public static Color happinessColor(int happiness) {
		if (happiness >= 70) {
			return ACCENT;
		} else if (happiness >= 40) {
			return WARNING;
		}
		return DANGER;
	}
}
