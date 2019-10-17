package gp.mybatis.custom.v2.cache;

public class CacheKey {

    private static final int DEFAULT_HASHCODE = 17;// 默认哈希值
    private static final int DEFAULT_MULTIPLIER = 37;

    private int hashCode;
    private int count;
    private int multiplier;

    public CacheKey() {
        this.hashCode = DEFAULT_HASHCODE;
        this.multiplier = DEFAULT_MULTIPLIER;
        this.count = 0;
    }

    public int getCode() {
        return hashCode;
    }

    /**
     * 计算CacheKey的hashcode
     * @param object
     */
    public void update(Object object) {
        int baseHashCode = object == null ? 1 : object.hashCode();
        count++;
        baseHashCode *= count;
        hashCode = multiplier * hashCode + baseHashCode;
    }
}
