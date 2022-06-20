package cn.itcast.music.service;

import cn.itcast.music.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
}
