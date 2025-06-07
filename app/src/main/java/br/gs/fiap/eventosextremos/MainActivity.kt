package br.gs.fiap.eventosextremos

import EventoAdapter
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.gs.fiap.eventosextremos.viewmodel.EventoViewModel
import br.gs.fiap.eventosextremos.viewmodel.EventoViewModelFactory
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: EventoViewModel
    private lateinit var adapter: EventoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val edtLocal = findViewById<EditText>(R.id.edtLocal)
        val edtTipo = findViewById<EditText>(R.id.edtTipo)
        val edtImpacto = findViewById<EditText>(R.id.edtImpacto)
        val edtData = findViewById<EditText>(R.id.edtData)
        val edtPessoas = findViewById<EditText>(R.id.edtPessoas)
        val btnAdicionar = findViewById<Button>(R.id.btnAdicionar)
        val btnIdentificacao = findViewById<Button>(R.id.btnIdentificacao)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEventos)


        viewModel = ViewModelProvider(this, EventoViewModelFactory(application))
            .get(EventoViewModel::class.java)


        adapter = EventoAdapter { evento ->
            viewModel.removerEvento(evento)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        viewModel.eventosLiveData.observe(this) { eventos ->
            adapter.atualizarLista(eventos)
        }


        edtData.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                val dataFormatada = String.format("%02d/%02d/%04d", d, m + 1, y)
                edtData.setText(dataFormatada)
            }, year, month, day)

            datePicker.show()
        }


        btnAdicionar.setOnClickListener {
            val local = edtLocal.text.toString().trim()
            val tipo = edtTipo.text.toString().trim()
            val impacto = edtImpacto.text.toString().trim()
            val data = edtData.text.toString().trim()
            val pessoasTexto = edtPessoas.text.toString().trim()

            // Validação
            if (local.isEmpty() || tipo.isEmpty() || impacto.isEmpty() || data.isEmpty() || pessoasTexto.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pessoasAfetadas = pessoasTexto.toIntOrNull()
            if (pessoasAfetadas == null || pessoasAfetadas <= 0) {
                Toast.makeText(this, "Número de pessoas deve ser maior que zero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            viewModel.adicionarEvento(local, tipo, impacto, data, pessoasAfetadas)


            edtLocal.text.clear()
            edtTipo.text.clear()
            edtImpacto.text.clear()
            edtData.text.clear()
            edtPessoas.text.clear()
        }

        btnIdentificacao.setOnClickListener {
            startActivity(Intent(this, IdentificacaoActivity::class.java))
        }
    }
}
