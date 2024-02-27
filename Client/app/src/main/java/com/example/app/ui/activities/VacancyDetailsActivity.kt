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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.di.components.DaggerAppComponent
import com.example.app.model.Company
import com.example.app.model.Vacancy
import com.example.app.network.api.VacancyService
import com.example.app.ui.activities.ui.theme.VacancyCenterclientTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class VacancyDetailsActivity : ComponentActivity() {
    private val tag = this::class.simpleName

    @Inject
    lateinit var vacancyService: VacancyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerAppComponent.create().inject(this)

        val companyId = intent.extras?.getInt("companyId") ?: 0
        val companyName = intent.extras?.getString("companyName")
        val vacancyName = intent.extras?.getString("vacancyName")

        setContent {
            VacancyCenterclientTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (companyName != null && vacancyName != null) {
                        VacancyDetails(vacancyService, companyId, companyName, vacancyName)
                    } else {
                        Text("Error: incorrect company data")
                    }
                }
            }
        }
    }


}

@Composable
fun VacancyDetails(vacancyService: VacancyService, companyId: Int, companyName: String, vacancyName: String) {
    var vacancy by remember { mutableStateOf<Vacancy?>(null) }

    vacancyService.getVacancy(companyName, vacancyName).enqueue(object: Callback<Vacancy> {
        override fun onResponse(
            call: Call<Vacancy>,
            response: Response<Vacancy>
        ) {
            vacancy = response.body()
        }
        override fun onFailure(call: Call<Vacancy>, t: Throwable) {
            Log.e("${this::class.simpleName}", t.message.toString())
        }
    })

    vacancy?.let { VacancyDetails(vacancy!!,companyId, companyName) } ?: "Loading..."
}


@Composable
fun VacancyDetails(vacancy: Vacancy, companyId: Int, companyName: String) {
    val context = LocalContext.current

    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {})

    Column(modifier = Modifier
                        .wrapContentHeight()
                        .padding(16.dp)
                        .border(2.dp, Color.Blue, RoundedCornerShape(5.dp))
                        .padding(15.dp)) {
        Text(text = "Vacancy: ${vacancy.profession.value}")
        Text(text = "Level: ${vacancy.level.value}")
        Text(text = "Salary: ${vacancy.salary}")
        Text(text = "Company: $companyName",
            Modifier
                .padding(5.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                .padding(5.dp)
                .clickable {
                    activityLauncher.launch(
                        Intent(
                        context,
                        CompanyDetailsActivity::class.java
                    ).apply {
                        putExtra("companyId", companyId)
                    })
                })
    }
}