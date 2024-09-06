import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import java.text.SimpleDateFormat
import java.util.Locale
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.routine59b.R

@Composable
fun RoutineApp() {
    var selectedDay by remember { mutableStateOf("Select Day") }
    var classTimeInput by remember { mutableStateOf(TextFieldValue("")) }
    var classDetails by remember { mutableStateOf("") }
    val context = LocalContext.current
    val daysOfWeek = listOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    var showImage by remember { mutableStateOf(false) }
    var isClassFound by remember { mutableStateOf(false) }
    var titleColor by remember { mutableStateOf(Color.Black) } // Default color

    val classScheduleByDay = mapOf(
        "Sunday" to listOf(
            "Macro Economics - GED-2115 (RAB - 111)" to Pair("10:44 AM", "11:36 AM"),
            "Macro Economics - GED-2115 (RAB - 111)" to Pair("11:39 AM", "12:31 PM"),
            "Smart Application - CSE-3212 (ACL3)" to Pair("02:14 PM", "03:06 PM"),
            "Smart Application - CSE-3212 (ACL3)" to Pair("03:09 PM", "04:01 PM"),
            "Software LAB - CSE-3214 (Online)" to Pair("07:59 PM", "08:51 PM") // Online
        ),
        "Monday" to listOf(
            "Computer Networks - CSE-3231 (RAB - 306)" to Pair("08:54 AM", "09:46 AM"),
            "Computer Networks - CSE-3231 (RAB - 306)" to Pair("09:49 AM", "10:41 AM"),
            "Management - GED-1116 (RKB - 402)" to Pair("10:44 AM", "11:36 AM"),
            "Management - GED-1116 (RKB - 402)" to Pair("11:39 AM", "12:31 PM"),
            "Software Engineering - CSE-3213 (RKB - 105)" to Pair("02:14 PM", "03:06 PM"),
            "Software Engineering - CSE-3213 (RKB - 105)" to Pair("03:09 PM", "04:01 PM"),
            "Computer Networks - CSE-3232 (ACL3)" to Pair("04:04 PM", "04:56 PM")
        ),
        "Tuesday" to listOf(
            "Software LAB - CSE-3214 (ACL2)" to Pair("08:49 AM", "09:46 AM"),
            "Software Engineering - CSE-3213 (G2)" to Pair("09:49 AM", "10:41 AM"),
            "Computer Networks - CSE-3231 (RKB - 304)" to Pair("10:44 AM", "11:36 AM"),
            "Microprocessor - CSE-3201 (RKB - 304)" to Pair("1:29 PM", "2:11 PM"),
            "Microprocessor - CSE-3202 (Online)" to Pair("6:59 PM", "8:56 PM"),

            ),
        "Wednesday" to listOf(
            "Microprocessor - CSE-3202 (NL)" to Pair("12:34 PM", "01:26 PM"),
            "Smart Application - CSE-3212 (ACL3)" to Pair("1:29 PM", "2:11 PM"),
            "Microprocessor - CSE-3201 (RKB - 106)" to Pair("2:14 PM", "3:06 PM"),
            "Microprocessor - CSE-3202 (RKB - 106)" to Pair("3:09 PM", "4:01 PM"),
            "Software LAB - CSE-3214" to Pair("4:04 PM", "4:55 PM")
        ),
        "Saturday" to listOf(
            "Computer Networks - CSE-3232 (NL)" to Pair("10:44 AM", "11:36 AM"),
            "Computer Networks - CSE-3232 (NL)" to Pair("11:39 AM", "12:31 PM"),
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg), // Your background image resource
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Routine of 59B",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                color = titleColor,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(30.dp))

            var expanded by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {

                Button(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFfbbfbf) // Customize button color
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(8.dp) // Slight elevation for effect
                ) {
                    Text(
                        text = selectedDay,
                        fontSize = 18.sp, // Slightly larger text size
                        color = Color.White
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    daysOfWeek.forEach { day ->
                        DropdownMenuItem(
                            text = { Text(text = day) },
                            onClick = {
                                selectedDay = day
                                expanded = false
                            }
                        )
                    }
                }
            }

            TextField(
                value = classTimeInput,
                onValueChange = { classTimeInput = it },
                label = { Text("Enter Class Time (e.g., 08:30 AM)") },
                modifier = Modifier.fillMaxWidth(),

            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    val inputTime = classTimeInput.text
                    classDetails = "No class at this time"
                    isClassFound = false
                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    val parsedInputTime = try {
                        sdf.parse(inputTime)
                    } catch (e: Exception) {
                        null
                    }

                    if (parsedInputTime == null) {
                        Toast.makeText(
                            context,
                            "Invalid time format. Please use 'hh:mm AM/PM'",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    val scheduleForDay = classScheduleByDay[selectedDay] ?: emptyList()

                    var foundClass = false
                    for ((className, timeRange) in scheduleForDay) {
                        val startTime = try {
                            sdf.parse(timeRange.first)
                        } catch (e: Exception) {
                            null
                        }
                        val endTime = try {
                            sdf.parse(timeRange.second)
                        } catch (e: Exception) {
                            null
                        }

                        if (startTime != null && endTime != null && parsedInputTime.after(startTime) && parsedInputTime.before(endTime)) {
                            classDetails = className
                            foundClass = true
                            isClassFound = true
                            break
                        }
                    }

                    val toastMessage = if (foundClass) {
                        "😊"
                    } else {
                        "😢"
                    }
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF03DAC5)
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text(
                    text = "Search",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = classDetails,
                color = if (isClassFound) Color(0xFF03DAC5) else Color.Red,
                modifier = Modifier
                    .padding(8.dp)
                    .background(if (isClassFound) Color(0xFFc8f6d4) else Color(0xFFfbbfbf))
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(80.dp))


            Button(
                onClick = {
                    showImage = !showImage
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFc8f6d4)
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text(
                    text = if (showImage) "Hide Routine Image" else "See Routine Image",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showImage) {
                Image(
                    painter = painterResource(id = R.drawable.routine), // Replace with your image resource
                    contentDescription = "Class Routine Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Developed by Prapti Rani",
                modifier = Modifier
                    .padding(16.dp),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutineAppPreview() {
    RoutineApp()
}
