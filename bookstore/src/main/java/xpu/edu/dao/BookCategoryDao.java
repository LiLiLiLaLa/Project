package xpu.edu.dao;

public interface BookCategoryDao {
    /**
     * 添加分类
     */
    public boolean addCategory(int category_id,String category_name);
    /**
     * 删除分类
     */
    public boolean deleteCategory(int category_id);
    /**
     * 修改分类名
     */
    public boolean updateCategory(int category_id);
}
