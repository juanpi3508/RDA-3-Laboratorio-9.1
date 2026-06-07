package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.InMemoryTaskRepository
import com.example.myapplication.domain.usecase.AddTaskUseCase
import com.example.myapplication.domain.usecase.GetTasksUseCase
import com.example.myapplication.ui.presentation.tasks.AcademicTaskApp
import com.example.myapplication.ui.presentation.tasks.AcademicTaskViewModel

class MainActivity : ComponentActivity() {

    // Delegamos el ciclo de vida del ViewModel al sistema de Android
    private val viewModel: AcademicTaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            // El repositorio sobrevive a rotaciones compartiendo el ciclo de vida del ViewModel
            private val repository = InMemoryTaskRepository()

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val getTasksUseCase = GetTasksUseCase(repository)
                val addTaskUseCase = AddTaskUseCase(repository)
                return AcademicTaskViewModel(getTasksUseCase, addTaskUseCase, repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 3. Renderizacion de la vista declarativa limpia
        setContent {
            AcademicTaskApp(viewModel = viewModel)
        }
    }
}