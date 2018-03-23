package com.twitter.TwitterEduApp.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by emawary on 2018-02-12.
 */

@Component
public class MySingletonBean {

    @Autowired
    private MyPrototypeBean prototypeBean;

/*
    If same scoped beans are wired together there's no problem.
    For example a singleton bean A injected into another singleton bean B.
    But if the bean A has the narrower scope say prototype scope then there's a problem!
*/

/*
    To understand the problem let's see an example. We are going to have two beans MyPrototypeBean,
    scoped as prototype and MySingletonBean, scoped as singleton. We will inject the prototype bean into
    the singleton bean. We will also access MySingletonBean via method call context#getBean(MySingletonBean.class)
    multiple times. We are expecting (per prototype specifications) that a new prototype bean will be created and be
    injected into MySingletonBean every time.
*/

/*
    The problem is: spring container only creates the singleton bean MySingletonBean once,
    and thus only gets one opportunity to inject the dependencies into it. The container cannot provide MySingletonBean with
    a new instance of MyPrototypeBean every time one is needed.

    http://www.logicbig.com/tutorials/spring-framework/spring-core/injecting-singleton-with-prototype-bean/
*/

/*
    Problem wstrzyknięcia beana o zasięgu sesji do
    kontrolera  będącego  singletonem  rozwiążemy  w  kolejnym  punkcie  poprzez
    wykorzystanie obiektu proxy. Z  punktu  widzenia  skalowalności aplikacji lepiej aby kontroler był singletonem.
*/

    public void showMessage(){
        System.out.println("Hi, the time is "+ prototypeBean.getDateTime());
    }
}