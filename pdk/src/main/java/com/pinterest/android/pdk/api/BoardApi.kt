package com.pinterest.android.pdk.api

import com.pinterest.android.pdk.base.Unwrap
import com.pinterest.android.pdk.data.Board
import com.pinterest.android.pdk.data.Pin
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardApi {

    /**
     * Creates a board for the authenticated user. The default response returns the
     * ID, URL and name of the created board.
     *
     * Required Scope: write_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @FormUrlEncoded
    @POST("v1/boards/")
    fun createBoard(@Field("name") boardName: String,
                    @Field("description") description: String? = null): Single<Board>



    /**
     * The default response returns the ID, URL and name of the specified board.
     *
     * Required scope: read_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/boards/{username}/{boardname}/")
    fun getBoard(@Path("username") username: String,
                 @Path("boardname") boardName: String): Single<Board>


    /**
     * The default response returns a list of Pins on the board with their ID, URL, link and description.
     *
     * Required scope: read_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/boards/{username}/{boardname}/pins/")
    fun getBoardsPins(@Path("username") username: String,
                      @Path("boardname") boardname: String): Single<List<Pin>>


    /**
     * The default response returns a list of the authenticated user’s public boards,
     * including the URL, ID and name.
     *
     * Required scope: read_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @GET("v1/me/boards")
    fun getMyPublicBoards(): Single<List<Board>>


    /**
     * Changes the chosen board’s name and/or description. The default response returns the
     * ID, URL and name of the edited board.
     *
     * Required scope: write_public
     */
    @Unwrap.EnvelopeJsonAdapter.Enveloped
    @FormUrlEncoded
    @PATCH("v1/boards/{username}/{boardname}/")
    fun updateBoard(@Path("username") username: String,
                    @Path("boardname") boardname: String,
                    @Field("name") name: String? = null,
                    @Field("description") description: String? = null,
                    @Field("board") boardSpec: String = "$username/$boardname"): Single<Board>


    /**
     * Deletes the specified board. This action is permanent and cannot be undone.
     *
     * Required scope: write_public
     */
    @DELETE("v1/boards/{username}/{boardname}/")
    fun deleteBoard(@Path("username") username: String,
                    @Path("boardname") boardname: String): Completable
}
