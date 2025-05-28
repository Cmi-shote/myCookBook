package com.example.mycookbook.presentation.details

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mycookbook.R
import com.example.mycookbook.data.model.ExtendedIngredient

@Composable
fun Ingredients(
    ingredient: ExtendedIngredient,
    modifier: Modifier = Modifier,
    servings: Int = 1
) {
    ListItem(
        headlineContent = {
            Text(text = ingredient.name)
        },
        leadingContent = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://img.spoonacular.com/ingredients_100x100/${ingredient.image}")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.images), // Add your placeholder
                error = painterResource(id = R.drawable.images), // Add your error image
                contentDescription = ingredient.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        },
        trailingContent = {
            Text(
                text = ingredient.amount.toString() + " " + ingredient.unit,
                fontWeight = FontWeight.Light,
            )
        },
        modifier = modifier
    )
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun IngredientsPreview() {
//
//    Ingredients(
//        ingredient = sampleIngredient.first()
//    )
//}
