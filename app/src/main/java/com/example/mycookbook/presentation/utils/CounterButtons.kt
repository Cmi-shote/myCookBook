package com.example.mycookbook.presentation.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycookbook.ui.theme.MyCookBookTheme

@Composable
fun CounterButton(
    modifier: Modifier = Modifier
) {
    var count by remember { mutableIntStateOf(1) }
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.height(34.dp) // Set a fixed height for the Row
        ) {
            IconButton(
                onClick = { if (count > 1) count-- },
                modifier = Modifier.height(20.dp)
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease")
            }

            VerticalDivider(
                modifier = Modifier.height(24.dp), // Set a fixed height for the divider
                color = Color.Black
            )

            IconButton(
                onClick = { count++ },
                modifier = Modifier.height(20.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CounterButtonPreview() {
    MyCookBookTheme {
        CounterButton()
    }
}