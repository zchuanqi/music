package cn.itcast.music.web.music_servlet;


import cn.itcast.music.music_domain.ResultInfo;
import cn.itcast.music.music_domain.User;
import cn.itcast.music.music_service.UserService;
import cn.itcast.music.music_service.impl.UserServiceImpl;
import cn.itcast.music.web.servlet.BaseServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/music/user/*")
public class UserServlet extends BaseServlet {
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 取参数
        Map<String, String[]> parameterMap = req.getParameterMap();
        //2. 封装为User对象
        User user = new User();
        //3. 映射
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4. 调用login服务
        UserService userService = new UserServiceImpl();
        User u = userService.login(user);
        //5. 判断u
        ResultInfo info = new ResultInfo();
        if(u == null){
            //用户名/密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        } else {
            info.setFlag(true);
            req.getSession().setAttribute("user",u);
        }
        //6. 将info转为json格式
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getWriter(),info);
    }

    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 处理数据
        Map<String,String[]> parameterMap=req.getParameterMap();
        //2. 封装user对象
        User user=new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3. service完成注册
        UserService userService = new UserServiceImpl();
        boolean flag = userService.regist(user);
        ResultInfo info=new ResultInfo();

        //4. 响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }

        //5. 将info序列化为json，写到客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //6. 将json写到客户端
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    public void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }

    public void findLoginUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从Session中取得登录用户
        Object user = req.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),user);
    }
}
