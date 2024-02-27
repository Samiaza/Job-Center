package com.example.app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.di.components.DaggerAppComponent
import com.example.app.ui.theme.VacancyCenterclientTheme
import com.example.app.network.api.VacancyService
import retrofit2.Call
import javax.inject.Inject
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val tag = this::class.simpleName

    @Inject
    lateinit var vacancyService: VacancyService

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerAppComponent.create().inject(this)

        setContent {
            val activityLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = {})
            val context = LocalContext.current

            val scaffoldState = rememberScaffoldState()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = { Text("Vacancy Center") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        scaffoldState.drawerState.open()
                                    } else {
                                        scaffoldState.drawerState.close()
                                    }
                                }
                            }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },
                drawerContent = {
                    Column {
                        Text(
                            "Resume",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .border(2.dp, Color.Blue, RoundedCornerShape(5.dp))
                                .padding(5.dp)
                                .clickable {
                                    activityLauncher.launch(Intent(context, ResumeActivity::class.java)) },
                            fontSize = 32.sp,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center
                            )
                    }
                }

            ) { innerPadding ->
                VacancyCenterclientTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreenContent(vacancyService, activityLauncher)
                    }
                }
            }
        }
    }
}
@Composable
fun MainScreenContent(vacancyService: VacancyService,
                      activityLauncher: ActivityResultLauncher<Intent>) {
    val tabs = listOf("Companies", "Vacancies")
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    Column {
        TabRow(selectedTabIndex = selectedTabIndex.value) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = { selectedTabIndex.value = index },
                    text = { Text(title) })
            }
        }
        when(selectedTabIndex.value) {
            0 -> CompaniesContent(vacancyService, activityLauncher)
            1 -> VacanciesContent(vacancyService, activityLauncher)
        }
    }
}

@Composable
fun CompaniesContent(vacancyService: VacancyService,
                     activityLauncher: ActivityResultLauncher<Intent>) {
    var companies by remember { mutableStateOf<Map<String, String>?>(null) }
    val context = LocalContext.current

    vacancyService.getCompanies().enqueue(object: Callback<Map<String, String>>{
        override fun onResponse(
            call: Call<Map<String, String>>,
            response: Response<Map<String, String>>
        ) {
            companies = response.body()
        }
        override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
            Log.e("${this::class.simpleName}", t.message.toString())
        }
    })

    LazyColumn(
        Modifier.padding(10.dp)
    ) {
        companies?.entries?.let {
            items(it.toList()){ entry ->
                val info = extractCompanyNameAndId(entry.key)
                Row(
                    Modifier
                        .padding(5.dp)
                        .fillParentMaxWidth()
                        .border(2.dp, Color.Blue, RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .clickable {
                            activityLauncher.launch(Intent(
                                context,
                                CompanyDetailsActivity::class.java
                            ).apply {
                                putExtra("companyId", info?.second)
                            })
                        }
                ) {
                    Text(info?.first.toString(),
                        Modifier
                            .padding(5.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                            .padding(5.dp)
                            .weight(1f))
                    Text(entry.value,
                        Modifier
                            .padding(5.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                            .padding(5.dp))
                }
            }
        }
    }
}

@Composable
fun VacanciesContent(vacancyService: VacancyService,
                     activityLauncher: ActivityResultLauncher<Intent>) {
    var vacancies by remember { mutableStateOf<Map<String, List<String>>?>(null) }
    val context = LocalContext.current

    vacancyService.getVacancies().enqueue(object: Callback<Map<String, List<String>>>{
        override fun onResponse(
            call: Call<Map<String, List<String>>>,
            response: Response<Map<String, List<String>>>
        ) {
            vacancies = response.body()
        }
        override fun onFailure(call: Call<Map<String, List<String>>>, t: Throwable) {
            Log.e("${this::class.simpleName}", t.message.toString())
        }
    })

    LazyColumn(
        Modifier.padding(10.dp)
    ) {
        vacancies?.let {
            items(it.flatMap { (key, values) -> values.map { value -> Pair(key, value) }}) {
                entry ->
                val info = extractCompanyNameAndId(entry.first)
                Row(
                    Modifier
                        .padding(5.dp)
                        .fillParentMaxWidth()
                        .border(2.dp, Color.Blue, RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .clickable {
                            activityLauncher.launch(Intent(
                                context,
                                VacancyDetailsActivity::class.java
                            ).apply {
                                putExtra("companyId", info?.second)
                                putExtra("companyName", info?.first)
                                putExtra("vacancyName", entry.second)
                            })
                        }
                ) {
                    Text(info?.first.toString(),
                        Modifier
                            .padding(5.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                            .padding(5.dp))
                    Text(entry.second,
                        Modifier
                            .padding(5.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                            .padding(5.dp)
                            .weight(1f),
                        textAlign = TextAlign.Center)
                }
            }
        }
    }
}

fun extractCompanyNameAndId(input: String?): Pair<String, Int?>? {
    val pattern = Regex("""(.+?) \(id = (\d+)\)""")
    val matchResult = input?.let {pattern.find(it)}

    return matchResult?.let {
        val name = it.groupValues[1]
        val id = it.groupValues[2].toIntOrNull()
        Pair(name, id)
    }
}