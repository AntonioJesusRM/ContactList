package com.example.contactlistjc.ui.adduser

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.contactlistjc.R
import com.example.contactlistjc.data.repository.local.UserDB
import com.example.contactlistjc.domain.model.KeyboardActionsConfigModels
import com.example.contactlistjc.ui.components.CustomButton
import com.example.contactlistjc.ui.components.CustomFormParam
import com.example.contactlistjc.ui.components.CustomLoad
import com.example.contactlistjc.ui.components.CustomTopBar
import com.example.contactlistjc.ui.components.showErrorDialog

@Composable
fun AddUserScreen(
    navController: NavController,
    onSaveClick: () -> Unit = {},
    addUserViewModel: AddUserViewModel = hiltViewModel()
) {
    val addUserUiState by addUserViewModel.uiState.collectAsState()

    if (addUserUiState.isLoading) {
        CustomLoad()
    } else if (addUserUiState.error != null) {
        val context = LocalContext.current
        val errorMessage = addUserUiState.error!!
        showErrorDialog(context, errorMessage)
    } else {
        AddUserLayout(addUserUiState, onSaveClick, onBackClick = {
            navController.navigateUp()
        }, addUserViewModel)
    }
}

@Composable
fun AddUserLayout(
    addUserUiState: AddUserUiState,
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    addUserViewModel: AddUserViewModel
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val getImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
        }
    }

    Scaffold(topBar = {
        CustomTopBar(
            stringResource(R.string.add_user_screen_tool_bar_title),
            backButton = true,
            optionButton = false,
            onBackClick = onBackClick
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val nameFocusRequester = FocusRequester()
            val lastNameFocusRequester = FocusRequester()
            val addressFocusRequester = FocusRequester()
            val numberFocusRequester = FocusRequester()
            var name by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var address by remember { mutableStateOf("") }
            var number by remember { mutableStateOf("") }

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable {
                        addImage(getImageLauncher)
                    }, contentAlignment = Alignment.Center
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
            CustomFormParam(
                title = stringResource(R.string.add_user_screen_param_name),
                icon = Icons.Filled.Person,
                value = name,
                onValueChange = { name = it },
                keyboardConfig = KeyboardActionsConfigModels(
                    focusRequester = nameFocusRequester, nextParam = lastNameFocusRequester
                ),
                isError = addUserUiState.nameError,
            )
            CustomFormParam(
                title = stringResource(R.string.add_user_screen_param_last_name),
                icon = null,
                value = lastName,
                onValueChange = { lastName = it },
                keyboardConfig = KeyboardActionsConfigModels(
                    focusRequester = lastNameFocusRequester, nextParam = addressFocusRequester
                ),
                isError = addUserUiState.lastNameError,
            )
            CustomFormParam(
                title = stringResource(R.string.add_user_screen_param_address),
                icon = Icons.Filled.Home,
                value = address,
                onValueChange = { address = it },
                keyboardConfig = KeyboardActionsConfigModels(
                    focusRequester = addressFocusRequester, nextParam = numberFocusRequester
                ),
                isError = addUserUiState.addressError
            )
            CustomFormParam(
                title = stringResource(R.string.add_user_screen_param_phone_number),
                icon = Icons.Filled.Phone,
                value = number,
                onValueChange = { number = it },
                keyboardConfig = KeyboardActionsConfigModels(focusRequester = numberFocusRequester,
                    onDoneClick = {
                        addUserViewModel.saveUser(
                            UserDB(
                                name = name,
                                lastName = lastName,
                                address = address,
                                phoneNumber = number,
                                avatar = imageUri.toString()
                            ), onSaveClick
                        )
                    }),
                isError = addUserUiState.phoneNumberError
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(text = stringResource(R.string.add_user_screen_save), onClick = {
                addUserViewModel.saveUser(
                    UserDB(
                        name = name,
                        lastName = lastName,
                        address = address,
                        phoneNumber = number,
                        avatar = imageUri.toString()
                    ), onSaveClick
                )
            })
        }
    })
}

fun addImage(getImageLauncher: ManagedActivityResultLauncher<String, Uri?>) {
    getImageLauncher.launch("image/*")
}