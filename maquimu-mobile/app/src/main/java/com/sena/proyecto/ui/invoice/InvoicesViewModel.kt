package com.sena.proyecto.ui.invoice

import android.app.Application
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sena.proyecto.data.model.Factura
import com.sena.proyecto.data.repository.FacturaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class InvoicesViewModel(
    application: Application,
    private val facturaRepository: FacturaRepository
) : AndroidViewModel(application) {

    sealed class InvoicesState {
        object Loading : InvoicesState()
        data class Success(val facturas: List<Factura>) : InvoicesState()
        data class Error(val message: String) : InvoicesState()
    }

    sealed class DownloadState {
        object Idle : DownloadState()
        object Loading : DownloadState()
        data class Success(val filePath: String) : DownloadState()
        data class Error(val message: String) : DownloadState()
    }

    private val _state = MutableLiveData<InvoicesState>()
    val state: LiveData<InvoicesState> = _state

    private val _downloadState = MutableLiveData<DownloadState>(DownloadState.Idle)
    val downloadState: LiveData<DownloadState> = _downloadState

    fun loadFacturas() {
        _state.value = InvoicesState.Loading
        viewModelScope.launch {
            val result = facturaRepository.getMisFacturas()
            result.fold(
                onSuccess = { _state.value = InvoicesState.Success(it) },
                onFailure = { _state.value = InvoicesState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun descargarPdf(facturaId: Long) {
        _downloadState.value = DownloadState.Loading
        viewModelScope.launch {
            val result = facturaRepository.descargarFacturaPdf(facturaId)
            result.fold(
                onSuccess = { body ->
                    withContext(Dispatchers.IO) {
                        try {
                            val fileName = "factura_$facturaId.pdf"
                            val filePath = saveFile(fileName, body.bytes())
                            withContext(Dispatchers.Main) {
                                _downloadState.value = DownloadState.Success(filePath)
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                _downloadState.value = DownloadState.Error("Error al guardar PDF: ${e.message}")
                            }
                        }
                    }
                },
                onFailure = {
                    _downloadState.value = DownloadState.Error(it.message ?: "Error al descargar")
                }
            )
        }
    }

    fun clearDownloadState() {
        _downloadState.value = DownloadState.Idle
    }

    private fun saveFile(fileName: String, bytes: ByteArray): String {
        val context = getApplication<Application>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                ?: throw Exception("No se pudo crear archivo")
            context.contentResolver.openOutputStream(uri)?.use { it.write(bytes) }
            return uri.toString()
        } else {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)
            FileOutputStream(file).use { it.write(bytes) }
            return file.absolutePath
        }
    }

    class Factory(
        private val application: Application,
        private val facturaRepository: FacturaRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return InvoicesViewModel(application, facturaRepository) as T
        }
    }
}
