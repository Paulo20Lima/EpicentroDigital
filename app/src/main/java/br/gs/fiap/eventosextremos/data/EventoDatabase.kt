package br.gs.fiap.eventosextremos.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.gs.fiap.eventosextremos.model.EventoModel

@Database(entities = [EventoModel::class], version = 1)
abstract class EventoDatabase : RoomDatabase() {
    abstract fun eventoDao(): EventoDao
}