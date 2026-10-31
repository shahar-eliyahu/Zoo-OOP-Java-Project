package gui;

import interfaces.ZooInterface;

/**
 * What every screen needs from the main window: the zoo, a way to log events and a way to refresh all screens.
 */
public interface AppContext {
	ZooInterface zoo();

	void log(String message);

	void refreshAll();
}
