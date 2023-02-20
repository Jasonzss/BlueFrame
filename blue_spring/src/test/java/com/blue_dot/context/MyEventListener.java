package com.blue_dot.context;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 21:02
 * @Description ：
 */
public class MyEventListener implements ApplicationListener<MyEvent>{
    private String name;

    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println(name+"接收到消息："+event.getMsg());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
