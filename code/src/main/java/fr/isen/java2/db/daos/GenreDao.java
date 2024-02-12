package fr.isen.java2.db.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.isen.java2.db.entities.Genre;

public class GenreDao {

    public List<Genre> listGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genre";

        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Genre genre = new Genre(resultSet.getInt("idgenre"), resultSet.getString("name"));
                genres.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
            System.out.println("Handle exception as needed");
        }

        return genres;
    }

    public Genre getGenre(String name) {
        Genre genre = null;
        String sql = "SELECT * FROM genre WHERE name = ?";

        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    genre = new Genre(resultSet.getInt("idgenre"), resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
            System.out.println("Handle exception as needed");
        }

        return genre;
    }
    
	public Genre getGenreById(Integer id) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE idgenre=?")) {
				statement.setInt(1, id);
				try (ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						Genre genre = new Genre(
								result.getInt("idgenre"),
								result.getString("name"));
						return genre;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

    public void addGenre(String name) {
        String sql = "INSERT INTO genre(name) VALUES(?)";

        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
            System.out.println("Handle exception as needed");
        }
    }
}
