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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.di.components.DaggerAppComponent
import com.example.app.model.Company
import com.example.app.network.api.VacancyService
import com.example.app.ui.activities.ui.theme.VacancyCenterclientTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CompanyDetailsActivity : ComponentActivity() {
    private val tag = this::class.simpleName

    @Inject
    lateinit var vacancyService: VacancyService

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerAppComponent.create().inject(this)

        val companyId = intent.extras?.getInt("companyId")

        setContent {
            VacancyCenterclientTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    companyId?.let {
                        CompanyDetails(vacancyService, companyId)
                    } ?: Text("Error: incorrect company data")
                }
            }
        }
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        return super.navigateUpTo(upIntent)
    }
}

@Composable
fun CompanyDetails(vacancyService: VacancyService, companyId: Int) {
    var company by remember { mutableStateOf<Company?>(null) }

    vacancyService.getCompany(companyId).enqueue(object: Callback<Company> {
        override fun onResponse(
            call: Call<Company>,
            response: Response<Company>
        ) {
            company = response.body()
        }
        override fun onFailure(call: Call<Company>, t: Throwable) {
            Log.e("${this::class.simpleName}", t.message.toString())
        }
    })

    company?.let { CompanyDetails(company!!) } ?: "Loading..."
}

@Composable
fun CompanyDetails(company: Company) {
    val context = LocalContext.current

    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {})

    Column(modifier = Modifier
        .wrapContentHeight()
        .padding(16.dp)
        .border(2.dp, Color.Blue, RoundedCornerShape(5.dp))
        .padding(15.dp)) {
        Text(text = "Name: ${company.name}")
        company.activity?.let {
            Text(text = "Activity: ${it.value}")
        }
        company.vacancies?.let { vacancies ->
            Text(text = "Vacancies:")
            vacancies.forEach { vacancy ->
                Text(text = "- ${vacancy.profession.value}",
                    Modifier
                        .padding(5.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .clickable {
                            activityLauncher.launch(Intent(
                                context,
                                VacancyDetailsActivity::class.java
                            ).apply {
                                putExtra("companyId", company.id)
                                putExtra("companyName", company.name)
                                putExtra("vacancyName", vacancy.profession.value)
                            })
                        })
            }
        }
        company.contacts?.let {
            Text(text = "Contacts: $it")
        }
    }
}
