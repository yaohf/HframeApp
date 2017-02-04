package yaohf.com.model.db;

/**
 * 基础接口
 */

public interface DBInterface<T> {

    public void addInfo(T item);
    public void deleteInfo(T item);
    public T getInfo(T item);
    public void updateInfo(T item);
}
