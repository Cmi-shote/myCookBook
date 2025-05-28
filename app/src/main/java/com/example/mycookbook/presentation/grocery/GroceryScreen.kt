package com.example.mycookbook.presentation.grocery

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.recipes.RecipesViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GroceryScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (RecipeDetails) -> Unit, //pass id or something
    viewModel: RecipesViewModel
) {
//    val today = LocalDate.now()
//    val weekDays = remember { getCurrentWeek(today) }
//    var selectedDay by remember { mutableStateOf(today) }
//
//    // Placeholder grocery data by day
//    val groceryData = remember {
//        mapOf(
//            weekDays[2] to
//                    sampleRecipeDetails
//        )
//    }
//    val items = groceryData[selectedDay] ?: emptyList()
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp)
//    ) {
//        Text(
//            text = "Grocery",
//            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        GroceryCalendarRow(
//            weekDays = weekDays,
//            selectedDay = selectedDay,
//            onDaySelected = { selectedDay = it }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        if (items.isEmpty()) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "No grocery list added yet",
//                    color = Color.LightGray,
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            }
//        } else {
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                items(items) { item ->
//                    GroceryItemCard(data = item, onItemClicked = {
//                        viewModel.updateRecipe(item)
//                        onItemClicked(item)
//                    })
//                }
//            }
//        }
//    }
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
fun GroceryItemCard(data: RecipeDetails, onItemClicked: (RecipeDetails) -> Unit = {}) {
//    val size = data.ingredients.size
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(80.dp)
//            .clickable {
//                onItemClicked(data)
//            },
//        elevation = CardDefaults.cardElevation(2.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Image(
//                painter = painterResource(id = data.foodImage),
//                contentDescription = data.title,
//                modifier = Modifier
//                    .width(120.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .padding(end = 8.dp),
//                contentScale = ContentScale.FillBounds
//            )
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Text(
//                    text = data.title,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 16.sp
//                )
//
//                Text(
//                    text = "${data.current} / $size ingredients",
//                    fontSize = 13.sp,
//                    color = Color.Gray
//                )
//            }
//            if (data.current == size) {
//                Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = "Complete",
//                    tint = Color(0xFF7CA86E),
//                    modifier = Modifier.padding(end = 16.dp)
//                )
//            }
//        }
//    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentWeek(today: LocalDate): List<LocalDate> {
    val startOfWeek = today.minusDays((today.dayOfWeek.value % 7).toLong())
    return (0..6).map { startOfWeek.plusDays(it.toLong()) }
} 