package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */

public abstract class Critter {
	private static String myPackage;
	private static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static List<Critter> moveHistory = new java.util.ArrayList<Critter>();
	// map update in every call to Time step to update numbers of critter in each
	// coordinates
	private static int map[][] = new int[Params.world_height][Params.world_width];

	// Gets the package name. This assumes that Critter and its subclasses are all
	// in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private static java.util.Random rand = new java.util.Random();

	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}


	/*
	 * a one-character long string that visually depicts your critter in the ASCII
	 * interface
	 */
	public String toString() {
		return "";
	}

	private int energy = 0;

	protected int getEnergy() {
		return energy;
	}

	private int x_coord;
	private int y_coord;

	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;
		if (moveHistory.contains(this)) {
			return;
		}
		moveHistory.add(this);
		if (this.energy <= 0) {
			return;
		}
		switch (direction) {
		case 0:
			this.updateDir(1, 0);
			break;
		case 1:
			this.updateDir(1, -1);
			break;
		case 2:
			this.updateDir(0, -1);
			break;
		case 3:
			this.updateDir(-1, -1);
			break;
		case 4:
			this.updateDir(-1, 0);
			break;
		case 5:
			this.updateDir(-1, 1);
			break;
		case 6:
			this.updateDir(0, 1);
			break;
		case 7:
			this.updateDir(1, 1);
			break;
		}
	}

	protected final void run(int direction) {
		this.energy -= Params.run_energy_cost;
		if (moveHistory.contains(this)) {
			return;
		}
		moveHistory.add(this);
		if (this.energy <= 0) {
			return;
		}
		switch (direction) {
		case 0:
			this.updateDir(2, 0);
			break;
		case 1:
			this.updateDir(2, -2);
			break;
		case 2:
			this.updateDir(0, -2);
			break;
		case 3:
			this.updateDir(-2, -2);
			break;
		case 4:
			this.updateDir(-2, 0);
			break;
		case 5:
			this.updateDir(-2, 2);
			break;
		case 6:
			this.updateDir(0, 2);
			break;
		case 7:
			this.updateDir(2, 2);
			break;
		}
	}

	protected final void reproduce(Critter offspring, int direction) {
		if (offspring.energy < Params.min_reproduce_energy || offspring.energy <= 0) {
			return;
		}
		try {
			Class placeHolder = offspring.getClass();
			Critter newBorn = (Critter) placeHolder.newInstance();
			newBorn.energy = offspring.energy/2;
			offspring.energy -= newBorn.energy;
			newBorn.x_coord = offspring.x_coord;
			newBorn.y_coord = offspring.y_coord;
			switch (direction) {
			case 0:
				newBorn.updateDir(1, 0);
				break;
			case 1:
				newBorn.updateDir(1, -1);
				break;
			case 2:
				newBorn.updateDir(0, -1);
				break;
			case 3:
				newBorn.updateDir(-1, -1);
				break;
			case 4:
				newBorn.updateDir(-1, 0);
				break;
			case 5:
				newBorn.updateDir(-1, 1);
				break;
			case 6:
				newBorn.updateDir(0, 1);
				break;
			case 7:
				newBorn.updateDir(1, 1);
				break;
			}
			babies.add(newBorn);
		} catch (Exception e) {
			//throw new InvalidCritterException("haha");
		}
	}

	public abstract void doTimeStep();

	public abstract boolean fight(String oponent);

	/**
	 * create and initialize a Critter subclass. critter_class_name must be the
	 * unqualified name of a concrete subclass of Critter, if not, an
	 * InvalidCritterException must be thrown. (Java weirdness: Exception throwing
	 * does not work properly if the parameter has lower-case instead of upper. For
	 * example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * 
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class placeHolder = Class.forName(critter_class_name);
			Critter general = (Critter) placeHolder.newInstance();
			population.add(general);
		} catch (Exception e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}

	/**
	 * Gets a list of critters of a specific type.
	 * 
	 * @param critter_class_name
	 *            What kind of Critter is to be listed. Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		try {
			List<Critter> result = new java.util.ArrayList<Critter>();

			for (Critter c : population) {
				if (c.getClass().getName().equalsIgnoreCase(critter_class_name)) {
					result.add(c);
				}
			}
			return result;
		} catch (Exception e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * 
	 * @param critters
	 *            List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string, 1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}

	/*
	 * the TestCritter class allows some critters to "cheat". If you want to create
	 * tests of your Critter model, you can create subclasses of this class and then
	 * use the setter functions contained here.
	 * 
	 * NOTE: you must make sure that the setter functions work with your
	 * implementation of Critter. That means, if you're recording the positions of
	 * your critters using some sort of external grid or some other data structure
	 * in addition to the x_coord and y_coord functions, then you MUST update these
	 * setter functions so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}

		/*
		 * This method getPopulation has to be modified by you if you are not using the
		 * population ArrayList that has been provided in the starter code. In any case,
		 * it has to be implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the
		 * babies ArrayList that has been provided in the starter code. In any case, it
		 * has to be implemented for grading tests to work. Babies should be added to
		 * the general population at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
	}

	public static void worldTimeStep() {
		Critter temp;
		java.util.ArrayList<Critter> samePlace = new java.util.ArrayList<Critter>();
		moveHistory.clear();
		map = null;
		for (Critter c : population) {
			c.doTimeStep();
		}
		for (int w = 0; w < Params.world_width; w++) {
			for (int h = 0; h < Params.world_height; h++) {
				for (Critter c : population) {
					if ((c.x_coord == w) && (c.y_coord == h)) {
						map[h][w]++;
						samePlace.add(c);
					}
				}
				while (samePlace.size() >= 2) {
					samePlace = encounter(samePlace);
				}
			}
		}
		for (Critter bay : babies) {
			population.add(bay);
		}
		babies.clear();
		for (Critter c : population) {
			c.energy -= Params.rest_energy_cost;
		}
		for (int i = 0; i < population.size(); i++) {
			temp = population.get(i);
			if (temp.getEnergy() <= 0) {
				population.remove(temp);
				i--;
			}
		}
	}

	public static void displayWorld() {
		String display[][] = new String[Params.world_height + 2][Params.world_width + 2];
		display[0][0] = "+";
		for (int w = 1; w < Params.world_width + 1; w++) {
			display[0][w] = "-";
		}
		display[0][Params.world_width + 1] = "+";
		for (int h = 1; h < (Params.world_height + 1); h++) {
			display[h][0] = "|";
			for (int w = 1; w < (Params.world_width + 1); w++) {
				display[h][w] = " ";
			}
			display[h][Params.world_width + 1] = "|";
		}
		display[Params.world_height + 1][0] = "+";
		for (int w = 1; w < (Params.world_width + 1); w++) {
			display[Params.world_height + 1][w] = "-";
		}
		display[Params.world_height + 1][Params.world_width + 1] = "+";
		for (Critter c : population) {
			display[c.y_coord + 1][c.x_coord + 1] = c.toString();
		}
		for (int h = 0; h < (Params.world_height + 2); h++) {
			for (int w = 0; w < (Params.world_width + 2); w++) {
				System.out.print(display[h][w]);
			}
			System.out.println("");
		}
	}

	private static java.util.ArrayList<Critter> encounter(java.util.ArrayList<Critter> list) {
		Critter loser;
		Critter critter1 = list.get(0);
		Critter critter2 = list.get(1);
		Boolean fight1 = critter1.fight(critter2.toString());
		Boolean fight2 = critter2.fight(critter1.toString());
		map[critter1.y_coord][critter1.x_coord]--;
		if (fight1 && fight2) {
			loser = battle(critter1, critter2);
			loser.energy = -1;
			list.remove(loser);
		} else if (fight1 && !fight2) {
			critter2.walk(getRandomInt(8));
			if (map[critter2.y_coord][critter2.x_coord] >= 1) {
				critter2.energy = -1;
			} else {
				map[critter2.y_coord][critter2.x_coord]++;
			}
			list.remove(critter2);
		} else if (!fight1 && fight2) {
			critter1.walk(getRandomInt(8));
			if (map[critter1.y_coord][critter1.x_coord] >= 1) {
				critter1.energy = -1;
			} else {
				map[critter1.y_coord][critter1.x_coord]++;
			}
			list.remove(critter1);
		} else {
			// x&y original position
			int x = critter1.x_coord;
			int y = critter1.y_coord;

			critter1.walk(getRandomInt(8));
			critter2.walk(getRandomInt(8));
			if (map[critter1.y_coord][critter1.x_coord] >= 1 && map[critter2.y_coord][critter2.x_coord] >= 1) {
				loser = battle(critter1, critter2);
				loser.energy = -1;
				list.remove(loser);
				// winner in original place
				list.get(0).x_coord = x;
				list.get(0).y_coord = y;
			} else if (map[critter2.y_coord][critter2.x_coord] >= 1) {
				list.remove(critter1);
				critter2.x_coord = x;
				critter2.y_coord = y;
				map[critter1.y_coord][critter1.x_coord]++;
			} else if (map[critter1.y_coord][critter1.x_coord] >= 1) {
				list.remove(critter2);
				critter1.x_coord = x;
				critter1.y_coord = y;
				map[critter2.y_coord][critter2.x_coord]++;
			} else {
				if (critter1.x_coord == critter2.x_coord && critter1.y_coord == critter2.y_coord) {
					critter2.x_coord = x;
					critter2.y_coord = y;
					list.remove(critter1);
					map[critter1.y_coord][critter1.x_coord]++;
				} else {
					list.remove(critter1);
					list.remove(critter2);
					map[critter1.y_coord][critter1.x_coord]++;
					map[critter2.y_coord][critter2.x_coord]++;
					map[y][x]--;
				}
			}
		}
		return list;
	}

	private static Critter battle(Critter critter1, Critter critter2) {
		int random1 = getRandomInt(critter1.energy);
		int random2 = getRandomInt(critter2.energy);
		if (random1 >= random2) {
			critter1.energy += critter2.energy / 2;
			return critter2;
		} else {
			critter2.energy += critter1.energy / 2;
			return critter1;
		}
	}

	private void updateDir(int xChange, int yChange) {
		this.x_coord += xChange;
		this.y_coord += yChange;
		if (this.x_coord < 0) {
			this.x_coord += Params.world_width;
		}
		if (this.x_coord > (Params.world_width - 1)) {
			this.x_coord -= Params.world_width;
		}
		if (this.y_coord < 0) {
			this.y_coord += Params.world_height;
		}
		if (this.y_coord > (Params.world_height - 1)) {
			this.y_coord -= Params.world_height;
		}
	}
}
