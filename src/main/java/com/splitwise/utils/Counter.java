package com.splitwise.utils;

public class Counter {
    private long start;
    public void Counter(Long start){
       this.start = start;
    }

    public Long next(){
        start++;
        return start - 1;
    }
}
