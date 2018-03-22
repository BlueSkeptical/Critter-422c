package assignment4;
/**
 * This class is a "bat", the critter does not walk nor run, 
 * in every do time step, it chose a place on the map randomly and fly there
 * one eighth chance to fight
 * @author Zitian Xie
 *
 */
public class Critter3 extends Critter {

	private int random = getRandomInt(100);

	@Override
	public String toString() {
		return "^";
	}

	public Critter3() {

	}

	public boolean fight(String not_used) {
		if (random % 8 == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void doTimeStep() {
		int x = getRandomInt(Params.world_width);
		int y = getRandomInt(Params.world_height);
		this.setX(x);
		this.setY(y);
	}
}
