package com.only5c.unsplash.api

import com.only5c.unsplash.models.*
import com.only5c.unsplash.models.Collection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/photos/curated?per_page=20")
    fun getHome(@Query("page") pageNumber: Int) : Call<List<Photo>>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/photos?per_page=20")
    fun getNew(@Query("page") pageNumber: Int) : Call<List<Photo>>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/users/{username}")
    fun getUser(@Path("username") userName: String) : Call<User>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/photos/{id}")
    fun getPhoto(@Path("id") photoId: String) : Call<Photo>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/users/{username}/photos")
    fun getUserPhotos(@Path("username") userName: String, @Query("page") pageNumber: Int) : Call<List<Photo>>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/users/{username}/likes")
    fun getUserLikes(@Path("username") userName: String, @Query("page") pageNumber: Int) : Call<List<Photo>>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/search/photos")
    fun getSearchPhotos(@Query("query") search: String, @Query("page") pageNumber: Int) : Call<PhotoSearch>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/search/users")
    fun getSearchUsers(@Query("query") search: String, @Query("page") pageNumber: Int) : Call<UserSearch>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/collections/featured")
    fun getFeaturedCollections() : Call<List<Collection>>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/collections/{id}")
    fun getCollection(@Path("id") collectionId: String) : Call<Collection>

    @Headers("Authorization: Client-ID 5aaed5ddc13df4f6a3db0ae5d1db456a9e6a30984ec7bab43d9cf2c500a5fcd2")
    @GET("/collections/{id}/photos?per_page=100")
    fun getCollectionPhotos(@Path("id") collectionId: String, @Query("page") pageNumber: Int) : Call<List<Photo>>

}
