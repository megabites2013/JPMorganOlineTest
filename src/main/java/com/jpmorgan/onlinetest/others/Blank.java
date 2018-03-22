package com.jpmorgan.onlinetest.others;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.EmptyStackException;

//playgroud



/*
与垃圾回收相关的JVM参数：



-Xms / -Xmx --- 堆的初始大小 / 堆的最大大小
-Xmn --- 堆中年轻代的大小
-XX:-DisableExplicitGC --- 让System.gc()不产生任何作用
-XX:+PrintGCDetail --- 打印GC的细节
-XX:+PrintGCDateStamps --- 打印GC操作的时间戳

 */


/*
怎样将GB2312编码的字符串转换为ISO-8859-1编码的字符串？阴吹思婷

答：代码如下所示:

String s1 = "你好";

String s2 = newString(s1.getBytes("GB2312"), "ISO-8859-1");
 */


public class Blank {

    int[] []x[];
    short s = 28;
    //float f = 2.3;
    double d = 2.3;
    int I = '1';

    	int arr1[][] = new int[5][5];
    	int []arr2[] = new int[5][5];
    	int[][] arr3 = new int[5][5];

    public static void main(String args[]){
        int i1=0, j1=2;
        do {
            i1=++i1;
            j1--;
        } while(j1>0);
        System.out.println(i1);


        for(int i = 1; i < 4; i++)
            for(int j = 1; j < 4; j++)
                if(i < j)
                    assert i!=j : i;

        for(int i = 1; i < 3; i++)
            for(int j = 3; j >= 1; j--)
                assert i!=j : i;


        int i,j,k,l=0;
        k = l++;
        j = ++k;
        i = j++;
        System.out.println(i);
        //int x;   if you use it later, 编译器才会报未初始化


        System.out.println(14^23);

    }

}

//An anonymous class may implement an interface or extend a superclass, but may not be declared to do both.
abstract class abc1{}

abstract class abc2 extends abc1{}

class Compare {
    public static void main(String args[]) {
        //int x = 10, y; //y not initialized
        int x = 10, y=0;
        if(x < 10)
            y = 1;
        if(x>= 10) y = 2;
        System.out.println("y is " + y);
    }
}






/*
25、Java 中会存在内存泄漏吗，请简单描述。

答：理论上Java因为有垃圾回收机制（GC）不会存在内存泄露问题（这也是Java被广泛使用于服务器端编程的一个重要原因）；
然而在实际开发中，可能会存在无用但可达的对象，这些对象不能被GC回收也会发生内存泄露。
一个例子就是Hibernate的Session（一级缓存）中的对象属于持久态，垃圾回收器是不会回收这些对象的，然而这些对象中可能存在无用的垃圾对象。

下面的例子也展示了Java中发生内存泄露的情况：

xia面的代码实现了一个栈（先进后出（FILO））结构，乍看之下似乎没有什么明显的问题，它甚至可以通过你编写的各种单元测试。
然而其中的pop方法却存在内存泄露的问题，当我们用pop方法弹出栈中的对象时，该对象不会被当作垃圾回收，
即使使用栈的程序不再引用这些对象，因为栈内部维护着对这些对象的过期引用（obsolete reference）。
在支持垃圾回收的语言中，内存泄露是很隐蔽的，这种内存泄露其实就是无意识的对象保持。如果一个对象引用被无意识的保留起来了，
那么垃圾回收器不会处理这个对象，也不会处理该对象引用的其他对象，即使这样的对象只有少数几个，也可能会导致很多的对象被排除在垃圾回收之外，
从而对性能造成重大影响，极端情况下会引发Disk Paging（物理内存与硬盘的虚拟内存交换数据），甚至造成OutOfMemoryError。


 */

class MyStack<T> {
    private T[] elements;
    private int size = 0;

    private static final int INIT_CAPACITY = 16;

    public MyStack() {
        elements = (T[]) new Object[INIT_CAPACITY];
    }

    public void push(T elem) {
        ensureCapacity();
        elements[size++] = elem;
    }

