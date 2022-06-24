package com.linda.dailynasa.ui.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.linda.dailynasa.BuildConfig
import com.linda.dailynasa.data.remote.NasaApi
import com.linda.dailynasa.data.remote.dto.Photo
import retrofit2.HttpException
import java.io.IOException

class RoverPagingSource(
    private val api: NasaApi,
    private val camera:String): PagingSource<Int,Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: 1
        val key = BuildConfig.API_KEY

        return try {
            val response = api.getMarsRoverData(camera,key,1000, position)
            val repos = response.body()
            val nextKey = if (repos == null) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + 1
            }
            LoadResult.Page(
                data = repos!!.photos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}