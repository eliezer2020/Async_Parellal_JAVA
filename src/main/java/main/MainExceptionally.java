package main;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MainExceptionally {

    public static void main(String[] args) {




        //exceptionally only is executed when error stage is present
        Integer unknowResultRecovered = integerMayFailFuture(6).exceptionally((e)->{
            System.out.println("handled result default value : 0");
            return 0;
        }).join();
        System.out.println(unknowResultRecovered);

        //handled takes Result & Exception and we can customize behavior
        Integer unknowResultBiHandled = integerMayFailFuture(5).handle((number, e)->{
          if(e==null) {
              System.out.println("everything is OKEY ");
              return  number;
          }else{
              return 0;
          }
      }).join();
        System.out.println(unknowResultBiHandled);

        //if not tryCatch this will break the main thread
        try {
            Integer unknowResultFailed = integerMayFailFuture(6).join();
            System.out.println(unknowResultFailed);
        }catch (Exception e){
            System.out.println(e);
        }





    }
    public static void delay(int n){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<Integer> integerMayFailFuture(Integer n){
        return CompletableFuture.supplyAsync(()->{
            if(n%2==0) throw new RuntimeException("Exception unable to handled pair numbers");
            delay(1);
            return n;
        });
    }
}


