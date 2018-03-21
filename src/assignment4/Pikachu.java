package assignment4;

public class Pikachu extends Critter {

	private int dir;
	private int energy;
	@Override
	public String toString() {
		return "*";
	}

	public Pikachu() {
		
	}

	public boolean fight(String not_used) {
		if(this.getEnergy() > Params.start_energy/2) {
			energy = this.getEnergy();
			energy += 1000;
			this.setEnergy(energy);
			return false;
		}else {
			return true;
		}
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		dir = Critter.getRandomInt(8);
		if(this.getEnergy() > Params.start_energy/2) {
			walk(dir);
		}else {
			run(dir);
		}
	}
	
	public static void runStats(java.util.List<Critter> pikachu) {
		System.out.print("" + pikachu.size() + " total Pikachu    ");
		System.out.print("Thunderbolt!");
		System.out.println();
	}
}
