package com.potatomeme.sample_compose.component_example

import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme
import java.time.format.TextStyle

@Preview(showBackground = true)
@Composable
fun TextPreview() {
    SampleComposeTheme {
        TextExample("")
    }
}

@Composable
fun SimpleText() {
    Text("Text")
}

@Composable
fun TextExample(name: String) {
    Text(
        // text - the text to be displayed
        // text - 표시할 텍스트
        text = "Hello $name\nHello $name\nHello $name",

        // modifier - the Modifier to be applied to this layout node
        // modifier - 이 레이아웃 노드에 적용할 Modifier
        modifier = Modifier
            .width(200.dp)
            .height(200.dp),

        // color - Color to apply to the text. If Color.Unspecified, and style has no color set, this will be LocalContentColor.
        // color - 텍스트에 적용할 색상. Color.Unspecified일 경우, 스타일에 색상이 설정되지 않았다면 LocalContentColor가 적용됩니다.
        color = Color(0xffff9944),

        // fontSize - the size of glyphs to use when painting the text. See TextStyle.fontSize.
        // fontSize - 텍스트를 그릴 때 사용할 글리프 크기. TextStyle.fontSize를 참조하세요.
        fontSize = 30.sp,

        // fontStyle - the typeface variant to use when drawing the letters (e.g., italic). See TextStyle.fontStyle.
        // fontStyle - 글자를 그릴 때 사용할 서체 변형 (예: 이탤릭체). TextStyle.fontStyle을 참조하세요.
        fontStyle = FontStyle.Italic,

        // fontWeight - the typeface thickness to use when painting the text (e.g., FontWeight.Bold).
        // fontWeight - 텍스트를 그릴 때 사용할 서체 두께 (예: FontWeight.Bold).
        fontWeight = FontWeight.Bold,

        // fontFamily - the font family to be used when rendering the text. See TextStyle.fontFamily.
        // fontFamily - 텍스트를 렌더링할 때 사용할 글꼴 패밀리. TextStyle.fontFamily를 참조하세요.
        fontFamily = FontFamily.Cursive,

        // letterSpacing - the amount of space to add between each letter. See TextStyle.letterSpacing.
        // letterSpacing - 각 글자 사이에 추가할 공간의 양. TextStyle.letterSpacing을 참조하세요.
        letterSpacing = 2.sp,

        // textDecoration - the decorations to paint on the text (e.g., an underline). See TextStyle.textDecoration.
        // textDecoration - 텍스트에 그릴 장식 (예: 밑줄). TextStyle.textDecoration을 참조하세요.
        textDecoration = TextDecoration.Underline,


        // textAlign - the alignment of the text within the lines of the paragraph. See TextStyle.textAlign.
        // textAlign - 문단의 각 줄 내에서 텍스트의 정렬. TextStyle.textAlign을 참조하세요.
        textAlign = TextAlign.Center,

        // lineHeight - line height for the Paragraph in TextUnit unit, e.g. SP or EM. See TextStyle.lineHeight.
        // lineHeight - TextUnit 단위로 문단의 줄 높이, 예: SP 또는 EM. TextStyle.lineHeight를 참조하세요.
        lineHeight = 45.sp,

        // overflow - how visual overflow should be handled.
        // overflow - 시각적 오버플로를 처리하는 방법.
        overflow = TextOverflow.Ellipsis,

        // softWrap - whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as if there was unlimited horizontal space. If softWrap is false, overflow and TextAlign may have unexpected effects.
        // softWrap - 텍스트가 소프트 줄 바꿈에서 줄을 나눌지 여부. false일 경우, 텍스트의 글리프는 무한한 가로 공간이 있는 것처럼 배치됩니다. softWrap이 false이면 overflow 및 TextAlign이 예상치 못한 효과를 가질 수 있습니다.
        softWrap = false,

        // maxLines - An optional maximum number of lines for the text to span, wrapping if necessary. If the text exceeds the given number of lines, it will be truncated according to overflow and softWrap. It is required that 1 <= minLines <= maxLines.
        // maxLines - 텍스트가 필요한 경우 줄 바꿈하여 걸쳐 있을 최대 줄 수. 텍스트가 주어진 줄 수를 초과하면 overflow 및 softWrap에 따라 잘립니다. 1 <= minLines <= maxLines를 만족해야 합니다.
        maxLines = 2,

        // minLines - The minimum height in terms of minimum number of visible lines. It is required that 1 <= minLines <= maxLines.
        // minLines - 최소 보이는 줄 수의 최소 높이. 1 <= minLines <= maxLines를 만족해야 합니다.
        minLines = 1,

        // onTextLayout - callback that is executed when a new text layout is calculated. A TextLayoutResult object that callback provides contains paragraph information, size of the text, baselines and other details. The callback can be used to add additional decoration or functionality to the text. For example, to draw selection around the text.
        // onTextLayout - 새로운 텍스트 레이아웃이 계산될 때 실행되는 콜백. 콜백이 제공하는 TextLayoutResult 객체에는 문단 정보, 텍스트 크기, 기준선 및 기타 세부 정보가 포함되어 있습니다. 이 콜백을 사용하여 텍스트 주위에 추가 장식이나 기능을 추가할 수 있습니다. 예를 들어, 텍스트 주위에 선택 영역을 그리기 위해 사용합니다.
        onTextLayout = { textLayoutResult: TextLayoutResult ->
            if (textLayoutResult.didOverflowHeight) {
                Log.d(TextExampleCompanion.TAG, "text가 길어 오버플로우 됬습니다.")
            }
            val isVisualOverFlow: Boolean = textLayoutResult.hasVisualOverflow
            val lines: Int = textLayoutResult.lineCount
            //..
        },

        // style - style configuration for the text such as color, font, line height etc.
        // style - 색상, 글꼴, 줄 높이 등 텍스트에 대한 스타일 구성.
        style = androidx.compose.ui.text.TextStyle(
            // 위의 파라미터를 포함

            //The amount by which the text is shifted up from the current baseline.
            //텍스트가 현재 기준선에서 위로 이동되는 정도입니다.
            baselineShift = null,

            //The advanced typography settings provided by font. The format is the same as the CSS font-feature-settings attribute
            //글꼴에서 제공하는 고급 타이포그래피 설정입니다. 형식은 CSS Font-feature-settings 속성과 동일합니다.
            fontFeatureSettings = null,

            //The geometric transformation applied the text
            //텍스트에 기하학적 변환을 적용했습니다.
            textGeometricTransform = null,

            //The locale list used to select region-specific glyphs.
            //지역별 문자 모양을 선택하는 데 사용되는 로케일 목록입니다
            localeList = null,

            shadow = null,
            drawStyle = null,
            textDirection = null,

            //The indentation of the paragraph.
            //단락의 들여쓰기입니다.
            textIndent = null,

            //Platform specific TextStyle parameters.
            //플랫폼별 TextStyle 매개변수.
            platformStyle = null,
            lineHeightStyle = null,
            lineBreak = null,
            hyphens = null,
            textMotion = null,
        )
    )
}

object TextExampleCompanion {
    const val TAG = "TextExample"
}