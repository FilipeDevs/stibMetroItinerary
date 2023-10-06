package model.repository;

import model.dto.StopDto;
import model.dto.StopKey;
import model.exceptions.RepositoryException;
import model.jdbc.StopsDao;
import java.util.List;
import java.util.Map;

public class StopRepository implements Repository<StopKey, StopDto>{

    private final StopsDao dao;

    public StopRepository() throws RepositoryException{
        dao = StopsDao.getInstance();
    }

    public StopRepository(StopsDao dao) {
        this.dao = dao;
    }

    @Override
    public StopKey add(StopDto item) throws RepositoryException {
        return null;
    }

    @Override
    public void remove(StopKey key) throws RepositoryException {

    }

    @Override
    public List<StopDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopDto get(StopKey key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(StopKey key) throws RepositoryException {
        StopDto refreshItem = dao.select(key);
        return refreshItem != null;
    }
}




