package com.udacity.gradle.builditbigger;

import android.text.TextUtils;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class EndpointsAsyncTaskTest {

    private String res = null;

    @Test
    public void doInBackground() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new EndpointsAsyncTask().execute(new EndpointsAsyncTask.EndpointsListener() {
            @Override
            public void onPostExecute(String result) {
                res = result;
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
            assertNotNull("result is null", res);
            assertFalse("result is empty", TextUtils.isEmpty(res));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}