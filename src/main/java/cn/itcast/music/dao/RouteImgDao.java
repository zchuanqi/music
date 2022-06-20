package cn.itcast.music.dao;

import cn.itcast.music.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    List<RouteImg> findByRid(int rid);
}
