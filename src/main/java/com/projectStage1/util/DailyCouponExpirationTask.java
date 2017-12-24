package com.projectStage1.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.projectStage1.beans.Coupon;
import com.projectStage1.dao.CouponDAO;
import com.projectStage1.exceptions.CouponNotAvailableException;
import com.projectStage1.exceptions.CouponRemovalException;
import com.projectStage1.exceptions.GetOldCouponsException;
import com.projectStage1.exceptions.RollbackDataBaseException;

public class DailyCouponExpirationTask implements Runnable 
{
    CouponDAO coupDAO;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public DailyCouponExpirationTask(CouponDAO coupDAO){
        this.coupDAO = coupDAO;
    }

    @Override
    public void run() 
    {
        System.out.println("ExpirationTask Daily thread started.");
        cleanOldCoupons();
        System.out.println("ExpirationTask Daily thread finished.");
    }
    
    private void cleanOldCoupons()
    {
        Collection<Coupon> oldCoupons;

        try {
            oldCoupons = coupDAO.getOldCoupons();
            int numOfOldCoupons = oldCoupons.size();

            if (numOfOldCoupons <= 0) {
                System.out.println("ExpirationTask no old coupons to remove. cleaner thread has nothing to do");
                return;
            } else {
                System.out.println("ExpirationTask , " + numOfOldCoupons + " old coupons found.");
                for (Coupon coupon : oldCoupons) {

                    coupDAO.removeCoupon(coupon);
                    System.out.println("ExpirationTask Coupon " + coupon.getID() + " was removed.");
                }
            }
        } catch (CouponRemovalException | RollbackDataBaseException | CouponNotAvailableException | GetOldCouponsException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void startTask()
    {
        // Create scheduled time to run task
        LocalDateTime localNow = LocalDateTime.now();
        LocalDateTime TWO =  localNow.withHour(2).withMinute(0).withSecond(0);

        if(localNow.compareTo(TWO) > 0)
            TWO = TWO.plusDays(1);

        Duration duration = Duration.between(localNow, TWO);
        long initalDelay = duration.getSeconds();
        // 
        scheduler.scheduleAtFixedRate(this, initalDelay,
                                      24*60*60, TimeUnit.SECONDS);
    }
    
    public void stopTask(){
        scheduler.shutdown();
    }

}
