package cn.itcast.music.web.music_servlet;

import cn.itcast.music.music_domain.Music;
import cn.itcast.music.music_service.MusicService;
import cn.itcast.music.music_service.impl.MusicServiceImpl;
import cn.itcast.music.util.ByteUtil;
import cn.itcast.music.web.servlet.BaseServlet;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3FileReader;
import org.jaudiotagger.tag.TagException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/music/music/*")
public class MusicServlet extends BaseServlet {

    private MusicService musicService = new MusicServiceImpl();

    public void upload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取音乐名
        String file_name = req.getParameter("file_name");

        //获取音乐名、文件后缀名
        int split = file_name.lastIndexOf(".");
        String mname = file_name.substring(0, split - 1);
        String suffix = file_name.substring(split);

        //文件字节流保存到文件
        File file = new File("D:\\web-music\\music_home\\" + mname + suffix);
        //防止文件重名
        int i = 1;
        while (file.exists()) {
            mname = mname + Integer.toString(i++);
            file = new File("D:\\web-music\\music_home\\" + mname + suffix);
        }
        ByteUtil.inputStream_to_file(req.getPart("music_bytes").getInputStream(), file);
    }

    public void display_music(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取mid
        String mid = req.getParameter("mid");
        //根据mid得到Music对象
        Music music = musicService.findById(Integer.parseInt(mid));
        //封装MusicDisplayer对象
    }
}
