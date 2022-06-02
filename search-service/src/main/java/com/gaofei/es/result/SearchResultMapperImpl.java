package com.gaofei.es.result;

import com.gaofei.es.entity.User;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * @author : gaofee
 * @date : 9:54 2022/6/2
 * @码云地址 : https://feege.gitee.io
 */
public class SearchResultMapperImpl implements SearchResultMapper {
    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
        // 获取查询结果中的所有文档
        SearchHit[] hits = searchResponse.getHits().getHits();
        ArrayList<User> poemList = new ArrayList<>();
        for (SearchHit hit : hits) {
            // 获取原生的结果
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //赋值给sourceAsMap中的id→与hit中的id一致
            sourceAsMap.put("id", hit.getId());

            //获取高亮字段的结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            Set<Map.Entry<String, Object>> entries = sourceAsMap.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                //通过key获取高亮字段                                   id  name
                HighlightField highlightField = highlightFields.get(entry.getKey());
                //将找到的高亮字段的内容  替换到原生结果集的map中
                if (highlightField != null) {
                    sourceAsMap.put(entry.getKey(), highlightField.fragments()[0].toString());
                }
            }

            /*
             * 自定义结果集的封装  将sourceAsMap中的结果封装成list集合返回
             * */
            User tPoem = new User();
            tPoem.setId(Integer.parseInt(sourceAsMap.get("id").toString()));
            tPoem.setName((String) sourceAsMap.get("name"));
            tPoem.setAddress((String) sourceAsMap.get("address"));
            tPoem.setUsername((String) sourceAsMap.get("username"));
            poemList.add(tPoem);
        }

        long totalHits = searchResponse.getHits().getTotalHits();

        //将封装好的结果集 传给 一个数据传输的载体对象
        return new AggregatedPageImpl(poemList, pageable, totalHits,searchResponse.getScrollId());
    }

    @Override
    public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
        return null;
    }
}
