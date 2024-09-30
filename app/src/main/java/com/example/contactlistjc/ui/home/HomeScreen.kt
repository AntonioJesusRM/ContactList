package com.example.contactlistjc.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.contactlistjc.R
import com.example.contactlistjc.ui.components.CustomLoad
import com.example.contactlistjc.ui.components.CustomSearchBarRow
import com.example.contactlistjc.ui.components.CustomTopBar
import com.example.contactlistjc.ui.components.showErrorDialog

@Composable
fun HomeScreen(
    onChatClick: () -> Unit = {},
    onAddUserClick: () -> Unit = {},
    onOptionClick: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by homeViewModel.uiState.collectAsState()
    homeViewModel.getUsers()
    if (homeUiState.isLoading) {
        CustomLoad()
    } else if (homeUiState.error != null) {
        val context = LocalContext.current
        val errorMessage = homeUiState.error!!
        showErrorDialog(context, errorMessage)
    } else {
        HomeLayout(
            homeUiState = homeUiState,
            onChatClick = onChatClick,
            onAddUserClick = onAddUserClick,
            onOptionClick = onOptionClick
        )
    }
}

@Composable
fun HomeLayout(
    homeUiState: HomeUiState,
    onChatClick: () -> Unit = {},
    onAddUserClick: () -> Unit = {},
    onOptionClick: () -> Unit = {}
) {
    Scaffold(topBar = {
        CustomTopBar(
            title = stringResource(R.string.home_screen_tool_bar_title),
            backButton = false,
            optionButton = true,
            onOptionClick = onOptionClick
        )
    }, content = { innerPadding ->
        var text by rememberSaveable { mutableStateOf("") }

        val filteredUsers = homeUiState.listUser.filter { user ->
            user.name.contains(text, ignoreCase = true) || user.lastName.contains(
                text, ignoreCase = true
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true }
                .padding(innerPadding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomSearchBarRow(
                    text = text, onAddUserClick = onAddUserClick
                ) { query -> text = query }
            }
            if (homeUiState.listUser.isEmpty()) {
                Text(
                    text = stringResource(R.string.home_screen_empty_user_list),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .semantics { traversalIndex = 1f }
                        .padding(12.dp)) {
                    items(items = filteredUsers) { user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (user.avatar.isEmpty()) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Image(
                                        painter = rememberAsyncImagePainter(user.avatar),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            Text(
                                text = user.name,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .weight(1f)
                                    .clickable { onChatClick() },
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                            )
                            Icon(imageVector = Icons.Filled.Create,
                                contentDescription = stringResource(R.string.home_screen_edit_user),
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { onAddUserClick() })
                        }
                    }
                }
            }
        }
    })
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}