package com.gaofei.es;

import cn.hutool.json.JSONUtil;
import com.gaofei.es.entity.User;
import com.gaofei.es.repositories.UserRep;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : gaofee
 * @date : 10:34 2022/5/31
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootTest(classes =SearchApp.class )
@RunWith(SpringRunner.class)
public class SearchTest {
    //这种方式使用简单,但是针对一些复杂的查询很不友好的.
    @Autowired
    UserRep userRep;

    //复杂的查询使用ElasticsearchTemplate
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void search(){

        /**
         *{
         *   "query": {
         *     "bool": {
         *       "must":[
         *                {
         *           "term": {
         *                   "name": "中国"
         *                }
         *         },
         *        {
         *           "term": {
         *                   "id": "2"
         *                }
         *         }
         * 	  ],
         * 	  "must_not":[
         *        {
         *           "term": {
         *                   "name": "高飞"
         *                }
         *         }
         * 	  ]
         *     }
         *   },
         *   "highlight": {
         *     "fields": {
         *       "name": {
         *         "require_field_match": "false"
         *       }
         *     },
         *     "pre_tags": "<span style='color:red;'>",
         *     "post_tags": "</span>"
         *   }
         * }
         */
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

        NativeSearchQuery nativeSearchQuery =new  NativeSearchQueryBuilder()
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

        System.out.println(JSONUtil.toJsonStr(aggregatedPage));
        //===================================================
        /**
         * {
         *   "query": {
         *     "term": {
         *       "name": "中国"
         *     }
         *   }
         * }
         */

        /*NativeSearchQuery nativeSearchQuery =new  NativeSearchQueryBuilder()
                .withIndices("users").withTypes("user") //指定索引库的名称,指定类型
                .withQuery(QueryBuilders.termQuery("name","中国"))
                .withPageable(PageRequest.of(0,3)) //分页查询
                .build();
        List<User> users = elasticsearchTemplate.queryForList(nativeSearchQuery, User.class);
        users.forEach(user -> System.out.println(user));*/

//===============================================

        //建造者模式,设计模式的其中一种
        /**
         * {
         *   "query": {
         *     "match": {
         *       "name": "中国"
         *     }
         *   }
         * }
         */
        /*NativeSearchQuery nativeSearchQuery =new  NativeSearchQueryBuilder()
                .withIndices("users").withTypes("user") //指定索引库的名称,指定类型
                .withQuery(QueryBuilders.matchQuery("name","中国"))
                .build();
        List<User> users = elasticsearchTemplate.queryForList(nativeSearchQuery, User.class);
        users.forEach(user -> System.out.println(user));*/
    }

    @Test
    public void save(){
        ArrayList<User> users = new ArrayList<>();

        for (int i = 1; i < 90; i++) {
            User user = new User();
            user.setId(i);
            user.setAddress("北京市丰台区马家堡"+i);
            user.setUsername("杨涛"+i);
            user.setName("中国人"+i);
            users.add(user);
        }
        //批量保存集合
        userRep.saveAll(users);
    }
}
