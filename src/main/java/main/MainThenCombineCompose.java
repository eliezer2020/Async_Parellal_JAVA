package main;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MainThenCombineCompose {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        System.out.println("available processors: "+ Runtime.getRuntime().availableProcessors());
        //Runnable don t return anything
        CompletableFuture<Void> cfRunnable= CompletableFuture.runAsync(()->{
            delay(1);
            System.out.println("runnable  is running on - "+Thread.currentThread().getName());
            //throw new RuntimeException("Exception on future");
        });


        System.out.println("ending main is running on - "+Thread.currentThread().getName());
        //wait runnable to be completed and bring result to main Thread
        //cfRunnable.join(); //if removed it will finish main and don t wait for future completion

        //supplier return a value
        CompletableFuture<String> cfSupplier = CompletableFuture.supplyAsync(()->{
            delay(1);
            return "supplier is running on thread - "+ Thread.currentThread().getName();
        });
        //CF.all of will invoke parallelCompletion for both CF
        CompletableFuture.allOf(cfRunnable,cfSupplier);
        //get and join works similar but using get you need to specify exception signature
        //whereas join will not check for Exceptions, you can use exceptionally to catch error
        System.out.println(cfSupplier.get());
        System.out.println(cfSupplier.join());


        long endTime = System.currentTimeMillis();
        System.out.println("Time of execution  first part : "+ (endTime-startTime));
        startTime= endTime;

        System.out.println("################# 2nd PART ########################");




        CompletableFuture<Void> cfStringPipeline = CompletableFuture.supplyAsync(()->{
            delay(2);
            return "Thread... " + Thread.currentThread().getName() +" ...Is";
        }).thenApply(thisString -> {
            return  thisString + "  Saying Hello World";
        }).thenAccept(composeString->
                System.out.println(composeString)).
                thenRun(()-> System.out.println("Process EXITED"));
        cfStringPipeline.join();


        endTime = System.currentTimeMillis();
        System.out.println("Time of execution  second part : "+ (endTime-startTime));
    }





    public static void delay(int n){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
