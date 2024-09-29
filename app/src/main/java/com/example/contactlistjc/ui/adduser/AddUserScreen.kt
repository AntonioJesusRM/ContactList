package com.example.contactlistjc.ui.adduser

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.contactlistjc.R
import com.example.contactlistjc.ui.components.CustomButton
import com.example.contactlistjc.ui.components.CustomFormParam
import com.example.contactlistjc.ui.components.CustomTopBar
import com.example.contactlistjc.ui.extensions.TAG

@Composable
fun AddUserScreen(
    onSaveClick: () -> Unit = {},
    addUserViewModel: AddUserViewModel = viewModel()
) {
    Scaffold(topBar = {
        CustomTopBar(
            stringResource(R.string.add_user_screen_tool_bar_title),
            backButton = true,
            optionButton = false
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var name by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var address by remember { mutableStateOf("") }
            var number by remember { mutableStateOf("") }
            val imageUri by remember { mutableStateOf<Uri?>(null) }

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable {
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri == null) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomFormParam(title = stringResource(R.string.add_user_screen_param_name),
                icon = Icons.Filled.Person,
                value = name,
                onValueChange = { name = it })
            CustomFormParam(title = stringResource(R.string.add_user_screen_param_last_name),
                icon = null,
                value = lastName,
                onValueChange = { lastName = it })
            CustomFormParam(title = stringResource(R.string.add_user_screen_param_address),
                icon = Icons.Filled.Home,
                value = address,
                onValueChange = { address = it })
            CustomFormParam(title = stringResource(R.string.add_user_screen_param_phone_number),
                icon = Icons.Filled.Phone,
                value = number,
                onValueChange = { number = it })
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(text = stringResource(R.string.add_user_screen_save), onClick = {
                if (addUserViewModel.checkParam(name, lastName, address, number, imageUri)) {
                    onSaveClick()
                } else {
                    Log.d(TAG, "%> Error")
                }
            })
        }
    })
}

@Preview
@Composable
fun AddUserScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        AddUserScreen()
    }
}
