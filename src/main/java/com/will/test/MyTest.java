package com.will.test;

import com.will.bean.ComponentScanBean;
import com.will.bean.Person;
import com.will.bean.Student;
import com.will.spi.ISpiService;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import sun.misc.Service;

public class MyTest{

    /*
    * spring
    * */
    @Test
    public void test1() {
        /*
        * 单元测试的用一下
        * */
        //AnnotationConfigApplicationContext appcationContext =
        //    new AnnotationConfigApplicationContext("com.will.bean");
        AnnotationConfigApplicationContext appcationContext =
            new AnnotationConfigApplicationContext(ComponentScanBean.class);

        Person person = (Person) appcationContext.getBean("person");
        System.out.println(person.getStudent().getUsername());
    }

    @Test
    public void test2() {
        //基于xml的方式加载spring的配置文件，启动spring容器
        //springboot
        ClassPathXmlApplicationContext appcationContext = new ClassPathXmlApplicationContext("spring.xml");
        Student student = (Student)appcationContext.getBean("student");
        System.out.println(student.getUsername());

/*        appcationContext.setAllowCircularReferences(true);
        appcationContext.setAllowBeanDefinitionOverriding(true);
        appcationContext.refresh();*/
    }

    @Test
    public void test3() {
        //基本上不用
        new FileSystemXmlApplicationContext("");
    }

  /*  public void test4() {
        //这个springboot在启动的时候就会用到这个上下文来，启动spring容器，new Tomcat 嵌入式tomcat
        new EmbeddedWebApplicationContext();
    }*/

    /**
     * 测试spi
     */
    @Test
    public void test5(){
        //方式1
        Iterator<ISpiService> providers = Service.providers(ISpiService.class);
        while(providers.hasNext()){
            providers.next().test();
        }
        System.out.println(
            "====================================分割线====================================");
        //方式2
        ServiceLoader<ISpiService> load = ServiceLoader.load(ISpiService.class);
        Iterator<ISpiService> iterator = load.iterator();
        while (iterator.hasNext()){
            iterator.next().test();
        }
    }

    @Test
    public void test6() throws ParseException {
        String tempTimeString = "2021-06-15";
        //1.方式1
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(tempTimeString));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));

        //2.方式2
        LocalDate localDateTime = LocalDate.parse(tempTimeString);
        System.out.println(localDateTime.getDayOfMonth());
    }

    @Test
    public void test7() {
        String[] names = new String[]{"will","zhang","zhang2"};
        List<String> nameList = Arrays.asList("will01","will02","zhang1","zhang2");
        int maxLen0 = nameList.parallelStream().filter(name -> (name.startsWith("zhang")))
            .mapToInt(String::length).max().getAsInt();
        System.out.println(maxLen0);
        int maxLen1 = Arrays.stream(names).filter(name -> (name.startsWith("zhang")))
            .mapToInt(String::length).max().getAsInt();
        System.out.println(maxLen1);
    }

    @Test
    public void test8() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition1 = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();
        new Thread(()->{
            try {
                reentrantLock.lock();
                condition1.await();
                System.out.println("thread 111111");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }
        }).start();
        new Thread(()->{
            try {
                reentrantLock.lock();
                condition1.await();
                System.out.println("thread 222222");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }
        }).start();
        new Thread(()->{
            try {
                reentrantLock.lock();
                condition2.await();
                System.out.println("thread 3333333");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }
        }).start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("main==========================");

        new Thread(()->{
            try{
                reentrantLock.lock();
                //condition1.signalAll();
                condition1.signal();
                condition2.signal();
            }finally {
                reentrantLock.unlock();
            }
        }).start();


        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test9() {
        String s = String.valueOf(-1.25);
        System.out.println(s);
    }
}
