package main;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MainAllofAnyOf {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();


        //ThenCompose chain one future that depends on another future
        //this will be exec synchronously
        String resultCompose= getUserInfo().thenCompose(s -> {
        return getUserOrder(s);
        }).join();

        System.out.println(resultCompose);

        //THenCombine make not sequential calls, for independent futures
        //this will be exec parallel
        String resultCombine = givePreparedCoffee().thenCombine(getUserInfo(), (action,user )-> {
            return action+user;
        }).join();
        System.out.println(resultCombine);


        long endTime = System.currentTimeMillis();
        System.out.println("Time of execution  first part : "+ (endTime-startTime));
        startTime= endTime;


        System.out.println("################# 2nd PART ########################");
        //AllOf will wait for all completion stages
        CompletableFuture.allOf(getUserInfo(),getUserInfo2()).join();
        //when we want the first faster API response and ignore the slow one
        CompletableFuture.anyOf(getUserInfo2(),getUserInfo()).join();
        endTime = System.currentTimeMillis();
        System.out.println("Time of execution  first part : "+ (endTime-startTime));
    }


    public static void delay(int n){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<String> getUserInfo(){
        return CompletableFuture.supplyAsync(()->{
            delay(1);
            //System.out.println("running get user info method 1");
            return "USER 1 GERSON 26 OLD";
        });
    }

    public static CompletableFuture<String> getUserOrder(String user){
        return CompletableFuture.supplyAsync(()->{
            delay(1);
            return user + " is ordering some coffee ";
        });
    }

    public static CompletableFuture<String> givePreparedCoffee(){
        return CompletableFuture.supplyAsync(()->{
            delay(1);
            return "preparing coffee and given to :  ";
        });
    }

    public static CompletableFuture<String> getUserInfo2(){
        return CompletableFuture.supplyAsync(()->{
            delay(2);
            System.out.println("running get user info method 2");

            return "USER 1 GERSON 26 OLD from method 2 ";
        });
    }
}
