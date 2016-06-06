package xyz.codingmentor.service;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;
import xyz.codingmentor.interceptror.MethodInterceptor;

@Singleton
@Interceptors(MethodInterceptor.class)
public class VisitorCounter {
    private int visitorNumber = 0;
    
    @Schedule(hour = "0", minute = "0", second = "0")
    public void resetVisitorNumber(){
        visitorNumber = 0;
    }
    
    public void increaseVisitorNumberByOne(){
        visitorNumber++;
    }
    
    @Lock(LockType.READ) 
    public int getVisitorNumber() {
        return visitorNumber;
    }
}
