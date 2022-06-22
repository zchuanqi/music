package cn.itcast.music.music_service.impl;

import cn.itcast.music.music_dao.MusicDao;
import cn.itcast.music.music_dao.impl.MusicDaoImpl;
import cn.itcast.music.music_domain.Music;
import cn.itcast.music.music_domain.PageBean;
import cn.itcast.music.music_service.MusicService;

import java.util.List;

public class MusicServiceImpl implements MusicService {

    private MusicDao musicDao = new MusicDaoImpl();

    @Override
    public void upload(Music music) {
        musicDao.upload(music);
    }

    @Override
    public Music findById(int mid) {
        return musicDao.findById(mid);
    }

    @Override
    public PageBean<Music> queryPage(int currentPage, int pageSize, String keyword) {
        PageBean<Music> pageBean = new PageBean<Music>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);

        int totalCount = musicDao.findTotal(keyword);
        pageBean.setTotalCount(totalCount);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pageBean.setTotalPage(totalPage);

        if (currentPage > totalPage)
            currentPage = totalPage;

        int start = (currentPage - 1) * pageSize;
        List<Music> list = musicDao.getMusicPage(start, pageSize, keyword);
        return pageBean;
    }
}
