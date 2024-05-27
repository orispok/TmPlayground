package com.osp.tmplayground.service

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osp.tmplayground.data.Profile
import com.osp.tmplayground.data.setAge
import com.osp.tmplayground.data.setName
import java.util.Calendar


@Composable
fun RegisterPage(
    modifier: Modifier = Modifier,
    profile: Profile,
    registerStep: String,
    onScreenChange: (String, String?) -> Unit // (screenName, registerStep?)
) {
    Surface(
        modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hi ${profile.name}! \n you almost there! \n few more steps to create your account :)\n next step: $registerStep")
            ElevatedButton(onClick = { onScreenChange("ProfileInputScreen", registerStep) }) {
                Text(text = "Continue")
            }
        }
    }
}

@Composable
fun ProfileInputScreen(
    modifier: Modifier = Modifier,
    profile: Profile,
    registerStep: String?,
    onProfileChange: (Profile) -> Unit,
    onScreenChange: (String, String) -> Unit
) {
    Surface(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (registerStep) {
                "name" -> GetInput(
                    label = "name",
                    value = profile.name,
                    onValueChange = { input ->
                        onProfileChange(profile.copy(name = input))
                    }
                )

                "age" -> GetAgeInput(
                    onValueChange = { input -> onProfileChange(profile.copy(age = input as Int))
                        onScreenChange("ProfileInputScreen", "imageUrl")
                    }
                )

                "imageUrl" -> GetInput(label = "URL", value = profile.imageUrl) {

                }
            }

            Button(onClick = {
                val nextStep = when (registerStep) {
                    "name" -> "age"
                    "age" -> "imageUrl"
                    "imageUrl" -> "description"
                    "description" -> "height"
                    "height" -> "gender"
                    "gender" -> "preferencesMatch"
                    else -> "name"
                }
                onScreenChange("ProfileInputScreen", nextStep)
            }) {
                Text(text = "next")
            }
        }
    }


}

@Composable
fun GetInput(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {

    TextField(
        label = { Text(label) },
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth()
    )


}


@Composable
fun GetAgeInput(onValueChange: (Any) -> Unit) {
    val context = LocalContext.current
    val selectedDate = remember { mutableStateOf("") }
    val calculatedAge = remember { mutableIntStateOf(0) }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog =
        DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            val dob = Calendar.getInstance()
            dob.set(selectedYear, selectedMonth, selectedDay)
            selectedDate.value = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"

            calculatedAge.intValue = calculateAge(dob.timeInMillis)
            onValueChange(calculatedAge.intValue)
        }, year, month, day)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        datePickerDialog.show()
    }
}

fun calculateAge(dateInMillis: Long): Int {
    val dob = Calendar.getInstance()
    dob.timeInMillis = dateInMillis
    val today = Calendar.getInstance()

    var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age -= 1
    }
    return age
}


@Preview(widthDp = 220, heightDp = 420)
@Composable
fun GetAgeInputPreview() {
//    GetAgeInput()
}


@Preview(widthDp = 220, heightDp = 420)
@Composable
fun RegisterPagePreview() {
    val profile = Profile(
        uid = "onlyName",
        name = "Renana Rimon"
    )
//    RegisterPage(profile = profile, registerStep = "age", onScreenChange = {"GetNameInput", "name" -> })
}