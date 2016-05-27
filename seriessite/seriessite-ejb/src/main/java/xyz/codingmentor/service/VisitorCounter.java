package xyz.codingmentor.service;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;
import xyz.codingmentor.interceptror.MethodInterceptor;

@Singleton
@Interceptors(MethodInterceptor.class)
public class VisitorCounter {
    private int visitor = 0;
    
    @Schedule(hour = "0", minute = "0", second = "0")
    public void resetVisitorNumber(){
        visitor = 0;
    }

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }   
}
