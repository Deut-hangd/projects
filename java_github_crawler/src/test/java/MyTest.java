import dao.DBUtil;
import org.junit.Assert;
import org.junit.Test;

public class MyTest {
    @Test
    public void connection(){
        Assert.assertNotNull(DBUtil.getConnection());
    }

}
