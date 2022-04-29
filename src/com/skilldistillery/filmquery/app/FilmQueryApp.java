package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() {
		Film film = db.findFilmById(1);
		System.out.println(film);
		Actor actor = db.findActorById(3);
		System.out.println(actor);
		System.out.println(film.getActors());
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		boolean valid = false;
		while (!valid) {
			try {
				int response = 3;
				do {
					System.out.println("What would you like to find?");
					System.out.println("Select an option:");
					System.out.println("1) Look up a film by ID");
					System.out.println("2) Look up film by keyword");
					System.out.println("3) Exit");
					response = input.nextInt();
					input.nextLine();
					switch (response) {
					case 1:
						filmLookupById(input);
						break;
					case 2:
						filmLookup(input);
						break;
					case 3:
						System.out.println("Goodbye");
						break;
					default:
						System.out.println("That was not a valid input. please enter 1-3");
					}
				} while (response != 3);
				valid = true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Input not recognized. Please enter a valid input");
			}
		}

	}

	private void filmLookup(Scanner input) {
		List<Film> films = new ArrayList<>();
		System.out.println("Enter query:");
		String response = "";
		boolean valid = false;
		while (!valid) {
			try {
				response = input.nextLine();
				films = db.findFilmsByQuery(response);
				if (films.size() == 0) {
					System.out.println("There was no film matching your query.");
					return;
				}
				System.out.println("Film(s) matching " + response + ":");
				for (Film film : films) {
					System.out.println(film);
				}
				valid = true;
			} catch (Exception e) {
				System.out.println(
						"You're input was not recognized. Please enter lookup for a film title or description.");
			}
		}
		

	}

	

	private void filmLookupById(Scanner input) {
		System.out.println("Enter film ID:");
		String response = "";
		boolean valid = false;
		while (!valid) {
			try {
				response = input.nextLine();
				Film film = db.findFilmById(Integer.parseInt(response));
				if (film == null) {
					System.out.println("There is no film with that Id.");
					return;
				}
				System.out.println("Film id:" + response);
				System.out.println(film);
				film.showActors();
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input for search, enter a number Id.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	

}
