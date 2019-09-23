package cn.fdongl.point.uploadapiimpl.util;

import java.util.List;
import java.util.function.Consumer;

public class BatchExecutor {

    public static <A> void batchExecute(List<A> list, Consumer<Iterable<A>>consumer){
        int max = list.size();
        for(int i=0;i<max;i+=400){
            int end = i+400;
            if(end > max){
                end = max;
            }
            consumer.accept(list.subList(i,end));
        }
    }

}
