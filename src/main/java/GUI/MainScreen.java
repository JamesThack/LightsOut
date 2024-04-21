package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import API.Components.Session;
import API.Race.Race;
import APIObjects.RegexAssist;
import GUI.Sections.*;

public class MainScreen {

	private final JFrame frame;
	private JPanel driverContainer;

	private RaceDriverViewSection driverViewTab;
	private final RaceControlSection raceControlSection;
	private final RaceInfoSection raceInfoSection;
	private RaceSelectorSection raceSelectorSection;

	private Session session;

	private static MainScreen instance;

	public static MainScreen getInstance() {
		if (instance == null) instance = new MainScreen(RegexAssist.convertToUnix("06:11:00"));

		return instance;
	}
	

	public MainScreen(int initialSeconds) {
		frame = new JFrame();

		raceSelectorSection = new RaceSelectorSection();
		raceControlSection = new RaceControlSection(initialSeconds);
		raceInfoSection = new RaceInfoSection();

		initialize();
		frame.setVisible(true);
	}
	
	public int getSeconds() {
		return raceControlSection.getSeconds();
	}
	
	public boolean isPaused() {
		return raceControlSection.getIsPaused();
	}

	public void setSeconds(int seconds) {
		raceControlSection.setSeconds(seconds);

		updateWholeScreen();

	}

	public float getZoomAmount() {
		return raceControlSection.getZoomAmount();
	}

	public void setSession(Session session) {
		this.session = session;
		driverContainer.removeAll();
		driverViewTab = new RaceDriverViewSection(this);
		driverContainer.add(driverViewTab.getDriverView());
		driverViewTab.updateDriverView(getSeconds());
	}

	public void updateWholeScreen() {

		if (driverViewTab != null) {
			driverViewTab.updateDriverView(getSeconds());
			raceControlSection.updateView();
		}
	}

	private void initialize() {
		frame.setBounds(0, 0, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		driverContainer = new JPanel();
		panel.add(driverContainer, BorderLayout.CENTER);
		
		
		driverContainer.setLayout(new BorderLayout(0, 0));


		driverContainer.add(raceSelectorSection.getPanel(), BorderLayout.CENTER);
		panel.add(raceControlSection.getPanel(), BorderLayout.SOUTH);
		panel.add(raceInfoSection.getPanel(), BorderLayout.WEST);

	}

	public Session getSession() {
		return session;
	}

	public ActionListener getDriverInformation() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				driverContainer.removeAll();
				DriverInfoSection section = new DriverInfoSection(Integer.parseInt( button.getText().split(" ")[2]));
				driverContainer.add(section.getPanel());
			}
		};
	}

	public ActionListener reopenMainScreen() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				driverContainer.removeAll();
				driverContainer.add(driverViewTab.getDriverView());
				driverViewTab.updateDriverView(getSeconds());
			}
		};
	}
	
}
