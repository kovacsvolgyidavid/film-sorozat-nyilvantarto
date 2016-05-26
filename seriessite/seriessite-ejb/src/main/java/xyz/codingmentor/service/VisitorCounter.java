package xyz.codingmentor.service;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class VisitorCounter {
    private int visitor = 0;
    
    @Schedule(hour = "0", minute = "33", second = "0")
    public void doWork(){
        visitor = 0;
    }

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }   
}
