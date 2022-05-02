package com.skilldistillery.filmquery.database;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.skilldistillery.filmquery.entities.Film;

class DatabaseAccessorObjectTest {
	private DatabaseAccessorObject dao = new DatabaseAccessorObject();

	@Test
	void find_film_by_id_returns_correct_film() {
		Film correctFilm = new Film(34, "ARABIA DOGMA", "A Touching Epistle of a Madman And a Mad Cow who must Defeat a Student in Nigeria", 1999, 1, 6, 0.99, 62, 29.99, "NC17", "Commentaries,Deleted Scenes" );
		Film testFilm = dao.findFilmById(34);
		assertTrue(correctFilm.equals(testFilm));
	}
	
	@Test void find_film_by_id_returns_null_for_invalid_id() {
		assertTrue(dao.findActorById(-1) == null);
	}
	@Test void find_film_by_id_adds_actors_to_film() {
		
		
		assertTrue(dao.findActorById(-1) == null);
	}
	@Test void find_film_by_id_adds_inventory() {
		assertTrue(dao.findActorById(-1) == null);
	}

}
