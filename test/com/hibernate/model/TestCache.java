package com.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCache {
	public static SessionFactory sf=null;
	@BeforeClass
	public static void beforeClass(){
		sf=new Configuration().configure().buildSessionFactory();
	}
	@AfterClass
	public static void afterClass(){
		sf.close();
	}
	@Test
	public void testSchmaExport(){
		ServiceRegistry serviceRegistry=new StandardServiceRegistryBuilder().configure().build();
		MetadataImplementor metadata=(MetadataImplementor)new MetadataSources(serviceRegistry).buildMetadata();
		new SchemaExport(metadata).create(false,true);
	}

	@Test
	public void testSaveStudent() {
		Student s1=new Student();
		Student s2=new Student();
		Student s3=new Student();
		s1.setName("s1");
		s1.setAge(18);
		s2.setName("s2");
		s2.setAge(19);
		s3.setName("s3");
		s3.setAge(20);
		
		Session s=sf.getCurrentSession();
		s.beginTransaction();
		s.save(s1);
		s.save(s2);
		s.save(s3);
		s.getTransaction().commit();
		
	}

	@Test
	public void testCache1(){
		Session s=sf.getCurrentSession();
		s.beginTransaction();
		Student s1=s.load(Student.class, 1);
		System.out.println(s1.getName());
		Student s2=s.load(Student.class, 1);
		System.out.println(s2.getName());
		s.getTransaction().commit();
	}
	@Test
	public void testCache2(){
		Session s=sf.getCurrentSession();
		s.beginTransaction();
		Student s1=s.load(Student.class, 1);
		System.out.println(s1.getName());
		s.getTransaction().commit();
		
		Session s2=sf.getCurrentSession();
		s2.beginTransaction();
		Student s3=s2.load(Student.class, 1);
		System.out.println(s3.getName());
		s2.getTransaction().commit();
	}

	public static void main(String[] args){
		beforeClass();
	}
}
