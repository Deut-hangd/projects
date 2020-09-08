package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Project;
import dao.ProjectDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/allRank")
public class AllRankServlet extends HttpServlet{
    private Gson gson = new GsonBuilder().create();
    // 处理客户端的请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        //解析请求,获取日期参数
        String date = req.getParameter("date").trim();
        if (date == null || "".equals(date)){
            resp.setStatus(404);
            resp.getWriter().println("请求参数错误!");
            return;
        }

        //从数据库中查找数据
        ProjectDao projectDao = new ProjectDao();
        List<Project> projects = projectDao.selectProjectByDate(date);

        //将数据整理成json格式,返回给客户端
        String respString = gson.toJson(projects);
        resp.getWriter().write(respString);
    }
}
