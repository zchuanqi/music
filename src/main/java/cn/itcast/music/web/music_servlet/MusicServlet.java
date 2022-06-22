package cn.itcast.music.web.music_servlet;

import cn.itcast.music.music_domain.Music;
import cn.itcast.music.music_domain.PageBean;
import cn.itcast.music.music_domain.User;
import cn.itcast.music.music_service.FavoriteService;
import cn.itcast.music.music_service.MusicService;
import cn.itcast.music.music_service.impl.FavoriteServiceImpl;
import cn.itcast.music.music_service.impl.MusicServiceImpl;
import cn.itcast.music.web.servlet.BaseServlet;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/music/music/*")
public class MusicServlet extends BaseServlet {
    //上传音乐，需要得到音乐名、封面、音频字节流
    public void upload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.处理数据
        String mname = null;
        String url = null;
        String img_url = null;
        String share_user = null;
        try {
            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator iterator = upload.getItemIterator(req);
            //处理前端上传的数据项
            while ((iterator.hasNext())) {
                FileItemStream item = iterator.next();
                if (!item.isFormField()) {
                    //非表单域，文件
                    if ("music".equals(item.getFieldName())) {
                        //音频文件
                        InputStream music_stream = item.openStream();
                        url = "D:\\web-music\\music\\" + item.getName();
                        File music_file = new File(url);
                        //避免文件重名
                        int index = 1;
                        while (music_file.exists()) {
                            url = "D:\\web-music\\music\\" + "(" + index + ")" + item.getName();
                            music_file.renameTo(new File(url));
                            index++;
                        }
                        OutputStream outputStream = new FileOutputStream(music_file);
                        Streams.copy(music_stream, outputStream, true);
                    } else if ("image".equals(item.getFieldName())) {
                        //图片文件
                        InputStream img_stream = item.openStream();
                        img_url = "D:\\web-music\\music_image\\" + item.getName();
                        File img_file = new File(img_url);
                        //避免文件重名
                        int index = 1;
                        while (img_file.exists()) {
                            img_url = "D:\\web-music\\music_image\\" + "(" + index + ")" + item.getName();
                            img_file.renameTo(new File(img_url));
                            index++;
                        }
                        OutputStream outputStream = new FileOutputStream(img_file);
                        Streams.copy(img_stream, outputStream, true);
                    }
                } else {
                    //表单域
                    if ("mname".equals(item.getFieldName()))
                        mname = Streams.asString(item.openStream());
                    else if ("share_user".equals(item.getFieldName()))
                        share_user = Streams.asString(item.openStream());
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }

        //2.封装Music对象
        Music music = new Music();
        music.setMname(mname);
        music.setUrl(url);
        music.setImg_url(img_url);
        music.setShare_user(share_user);

        //3.调用service上传Music
        MusicService musicService = new MusicServiceImpl();
        musicService.upload(music);
    }

    //返回Music列表
    public void queryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");

        //获取搜索关键字
        String keyword = req.getParameter("keyword");
        keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");

        int pageSize = 10;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        int currentPage = 1;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        }

        MusicService musicService = new MusicServiceImpl();
        PageBean<Music> pageBean = musicService.queryPage(currentPage, pageSize, keyword);
        writeJsonValue(resp, pageBean);
    }

    //判断是否收藏音乐
    public void isFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.获取音乐id
        String mid = req.getParameter("mid");

        //2.获取当前已经登录的User
        User loginUser = (User) req.getSession().getAttribute("user");
        String username = null;      //获取用户名
        if (loginUser != null) {
            username = loginUser.getUsername();
        }

        if (username == null) {
            writeJsonValue(resp, false);
            return;
        }

        //3.调用service判断是否收藏
        FavoriteService favoriteService = new FavoriteServiceImpl();
        boolean flag = favoriteService.isFavorite(username, Integer.parseInt(mid));

        //4.返回结果
        writeJsonValue(resp, flag);
    }

    //收藏音乐
    public void addFavorite(HttpServletRequest req, HttpServletResponse resp) {
        //1.获取音乐id
        String mid = req.getParameter("mid");

        //2.获取已登录用户
        User loginUser = (User) req.getSession().getAttribute("user");
        String username = null;      //获取用户名
        if (loginUser != null) {
            username = loginUser.getUsername();
        }

        if (username == null)
            return;

        //3.添加收藏
        FavoriteService favoriteService = new FavoriteServiceImpl();
        favoriteService.collect(username, Integer.parseInt(username));
    }

    //取消收藏
    public void removeFavorite(HttpServletRequest req, HttpServletResponse resp) {
        //1.获取音乐id
        String mid = req.getParameter("mid");

        //2.获取已登录的用户
        User loginUser = (User) req.getSession().getAttribute("user");
        String username = null;      //获取用户名
        if (loginUser != null) {
            username = loginUser.getUsername();
        }

        if (username == null)
            return;

        //3.移除收藏
        FavoriteService favoriteService = new FavoriteServiceImpl();
        favoriteService.removeCollect(username, Integer.parseInt(mid));
    }
}
