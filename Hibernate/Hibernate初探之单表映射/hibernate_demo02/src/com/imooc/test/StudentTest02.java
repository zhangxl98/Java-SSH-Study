package com.imooc.test;

import com.imooc.dao.StudentsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * @Author zhangxiaoliang98
 * @Date 3/20/19 9:04 AM
 * @Description
 *
 *          不开启事务 transaction
 *          使用 session.doWork() 自动提交
 */
public class StudentTest02 {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init(){

        // 创建配置对象
        Configuration configure = new Configuration().configure();

        // 创建服务注册对象
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();

        // 创建会话工厂对象
        sessionFactory = configure.buildSessionFactory(serviceRegistry);
//        SessionFactory sessionFactory = configure.buildSessionFactory();
        // 会话对象
        session = sessionFactory.openSession();

        // 开启事务
//        transaction = session.beginTransaction();

    }

    @After
    public void destory(){

//        transaction.commit();       // 提交事务
        session.close();            // 关闭会话
        sessionFactory.close();     // 关闭会话工厂
    }

    @Test
    public void testSaveStudent(){

        // 生成学生对象
        StudentsEntity student = new StudentsEntity(1,"张三", "男", new Date(), "北京");
        System.out.println(student);

        // 使用 session.doWork() 自动提交
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                // 设为 true 自动提交模式
                connection.setAutoCommit(true);
            }
        });

        // 保存数据进数据库
        session.save(student);


        // 必须强制输出
        session.flush();


    }
}
