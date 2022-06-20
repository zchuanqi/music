package cn.itcast.music.music_service.impl;

import cn.itcast.music.music_dao.MusicDao;
import cn.itcast.music.music_dao.impl.MusicDaoImpl;
import cn.itcast.music.music_domain.Music;
import cn.itcast.music.music_service.MusicService;

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
}
