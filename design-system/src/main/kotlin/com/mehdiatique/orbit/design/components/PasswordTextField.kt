package com.mehdiatique.orbit.design.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.mehdiatique.orbit.design.theme.OrbitTheme

/**
 * A composable function that renders a password input field with validation and error handling.
 *
 * @param value The current text entered in the password field.
 * @param onValueChange A callback invoked whenever the text in the password field changes.
 * @param modifier A [Modifier] for applying styling and layout adjustments to the password field container.
 * @param colors A [TextFieldColors] instance to customize the text field's appearance.
 * @param showValidationErrors A flag indicating whether validation errors should be displayed if the password is invalid.
 * @param onValidationChanged A callback invoked when the validation status changes.
 *        It receives a boolean indicating whether the password meets all validation criteria.
 * @param label A composable function to provide a custom label for the text field.
 *
 * This password field includes:
 * - A toggle for showing or hiding the password.
 * - Automatic validation based on the following rules:
 *   - Must be at least 8 characters long.
 *   - Must include at least one uppercase letter.
 *   - Must include at least one lowercase letter.
 *   - Must include at least one digit.
 *   - Must include at least one special character.
 * - Displays validation errors when the input does not meet the defined criteria.
 * - Highlights the field with an error indicator when validation fails.
 *
 * Validation errors are displayed in a list format below the text field when applicable.
 * If the field is left blank, a specific error message is shown.
 */
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    showValidationErrors: Boolean = false,
    onValidationChanged: ((isValid: Boolean) -> Unit)? = null,
    label: @Composable (() -> Unit)
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var showErrors by remember { mutableStateOf(false) }
    val passwordErrors = remember(value) { validatePassword(value) }

    LaunchedEffect(passwordErrors) {
        onValidationChanged?.invoke(passwordErrors.isEmpty())
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                if (showValidationErrors) showErrors = it.isNotEmpty()
            },
            label = label,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (showValidationErrors && !focusState.isFocused) {
                        showErrors = true
                    }
                },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            isError = showErrors && passwordErrors.isNotEmpty(),
            colors = colors
        )

        if (showErrors) {
            if (value.isEmpty()) {
                Text(
                    text = "Password field must not be blank",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            } else if (passwordErrors.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Password must include:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
                passwordErrors.forEach { error ->
                    Text(
                        text = "• $error",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

private fun validatePassword(password: String): List<String> =
    mutableListOf<String>().apply {
        if (password.length < 8) add("at least 8 characters")
        if (!password.any { it.isUpperCase() }) add("at least one uppercase letter")
        if (!password.any { it.isLowerCase() }) add("at least one lowercase letter")
        if (!password.any { it.isDigit() }) add("at least one number")
        if (!password.any { !it.isLetterOrDigit() }) add("at least one special character")
        if (password.isBlank()) clear()
    }

@PreviewFontScale
@Composable
fun PasswordTextFieldPreview() {
    OrbitTheme(darkTheme = true) {
        PasswordTextField(
            value = "test",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        ) { Text("Password") }
    }
}