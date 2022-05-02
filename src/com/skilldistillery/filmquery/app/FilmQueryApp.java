package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
			int response;
			System.out.println("check");
			try {
				do {
					System.out.println("What would you like to find?");
					System.out.println("Select an option:");
					System.out.println("1) Look up a film by ID");
					System.out.println("2) Look up film by keyword");
					System.out.println("3) Exit");
					response = Integer.parseInt(input.nextLine());
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
			} catch(NoSuchElementException e ) {
				System.out.println("Input not recognized. Please enter a valid input");
			}catch (NumberFormatException e ) {
				System.out.println("Input not recognized. Please enter a valid input");
			}catch (Exception e) {
				System.out.println("An unknown problem has been encountered. Please restart program to continue");
				e.printStackTrace();
				return;
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
				showFilmDetailsMenu(films, input);
				valid = true;
			} catch(NoSuchElementException e ) {
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
				showFilmDetailsMenu(film, input);
				valid = true;
			} catch(NoSuchElementException e ) {
				System.out.println("Input not recognized. Please enter a valid input");
			}catch (NumberFormatException e ) {
				System.out.println("Input not recognized. Please enter a valid input");
			} 
		}

	}
	private void showFilmDetailsMenu(Film film, Scanner input) {
		System.out.println("1) View more details on " + film.getTitle()+ ".");
		System.out.println("2) Return to main menu.");
		boolean valid = false;
		while(!valid) {
			try {
				int response = Integer.parseInt(input.nextLine());
				do {
					if( response == 1) {
						film.showDetails();
						return;
					}else if(response == 2) {
						return;
					}else {
						System.out.println("Please enter 1 or 2.");
					}
				}while(response != 2);
				valid = true;
			}catch(NoSuchElementException e ) {
				System.out.println("Invalid input, please enter 1 to display details or 2 to return to main menu.");
			}catch (NumberFormatException e ) {
				System.out.println("Invalid input, please enter 1 to display details or 2 to return to main menu.");
			}
		}
		
	}
	private void showFilmDetailsMenu(List<Film> films, Scanner input) {
		System.out.println("1) View more details on a film matching your keyword.");
		System.out.println("2) Return to main menu.");
		boolean valid = false;
		while(!valid) {
			try {
				int response = Integer.parseInt(input.nextLine());
				do {
					if( response == 1) {
						System.out.println("Which film would you like to view? (Enter number corresponding to film)");
						for(int i = 0; i < films.size(); i ++){
							System.out.println(i + ") " + films.get(i).getTitle());
						}
						int response2 = Integer.parseInt(input.nextLine());
						films.get(response2).showDetails();
					}else if(response == 2) {
						return;
					}else {
						System.out.println("Please enter 1 to view film details or 2 to exit.");
					}
				}while(response != 2);
				valid = true;
			}catch(NoSuchElementException e ) {
				System.out.println("Invalid input. Enter 1 to view film details and 2 to return to the main menu:");
			}catch (NumberFormatException e ) {
				System.out.println("Invalid input. Enter 1 to view film details and 2 to return to the main menu:");
			}
		}
		
	}

	

}
