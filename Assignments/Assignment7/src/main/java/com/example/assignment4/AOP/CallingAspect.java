package com.example.assignment4.AOP;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component

public class CallingAspect {
    List<CallingMeter> list;

    CallingAspect() throws ClassNotFoundException {
        list = new ArrayList<>();
        Class clazz = Class.forName("com.example.assignment4.controller.GoodsController");
        Method[] mths = clazz.getDeclaredMethods();
        CallingMeter temp;
        for(int i=0; i<mths.length;i++){
            temp = new CallingMeter(mths[i].getName(),0,0,0,0,0);
            list.add(temp);
        }
    }

    @Pointcut("execution(public * com.example.assignment4.controller.GoodsController.*(..))")
    public void pointcut(){}


    @Around("pointcut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime;
        long endTime;
        long duringTime;
        long newAverage;

        try {
            startTime = System.currentTimeMillis();
            joinPoint.proceed();
            endTime = System.currentTimeMillis();
            duringTime = endTime - startTime;

            String name = joinPoint.getSignature().getName();
            for (CallingMeter meter: list
            ) {

                if (meter.getApiName().equals(name)){
                    // 计算API的最长 / 最短 / 平均调用时间
                    if(meter.getLongestTime() < duringTime)
                        meter.setLongestTime(duringTime);
                    if(meter.getShortestTime() > duringTime)
                        meter.setShortestTime((duringTime));
                    newAverage = (meter.getAverageTime() * meter.getCount() + duringTime) / (meter.getCount()+1);
                    meter.setAverageTime(newAverage);
                    // 记录调用次数
                    meter.increasingCount();
                }


            }
        } catch (Throwable e){
            // 全局异常处理, 如果有异常就在这里计数并且结束, 并且此时需要计数调用次数
            String name = joinPoint.getSignature().getName();
            for (CallingMeter meter: list
            ) {
                if (meter.getApiName().equals(name)){
                    meter.increasingCount();
                    meter.increasingExceptionTime();
                }

            }
        }

    }


    protected void finalize(){
        for (CallingMeter meter:list
             ) {
            System.out.printf(meter.apiName + " has been calling for " + meter.count + " times");
        }

    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class CallingMeter{
    String apiName;
    int count;
    int exceptionTimes;
    long longestTime;
    long shortestTime;
    long averageTime;

    public void increasingCount(){
        count++;
    }
    public void increasingExceptionTime(){exceptionTimes++;}
}