package com.jarry.jvm;

/**
 * 逃逸分析: 没有被其他线程访问，也就是没有发生逃逸
 * 标量替换：直接使用属性(id, name替代User对象)在栈上分配
 * 满足以上条件为栈上分配
 *
 * 线程本地分配缓存区Thread Local Allocation Buffer：如果对象不大且开启了线程本地
 *
 * 关闭逃逸分析、标量替换、栈上分配：-XX:-DoEscapeAnalysis -XX:-EliminateAllocations -XX:-UseTLAB
 * 开启逃逸分析、标量替换、栈上分配：-XX:+DoEscapeAnalysis -XX:+EliminateAllocations -XX:+UseTLAB
 */
public class TestTLAB {

    class User {
        int id;
        String name;
        public User(int id, String name){
            this.id = id;
            this.name = name;
        }
    }

    void alloc(int i){
        User user = new User(i, "name " + i);
    }

    public static void main(String[] args) {
        TestTLAB t = new TestTLAB();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000_0000; i++){
            t.alloc(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


}
