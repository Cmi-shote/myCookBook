package com.example.mycookbook.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycookbook.R
import com.example.mycookbook.data.model.Ingredient

@Composable
fun IngredientsList(
    modifier: Modifier = Modifier,
    ingredients: List<Ingredient>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
    ) {
        items(ingredients) { ingredient ->
            ListItem(
                headlineContent = {
                    Text(text = ingredient.name)
                },
                leadingContent = {
                    Image(
                        painter = painterResource(id = ingredient.image),
                        contentDescription = "Background Image",
                        modifier = Modifier.size(40.dp),
                    )
                },
                trailingContent = {
                    Text(
                        text = ingredient.quantity,
                        fontWeight = FontWeight.Light,
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WizardHousesPreview() {
    val sampleIngredient = (1..10).map {
        Ingredient(
            image = R.drawable.images,
            name = "Tomatoes",
            quantity = "500 g"
        )
    }

    IngredientsList(
        ingredients = sampleIngredient,
        modifier = Modifier.fillMaxSize()
    )
}
