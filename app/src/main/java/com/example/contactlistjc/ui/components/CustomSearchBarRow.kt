package com.example.contactlistjc.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.example.contactlistjc.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBarRow(
    text: String,
    onAddUserClick: () -> Unit,
    onQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            modifier = Modifier
                .padding(end = 10.dp)
                .weight(1f)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(query = text,
                    onQueryChange = onQueryChange,
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = { Text(stringResource(R.string.home_screen_search_user)) },
                    leadingIcon = {
                        SearchIcon(text) { onQueryChange("") }
                    }
                )
            },
            expanded = false,
            onExpandedChange = {},
        ) {}
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.home_screen_add_user),
            modifier = Modifier
                .size(24.dp)
                .clickable { onAddUserClick() }
        )
    }
}

@Composable
fun SearchIcon(text: String, onClearClick: () -> Unit) {
    if (text.isNotEmpty()) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier.clickable { onClearClick() }
        )
    } else {
        Icon(Icons.Default.Search, contentDescription = null)
    }
}