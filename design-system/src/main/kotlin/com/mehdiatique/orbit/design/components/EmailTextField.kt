package com.mehdiatique.orbit.design.components

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.mehdiatique.orbit.design.theme.OrbitTheme

/**
 * A composable for an email input field with built-in validation and error handling.
 *
 * @param value The current text displayed in the email input field.
 * @param onValueChange Callback invoked when the text in the field changes.
 * @param modifier Modifier for styling and layout adjustments of the email input field.
 * @param colors [TextFieldColors] to customize the appearance of the email field.
 * @param showValidationErrors Boolean flag to determine whether to display validation error messages.
 * @param onValidationChanged Callback invoked with the validation status whenever it changes.
 *        Returns `true` if the email is valid, otherwise `false`.
 * @param label A composable to define the label of the email input field.
 *
 * The email input field automatically validates its content based on the following rules:
 * - The field must not be empty.
 * - The content must conform to a valid email address format.
 *
 * If the input is invalid, an error message is displayed below the field when `showValidationErrors` is `true`.
 * The error messages can indicate:
 * - "Email field must not be blank" if the field is empty.
 * - "Invalid email address" if the input doesn't match a valid email format.
 */
@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    showValidationErrors: Boolean = false,
    onValidationChanged: ((isValid: Boolean) -> Unit)? = null,
    label: @Composable (() -> Unit)
) {
    val emailErrors = remember(value) { validateEmail(value) }

    LaunchedEffect(emailErrors) {
        onValidationChanged?.invoke(emailErrors == null)
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            singleLine = true,
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            isError = showValidationErrors && emailErrors != null,
            colors = colors
        )

        if (showValidationErrors && emailErrors != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "• $emailErrors",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

private fun validateEmail(email: String): String? =
    when {
        email.isBlank() -> "Email field must not be blank"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email address"
        else -> null
    }

@PreviewFontScale
@Composable
fun EmailTextFieldPreview() {
    OrbitTheme(darkTheme = true) {
        EmailTextField(
            value = "test",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        ) { Text("Email") }
    }
}
