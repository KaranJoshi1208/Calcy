package com.karan.calcy.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PlatformImeOptions
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karan.calcy.model.CalculatorActions
import com.karan.calcy.ui.theme.DarkGray
import com.karan.calcy.ui.theme.MediumGray
import com.karan.calcy.ui.theme.Orange
import com.karan.calcy.ui.theme.Purple40

@Composable
fun CalButton(
    content: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .then(modifier)
    ) {
        Text(
            text = content,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
fun Calcy(
    expState: MutableState<TextFieldValue>,
    ansState: MutableState<String>,
    buttonSpacing: Dp = 12.dp,
    modifier: Modifier = Modifier,
    onAction: (CalculatorActions) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 44.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            val keyBoard = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current
            val cursorOffset = remember { mutableStateOf(Offset.Zero) }
            val textLayoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp)
                    .horizontalScroll(rememberScrollState()),

                ) {

                BasicTextField(
                    value = expState.value,
                    onValueChange = {
                        expState.value = it
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 44.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFFFFFFFF)
                        ),
                    cursorBrush = SolidColor(Color.Red),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    decorationBox = {innerTextField ->
                        Box {
                            innerTextField() // Display the actual text field

                            // Draw the custom cursor
                            Canvas(modifier = Modifier) {
                                drawLine(
                                    color = Color.Red,
                                    start = cursorOffset.value,
                                    end = cursorOffset.value.copy(y = cursorOffset.value.y + 20.dp.toPx()), // Length of the cursor
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }
                    },
                    singleLine = true,
                    readOnly = true
                )

//                TextField(
//                    value = expState.value,
//                    onValueChange = { new ->
//                        expState.value = new
//                    },
//
//                    modifier = Modifier
//                        .onFocusChanged { focus ->
//                            if (focus.isFocused) {
////                                focusManager.clearFocus()
//                                keyBoard?.hide()
//                            }
//                        }
//
//                    ,
////                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
//                    textStyle = LocalTextStyle.current.copy(fontSize = 44.sp, fontFamily = FontFamily.Monospace),
//                    colors = TextFieldDefaults.colors(
//                        unfocusedContainerColor = Color.Transparent,
//                        focusedContainerColor = Color.Transparent,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        focusedTextColor = Color.White,
//                        unfocusedTextColor = MediumGray,
//                        cursorColor = Orange
//                    ),
//
//                    readOnly = true,
//                    singleLine = true,
//                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = ansState.value,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 52.sp,
                    color = Color.White,
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Orange)
            )

            Column(
                modifier = Modifier
                    .padding(vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CalButton(
                        content = "AC",
                        modifier = Modifier
                            .background(Color.Red),
                        onClick = {
                            onAction(CalculatorActions.AllClear)
                        }
                    )
                    CalButton(
                        content = "(",
                        modifier = Modifier
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Operator.Opening)
                        }
                    )
                    CalButton(
                        content = ")",
                        modifier = Modifier
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Operator.Closing)
                        }
                    )
                    CalButton(
                        content = "/",
                        modifier = Modifier
                            .background(Orange),
                        onClick = {
                            onAction(CalculatorActions.Operator.Divide)
                        }
                    )
                    CalButton(
                        content = "/",
                        modifier = Modifier
                            .background(Orange),
                        onClick = {
                            onAction(CalculatorActions.Operator.Divide)
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CalButton(
                        content = "7",
                        modifier = Modifier
                            .size(70.dp)
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Number(7))
                        }
                    )
                    CalButton(
                        content = "8",
                        modifier = Modifier
                            .size(70.dp)
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Number(8))
                        }
                    )
                    CalButton(
                        content = "9",
                        modifier = Modifier
                            .size(70.dp)
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Number(9))
                        }
                    )
                    CalButton(
                        content = "*",
                        modifier = Modifier
                            .size(70.dp)
                            .background(Orange),
                        onClick = {
                            onAction(CalculatorActions.Operator.Multiply)
                        }
                    )
                    CalButton(
                        content = "/",
                        modifier = Modifier
                            .background(Orange),
                        onClick = {
                            onAction(CalculatorActions.Operator.Divide)
                        }
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CalButton(
                        content = "4",
                        modifier = Modifier
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Number(4))
                        }
                    )
                    CalButton(
                        content = "5",
                        modifier = Modifier
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Number(5))
                        }
                    )
                    CalButton(
                        content = "6",
                        modifier = Modifier
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Number(6))
                        }
                    )
                    CalButton(
                        content = "-",
                        modifier = Modifier
                            .background(Orange),
                        onClick = {
                            onAction(CalculatorActions.Operator.Subtract)
                        }
                    )

                    CalButton(
                        content = "/",
                        modifier = Modifier
                            .background(Orange),
                        onClick = {
                            onAction(CalculatorActions.Operator.Divide)
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CalButton(
                        content = "1",
                        modifier = Modifier
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Number(1))
                        }
                    )
                    CalButton(
                        content = "2",
                        modifier = Modifier
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Number(2))
                        }
                    )
                    CalButton(
                        content = "3",
                        modifier = Modifier
                            .background(MediumGray),
                        onClick = {
                            onAction(CalculatorActions.Number(3))
                        }
                    )
                    CalButton(
                        content = "+",
                        modifier = Modifier
                            .background(Orange),
                        onClick = {
                            onAction(CalculatorActions.Operator.Add)
                        }
                    )

                    CalButton(
                        content = "/",
                        modifier = Modifier
                            .background(Orange),
                        onClick = {
                            onAction(CalculatorActions.Operator.Divide)
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        CalButton(
                            content = "M",
                            modifier = Modifier
                                .background(Color.Magenta),
                            onClick = {
//                            onAction(CalculatorActions.Operation.Add)
                            }
                        )
                        CalButton(
                            content = "0",
                            modifier = Modifier
                                .background(MediumGray)
                            ,
                            onClick = {
                                onAction(CalculatorActions.Number(0))
                            }
                        )
                        CalButton(
                            content = ".",
                            modifier = Modifier
                                .background(MediumGray),
                            onClick = {
                                onAction(CalculatorActions.Decimal)
                            }
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(130.dp)
                            .height(60.dp)
                            .background(Purple40, RoundedCornerShape(20.dp))
                            .clickable { onAction(CalculatorActions.Calculate) }
                    ) {
                        Text(
                            text = "=",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview() {
    Calcy(
        expState = remember { mutableStateOf(TextFieldValue("3+8")) },
        ansState = remember { mutableStateOf("0") },
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray),
        onAction = {}
    )

}