package com.elasticsearch.practice.util;

import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequest;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.Iterator;

/**
 * Created by SatishDivakarla on 4/16/15.
 */
public class ElasticSearchUtil {

    private Client client = null;

    public Client getClient(){
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elastictest")
                .put("node.name", "SatishD").build();
        client =    new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        //client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
        return client;
    }

    public IndexResponse indexRecord(String jsonText){
        IndexResponse response = client.prepareIndex("events", "event")
                .setSource(jsonText)
                .execute()
                .actionGet();
        return response;
    }

    public IndexResponse indexRecord(String jsonText, String index, String type){
        IndexResponse response = client.prepareIndex(index, type)
                .setSource(jsonText)
                .execute()
                .actionGet();
        return response;
    }

    public String getRecordByIndex(String index, String type, String id){
        GetResponse response = client.prepareGet(index, type, id)
                .execute()
                .actionGet();
        return response.getSource().toString();
    }

    public void deleteAllFromIndex(String index){
        DeleteByQueryResponse response = client.prepareDeleteByQuery(index)
                .setQuery(new MatchAllQueryBuilder())
                .execute()
                .actionGet();
        //DeleteRequestBuilder deleteRequestBuilder = new DeleteRequestBuilder(client,"events");
        client.prepareDelete().setIndex("events");
    }

    public void deleteById(String index, String type, String id){
        DeleteResponse response = client.prepareDelete(index, type, id)
                .execute()
                .actionGet();
    }

    public void searchRecord(){
        SearchResponse response = client.prepareSearch("events")
                .setTypes("event")
                .setSearchType(SearchType.QUERY_THEN_FETCH)
               // .setScroll(new TimeValue(60000))
                .setQuery(QueryBuilders.termQuery("key", "user.video.lecture.action"))
                .setQuery(QueryBuilders.termQuery("username", "2c4ef5192a5338014f75edda86d68eba6f3b89c4"))
                .setPostFilter(FilterBuilders.rangeFilter("timestamp").to(1364250804418L).from(1364248031208L))
                // Query
                     //   .setQuery(QueryBuilders.matchQuery("username","lecture video").operator(MatchQueryBuilder.Operator.AND))
               // .setPostFilter(FilterBuilders.rangeFilter("age").from(12).to(18))   // Filter
               // .setFrom(0).setSize(1).setExplain(true)
                .execute()
                .actionGet();

        SearchHits searchHits  = response.getHits();
        Iterator<SearchHit> it = searchHits.iterator();
        //System.out.println(response.getHits().hits());
        System.out.println(searchHits.totalHits());
        while(it.hasNext()){
            System.out.println(it.next().getSourceAsString());
        }
    }

    public void closeConnection(Client client) {
        client.close();
    }

    public void closeConnection() {
        this.client.close();
    }
}
