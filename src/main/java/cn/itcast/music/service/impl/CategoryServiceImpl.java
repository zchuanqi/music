package cn.itcast.music.service.impl;

import cn.itcast.music.dao.CategoryDao;
import cn.itcast.music.dao.impl.CategoryDaoImpl;
import cn.itcast.music.domain.Category;
import cn.itcast.music.service.CategoryService;
import cn.itcast.music.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        List<Category> cs;
        // 从redis缓存查信息
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category",0,-1);
        if(categorys == null || categorys.size() == 0){
            System.out.println("从数据库查询......");
            cs = categoryDao.findAll();
            //放到Redis缓存中
            for(int i=0;i<cs.size();i++){
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
            System.out.println("从Redis查询......");
            cs = new ArrayList<>();
            for(Tuple t : categorys){
                Category category = new Category();
                category.setCname(t.getElement());
                category.setCid((int)t.getScore());
                cs.add(category);
                System.out.println(category);
            }
        }

        return cs;
    }
}
