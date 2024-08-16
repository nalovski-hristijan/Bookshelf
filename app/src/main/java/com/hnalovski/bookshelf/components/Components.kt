package com.hnalovski.bookshelf.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopBar(title: String, isHomeScreen: Boolean, onNavBackClicked: () -> Unit = {}) {
    Surface(shadowElevation = 10.dp) {
        CenterAlignedTopAppBar(
            title = { Text(text = title, textAlign = TextAlign.Center) },
            navigationIcon = {
                if (!isHomeScreen) {
                    Icon(
                        modifier = Modifier.clickable { onNavBackClicked.invoke() },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back button"
                    )
                } else {
                    Box {

                    }
                }
            })
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Search,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        value = valueState.value,
        onValueChange = { newValue -> valueState.value = newValue },
        label = { Text(labelId) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.onBackground),
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction)
    )

}