package com.imooc.test;

import com.imooc.dao.StudentsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * @Author zhangxiaoliang98
 * @Date 3/20/19 10:42 AM
 * @Description session 对象获取的两种方式的区别
 */

/*
    openSession() 方法 和  getCurrentSession() 方法  的区别

    1.getCurrentSession 在事务提交或者回滚之后会自动关闭，而 openSession 需要你手动关闭。
        如果使用 openSession 而没有手动关闭，多次之后会导致连接池溢出。

    2.openSession 每次创建新的 session 对象，getCurrentSession 只用现有的 session 对象（同一个对象）。
 */
public class SessionTest02 {

    // 使用 openSession() 方法
    @Test
    public void testOpenSession() {

        // 获得配置对象
        Configuration configure = new Configuration().configure();

        // 获得服务注册对象
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();

        // 获得 SessionFactory 对象
        SessionFactory sessionFactory = configure.buildSessionFactory(serviceRegistry);

        // 获得 session 对象
        // 使用 openSession() 方法
        // 创建两个对象
        Session session01 = sessionFactory.openSession();
        Session session02 = sessionFactory.openSession();

        System.out.println(session01 == session02); //false


    }

    // 使用 getCurrentSession() 方法
    @Test
    public void testGetCurrentSession() {

        // 获得配置对象
        Configuration configure = new Configuration().configure();

        // 获得服务注册对象
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();

        // 获得 SessionFactory 对象
        SessionFactory sessionFactory = configure.buildSessionFactory(serviceRegistry);

        // 获得 session 对象
        // 使用 getCurrentSession() 方法
        // 如果使用 getCurrentSession() 方法 来获得 session 对象，需要进行配置
        // 见 hibernate.cfg.xml
        // 创建两个对象
        Session session01 = sessionFactory.getCurrentSession();
        Session session02 = sessionFactory.getCurrentSession();

        System.out.println(session01 == session02); //true  指向同一个对象

    }


/*
    使用 OpenSession() 方法，不关闭 session

    两次的 hashCode() 不同
        connection.hashCode 01: 1248234350
        connection.hashCode 02: 70323523
 */
    @Test
    public void testSaveStudentWWithOpenSession(){

        // 获得配置对象
        Configuration configure = new Configuration().configure();

        // 获得服务注册对象
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();

        // 获得 SessionFactory 对象
        SessionFactory sessionFactory = configure.buildSessionFactory(serviceRegistry);

        // 创建 session 对象
        Session session01 = sessionFactory.openSession();

        // 开启事务
        Transaction transaction = session01.beginTransaction();

        // 生成一个 student 对象
        StudentsEntity student01 = new StudentsEntity(1,"张三", "男", new Date(), "北京");

        session01.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println("connection.hashCode 01: "+connection.hashCode());   // connection.hashCode 01: 1248234350
            }
        });

        // 保存 student 对象
        session01.save(student01);

        // 不进行关闭
//        session01.close();

        // 提交事务
        transaction.commit();


        // 创建第二个对象

        // 创建 session 对象
        Session session02 = sessionFactory.openSession();

        // 开启事务
        transaction = session02.beginTransaction();

        // 生成一个 student 对象
        StudentsEntity student02 = new StudentsEntity(2,"李四", "男", new Date(), "上海");

        session02.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println("connection.hashCode 02: "+connection.hashCode());   // connection.hashCode 02: 70323523
            }
        });

        // 保存 student 对象
        session02.save(student02);

        // 提交事务
        transaction.commit();

    }



    /*
        使用 GetCurrentSession() 方法，不关闭 session

        两次的 hashCode() 相同
            connection.hashCode 01: 1785397234
            connection.hashCode 02: 1785397234
     */

    @Test
    public void testSaveStudentWWithGetCurrentSession(){

        // 获得配置对象
        Configuration configure = new Configuration().configure();

        // 获得服务注册对象
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();

        // 获得 SessionFactory 对象
        SessionFactory sessionFactory = configure.buildSessionFactory(serviceRegistry);

        // 创建 session 对象
        Session session01 = sessionFactory.getCurrentSession();

        // 开启事务
        Transaction transaction = session01.beginTransaction();

        // 生成一个 student 对象
        StudentsEntity student01 = new StudentsEntity(1,"张三", "男", new Date(), "北京");

        session01.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println("connection.hashCode 01: "+connection.hashCode());   // connection.hashCode 01: 1785397234
            }
        });

        // 保存 student 对象
        session01.save(student01);

        // 不需要进行关闭  会自动关闭
//        session01.close();

        // 提交事务
        transaction.commit();


        // 创建第二个对象

        // 创建 session 对象
        Session session02 = sessionFactory.getCurrentSession();

        // 开启事务
        transaction = session02.beginTransaction();

        // 生成一个 student 对象
        StudentsEntity student02 = new StudentsEntity(2,"李四", "男", new Date(), "上海");

        session02.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println("connection.hashCode 02: "+connection.hashCode());   // connection.hashCode 02: 1785397234
            }
        });

        // 保存 student 对象
        session02.save(student02);

        // 提交事务
        transaction.commit();
    }
}
