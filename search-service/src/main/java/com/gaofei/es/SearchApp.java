package com.gaofei.es;

import com.gaofei.es.entity.User;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * @author : gaofee
 * @date : 10:24 2022/5/31
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.gaofei.es.repositories")

@RestController
public class SearchApp {

    @Resource
    ElasticsearchTemplate elasticsearchTemplate;
    public static void main(String[] args) {
        SpringApplication.run(SearchApp.class, args);
    }

    @RequestMapping("es")
    public Object list(){
        String name = "中国";
        Integer id = 2;
        Integer pageNum = 3;
        Integer pageSize = 6;

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //注意,拼这些条件的时候,一定要加上条件的非空判卷
        if(null!=name&&name!=""){
            boolQuery.must(QueryBuilders.termQuery("name","中国"));
            boolQuery.mustNot(QueryBuilders.termQuery("name","高飞"));
        }
        //注意,拼这些条件的时候,一定要加上条件的非空判卷
        if(id!=null&&id !=0){
//            boolQuery.must(QueryBuilders.termQuery("id",2));
        }

        //构建高亮的feild字段
        HighlightBuilder.Field fields = new HighlightBuilder
                .Field("*")
                .preTags("<span style='color:red'>").postTags("</span>")
                .requireFieldMatch(false);

        NativeSearchQuery nativeSearchQuery =new NativeSearchQueryBuilder()
                .withIndices("users").withTypes("user") //指定索引库的名称,指定类型
                .withQuery(boolQuery) //拼装条件
                .withPageable(PageRequest.of(pageNum-1,pageSize)) //分页查询
                .withHighlightFields(fields)
                .build();
        //这个对象就是分页的对象,类似于pagehelper中的pageInfo对象
        AggregatedPage<User> aggregatedPage =  elasticsearchTemplate.queryForPage(nativeSearchQuery, User.class,new SearchResultMapper(){

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
                return new AggregatedPageImpl(poemList,pageable,totalHits);
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                return null;
            }
        });

        System.out.println(aggregatedPage.getContent());
        System.out.println("总页数:"+aggregatedPage.getTotalPages());
        System.out.println("总记录数:"+aggregatedPage.getTotalElements()+" 条");
        System.out.println("当前页:"+(aggregatedPage.getNumber()+1));
        System.out.println("每页显示:"+aggregatedPage.getSize());

        return aggregatedPage;
    }
}
