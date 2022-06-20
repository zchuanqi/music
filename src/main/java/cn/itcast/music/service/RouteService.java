package cn.itcast.music.service;

import cn.itcast.music.domain.PageBean;
import cn.itcast.music.domain.Route;

public interface RouteService {

    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    Route findOne(String rid);
}
