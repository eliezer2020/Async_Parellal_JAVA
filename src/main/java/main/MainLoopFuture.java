package main;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static main.MainThenCombineCompose.delay;

public class MainLoopFuture {

     public static void main(String[] args) throws ExecutionException, InterruptedException {

         long startTime = System.currentTimeMillis();


         List<Integer> integerList = Arrays.asList(1,2,3,4);

         //Using the List stream I can process integerAsyncTransformationAPI() and get List<CompletableFuture<Integer>>
         List<CompletableFuture<Integer>> arrayOfIntegerFutures = integerList.stream().map(
                 number -> integerAsyncTransformationAPI(number)
         ).collect(Collectors.toList());

         //Returns a new CompletableFuture that is completed when all of the given CompletableFutures complete.
         CompletableFuture<Void> allFuturesVoidCompleted= CompletableFuture.allOf(arrayOfIntegerFutures.toArray(new
                 CompletableFuture[arrayOfIntegerFutures.size()]));
        //returns a new CompletableFuture of Integer results
         CompletableFuture<List<Integer>> allFuturesIntegerCompleted= allFuturesVoidCompleted.thenApply(integerFutures->{
             return arrayOfIntegerFutures.stream().map(integerFuture -> integerFuture.join()).collect(Collectors.toList());
         });

         //Now I can get our final CompletableFuture which holds our results.
         // This resulted CompletableFuture holds the list of Integers

        CompletableFuture completableFuture = allFuturesIntegerCompleted.thenApply(numbers->{
            return numbers.stream().collect(Collectors.toList());
        });

         //. And now we are ready to call the blocking completableFuture

         long endTime = System.currentTimeMillis();
         System.out.println(arrayOfIntegerFutures);
         System.out.println("Time of execution  first part : "+ (endTime-startTime));
 }

    //take one second to complete 2*n
    public static CompletableFuture<Integer> integerAsyncTransformationAPI(Integer n){
         return CompletableFuture.supplyAsync(()->{
             delay(2);
             Integer computedNumber= n*2;
             System.out.println(computedNumber +"  .. computed on thread - "+Thread.currentThread().getName());
             return computedNumber;
         });
    }

    public static CompletableFuture<Integer> integerAsyncTransformationAPIWithException(Integer n){
        return CompletableFuture.supplyAsync(()->{
            if(n==2) throw new RuntimeException("not able to process number 2");
            delay(2);
            Integer computedNumber= n*2;
            System.out.println(computedNumber +"  .. computed on thread - "+Thread.currentThread().getName());
            return computedNumber;
        });
    }









    }

