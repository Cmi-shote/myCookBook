package com.example.mycookbook.presentation.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mycookbook.data.model.ExtendedIngredient

@Composable
fun Ingredients(
    ingredient: ExtendedIngredient,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(text = ingredient.name)
        },
        leadingContent = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(ingredient.image)
                    .crossfade(true)
                    .build(),
//                placeholder = painterResource(id = R.drawable.ic_placeholder), // Add your placeholder
//                error = painterResource(id = R.drawable.ic_error), // Add your error image
                contentDescription = ingredient.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(40.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
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
