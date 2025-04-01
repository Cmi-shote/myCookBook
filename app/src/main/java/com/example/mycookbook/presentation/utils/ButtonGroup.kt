package com.example.mycookbook.presentation.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ButtonGroup(
    buttonLabelOne: String,
    buttonLabelTwo: String,
    buttonActionOne: () -> Unit,
    buttonActionTwo: () -> Unit,
    modifier: Modifier = Modifier,
    shouldLeadingIconOneShow: Boolean = false,
    shouldTrailingIconTwoShow: Boolean = false
) {
    FlowRow(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // First Button as OutlinedButton with white background
        OutlinedButton(
            onClick = { buttonActionOne() },
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary
            ),
        ) {
            if (shouldLeadingIconOneShow) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = buttonLabelOne,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Second Button as normal Button with default theme
        Button(
            onClick = { buttonActionTwo() },
            shape = RoundedCornerShape(32.dp),
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
    ButtonGroup(
        buttonLabelOne = "Button 1",
        buttonLabelTwo = "Button 2",
        buttonActionOne = { /* Handle button 1 action */ },
        buttonActionTwo = { /* Handle button 2 action */ }
    )
}