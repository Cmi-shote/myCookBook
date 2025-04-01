package com.example.mycookbook.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycookbook.data.model.Ingredient
import com.example.mycookbook.sampleIngredient

@Composable
fun Ingredients(
    ingredient: Ingredient,
    modifier: Modifier = Modifier
) {
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
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IngredientsPreview() {

    Ingredients(
        ingredient = sampleIngredient.first()
    )
}
