import com.itheima.dao.CheckGroupDao;
import com.itheima.pojo.CheckGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-dao.xml")
public class Demo {
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Test
    public void demo(){
        CheckGroup checkGroup = new CheckGroup("111","sa","sax","1","sca","wqf");
        checkGroupDao.add(checkGroup);
        System.out.println(checkGroup.getId());
//        checkGroupDao.addGroupAndItem(integer,34);

    }
}
