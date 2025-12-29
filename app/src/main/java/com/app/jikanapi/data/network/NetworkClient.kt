package com.app.jikanapi.data.network

import com.app.jikanapi.data.model.AnimDetailDTO
import com.app.jikanapi.data.model.AnimListDTO
import com.app.jikanapi.data.utils.ResponseResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class NetworkClient(private val client: HttpClient) {

    suspend fun getFlowImageList(page: Int): AnimListDTO {

        val response = client.get(NetworkConstants.topAnim) {
            url {
                parameters.append("page", "${page}")
            }
        }
        return response.body<AnimListDTO>()

    }

    /* suspend fun getAnimDetail(id: String): AnimDetailDTO {

         val response = client.get(NetworkConstants.animDetail) {

             url.appendPathSegments(id)
         }
         return response.body<AnimDetailDTO>()

     }*/
    suspend fun getAnimDetail(id: String): ResponseResult<AnimDetailDTO> {

        try {

            val response = client.get(NetworkConstants.animDetail) {
                url {
                    url.appendPathSegments(id)
                }
            }
            if (response.status.value == 200) {
                val data = response.body<AnimDetailDTO>()
                return ResponseResult.Success(data)

            } else {
                return ResponseResult.Error(null, "error")
            }

        } catch (e: Exception) {
            return ResponseResult.Error(null, e.message.toString())
        }

    }


}