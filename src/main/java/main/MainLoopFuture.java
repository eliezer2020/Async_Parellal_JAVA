package main;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static main.MainThenCombineCompose.delay;

public class MainLoopFuture {

     public static void main(String[] args) throws ExecutionException, InterruptedException {

         long startTime = System.currentTimeMillis();
         CompletableFuture<List<Integer>> arrayOfAsyncTransformation = new CompletableFuture<>();
         for(int i = 1; i<=3; i++){
             arrayOfAsyncTransformation.get().add(processAsyncNumberTransformation(i));
         }

         long endTime = System.currentTimeMillis();
         System.out.println(arrayOfAsyncTransformation);
         System.out.println("Time of execution  first part : "+ (endTime-startTime));
 }

    //take one second to complete 2*n
    public static CompletableFuture<Integer> processAsyncNumberTransformation(Integer n){
         return CompletableFuture.supplyAsync(()->{
             delay(1);
             return n*2;
         });
    }









    }

