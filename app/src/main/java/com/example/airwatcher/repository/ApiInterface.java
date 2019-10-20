package com.example.airwatcher.repository;

import com.example.airwatcher.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("posts")
    Call<List<Post>> getPosts();

}
