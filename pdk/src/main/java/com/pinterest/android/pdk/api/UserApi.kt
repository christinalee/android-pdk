package com.pinterest.android.pdk.api

import com.pinterest.android.pdk.base.Unwrap
import com.pinterest.android.pdk.data.Board
import com.pinterest.android.pdk.data.Pin
import com.pinterest.android.pdk.data.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    /**
     * The default response returns the first and last name, ID and URL of the authenticated user.
     *
     * Required Scope: read_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/")
    fun getAuthdUser(): Single<User>

    /**
     * The default response returns a list of the authenticated user’s public boards, including the URL, ID and name.
     *
     * Required Scope: read_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/boards")
    fun getMyBoards(): Single<List<Board>>

    /**
     * Returns the boards that Pinterest would suggest to the authenticated user if
     * they were to save the specified Pin. The default response returns the ID,
     * URL and name of the boards.
     *
     * Required Scope: read_public
     */
    // TODO: doesn't work here or on API explorer
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/boards/suggested/")
    fun getSuggestedBoardsForMe(@Query("pin_id") pin: String,
                                @Query("count") count: Int): Single<List<Board>>

    /**
     * The default response returns the ID, link, URL and descriptions of the
     * authenticated user’s Pins.
     *
     * Required Scope: read_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/pins")
    fun getMyPins(@Query("cursor") cursor: String? = null): Single<List<Pin>>



    /**
     * Searches the authenticated user’s board names (but not Pins on boards).
     * An empty response indicates that nothing was found that matched your search terms.
     * The default response returns the ID, name and URL of boards matching your query.
     *
     * Required Scope: read_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/search/boards/")
    fun searchMyBoards(@Query("query") query: String,
                       @Query("cursor") cursor: String? = null): Single<List<Board>> //TODO: vet return type

    /**
     * Searches the authenticated user’s Pin descriptions. An empty response indicates
     * that nothing was found that matched your search terms. The default response returns the ID,
     * link, URL and description of Pins matching your query.
     *
     * Required Scope: read_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/search/pins/")
    fun searchMyPins(@Query("query") query: String,
                     @Query("cursor") cursor: String? = null): Single<List<Pin>> //TODO: vet return type



    /**
     * Makes the authenticated user follow the specified board. A empty response
     * (or lack of error code) indicates success.
     *
     * Required Scope: write_relationships
     *
     * usernameAndBoard (required): The board you want to follow in the format <username>/<board_name>
     */
    @FormUrlEncoded
    @POST("v1/me/following/boards/")
    fun followBoard(@Field("board") usernameAndBoard: String): Completable //TODO: broken?

    /**
     * Makes the authenticated user follow the specified user. A empty response
     * (or lack of error code) indicates success.
     *
     * Required Scope: write_relationships
     *
     * user (required): The username of the user that you want to follow
     */
    @FormUrlEncoded
    @POST("v1/me/following/users/")
    fun followUser(@Field("user") username: String): Completable



    /**
     * Returns the users who follow the authenticated user. The default response
     * returns the first and last name, ID and URL of the users.
     *
     * Required Scope: read_relationships
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/followers/")
    fun getMyFollowers(@Query("cursor") cursor: String? = null): Single<List<User>>

    /**
     * Returns the boards that the authenticated user follows. The default response
     * returns the ID, name and URL of the board.
     *
     * Required Scope: read_relationships
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/following/boards/")
    fun getBoardsIFollow(@Query("cursor") cursor: String? = null): Single<List<Board>>

    /**
     * Returns the topics (e.g, modern architecture, Sherlock) that the
     * authenticated user follows. The default response returns the ID and name of the topic.
     *
     * Required Scope: read_relationships
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/following/interests/")
    fun getInterestsIFollow(@Query("cursor") cursor: String? = null): Single<List<Any>> //TODO

    /**
     * Returns the users that the authenticated user follows. The default response
     * returns the first and last name, ID and URL of the users.
     *
     * Required Scope: read_relationships
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/following/users/")
    fun getUsersIFollow(@Query("cursor") cursor: String? = null): Single<List<User>> //TODO



    /**
     * Makes the authenticated user unfollow the specified board. A empty response
     * (or lack of error code) indicates success.
     *
     * Required Scope: write_relationships
     */
    @DELETE("v1/me/following/boards/{username}/{boardname}/")
    fun unfollowBoard(@Path("username") username: String,
                      @Path("boardname") boardname: String): Completable

    /**
     * Makes the authenticated user unfollow the specified user. A
     * empty response (or lack of error code) indicates success.
     *
     * Required Scope: write_relationships
     */
    @DELETE("v1/me/following/users/{usernameOrId}/")
    fun unfollowUser(@Path("usernameOrId") usernameOrId: String): Completable
}
