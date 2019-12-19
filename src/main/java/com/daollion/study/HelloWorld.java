package com.daollion.study;


/*
    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ 
       Author   :  lixiaodaoaaa
       Date     :  2019/12/19
       Time     :  13:46
    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
 */

public class HelloWorld {

    public void sayHello() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
