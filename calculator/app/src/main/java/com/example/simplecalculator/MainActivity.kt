package com.example.simplecalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var num1 by remember{
                mutableStateOf("23")
            }
            var num2 by remember{
                mutableStateOf("17")
            }
            var result by remember {
                mutableStateOf("")
            }
            Column {
                Spacer(modifier = Modifier.height(100.dp))
                TextField(value = num1, onValueChange = {
                    num1 = it
                })
                TextField(value = num2, onValueChange = {
                    num2 = it
                })
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = {
                        result = (num1.toInt() + num2.toInt()).toString()
                        Toast.makeText(applicationContext,"Result is $result",Toast.LENGTH_SHORT).show()
                    }) {
                        Text(text = "add")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {
                        result =  (num1.toInt() - num2.toInt()).toString()
                        Toast.makeText(applicationContext,"Result is $result",Toast.LENGTH_SHORT).show()
                    }) {
                        Text(text = "Substract")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {
                        result =  (num1.toInt() * num2.toInt()).toString()
                        Toast.makeText(applicationContext,"Result is $result",Toast.LENGTH_SHORT).show()
                    }) {
                        Text(text = "Mult")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {
                        result =  (num1.toInt() / num2.toInt()).toString()
                        Toast.makeText(applicationContext,"Result is $result",Toast.LENGTH_SHORT).show()
                    }) {
                        Text(text = "Div")
                    }
                }

            }


        }
    }
}

