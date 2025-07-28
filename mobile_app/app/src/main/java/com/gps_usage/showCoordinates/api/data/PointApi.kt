package com.gps_usage.showCoordinates.api.data

import com.gps_usage.showCoordinates.data.apiAssociated.PostPointDTO
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.statement.HttpResponse

interface PointApi {
    @POST("point/post/{routeId}")
    suspend fun postPoints(
        @Path("routeId") routeId: Long,
        @Body points: List<PostPointDTO>
    ): HttpResponse
}