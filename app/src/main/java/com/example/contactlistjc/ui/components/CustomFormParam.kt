package com.example.contactlistjc.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contactlistjc.R
import com.example.contactlistjc.domain.models.KeyboardActionsConfigModels

@Composable
fun CustomFormParam(
    title: String,
    icon: ImageVector?,
    value: String,
    isError: Boolean = false,
    keyboardConfig: KeyboardActionsConfigModels,
    onValueChange: (String) -> Unit
) {
    val keyboardType =
        if (title == stringResource(R.string.add_user_screen_param_phone_number)) KeyboardType.Phone else KeyboardType.Text
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon, contentDescription = title, modifier = Modifier.size(24.dp)
            )
        } else {
            Spacer(modifier = Modifier.width(24.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = title) },
            modifier = Modifier
                .weight(1f)
                .focusRequester(keyboardConfig.focusRequester)
                .border(
                    width = 2.dp,
                    color = if (isError) Color.Red else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = if (keyboardConfig.nextParam != null) ImeAction.Next else ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onNext = {
                keyboardConfig.nextParam?.requestFocus()
            }, onDone = {
                keyboardConfig.onDoneClick?.invoke()
            })
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
fun CustomFormParamPreview() {
    var text by remember { mutableStateOf("") }
    val nameFocusRequester = FocusRequester()
    CustomFormParam(title = stringResource(R.string.add_user_screen_param_name),
        icon = Icons.Filled.Person,
        value = text,
        keyboardConfig = KeyboardActionsConfigModels(
            focusRequester = nameFocusRequester
        ),
        onValueChange = { text = it })
}