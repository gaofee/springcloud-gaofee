package com.gaofei.es;

import com.gaofei.es.entity.User;
import com.gaofei.es.result.SearchResultMapperImpl;
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
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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

    ScrolledPage<User> scrolledPage =null;


    public static void main(String[] args) {
        SpringApplication.run(SearchApp.class, args);
    }




    @RequestMapping("es")
    public Object list(){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //注意,拼这些条件的时候,一定要加上条件的非空判卷
        boolQuery.must(QueryBuilders.termQuery("name","中国"));

        //构建高亮的feild字段
        HighlightBuilder.Field fields = new HighlightBuilder
                .Field("*")
                .preTags("<span style='color:red'>").postTags("</span>")
                .requireFieldMatch(false);

        NativeSearchQuery nativeSearchQuery =new  NativeSearchQueryBuilder()
                .withIndices("users").withTypes("user") //指定索引库的名称,指定类型
                .withQuery(boolQuery) //拼装条件
                .withPageable(PageRequest.of(0,2)) //分页查询,如果是深分页第一个参数必须是0
                .withHighlightFields(fields)
                .build();
         scrolledPage = elasticsearchTemplate.startScroll(5000L, nativeSearchQuery, User.class,new SearchResultMapperImpl());

         System.out.println("ScrollId:"+scrolledPage.getScrollId());
        return scrolledPage;
    }


    @RequestMapping("next")
    public Object next(){

            Stream<User> userStream = scrolledPage.get();
            userStream.forEach(user-> System.out.println(user));
            scrolledPage=elasticsearchTemplate.continueScroll(scrolledPage.getScrollId(),5000L,User.class,new SearchResultMapperImpl());
            //及时释放es服务器资源
//            elasticsearchTemplate.clearScroll(scrolledPage.getScrollId());
        return scrolledPage;
    }

}
