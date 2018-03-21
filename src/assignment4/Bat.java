package assignment4;

public class Bat extends Critter {

	private int random = getRandomInt(100);

	@Override
	public String toString() {
		return "^";
	}

	public Bat() {

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
