package assignment4;

public class Crab extends Critter {

	private int random = getRandomInt(100);

	@Override
	public String toString() {
		return "=";
	}

	public Crab() {

	}

	public boolean fight(String not_used) {
		if (random % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */

		if (random % 2 == 0) {
			walk(0);
		} else {
			walk(4);
		}
	}
}
