package cn.itcast.music.dao;

import cn.itcast.music.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}
