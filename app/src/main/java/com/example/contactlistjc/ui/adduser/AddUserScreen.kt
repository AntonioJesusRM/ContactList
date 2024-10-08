package com.example.contactlistjc.ui.adduser

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.contactlistjc.R
import com.example.contactlistjc.data.repository.local.UserDB
import com.example.contactlistjc.domain.model.KeyboardActionsConfigModels
import com.example.contactlistjc.ui.components.CustomButton
import com.example.contactlistjc.ui.components.CustomFormParam
import com.example.contactlistjc.ui.components.CustomImagePicker
import com.example.contactlistjc.ui.components.CustomLoad
import com.example.contactlistjc.ui.components.CustomTopBar
import com.example.contactlistjc.ui.components.showErrorDialog
import java.io.File
import java.io.FileOutputStream

@Composable
fun AddUserScreen(
    navController: NavController,
    userName: String?,
    onSaveClick: () -> Unit = {},
    addUserViewModel: AddUserViewModel = hiltViewModel()
) {
    val addUserUiState by addUserViewModel.uiState.collectAsState()

    if (userName.isNullOrEmpty())
        Log.d("TAG", "%> Empty user")
    else
        Log.d("TAG", "%> User: $userName")

    if (addUserUiState.isLoading) {
        CustomLoad()
    } else if (addUserUiState.error != null) {
        val context = LocalContext.current
        val errorMessage = addUserUiState.error!!
        showErrorDialog(context, errorMessage) {
            addUserViewModel.quitError(addUserUiState.user)
        }
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
    var imageUri by remember {
        if (addUserUiState.user.avatar.isBlank())
            mutableStateOf<Uri?>(null)
        else
            mutableStateOf<Uri?>(Uri.parse(addUserUiState.user.avatar))
    }
    val context = LocalContext.current

    val getImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
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
            var name by remember { mutableStateOf(addUserUiState.user.name) }
            var lastName by remember { mutableStateOf(addUserUiState.user.lastName) }
            var address by remember { mutableStateOf(addUserUiState.user.address) }
            var number by remember { mutableStateOf(addUserUiState.user.phoneNumber) }

            CustomImagePicker(imageUri, getImageLauncher)

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
                keyboardConfig = KeyboardActionsConfigModels(
                    focusRequester = numberFocusRequester,
                    onDoneClick = {
                        val avatarPath = imageUri?.let { saveImageToInternalStorage(it, context) }
                        saveData(
                            addUserViewModel, avatarPath, UserDB(
                                name = name,
                                lastName = lastName,
                                address = address,
                                phoneNumber = number,
                                avatar = ""
                            ), onSaveClick
                        )
                    }),
                isError = addUserUiState.phoneNumberError
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(text = stringResource(R.string.add_user_screen_save), onClick = {
                val avatarPath = imageUri?.let { saveImageToInternalStorage(it, context) }
                saveData(
                    addUserViewModel, avatarPath, UserDB(
                        name = name,
                        lastName = lastName,
                        address = address,
                        phoneNumber = number,
                        avatar = ""
                    ), onSaveClick
                )
            })
        }
    })
}

fun saveData(
    addUserViewModel: AddUserViewModel,
    avatarPath: String?,
    user: UserDB,
    onSaveClick: () -> Unit = {}
) {
    val userSave = user.copy(avatar = avatarPath ?: "")
    addUserViewModel.saveUser(userSave, onSaveClick)
}

fun addImage(getImageLauncher: ManagedActivityResultLauncher<String, Uri?>) {
    getImageLauncher.launch("image/*")
}

fun saveImageToInternalStorage(uri: Uri, context: Context): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()
        inputStream?.close()
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}