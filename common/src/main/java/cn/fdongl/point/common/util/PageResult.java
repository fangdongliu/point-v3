package cn.fdongl.point.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult <K> {

    public static <T> PageResult<T> ofPage(Page<T> page){
        return new PageResult<>(page.getTotalElements(),page.getContent());
    }

    private long total;
    private List<K> resultList;

}
