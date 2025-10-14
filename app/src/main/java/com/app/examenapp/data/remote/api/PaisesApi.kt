package com.app.examenapp.data.remote.api
import com.app.examenapp.data.remote.dto.PaisDto
import com.app.examenapp.data.remote.dto.PaisesListaDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PaisesApi {

    @GET("all?fields=name")
    suspend fun getPaisesLista(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ):  List<PaisDto>

    @GET("name/{nombre}")
    suspend fun getPais(
        @Path("nombre") nombre: String,
    ):  List<PaisDto>

}