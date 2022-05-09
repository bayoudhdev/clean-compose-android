package com.demo.kaamelott.presentation.ui.personages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.R
import com.demo.kaamelott.core.utils.isScrolled

@Composable
fun PersonagesScreen(
    onClickItem: (String) -> Unit,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState,
    personageLazyListState: LazyListState,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PersonagesAppBar(
                elevation = if (!personageLazyListState.isScrolled) 0.dp else 4.dp,
                onBackPressed = onBackPressed
            )
        }
    ) { padding ->
        Modifier.padding(padding)
        PersonagesScreenContent(onClickItem = onClickItem, Modifier.padding(padding))
    }
}

@Composable
private fun PersonagesScreenContent(
    onClickItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val personagesArray = stringArrayResource(id = R.array.personages)
    LazyColumn(modifier = modifier.padding(top = 10.dp)) {
        items(personagesArray.size) { index ->
            PersonageItem(
                name = personagesArray[index],
                onClickItem = onClickItem,
            )
        }
    }
}

@Composable
fun PersonageItem(
    name: String,
    onClickItem: (String) -> Unit,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = { onClickItem(name) })
        ) {
            Image(
                painter = painterResource(R.drawable.profile_avatar_placeholder_large),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Text(
                text = name,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(Modifier.weight(0.01f))
            Image(
                contentDescription = null,
                imageVector = Icons.Filled.ChevronRight,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
            )
        }
        Divider(
            modifier = Modifier.padding(start = 72.dp, top = 8.dp, bottom = 8.dp),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
        )
    }
}
