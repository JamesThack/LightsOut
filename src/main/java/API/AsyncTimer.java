package API;

import java.util.TimerTask;

import GUI.MainScreen;

public class AsyncTimer extends TimerTask {
	
	private MainScreen menu;
	
	public AsyncTimer(MainScreen menu) {
		this.menu = menu;
		
	}

	@Override
	public void run() {
		if (!menu.isPaused()) menu.setSeconds(menu.getSeconds() + 1);
		
	}
	
}