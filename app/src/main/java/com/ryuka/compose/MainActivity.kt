package com.ryuka.compose

import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.text.style.TextAlign
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


                        TaskList(modifier = Modifier.padding(innerPadding))

                        HorizontalDivider()

                        NameForm(Modifier.padding(innerPadding))


                        CounterScreen(
                            modifier = Modifier.padding(innerPadding)
                        )

                        TiklaState()

                        ActionCard(
                            onClick = { bosAdamFonksiyonu() },
                            modifier = Modifier.fillMaxWidth(),
                            {
                                Text(
                                    text = "content burası artık nere gelcekse ",
                                    modifier = Modifier
                                )
                                Text(
                                    text = "neyse devam ", modifier = Modifier
                                )
                            })

                        Row(modifier = Modifier) {
                            ButonHasParam(
                                modifier = Modifier.weight(1f),
                                text = "deneme",
                                onClickParametreli = {
                                    println(it)
                                })

                            ActionButton(
                                text = "aksiyon", modifier = Modifier.weight(1f), onClick = {
                                    bosAdamFonksiyonu()
                                    println("1 tane fonksiyon yazdim icine 2 tane yazabiliyom ? ")
                                    bosAdamFonksiyonu()
                                })
                            Tikla(modifier = Modifier.weight(2f))
                        }

                        TopicStatusRow(
                            "Backend",
                            "Pending",
                            onClick = { println("backend satira bastin ") },
                            modifier = Modifier
                        )
                        SlotCard() { Yazi(status = "SUCUK") }

                        TwoParamSlotCard(modifier = Modifier, "param 0", header = {
                            Yazi(status = it + " : it")
                        }) {
                            TopicStatusRow(
                                "param 1 ",
                                "param 2",
                                onClick = { println("param satira bastin ") },
                                modifier = Modifier
                            )
                        }

                        NonParamTwoSlotCard(
                            modifier = Modifier.padding(vertical = 15.dp), header = {
                                Yazi(modifier = Modifier)
                            }) {
                            TopicStatusRow(
                                "NonParam1 topic ",
                                "NonParam2 status",
                                onClick = { println("NonParam1 satira bastin ") },
                                modifier = Modifier

                            )
                        }
                    }


                }
            }
        }
    }
}


@Composable
fun TaskList(modifier: Modifier = Modifier) {
    val taskList = listOf<Task>(
        Task(1, "iot teorik arastir"),
        Task(2, "esp32 ile pratik yap"),
        Task(3, "mqtt protokol "),
        Task(4, "sensör verilerini anlamlandır ! "),
    )

    LazyColumn(modifier.height(550.dp)) {
        items(
            items = taskList,
            key = { x -> x.taskId }
        ) { tas ->

            TaskRow(
                modifier = Modifier.padding(1.5.dp),
                onDelete = { println("will delete $tas") },
                task = tas
            )
        }
    }
}


@Composable
fun TaskRow(modifier: Modifier = Modifier, task: Task, onDelete: () -> Unit) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.background(Color.Black).fillMaxWidth().padding(12.dp) , horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier,
                text = "${task.taskName} -> ${task.isActive}",
                color = Color.White
            )

            Button(modifier= Modifier,onClick = onDelete) {
                Text(modifier= Modifier,text = "Sil")
            }
        }
    }
}

@Composable
fun NameForm(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }


//    val showError =k
    Column(modifier) {

        val isNameValid = name.length >= 3
        val showError = name.isNotEmpty() && !isNameValid

        var savedText: String = ""

        if (!showError) {

            Text(modifier = Modifier, text = "Yeni Ad : $name", color = Color.White)
        }

        Text(modifier = Modifier, text = "Yeni Ad : $name", color = Color.White)



        NameInput(name, Modifier, onNameChange = { ad -> name = ad }, showError)

        SaveButton(Modifier, onClack = {
            savedText = name
        }, isActive = !showError)

        ClearButton(Modifier, onClick = { name = "" })
    }

}


@Composable
fun SaveButton(modifier: Modifier = Modifier, onClack: () -> Unit, isActive: Boolean = false) {
    Button(
        modifier = modifier,
        enabled = isActive,
        onClick = { onClack() }
    ) {
        Text(text = "enable")
    }
}

