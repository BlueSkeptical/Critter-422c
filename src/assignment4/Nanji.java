package assignment4;

public class Nanji extends Critter {

	private int random = getRandomInt(100);
	private int dir;
	private int count = 0;
	@Override
	public String toString() {
		return "=";
	}

	public Nanji() {
		
	}

	public boolean fight(String not_used) {
		if (random % 2 == 0) {
			dir = Critter.getRandomInt(8);
			walk(dir);
			dir = Critter.getRandomInt(8);
			run(dir);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		dir = Critter.getRandomInt(8);
		walk(dir);
		count++;
		if(count >= 10) {
			this.setEnergy(0);
		} else {
			this.setEnergy(this.getEnergy()-2);
		}
	}
}
