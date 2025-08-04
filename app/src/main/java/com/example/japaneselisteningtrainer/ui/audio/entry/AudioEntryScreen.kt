package com.example.japaneselisteningtrainer.ui.audio.entry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.japaneselisteningtrainer.R
import com.example.japaneselisteningtrainer.TrainerTopAppBar
import com.example.japaneselisteningtrainer.ui.AppViewModelProvider
import com.example.japaneselisteningtrainer.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object AudioEntryDestination : NavigationDestination {
    override val route = "audio_entry"
    override val titleRes = R.string.audio_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioEntryScreen(
    navigateBack: () -> Unit,
    onNavigationUp: () -> Unit,
    audioEntryViewModel: AudioEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var title by audioEntryViewModel.audioTitle
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TrainerTopAppBar(
                title = stringResource(AudioEntryDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigationUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            TextField(
                value = title,
                onValueChange = { title= it },
                label = { Text("Input A New Audio") }
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        audioEntryViewModel.addAudio()
                        navigateBack()
                        // Đặt trong coroutine vì nó cho phép cập nhật trạng thái UI chỉ sau khi saveItem() hoàn thành, còn nếu đặt bên ngoài, thì ngay khi bị suspend sẽ navigate luôn
                    }

                }
            ) {
                Text("Add Audio")
            }
        }
    }
}

