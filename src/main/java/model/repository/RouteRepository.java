package model.repository;

import model.dto.RouteDto;
import model.exceptions.RepositoryException;
import model.jdbc.RouteDao;

import java.util.List;

public class RouteRepository implements Repository<Integer, RouteDto> {

    private final RouteDao routeDao;

    public RouteRepository() throws RepositoryException {
        this.routeDao = RouteDao.getInstance();
    }

    @Override
    public Integer add(RouteDto item) throws RepositoryException {
        Integer key = item.getKey();
        if (contains(item.getKey())) {
            routeDao.update(item);
        } else {
            key = routeDao.insert(item);
        }
        return key;
    }

    @Override
    public void remove(Integer key) throws RepositoryException {
        routeDao.delete(key);
    }

    @Override
    public List<RouteDto> getAll() throws RepositoryException {
        return routeDao.selectAll();
    }

    @Override
    public RouteDto get(Integer key) throws RepositoryException {
        return routeDao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        RouteDto refreshItem = routeDao.select(key);
        return refreshItem != null;
    }
}
