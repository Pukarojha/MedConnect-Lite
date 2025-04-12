package com.first.demo.models;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MedlinePlusService {


        @GET("feeds/news_en.xml")
//        @GET("druginfo/herb_All.html")
        Call<RssFeed> getHealthNews();


}
