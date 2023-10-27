package model.repository;

import model.dto.StationDto;
import model.dto.StopDto;
import model.exceptions.RepositoryException;
import model.jdbc.StationDao;

import java.util.List;
import java.util.Map;

public class StationRepository implements Repository<Integer, StationDto>{

    private final StationDao stationDao;

    public StationRepository() throws RepositoryException {
        this.stationDao = StationDao.getInstance();
    }

    public StationRepository(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    @Override
    public Integer add(StationDto item) throws RepositoryException {
        return null;
    }

    @Override
    public void remove(Integer key) throws RepositoryException {

    }

    @Override
    public List<StationDto> getAll() throws RepositoryException {
        return this.stationDao.selectAll();
    }

    @Override
    public StationDto get(Integer key) throws RepositoryException {
        return this.stationDao.select(key);
    }

    public List<StationDto> getNeighboursStations(Integer key) throws RepositoryException {
        return this.stationDao.getNeighbourStations(key);
    }

    public StationDto getFullStation(Integer key) throws RepositoryException {
        return this.stationDao.selectFullStation(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return false;
    }
}
