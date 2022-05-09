package com.demo.kaamelott.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.R
import com.demo.kaamelott.presentation.models.BookSeason
import com.demo.kaamelott.presentation.models.Quote
import com.demo.kaamelott.presentation.models.getKaamelottImageId

@Composable
fun HomeRandomQuoteCard(
    quote: Quote,
    modifier: Modifier = Modifier
) {
    val typography = MaterialTheme.typography
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val imageModifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
        Image(
            painter = painterResource(BookSeason.getByBook((1..6).random()).getKaamelottImageId()),
            contentDescription = null,
            modifier = imageModifier,
            contentScale = ContentScale.FillBounds
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = quote.quote,
            style = typography.h6,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = quote.metaData.author,
            style = typography.subtitle2,
            modifier = Modifier.padding(bottom = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(
                        id = R.string.home_quote_actor,
                        formatArgs = arrayOf(
                            quote.metaData.actor,
                            quote.metaData.author
                        )
                    ),
                    style = typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(
                        id = R.string.home_quote_personage,
                        quote.metaData.personage
                    ),
                    style = typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(
                        id = R.string.home_quote_episode,
                        formatArgs = arrayOf(
                            quote.metaData.season,
                            quote.metaData.episode
                        )
                    ),
                    style = typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
