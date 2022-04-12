package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static main.MainThenCombineCompose.delay;

public class MainParallelStream {
    public static void main(String[] args) {
        ArrayList<Integer> randomList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));

       // List<Integer> computedList = randomList.stream().map(integer -> hightIntensiveCPUtask(integer)).collect(Collectors.toList());
        List<Integer> computedListParallel = randomList.stream().parallel().map(integer -> hightIntensiveCPUtask(integer)).collect(Collectors.toList());
        System.out.println("#################");


        //terminal order operation execute this sync
        randomList.stream().parallel().forEachOrdered(item->{
            hightIntensiveCPUtask(item);
        });

    }





    public static Integer hightIntensiveCPUtask(Integer n ){
        delay(2);
        Integer nComputed= n+1;
        System.out.println(nComputed + "  on thread: "+Thread.currentThread().getName());
        return nComputed;
    }


}

