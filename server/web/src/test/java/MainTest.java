/**
 * Created by admin on 2018/7/3.
 */
public class MainTest {

    static class Human {

    }

    static class Man extends Human {

    }

    static class Woman extends Human {

    }

    public void say(Human hum) {
        System.out.println("I am human");
    }

    public void say(Man hum) {
        System.out.println("I am man");
    }

    public void say(Woman hum) {
        System.out.println("I am woman");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        MainTest sp = new MainTest();
        sp.say(man);
        sp.say(woman);
    }
}
