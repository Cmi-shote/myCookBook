package com.example.mycookbook.presentation.grocery

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mycookbook.data.model.Grocery
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.recipes.RecipesViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GroceryScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (Grocery) -> Unit,
    viewModel: RecipesViewModel,
) {
    val today = LocalDate.now()
    val weekDays = remember { getCurrentWeek(today) }
    var selectedDay by remember { mutableStateOf(today) }

    val groceryList by viewModel.groceryList.collectAsStateWithLifecycle()
    val filteredGroceryList = groceryList.filter { it.date == selectedDay.toString() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Grocery",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(16.dp))
        GroceryCalendarRow(
            weekDays = weekDays,
            selectedDay = selectedDay,
            onDaySelected = { selectedDay = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (filteredGroceryList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No grocery list for ${selectedDay.format(DateTimeFormatter.ofPattern("MMM dd"))}",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredGroceryList) { grocery ->
                    GroceryItemCard(
                        grocery = grocery,
                        onItemClicked = { onItemClicked(grocery) }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GroceryCalendarRow(
    weekDays: List<LocalDate>,
    selectedDay: LocalDate,
    onDaySelected: (LocalDate) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(weekDays) { day ->
            val isSelected = day == selectedDay
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) Color(0xFFE6F4EA) else Color.Transparent)
                    .clickable { onDaySelected(day) }
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    color = if (isSelected) Color(0xFF7CA86E) else Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = day.dayOfMonth.toString(),
                    color = if (isSelected) Color(0xFF7CA86E) else Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun GroceryItemCard(
    grocery: Grocery,
    onItemClicked: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onItemClicked),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(grocery.recipeDetails.image)
                    .crossfade(true)
                    .build(),
                contentDescription = grocery.recipeDetails.title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = grocery.recipeDetails.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "${grocery.current} / ${grocery.total} ingredients",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            if (grocery.current == grocery.total) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Complete",
                    tint = Color(0xFF7CA86E),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getCurrentWeek(today: LocalDate): List<LocalDate> {
    val startOfWeek = today.minusDays(today.dayOfWeek.value.toLong() - 1)
    return (0..6).map { startOfWeek.plusDays(it.toLong()) }
} 