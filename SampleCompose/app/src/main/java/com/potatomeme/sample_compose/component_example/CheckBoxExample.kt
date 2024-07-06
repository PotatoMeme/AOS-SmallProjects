package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewCheckBox() {
    SampleComposeTheme {
        CheckBoxExample()
    }
}

@Composable
fun SimpleCheckBox() {
    Checkbox(checked = false, onCheckedChange = {})
}


@Composable
fun CheckBoxExample() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val (getter, setter) = remember { mutableStateOf(false) }
        Checkbox(
            checked = getter,
            onCheckedChange = setter,
            modifier = Modifier,
            enabled = true,
            colors = CheckboxDefaults.colors(),
            interactionSource = remember { MutableInteractionSource() }
        )
        Text(
            text = "CheckBox State?",
            modifier = Modifier.clickable {
                setter(!getter)
            }
        )
    }

}