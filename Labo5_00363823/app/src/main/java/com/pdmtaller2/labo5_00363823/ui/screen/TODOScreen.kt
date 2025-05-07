package com.pdmtaller2.labo5_00363823.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import GeneralViewModel
import Task
import java.util.Date

@Composable
fun TODOScreen(
    viewModel: GeneralViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var titulo by remember { mutableStateOf(TextFieldValue()) }
    var descripcion by remember { mutableStateOf(TextFieldValue()) }
    var completada by remember { mutableStateOf(false) }

    val tareas by viewModel.tasks.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
    ) {
        // Sección para crear nueva tarea
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Crear Nueva Tarea",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = titulo.text,
                    onValueChange = { titulo = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Título de la tarea") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = descripcion.text,
                    onValueChange = { descripcion = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Descripción") },
                    maxLines = 3
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = completada,
                        onCheckedChange = { completada = it }
                    )
                    Text("¿Completada?", style = MaterialTheme.typography.bodyMedium)
                }

                Button(
                    onClick = {
                        if (titulo.text.isNotBlank() && descripcion.text.isNotBlank()) {
                            val nuevaTarea = Task(
                                id = tareas.size + 1,
                                title = titulo.text,
                                description = descripcion.text,
                                endDate = Date(),
                                isCompleted = completada
                            )
                            viewModel.addTask(nuevaTarea)
                            titulo = TextFieldValue()
                            descripcion = TextFieldValue()
                            completada = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Agregar Tarea")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Tareas Existentes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (tareas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay tareas registradas", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                tareas.forEach { tarea ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                tarea.title,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                tarea.description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Fecha: ${tarea.endDate}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    if (tarea.isCompleted) "✅ Completada" else "❌ Pendiente",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TODOScreenPreview() {
    TODOScreen()
}