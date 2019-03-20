package com.imooc.test;

import com.imooc.dao.StudentsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * @Author zhangxiaoliang98
 * @Date 3/20/19 9:04 AM
 * @Description
 */
public class StudentTest {

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
        transaction = session.beginTransaction();

    }

    @After
    public void destory(){

        transaction.commit();       // 提交事务
        session.close();            // 关闭会话
        sessionFactory.close();     // 关闭会话工厂
    }

    @Test
    public void testSaveStudent(){

        // 生成学生对象
        StudentsEntity student = new StudentsEntity(1, "张三", "男", new Date(), "上海");
        System.out.println(student);

        // 保存数据进数据库
        session.save(student);


    }
}
