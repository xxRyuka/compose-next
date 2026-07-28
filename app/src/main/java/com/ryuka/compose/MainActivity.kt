package com.ryuka.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.runtime.MovableContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ryuka.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .background(color = Color.DarkGray)
                        .fillMaxSize()
                ) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .background(Color.DarkGray)
                            .fillMaxSize()
                    ) {

                        TopicStatusRow("Backend", "Pending", modifier = Modifier)
                        SlotCard() { Yazi(status = "SUCUK") }

                        TwoParamSlotCard(modifier = Modifier, "param 0", header = {
                            Yazi(status = it + " : it")
                        }) {
                            TopicStatusRow("param 1 ", "param 2")
                        }

                        NonParamTwoSlotCard(
                            modifier = Modifier.padding(vertical = 15.dp),
                            header = {
                                Yazi(modifier = Modifier)
                            }) { TopicStatusRow("NonParam1 topic ", "NonParam2 status") }
                    }


                }
            }
        }
    }
}


@Composable
fun TopicStatusRow(topic: String, status: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
// Childlar dısardaki modifieri almamalı onu sadece root almalı childler kendi modifierini yazmalı
        Text(
            text = "topic : $topic", color = Color.White, modifier = Modifier.weight(2f)
        )
        Text(
            text = "status : $status",
            color = Color.White,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}


@Composable
fun SlotCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    // kendi iç paddingi dediğin bu sekilde mi ?
    Column(
        modifier = modifier
            .border(width = 1.5.dp, Color.Green)
            .padding(15.dp)
    ) {
//        TopicStatusRow(topic = "DevOps", status = "err", modifier = Modifier)
        content()
//
    }
}

@Composable
fun Yazi(modifier: Modifier = Modifier, status: String = "") {


    val displayedStatus = if (status.isBlank()) {
        "BOS"
    } else {
        "status : $status"

    }

    Text(text = displayedStatus, modifier = modifier, color = Color.Red)
}


@Composable
fun TwoParamSlotCard(
    modifier: Modifier = Modifier,
    status: String,
    header: @Composable (status: String) -> Unit,
    content: @Composable () -> Unit
) {
    // kendi iç paddingi dediğin bu sekilde mi ?
    Column(
        modifier = modifier
            .border(width = 1.5.dp, Color.Green)
            .padding(15.dp)
    ) {
        header(status)
        content()
//
    }
}


@Composable
fun NonParamTwoSlotCard(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .border(width = 1.7.dp, Color.Magenta)
            .padding(15.dp)
    ) {
        header()
        content()

    }

}