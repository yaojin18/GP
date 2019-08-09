package com.gp.vip.spring.demo.aspect;

import java.util.Arrays;

import com.gp.vip.spring.framework.aop.aspect.GPJoinPoint;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogAspect {

    // 在调用一个方法之前，先执行before方法
    public void before(GPJoinPoint joinPoint) {
        joinPoint.setUserAttribute("startTime_" + joinPoint.getMethod().getName(), System.currentTimeMillis());

        log.info("Invoker Before Method," + "\n TargetObject：" + joinPoint.getThis() + "\n Args:"
            + Arrays.toString(joinPoint.getArguments()));
    }

    public void after(GPJoinPoint joinPoint) {
        log.info("Invoker After Method!!!" + "\nTargetObject:" + joinPoint.getThis() + "\nArgs:"
            + Arrays.toString(joinPoint.getArguments()));
        long startTime = (Long)joinPoint.getUserAttribute("startTime_" + joinPoint.getMethod().getName());
        long endTime = System.currentTimeMillis();
        System.out.println("use time :" + (endTime - startTime));
    }

    public void afterThrowing(GPJoinPoint joinPoint, Throwable ex) {
        log.info("出现异常" + "\nTargetObject:" + joinPoint.getThis() + "\nArgs:"
            + Arrays.toString(joinPoint.getArguments()) + "\nThrows:" + ex.getMessage());
    }

}
