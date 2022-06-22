package cn.itcast.music.web.music_servlet;


import cn.itcast.music.music_domain.ResultInfo;
import cn.itcast.music.music_domain.User;
import cn.itcast.music.music_service.UserService;
import cn.itcast.music.music_service.impl.UserServiceImpl;
import cn.itcast.music.util.ByteUtil;
import cn.itcast.music.web.servlet.BaseServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("/music/user/*")
public class UserServlet extends BaseServlet {
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 取参数
        Map params = ByteUtil.parseParams(req);
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        //2. 封装为User对象
        User user = new User();
        //3. 映射
        user.setUsername(username);
        user.setPassword(password);
        //4. 调用login服务
        UserService userService = new UserServiceImpl();
        User u = userService.login(user);
        //5. 判断u
        ResultInfo info = new ResultInfo();
        if (u == null) {
            //用户名/密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        } else {
            info.setFlag(true);
            req.getSession().setAttribute("user", u);
        }
        //6. 将info转为json格式
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getWriter(), info);
    }

    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 处理数据
        String username = null;
        String password = null;
        String img_url = null;
        try {
            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator iterator = upload.getItemIterator(req);
            //处理前端上传的数据项
            while ((iterator.hasNext())) {
                FileItemStream item = iterator.next();
                if (!item.isFormField() && "image".equals(item.getFieldName())) {
                    //非表单域，即文件
                    InputStream inputStream = item.openStream();    //获得输入流
                    img_url = "D:\\web-music\\user_image\\" + item.getName();
                    File file = new File(img_url);
                    //避免文件重名
                    int index = 1;
                    while (file.exists()) {
                        img_url = "D:\\web-music\\user_image\\" + "(" + index + ")" + item.getName();
                        file.renameTo(new File(img_url));
                        index++;
                    }
                    OutputStream outputStream = new FileOutputStream(file);
                    Streams.copy(inputStream, outputStream, true);
                } else {
                    //表单域
                    if ("username".equals(item.getFieldName()))
                        username = Streams.asString(item.openStream());
                    else
                        password = Streams.asString(item.openStream());
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(username + ";" + password + ";" + img_url);

        //2. 封装user对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setImg_url(img_url);

        //3. service完成注册
        UserService userService = new UserServiceImpl();
        boolean flag = userService.regist(user);
        ResultInfo info = new ResultInfo();

        //4. 响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);
            System.out.println("成功");
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
            System.out.println("失败");
            //将头像图片删除
            File file = new File(img_url);
            if (file.exists()) {
                file.delete();
            }
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
        resp.sendRedirect(req.getContextPath() + "/loginbox.html");
    }

    public void findLoginUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从Session中取得登录用户
        Object user = req.getSession().getAttribute("user");
        writeJsonValue(resp, user);
    }

    public void getUserImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取参数
        String username = req.getParameter("username");
        //2.调用服务
        UserService userService = new UserServiceImpl();
        String img_url = userService.getImageByName(username);
        //3.结果写回客户端
        writeJsonValue(resp, img_url);
    }
}
