package com.demo.kaamelott.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.R
import com.demo.kaamelott.presentation.models.Quote

@Composable
fun RandomQuotesComponent(
    quote: Quote,
    navigateToQuote: (Quote) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    Row(
        Modifier
            .clickable(
                onClick = {
                    navigateToQuote(quote)
                }
            )
    ) {
        Image(
            painter = painterResource(R.drawable.citations),
            contentDescription = null, // decorative
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .size(40.dp, 40.dp)
                .clip(MaterialTheme.shapes.small)
        )
        Column(
            Modifier
                .weight(1f)
                .padding(top = 10.dp, bottom = 16.dp)
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = quote.metaData.actor,
                    style = MaterialTheme.typography.overline
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(quote.quote, style = MaterialTheme.typography.subtitle2, maxLines = 2)
            SeasonAndEpisodeSection(
                quote = quote,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            IconButton(onClick = { openDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null
                )
            }
        }
    }
    if (openDialog) {
        NotAvailableFeaturePopupDialog(message = R.string.functionality_not_available) {
            openDialog = false
        }
    }
}

@Composable
private fun SeasonAndEpisodeSection(
    quote: Quote,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(
                    id = R.string.home_quote_episode,
                    formatArgs = arrayOf(
                        quote.metaData.season,
                        quote.metaData.episode,
                    )
                ),
                style = MaterialTheme.typography.body2
            )
        }
    }
}