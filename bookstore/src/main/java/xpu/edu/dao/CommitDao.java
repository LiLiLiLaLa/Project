package xpu.edu.dao;

public interface CommitDao {
    /**
     * 发布评论
     */
    public boolean addComment(String commit_id,String comment);
    /**
     * 删除评论
     */
    public boolean addComment(String commit_id);
}
