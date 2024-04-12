package APIObjects;

import java.awt.Color;

public class Team {
	
	private final String name;
	private Color colour;
	
	public Team(String name) {
		this.name = name;
		setColour();
	}

	public String getName() {
		return name;
	}


	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	private void setColour() {
		if (name.contains("Williams")) {
			colour = new Color(0,0,255);
		} else if (name.contains("Red Bull Racing")) {
			colour = new Color(0,0,128);
		} else if (name.contains("RB")) {
			colour = new Color(255, 255, 255);
		} else if (name.contains("McLaren")) {
			colour = new Color(255,165,0);
		} else if (name.contains("Ferrari")) {
			colour = new Color(255,0,0);
		} else if (name.contains("Aston Martin")) {
			colour = new Color(0,100,0);
		} else if (name.contains("Haas F1 Team")) {
			colour = new Color(178,34,34);
		} else if (name.contains("Kick Sauber")) {
			colour = new Color(0,255,0);
		} else if (name.contains("Mercedes")) {
			colour = new Color(0,206,209);
		} else if (name.contains("Alpine")) {
			colour = new Color(169,169,169);
		} else {
			colour = new Color(255, 255, 255);
		}
	}

	
	
	
}
