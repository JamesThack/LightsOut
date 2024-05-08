package API;

import java.util.TimerTask;

import GUI.MainScreen;

public class AsyncTimer extends TimerTask {
	
	private final MainScreen menu;

	private int lapsAndPositions;
	private int raceInfo;
	
	public AsyncTimer(MainScreen menu) {
		this.menu = menu;

		lapsAndPositions = 30;
		raceInfo = 45;
		
	}

	@Override
	public void run() {
		if (!menu.isPaused()) {
			menu.setSeconds(menu.getSeconds() + 1);
			menu.getSpeechHandler().checkSpeech();

		}

		lapsAndPositions -=1;
		raceInfo -=1;

		if (lapsAndPositions < 0) {
			lapsAndPositions = 6;
			menu.updatePositions();
		}

		if (raceInfo <0) {
			raceInfo = 30;
			menu.updateSafety();
		}

	}
	
}