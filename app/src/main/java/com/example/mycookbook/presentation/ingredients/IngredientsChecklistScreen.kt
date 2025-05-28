package com.example.mycookbook.presentation.ingredients

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mycookbook.data.model.RecipeDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsChecklistScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    selectedRecipe: RecipeDetails
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp // Get the device screen height
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState),
        sheetPeekHeight = screenHeight / 2, // Set peek height to half of the screen
        sheetContent = {
//            Content(selectedRecipe = selectedRecipe)
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(selectedRecipe.image)
                    .crossfade(true)
                    .build(),
//                placeholder = painterResource(id = R.drawable.ic_placeholder), // Add your placeholder
//                error = painterResource(id = R.drawable.ic_error), // Add your error image
                contentDescription = selectedRecipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
        }
    }
}

//@Composable
//fun Content(modifier: Modifier = Modifier, selectedRecipe: RecipeDetails) {
//    var servings by remember { mutableStateOf(4) }
//    val ingredientsSize = selectedRecipe.extendedIngredients.size
//    var checkedState by remember { mutableStateOf(List(ingredientsSize) { false }) }
//
//    val checkedCount = checkedState.count { it }
//    Box(modifier = modifier.fillMaxSize()) {
//        // Top image background
//        Image(
//            painter = painterResource(id = selectedRecipe.extendedIngredients.),
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(180.dp),
//            alignment = Alignment.TopCenter
//        )
//        // Main content
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White, shape = RectangleShape)
//                .padding(top = 140.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//            ) {
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    text = selectedRecipe.title,
//                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//                    modifier = Modifier.weight(1f)
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = "$checkedCount / $ingredientsSize ingredients",
//                    color = Color.Gray,
//                    fontSize = 16.sp,
//                    modifier = Modifier.padding(start = 56.dp)
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(
//                        text = "Ingredients",
//                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "$servings Serves",
//                        color = Color.Gray,
//                        fontSize = 15.sp
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(8.dp))
//                            .background(Color(0xFFF5F6FA))
//                    ) {
//                        IconButton(
//                            onClick = { if (servings > 1) servings-- },
//                            enabled = servings > 1
//                        ) {
//                            Text("-", fontSize = 18.sp, color = Color.Black)
//                        }
//                        Text(servings.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                        IconButton(onClick = { servings++ }) {
//                            Text("+", fontSize = 18.sp, color = Color.Black)
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                HorizontalDivider()
//                LazyColumn(
//                    modifier = Modifier.fillMaxWidth(),
//                    contentPadding = PaddingValues(vertical = 8.dp),
//                    verticalArrangement = Arrangement.spacedBy(0.dp)
//                ) {
//                    itemsIndexed(selectedRecipe.ingredients) { index, item ->
//                        val checked = checkedState[index]
//                        IngredientChecklistRow(
//                            item = item,
//                            checked = checked,
//                            onCheckedChange = {
//                                checkedState = checkedState.toMutableList().also { it[index] = it[index].not() }
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun IngredientChecklistRow(
//    item: Ingredient,
//    checked: Boolean,
//    onCheckedChange: () -> Unit
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onCheckedChange() }
//            .padding(vertical = 8.dp)
//    ) {
//        Checkbox(
//            checked = checked,
//            onCheckedChange = { onCheckedChange() },
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color(0xFF7CA86E),
//                uncheckedColor = Color.LightGray
//            )
//        )
//        Text(
//            text = item.name,
//            modifier = Modifier.weight(1f),
//            style = TextStyle(
//                fontSize = 16.sp,
//                color = if (checked) Color.Gray else Color.Black,
//                textDecoration = if (checked) TextDecoration.LineThrough else null
//            )
//        )
//        Text(
//            text = item.quantity,
//            color = Color.Gray,
//            fontSize = 15.sp
//        )
//    }
//}