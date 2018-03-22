package assignment4;
/**
 * A critter of "Pikachu", who can gain energy during fight if 
 * it has more than half start energy. It will run when energy drop less
 * than half of it's start energy, otherwise walking
 * @author Xin Geng
 *
 */
public class Critter2 extends Critter {

	private int dir;
	private int energy;

	@Override
	public String toString() {
		return "*";
	}

	public Critter2() {

	}

	public boolean fight(String not_used) {
		if (this.getEnergy() > Params.start_energy / 2) {
			energy = this.getEnergy();
			energy += 1000;
			this.setEnergy(energy);
			return true;
		} else {
			return true;
		}
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		dir = Critter.getRandomInt(8);
		if (this.getEnergy() > Params.start_energy / 2) {
			walk(dir);
		} else {
			run(dir);
		}
	}

	public static void runStats(java.util.List<Critter> pikachu) {
		System.out.print("" + pikachu.size() + " total Pikachu    ");
		System.out.print("Thunderbolt!");
		System.out.println();
	}
}
