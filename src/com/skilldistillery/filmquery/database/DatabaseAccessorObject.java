package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private String user = "student";
	private String pw = "student";
	private static String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public DatabaseAccessorObject() {

	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		ResultSet rs;
		String sql = "SELECT film.*, language.name FROM film JOIN language ON language.id = film.language_id WHERE film.id = ?";
		String actorQuery = "SELECT actor.* FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film.id = film_actor.film_id WHERE film.id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pw);
				PreparedStatement pst = conn.prepareStatement(sql);
				PreparedStatement actorPst = conn.prepareStatement(actorQuery);) {
			pst.setInt(1, filmId);
			rs = pst.executeQuery();
			if (rs.next()) {

				film = new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getInt("rental_duration"),
						rs.getDouble("rental_rate"), rs.getInt("length"), rs.getDouble("replacement_cost"),
						rs.getString("rating"), rs.getString("special_features"));
				film.setLanguage(rs.getNString("language.name"));
				List<Actor> filmActors = new ArrayList<>();
				actorPst.setInt(1, filmId);
				rs = actorPst.executeQuery();
				while (rs.next()) {
					filmActors.add(new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));
				}
				film.setActors(filmActors);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public List<Film> findFilmsByQuery(String query) {
		List<Film> films = new ArrayList<>();
		query = "%" + query + "%";
		ResultSet rs;
		String actorQuery = "SELECT actor.* FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film.id = film_actor.film_id WHERE film.id = ?";
		String sql = "SELECT film.*, language.name FROM film JOIN language ON film.language_id = language.id WHERE film.title LIKE ? OR film.description Like ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pw);
				PreparedStatement pst = conn.prepareStatement(sql);
				PreparedStatement actorPst = conn.prepareStatement(actorQuery);) {
			pst.setNString(1, query);
			pst.setNString(2, query);
			rs = pst.executeQuery();
			while (rs.next()) {
				Film film = new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getInt("rental_duration"),
						rs.getDouble("rental_rate"), rs.getInt("length"), rs.getDouble("replacement_cost"),
						rs.getString("rating"), rs.getString("special_features"));
				film.setLanguage(rs.getNString("language.name"));
				List<Actor> filmActors = new ArrayList<>();
				actorPst.setInt(1, film.getId());
				ResultSet actAndLang = actorPst.executeQuery();
				while (actAndLang.next()) {
					filmActors.add(new Actor(actAndLang.getInt("id"), actAndLang.getString("first_name"), actAndLang.getString("last_name")));
				}
				film.setActors(filmActors);
				films.add(film);

				actAndLang.close();
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return films;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		ResultSet rs;
		String filmQuery = "Select film.* FROM film JOIN film_actor ON film.id = film_actor.film_id JOIN actor ON actor.id = film_actor.actor_id WHERE actor.id = ?";
		String sql = "SELECT * FROM actor WHERE actor.id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pw);
				PreparedStatement actorPst = conn.prepareStatement(filmQuery);
				PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setInt(1, actorId);
			rs = pst.executeQuery();
			if (rs.next()) {
				actor = new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
//				actor.setFilms(findFilmsByActorId(actorId));
				List<Film> actorFilms = new ArrayList<>();
				actorPst.setInt(1, actorId);
				rs = actorPst.executeQuery();
				while(rs.next()) {
					actorFilms.add(new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getInt("rental_duration"),
						rs.getDouble("rental_rate"), rs.getInt("length"), rs.getDouble("replacement_cost"),
						rs.getString("rating"), rs.getString("special_features")));
				}
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return actor;
	}

}
