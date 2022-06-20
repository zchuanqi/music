package cn.itcast.music.service.impl;

import cn.itcast.music.dao.FavoriteDao;
import cn.itcast.music.dao.RouteDao;
import cn.itcast.music.dao.RouteImgDao;
import cn.itcast.music.dao.SellerDao;
import cn.itcast.music.dao.impl.FavoriteDaoImpl;
import cn.itcast.music.dao.impl.RouteDaoImpl;
import cn.itcast.music.dao.impl.RouteImgDaoImpl;
import cn.itcast.music.dao.impl.SellerDaoImpl;
import cn.itcast.music.domain.PageBean;
import cn.itcast.music.domain.Route;
import cn.itcast.music.domain.RouteImg;
import cn.itcast.music.domain.Seller;
import cn.itcast.music.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();

    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    private SellerDao sellerDao = new SellerDaoImpl();

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);

        int totalCount = routeDao.findTotal(cid, rname);
        pageBean.setTotalCount(totalCount);

        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pageBean.setList(list);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        //1.根据 id 去 route 表中查询 route 对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //2.根据 route 的 id 查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //2.2 将集合设置到 route 对象
        route.setRouteImgList(routeImgList);
        //3.根据 route 的 sid（商家 id）查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        //4.查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);

        return route;
    }
}
