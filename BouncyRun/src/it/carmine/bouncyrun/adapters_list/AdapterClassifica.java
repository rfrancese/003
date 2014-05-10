package it.carmine.bouncyrun.adapters_list;

import java.util.ArrayList;

import it.carmine.bouncyrun.R;
import it.carmine.bouncyrun.util.httpRequests.PosizioneClassifica;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterClassifica  extends ArrayAdapter<Object> {
	  private final Context context;
	  private final ArrayList<PosizioneClassifica> values;

	  public AdapterClassifica(Context context, ArrayList<PosizioneClassifica>values) {
	    super(context, R.layout.custom_classifica_list_row,values.toArray());
	    this.context = context;
	    this.values = values;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate( R.layout.custom_classifica_list_row, parent, false);
	    
	    TextView nick = (TextView) rowView.findViewById(R.id.nick_classifica);
	    TextView punteggio=(TextView)rowView.findViewById(R.id.punteggio_classifica);
	    TextView difficolta=(TextView)rowView.findViewById(R.id.difficolta_classifica);
	    
	    
	    nick.setText(values.get(position).getNick());
	    punteggio.setText(values.get(position).getP());
	    difficolta.setText(values.get(position).getDiff());
	    return rowView;
	  }
	} 
