package com.osp.tmplayground.service

import android.app.DatePickerDialog
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osp.tmplayground.data.Gender
import com.osp.tmplayground.data.PreferencesMatch
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
                    onValueChange = { input ->
                        onProfileChange(profile.copy(age = input as Int))
                        onScreenChange("ProfileInputScreen", "imageUrl")
                    }
                )

                "imageUrl" -> GetInput(
                    label = "URL",
                    value = profile.imageUrl,
                    onValueChange = { input ->
                        onProfileChange(profile.copy(imageUrl = input))
                    })

                "description" -> GetInput(
                    label = "Tell us about yourself :)",
                    value = profile.description,
                    onValueChange = { input -> onProfileChange(profile.copy(description = input)) }
                )

                "height" -> GetSliderInput(
                    label = "Pick your height",
                    onValueChange = { input ->
                        onProfileChange(profile.copy(height = input))
                    },
                    measurementUnit = "cm",
                    minVal = 80f,
                    maxVal = 250f
                )

                "gender" -> GetGenderInput(
//                    label = "gender",
                    value = profile.gender,
                    onValueChange = { input ->
                        onProfileChange(profile.copy(gender = input))
                    }
                )

                "preferencesMatch" -> {
                    val preferencesMatch = remember {
                        mutableStateOf<PreferencesMatch>(profile.preferencesMatch)
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            5.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "preferences match\nWho you want to date?")
                        GetSliderInput(
                            label = "max distance",
                            onValueChange = { input -> preferencesMatch.value.maxDistance = input
                                            onProfileChange(profile.copy(preferencesMatch = preferencesMatch.value))},
                            measurementUnit = "km",
                            minVal = 0f,
                            maxVal = 1000f
                        )

                        GetSliderInput(
                            label = "min age",
                            onValueChange = { input -> preferencesMatch.value.ageMin = input
                                onProfileChange(profile.copy(preferencesMatch = preferencesMatch.value))},
                            measurementUnit = "years",
                            minVal = 18f,
                            maxVal = 70f
                        )

                        GetSliderInput(
                            label = "max age",
                            onValueChange = { input -> preferencesMatch.value.ageMax = input
                                onProfileChange(profile.copy(preferencesMatch = preferencesMatch.value))},
                            measurementUnit = "years",
                            minVal = 18f,
                            maxVal = 70f
                        )
                    }

                    GetGenderInput(
//                        label = "I want to date:",
                        value = profile.preferencesMatch.dateGender,
                        onValueChange = { input -> preferencesMatch.value.dateGender = input
                            onProfileChange(profile.copy(preferencesMatch = preferencesMatch.value))}

                    )

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
                val screen = when(registerStep){
                    "preferencesMatch" -> "Profile"
                    else -> "ProfileInputScreen"
                }

                onScreenChange(screen, nextStep)
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

@Composable
fun GetSliderInput(
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (Int) -> Unit,
    measurementUnit: String,
    minVal: Float,
    maxVal: Float
) {
    val sliderPosition = remember { mutableFloatStateOf(0.0F) }
    Text(text = label)
    Text(text = "${sliderPosition.floatValue.toInt()} $measurementUnit")
    Slider(

        value = sliderPosition.floatValue,
        onValueChange = { newValue ->
            sliderPosition.floatValue = newValue
            onValueChange(newValue.toInt())
        },
        valueRange = minVal..maxVal,
        steps = 0, // No steps between values
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetGenderInput(
    modifier: Modifier = Modifier,
//    label: String,
    value: Gender?,
    onValueChange: (Gender) -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }
    val newValue = remember {
        mutableStateOf<Gender?>(value)
    }
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = !expanded.value
        }
    ) {

        TextField(
            value = if (newValue.value != null) newValue.value.toString() else "Gender",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            Gender.entries
                .forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.toString()) },
                        onClick = {
                            newValue.value = item
                            expanded.value = false
                            onValueChange(item)
                        }
                    )
                }
        }
    }
}

//@Preview
//@Composable
//fun GetSliderInputPreview(){
//    GetSliderInput(label = "height", onValueChange = { input -> onProfileChange(profile.copy(height = input))
//        onScreenChange("Profile", "gender")}))
//}

//@Preview(widthDp = 220, heightDp = 420)
//@Composable
//fun GetAgeInputPreview() {
////    GetAgeInput()
//}
//
//
//@Preview(widthDp = 220, heightDp = 420)
//@Composable
//fun RegisterPagePreview() {
//    val profile = Profile(
//        uid = "onlyName",
//        name = "Renana Rimon"
//    )
////    RegisterPage(profile = profile, registerStep = "age", onScreenChange = {"GetNameInput", "name" -> })
//}

@Preview(widthDp = 220, heightDp = 420)
@Composable
fun ProfileInputScreenPreview() {
    val screen = remember { mutableStateOf("welcome") }
    val registerStep = remember { mutableStateOf<String?>(null) }
    var profile = Profile(
        uid = "onlyName"

    )
    ProfileInputScreen(
        profile = profile,
        registerStep = "preferencesMatch",
        onProfileChange = { newProfile -> profile = newProfile },
        onScreenChange = { screenName, step ->
            screen.value = screenName
            registerStep.value = step
        })
}