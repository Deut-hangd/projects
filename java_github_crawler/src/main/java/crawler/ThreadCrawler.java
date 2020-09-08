package crawler;

import dao.Project;
import dao.ProjectDao;

import javax.swing.tree.FixedHeightLayoutCache;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadCrawler extends Crawler {

    public static void main(String[] args) throws IOException {
        //使用多线程的方式改写核心逻辑(GitHub api)的调用, 即并行式访问 github api
        ThreadCrawler threadCrawler = new ThreadCrawler();

        // 1.获取首页内容
        String html = threadCrawler.getPage("https://github.com/akullpp/awesome-java/blob/master/README.md");

        // 2.解析项目列表并将关键信息保存进 project 对象
        List<Project> projects = threadCrawler.parseProjectList(html);

        // 线程结果集
        List<Future<?>> taskResults = new ArrayList<>();

        //
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (Project project : projects){
            Future<?> result = executorService.submit(new CrawlerTask(project, threadCrawler));
            taskResults.add(result);
        }

        for (Future<?> taskResult : taskResults){
            try {
                taskResult.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();

        ProjectDao projectDao = new ProjectDao();
        for (Project project : projects){
            projectDao.save(project);
        }
    }

    static class CrawlerTask implements Runnable{
        private Project project;
        private ThreadCrawler threadCrawler;

        public CrawlerTask(Project project, ThreadCrawler threadCrawler) {
            this.project = project;
            this.threadCrawler = threadCrawler;
        }

        @Override
        public void run() {
            //
            try {
                System.out.println("crawing" + project.getName() + "...");
                String repoName = threadCrawler.getRepoName(project.getUrl());
                String jsonString = threadCrawler.getRepoInfo(repoName);
                threadCrawler.parseRepoInfo(jsonString, project);
                System.out.println("crawing" + project.getName() + " done!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
