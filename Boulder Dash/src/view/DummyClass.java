//AUTORE: Davide Caligiuri
package view;

import model.GameObject;

//un semplice wrapper per utilizzare in maniera uniforme LivingSprite e BlockSprite
public class DummyClass {

	GameObject logicObj;

	public GameObject getLogicObj() {
		return logicObj;
	}

	public void setLogicObj(GameObject logicObj) {
		this.logicObj = logicObj;
	}

}