@Composable
fun NameInput(
    name: String, modifier: Modifier = Modifier, onNameChange: (String) -> Unit, isError: Boolean
) {


    OutlinedTextField(
        value = name,
        onValueChange = { newText: String -> onNameChange(newText) },
        modifier = modifier,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = "HATA KAKNA ")
            }
        }

    )
}


@Composable
fun ClearButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = { onClick() }) {
        Text("temizle", Modifier)
    }

}


@Composable
fun CounterScreen(modifier: Modifier = Modifier) {
    var counter by remember { mutableStateOf(0) }

    Column(modifier) {

        Text(
            text = "sayac:  $counter",
            modifier = Modifier
                .border(1.6.dp, Color.Yellow)
                .padding(4.dp),
            color = Color.White
        )

        Row(Modifier.padding(horizontal = 3.dp)) {

            ResetButton() {
                counter = 0
            }
            ArttirButton() {
                counter++
            }
            AzaltButton(onClicks = {
                counter--
            })
        }
        HorizontalDivider(color = Color.White, thickness = 5.dp)
    }
}


@Composable
fun ResetButton(
    modifier: Modifier = Modifier, onClicks: () -> Unit
) { // buna parametre verince patlıyor ?

    Button(modifier = modifier, onClick = { onClicks() }) {
        Text(text = "reset", modifier = Modifier)
    }
}

@Composable
fun ArttirButton(
    modifier: Modifier = Modifier, onClicks: () -> Unit
) { // buna parametre verince patlıyor ?

    Button(modifier = modifier, onClick = { onClicks() }) {
        Text(text = "arttir", modifier = Modifier)
    }
}


@Composable
fun AzaltButton(
    modifier: Modifier = Modifier, onClicks: () -> Unit
) { // buna parametre verince patlıyor ?

    Button(modifier = modifier, onClick = { onClicks() }) {
        Text(text = "azalt", modifier = Modifier)
    }
}

@Composable
fun ActionCard(
    onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable () -> Unit
) {

    // ROOT
    Button(
        onClick = onClick,
        modifier = modifier.height(150.dp),
    ) {
        Column() {


            Text(text = "Action Card Text ", modifier = Modifier)
            HorizontalDivider()
            Row() {

                content()
            }
        }
    }

}


fun bosAdamFonksiyonu() {
    println("bombos")
}

var counter = 1

@Composable
fun Tikla(modifier: Modifier = Modifier) {
    Button(modifier = modifier, onClick = {
        println("BASTİ BANA $counter")
        counter++
    }) {
        Text(text = "slm $counter")
    }
}

@Composable
fun TiklaState(modifier: Modifier = Modifier) {

    var counter by remember { mutableStateOf(0) }
    Button(modifier = modifier, onClick = {
        counter++
    }) {
        Text(text = "slm ${counter}")

    }
}

@Composable
fun ActionButton(
    text: String, onClick: () -> Unit, // callback
    modifier: Modifier = Modifier
) {
    // root button
    Button(modifier = modifier, onClick = onClick) {
        Text(
            text = "herkes bana basiyo $text",
            modifier = Modifier
                .background(Color.Red)
                .padding(5.dp)
        )
    }

}


@Composable
fun ButonHasParam(
    text: String, onClickParametreli: (String) -> Unit, // callback
    modifier: Modifier = Modifier
) {
    // root button
    Button(modifier = modifier, onClick = { onClickParametreli(text) }) {
        Text(
            text = "herkes bana basiyo $text",
            modifier = Modifier
                .background(Color.Red)
                .padding(5.dp)
        )
//        onClickParametreli("cabbar")  // burda cagırmanın manası yok ! cünkü burdayken component compose edilirken calısır sadece
    }

}


@Composable
fun TopicStatusRow(
    topic: String, status: String, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .border(1.5.dp, Color.Gray)
            .padding(12.dp)
    ) {
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
    modifier: Modifier = Modifier, header: @Composable () -> Unit, content: @Composable () -> Unit
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