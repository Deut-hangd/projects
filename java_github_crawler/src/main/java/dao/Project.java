package dao;

public class Project {
    //项目名, 对应 a 标签中的内容
   private String name;
   //项目主页链接, 对应 a 标签中的 href 属性
   private String url;
   //项目的描述信息, 对应到 li 标签里的内容
   private String description;
   //从 url 进入页面, 获取对应的信息
   private int starCount;
   private int forkCount;
   private int openIssueCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int startCount) {
        this.starCount = startCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public int getOpenIssueCount() {
        return openIssueCount;
    }

    public void setOpenIssueCount(int openIssueCount) {
        this.openIssueCount = openIssueCount;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", startCount=" + starCount +
                ", forkCount=" + forkCount +
                ", openIssueCount=" + openIssueCount +
                '}';
    }
}
