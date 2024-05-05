package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import API.AccountHandler;
import API.Components.Session;
import API.Race.Flags;
import API.Race.Race;
import API.Race.SafetyCar;
import API.Race.Tyres;
import API.Speech;
import APIObjects.RegexAssist;
import GUI.Components.BackgroundFrame;
import GUI.Pages.AccountTab;
import GUI.Pages.SignIn;
import GUI.Sections.*;

public class MainScreen {

	private final JFrame frame;
	private JPanel driverContainer;

	private BackgroundFrame controllerContainer;

	private RaceDriverViewSection driverViewTab;
	private RaceControlSection raceControlSection;
	private final RaceInfoSection raceInfoSection;
	private SignIn signIn;
	private RaceSelectorSection raceSelectorSection;
	private BackgroundFrame accountTabPanel;
	private AccountTab accountTab;
	private Flags flags;
	private SafetyCar safetyCar;

	private boolean isLive;

	private Session session;

	private Tyres tyreHandler;

	private static MainScreen instance;

	private Speech speechHandler;

	public static MainScreen getInstance() {
		if (instance == null) instance = new MainScreen(RegexAssist.convertToUnix("06:11:00"));

		return instance;
	}
	

	public MainScreen(int initialSeconds) {
		frame = new JFrame();

		accountTabPanel = new BackgroundFrame("Art/SignIn.png");

		raceControlSection = new RaceControlSection(this);
		raceSelectorSection = new RaceSelectorSection();
		raceInfoSection = new RaceInfoSection(this);
		accountTab = new AccountTab(this, accountTabPanel);
		signIn = new SignIn(accountTabPanel);

		speechHandler = new Speech();

		isLive = false;

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

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean live) {
		isLive = live;
	}

	public float getZoomAmount() {
		return raceControlSection.getZoomAmount();
	}

	public void setSession(Session session) {
		this.session = session;
		tyreHandler = new Tyres(session);
		driverContainer.removeAll();
		driverViewTab = new RaceDriverViewSection(this);
		driverContainer.add(driverViewTab.getDriverView());
		driverViewTab.updateDriverView(getSeconds());
		controllerContainer.remove(raceControlSection.getPanel());
		raceControlSection = new RaceControlSection(this);
		controllerContainer.add(raceControlSection.getPanel(), BorderLayout.SOUTH);
		flags = new Flags(session.getSessionKey());
		safetyCar = new SafetyCar(session.getSessionKey());
		raceInfoSection.addComponents();
	}

	public void updatePositions() {

		System.out.println("Updating positions");

		driverViewTab.getDriverPositions().refreshData();
		raceControlSection.getLapCalculator().refreshData();
	}

	public void updateSafety() {

		System.out.println("Updating safety");

		tyreHandler.refreshData();
		safetyCar.refreshData();
		flags.refreshData();
	}

	public SafetyCar getSafetyCar() {
		return safetyCar;
	}

	public Flags getFlags() {
		return flags;
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

		signIn.refreshPanel();
		
		controllerContainer = new BackgroundFrame("/home/james/Documents/LightsOutBackground.png");
		tabbedPane.addTab("Race Overview", null, controllerContainer, null);
		tabbedPane.addTab("Account And Preferences", null, accountTabPanel, null);
		controllerContainer.setLayout(new BorderLayout(0, 0));
		
		driverContainer = new JPanel();
		controllerContainer.add(driverContainer, BorderLayout.CENTER);
		
		
		driverContainer.setLayout(new BorderLayout(0, 0));


		driverContainer.add(raceSelectorSection.getPanel(), BorderLayout.CENTER);
		controllerContainer.add(raceInfoSection.getPanel(), BorderLayout.WEST);

	}

	public void checkLoggedIn() {
		if (AccountHandler.getInstance().isLoggedIn()) {
			accountTab.refreshPage();
		} else {
			signIn.refreshPanel();
		}
	}

	public Speech getSpeechHandler() {
		return speechHandler;
	}

	public Tyres getTyreHandler() {
		return tyreHandler;
	}

	public int getCurrentLap() {
		if (raceControlSection != null) return raceControlSection.getCurrentLap();
		return 1;
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

	public ActionListener changeRace() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				speechHandler.clearCache();
				driverViewTab.clearCache();
				driverContainer.removeAll();
				driverContainer.add(raceSelectorSection.getPanel());
				driverViewTab = null;
				raceSelectorSection.redrawComponents();
				controllerContainer.remove(raceControlSection.getPanel());
				updateWholeScreen();
			}
		};
	}
	
}
