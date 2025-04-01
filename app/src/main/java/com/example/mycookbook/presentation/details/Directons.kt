package com.example.mycookbook.presentation.details

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycookbook.data.model.Direction
import com.example.mycookbook.data.model.toDirectionList

@Composable
fun Directions(
    direction: Direction,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(text = "Step ${direction.step} / ${direction.totalSteps}") //todo:change to build with annotated string
        },
        supportingContent = {
            Text(text = direction.description)
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DirectionsPreview() {
    val dummy = "Cook pasta. Cook pasta. Cook pasta"
    Directions(dummy.toDirectionList().first())
}