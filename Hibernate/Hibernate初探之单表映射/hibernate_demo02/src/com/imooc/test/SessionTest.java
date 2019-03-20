package com.imooc.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

/**
 * @Author zhangxiaoliang98
 * @Date 3/20/19 10:42 AM
 * @Description     session 对象获取的两种方式
 */
public class SessionTest {

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
        Session session = sessionFactory.openSession();

        if (session != null) {
            System.out.println("session 创建成功！");
        } else {
            System.out.println(" session 创建失败！");
        }
    }

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
        Session session = sessionFactory.getCurrentSession();

        if (session != null) {
            System.out.println("session 创建成功！");
        } else {
            System.out.println(" session 创建失败！");
        }
    }
}
