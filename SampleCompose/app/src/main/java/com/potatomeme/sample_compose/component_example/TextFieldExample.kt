package com.potatomeme.sample_compose.component_example

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewTextField() {
    SampleComposeTheme {
        TextFieldExample()
    }
}

@Composable
fun SimpleTextField() {
    TextField(value = "", onValueChange = { text: String ->
        Log.e("*****", "SimpleTextField: $text")
    })
}


@Composable
fun TextFieldExample() {
    val (getter, setter) = remember {
        mutableStateOf<String>("")
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusState = if (isFocused) {
        "포커스 on"
    } else {
        "포커스 off"
    }

    TextField(
        value = getter,
        onValueChange = setter,
        modifier = Modifier
            .fillMaxWidth()

            .padding(20.dp),
        enabled = true,
        readOnly = false,
        textStyle = LocalTextStyle.current,
        label = @Composable {
            Text(text = "Sample Label")
        },
        placeholder = @Composable {
            Text(text = "Sample PlaceHolder")
        },
        leadingIcon = if (getter.isNotBlank()) {
            null
        } else {
            @Composable {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Sample Icon",
                )
            }
        },
        trailingIcon = if (getter.isNotBlank() && getter.length > 5) {
            @Composable {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Sample Icon",
                )
            }
        } else {
            null
        },
        prefix = @Composable {
            Text(text = "Front")
        },
        suffix = @Composable {
            Text(text = "End")
        },
        supportingText = @Composable {
            Text(text = focusState)
        },
        isError = false,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
        singleLine = true,
        //maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
        //minLines: Int = 1,
        interactionSource = interactionSource,
        shape = TextFieldDefaults.shape,
        colors = TextFieldDefaults.colors()
    )
}