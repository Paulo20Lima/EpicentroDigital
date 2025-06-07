package br.gs.fiap.eventosextremos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import br.gs.fiap.eventosextremos.data.EventoDao
import br.gs.fiap.eventosextremos.data.EventoDatabase
import br.gs.fiap.eventosextremos.model.EventoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventoViewModel(application: Application) : AndroidViewModel(application) {

    private val eventoDao: EventoDao
    val eventosLiveData: LiveData<List<EventoModel>>

    init {
        val database = Room.databaseBuilder(
            getApplication(),
            EventoDatabase::class.java,
            "eventos_database"
        ).build()

        eventoDao = database.eventoDao()
        eventosLiveData = eventoDao.getAll()
    }

    fun adicionarEvento(
        local: String,
        tipo: String,
        impacto: String,
        data: String,
        pessoasAfetadas: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val evento = EventoModel(
                local = local,
                tipo = tipo,
                impacto = impacto,
                data = data,
                pessoasAfetadas = pessoasAfetadas
            )
            eventoDao.insert(evento)
        }
    }

    fun removerEvento(evento: EventoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            eventoDao.delete(evento)
        }
    }
}
