package com.demo.kaamelott.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.R
import com.demo.kaamelott.presentation.models.Quote

@Composable
fun NotAvailableFeaturePopupDialog(
    @StringRes message: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = stringResource(id = message),
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.close))
            }
        }
    )
}

@Composable
fun QuotePopupDialog(
    quote: Quote?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column(
                modifier = Modifier
                    .wrapContentHeight(CenterVertically)
                    .fillMaxWidth()
                    .wrapContentWidth(CenterHorizontally),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        id = R.string.quote_title,
                        quote?.metaData?.personage.orEmpty()
                    ),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    softWrap = true
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = stringResource(id = R.string.quote_details, quote?.quote.orEmpty()),
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 25,
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Justify,
                    softWrap = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = quote?.metaData?.author.orEmpty(),
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.Start)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = stringResource(
                        id = R.string.home_quote_episode,
                        formatArgs = arrayOf(
                            quote?.metaData?.season.orEmpty(),
                            quote?.metaData?.episode.orEmpty()
                        )
                    ),
                    style = MaterialTheme.typography.caption,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.Start)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.close))
            }
        }
    )
}