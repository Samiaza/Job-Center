package com.example.app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.di.components.DaggerAppComponent
import com.example.app.model.Resume
import com.example.app.network.api.VacancyService
import com.example.app.ui.activities.ui.theme.VacancyCenterclientTheme
import com.example.app.util.readJsonFromResource
import com.example.vacancycenterclient.R
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ResumeActivity : ComponentActivity() {
    private val tag = this::class.simpleName

    @Inject
    lateinit var vacancyService: VacancyService

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerAppComponent.create().inject(this)

        val resumeDefault = Json.decodeFromString<Resume>(
            readJsonFromResource(this, R.raw.resume)
        )

        setContent {
            val activityLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = {})
            val context = LocalContext.current


            val scaffoldState = rememberScaffoldState()
            val editable by remember { mutableStateOf(false) }
            val needRefresh by remember { mutableStateOf(true) }

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = { Text("Resume") },
                        navigationIcon = {
                            IconButton(onClick = {
                                activityLauncher.launch(Intent(context, MainActivity::class.java))
                            }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        actions = {
                            IconButton(onClick = {

                            }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Back")
                            }
                        }
                    )
                }

            ) { innerPadding ->
                com.example.app.ui.theme.VacancyCenterclientTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ResumeContent(vacancyService, editable, needRefresh)
                    }
                }
            }
        }
    }
}

@Composable
fun ResumeContent(vacancyService: VacancyService, editable: Boolean, needRefresh: Boolean) {
    var resume by remember { mutableStateOf<String>("") }
    var tags by remember { mutableStateOf<String>("") }

    if (needRefresh) vacancyService.getResume(666).enqueue(object: Callback<Resume> {
        override fun onResponse(
            call: Call<Resume>,
            response: Response<Resume>
        ) {
            resume = response.body().toString()
        }
        override fun onFailure(call: Call<Resume>, t: Throwable) {
            Log.e("${this::class.simpleName}", t.message.toString())
        }
    })

    if (needRefresh) vacancyService.getTags(666).enqueue(object: Callback<List<String>> {
        override fun onResponse(
            call: Call<List<String>>,
            response: Response<List<String>>
        ) {
            tags = response.body().toString()
        }
        override fun onFailure(call: Call<List<String>>, t: Throwable) {
            Log.e("${this::class.simpleName}", t.message.toString())
        }
    })
    Column {
        TextField(value = resume,
            onValueChange = {},
            readOnly = editable.not(),
            label = { Text(text = "Resume.Body") }
        )
        Text("Tags: $tags")
    }

}
