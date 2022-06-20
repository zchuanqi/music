package cn.itcast.music.web.servlet;

import cn.itcast.music.domain.Category;
import cn.itcast.music.service.CategoryService;
import cn.itcast.music.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet{
    private CategoryService categoryService = new CategoryServiceImpl();

    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Category> all = categoryService.findAll();
        writeJsonValue(resp,all);
    }
}
