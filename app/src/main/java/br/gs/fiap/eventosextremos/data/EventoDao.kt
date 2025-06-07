package br.gs.fiap.eventosextremos.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.gs.fiap.eventosextremos.model.EventoModel

@Dao
interface EventoDao {

    @Query("SELECT * FROM EventoModel")
    fun getAll(): LiveData<List<EventoModel>>

    @Insert
    fun insert(evento: EventoModel)

    @Delete
    fun delete(evento: EventoModel)
}