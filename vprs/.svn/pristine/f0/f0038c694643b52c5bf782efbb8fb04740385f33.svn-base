package com.pccc.vprs.servicecustom.kafka;

public class SleepInBetween
{
    private int count = 0;
    private long lastInvokeTimestamp;

    public void sleepAtMax(long maxSleepTime)
    {
        long invokeTimestamp = now();
        
        long toSleepTime = maxSleepTime - (invokeTimestamp - lastInvokeTimestamp);
        System.out.println(sleepLog(toSleepTime));
        this.lastInvokeTimestamp = invokeTimestamp;
        
        if(toSleepTime > 0)
        {
            sleep(toSleepTime);
        }
    }

    private String sleepLog(long toSleepTime)
    {
        if(lastInvokeTimestamp == 0)
        {
            return "Sleeping No. " + ++count;
        }
        
        return "Sleeping No. " + ++count + " | sleep for " + toSleepTime + "ms.";
    }

    private void sleep(long toSleepTime)
    {
        try
        {
            Thread.sleep(toSleepTime);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private long now()
    {
        return System.currentTimeMillis();
    }

}
