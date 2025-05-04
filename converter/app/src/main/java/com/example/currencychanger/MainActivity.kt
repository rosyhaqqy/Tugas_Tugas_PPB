package com.example.currencychanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencychanger.ui.theme.CurrencyChangerTheme
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyChangerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyChangerApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyChangerApp() {
    val currencies = listOf("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "IDR")

    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf(currencies[0]) }
    var toCurrency by remember { mutableStateOf(currencies[4]) }
    var result by remember { mutableStateOf("0.00") }

    var fromDropdownExpanded by remember { mutableStateOf(false) }
    var toDropdownExpanded by remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.height(100.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Currency Converter",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
        )

        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
                if (amount.isNotEmpty()) {
                    result = convertCurrency(amount.toDoubleOrNull() ?: 0.0, fromCurrency, toCurrency)
                } else {
                    result = "0.00"
                }
            },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // From Currency Dropdown
            ExposedDropdownMenuBox(
                expanded = fromDropdownExpanded,
                onExpandedChange = { fromDropdownExpanded = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = fromCurrency,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { Icon(Icons.Filled.KeyboardArrowDown, "dropdown") },
                    label = { Text("From") },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = fromDropdownExpanded,
                    onDismissRequest = { fromDropdownExpanded = false }
                ) {
                    currencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                fromCurrency = currency
                                fromDropdownExpanded = false
                                if (amount.isNotEmpty()) {
                                    result = convertCurrency(amount.toDoubleOrNull() ?: 0.0, fromCurrency, toCurrency)
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // To Currency Dropdown
            ExposedDropdownMenuBox(
                expanded = toDropdownExpanded,
                onExpandedChange = { toDropdownExpanded = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = toCurrency,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { Icon(Icons.Filled.KeyboardArrowDown, "dropdown") },
                    label = { Text("To") },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = toDropdownExpanded,
                    onDismissRequest = { toDropdownExpanded = false }
                ) {
                    currencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                toCurrency = currency
                                toDropdownExpanded = false
                                if (amount.isNotEmpty()) {
                                    result = convertCurrency(amount.toDoubleOrNull() ?: 0.0, fromCurrency, toCurrency)
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (amount.isNotEmpty()) {
                    result = convertCurrency(amount.toDoubleOrNull() ?: 0.0, fromCurrency, toCurrency)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Result",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$result $toCurrency",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                if (amount.isNotEmpty()) {
                    Text(
                        text = "$amount $fromCurrency = $result $toCurrency",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

fun convertCurrency(amount: Double, from: String, to: String): String {
    // Exchange rates relative to USD (as of October 2024)
    val rates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.92,
        "JPY" to 152.5,
        "GBP" to 0.78,
        "AUD" to 1.56,
        "CAD" to 1.38,
        "CHF" to 0.90,
        "CNY" to 7.23,
        "IDR" to 16100.0
    )

    // Convert from source currency to USD
    val usdAmount = if (from == "USD") amount else amount / rates[from]!!

    // Convert from USD to target currency
    val targetAmount = if (to == "USD") usdAmount else usdAmount * rates[to]!!

    // Round to 2 decimal places (except for currencies like JPY or IDR)
    val roundedAmount = when (to) {
        "JPY", "IDR" -> round(targetAmount)
        else -> round(targetAmount * 100) / 100
    }

    return when (to) {
        "JPY", "IDR" -> roundedAmount.toInt().toString()
        else -> String.format("%.2f", roundedAmount)
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyChangerPreview() {
    CurrencyChangerTheme {
        CurrencyChangerApp()
    }
}