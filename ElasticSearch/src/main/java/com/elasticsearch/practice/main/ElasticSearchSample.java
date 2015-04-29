package com.elasticsearch.practice.main;

/**
 * Created by SatishDivakarla on 4/16/15.
 */

import com.elasticsearch.practice.util.ElasticSearchUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.mapper.object.ObjectMapper;

import java.io.*;

import static org.elasticsearch.node.NodeBuilder.*;

public class ElasticSearchSample {

    public static void main(String args[]){
        // psy-001_clickstream_export.txt
        String file = "/Users/SatishDivakarla/Documents/Clairvoyant/DataChallenge/Raw/psy-001_clickstream_export.txt";
        //String file = "/Users/SatishDivakarla/Downloads/test/a.txt";
        ElasticSearchUtil helper = new ElasticSearchUtil();
        Client client = helper.getClient();
       //helper.deleteAllFromIndex("events");

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);

        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String json;
            while ((json = br.readLine()) != null) {
                json = json.replace("\\\"","\"");
                json = json.replace("\"{","{");
                json = json.replace("}\"","}");
                System.out.println(json);
                helper.indexRecord(json);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        helper.closeConnection(client);
    }



}
