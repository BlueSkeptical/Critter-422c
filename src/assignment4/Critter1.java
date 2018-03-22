package assignment4;
/**
 * The critter is a "crab" which can only move horizontally,
 * one half chance of choosing to fight
 * @author Zitian Xie
 *
 */
public class Critter1 extends Critter {

	private int random = getRandomInt(100);

	@Override
	public String toString() {
		return "=";
	}

	public Critter1() {
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
		if (random % 2 == 0) {
			walk(0);
		} else {
			walk(4);
		}
	}
}
