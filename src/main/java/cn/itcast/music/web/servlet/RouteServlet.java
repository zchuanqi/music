package cn.itcast.music.web.servlet;

import cn.itcast.music.domain.PageBean;
import cn.itcast.music.domain.Route;
import cn.itcast.music.domain.User;
import cn.itcast.music.service.FavoriteService;
import cn.itcast.music.service.RouteService;
import cn.itcast.music.service.impl.FavoriteServiceImpl;
import cn.itcast.music.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");

        //接受rname路线名称
        String rname = req.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");

        int cid = 0;
        if (cidStr != null && cidStr.length() > 0) {
            cid = Integer.parseInt(cidStr);
        }

        int pageSize = 5;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        int currentPage = 1;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        }

        PageBean<Route> pageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
        writeJsonValue(resp, pageBean);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收 id
        String rid = request.getParameter("rid");
        //2.调用 service 查询 route 对象
        Route route = routeService.findOne(rid);
        //3.转为 json 写回客户端
        writeJsonValue(response, route);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路 id
        String rid = request.getParameter("rid");

        //2. 获取当前登录的用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户 id
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }

        //3. 调用 FavoriteService 查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4. 写回客户端
        writeJsonValue(response, flag);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路 rid
        String rid = request.getParameter("rid");
        //2. 获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户 id
        if (user == null) {
            //尚未登录
            return;
        } else {
            //已登录
            uid = user.getUid();
        }

        //3. 调用 service 添加
        favoriteService.add(rid, uid);
    }
}
