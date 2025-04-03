package com.mehdiatique.orbit.design.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerSelector(
    allContacts: List<Contact>,
    selectedOwner: Contact?,
    onOwnerSelected: (Contact?) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val currentName = selectedOwner?.name ?: ""
    var inputText by remember(currentName) { mutableStateOf(currentName) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                expanded = true
            },
            label = { Text("Owner") },
            trailingIcon = {
                if (selectedOwner != null) {
                    Text(
                        text = "✕",
                        modifier = Modifier
                            .width(24.dp)
                            .clickable {
                                inputText = ""
                                onOwnerSelected(null)
                            }
                            .semantics { contentDescription = "Clear selected owner" }
                    )
                } else {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            allContacts
                .filter { contact -> contact.name.contains(inputText, ignoreCase = true) }
                .forEach { contact ->
                    DropdownMenuItem(
                        text = { Text(contact.name) },
                        onClick = {
                            expanded = false
                            onOwnerSelected(contact)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
        }
    }
}