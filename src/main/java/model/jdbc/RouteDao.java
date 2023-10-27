package model.jdbc;

import model.dto.RouteDto;
import model.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDao implements Dao<Integer, RouteDto> {

    private final Connection connection;

    public RouteDao() throws RepositoryException {
        this.connection = DBManager.getInstance().getConnection();
    }

    public static RouteDao getInstance() throws RepositoryException {
        return RouteDao.RouteDaoHolder.getInstance();
    }

    @Override
    public List<RouteDto> selectAll() throws RepositoryException {
        List<RouteDto> routeDtos = new ArrayList<>();
        String sql = "SELECT * FROM ROUTES"; // Selects all stations

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                routeDtos.add(new RouteDto(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return routeDtos;
    }

    @Override
    public RouteDto select(Integer key) throws RepositoryException {
        if(key == null) {
            throw new RepositoryException("No key was given !");
        }

        String sql = "SELECT id, name, origin, destination FROM ROUTES WHERE id = ?";
        RouteDto dto = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, key); // id
            ResultSet resultSet = preparedStatement.executeQuery();

            int count = 0;
            while(resultSet.next()) {
                dto = new RouteDto(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4));
                count++;
            }
            if(count > 1) {
                throw new RepositoryException("Record is not unique !" + key);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return dto;
    }

    @Override
    public void delete(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "DELETE FROM ROUTES WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(RouteDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        String sql = "UPDATE ROUTES SET name =?, origin=?, destination=? where id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getName()); // name
            pstmt.setString(2, item.getSourceStation()); // origin
            pstmt.setString(3, item.getDestStation()); // destination
            pstmt.setInt(4, item.getKey()); // id
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

    }

    @Override
    public Integer insert(RouteDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        Integer id = item.getKey();
        String sql = "INSERT INTO ROUTES(name, origin, destination) values(?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getSourceStation());
            pstmt.setString(3, item.getDestStation());
            pstmt.executeUpdate();

            ResultSet result = pstmt.getGeneratedKeys();
            while (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return id;
    }

    private static class RouteDaoHolder {
        private static RouteDao getInstance() throws RepositoryException {
            return new RouteDao();
        }
    }
}
