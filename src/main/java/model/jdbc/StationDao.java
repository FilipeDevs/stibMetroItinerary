package model.jdbc;

import model.dto.StationDto;
import model.dto.StopKey;
import model.exceptions.RepositoryException;
import model.dto.StopDto;
import model.exceptions.RepositoryException;
import java.sql.*;
import java.util.*;

import java.util.List;

public class StationDao implements Dao<Integer, StationDto> {

    private final Connection connection;

    public StationDao() throws RepositoryException {
        this.connection = DBManager.getInstance().getConnection();
    }

    public static StationDao getInstance() throws RepositoryException {
        return StationDaoHolder.getInstance();
    }

    @Override
    public List<StationDto> selectAll() throws RepositoryException {
        List<StationDto> stationDtoList = new ArrayList<>();
        String sql = "SELECT id, name FROM STATIONS"; // Selects all stations

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                stationDtoList.add(new StationDto(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return stationDtoList;
    }

    public List<StationDto> getNeighbourStations(Integer key) throws RepositoryException {
        if(key == null) {
            throw new RepositoryException("No key was given !");
        }
        String sql = """
                 SELECT s2.id_station AS station2, st2.name
                 FROM Stops s1
                 JOIN Stops s2 ON s1.id_line = s2.id_line
                 JOIN STATIONS st1 ON st1.id = s1.id_station
                 JOIN STATIONS st2 ON st2.id = s2.id_station
                 WHERE (s1.id_station != station2 AND s1.id_order = s2.id_order + 1 OR s1.id_order = s2.id_order - 1)
                 AND s1.id_station = ?
                 GROUP BY station2
                """;

       List<StationDto> neighbours = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, key); // s1.id_station = ?
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                neighbours.add(new StationDto(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return neighbours;
    }

    @Override
    public StationDto select(Integer key) throws RepositoryException {
        if(key == null) {
            throw new RepositoryException("No key was given !");
        }

        String sql = "SELECT id, name FROM STATIONS WHERE id = ?";
        StationDto dto = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, key); // id
            ResultSet resultSet = preparedStatement.executeQuery();

            int count = 0;
            while(resultSet.next()) {
                dto = new StationDto(resultSet.getInt(1), resultSet.getString(2));
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
    public void delete(Integer key) throws RepositoryException {}

    @Override
    public void update(StationDto item) throws RepositoryException {}

    @Override
    public Integer insert(StationDto item) throws RepositoryException {
        return null;
    }

    public StationDto selectFullStation(Integer key) throws RepositoryException {
        if(key == null) {
            throw new RepositoryException("No key was given !");
        }
        StationDto stationDtoFull;
        String sql = """
                SELECT st.id, st.name, s1.id_line
                FROM STATIONS st
                JOIN STOPS s1 ON s1.id_station = st.id
                WHERE st.id = ?
                GROUP BY st.name, s1.id_line
                """; // Select station with all lines

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, key); // st.id = ?
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            stationDtoFull = new StationDto(key, resultSet.getString(2));
            stationDtoFull.addLine(resultSet.getInt(3));

            while(resultSet.next()) {
                stationDtoFull.addLine(resultSet.getInt(3));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return stationDtoFull;
    }


    private static class StationDaoHolder {
        private static StationDao getInstance() throws RepositoryException {
            return new StationDao();
        }
    }
}
