package com.example.jetpackinstrumentation

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackinstrumentation.ui.theme.JetpackInstrumentationTheme
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.io.ByteArrayInputStream
import java.io.File

public class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackInstrumentationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainView(this)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    private fun MainView(context: Context) {
        var texts by remember { mutableStateOf(listOf<@Composable () -> Unit>()) }
        var textsFromAnotherApp by remember { mutableStateOf(listOf<@Composable () -> Unit>()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    val loader = getDexClassLoader("classes.dex")
                    val libProviderClazz = loader.loadClass("com.example.mylibrary.HiddenViewKt")
                    val method = libProviderClazz.declaredMethods[0]
                    texts = texts + {
                        method.invoke(null, currentComposer, 0)
                    }
                },
            ) {
                Text(text = "Get text: ")
                texts.forEach { text ->
                    text()
                }
            }
            Button(
                onClick = {
                    val classLoader = getPatchClassLoader(packageManager, "com.example.myapplication", context)
                    val constructor =
                        (classLoader.loadClass("com.example.myapplication.MainActivity") as Class).constructors[0]
                    constructor.isAccessible = true
                    val instance = constructor.newInstance()
                    val method = instance.javaClass.declaredMethods[0]

                    textsFromAnotherApp = textsFromAnotherApp + {
                        method.invoke(instance, currentComposer, 0)
                    }
                },
            ) {
                Text(text = "Get text from another app: ")
                textsFromAnotherApp.forEach { text ->
                    text()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun getPatchClassLoader(pm: PackageManager, hostPackageName: String, context: Context): PathClassLoader {
        val packages =
            pm.getInstalledPackages(PackageManager.PackageInfoFlags.of(PackageManager.GET_ACTIVITIES.toLong()))
        for (i in packages.indices) {
            val packageInfo = packages[i]
            if (hostPackageName == packageInfo.packageName) {
                return PathClassLoader(packageInfo.applicationInfo.sourceDir, context.classLoader)
            }
        }
        throw Exception("Error")
    }

    fun getDexClassLoader(dexFileName: String): DexClassLoader {
        val dexFile = File.createTempFile("pref", ".dex")
        val inputStream = ByteArrayInputStream(baseContext.assets.open(dexFileName).readBytes())

        inputStream.use { input ->
            dexFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return DexClassLoader(
            dexFile.absolutePath,
            null,
            null,
            this.javaClass.classLoader
        )
    }
}