package com.coderz.f1.protodatastoreexperiment

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import com.coderz.f1.protodatastoreexperiment.ui.theme.ProtoDataStoreExperimentTheme
import kotlinx.coroutines.launch


val Context.dataStore by dataStore("settings.json", AppSettingsSerializer)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProtoDataStoreExperimentTheme {
                val appSettings = dataStore.data.collectAsState(
                    initial = AppSettings()
                ).value
                val scope = rememberCoroutineScope()

                Column(modifier=Modifier.fillMaxSize().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    OutlinedTextField(
                        appSettings.userName,
                        onValueChange = {text:String ->

                        scope.launch{
                            saveUsername(text)
                        }
                    },
                        placeholder = {
                            Text("Username", color= Color.LightGray)
                        },
                        label = {
                            Text("Username")
                        },
                        shape = MaterialTheme.shapes.small.copy(bottomStart= ZeroCornerSize,bottomEnd= ZeroCornerSize)
                    )
                    Spacer(modifier=Modifier.height(8.dp))
                    OutlinedTextField(appSettings.userEmail, onValueChange = {text:String->
                        scope.launch{
                            saveEmail(text)
                        }
                    },
                        placeholder = {
                            Text("Email Address", color=Color.LightGray)
                        },
                        label = {
                            Text("Email Address")
                        },
                        shape = MaterialTheme.shapes.small.copy(bottomStart= ZeroCornerSize,bottomEnd= ZeroCornerSize)
                    )
                    Spacer(modifier=Modifier.height(8.dp))
                    Text("Language:", modifier=Modifier.fillMaxWidth(), textAlign = TextAlign.Left)
                    Spacer(modifier=Modifier.height(8.dp))
                    for(i in 0..2){
                        val language = Language.values()[i]
                        Row(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                            RadioButton(
                                selected=language==appSettings.userLanguage,
                                onClick = {
                                    scope.launch {
                                        saveLanguage(language)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(language.toString())
                        }
                    }
                }
            }
        }
    }

    private suspend fun saveUsername(userName:String){
        dataStore.updateData { appSettings->
            appSettings.copy(userName=userName)
        }
    }

    private suspend fun saveEmail(userEmail:String){
        dataStore.updateData { appSettings->
            appSettings.copy(userEmail = userEmail)
        }
    }
    private suspend fun saveLanguage(userLanguage: Language){
        dataStore.updateData { appSettings->
            appSettings.copy(userLanguage = userLanguage)
        }
    }
}