    public T pop() {
        if(size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }

    private void ensureCapacity() {
        if(elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}


/*
29、如何实现对象克隆？

答：有两种方式：

1.实现Cloneable接口并重写Object类中的clone()方法；

2.实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆，代码如下。
 */


 class MyUtil {

    private MyUtil() {
        throw new AssertionError();
    }

    public static <T> T clone(T obj) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();

        // 说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
        // 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源
    }
}

/*
开放问题:


用一句话概括 Web 编程的特点
Google是如何在一秒内把搜索结果返回给用户
哪种依赖注入方式你建议使用，构造器注入，还是 Setter方法注入
树（二叉或其他）形成许多普通数据结构的基础。请描述一些这样的数据结构以及何时可以使用它们
某一项功能如何设计
线上系统突然变得异常缓慢，你如何查找问题
什么样的项目不适合用框架
新浪微博是如何实现把微博推给订阅者
简要介绍下从浏览器输入 URL 开始到获取到请求界面之后 Java Web 应用中发生了什么
请你谈谈SSH整合
高并发下，如何做到安全的修改同一行数据
12306网站的订票系统如何实现，如何保证不会票不被超卖
网站性能优化如何优化的
聊了下曾经参与设计的服务器架构
请思考一个方案，实现分布式环境下的 countDownLatch
请思考一个方案，设计一个可以控制缓存总体大小的自动适应的本地缓存
在你的职业生涯中，算得上最困难的技术挑战是什么
如何写一篇设计文档，目录是什么
大写的O是什么？举几个例子
编程中自己都怎么考虑一些设计原则的，比如开闭原则，以及在工作中的应用
解释一下网络应用的模式及其特点
设计一个在线文档系统，文档可以被编辑，如何防止多人同时对同一份文档进行编辑更新
说出数据连接池的工作机制是什么
怎么获取一个文件中单词出现的最高频率
描述一下你最常用的编程风格
如果有机会重新设计你们的产品，你会怎么做
如何搭建一个高可用系统
如何启动时不需输入用户名与密码
如何在基于Java的Web项目中实现文件上传和下载
如何实现一个秒杀系统，保证只有几位用户能买到某件商品。
如何实现负载均衡，有哪些算法可以实现
如何设计一个购物车？想想淘宝的购物车如何实现的
如何设计一套高并发支付方案，架构如何设计
如何设计建立和保持 100w 的长连接
如何避免浏览器缓存。
如何防止缓存雪崩
如果AB两个系统互相依赖，如何解除依
如果有人恶意创建非法连接，怎么解决
如果有几十亿的白名单，每天白天需要高并发查询，晚上需要更新一次，如何设计这个功能
如果系统要使用超大整数（超过long长度范围），请你设计一个数据结构来存储这种超大型数字以及设计一种算法来实现超大整数加法运算）
如果要设计一个图形系统，请你设计基本的图形元件(Point,Line,Rectangle,Triangle)的简单实现
如果让你实现一个并发安全的链表，你会怎么做
应用服务器与WEB 服务器的区别？应用服务器怎么监控性能，各种方式的区别？你使用过的应用服务器优化技术有哪些
大型网站在架构上应当考虑哪些问题
有没有处理过线上问题？出现内存泄露，CPU利用率标高，应用无响应时如何处理的
最近看什么书，印象最深刻的是什么
描述下常用的重构技巧
你使用什么版本管理工具？分支（Branch）与标签（Tag）之间的区别在哪里
你有了解过存在哪些反模式（Anti-Patterns）吗
你用过的网站前端优化的技术有哪些
如何分析Thread dump
你如何理解AOP中的连接点（Joinpoint）、切点（Pointcut）、增强（Advice）、引介（Introduction）、织入（Weaving）、切面（Aspect）这些概念
你是如何处理内存泄露或者栈溢出问题的
你们线上应用的 JVM 参数有哪些
怎么提升系统的QPS和吞吐量
知识面
解释什么是 MESI 协议(缓存一致性)
谈谈 reactor 模型
Java 9 带来了怎样的新功能
Java 与 C++ 对比，C++ 或 Java 中的异常处理机制的简单原理和应用
简单讲讲 Tomcat 结构，以及其类加载器流程
虚拟内存是什么
阐述下 SOLID 原则
请简要讲一下你对测试驱动开发（TDD）的认识
CDN实现原理
Maven 和 ANT 有什么区别
UML中有哪些常用的图
Linux
Linux 下 IO 模型有几种，各自的含义是什么。
Linux 系统下你关注过哪些内核参数，说说你知道的
Linux 下用一行命令查看文件的最后五行
平时用到哪些 Linux 命令
用一行命令输出正在运行的 Java 进程
使用什么命令来确定是否有 Tomcat 实例运行在机器上
什么是 N+1 难题
什么是 paxos 算法
什么是 restful，讲讲你理解的 restful
什么是 zab 协议
什么是领域模型(domain model)？贫血模型(anaemic domain model) 和充血模型(rich domain model)有什么区别
什么是领域驱动开发（Domain Driven Development）
介绍一下了解的 Java 领域的 Web Service 框架
Web Server、Web Container 与 Application Server 的区别是什么
微服务（MicroServices）与巨石型应用（Monolithic Applications）之间的区别在哪里
描述 Cookie 和 Session 的作用，区别和各自的应用范围，Session工作原理
你常用的持续集成（Continuous Integration）、静态代码分析（Static Code Analysis）工具有哪些
简述下数据库正则化（Normalizations）
KISS,DRY,YAGNI 等原则是什么含义
分布式事务的原理，优缺点，如何使用分布式事务？
布式集群下如何做到唯一序列号
网络
HTTPS 的加密方式是什么，讲讲整个加密解密流程
HTTPS和HTTP的区别
HTTP连接池实现原理
HTTP集群方案
Nginx、lighttpd、Apache三大主流 Web服务器的区别
是否看过框架的一些代码
持久层设计要考虑的问题有哪些？你用过的持久层框架有哪些
数值提升是什么
你能解释一下里氏替换原则吗
你是如何测试一个应用的？知道哪些测试框架
传输层常见编程协议有哪些？并说出各自的特点
 */