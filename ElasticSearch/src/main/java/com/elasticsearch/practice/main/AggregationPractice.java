package com.elasticsearch.practice.main;

import com.elasticsearch.practice.util.ElasticSearchUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.StringTermsAggregator;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.util.Iterator;

/**
 * Created by SatishDivakarla on 4/18/15.
 */
public class AggregationPractice {
    public static void main(String[] args) {
        ElasticSearchUtil helper = new ElasticSearchUtil();
        Client client = helper.getClient();
        SearchResponse searchResponse = client.prepareSearch()
                .addAggregation(
                        AggregationBuilders.terms("by_key").field("key")
                                .subAggregation(AggregationBuilders.terms("by_username").field("username").size(0))

                )
                .execute().actionGet();

        Aggregations aggregations = searchResponse.getAggregations();
        printAggregations(aggregations, "by_key");
    }

    private static void printAggregations(Aggregations aggregations, String key) {
        Terms terms = aggregations.get(key);
        Iterator<Terms.Bucket> iterator = terms.getBuckets().iterator();
        while(iterator.hasNext()){
            Terms.Bucket bucket = iterator.next();
            System.out.println("iterator.next().getKeyAsText() = " + bucket.getKeyAsText());
            System.out.println("iterator.next().getDocCount() = " + bucket.getDocCount());
            if(bucket.getAggregations().asList().size() >0) {
                printAggregations(bucket.getAggregations(), "by_username");
            }
        }
    }

}
