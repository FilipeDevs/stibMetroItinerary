package model.jdbc;

import model.dto.StopDto;
import model.dto.StopKey;
import model.exceptions.RepositoryException;
import java.sql.*;
import java.util.*;

public class StopsDao implements Dao<StopKey, StopDto> {

    private final Connection connection;

    public StopsDao() throws RepositoryException {
        connection = DBManager.getInstance().getConnection();
    }

    public static StopsDao getInstance() throws RepositoryException {
        return StopsDaoHolder.getInstance();
    }

    @Override
    public List<StopDto> selectAll() throws RepositoryException {
        String sql = "SELECT id_line, id_station, id_order FROM STOPS";
        List<StopDto> dtos = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                StopKey stopKey = new StopKey(resultSet.getInt(1), resultSet.getInt(2));
                dtos.add(new StopDto(stopKey, resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public StopDto select(StopKey key) throws RepositoryException {
        if(key == null) {
            throw new RepositoryException("No key was given !");
        }

        String sql = "SELECT id_line, id_station, id_order FROM STOPS where id_line = ? AND id_station = ?";
        StopDto dto = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, key.id_line()); // id_line = ?
            preparedStatement.setInt(2, key.id_station()); //id_station = ?
            ResultSet resultSet = preparedStatement.executeQuery();

            int count = 0;
            while(resultSet.next()) {
                StopKey stopKey = new StopKey(resultSet.getInt(1), resultSet.getInt(2)) ;
                dto = new StopDto(stopKey, resultSet.getInt(3));
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
    public void delete(StopKey key) throws RepositoryException {}

    @Override
    public void update(StopDto item) throws RepositoryException {}

    @Override
    public StopKey insert(StopDto item) throws RepositoryException {
        return null;
    }

    private static class StopsDaoHolder {
        private static StopsDao getInstance() throws RepositoryException {
            return new StopsDao();
        }
    }
}
