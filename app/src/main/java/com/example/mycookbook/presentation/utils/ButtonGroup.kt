package com.example.mycookbook.presentation.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtonGroup(
    buttonLabelTwo: String,
    buttonActionTwo: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onFavoriteToggle: () -> Unit = {},
    shouldTrailingIconTwoShow: Boolean = false
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // First Button as Favorite Toggle Button
        OutlinedButton(
            onClick = { onFavoriteToggle() },
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isFavorite) MaterialTheme.colorScheme.primaryContainer else Color.White,
                contentColor = if (isFavorite) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isFavorite) "Favorited" else "Favorite",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Second Button as normal Button with default theme
        Button(
            onClick = { buttonActionTwo() },
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = buttonLabelTwo,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )

            if (shouldTrailingIconTwoShow) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "forward"
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ButtonGroupPreview() {
    var isFavorite by remember { mutableStateOf(false) }

    ButtonGroup(
        buttonLabelTwo = "Button 2",
        buttonActionTwo = { /* Handle button 2 action */ },
        isFavorite = isFavorite,
        onFavoriteToggle = { isFavorite = !isFavorite }
    )
}