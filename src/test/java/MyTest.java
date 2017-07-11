import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.reey.VMSweb.dao.UserDAO;

/**   
 * filename：MyTest.java
 *   
 * date：2016年4月22日
 * Copyright reey Corporation 2016 
 *   
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath*:spring-hibernate.xml",
	"classpath*:spring-other.xml",
})
public class MyTest {


	@Resource
	private UserDAO userDao;
	
	@Test
	public void test() {
		//System.out.println(userDao.findByName("mifan"));
	}
}
