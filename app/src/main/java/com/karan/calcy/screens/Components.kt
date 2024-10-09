package com.karan.calcy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karan.calcy.model.CalculatorActions
import com.karan.calcy.model.CalculatorState
import com.karan.calcy.ui.theme.LightGray
import com.karan.calcy.ui.theme.MediumGray
import com.karan.calcy.ui.theme.Orange
import com.karan.calcy.ui.theme.Purple40
import java.awt.font.NumericShaper

@Composable
fun CalButton(
    content : String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .then(modifier)
    ) {
        Text(
            text = content,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
fun Calcy(
    state : CalculatorState,
    buttonSpacing : Dp = 8.dp,
    modifier: Modifier = Modifier,
    onAction : (CalculatorActions) -> Unit
) {
    Box(
        modifier = Modifier
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 2.dp),
            ) {
                Text(
                    text = state.num1 + (state.op?.symbol ?: "") + state.num2,
                    modifier = Modifier
                        .height(100.dp),
                    fontWeight = FontWeight.Light,
                    fontSize = 50.sp,
                    color = Color.White,
                )
            }
            Text(
                text = state.num1 ,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                fontWeight = FontWeight.Light,
                fontSize = 70.sp,
                color = Color.White,
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Orange)
            )

            Column(
                modifier = Modifier
                    .padding(vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalButton(
                        content = "AC",
                        modifier = Modifier
                            .background(LightGray)
                            .aspectRatio(2f)
                            .weight(2f),
                        onClick = {
                            onAction(CalculatorActions.AllClear)
                        }
                    )
                    CalButton(
                        content = "Del",
                        modifier = Modifier
                            .background(LightGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Clear)
                        }
                    )
                    CalButton(
                        content = "/",
                        modifier = Modifier
                            .background(Orange)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Operator.Divide)
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalButton(
                        content = "7",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(7))
                        }
                    )
                    CalButton(
                        content = "8",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(8))
                        }
                    )
                    CalButton(
                        content = "9",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(9))
                        }
                    )
                    CalButton(
                        content = "x",
                        modifier = Modifier
                            .background(Orange)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Operator.Multiply)
                        }
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalButton(
                        content = "4",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(4))
                        }
                    )
                    CalButton(
                        content = "5",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(5))
                        }
                    )
                    CalButton(
                        content = "6",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(6))
                        }
                    )
                    CalButton(
                        content = "-",
                        modifier = Modifier
                            .background(Orange)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Operator.Subtract)
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalButton(
                        content = "1",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(1))
                        }
                    )
                    CalButton(
                        content = "2",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(2))
                        }
                    )
                    CalButton(
                        content = "3",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(3))
                        }
                    )
                    CalButton(
                        content = "+",
                        modifier = Modifier
                            .background(Orange)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Operator.Add)
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {

                    CalButton(
                        content = "M",
                        modifier = Modifier
                            .background(Color.Magenta)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
//                            onAction(CalculatorActions.Operation.Add)
                        }
                    )
                    CalButton(
                        content = "0",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Number(0))
                        }
                    )
                    CalButton(
                        content = ".",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorActions.Decimal)
                        }
                    )
                    CalButton(
                        content = "=",
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Purple40)
                            .aspectRatio(1f)
                            .weight(1f)
                        ,
                        onClick = {
                            onAction(CalculatorActions.Calculate)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview() {
    Calcy (
        state = CalculatorState(),
        buttonSpacing = 8.dp,
        modifier = Modifier
            .fillMaxSize()
            .background(MediumGray)
            .padding(16.dp),
        onAction = {}
    )
    
}