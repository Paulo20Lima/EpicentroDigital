import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.gs.fiap.eventosextremos.model.EventoModel
import br.gs.fiap.eventosextremos.R

class EventoAdapter(
    private val onItemRemoved: (EventoModel) -> Unit
) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    private var listaEventos = listOf<EventoModel>()

    inner class EventoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtLocal = view.findViewById<TextView>(R.id.txtLocal)
        val txtTipo = view.findViewById<TextView>(R.id.txtTipo)
        val txtImpacto = view.findViewById<TextView>(R.id.txtImpacto)
        val txtData = view.findViewById<TextView>(R.id.txtData)
        val txtPessoas = view.findViewById<TextView>(R.id.txtPessoas)
        val btnExcluir = view.findViewById<Button>(R.id.btnExcluir)

        fun bind(evento: EventoModel) {
            txtLocal.text = "Local: ${evento.local}"
            txtTipo.text = "Tipo: ${evento.tipo}"
            txtImpacto.text = "Impacto: ${evento.impacto}"
            txtData.text = "Data: ${evento.data}"
            txtPessoas.text = "Pessoas afetadas: ${evento.pessoasAfetadas}"

            btnExcluir.setOnClickListener {
                onItemRemoved(evento)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = listaEventos[position]
        holder.bind(evento)
    }

    override fun getItemCount(): Int = listaEventos.size

    fun atualizarLista(novaLista: List<EventoModel>) {
        listaEventos = novaLista
        notifyDataSetChanged()
    }
}