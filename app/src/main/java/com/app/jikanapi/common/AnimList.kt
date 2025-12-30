package com.app.jikanapi.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.app.jikanapi.data.model.AnimeEntity

@Composable
fun AnimList(
    animList: LazyPagingItems<AnimeEntity>,
    onClick: (AnimeEntity, String) -> Unit,
) {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
    ) {
        items(
            animList.itemCount,
        ) { index ->
            val item = animList[index] ?: return@items // This ensures pagination works correctly

            Column(Modifier.padding(6.dp)) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(4.dp),
                    onClick = {
                        animList[index]?.let {
                            onClick(item, index.toString())

                        }
                    }
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    item.titleEnglish ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600)
                )
                Row {

                    Text(
                        "Episodes: ",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600)
                    )

                    Text(
                        item.episodes.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600)
                    )
                }
                Row {

                    Text(
                        "Rating: ",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600)
                    )

                    Text(
                        item.rating.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600)
                    )
                }

            }
        }
        animList.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            CircularProgressIndicator(
                                color = Color.Red,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}