package cn.itcast.music.dao;

import cn.itcast.music.domain.Route;

import java.util.List;

public interface RouteDao {

    int findTotal(int cid, String rname);

    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    Route findOne(int rid);
}
