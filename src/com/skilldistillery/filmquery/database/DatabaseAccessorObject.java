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
		String sql = "SELECT * FROM film WHERE film.id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pw);
				PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setInt(1, filmId);
			rs = pst.executeQuery();
			if (rs.next()) {

				film = new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getInt("rental_duration"),
						rs.getDouble("rental_rate"), rs.getInt("length"), rs.getDouble("replacement_cost"),
						rs.getString("rating"), rs.getString("special_features"));
				film.setActors(findActorsByFilmId(filmId));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		ResultSet rs;
		String sql = "SELECT * FROM actor WHERE actor.id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pw);
				PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setInt(1, actorId);
			rs = pst.executeQuery();
			if (rs.next()) {

				actor = new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		ResultSet rs;
		String sql = "SELECT actor.* FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film.id = film_actor.film_id WHERE film.id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pw);
				PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setInt(1, filmId);
			rs = pst.executeQuery();
			while (rs.next()) {
				actors.add(new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return actors;
	}

}
