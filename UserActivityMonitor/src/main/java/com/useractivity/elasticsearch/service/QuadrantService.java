package com.useractivity.elasticsearch.service;

import com.elasticsearch.practice.util.ElasticSearchUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by SatishDivakarla on 4/25/15.
 */
public class QuadrantService {

    public static int getActiveQuadrant(){
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);
        calendar.add(Calendar.MINUTE, -5);
        long oldTime = calendar.getTimeInMillis();
        int activeQuadrant  = getActiveQuadrantInWindow(oldTime, currentTime);
        return activeQuadrant;
    }

    private static int getActiveQuadrantInWindow(long oldTime, long currentTime) {
        System.out.println("oldTime = " + oldTime);
        System.out.println("currentTime = " + currentTime);
        ElasticSearchUtil elasticSearchUtil = new ElasticSearchUtil();
        Client client = elasticSearchUtil.getClient();

        SearchResponse  searchResponse = client.prepareSearch("useractivities").
                setTypes("quadrant").setQuery(
                QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilders.rangeFilter("timestamp")
                        .from(oldTime).to(currentTime))).setSize(100)
                .addAggregation(AggregationBuilders.terms("by_id").field("id").size(0)
                )
                .execute().actionGet();
        //printAggregations(searchResponse.getAggregations(), "by_id");

        /*SearchHits searchHits = searchResponse.getHits();

        Iterator<SearchHit> searchHitIterator = searchHits.iterator();

        while(searchHitIterator.hasNext()){
            SearchHit hit = searchHitIterator.next();
            System.out.println(hit.getSourceAsString());
        }*/

        return getActiveBucketFromAggResults(searchResponse.getAggregations(), "by_id");
    }

    private static void printAggregations(Aggregations aggregations, String key) {
        Terms terms = aggregations.get(key);
        Iterator<Terms.Bucket> iterator = terms.getBuckets().iterator();
        while(iterator.hasNext()){
            Terms.Bucket bucket = iterator.next();
            System.out.println("iterator.next().getKeyAsText() = " + bucket.getKeyAsText());
            System.out.println("iterator.next().getDocCount() = " + bucket.getDocCount());
        }
    }

    private static int getActiveBucketFromAggResults(Aggregations aggregations, String key) {
        Terms terms = aggregations.get(key);
        Iterator<Terms.Bucket> iterator = terms.getBuckets().iterator();
        Long maxCount = 0L;
        int activeQuadrant=0;
        while(iterator.hasNext()){
            Terms.Bucket bucket = iterator.next();
            long docCount = bucket.getDocCount();
            System.out.println("iterator.next().getDocCount() = " + docCount);
            if(docCount > maxCount) {
                System.out.println("quadrant is = " + bucket.getKeyAsNumber());
                maxCount = docCount;
                activeQuadrant = bucket.getKeyAsNumber().intValue();
            }
        }
        return activeQuadrant;
    }
    public static void main(String[] args) {
        System.out.println("Active Quadrant = " + getActiveQuadrant());
    }

}
