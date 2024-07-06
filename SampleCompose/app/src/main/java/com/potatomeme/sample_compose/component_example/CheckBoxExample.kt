package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
        //CheckBoxExample()
        SlotEx()
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

@Composable
fun CheckBoxWithContent(
    checked: Boolean,
    toggleState: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { toggleState() }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { toggleState() },
        )
        content()
    }
}

@Composable
fun SlotEx() {
    val checked1 = remember { mutableStateOf(false) }
    val checked2 = remember { mutableStateOf(false) }

    Column {
        CheckBoxWithContent(
            checked = checked1.value,
            toggleState = { checked1.value = !checked1.value }
        ) {
            Text(
                text = "텍스트 1"
            )
        }
        CheckBoxWithContent(
            checked = checked2.value,
            toggleState = { checked2.value = !checked2.value }
        ) {
            Text(
                text = "텍스트 2"
            )
        }
    }
}


