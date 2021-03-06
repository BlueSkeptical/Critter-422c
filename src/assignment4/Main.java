package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Xin Geng
 * xg2543
 * 15465
 * Zitian Xie
 * zx2253
 * 15465
 * Slip days used: <0>
 * Spring 2018
 */

import java.util.Scanner;
import java.io.*;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

	static Scanner kb; // scanner connected to keyboard input, or input file
	private static String inputFile; // input file, used instead of keyboard input if specified
	static ByteArrayOutputStream testOutputString; // if test specified, holds all console output
	private static String myPackage; // package of Critter file. Critter cannot be in default pkg.
	private static boolean DEBUG = false; // Use it or not, as you wish!
	static PrintStream old = System.out; // if you want to restore output to console

	// Gets the package name. The usage assumes that Critter and its subclasses are
	// all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            args can be empty. If not empty, provide two parameters -- the
	 *            first is a file name, and the second is test (for test output,
	 *            where all output to be directed to a String), or nothing.
	 */
	public static void main(String[] args) {
		if (args.length != 0) {
			try {
				inputFile = args[0];
				kb = new Scanner(new File(inputFile));
			} catch (FileNotFoundException e) {
				System.out.println("USAGE: java Main OR java Main <input file> <test output>");
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
			}
			if (args.length >= 2) {
				if (args[1].equals("test")) { // if the word "test" is the second argument to java
					// Create a stream to hold the output
					testOutputString = new ByteArrayOutputStream();
					PrintStream ps = new PrintStream(testOutputString);
					// Save the old System.out.
					old = System.out;
					// Tell Java to use the special stream; all console output will be redirected
					// here from now
					System.setOut(ps);
				}
			}
		} else { // if no arguments to main
			kb = new Scanner(System.in); // use keyboard and console
		}

		/* Do not alter the code above for your submission. */
		/* Write your code below. */
		Scanner wordScanner;
		String command;

		while (true) {
			System.out.print("critters> ");
			// get the command
			String inputL = kb.nextLine();
			wordScanner = new Scanner(inputL);
			command = wordScanner.next();
			if (command.equals("quit")) {
				if (wordScanner.hasNext()) {
					System.out.println("error processing: " + inputL);
					return;
				}
				return;
			} else if (command.equals("show")) {
				if (wordScanner.hasNext()) {
					System.out.println("error processing: " + inputL);
					return;
				}
				Critter.displayWorld();
			} else if (command.equals("step")) {
				if (wordScanner.hasNext()) {
					int stepTimes = wordScanner.nextInt();
					if (wordScanner.hasNext()) {
						System.out.println("error processing: " + inputL);
						return;
					}
					while (stepTimes > 0) {
						Critter.worldTimeStep();
						stepTimes--;
					}
				} else {
					Critter.worldTimeStep();
				}
			} else if (command.equals("seed")) {
				if (wordScanner.hasNext()) {
					System.out.println("error processing: " + inputL);
					return;
				}
				int seedNum = wordScanner.nextInt();
				Critter.setSeed(seedNum);
			} else if (command.equals("make")) {
				String className = wordScanner.next();
				try {
					if (wordScanner.hasNext()) {
						int makeNum = wordScanner.nextInt();
						if (wordScanner.hasNext()) {
							System.out.println("error processing: " + inputL);
							return;
						}
						while (makeNum > 0) {
							Critter.makeCritter(className);
							makeNum--;
						}
					} else {
						Critter.makeCritter(className);
					}
				} catch (Exception e) {
					System.out.println("error processing:" + inputL);
				}
			} else if (command.equals("stats")) {
				String className = wordScanner.next();
				if (wordScanner.hasNext()) {
					System.out.println("error processing: " + inputL);
					return;
				}
				try {
					// get the right name
					String cName = myPackage.toString();
					cName += ".";
					cName += className;
					java.util.List<Critter> list = Critter.getInstances(cName);
					Class placeHolder = Class.forName(cName);
					// get the method from subclass
					java.lang.reflect.Method theMethod = placeHolder.getMethod("runStats", java.util.List.class);
					theMethod.invoke(placeHolder, list);
				} catch (Exception e) {
					System.out.println("error processing:" + inputL);
				}
			} else {
				System.out.println("invalid command: " + inputL);
			}
			wordScanner.close();
			System.out.flush();
		}
	}
}
