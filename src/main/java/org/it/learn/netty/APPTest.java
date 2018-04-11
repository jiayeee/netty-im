package org.it.learn.netty;

import org.it.learn.netty.common.Invoker;
import org.it.learn.netty.common.InvokerHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by lwz on 2017/06/22 11:20.
 */
@ContextConfiguration(locations = { "classpath:spring.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class APPTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void testTx() {
        TransactionTemplate txTemplate = ctx.getBean(TransactionTemplate.class);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testApp() {
        Invoker invoker = InvokerHolder.getInvoker((short) 1, (short) 1);
        invoker.invoke(null);
    }
}
