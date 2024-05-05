package API;

import java.util.TimerTask;

import GUI.MainScreen;

public class AsyncTimer extends TimerTask {
	
	private final MainScreen menu;

	private int lapsAndPositions;
	private int raceInfo;
	
	public AsyncTimer(MainScreen menu) {
		this.menu = menu;

		lapsAndPositions = 15;
		raceInfo = 30;
		
	}

	@Override
	public void run() {
		if (!menu.isPaused()) {
			menu.setSeconds(menu.getSeconds() + 1);
			menu.getSpeechHandler().checkSpeech();

			lapsAndPositions -=1;
			raceInfo -=1;

			if (lapsAndPositions < 0) {
				lapsAndPositions = 30;
				menu.updatePositions();
			}

			if (raceInfo <0) {
				raceInfo = 30;
				menu.updateSafety();
			}



		}
	}
	
}